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
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.QuantityOnHandNotZeroException;
import util.exception.UnableToDeleteAccessoryEntityException;
import util.exception.UnableToDeleteAccessoryItemException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deleteAccessoryItemManagedBean")
@ViewScoped
public class DeleteAccessoryItemManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    private AccessoryItemEntity accessoryItemEntityToDelete;

    /**
     * Creates a new instance of DeleteAccessoryItemManagedBean
     */
    public DeleteAccessoryItemManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {

    }

    public void deleteAccessoryItem(ActionEvent event) {
        try {
            accessoryItemEntitySessionBeanLocal.deleteAccessoryItemEntity(accessoryItemEntityToDelete.getAccessoryItemEntityId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Deleted Product!", null));
            initializeState();
        } catch (AccessoryItemEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Item Cannot Be Found", null));
        } catch (UnableToDeleteAccessoryItemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Delete Accessory Item", null));
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

    public AccessoryItemEntity getAccessoryItemEntityToDelete() {
        return accessoryItemEntityToDelete;
    }

    public void setAccessoryItemEntityToDelete(AccessoryItemEntity accessoryItemEntityToDelete) {
        this.accessoryItemEntityToDelete = accessoryItemEntityToDelete;
    }
}
