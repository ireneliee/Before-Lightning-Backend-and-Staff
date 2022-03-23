/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateAccessoryEntityException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "disableAccessoryItemManagedBean")
@ViewScoped
public class DisableAccessoryItemManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    private AccessoryItemEntity accessoryItemEntityToDisable;

    /**
     * Creates a new instance of DisableAccessoryItemManagedBean
     */
    public DisableAccessoryItemManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
    }

    public void disableAccessoryItem(ActionEvent event) {
        Boolean initialState = accessoryItemEntityToDisable.getIsDisabled();
        try {
            accessoryItemEntitySessionBeanLocal.toggleDisableAccessoryItemEntity(accessoryItemEntityToDisable.getAccessoryItemEntityId());
        } catch (AccessoryItemEntityNotFoundException | UpdateAccessoryItemEntityException | InputDataValidationException ex) {
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
        accessoryItemManagementManagedBean.initializeState();
    }

    public AccessoryItemEntitySessionBeanLocal getAccessoryItemEntitySessionBeanLocal() {
        return accessoryItemEntitySessionBeanLocal;
    }

    public void setAccessoryItemEntitySessionBeanLocal(AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal) {
        this.accessoryItemEntitySessionBeanLocal = accessoryItemEntitySessionBeanLocal;
    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;
    }

    public AccessoryItemEntity getAccessoryItemEntityToDisable() {
        return accessoryItemEntityToDisable;
    }

    public void setAccessoryItemEntityToDisable(AccessoryItemEntity accessoryItemEntityToDisable) {
        this.accessoryItemEntityToDisable = accessoryItemEntityToDisable;
    }

}
