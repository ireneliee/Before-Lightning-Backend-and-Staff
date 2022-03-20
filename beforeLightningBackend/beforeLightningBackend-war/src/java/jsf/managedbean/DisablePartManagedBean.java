/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartEntitySessionBeanLocal;
import entity.PartEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.UpdatePartEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "disablePartManagedBean")
@ViewScoped
public class DisablePartManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartManagementManagedBean partManagementManagedBean;
    /**
     * Creates a new instance of DisablePartManagedBean
     */
    private PartEntity partEntityToDisable;

    public DisablePartManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        partEntityToDisable = null;
    }

    public void disablePart(ActionEvent event) {
        Boolean initialState = partEntityToDisable.getIsDisabled();
        try {
            partEntitySessionBeanLocal.toggleDisablePartEntity(partEntityToDisable.getPartEntityId());
        } catch (UpdatePartEntityException ex) {
            if (initialState == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Enable Part", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Disable Part", null));
            }
        }
        if (initialState == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Enabled Part!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Disabled Part!", null));
        }
        partManagementManagedBean.initializeState();
    }

    public PartEntity getPartEntityToDisable() {
        return partEntityToDisable;
    }

    public void setPartEntityToDisable(PartEntity partEntityToDisable) {
        this.partEntityToDisable = partEntityToDisable;
    }

    public PartManagementManagedBean getPartManagementManagedBean() {
        return partManagementManagedBean;
    }

    public void setPartManagementManagedBean(PartManagementManagedBean partManagementManagedBean) {
        this.partManagementManagedBean = partManagementManagedBean;
    }

}
