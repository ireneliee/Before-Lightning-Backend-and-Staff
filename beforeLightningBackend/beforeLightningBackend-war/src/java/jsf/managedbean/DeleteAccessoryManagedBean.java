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
import util.exception.AccessoryEntityNotFoundException;
import util.exception.UnableToDeleteAccessoryEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deleteAccessoryManagedBean")
@ViewScoped
public class DeleteAccessoryManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @Inject
    private AccessoryManagementManagedBean accessoryManagementManagedBean;

    private AccessoryEntity accessoryEntityToDelete;

    /**
     * Creates a new instance of DeleteAccessoryManagedBean
     */
    public DeleteAccessoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        accessoryEntityToDelete = null;
    }

    public void deleteAccessory(ActionEvent event) {
        try {
            accessoryEntitySessionBeanLocal.deleteAccessoryEntity(accessoryEntityToDelete.getAccessoryEntityId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Deleted Product!", null));
            initializeState();
        } catch (AccessoryEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Cannot Be Found", null));
        } catch (UnableToDeleteAccessoryEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Delete Accessory", null));
        }
        accessoryManagementManagedBean.initializeState();
    }

    public AccessoryEntitySessionBeanLocal getAccessoryEntitySessionBeanLocal() {
        return accessoryEntitySessionBeanLocal;
    }

    public void setAccessoryEntitySessionBeanLocal(AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal) {
        this.accessoryEntitySessionBeanLocal = accessoryEntitySessionBeanLocal;
    }

    public AccessoryManagementManagedBean getAccessoryManagementManagedBean() {
        return accessoryManagementManagedBean;
    }

    public void setAccessoryManagementManagedBean(AccessoryManagementManagedBean accessoryManagementManagedBean) {
        this.accessoryManagementManagedBean = accessoryManagementManagedBean;
    }

    public AccessoryEntity getAccessoryEntityToDelete() {
        return accessoryEntityToDelete;
    }

    public void setAccessoryEntityToDelete(AccessoryEntity accessoryEntityToDelete) {
        this.accessoryEntityToDelete = accessoryEntityToDelete;
    }
}
