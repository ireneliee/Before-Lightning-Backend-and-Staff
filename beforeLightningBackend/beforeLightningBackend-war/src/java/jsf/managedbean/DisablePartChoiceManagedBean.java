/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "disablePartChoiceManagedBean")
@ViewScoped
public class DisablePartChoiceManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;

    private PartChoiceEntity partChoiceEntityToDisable;

    /**
     * Creates a new instance of DisablePartChoiceManageBean
     */
    public DisablePartChoiceManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
    }

    public void disablePartChoice(ActionEvent event) {
        Boolean initialState = getPartChoiceEntityToDisable().getIsDisabled();
        try {
            partChoiceEntitySessionBeanLocal.toggleDisablePartChoiceEntity(getPartChoiceEntityToDisable().getPartChoiceEntityId());
        } catch (UpdatePartChoiceEntityException ex) {
            if (initialState == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Enable Part Choice", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Disable Part Choice", null));
            }
        }

        if (initialState == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Enabled Part!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Disabled Part!", null));
        }
        getPartChoiceManagementManagedBean().initializeState();
    }

    /**
     * @return the partChoiceManagementManagedBean
     */
    public PartChoiceManagementManagedBean getPartChoiceManagementManagedBean() {
        return partChoiceManagementManagedBean;
    }

    /**
     * @param partChoiceManagementManagedBean the partChoiceManagementManagedBean to set
     */
    public void setPartChoiceManagementManagedBean(PartChoiceManagementManagedBean partChoiceManagementManagedBean) {
        this.partChoiceManagementManagedBean = partChoiceManagementManagedBean;
    }

    /**
     * @return the partChoiceEntityToDisable
     */
    public PartChoiceEntity getPartChoiceEntityToDisable() {
        return partChoiceEntityToDisable;
    }

    /**
     * @param partChoiceEntityToDisable the partChoiceEntityToDisable to set
     */
    public void setPartChoiceEntityToDisable(PartChoiceEntity partChoiceEntityToDisable) {
        this.partChoiceEntityToDisable = partChoiceEntityToDisable;
    }
}
