/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewProductEntityException;
import util.exception.DeletePartChoiceEntityException;
import util.exception.DeleteProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityExistException;
import util.exception.PartEntityNotFoundException;
import util.exception.PartNameNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.ProductNameNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.ProductSkuNotFoundException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnableToAddPartToProductException;
import util.exception.UnableToRemovePartFromProductException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author irene
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductEntitySessionBean() {

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    @Override
    public Long createNewProductEntity(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException, ProductSkuCodeExistException {

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);

        if (constraintViolations.isEmpty()) {
            try {
                entityManager.persist(newProductEntity);
                entityManager.flush();
                return newProductEntity.getProductEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        System.out.println("====================");
                        System.out.println(ex.getCause());
                        throw new CreateNewProductEntityException(ex.getMessage());
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Long createBrandNewProductEntity(ProductEntity newProductEntity, Integer quantityOnHand, Integer reorderQuantity, String brand,
            BigDecimal price) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException, ProductSkuCodeExistException {

        createNewProductEntity(newProductEntity);
        ProductEntity product;
        try {
            product = retrieveProductEntityBySkuCode(newProductEntity.getSkuCode());
        } catch (ProductSkuNotFoundException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT AS PRODUCT CANT BE FOUND");
        }

        Query query = entityManager.createQuery("SELECT p FROM PartEntity p WHERE p.partName = :inName");
        query.setParameter("inName", "Chassis");
        PartEntity chassis;
        try {
            chassis = (PartEntity) query.getSingleResult();

        } catch (NoResultException ex) {
            PartEntity chassisPart = new PartEntity("Chassis", "This is the Chassis of the Build");
            try {
                partEntitySessionBeanLocal.createNewPartEntity(chassisPart);
                chassis = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis");
            } catch (PartEntityExistException ex1) {
                throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT AS PART ALREADY EXIST");
            } catch (PartNameNotFoundException ex1) {
                throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT PART CANT BE FOUND");
            }
        }
        PartChoiceEntity chassisPartChoice = new PartChoiceEntity((product.getProductName() + " Chassis"), quantityOnHand, reorderQuantity, brand, price, product.getProductOverview(), product.getDescription());
        try {
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(chassisPartChoice);
        } catch (PartChoiceEntityExistException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }

        try {
            partEntitySessionBeanLocal.addPartChoiceToPart(chassisPartChoice.getPartChoiceEntityId(), chassis.getPartEntityId());
        } catch (PartEntityNotFoundException | PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }
        try {
            addPartToProduct(chassis.getPartEntityId(), product.getProductEntityId());
        } catch (PartEntityNotFoundException | ProductEntityNotFoundException | UnableToAddPartToProductException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }

        return product.getProductEntityId();
    }

    //With Image
    @Override
    public Long createBrandNewProductEntity(ProductEntity newProductEntity, Integer quantityOnHand, Integer reorderQuantity, String brand,
            BigDecimal price, String imageLink) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException, ProductSkuCodeExistException {

        createNewProductEntity(newProductEntity);
        ProductEntity product;
        try {
            product = retrieveProductEntityBySkuCode(newProductEntity.getSkuCode());
        } catch (ProductSkuNotFoundException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT AS PRODUCT CANT BE FOUND");
        }

        //Add image
        product.setImageLink(imageLink);

        Query query = entityManager.createQuery("SELECT p FROM PartEntity p WHERE p.partName = :inName");
        query.setParameter("inName", "Chassis");
        PartEntity chassis;
        try {
            chassis = (PartEntity) query.getSingleResult();

        } catch (NoResultException ex) {
            PartEntity chassisPart = new PartEntity("Chassis", "This is the Chassis of the Build");
            try {
                partEntitySessionBeanLocal.createNewPartEntity(chassisPart);
                chassis = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis");
            } catch (PartEntityExistException ex1) {
                throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT AS PART ALREADY EXIST");
            } catch (PartNameNotFoundException ex1) {
                throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT PART CANT BE FOUND");
            }
        }
        //Add image
        PartChoiceEntity chassisPartChoice = new PartChoiceEntity((product.getProductName() + " Chassis"), quantityOnHand, reorderQuantity, brand, price, product.getProductOverview(), product.getDescription());
        chassisPartChoice.setImageLink(imageLink);
        try {
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(chassisPartChoice);
        } catch (PartChoiceEntityExistException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }

        try {
            partEntitySessionBeanLocal.addPartChoiceToPart(chassisPartChoice.getPartChoiceEntityId(), chassis.getPartEntityId());
        } catch (PartEntityNotFoundException | PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }
        try {
            addPartToProduct(chassis.getPartEntityId(), product.getProductEntityId());
        } catch (PartEntityNotFoundException | ProductEntityNotFoundException | UnableToAddPartToProductException ex) {
            throw new CreateNewProductEntityException("UNABLE TO CREATE BRAND NEW PRODUCT");
        }

        return product.getProductEntityId();
    }

    @Override
    public List<ProductEntity> retrieveAllProductEntities() {
        Query query = entityManager.createQuery("SELECT p FROM ProductEntity p");
        List<ProductEntity> list = query.getResultList();
        for (ProductEntity prod : list) {
            prod.getReviewEntities().size();
            List<PartEntity> partList = prod.getPartEntities();
            for (PartEntity part : partList) {
                part.getPartChoiceEntities().size();
            }
        }
        return list;
    }

    @Override
    public List<ProductEntity> retrieveAllProductEntitiesThatCanSell() {
        //Retrieves Products that are not disabled, with no disabled parts but at least 1 part, with all parts that have at least 1 not disabled part choice
        List<ProductEntity> list = retrieveAllProductEntities();
        List<ProductEntity> newList = new ArrayList<>();

        //check product not disabled
        for (ProductEntity pe : list) {

            pe.getReviewEntities().size();
            boolean check = true;
            if (pe.getIsDisabled() == true) {
                check = false;
            } else {

                if (pe.getPartEntities().size() > 0) {
                    try {
                        //retrieve instance of Chassis Part Choice first
                        PartChoiceEntity chassisPartChoice = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(pe.getProductName() + " Chassis");
                        for (PartEntity part : pe.getPartEntities()) {
                            
                            if(part.getIsDisabled() == true) {
                                check = false;
                                break;
                            }
                            
                            if (part.getPartName().equals("Chassis")) {
                                continue;
                            } else {
                                boolean checkCompatiblePartChoice = false;
                                for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
                                    if (chassisPartChoice.getCompatiblePartsPartChoiceEntities().contains(pce) && pce.getIsDisabled() == false) {
                                        checkCompatiblePartChoice= true;
                                        break;
                                    }
                                }
                                
                                if (checkCompatiblePartChoice == false) {
                                    check = false;
                                    break;
                                }
                                
                            }
                        }
                    } catch (PartChoiceEntityNotFoundException ex) {
                        System.out.println("============= ERROR CANT FIND CHASSIS PART CHOICE ENTITY ===========");
                    }

                } else {
                    check = false;
                }

                if (check == false) {
                    break;
                }
            }
            //if all conditions pass, then can add into the new list to display
            if (check == true) {
                newList.add(pe);
            }
        }
        return newList;
    }

//    @Override
//    public List<ProductEntity> retrieveAllProductEntitiesThatCanSell() {
//        //Retrieves Products that are not disabled, with no disabled parts but at least 1 part, with all parts that have at least 1 not disabled part choice
//        List<ProductEntity> list = retrieveAllProductEntities();
//        List<ProductEntity> newList = new ArrayList<>();
//
//        //check product not disabled
//        for (ProductEntity pe : list) {
//
//            pe.getReviewEntities().size();
//            boolean check = true;
//            if (pe.getIsDisabled() == true) {
//                check = false;
//            } else {
//
//                if (pe.getPartEntities().size() > 0) {
//                    //check parts in product not disabled
//                    for (PartEntity part : pe.getPartEntities()) {
//                        if (part.getPartChoiceEntities().size() > 0) {
//                            if (part.getIsDisabled() == true) {
//                                check = false;
//                                break;
//                            } else {
//
//                                //check that there is at least 1 not disabled part choice
//                                boolean checkPartChoice = false;
//                                for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
//                                    //If no quantity on hand, then product should not be labelled as cannot sell,
//                                    //should still be live but front end need handle
////                                    if (pce.getIsDisabled() == false && pce.getQuantityOnHand() > 0) {
//                                    pce.getPromotionEntities().size();
//                                    if (pce.getIsDisabled() == false) {
//                                        checkPartChoice = true;
//                                        break;
//                                    }
//                                }
//                                if (checkPartChoice == false) {
//                                    check = false;
//                                    break;
//                                }
//                            }
//                        } else {
//                            check = false;
//                        }
//                    }
//                } else {
//                    check = false;
//                }
//
//                if (check == false) {
//                    break;
//                }
//            }
//            //if all conditions pass, then can add into the new list to display
//            if (check == true) {
//                newList.add(pe);
//            }
//        }
//        return newList;
//    }
    
    
    @Override
    public ProductEntity retrieveProductEntityByProductEntityId(Long productId) throws ProductEntityNotFoundException {
        ProductEntity productEntity = entityManager.find(ProductEntity.class,
                productId);

        if (productEntity != null) {
            productEntity.getReviewEntities().size();
            List<PartEntity> partList = productEntity.getPartEntities();
            for (PartEntity part : partList) {
                part.getPartChoiceEntities().size();
            }
            return productEntity;
        } else {
            throw new ProductEntityNotFoundException("Product ID " + productId + " does not exist!");
        }
    }

    @Override
    public ProductEntity retrieveProductEntityBySkuCode(String skuCode) throws ProductSkuNotFoundException {
        String queryInString = "SELECT p FROM ProductEntity p WHERE p.skuCode = :iSkuCode";
        Query retrievalQuery = entityManager.createQuery(queryInString);
        retrievalQuery.setParameter("iSkuCode", skuCode);
        ProductEntity result = (ProductEntity) retrievalQuery.getSingleResult();
        if (result == null) {
            throw new ProductSkuNotFoundException("Product with SKU Code of " + skuCode + " does not exist.");
        } else {
            result.getReviewEntities().size();
            List<PartEntity> partList = result.getPartEntities();
            for (PartEntity part : partList) {
                part.getPartChoiceEntities().size();
            }
            return result;
        }
    }

    @Override
    public ProductEntity retrieveProductEntityByProductName(String productName) throws ProductNameNotFoundException {
        String queryInString = "SELECT p FROM ProductEntity p WHERE p.productName = iProductName";
        Query retrievalQuery = entityManager.createQuery(queryInString);
        retrievalQuery.setParameter("iProductName", productName);
        ProductEntity result = (ProductEntity) retrievalQuery.getSingleResult();
        if (result == null) {
            throw new ProductNameNotFoundException("Product with Product Name of " + productName + " does not exist.");
        } else {
            result.getReviewEntities().size();
            List<PartEntity> partList = result.getPartEntities();
            for (PartEntity part : partList) {
                part.getPartChoiceEntities().size();
            }
            return result;
        }
    }

    @Override
    public void updateProductEntity(ProductEntity productEntity) throws ProductEntityNotFoundException, UpdateProductEntityException, InputDataValidationException {
        if (productEntity != null && productEntity.getProductEntityId() != null) {
            Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntity);

            if (constraintViolations.isEmpty()) {
                ProductEntity productEntityToUpdate = retrieveProductEntityByProductEntityId(productEntity.getProductEntityId());

                if (productEntityToUpdate.getProductEntityId().equals(productEntity.getProductEntityId())) {
                    productEntityToUpdate.setProductName(productEntity.getProductName());
                    productEntityToUpdate.setDescription(productEntity.getDescription());
                    productEntityToUpdate.setProductOverview(productEntity.getProductOverview());
                    productEntityToUpdate.setImageLink(productEntity.getImageLink());

                    productEntityToUpdate.setPartEntities(productEntity.getPartEntities());
                } else {
                    throw new UpdateProductEntityException("Product ID of productEntity record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ProductEntityNotFoundException("ProductEntity ID not provided for productEntity to be updated");
        }
    }

    @Override
    public void updateProductEntitiyWithNewParts(List<PartEntity> listOfNewParts, ProductEntity productEntityToUpdate) throws UpdateProductEntityException {
        ProductEntity managedProductToUpdate;
        try {
            managedProductToUpdate = retrieveProductEntityByProductEntityId(productEntityToUpdate.getProductEntityId());
            //Remove old links first (EXCEPT CHASSIS)
            List<PartEntity> oldListOfParts = productEntityToUpdate.getPartEntities();
            oldListOfParts.removeIf(part -> part.getPartName().equals("Chassis"));
            System.out.println("TO REMOVE =================");

            for (PartEntity oldPart : oldListOfParts) {
                try {
                    System.out.println(oldPart.getPartName());
                    partEntitySessionBeanLocal.removePartFromProduct(oldPart.getPartEntityId(), productEntityToUpdate.getProductEntityId());
                } catch (PartEntityNotFoundException | ProductEntityNotFoundException | UnableToRemovePartFromProductException ex) {
                    throw new UpdateProductEntityException(ex.getMessage());
                }
            }
            System.out.println("TO ADD =================");

            //Now add back new List Of Parts selected (EXCEPT CHASSIS)
            for (PartEntity newPart : listOfNewParts) {

                try {
                    System.out.println(newPart.getPartName());
                    partEntitySessionBeanLocal.addPartToProduct(newPart.getPartEntityId(), productEntityToUpdate.getProductEntityId());
                } catch (PartEntityNotFoundException | UnableToAddPartToProductException ex) {
                    throw new UpdateProductEntityException(ex.getMessage());
                }
            }
        } catch (ProductEntityNotFoundException ex) {
            throw new UpdateProductEntityException(ex.getMessage());
        }
    }

    @Override
    public void deleteProductEntity(Long productEntityId) throws ProductEntityNotFoundException, DeleteProductEntityException {
        try {
            ProductEntity productEntityToRemove = retrieveProductEntityByProductEntityId(productEntityId);
            if (productEntityToRemove.getPartEntities().size() == 1) {
                //Remove PartChoice From Part
                PartChoiceEntity chassisPartChoice = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(productEntityToRemove.getProductName() + " Chassis");
                partChoiceEntitySessionBeanLocal.deletePartChoiceEntity(chassisPartChoice.getPartChoiceEntityId());
                //Remove Part From Product
                PartEntity chassisPart = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis");
                removePartFromProduct(chassisPart.getPartEntityId(), productEntityToRemove.getProductEntityId());
                entityManager.remove(productEntityToRemove);

            } else {
                throw new DeleteProductEntityException("Unable to Delete Product That is Linked To Parts Other Than Chassis");
            }

        } catch (PartChoiceEntityNotFoundException | DeletePartChoiceEntityException | PartNameNotFoundException | PartEntityNotFoundException | UnableToRemovePartFromProductException ex) {
            System.out.println(ex.getMessage());
            throw new DeleteProductEntityException("Unable to Delete Product" + ex.getMessage());
        }
    }

    @Override
    public void toggleDisableProductEntity(Long productEntityId) throws UpdateProductEntityException {
        try {
            ProductEntity productToDisable = retrieveProductEntityByProductEntityId(productEntityId);
            PartChoiceEntity chassisPartChoice = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(productToDisable.getProductName() + " Chassis");
            productToDisable.setIsDisabled(!productToDisable.getIsDisabled());
            chassisPartChoice.setIsDisabled(!chassisPartChoice.getIsDisabled());
        } catch (ProductEntityNotFoundException | PartChoiceEntityNotFoundException ex) {
            throw new UpdateProductEntityException("Unable To Disable/Enable Product!");
        }
    }

    @Override
    public void addPartToProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToAddPartToProductException {
        PartEntity partToAdd = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(partEntityId);
        ProductEntity productToAdd = retrieveProductEntityByProductEntityId(productEntityId);

        if (partToAdd.getProductEntities().contains(productToAdd) || productToAdd.getPartEntities().contains(partToAdd)) {
            throw new UnableToAddPartToProductException("Unable to Add Part To product");
        } else {
            partToAdd.getProductEntities().add(productToAdd);
            productToAdd.getPartEntities().add(partToAdd);
        }
    }

    @Override
    public void removePartFromProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToRemovePartFromProductException {
        PartEntity partToRemove = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(partEntityId);
        ProductEntity productToRemove = retrieveProductEntityByProductEntityId(productEntityId);

        if (!(partToRemove.getProductEntities().contains(productToRemove) || productToRemove.getPartEntities().contains(partToRemove))) {
            throw new UnableToRemovePartFromProductException("Unable to remove Part from Product");
        } else {
            partToRemove.getProductEntities().remove(productToRemove);
            productToRemove.getPartEntities().remove(partToRemove);
        }
    }
    // assuming parts have yet to exist
    //    @Override
    //    public ProductEntity createNewProduct(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException,
    //            UnknownPersistenceException, ProductSkuCodeExistException {
    //
    //        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);
    //
    //        if (constraintViolations.isEmpty()) {
    //
    //            try {
    //                /*
    //                String productTypeName = newProductEntity.getProductTypeName();
    //                ProductTypeEntity productType = new ProductTypeEntity(productTypeName);
    //                productTypeSessionBeanLocal.createNewProductTypeEntity(productType);
    //                System.out.println("persist product type");
    //                
    //                /*
    //                List<PartEntity> listOfPartEntities = newProductEntity.getParts();
    //                
    //                
    //                for (PartEntity p : listOfPartEntities) {
    //
    //                    List<PartChoiceEntity> listOfPartChoices = p.getPartChoices();
    //                    
    //                    for (PartChoiceEntity pc : listOfPartChoices) {
    //
    //                        entityManager.persist(pc);
    //                        System.out.println("persist part choices");
    //                    }
    //
    //                    entityManager.persist(p);
    //                    System.out.println("persist part");
    //
    //                }
    //                */
    //                entityManager.persist(newProductEntity);
    //                System.out.println("persist product");
    //
    //                entityManager.flush();
    //
    //                return newProductEntity;
    //
    //            } catch (PersistenceException ex) {
    //
    //                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
    //
    //                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
    //
    //                        throw new ProductSkuCodeExistException();
    //
    //                    } else {
    //                        throw new UnknownPersistenceException(ex.getMessage());
    //                    }
    //                } else {
    //
    //                    throw new UnknownPersistenceException(ex.getMessage());
    //
    //                }
    //            }
    //
    //        } else {
    //
    //            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
    //
    //        }
    //
    //    }
    //    public void updateProduct(ProductEntity updatedProductEntity) throws UpdateProductEntityException {
    //
    //        //assume that if a part (eg.CPU) was related to a product, it wont become unrelated to the product 
    //        //ie will only add parts to a product, wont remove parts from a product
    //        if (updatedProductEntity != null && updatedProductEntity.getSkuCode() != null) {
    //            Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(updatedProductEntity);
    //            if (constraintViolations.isEmpty()) {
    //
    //                try {
    //                    ProductEntity productToBeUpdated = retrieveProductEntityBySkuCode(updatedProductEntity.getSkuCode());
    //                    List<PartEntity> newPartList = new ArrayList<>();
    //
    //                    // update the part
    //                    for (PartEntity p : updatedProductEntity.getParts()) {
    //                        PartEntity pToBeUpdated = entityManager.find(PartEntity.class, p.getPartId());
    //                        if (pToBeUpdated == null) {
    //                            throw new UpdateProductEntityException("An error has occured while updating the product: part cannot be found. ");
    //                        } else {
    //                            newPartList.add(p);
    //                        }
    //                    }
    //
    //                    productToBeUpdated.setParts(newPartList);
    //
    //                    //not finished yet tbc
    //                } catch (ProductSkuNotFoundException ex) {
    //                    //do something
    //                }
    //
    //            }
    //
    //        }
    //
    //    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ProductEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
