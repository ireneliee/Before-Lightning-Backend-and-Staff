/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccessoryAlreadyExistsInPromotionException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceAlreadyExistsInPromotionException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PromotionAlreadyExistsInAccessoryException;
import util.exception.PromotionAlreadyExistsInPartChoiceException;
import util.exception.PromotionDiscountTypeExclusiveOrException;
import util.exception.PromotionEntityNameExistsException;
import util.exception.PromotionEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePromotionException;

/**
 *
 * @author kaiyu
 */
@Local
public interface PromotionEntitySessionBeanLocal {

	public Long createNewPromotionEntity(PromotionEntity newPromotion) throws PromotionEntityNameExistsException, UnknownPersistenceException, InputDataValidationException, PromotionDiscountTypeExclusiveOrException;

	public List<PromotionEntity> retrieveAllPromotions();

	public PromotionEntity retrievePromotionEntityById(Long Id) throws PromotionEntityNotFoundException;

	public List<PartChoiceEntity> retrievePartChoicesWithSpecificPromotion(Long promotionId);

	public List<AccessoryItemEntity> retrieveAccessoryItemsWithSpecificPromotion(Long promotionId);

	public List<PartChoiceEntity> addPartChoicesToPromotion(Long PromotionId, List<PartChoiceEntity> partChoicesToAdd) throws PromotionEntityNotFoundException, PartChoiceEntityNotFoundException, PartChoiceAlreadyExistsInPromotionException;

	public List<AccessoryItemEntity> addAccessoryItemsToPromotion(Long PromotionId, List<AccessoryItemEntity> accessoryItemToAdd) throws PromotionEntityNotFoundException, AccessoryItemEntityNotFoundException, AccessoryAlreadyExistsInPromotionException;

	public Long removePartChoicesFromPromotion(Long promotionId, List<PartChoiceEntity> partChoicesToRemove) throws PromotionEntityNotFoundException, PartChoiceEntityNotFoundException;

	public Long removeAccessoryItemsFromPromotion(Long promotionId, List<AccessoryItemEntity> accessoryItemsToRemove) throws AccessoryItemEntityNotFoundException, PromotionEntityNotFoundException;

	public void updatePromotionEntity(PromotionEntity updatedPromotion) throws PromotionEntityNotFoundException, UpdatePromotionException, InputDataValidationException, PromotionDiscountTypeExclusiveOrException;

	public void removePromotionEntity(Long promotionId) throws PromotionEntityNotFoundException;

}
