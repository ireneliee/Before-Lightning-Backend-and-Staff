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
import util.exception.UpdateEmployeeEntityException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateEmployeeManagedBean")
@ViewScoped
public class UpdateEmployeeManagedBean implements Serializable {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @Inject
    private EmployeeManagementManagedBean employeeManagementManagedBean;

    private EmployeeEntity employeeEntityToUpdate;
    private String updatedFirstname;
    private String updatedLastname;
    private String updatedEmail;
    private String updatedContact;
    private String updatedUsername;
    private String updatedPassword;
    private String updatedAccessRight;

    public UpdateEmployeeManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        initialiseState();
    }

    public void initialiseState() {
        employeeEntityToUpdate = new EmployeeEntity();
        updatedFirstname = "";
        updatedLastname = "";
        updatedEmail = "";
        updatedContact = "";
        updatedPassword = "";
        updatedAccessRight = "";
    }

    //CANNOT CHANGE USERNAME
    public void updateEmployee(ActionEvent event) {

        try {
            if (!updatedFirstname.equals("")) {
                employeeEntityToUpdate.setFirstname(updatedFirstname);
            }
            if (!updatedLastname.equals("")) {
                employeeEntityToUpdate.setLastname(updatedLastname);
            }
            if (!updatedEmail.equals("")) {
                employeeEntityToUpdate.setEmail(updatedEmail);
            }
            if (!updatedContact.equals("")) {
                employeeEntityToUpdate.setContact(updatedContact);
            }
            if (!updatedPassword.equals("")) {
                employeeEntityToUpdate.setPassword(updatedPassword);
            }

            if (!updatedAccessRight.equals("NO CHANGE")) {
                if (updatedAccessRight.equals("ADMIN")) {
                    employeeEntityToUpdate.setEmployeeAccessRight(EmployeeAccessRightEnum.ADMIN);
                } else if (updatedAccessRight.equals("SALES")) {
                    employeeEntityToUpdate.setEmployeeAccessRight(EmployeeAccessRightEnum.SALES);

                } else if (updatedAccessRight.equals("OPERATION")) {
                    employeeEntityToUpdate.setEmployeeAccessRight(EmployeeAccessRightEnum.OPERATION);

                } else if (updatedAccessRight.equals("PRODUCT")) {
                    employeeEntityToUpdate.setEmployeeAccessRight(EmployeeAccessRightEnum.PRODUCT);
                }
            }
            employeeEntitySessionBeanLocal.updateEmployeeEntity(employeeEntityToUpdate);
            employeeManagementManagedBean.setListOfEmployeeEntities(employeeEntitySessionBeanLocal.retrieveAllEmployeeEntities());

            if (employeeManagementManagedBean.getFilteredListOfEmployeeEntities() != null) {
                List<EmployeeEntity> newFilteredListOfEmployeeEntities = employeeManagementManagedBean.getFilteredListOfEmployeeEntities();
                for (EmployeeEntity emp : newFilteredListOfEmployeeEntities) {
                    if (emp.getUserEntityId().equals(employeeEntityToUpdate.getUserEntityId())) {
                        newFilteredListOfEmployeeEntities.set(newFilteredListOfEmployeeEntities.indexOf(emp), employeeEntityToUpdate);
                        break;
                    }
                }
                employeeManagementManagedBean.setFilteredListOfEmployeeEntities(newFilteredListOfEmployeeEntities);
            }

        } catch (EmployeeEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Employee Not Found Error: " + ex.getMessage(), null));
        } catch (UpdateEmployeeEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to Update Employee: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error: " + ex.getMessage(), null));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee has been Updated Successfully", null));
        EmployeeEntity currEmployeeEntity = (EmployeeEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentEmployeeEntity");

        if (currEmployeeEntity.getUserEntityId().equals(employeeEntityToUpdate.getUserEntityId())) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentEmployeeEntity", employeeEntitySessionBeanLocal.retrieveEmployeeEntityByEmployeeEntityId(currEmployeeEntity.getUserEntityId()));
            } catch (EmployeeEntityNotFoundException ex) {
                Logger.getLogger(UpdateEmployeeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        initialiseState();
    }

    /**
     * @return the employeeEntityToUpdate
     */
    public EmployeeEntity getEmployeeEntityToUpdate() {
        return employeeEntityToUpdate;
    }

    /**
     * @param employeeEntityToUpdate the employeeEntityToUpdate to set
     */
    public void setEmployeeEntityToUpdate(EmployeeEntity employeeEntityToUpdate) {
        this.employeeEntityToUpdate = employeeEntityToUpdate;
    }

    public EmployeeManagementManagedBean getEmployeeManagementManagedBean() {
        return employeeManagementManagedBean;
    }

    public void setEmployeeManagementManagedBean(EmployeeManagementManagedBean employeeManagementManagedBean) {
        this.employeeManagementManagedBean = employeeManagementManagedBean;
    }

    public EmployeeEntitySessionBeanLocal getEmployeeEntitySessionBeanLocal() {
        return employeeEntitySessionBeanLocal;
    }

    public void setEmployeeEntitySessionBeanLocal(EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal) {
        this.employeeEntitySessionBeanLocal = employeeEntitySessionBeanLocal;
    }

    public String getUpdatedAccessRight() {
        return updatedAccessRight;
    }

    public void setUpdatedAccessRight(String updatedAccessRight) {
        this.updatedAccessRight = updatedAccessRight;
    }

    public String getUpdatedFirstname() {
        return updatedFirstname;
    }

    public void setUpdatedFirstname(String updatedFirstname) {
        this.updatedFirstname = updatedFirstname;
    }

    public String getUpdatedLastname() {
        return updatedLastname;
    }

    public void setUpdatedLastname(String updatedLastname) {
        this.updatedLastname = updatedLastname;
    }

    public String getUpdatedEmail() {
        return updatedEmail;
    }

    public void setUpdatedEmail(String updatedEmail) {
        this.updatedEmail = updatedEmail;
    }

    public String getUpdatedContact() {
        return updatedContact;
    }

    public void setUpdatedContact(String updatedContact) {
        this.updatedContact = updatedContact;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.updatedPassword = updatedPassword;
    }

    public String getUpdatedUsername() {
        return updatedUsername;
    }

    public void setUpdatedUsername(String updatedUsername) {
        this.updatedUsername = updatedUsername;
    }
}
