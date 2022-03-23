/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
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
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnableToDeleteAccessoryEntityException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryEntityException;

/**
 *
 * @author kaiyu
 */
@Stateless
public class AccessoryEntitySessionBean implements AccessoryEntitySessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public AccessoryEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewAccessoryEntity(AccessoryEntity newAccessoryEntity) throws AccessoryNameExistsException, UnknownPersistenceException, InputDataValidationException {

        Set<ConstraintViolation<AccessoryEntity>> constraintViolations = validator.validate(newAccessoryEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newAccessoryEntity);
                em.flush();
                return newAccessoryEntity.getAccessoryEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new AccessoryNameExistsException(ex.getMessage());
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

    public List<AccessoryEntity> retrieveAllAccessoryEntities() {

        Query query = em.createQuery("SELECT a FROM AccessoryEntity a");
        List<AccessoryEntity> list = query.getResultList();
        for (AccessoryEntity acc : list) {
            acc.getAccessoryItemEntities().size();
        }
        return list;
    }

    @Override
    public AccessoryEntity retrieveAccessoryEntityById(Long id) throws AccessoryEntityNotFoundException {
        AccessoryEntity item = em.find(AccessoryEntity.class, id);
        if (item != null) {
            item.getAccessoryItemEntities().size();
            return item;
        } else {
            throw new AccessoryEntityNotFoundException("AccessoryId ID " + id + " does not exist!");
        }

    }

    public AccessoryEntity retrieveAccessoryEntityByAccessoryName(String accessoryName) throws AccessoryEntityNotFoundException {

        Query query = em.createQuery("SELECT a FROM AccessoryEntity a WHERE a.accessoryName=:inputName");
        query.setParameter("inputName", accessoryName);

        try {
            AccessoryEntity item = (AccessoryEntity) query.getSingleResult();
            item.getAccessoryItemEntities().size();
            return item;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccessoryEntityNotFoundException("Accessory item name: " + accessoryName + " does not exist!");
        }

    }

    @Override
    public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity) throws UpdateAccessoryEntityException, InputDataValidationException, AccessoryEntityNotFoundException, AccessoryNameExistsException {
        if (newAccessoryEntity != null && newAccessoryEntity.getAccessoryEntityId() != null) {
            Set<ConstraintViolation<AccessoryEntity>> constraintViolations = validator.validate(newAccessoryEntity);

            if (constraintViolations.isEmpty()) {

                AccessoryEntity accessoryToUpdate = retrieveAccessoryEntityById(newAccessoryEntity.getAccessoryEntityId());
                if (accessoryToUpdate.getAccessoryName().equals(newAccessoryEntity.getAccessoryName())) {
                    //if name unchanged
                    accessoryToUpdate.setDescription(newAccessoryEntity.getDescription());
                } else {
                    //check if name is in use
                    Query query = em.createQuery("SELECT a FROM AccessoryEntity a WHERE a.accessoryName = :inputName");
                    query.setParameter("inputName", newAccessoryEntity.getAccessoryName());
                    List<AccessoryEntity> listName = query.getResultList();

                    if (listName.size() > 0) {
                        throw new AccessoryNameExistsException();
                    } else {
                        accessoryToUpdate.setAccessoryName(newAccessoryEntity.getAccessoryName());
                        accessoryToUpdate.setDescription(newAccessoryEntity.getDescription());
                    }
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryEntityNotFoundException("AccessoryItem ID not provided for accessoryEntity to be updated");
        }

    }

    @Override
    public void toggleDisableAccessoryEntity(Long accessoryEntityId) throws UpdateAccessoryEntityException {
        try {
            AccessoryEntity accessoryToDisable = retrieveAccessoryEntityById(accessoryEntityId);
            accessoryToDisable.setIsDisabled(!accessoryToDisable.getIsDisabled());
        } catch (AccessoryEntityNotFoundException ex) {
            throw new UpdateAccessoryEntityException("Unable To Disable/Enable Part Choice!");
        }
    }

    @Override
    public void deleteAccessoryEntity(Long accessoryEntityId) throws AccessoryEntityNotFoundException, UnableToDeleteAccessoryEntityException {
        AccessoryEntity accessoryToDelete = retrieveAccessoryEntityById(accessoryEntityId);
        if (accessoryToDelete.getAccessoryItemEntities().size() > 0) {
            throw new UnableToDeleteAccessoryEntityException();
        } else {
            em.remove(accessoryToDelete);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AccessoryEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
