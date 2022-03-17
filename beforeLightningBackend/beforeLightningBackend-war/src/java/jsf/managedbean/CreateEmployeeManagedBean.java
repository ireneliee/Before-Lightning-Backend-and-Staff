/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "createEmployeeManagedBean")
@ViewScoped
public class CreateEmployeeManagedBean implements Serializable {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @Inject
    private EmployeeManagementManagedBean employeeManagementManagedBean;

    private EmployeeEntity newEmployeeEntity;
    private String newPassword;
    private String newEmployeeAccessRight;

    @PostConstruct
    public void postConstruct() {
        newEmployeeEntity = new EmployeeEntity();
        newEmployeeAccessRight = "";

    }

    public void createNewEmployee(ActionEvent event) throws IOException {
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
            Long employeeId = employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployeeEntity);
            if (employeeId != null) {

                newEmployeeEntity = new EmployeeEntity();
                newEmployeeAccessRight = "";
                newPassword = "";
                FacesContext.getCurrentInstance().addMessage("successEmployeeCreation", new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee is successfully created", null));
                
//ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                //ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dialogCreateEmployee').hide();");
            }

            employeeManagementManagedBean.setListOfEmployeeEntities(employeeEntitySessionBeanLocal.retrieveAllEmployeeEntities());
        } catch (EmployeeEntityUsernameExistException ex) {
            FacesContext.getCurrentInstance().addMessage("createEmployeeMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is currently in use!", null));
            
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage("createEmployeeMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error" + ex.getMessage(), null));
           
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage("createEmployeeMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Create New Employee now", null));
            
        }

    }

    /**
     * Creates a new instance of CreateEmployeeManagedBean
     */
    public CreateEmployeeManagedBean() {
    }

    public EmployeeManagementManagedBean getEmployeeManagementManagedBean() {
        return employeeManagementManagedBean;
    }

    public void setEmployeeManagementManagedBean(EmployeeManagementManagedBean employeeManagementManagedBean) {
        this.employeeManagementManagedBean = employeeManagementManagedBean;
    }

    public EmployeeEntity getNewEmployeeEntity() {
        return newEmployeeEntity;
    }

    public void setNewEmployeeEntity(EmployeeEntity newEmployeeEntity) {
        this.newEmployeeEntity = newEmployeeEntity;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewEmployeeAccessRight() {
        return newEmployeeAccessRight;
    }

    public void setNewEmployeeAccessRight(String newEmployeeAccessRight) {
        this.newEmployeeAccessRight = newEmployeeAccessRight;
    }

    public EmployeeEntitySessionBeanLocal getEmployeeEntitySessionBeanLocal() {
        return employeeEntitySessionBeanLocal;
    }

    public void setEmployeeEntitySessionBeanLocal(EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal) {
        this.employeeEntitySessionBeanLocal = employeeEntitySessionBeanLocal;
    }

}
