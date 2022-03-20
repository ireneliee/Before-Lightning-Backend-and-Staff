/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
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
import util.exception.DeletePartEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityExistException;
import util.exception.PartEntityNotFoundException;
import util.exception.PartNameNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnableToAddPartToProductException;
import util.exception.UnableToRemovePartChoiceFromPartException;
import util.exception.UnableToRemovePartFromProductException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartEntityException;

/**
 *
 * @author irene
 */
@Stateless
public class PartEntitySessionBean implements PartEntitySessionBeanLocal {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;

    public PartEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewPartEntity(PartEntity newPartEntity) throws PartEntityExistException, InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<PartEntity>> constraintViolations = validator.validate(newPartEntity);

        if (constraintViolations.isEmpty()) {
            try {
                entityManager.persist(newPartEntity);
                entityManager.flush();
                return newPartEntity.getPartEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        System.out.println("====================");
                        System.out.println(ex.getCause());
                        throw new PartEntityExistException();
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
    public List<PartEntity> retrieveAllPartEntities() {
        Query query = entityManager.createQuery("SELECT p FROM PartEntity p");
        List<PartEntity> list = query.getResultList();
        for (PartEntity part : list) {
            part.getPartChoiceEntities().size();
            part.getProductEntities().size();
        }
        return list;
    }

    @Override
    public PartEntity retrievePartEntityByPartEntityId(Long partEntityId) throws PartEntityNotFoundException {
        PartEntity pe = entityManager.find(PartEntity.class, partEntityId);
        if (pe == null) {
            throw new PartEntityNotFoundException();
        } else {
            pe.getPartChoiceEntities().size();
            pe.getProductEntities().size();
            return pe;
        }
    }

    @Override
    public PartEntity retrievePartEntityByPartName(String partName) throws PartNameNotFoundException {
        String queryInString = "SELECT p FROM PartEntity p WHERE p.partName = :iPartName";
        Query retrievalQuery = entityManager.createQuery(queryInString);
        retrievalQuery.setParameter("iPartName", partName);
        PartEntity result = (PartEntity) retrievalQuery.getSingleResult();
        if (result == null) {
            throw new PartNameNotFoundException("Product with Product Name of " + partName + " does not exist.");
        } else {
            result.getPartChoiceEntities().size();
            result.getProductEntities().size();
        }
        return result;
    }

    @Override
    public void deletePartEntity(Long partEntityId) throws PartEntityNotFoundException, DeletePartEntityException {
        PartEntity partEntityToRemove = retrievePartEntityByPartEntityId(partEntityId);
        //need to check for any conditions before removing product?
        if (partEntityToRemove.getProductEntities().size() > 0 || partEntityToRemove.getPartChoiceEntities().size() > 0) {
            throw new DeletePartEntityException("Unable to Delete Part that is part of Product or has Part Choices");
        } else {
            entityManager.remove(partEntityToRemove);
        }
    }

    @Override
    public void toggleDisablePartEntity(Long partEntityId) throws UpdatePartEntityException {
        try {
            PartEntity partToDisable = retrievePartEntityByPartEntityId(partEntityId);
            partToDisable.setIsDisabled(!partToDisable.getIsDisabled());
        } catch (PartEntityNotFoundException ex) {
            throw new UpdatePartEntityException("Unable To Disable/Enable Part!");
        }
    }

    @Override
    public void updatePartEntity(PartEntity partEntity) throws PartEntityNotFoundException, UpdatePartEntityException, InputDataValidationException {
        if (partEntity != null && partEntity.getPartEntityId() != null) {
            Set<ConstraintViolation<PartEntity>> constraintViolations = validator.validate(partEntity);

            if (constraintViolations.isEmpty()) {
                PartEntity partEntityToUpdate = retrievePartEntityByPartEntityId(partEntity.getPartEntityId());

                if (partEntityToUpdate.getPartEntityId().equals(partEntity.getPartEntityId())) {
                    partEntityToUpdate.setPartName(partEntity.getPartName());
                    partEntityToUpdate.setDescription(partEntity.getDescription());

                    partEntityToUpdate.setPartChoiceEntities(partEntity.getPartChoiceEntities());
                    partEntityToUpdate.setProductEntities(partEntity.getProductEntities());

                } else {
                    throw new UpdatePartEntityException("Username of partEntity record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PartEntityNotFoundException("PartEntity ID not provided for partEntity to be updated");
        }
    }

    @Override
    public void addPartToProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToAddPartToProductException {
        PartEntity partToAdd = retrievePartEntityByPartEntityId(partEntityId);
        ProductEntity productToAdd = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(productEntityId);

        if (partToAdd.getProductEntities().contains(productToAdd) || productToAdd.getPartEntities().contains(partToAdd)) {
            throw new UnableToAddPartToProductException("Unable to Add Part To product");
        } else {
            partToAdd.getProductEntities().add(productToAdd);
            productToAdd.getPartEntities().add(partToAdd);
        }
    }

    @Override
    public void removePartFromProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToRemovePartFromProductException {
        PartEntity partToRemove = retrievePartEntityByPartEntityId(partEntityId);
        ProductEntity productToRemove = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(productEntityId);

        if (!partToRemove.getProductEntities().contains(productToRemove) || !productToRemove.getPartEntities().contains(partToRemove)) {
            throw new UnableToRemovePartFromProductException("Unable to remove Part from Product");
        } else {
            partToRemove.getProductEntities().remove(productToRemove);
            productToRemove.getPartEntities().remove(partToRemove);
        }
    }

    @Override
    public void addPartChoiceToPart(Long partChoiceEntityId, Long partEntityId) throws PartEntityNotFoundException, PartChoiceEntityNotFoundException, UnableToAddPartChoiceToPartException {
        PartChoiceEntity partChoiceToAdd = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(partChoiceEntityId);
        PartEntity partToAdd = retrievePartEntityByPartEntityId(partEntityId);

        if (partToAdd.getPartChoiceEntities().contains(partChoiceToAdd)) {
            throw new UnableToAddPartChoiceToPartException("Unable to Add Part Choice To Part");
        } else {
            partToAdd.getPartChoiceEntities().add(partChoiceToAdd);
        }
    }

    @Override
    public void addPartChoiceToListOfParts(Long partChoiceEntityId, List<PartEntity> listOfPartEntities) throws UnableToAddPartChoiceToPartException {

        for (PartEntity part : listOfPartEntities) {
            try {
                addPartChoiceToPart(partChoiceEntityId, part.getPartEntityId());
            } catch (PartEntityNotFoundException | PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartException ex) {
                throw new UnableToAddPartChoiceToPartException(ex.getMessage());
            }
        }
    }

    @Override
    public void removePartChoiceFromPart(Long partChoiceEntityId, Long partEntityId) throws PartEntityNotFoundException, PartChoiceEntityNotFoundException, UnableToRemovePartChoiceFromPartException {
        PartChoiceEntity partChoiceToRemove = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(partChoiceEntityId);
        PartEntity partToRemove = retrievePartEntityByPartEntityId(partEntityId);

        if (!partToRemove.getPartChoiceEntities().contains(partChoiceToRemove)) {
            throw new UnableToRemovePartChoiceFromPartException("Unable to Remove Part Choice From Part");
        } else {
            partToRemove.getPartChoiceEntities().remove(partChoiceToRemove);
        }
    }

    // to add new parts with all new part choices, linked with existing product
//    @Override
//    public PartEntity createNewPartEntity(List<String> productCodes, PartEntity newPartEntity) throws CreateNewPartEntityException,
//            UnknownPersistenceException, InputDataValidationException {
//        Set<ConstraintViolation<PartEntity>> constraintViolations = validator.validate(newPartEntity);
//
//        if (constraintViolations.isEmpty()) {
//            try {
//                // persisting the new part choices
//                /*
//                for (PartChoiceEntity p : newPartEntity.getPartChoices()) {
//
//                    entityManager.persist(p);
//
//                }
//                 */
//
//                entityManager.persist(newPartEntity);
//
//                entityManager.flush();
//
//                PartEntity newlyCreatedPart = entityManager.find(PartEntity.class, newPartEntity.getPartId());
//
//                // updating the product
//                for (String skuCode : productCodes) {
//                    try {
//                        ProductEntity product = productEntitySessionBeanLocal.retrieveProductEntityBySkuCode(skuCode);
//                        product.getParts().add(newlyCreatedPart);
//                        newlyCreatedPart.getProducts().add(product);
//                    } catch (ProductSkuNotFoundException ex) {
//
//                        throw new CreateNewPartEntityException("An error has occured while creating the part: " + ex.getMessage());
//
//                    }
//                }
//
//                return newlyCreatedPart;
//            } catch (PersistenceException ex) {
//                throw new UnknownPersistenceException(ex.getMessage());
//            }
//        } else {
//            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//        }
//    }
//    @Override
//    public PartEntity createNewPartEntity(PartEntity newPartEntity) throws CreateNewPartEntityException,
//            UnknownPersistenceException, InputDataValidationException {
//        Set<ConstraintViolation<PartEntity>> constraintViolations = validator.validate(newPartEntity);
//
//        if (constraintViolations.isEmpty()) {
//            try {
//                // persisting the new part choices
//                /*
//                for (PartChoiceEntity p : newPartEntity.getPartChoices()) {
//
//                    entityManager.persist(p);
//
//                }
//                 */
//
//                entityManager.persist(newPartEntity);
//
//                entityManager.flush();
//
//                return newPartEntity;
//            } catch (PersistenceException ex) {
//                throw new UnknownPersistenceException(ex.getMessage());
//            }
//        } else {
//            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//        }
//    }
    // updating parts with existing products
    // Use this if you want to update part choice entity 
//    @Override
//    public void updatePartEntity(PartEntity updatedPartEntity) throws PartEntityNotFoundException,
//            UpdatePartEntityException, InputDataValidationException {
//
//        if (updatedPartEntity != null && updatedPartEntity.getPartEntityId() != null) {
//            Set<ConstraintViolation<PartEntity>> constraintViolations = validator.validate(updatedPartEntity);
//            if (constraintViolations.isEmpty()) {
//
//                try {
//                    PartEntity partToBeUpdated = retrievePartEntityByPartEntityId(updatedPartEntity.getPartEntityId());
//
//                    // update part choices of individual product - remove parts from products
//                    for (ProductEntity p : partToBeUpdated.getProducts()) {
//                        p.getParts().remove(partToBeUpdated);
//                    }
//
//                    // update the product
//                    partToBeUpdated.getProductEntities().clear();
//                    for (ProductEntity p : updatedPartEntity.getProductEntities()) {
//                        ProductEntity productToBeUpdated = entityManager.find(ProductEntity.class, p.getProductTypeEntityId());
//                        if (productToBeUpdated == null) {
//                            throw new UpdatePartEntityException("An error has occured while updating the part: product cannot be found. ");
//                        } else {
//                            partToBeUpdated.getProducts().add(productToBeUpdated);
//                            // update part choices of newly added product - add parts to products
//                            productToBeUpdated.getParts().add(partToBeUpdated);
//                        }
//
//                    }
//
//                    // updating part choices
//                    List<PartChoiceEntity> listOfPartChoices = new ArrayList<>();
//
//                    for (PartChoiceEntity p : updatedPartEntity.getPartChoices()) {
//                        PartChoiceEntity toBeAdded = null;
//
//                        if (p.getPartChoiceId() != null) {
//                            toBeAdded = entityManager.find(PartChoiceEntity.class, p.getPartChoiceId());
//                        }
//
//                        // if the part choices do not currently exist in database
//                        if (toBeAdded == null) {
//                            entityManager.persist(toBeAdded);
//                            entityManager.flush();
//                            toBeAdded = entityManager.find(PartChoiceEntity.class, toBeAdded.getPartChoiceId());
//                        }
//
//                        listOfPartChoices.add(toBeAdded);
//                    }
//
//                    partToBeUpdated.setPartChoices(listOfPartChoices);
//                    partToBeUpdated.setPartName(updatedPartEntity.getPartName());
//                    partToBeUpdated.setDescription(updatedPartEntity.getDescription());
//
//                } catch (PartEntityNotFoundException ex) {
//                    throw new UpdatePartEntityException("An error has occured while updating the part: part ID does not exist. ");
//                }
//
//            } else {
//                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//            }
//        } else {
//            throw new PartEntityNotFoundException("Part entity ID is not provided. ");
//        }
//    }
//    @Override
//    public void removePartEntity(Long partEntityId) throws PartEntityNotFoundException, DeletePartEntityException {
//        try {
//            PartEntity partEntity = retrievePartEntityByPartEntityId(partEntityId);
//
//            List<ProductEntity> listOfProductEntitiesLinked = partEntity.getProducts();
//
//            if (listOfProductEntitiesLinked.isEmpty()) {
//                List<PartChoiceEntity> listOfPartChoiceEntity = partEntity.getPartChoices();
//                if (listOfPartChoiceEntity.isEmpty()) {
//
//                    entityManager.remove(partEntity);
//
//                } else {
//
//                    throw new DeletePartEntityException("An error has occured while removing part entity: part entity is linked to a part choice entity. ");
//
//                }
//
//            } else {
//                throw new DeletePartEntityException("An error has occured while removing part entity: part entity is linked to a product. ");
//            }
//        } catch (PartEntityNotFoundException ex) {
//            throw new PartEntityNotFoundException("An error has occured while removing part entity: part ID does not exist.");
//        }
//
//    }
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
