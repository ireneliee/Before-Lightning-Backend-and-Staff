/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "employeeManagementManagedBean")
@ViewScoped
public class EmployeeManagementManagedBean implements Serializable {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    private List<EmployeeEntity> listOfEmployeeEntities;
    private List<EmployeeEntity> filteredListOfEmployeeEntities;

    private EmployeeEntity newEmployeeEntity;
    private String newPassword;
    private String newEmployeeAccessRight;

    @Inject
    private ViewEmployeeManagedBean viewEmployeeManagedBean;

    /**
     * Creates a new instance of EmployeeManagementManagedBean
     */
    public EmployeeManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setListOfEmployeeEntities(employeeEntitySessionBeanLocal.retrieveAllEmployeeEntities());
        newEmployeeEntity = new EmployeeEntity();
        newEmployeeAccessRight = "";

    }

    public void createNewEmployee(ActionEvent event) {
        if (newEmployeeAccessRight.equals("ADMIN")) {
            newEmployeeEntity.setEmployeeAccessRight(EmployeeAccessRightEnum.ADMIN);
        } else if (newEmployeeAccessRight.equals("SALES")) {
            newEmployeeEntity.setEmployeeAccessRight(EmployeeAccessRightEnum.SALES);

        } else if (newEmployeeAccessRight.equals("OPERATION")) {
            newEmployeeEntity.setEmployeeAccessRight(EmployeeAccessRightEnum.OPERATION);

        } else if (newEmployeeAccessRight.equals("PRODUCT")) {
            newEmployeeEntity.setEmployeeAccessRight(EmployeeAccessRightEnum.PRODUCT);
        }
        newEmployeeEntity.setPassword(newPassword);

        try {
            employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployeeEntity);
        } catch (EmployeeEntityUsernameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is currently in use!", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error" + ex.getMessage(), null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Create New Employee now", null));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee successfully created", null));
        newEmployeeEntity = new EmployeeEntity();
        newEmployeeAccessRight = "";
        newPassword = "";
        setListOfEmployeeEntities(employeeEntitySessionBeanLocal.retrieveAllEmployeeEntities());
    }

    /**
     * @return the listOfEmployeeEntities
     */
    public List<EmployeeEntity> getListOfEmployeeEntities() {
        return listOfEmployeeEntities;
    }

    /**
     * @param listOfEmployeeEntities the listOfEmployeeEntities to set
     */
    public void setListOfEmployeeEntities(List<EmployeeEntity> listOfEmployeeEntities) {
        this.listOfEmployeeEntities = listOfEmployeeEntities;
    }

    /**
     * @return the filteredListOfEmployeeEntities
     */
    public List<EmployeeEntity> getFilteredListOfEmployeeEntities() {
        return filteredListOfEmployeeEntities;
    }

    /**
     * @param filteredListOfEmployeeEntities the filteredListOfEmployeeEntities
     * to set
     */
    public void setFilteredListOfEmployeeEntities(List<EmployeeEntity> filteredListOfEmployeeEntities) {
        this.filteredListOfEmployeeEntities = filteredListOfEmployeeEntities;
    }

    public EmployeeEntitySessionBeanLocal getEmployeeEntitySessionBeanLocal() {
        return employeeEntitySessionBeanLocal;
    }

    public void setEmployeeEntitySessionBeanLocal(EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal) {
        this.employeeEntitySessionBeanLocal = employeeEntitySessionBeanLocal;
    }

    public ViewEmployeeManagedBean getViewEmployeeManagedBean() {
        return viewEmployeeManagedBean;
    }

    public void setViewEmployeeManagedBean(ViewEmployeeManagedBean viewEmployeeManagedBean) {
        this.viewEmployeeManagedBean = viewEmployeeManagedBean;
    }

    /**
     * @return the newEmployeeEntity
     */
    public EmployeeEntity getNewEmployeeEntity() {
        return newEmployeeEntity;
    }

    /**
     * @param newEmployeeEntity the newEmployeeEntity to set
     */
    public void setNewEmployeeEntity(EmployeeEntity newEmployeeEntity) {
        this.newEmployeeEntity = newEmployeeEntity;
    }

    public String getNewEmployeeAccessRight() {
        return newEmployeeAccessRight;
    }

    public void setNewEmployeeAccessRight(String newEmployeeAccessRight) {
        this.newEmployeeAccessRight = newEmployeeAccessRight;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
