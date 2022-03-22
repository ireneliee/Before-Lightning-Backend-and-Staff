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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "createAccessoryManagedBean")
@ViewScoped
public class CreateAccessoryManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @Inject
    private AccessoryManagementManagedBean accessoryManagementManagedBean;

    private AccessoryEntity newAccessoryEntity;

    /**
     * Creates a new instance of CreateAccessoryManagedBean
     */
    public CreateAccessoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        newAccessoryEntity = new AccessoryEntity();
    }

    public void createNewAccessory(ActionEvent event) {
        try {
            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(newAccessoryEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created Accessory", null));
            initializeState();
            accessoryManagementManagedBean.initializeState();
        } catch (AccessoryNameExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Name In Use Already ", null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Create Accessory " , null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error " , null));
        }
    }

    public AccessoryManagementManagedBean getAccessoryManagementManagedBean() {
        return accessoryManagementManagedBean;
    }

    public void setAccessoryManagementManagedBean(AccessoryManagementManagedBean accessoryManagementManagedBean) {
        this.accessoryManagementManagedBean = accessoryManagementManagedBean;
    }

    public AccessoryEntity getNewAccessoryEntity() {
        return newAccessoryEntity;
    }

    public void setNewAccessoryEntity(AccessoryEntity newAccessoryEntity) {
        this.newAccessoryEntity = newAccessoryEntity;
    }

}
