/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
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
import util.exception.DeleteEmployeeEntityException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateEmployeeEntityException;
import util.security.CryptographicHelper;

/**
 *
 * @author Koh Wen Jie
 */
@Stateless
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public EmployeeEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Long createNewEmployeeEntity(EmployeeEntity newEmployeeEntity) throws EmployeeEntityUsernameExistException, InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<EmployeeEntity>> constraintViolations = validator.validate(newEmployeeEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newEmployeeEntity);
                em.flush();
                return newEmployeeEntity.getUserEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        System.out.println("====================");
                        System.out.println(ex.getCause());
                        throw new EmployeeEntityUsernameExistException();
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
    public List<EmployeeEntity> retrieveAllEmployeeEntities() {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
        return query.getResultList();
    }

    @Override
    public EmployeeEntity retrieveEmployeeEntityByEmployeeEntityId(Long employeeId) throws EmployeeEntityNotFoundException {
        EmployeeEntity employeeEntity = em.find(EmployeeEntity.class, employeeId);

        if (employeeEntity != null) {
            return employeeEntity;
        } else {
            throw new EmployeeEntityNotFoundException("Employee ID " + employeeId + " does not exist!");
        }
    }

    @Override
    public EmployeeEntity retrieveEmployeeEntityByUsername(String username) throws EmployeeEntityNotFoundException {
        Query query = em.createQuery("SELECT s FROM EmployeeEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (EmployeeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeEntityNotFoundException("Employee Username " + username + " does not exist!");
        }
    }

    @Override
    public EmployeeEntity employeeEntityLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            EmployeeEntity employeeEntity = retrieveEmployeeEntityByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + employeeEntity.getSalt()));

            if (employeeEntity.getPassword().equals(passwordHash)) {
                return employeeEntity;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (EmployeeEntityNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateEmployeeEntity(EmployeeEntity employeeEntity) throws EmployeeEntityNotFoundException, UpdateEmployeeEntityException, InputDataValidationException {
        if (employeeEntity != null && employeeEntity.getUserEntityId() != null) {
            Set<ConstraintViolation<EmployeeEntity>> constraintViolations = validator.validate(employeeEntity);

            if (constraintViolations.isEmpty()) {
                EmployeeEntity employeeEntityToUpdate = retrieveEmployeeEntityByEmployeeEntityId(employeeEntity.getUserEntityId());

                if (employeeEntityToUpdate.getUsername().equals(employeeEntity.getUsername())) {
                    employeeEntityToUpdate.setFirstname(employeeEntity.getFirstname());
                    employeeEntityToUpdate.setLastname(employeeEntity.getLastname());
                    employeeEntityToUpdate.setContact(employeeEntity.getContact());
                    employeeEntityToUpdate.setEmail(employeeEntity.getEmail());
                    employeeEntityToUpdate.setImageLink(employeeEntity.getImageLink());

                    employeeEntityToUpdate.setEmployeeAccessRight(employeeEntity.getEmployeeAccessRight());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                } else {
                    throw new UpdateEmployeeEntityException("Username of employeeEntity record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new EmployeeEntityNotFoundException("EmployeeEntity ID not provided for employeeEntity to be updated");
        }
    }

    @Override
    public void deleteEmployeeEntity(Long employeeEntityId) throws EmployeeEntityNotFoundException, DeleteEmployeeEntityException {
        EmployeeEntity employeeEntityToRemove = retrieveEmployeeEntityByEmployeeEntityId(employeeEntityId);
        //need to check for any conditions before removing employee?
        em.remove(employeeEntityToRemove);

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<EmployeeEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
