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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
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
		for (PartChoiceEntity p : promotionToUpdate.getPartChoices()) {
			p.getPromotions().remove(promotionToUpdate);
		}

		//update partchoices
		promotionToUpdate.getPartChoices().clear();
		for (PartChoiceEntity p : updatedPromotion.getPartChoices()) {
			PartChoiceEntity partChoiceToBeUpdated = em.find(PartChoiceEntity.class, p.getPartChoiceId());
			if (partChoiceToBeUpdated == null) {
				throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
			} else {
				promotionToUpdate.getPartChoices().add(partChoiceToBeUpdated);
				partChoiceToBeUpdated.getPromotions().add(promotionToUpdate);
			}
		}

		//update accessoryItems
		promotionToUpdate.getAccessories().clear();
		for (AccessoryItemEntity a : updatedPromotion.getAccessories()) {
			AccessoryItemEntity aToBeUpdated = em.find(AccessoryItemEntity.class, a.getAccessoryItemId());
			if (aToBeUpdated == null) {
				throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
			} else {
				promotionToUpdate.getAccessories().add(aToBeUpdated);
				aToBeUpdated.getPromotions().add(promotionToUpdate);
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
		List<PartChoiceEntity> listOfRelatedPartChoices = promotionEntity.getPartChoices();
		for (PartChoiceEntity c : listOfRelatedPartChoices) {
			c.getPromotions().remove(promotionEntity);
		}

		//remove promotion from related acessory items
		List<AccessoryItemEntity> listOfRelatedAccessoryItems = promotionEntity.getAccessories();
		for (AccessoryItemEntity a : listOfRelatedAccessoryItems) {
			a.getPromotions().remove(promotionEntity);
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