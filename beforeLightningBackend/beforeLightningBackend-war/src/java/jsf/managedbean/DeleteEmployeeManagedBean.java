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
import util.exception.DeleteEmployeeEntityException;
import util.exception.EmployeeEntityNotFoundException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deleteEmployeeManagedBean")
@ViewScoped
public class DeleteEmployeeManagedBean implements Serializable {

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @Inject
    private EmployeeManagementManagedBean employeeManagementManagedBean;

    private EmployeeEntity employeeEntityToDelete;

    public DeleteEmployeeManagedBean() {
        employeeEntityToDelete = new EmployeeEntity();
    }

    @PostConstruct
    public void postConstruct() {
    }

    public void deleteEmployee(ActionEvent event) {
        try {
            employeeEntitySessionBeanLocal.deleteEmployeeEntity(employeeEntityToDelete.getUserEntityId());
            List<EmployeeEntity> newListOfEmployeeEntities = employeeManagementManagedBean.getListOfEmployeeEntities();
            newListOfEmployeeEntities.remove(employeeEntityToDelete);
            employeeManagementManagedBean.setListOfEmployeeEntities(newListOfEmployeeEntities);

            if (employeeManagementManagedBean.getFilteredListOfEmployeeEntities() != null) {
                List<EmployeeEntity> newFilteredListOfEmployeeEntities = employeeManagementManagedBean.getFilteredListOfEmployeeEntities();
                newFilteredListOfEmployeeEntities.remove(employeeEntityToDelete);
                employeeManagementManagedBean.setFilteredListOfEmployeeEntities(newFilteredListOfEmployeeEntities);
            }

        } catch (EmployeeEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Employee Not Found", null));
        } catch (DeleteEmployeeEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Employee Cannot Be Deleted", null));
        }
        employeeEntityToDelete = new EmployeeEntity();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee deleted successfully", null));
    }

    /**
     * @return the employeeEntityToDelete
     */
    public EmployeeEntity getEmployeeEntityToDelete() {
        return employeeEntityToDelete;
    }

    /**
     * @param employeeEntityToDelete the employeeEntityToDelete to set
     */
    public void setEmployeeEntityToDelete(EmployeeEntity employeeEntityToDelete) {
        this.employeeEntityToDelete = employeeEntityToDelete;
    }

    public EmployeeManagementManagedBean getEmployeeManagementManagedBean() {
        return employeeManagementManagedBean;
    }

    public void setEmployeeManagementManagedBean(EmployeeManagementManagedBean employeeManagementManagedBean) {
        this.employeeManagementManagedBean = employeeManagementManagedBean;
    }

}
