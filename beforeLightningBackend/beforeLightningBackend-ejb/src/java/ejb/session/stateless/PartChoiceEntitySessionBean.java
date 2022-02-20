/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewPartChoiceEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author irene
 */
@Stateless
public class PartChoiceEntitySessionBean implements PartChoiceEntitySessionBeanLocal {

    @EJB
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;
    @Resource
    private EJBContext eJBContext;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PartChoiceEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    // update the part choice entity alone, without promotions
    @Override
    public void updatePartChoiceEntity(List<Long> newRelatedPartChoiceEntityIds, PartChoiceEntity updatedPartChoiceEntity) throws PartChoiceEntityNotFoundException, 
            UpdatePartChoiceEntityException{
        if(updatedPartChoiceEntity != null && updatedPartChoiceEntity.getPartChoiceId() != null) {
            
            Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(updatedPartChoiceEntity);
            
            if(constraintViolations.isEmpty()) {
                PartChoiceEntity partChoiceToBeUpdated = entityManager.find(PartChoiceEntity.class, updatedPartChoiceEntity.getPartChoiceId());
                
                if (partChoiceToBeUpdated == null) {
                    throw new PartChoiceEntityNotFoundException("Part choice ID given does not exist within the system. ");
                }
                
                partChoiceToBeUpdated.getLeftSuitablePartChoices().clear();
                partChoiceToBeUpdated.getRightSuitablePartChoices().clear();
                
                for(Long partChoiceId: newRelatedPartChoiceEntityIds) {
                    PartChoiceEntity p = entityManager.find(PartChoiceEntity.class, partChoiceId);
                    if(p == null) {
                        eJBContext.setRollbackOnly();
                        throw new UpdatePartChoiceEntityException("An error has occured while updating the part choice: id of related part choice does not exist .");
                    }
                    
                    partChoiceToBeUpdated.getLeftSuitablePartChoices().add(p);
                    p.getRightSuitablePartChoices().add(partChoiceToBeUpdated);
                    
                }
                
                partChoiceToBeUpdated.setBrand(updatedPartChoiceEntity.getBrand());
                partChoiceToBeUpdated.setSpecification(updatedPartChoiceEntity.getSpecification());
                partChoiceToBeUpdated.setQuantityOnHand(updatedPartChoiceEntity.getQuantityOnHand());
                partChoiceToBeUpdated.setReorderQuantity(updatedPartChoiceEntity.getReorderQuantity());
                partChoiceToBeUpdated.setPrice(updatedPartChoiceEntity.getPrice());
                partChoiceToBeUpdated.setPartOverview(updatedPartChoiceEntity.getPartOverview());
                partChoiceToBeUpdated.setPartDescription(updatedPartChoiceEntity.getPartDescription());
            }
        
        } else {
            throw new PartChoiceEntityNotFoundException("Part choice ID not provided for the part choice to be updated. ");
        }
    }
    
    
    @Override
    public List<PartChoiceEntity> retrieveAllPartChoices() {
        String queryInString = "SELECT p FROM PartChoiceEntity p";
        Query query = entityManager.createQuery(queryInString);
        List<PartChoiceEntity> queryResult = query.getResultList();
        
        for(PartChoiceEntity p: queryResult) {
            p.getLeftSuitablePartChoices().size();
            p.getRightSuitablePartChoices().size();
            p.getPromotions().size();
        }
        
        return queryResult;
    }
    
    @Override
    public PartChoiceEntity retrievePartChoiceEntityById(Long partChoiceEntityId) throws PartChoiceEntityNotFoundException {
        
        PartChoiceEntity partChoiceToRetrieve = entityManager.find(PartChoiceEntity.class, partChoiceEntityId);
        
        if(partChoiceToRetrieve == null) {
            throw new PartChoiceEntityNotFoundException("An error has occured while trying to retrieve part choice entity: part choice ID cannot be found. ");
        }
        
        return partChoiceToRetrieve;
        
    }

    // with existing related part choice entity
    @Override
    public PartChoiceEntity createPartChoiceEntity(List<Long> relatedPartChoiceEntityIds, Long partEntityId, PartChoiceEntity newPartChoice) throws UnknownPersistenceException, InputDataValidationException, CreateNewPartChoiceEntityException, PartEntityNotFoundException {

        Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(newPartChoice);

        if (constraintViolations.isEmpty()) {
            PartEntity partEntity = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(partEntityId);

            entityManager.persist(newPartChoice);
            entityManager.flush();

            partEntity.getPartChoices().add(newPartChoice);

            for (Long id : relatedPartChoiceEntityIds) {
                PartChoiceEntity relatedPartChoice = entityManager.find(PartChoiceEntity.class, id);
                if (relatedPartChoice == null) {
                    eJBContext.setRollbackOnly();
                    throw new CreateNewPartChoiceEntityException("An error has occured while creating the part choice entity: id of related part choice does not exist. ");
                } else {
                    relatedPartChoice.getLeftSuitablePartChoices().add(newPartChoice);
                    newPartChoice.getRightSuitablePartChoices().add(relatedPartChoice);
                }
                
            }
            
            return newPartChoice;

        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartChoiceEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
