/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import entity.ProductTypeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.ProductSkuNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author irene
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal {

    @EJB
    private ProductTypeSessionBeanLocal productTypeSessionBeanLocal;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductEntitySessionBean() {

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    // assuming parts have yet to exist
    @Override
    public ProductEntity createNewProduct(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException, ProductSkuCodeExistException {

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);

        if (constraintViolations.isEmpty()) {

            try {
                /*
                String productTypeName = newProductEntity.getProductTypeName();
                ProductTypeEntity productType = new ProductTypeEntity(productTypeName);
                productTypeSessionBeanLocal.createNewProductTypeEntity(productType);
                System.out.println("persist product type");
                
                /*
                List<PartEntity> listOfPartEntities = newProductEntity.getParts();
                
                
                for (PartEntity p : listOfPartEntities) {

                    List<PartChoiceEntity> listOfPartChoices = p.getPartChoices();
                    
                    for (PartChoiceEntity pc : listOfPartChoices) {

                        entityManager.persist(pc);
                        System.out.println("persist part choices");
                    }

                    entityManager.persist(p);
                    System.out.println("persist part");

                }
                */
                entityManager.persist(newProductEntity);
                System.out.println("persist product");

                entityManager.flush();

                return newProductEntity;

            } catch (PersistenceException ex) {

                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {

                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {

                        throw new ProductSkuCodeExistException();

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

    public void updateProduct(ProductEntity updatedProductEntity) throws UpdateProductEntityException {

        //assume that if a part (eg.CPU) was related to a product, it wont become unrelated to the product 
        //ie will only add parts to a product, wont remove parts from a product
        if (updatedProductEntity != null && updatedProductEntity.getSkuCode() != null) {
            Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(updatedProductEntity);
            if (constraintViolations.isEmpty()) {

                try {
                    ProductEntity productToBeUpdated = retrieveProductEntityBySkuCode(updatedProductEntity.getSkuCode());
                    List<PartEntity> newPartList = new ArrayList<>();

                    // update the part
                    for (PartEntity p : updatedProductEntity.getParts()) {
                        PartEntity pToBeUpdated = entityManager.find(PartEntity.class, p.getPartId());
                        if (pToBeUpdated == null) {
                            throw new UpdateProductEntityException("An error has occured while updating the product: part cannot be found. ");
                        } else {
                            newPartList.add(p);
                        }
                    }

                    productToBeUpdated.setParts(newPartList);

                    //not finished yet tbc
                } catch (ProductSkuNotFoundException ex) {
                    //do something
                }

            }

        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ProductEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    @Override
    public ProductEntity retrieveProductEntityBySkuCode(String skuCode) throws ProductSkuNotFoundException {
        String queryInString = "SELECT p FROM ProductEntity p WHERE p.skuCode = iSkuCode";
        Query retrievalQuery = entityManager.createQuery(queryInString);
        retrievalQuery.setParameter("iSkuCode", skuCode);
        ProductEntity result = (ProductEntity) retrievalQuery.getSingleResult();
        if (result == null) {
            throw new ProductSkuNotFoundException("Product with SKU Code of " + skuCode + " does not exist.");
        }

        return result;

    }

}
