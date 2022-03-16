/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import java.util.List;
import java.util.Set;
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
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.QuantityOnHandNotZeroException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryEntityException;
import util.exception.UpdateAccessoryItemEntityException;

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
                        throw new AccessoryNameExistsException();
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
        return query.getResultList();

    }

    @Override
    public AccessoryEntity retrieveAccessoryEntityById(Long id) throws AccessoryEntityNotFoundException {
        AccessoryEntity item = em.find(AccessoryEntity.class, id);
        if (item != null) {
            return item;
        } else {
            throw new AccessoryEntityNotFoundException("AccessoryId ID " + id + " does not exist!");
        }



    }

    public AccessoryEntity retrieveAccessoryEntityByAccessoryName(String accessoryName) throws AccessoryEntityNotFoundException{

               Query query = em.createQuery("SELECT a FROM AccessoryEntity a WHERE a.accessoryName=:inputName");
        query.setParameter("inputName", accessoryName);

        try {
            return (AccessoryEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AccessoryEntityNotFoundException("Accessory item name: " + accessoryName + " does not exist!");
        }

    }

    public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity) throws UpdateAccessoryEntityException, InputDataValidationException, AccessoryEntityNotFoundException{
                if (newAccessoryEntity != null && newAccessoryEntity.getAccessoryEntityId()!= null) {
            Set<ConstraintViolation<AccessoryEntity>> constraintViolations = validator.validate(newAccessoryEntity);

            if (constraintViolations.isEmpty()) {
                AccessoryEntity accessoryToUpdate = retrieveAccessoryEntityById(newAccessoryEntity.getAccessoryEntityId());
                accessoryToUpdate.setAccessoryName(newAccessoryEntity.getAccessoryName());
                accessoryToUpdate.setDescription(newAccessoryEntity.getDescription());
                //promotions updated in promotionEntitySessionbean

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryEntityNotFoundException("AccessoryItem ID not provided for accessoryEntity to be updated");
        }

    }

    @Override
    public void toggleDisableAccessoryEntity(AccessoryEntity accessoryEntity) throws AccessoryEntityNotFoundException, UpdateAccessoryEntityException, InputDataValidationException {
        if (accessoryEntity != null && accessoryEntity.getAccessoryEntityId() != null) {
            Set<ConstraintViolation<AccessoryEntity>> constraintViolations = validator.validate(accessoryEntity);

            if (constraintViolations.isEmpty()) {
                AccessoryEntity accessoryEntityToUpdate = retrieveAccessoryEntityById(accessoryEntity.getAccessoryEntityId());

                if (accessoryEntityToUpdate.getAccessoryEntityId().equals(accessoryEntity.getAccessoryEntityId())) {
                    accessoryEntityToUpdate.setIsDisabled(accessoryEntity.getIsDisabled());
                    
                } else {
                    throw new UpdateAccessoryEntityException();
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AccessoryEntityNotFoundException("AccessoryEntity ID not provided for accessoryEntity to be updated");
        }
    }
    
    public void deleteAccessoryEntity(Long accessoryEntityId) throws AccessoryEntityNotFoundException{
        AccessoryEntity accessoryToDelete = retrieveAccessoryEntityById(accessoryEntityId);

        em.remove(accessoryToDelete);
        


    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AccessoryEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
