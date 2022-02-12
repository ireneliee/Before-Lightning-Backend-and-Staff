/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteEmployeeEntityException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateEmployeeEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Local
public interface EmployeeEntitySessionBeanLocal {

    public Long createNewEmployeeEntity(EmployeeEntity newEmployeeEntity) throws EmployeeEntityUsernameExistException, InputDataValidationException, UnknownPersistenceException;

    public List<EmployeeEntity> retrieveAllEmployeeEntities();

    public EmployeeEntity retrieveEmployeeEntityByEmployeeEntityId(Long employeeId) throws EmployeeEntityNotFoundException;

    public EmployeeEntity retrieveEmployeeEntityByUsername(String username) throws EmployeeEntityNotFoundException;

    public EmployeeEntity employeeEntityLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateEmployeeEntity(EmployeeEntity employeeEntity) throws EmployeeEntityNotFoundException, UpdateEmployeeEntityException, InputDataValidationException;

    public void deleteEmployeeEntity(Long employeeEntityId) throws EmployeeEntityNotFoundException, DeleteEmployeeEntityException;

}
