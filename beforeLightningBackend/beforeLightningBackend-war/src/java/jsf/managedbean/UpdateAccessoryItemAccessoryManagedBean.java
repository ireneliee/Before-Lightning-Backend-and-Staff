/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
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
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateAccessoryItemAccessoryManagedBean")
@ViewScoped
public class UpdateAccessoryItemAccessoryManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    private AccessoryItemEntity updateAccessoryItemAccessoryEntity;
    private AccessoryEntity accessoryToLink;
    private List<AccessoryEntity> listOfAccessoryEntities;
    private AccessoryEntity selectedAccessory;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    /**
     * Creates a new instance of UpdateAccessoryItemAccessoryManagedBean
     */
    public UpdateAccessoryItemAccessoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfAccessoryEntities = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntitiesNotDisabled();
    }

    public void updateAccessoryItem(ActionEvent event) {
        try {
            accessoryItemEntitySessionBeanLocal.updateAccessoryItemAccessory(updateAccessoryItemAccessoryEntity, accessoryToLink);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updates Accessory Item's Accessory Type", null));
            initializeState();
        } catch (UpdateAccessoryItemEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Accessory Item", null));
        }
        accessoryItemManagementManagedBean.initializeState();
    }

    public AccessoryItemEntitySessionBeanLocal getAccessoryItemEntitySessionBeanLocal() {
        return accessoryItemEntitySessionBeanLocal;
    }

    public void setAccessoryItemEntitySessionBeanLocal(AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal) {
        this.accessoryItemEntitySessionBeanLocal = accessoryItemEntitySessionBeanLocal;
    }

    public AccessoryItemEntity getUpdateAccessoryItemAccessoryEntity() {
        return updateAccessoryItemAccessoryEntity;
    }

    public void setUpdateAccessoryItemAccessoryEntity(AccessoryItemEntity updateAccessoryItemAccessoryEntity) {
        this.updateAccessoryItemAccessoryEntity = updateAccessoryItemAccessoryEntity;
    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;
    }

    public AccessoryEntity getAccessoryToLink() {
        return accessoryToLink;
    }

    public void setAccessoryToLink(AccessoryEntity accessoryToLink) {
        this.accessoryToLink = accessoryToLink;
    }

    public List<AccessoryEntity> getListOfAccessoryEntities() {
        return listOfAccessoryEntities;
    }

    public void setListOfAccessoryEntities(List<AccessoryEntity> listOfAccessoryEntities) {
        this.listOfAccessoryEntities = listOfAccessoryEntities;
    }

    public AccessoryEntity getSelectedAccessory() {
        return selectedAccessory;
    }

    public void setSelectedAccessory(AccessoryEntity selectedAccessory) {
        this.selectedAccessory = selectedAccessory;
    }

}
