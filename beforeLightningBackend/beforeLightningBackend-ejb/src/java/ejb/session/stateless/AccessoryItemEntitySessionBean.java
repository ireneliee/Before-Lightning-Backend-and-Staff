/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.AccessoryItemEntity;
import java.util.List;
import java.util.Set;
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
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.InputDataValidationException;
import util.exception.QuantityOnHandNotZeroException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryItemEntityException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author kaiyu
 */
@Stateless
public class AccessoryItemEntitySessionBean implements AccessoryItemEntitySessionBeanLocal {

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
    public Long createNewAccessoryItemEntity(AccessoryItemEntity newAccessoryItem) throws AccessoryItemNameExists, InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(newAccessoryItem);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newAccessoryItem);
                em.flush();
                return newAccessoryItem.getAccessoryItemEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new AccessoryItemNameExists();
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
        return query.getResultList();

    }

    public AccessoryItemEntity retrieveAccessoryItemById(Long id) throws AccessoryItemEntityNotFoundException {

        AccessoryItemEntity item = em.find(AccessoryItemEntity.class, id);
        if (item != null) {
            return item;
        } else {
            throw new AccessoryItemEntityNotFoundException("AccessoryId ID " + id + " does not exist!");
        }
    }

    public AccessoryItemEntity retrieveAccessoryItemByName(String accessoryItemName) throws AccessoryItemEntityNotFoundException {

        Query query = em.createQuery("SELECT a FROM AccessoryItemEntity a WHERE a.accessoryItemName=:inputName");
        query.setParameter("inputName", accessoryItemName);

        try {
            return (AccessoryItemEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccessoryItemEntityNotFoundException("Accessory item name: " + accessoryItemName + " does not exist!");
        }

    }

    public void updateAccessoryItem(AccessoryItemEntity newAccessoryItem) throws UpdateAccessoryItemEntityException, InputDataValidationException, AccessoryItemEntityNotFoundException {

        if (newAccessoryItem != null && newAccessoryItem.getAccessoryItemEntityId()!= null) {
            Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(newAccessoryItem);

            if (constraintViolations.isEmpty()) {
                AccessoryItemEntity accessoryItemToUpdate = retrieveAccessoryItemById(newAccessoryItem.getAccessoryItemEntityId());
                accessoryItemToUpdate.setAccessoryItemName(newAccessoryItem.getAccessoryItemName());
                accessoryItemToUpdate.setQuantityOnHand(newAccessoryItem.getQuantityOnHand());
                accessoryItemToUpdate.setPrice(newAccessoryItem.getPrice());
                //promotions updated in promotionEntitySessionbean

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryItemEntityNotFoundException("AccessoryItem ID not provided for accessoryItemEntity to be updated");
        }

    }

    public void deleteAccessoryItemEntity(Long accessoryItemId) throws AccessoryItemEntityNotFoundException, QuantityOnHandNotZeroException {

        AccessoryItemEntity accessoryItemToDelete = retrieveAccessoryItemById(accessoryItemId);
        if (accessoryItemToDelete.getQuantityOnHand() > 0) {
            throw new QuantityOnHandNotZeroException("Quantity on hand of: " + accessoryItemToDelete.getAccessoryItemName() + " is not zero! Cannot delete item from database");

        } else {
            em.remove(accessoryItemToDelete);
        }

    }
    
    @Override
    public void toggleDisableAccessoryItemEntity(AccessoryItemEntity accessoryItemEntity) throws AccessoryItemEntityNotFoundException, UpdateAccessoryItemEntityException, InputDataValidationException {
        if (accessoryItemEntity != null && accessoryItemEntity.getAccessoryItemEntityId() != null) {
            Set<ConstraintViolation<AccessoryItemEntity>> constraintViolations = validator.validate(accessoryItemEntity);

            if (constraintViolations.isEmpty()) {
                AccessoryItemEntity accessoryItemEntityToUpdate =retrieveAccessoryItemById(accessoryItemEntity.getAccessoryItemEntityId());

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
