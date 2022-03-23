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
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateAccessoryManagedBean")
@ViewScoped
public class UpdateAccessoryManagedBean implements Serializable {

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    private AccessoryEntity accessoryEntityToUpdate;

    @Inject
    private AccessoryManagementManagedBean accessoryManagementManagedBean;

    /**
     * Creates a new instance of UpdateAccessoryManagedBean
     */
    public UpdateAccessoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {

    }

    public void updateAccessory(ActionEvent event) {
        try {
            accessoryEntitySessionBeanLocal.updateAccessoryEntity(accessoryEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Accessory", null));
            initializeState();
        } catch (UpdateAccessoryEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Accessory", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Validation Error", null));
        } catch (AccessoryEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Not Found", null));
        } catch (UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Persistence Error", null));
        } catch (AccessoryNameExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accessory Name Already in use!", null));
        }
        accessoryManagementManagedBean.initializeState();
    }

    public AccessoryEntity getAccessoryEntityToUpdate() {
        return accessoryEntityToUpdate;
    }

    public void setAccessoryEntityToUpdate(AccessoryEntity accessoryEntityToUpdate) {
        this.accessoryEntityToUpdate = accessoryEntityToUpdate;
    }

    public AccessoryManagementManagedBean getAccessoryManagementManagedBean() {
        return accessoryManagementManagedBean;
    }

    public void setAccessoryManagementManagedBean(AccessoryManagementManagedBean accessoryManagementManagedBean) {
        this.accessoryManagementManagedBean = accessoryManagementManagedBean;
    }

}
