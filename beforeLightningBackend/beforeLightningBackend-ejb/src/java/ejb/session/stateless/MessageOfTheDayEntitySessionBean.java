/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.EmployeeEntity;
import entity.MessageOfTheDayEntity;
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
import util.exception.EmployeeEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Stateless
public class MessageOfTheDayEntitySessionBean implements MessageOfTheDayEntitySessionBeanLocal {

	@EJB
	private EmployeeEntitySessionBeanLocal employeeEntitySessionBean;

	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")
	//need to change persistence context name!!
	@PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
	private EntityManager entityManager;

	private final ValidatorFactory validatorFactory;
	private final Validator validator;

	public MessageOfTheDayEntitySessionBean() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Override
	public MessageOfTheDayEntity createNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDayEntity, EmployeeEntity employee) throws InputDataValidationException, UnknownPersistenceException, EmployeeEntityNotFoundException {
		Set<ConstraintViolation<MessageOfTheDayEntity>> constraintViolations = validator.validate(newMessageOfTheDayEntity);

		EmployeeEntity managedEmployee = employeeEntitySessionBean.retrieveEmployeeEntityByEmployeeEntityId(employee.getUserEntityId());
		if (constraintViolations.isEmpty()) {
			try {
				newMessageOfTheDayEntity.setEmployeeAuthor(employee);
				managedEmployee.getListOfMessagesOfTheDay().add(newMessageOfTheDayEntity);
				entityManager.persist(newMessageOfTheDayEntity);
				entityManager.flush();
				return newMessageOfTheDayEntity;
			} catch (PersistenceException ex) {
				if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
					throw new UnknownPersistenceException(ex.getMessage());
				} else {
					throw new UnknownPersistenceException(ex.getMessage());
				}
			}
		} else {
			throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
		}

	}

	private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MessageOfTheDayEntity>> constraintViolations) {
		String msg = "Input data validation error!:";

		for (ConstraintViolation constraintViolation : constraintViolations) {
			msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
		}

		return msg;
	}

	@Override
	public List<MessageOfTheDayEntity> retrieveAllMessagesOfTheDay() {
		Query query = entityManager.createQuery("SELECT motd FROM MessageOfTheDayEntity motd ORDER BY motd.motdId ASC");

		return query.getResultList();
	}
}
