/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.EmployeeEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewEmployeeManagedBean")
@ViewScoped
public class ViewEmployeeManagedBean implements Serializable {

    private EmployeeEntity employeeEntityToView;

    public ViewEmployeeManagedBean() {
        employeeEntityToView = new EmployeeEntity();
    }

    @PostConstruct
    public void postConstruct() {
    }

    /**
     * @return the employeeEntityToView
     */
    public EmployeeEntity getEmployeeEntityToView() {
        return employeeEntityToView;
    }

    /**
     * @param employeeEntityToView the employeeEntityToView to set
     */
    public void setEmployeeEntityToView(EmployeeEntity employeeEntityToView) {
        this.employeeEntityToView = employeeEntityToView;
    }

}
