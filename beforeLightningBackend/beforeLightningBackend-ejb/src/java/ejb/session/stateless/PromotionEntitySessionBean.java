/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import util.exception.AccessoryAlreadyExistsInPromotionException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceAlreadyExistsInPromotionException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PromotionDiscountTypeExclusiveOrException;
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

	@Override
	public Long createNewPromotionEntity(PromotionEntity newPromotion) throws PromotionEntityNameExistsException, UnknownPersistenceException, InputDataValidationException, PromotionDiscountTypeExclusiveOrException {

		Set<ConstraintViolation<PromotionEntity>> constraintViolations = validator.validate(newPromotion);

		if (newPromotion == null) {
			System.out.println("passed in empty promo bro");
			return new Long(0);
		}

		if (constraintViolations.isEmpty()) {
			try {
				//if both are 0 or if both have non-zero values, throw error
				if (!checkDiscountValidity(newPromotion)) {
					System.out.println("throw error cos discount fields not valid");
					throw new PromotionDiscountTypeExclusiveOrException();
				} else {
					em.persist(newPromotion);
					em.flush();
					System.out.println("promoentitysessionbean :: createNewPromo :: just persisted, returning promoId");
					return newPromotion.getPromotionEntityId();
				}
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

	@Override
	public List<PromotionEntity> retrieveAllPromotions() {

		Query query = em.createQuery("SELECT p FROM PromotionEntity p");
		List<PromotionEntity> list = query.getResultList();
		System.out.println("promosessionbean :: retrieveAllPromotions()");
		for (PromotionEntity p : list) {
			p.getAccessoryItemEntities().size();
			p.getPartChoiceEntities().size();
		}
		return list;

	}

	@Override
	public List<PromotionEntity> retrieveAllOngoingPromotion() {
		LocalDate currentDate = LocalDate.now();
		String queryInString = "SELECT p FROM PromotionEntity p WHERE p.startDate <= :iTodayDate AND p.endDate >= :iTodayDate";
		Query query = em.createQuery(queryInString);
		query.setParameter("iTodayDate", currentDate);

		List<PromotionEntity> list = query.getResultList();
		System.out.println("promosessionbean :: retrieveAllPromotions()");
		for (PromotionEntity p : list) {
			p.getAccessoryItemEntities().size();
			p.getPartChoiceEntities().size();
		}
		return list;

	}

	@Override
	public PromotionEntity retrievePromotionEntityById(Long Id) throws PromotionEntityNotFoundException {

		PromotionEntity promotion = em.find(PromotionEntity.class, Id);
		if (promotion != null) {
			return promotion;
		} else {
			throw new PromotionEntityNotFoundException("Promotion with promotion ID: " + Id + " cannot be found!");
		}
	}

	//to use for viewing promotional part choices & removing part choice from promo
	//should move this to partchoice sessionbean
	@Override
	public List<PartChoiceEntity> retrievePartChoicesWithSpecificPromotion(Long promotionId) {

		List<PartChoiceEntity> listOfPartChoiceWithSpecificPromotion = new ArrayList<>();

		Query query = em.createQuery("SELECT p FROM PartChoiceEntity p, IN (p.promotionEntities) e");
		List<PartChoiceEntity> listOfPromotionalPartChoices = query.getResultList();

		//find part choices with a particular promotion
		for (PartChoiceEntity partChoice : listOfPromotionalPartChoices) {
			List<PromotionEntity> listOfPromotions = partChoice.getPromotionEntities();
			for (PromotionEntity promo : listOfPromotions) {
				if (promo.getPromotionEntityId().equals(promotionId)) {
					//add that part choice into list
					listOfPartChoiceWithSpecificPromotion.add(partChoice);
				}
			}
		}
		return listOfPartChoiceWithSpecificPromotion;
	}

	//to use for viewing promotional accessories & removing accessory from promo
	//should move this to accessoryitems sessionbean
	@Override
	public List<AccessoryItemEntity> retrieveAccessoryItemsWithSpecificPromotion(Long promotionId) {

		List<AccessoryItemEntity> listOfAccessoryItemsWithSpecificPromotion = new ArrayList<>();
		Query query = em.createQuery("SELECT a FROM AccessoryItemEntity a, IN (a.promotionEntities) e");
		List<AccessoryItemEntity> listOfPromotionalAccessoryItems = query.getResultList();

		//find accessories with a particular promotion
		for (AccessoryItemEntity accessory : listOfPromotionalAccessoryItems) {
			List<PromotionEntity> listOfPromotions = accessory.getPromotionEntities();
			for (PromotionEntity promo : listOfPromotions) {
				if (promo.getPromotionEntityId().equals(promotionId)) {
					//add that accessory into list
					listOfAccessoryItemsWithSpecificPromotion.add(accessory);
				}
			}
		}
		return listOfAccessoryItemsWithSpecificPromotion;
	}

	@Override
	public List<PartChoiceEntity> addPartChoicesToPromotion(Long PromotionId, List<PartChoiceEntity> partChoicesToAdd) throws PromotionEntityNotFoundException, PartChoiceEntityNotFoundException, PartChoiceAlreadyExistsInPromotionException {

		List<PartChoiceEntity> currentListPartChoiceWithParticularPromotion = retrievePartChoicesWithSpecificPromotion(PromotionId);
		PromotionEntity promotion = em.find(PromotionEntity.class, PromotionId);
		List<PartChoiceEntity> updatedPartChoiceList = promotion.getPartChoiceEntities();

		for (PartChoiceEntity p : partChoicesToAdd) {
			updatedPartChoiceList.add(p);
			PartChoiceEntity managedPartChoice = em.find(PartChoiceEntity.class, p.getPartChoiceEntityId());
			managedPartChoice.getPromotionEntities().add(promotion);
		}
		promotion.setPartChoiceEntities(updatedPartChoiceList);

		return updatedPartChoiceList;
	}

	@Override
	public List<AccessoryItemEntity> addAccessoryItemsToPromotion(Long PromotionId, List<AccessoryItemEntity> accessoryItemToAdd) throws PromotionEntityNotFoundException, AccessoryItemEntityNotFoundException, AccessoryAlreadyExistsInPromotionException {

		System.out.println("promosessionbean :: called add accessoryitem for promo: " + PromotionId);
		PromotionEntity promotion = em.find(PromotionEntity.class, PromotionId);
		List<AccessoryItemEntity> updatedAccessoryItemList = promotion.getAccessoryItemEntities();

		for (AccessoryItemEntity a : accessoryItemToAdd) {
			updatedAccessoryItemList.add(a);
			AccessoryItemEntity managedAccessory = em.find(AccessoryItemEntity.class, a.getAccessoryItemEntityId());
			managedAccessory.getPromotionEntities().add(promotion);
		}
		promotion.setAccessoryItemEntities(updatedAccessoryItemList);

		return updatedAccessoryItemList;
	}

	@Override
	public Long removePartChoicesFromPromotion(Long promotionId, List<PartChoiceEntity> partChoicesToRemove) throws PromotionEntityNotFoundException, PartChoiceEntityNotFoundException {

		PromotionEntity promotion = em.find(PromotionEntity.class, promotionId);

		//get list of promo part choices 
		List<PartChoiceEntity> listOfPartChoice = promotion.getPartChoiceEntities();

		for (PartChoiceEntity p : partChoicesToRemove) {
			listOfPartChoice.remove(p);
			PartChoiceEntity managedPartChoice = partChoiceEntitySessionBean.retrievePartChoiceEntityByPartChoiceEntityId(p.getPartChoiceEntityId());
			managedPartChoice.getPromotionEntities().remove(promotion);
		}

		promotion.setPartChoiceEntities(listOfPartChoice);
		return promotionId;

	}

	@Override
	public Long removeAccessoryItemsFromPromotion(Long promotionId, List<AccessoryItemEntity> accessoryItemsToRemove) throws AccessoryItemEntityNotFoundException, PromotionEntityNotFoundException {

		PromotionEntity promotion = em.find(PromotionEntity.class, promotionId);

		//get list of promo part choices 
		List<AccessoryItemEntity> listOfAccessories = promotion.getAccessoryItemEntities();

		System.out.println("promoEntitySessionBean :: removeAccessoryItemsFromPromo :: \n");
		for (AccessoryItemEntity p : accessoryItemsToRemove) {
			System.out.println("currently removing accessory: " + p + " from promo: " + promotionId);
			listOfAccessories.remove(p);
			AccessoryItemEntity managedAccessory = accessoryItemEntitySessionBean.retrieveAccessoryItemById(p.getAccessoryItemEntityId());
			managedAccessory.getPromotionEntities().remove(promotion);
			System.out.println("currently removing promo: " + promotionId);

		}

		promotion.setAccessoryItemEntities(listOfAccessories);
		return promotionId;

	}

	@Override
	public void updatePromotionEntity(PromotionEntity updatedPromotion) throws PromotionEntityNotFoundException, UpdatePromotionException, InputDataValidationException, PromotionDiscountTypeExclusiveOrException {

		if (updatedPromotion != null && updatedPromotion.getPromotionEntityId() != null) {
			Set<ConstraintViolation<PromotionEntity>> constraintViolations = validator.validate(updatedPromotion);

			if (constraintViolations.isEmpty()) {
				PromotionEntity promotionToUpdate = retrievePromotionEntityById(updatedPromotion.getPromotionEntityId());

				if (promotionToUpdate.getPromotionEntityId().equals(updatedPromotion.getPromotionEntityId())) {
					System.out.println("promoSessionBean update method :: \n"
							+ "discount: " + updatedPromotion.getDiscount() + "\n"
							+ "discountedPrice: " + updatedPromotion.getDiscountedPrice());

					if (!checkDiscountValidity(updatedPromotion)) {
						System.out.println("shits wacc not supposed to updated...");
						throw new PromotionDiscountTypeExclusiveOrException();
					} else {
						promotionToUpdate.setPromotionName(updatedPromotion.getPromotionName());
						promotionToUpdate.setStartDate(updatedPromotion.getStartDate());
						promotionToUpdate.setEndDate(updatedPromotion.getEndDate());
						promotionToUpdate.setDiscount(updatedPromotion.getDiscount());
						promotionToUpdate.setDiscountedPrice(updatedPromotion.getDiscountedPrice());
						em.flush();
					}

				} else {
					throw new UpdatePromotionException("Promo ID of promotionEntity record to be updated does not match the existing record!");
				}
			} else {
				throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
			}
		} else {
			throw new PromotionEntityNotFoundException("PromotionEntity ID not provided for promotionEntity to be updated!");
		}
	}

//		//remove promo from all related partchoices
//		for (PartChoiceEntity p : promotionToUpdate.getPartChoiceEntities()) {
//			p.getPromotionEntities().remove(promotionToUpdate);
//		}
//
//		for (AccessoryItemEntity a : promotionToUpdate.getAccessoryItemEntities()) {
//			a.getPromotionEntities().remove(promotionToUpdate);
//		}
//
//		//update partchoices
//		promotionToUpdate.getPartChoiceEntities().clear();
//		for (PartChoiceEntity p : updatedPromotion.getPartChoiceEntities()) {
//			PartChoiceEntity partChoiceToBeUpdated = em.find(PartChoiceEntity.class, p.getPartChoiceEntityId());
//			if (partChoiceToBeUpdated == null) {
//				throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
//			} else {
//				promotionToUpdate.getPartChoiceEntities().add(partChoiceToBeUpdated);
//				partChoiceToBeUpdated.getPromotionEntities().add(promotionToUpdate);
//			}
//		}
//
//		//update accessoryItems
//		promotionToUpdate.getAccessoryItemEntities().clear();
//		for (AccessoryItemEntity a : updatedPromotion.getAccessoryItemEntities()) {
//			AccessoryItemEntity aToBeUpdated = em.find(AccessoryItemEntity.class, a.getAccessoryItemEntityId());
//			if (aToBeUpdated == null) {
//				throw new UpdatePromotionException("An error has occured while updating the part: part choice cannot be found. ");
//			} else {
//				promotionToUpdate.getAccessoryItemEntities().add(aToBeUpdated);
//				aToBeUpdated.getPromotionEntities().add(promotionToUpdate);
//			}
//		}
	@Override
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

	//returns if promo discounts are valid or not
	public boolean checkDiscountValidity(PromotionEntity promotion) {

		boolean zeroDiscount = (promotion.getDiscount().equals(0.0));
		boolean zeroDiscountedPrice = promotion.getDiscountedPrice().compareTo(BigDecimal.ZERO) == 0;

		System.out.println("zeroDiscount: " + zeroDiscount + " zeroDiscountedPrice: " + zeroDiscountedPrice);
		//if both are 0 or if both have non-zero values, return false
		if (!((zeroDiscount && !zeroDiscountedPrice) || (!zeroDiscount && zeroDiscountedPrice))) {
			System.out.println("returned false cos not valid");
			return false;
		}
		System.out.println("returned true cos valid");
		return true;
	}

	private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PromotionEntity>> constraintViolations) {
		String msg = "Input data validation error!:";

		for (ConstraintViolation constraintViolation : constraintViolations) {
			msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
		}

		return msg;
	}
}
