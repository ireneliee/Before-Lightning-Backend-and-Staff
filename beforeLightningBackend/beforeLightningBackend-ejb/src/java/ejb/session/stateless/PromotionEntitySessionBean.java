/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.EmployeeEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
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
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PromotionEntityNameExistsException;
import util.exception.PromotionEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePromotionException;

/**
 *
 * @author kaiyu
 */
@Stateless
public class PromotionEntitySessionBean implements PromotionEntitySessionBeanLocal {

    @EJB
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean;

    @EJB
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PromotionEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Long createNewPromotionEntity(PromotionEntity newPromotion) throws PromotionEntityNameExistsException, UnknownPersistenceException, InputDataValidationException {

        Set<ConstraintViolation<PromotionEntity>> constraintViolations = validator.validate(newPromotion);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newPromotion);
                em.flush();
                return newPromotion.getPromotionEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new PromotionEntityNameExistsException();
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

    public List<PartChoiceEntity> addPartChoice(Long PromotionId, List<PartChoiceEntity> partChoice) throws PromotionEntityNotFoundException, PartChoiceEntityNotFoundException {
        PromotionEntity managedPromotion = retrievePromotionEntityById(PromotionId);
        for (PartChoiceEntity choice : partChoice) {
            PartChoiceEntity managedChoice = partChoiceEntitySessionBean.retrievePartChoiceEntityByPartChoiceEntityId(choice.getPartChoiceEntityId());
            managedChoice.getPromotionEntities().add(managedPromotion);
            managedPromotion.getPartChoiceEntities().add(managedChoice);
        }
        return managedPromotion.getPartChoiceEntities();
    }

    public List<AccessoryItemEntity> addAccessoryItem(Long PromotionId, List<AccessoryItemEntity> accessoryItem) throws PromotionEntityNotFoundException, AccessoryItemEntityNotFoundException {
        PromotionEntity managedPromotion = retrievePromotionEntityById(PromotionId);
        for (AccessoryItemEntity choice : accessoryItem) {
            AccessoryItemEntity managedChoice = accessoryItemEntitySessionBean.retrieveAccessoryItemById(choice.getAccessoryItemEntityId());
            managedChoice.getPromotionEntities().add(managedPromotion);
            managedPromotion.getAccessoryItemEntities().add(managedChoice);
        }
        return managedPromotion.getAccessoryItemEntities();
    }

    public List<PromotionEntity> retrieveAllPromotions() {

        Query query = em.createQuery("SELECT p FROM PromotionEntity p");
        return query.getResultList();

    }

    public PromotionEntity retrievePromotionEntityById(Long Id) throws PromotionEntityNotFoundException {

        PromotionEntity promotion = em.find(PromotionEntity.class, Id);
        if (promotion != null) {
            return promotion;
        } else {
            throw new PromotionEntityNotFoundException("Promotion with promotion ID: " + Id + " cannot be found!");
        }
    }

    public void updatePromotionEntity(PromotionEntity updatedPromotion) throws PromotionEntityNotFoundException, UpdatePromotionException {

        PromotionEntity promotionToUpdate = retrievePromotionEntityById(updatedPromotion.getPromotionEntityId());

        //remove promo from all related partchoices
        for (PartChoiceEntity p : promotionToUpdate.getPartChoiceEntities()) {
            p.getPromotionEntities().remove(promotionToUpdate);
        }

        for (AccessoryItemEntity a : promotionToUpdate.getAccessoryItemEntities()) {
            a.getPromotionEntities().remove(promotionToUpdate);
        }

        //update partchoices
        promotionToUpdate.getPartChoiceEntities().clear();
        for (PartChoiceEntity p : updatedPromotion.getPartChoiceEntities()) {
            PartChoiceEntity partChoiceToBeUpdated = em.find(PartChoiceEntity.class, p.getPartChoiceEntityId());
            if (partChoiceToBeUpdated == null) {
                throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
            } else {
                promotionToUpdate.getPartChoiceEntities().add(partChoiceToBeUpdated);
                partChoiceToBeUpdated.getPromotionEntities().add(promotionToUpdate);
            }
        }

        //update accessoryItems
        promotionToUpdate.getAccessoryItemEntities().clear();
        for (AccessoryItemEntity a : updatedPromotion.getAccessoryItemEntities()) {
            AccessoryItemEntity aToBeUpdated = em.find(AccessoryItemEntity.class, a.getAccessoryItemEntityId());
            if (aToBeUpdated == null) {
                throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
            } else {
                promotionToUpdate.getAccessoryItemEntities().add(aToBeUpdated);
                aToBeUpdated.getPromotionEntities().add(promotionToUpdate);
            }
        }

        promotionToUpdate.setPromotionName(updatedPromotion.getPromotionName());
        promotionToUpdate.setStartDate(updatedPromotion.getStartDate());
        promotionToUpdate.setEndDate(updatedPromotion.getEndDate());
        promotionToUpdate.setDiscountedPrice(updatedPromotion.getDiscountedPrice());

    }

    public void removePromotionEntity(Long promotionId) throws PromotionEntityNotFoundException {

        PromotionEntity promotionEntity = retrievePromotionEntityById(promotionId);

        //remove promotion from related part choices
        List<PartChoiceEntity> listOfRelatedPartChoices = promotionEntity.getPartChoiceEntities();
        for (PartChoiceEntity c : listOfRelatedPartChoices) {
            c.getPromotionEntities().remove(promotionEntity);
        }

        //remove promotion from related acessory items
        List<AccessoryItemEntity> listOfRelatedAccessoryItems = promotionEntity.getAccessoryItemEntities();
        for (AccessoryItemEntity a : listOfRelatedAccessoryItems) {
            a.getPromotionEntities().remove(promotionEntity);
        }

        em.remove(promotionEntity);

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PromotionEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
