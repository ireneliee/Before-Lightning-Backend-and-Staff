/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.AccessoryItemEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.InputDataValidationException;
import util.exception.QuantityOnHandNotZeroException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author kaiyu
 */
@Stateless
public class AccessoryItemEntitySessionBean implements AccessoryItemEntitySessionBeanLocal {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public AccessoryItemEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewAccessoryItemEntity(AccessoryItemEntity newAccessoryItem, AccessoryEntity accessoryEntity) throws AccessoryEntityNotFoundException, AccessoryItemNameExists, InputDataValidationException, UnknownPersistenceException {
        AccessoryEntity managedAccessoryEntity;
        try {
            managedAccessoryEntity = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(accessoryEntity.getAccessoryEntityId());
        } catch (AccessoryEntityNotFoundException ex) {
            throw new AccessoryEntityNotFoundException("Accessory Entity cannot be found");
        }
        newAccessoryItem.setAccessoryEntity(managedAccessoryEntity);
        Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(newAccessoryItem);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newAccessoryItem);
                em.flush();
                managedAccessoryEntity.getAccessoryItemEntities().add(newAccessoryItem);
                return newAccessoryItem.getAccessoryItemEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new AccessoryItemNameExists(ex.getMessage());
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

    public List<AccessoryItemEntity> retrieveAllAccessoryItemEntities() {

        Query query = em.createQuery("SELECT a FROM AccessoryItemEntity a");
        List<AccessoryItemEntity> list = query.getResultList();
        for (AccessoryItemEntity accItem : list) {
            accItem.getPromotionEntities().size();
            accItem.getAccessoryEntity();
        }
        return list;
    }

    public AccessoryItemEntity retrieveAccessoryItemById(Long id) throws AccessoryItemEntityNotFoundException {

        AccessoryItemEntity item = em.find(AccessoryItemEntity.class, id);
        if (item != null) {
            item.getPromotionEntities().size();
            item.getAccessoryEntity();
            return item;
        } else {
            throw new AccessoryItemEntityNotFoundException("AccessoryId ID " + id + " does not exist!");
        }
    }

    public AccessoryItemEntity retrieveAccessoryItemByName(String accessoryItemName) throws AccessoryItemEntityNotFoundException {

        Query query = em.createQuery("SELECT a FROM AccessoryItemEntity a WHERE a.accessoryItemName=:inputName");
        query.setParameter("inputName", accessoryItemName);

        try {
            AccessoryItemEntity accItem = (AccessoryItemEntity) query.getSingleResult();
            accItem.getPromotionEntities().size();
            accItem.getAccessoryEntity();
            return accItem;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccessoryItemEntityNotFoundException("Accessory item name: " + accessoryItemName + " does not exist!");
        }
    }

    @Override
    public AccessoryItemEntity retrieveAccessoryItemBySkuCode(String accessoryItemSkuCode) throws AccessoryItemEntityNotFoundException {

        Query query = em.createQuery("SELECT a FROM AccessoryItemEntity a WHERE a.skuCode=:inputName");
        query.setParameter("inputName", accessoryItemSkuCode);

        try {
            AccessoryItemEntity accItem = (AccessoryItemEntity) query.getSingleResult();
            accItem.getPromotionEntities().size();
            accItem.getAccessoryEntity();
            return accItem;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccessoryItemEntityNotFoundException("Accessory item name: " + accessoryItemSkuCode + " does not exist!");
        }
    }

    public void updateAccessoryItem(AccessoryItemEntity newAccessoryItem) throws UpdateAccessoryItemEntityException, InputDataValidationException, AccessoryItemEntityNotFoundException {

        if (newAccessoryItem != null && newAccessoryItem.getAccessoryItemEntityId() != null) {
            Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(newAccessoryItem);

            if (constraintViolations.isEmpty()) {
                AccessoryItemEntity accessoryItemToUpdate = retrieveAccessoryItemById(newAccessoryItem.getAccessoryItemEntityId());
                accessoryItemToUpdate.setAccessoryItemName(newAccessoryItem.getAccessoryItemName());
                accessoryItemToUpdate.setReorderQuantity(newAccessoryItem.getReorderQuantity());
                accessoryItemToUpdate.setBrand(newAccessoryItem.getBrand());
                accessoryItemToUpdate.setQuantityOnHand(newAccessoryItem.getQuantityOnHand());
                accessoryItemToUpdate.setPrice(newAccessoryItem.getPrice());
                accessoryItemToUpdate.setDescription(newAccessoryItem.getDescription());
                accessoryItemToUpdate.setImageLink(newAccessoryItem.getImageLink());
                //promotions updated in promotionEntitySessionbean
                //cannot update SKU code

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryItemEntityNotFoundException("AccessoryItem ID not provided for accessoryItemEntity to be updated");
        }

    }

    @Override
    public void deleteAccessoryItemEntity(Long accessoryItemId) throws AccessoryItemEntityNotFoundException, QuantityOnHandNotZeroException {

        AccessoryItemEntity accessoryItemToDelete = retrieveAccessoryItemById(accessoryItemId);

        Query query = em.createQuery("SELECT polie FROM PurchaseOrderLineItemEntity polie WHERE polie.accessoryItemEntity = :inName");
        query.setParameter("inName", accessoryItemToDelete);
        List<PurchaseOrderLineItemEntity> list = query.getResultList();
        if (list.size() > 0 || accessoryItemToDelete.getPromotionEntities().size() > 0) {
            throw new QuantityOnHandNotZeroException("Unable to Delete Accessory Item");
        } else {
            AccessoryEntity acc = accessoryItemToDelete.getAccessoryEntity();
            acc.getAccessoryItemEntities().remove(accessoryItemToDelete);
            em.remove(accessoryItemToDelete);
        }
    }

    @Override
    public void toggleDisableAccessoryItemEntity(AccessoryItemEntity accessoryItemEntity) throws AccessoryItemEntityNotFoundException, UpdateAccessoryItemEntityException, InputDataValidationException {
        if (accessoryItemEntity != null && accessoryItemEntity.getAccessoryItemEntityId() != null) {
            Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(accessoryItemEntity);

            if (constraintViolations.isEmpty()) {
                AccessoryItemEntity accessoryItemEntityToUpdate = retrieveAccessoryItemById(accessoryItemEntity.getAccessoryItemEntityId());

                if (accessoryItemEntityToUpdate.getAccessoryItemEntityId().equals(accessoryItemEntity.getAccessoryItemEntityId())) {
                    accessoryItemEntityToUpdate.setIsDisabled(accessoryItemEntity.getIsDisabled());

                } else {
                    throw new UpdateAccessoryItemEntityException();
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryItemEntityNotFoundException("AccessoryItemEntity ID not provided for accessoryItemEntity to be updated");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
