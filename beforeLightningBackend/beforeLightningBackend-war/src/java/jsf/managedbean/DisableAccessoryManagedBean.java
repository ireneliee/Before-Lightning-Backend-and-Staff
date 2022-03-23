/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import entity.AccessoryEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.UpdateAccessoryEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "disableAccessoryManagedBean")
@ViewScoped
public class DisableAccessoryManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @Inject
    private AccessoryManagementManagedBean accessoryManagementManagedBean;

    private AccessoryEntity accessoryEntityToDisable;

    /**
     * Creates a new instance of DisableAccessoryManagedBean
     */
    public DisableAccessoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        accessoryEntityToDisable = null;
    }

    public void disableAccessory(ActionEvent event) {
        Boolean initialState = accessoryEntityToDisable.getIsDisabled();
        try {
            accessoryEntitySessionBeanLocal.toggleDisableAccessoryEntity(accessoryEntityToDisable.getAccessoryEntityId());
        } catch (UpdateAccessoryEntityException ex) {
            if (initialState == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Enable Accessory", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Disable Accessory", null));
            }
        }

        if (initialState == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Enabled Accessory!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Disabled Accessory!", null));
        }
        accessoryManagementManagedBean.initializeState();
    }

    public AccessoryManagementManagedBean getAccessoryManagementManagedBean() {
        return accessoryManagementManagedBean;
    }

    public void setAccessoryManagementManagedBean(AccessoryManagementManagedBean accessoryManagementManagedBean) {
        this.accessoryManagementManagedBean = accessoryManagementManagedBean;
    }

    public AccessoryEntity getAccessoryEntityToDisable() {
        return accessoryEntityToDisable;
    }

    public void setAccessoryEntityToDisable(AccessoryEntity accessoryEntityToDisable) {
        this.accessoryEntityToDisable = accessoryEntityToDisable;
    }

}
