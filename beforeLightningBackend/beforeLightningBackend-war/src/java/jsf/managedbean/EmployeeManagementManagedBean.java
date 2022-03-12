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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import util.enumeration.EmployeeAccessRightEnum;

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

}
