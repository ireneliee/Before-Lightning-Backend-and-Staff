/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
import entity.EmployeeEntity;
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
import util.exception.AccessoryNameExistsException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

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
                return newAccessoryEntity.getProductTypeEntityId();
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
	
	public AccessoryEntity retrieveAccessoryEntityById(Long id) {
		
		return null;
		
	}
	
	public AccessoryEntity retrieveAccessoryEntityByAccessoryName(String accessoryName) {
		
		return null;
		
	}
	
	
	public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity) {
		
	}
	
	public void deleteAccessoryEntity(Long productTypeId) {
		
	
	}
	
	
	
	 private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AccessoryEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
