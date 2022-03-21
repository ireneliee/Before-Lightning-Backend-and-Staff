/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.PartNameNotFoundException;
import util.exception.UnableToLinkPartChoicesException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateLinkedPartChoicesManagedBean")
@ViewScoped
public class UpdateLinkedPartChoicesManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;

    private List<PartChoiceEntity> listOfAvailableChassisPartChoiceEntities;
    private List<PartChoiceEntity> newListOfSelectedChassisPartChoiceEntities;
    private PartChoiceEntity partChoiceEntityChassisPartChoicesToUpdate;

    /**
     * Creates a new instance of UpdateLinkedPartChoicesManagedBean
     */
    public UpdateLinkedPartChoicesManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        try {
            listOfAvailableChassisPartChoiceEntities = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis").getPartChoiceEntities();
        } catch (PartNameNotFoundException ex) {
            Logger.getLogger(PartChoiceManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        newListOfSelectedChassisPartChoiceEntities = new ArrayList<>();
    }

    public void updateLinkedPartChoices(ActionEvent event) {
        try {
            partChoiceEntitySessionBeanLocal.updateLinkedPartChoiceEntities(newListOfSelectedChassisPartChoiceEntities, partChoiceEntityChassisPartChoicesToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updates Linked Part Choices", null));

        } catch (UnableToLinkPartChoicesException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Linked Part Choices", null));
        }
        initializeState();
        partChoiceManagementManagedBean.initializeState();
    }

    public List<PartChoiceEntity> getListOfAvailableChassisPartChoiceEntities() {
        return listOfAvailableChassisPartChoiceEntities;
    }

    public void setListOfAvailableChassisPartChoiceEntities(List<PartChoiceEntity> listOfAvailableChassisPartChoiceEntities) {
        this.listOfAvailableChassisPartChoiceEntities = listOfAvailableChassisPartChoiceEntities;
    }

    public List<PartChoiceEntity> getNewListOfSelectedChassisPartChoiceEntities() {
        return newListOfSelectedChassisPartChoiceEntities;
    }

    public void setNewListOfSelectedChassisPartChoiceEntities(List<PartChoiceEntity> newListOfSelectedChassisPartChoiceEntities) {
        this.newListOfSelectedChassisPartChoiceEntities = newListOfSelectedChassisPartChoiceEntities;
    }

    public PartChoiceEntity getPartChoiceEntityChassisPartChoicesToUpdate() {
        return partChoiceEntityChassisPartChoicesToUpdate;
    }

    public void setPartChoiceEntityChassisPartChoicesToUpdate(PartChoiceEntity partChoiceEntityChassisPartChoicesToUpdate) {
        this.partChoiceEntityChassisPartChoicesToUpdate = partChoiceEntityChassisPartChoicesToUpdate;
    }

    public PartChoiceManagementManagedBean getPartChoiceManagementManagedBean() {
        return partChoiceManagementManagedBean;
    }

    public void setPartChoiceManagementManagedBean(PartChoiceManagementManagedBean partChoiceManagementManagedBean) {
        this.partChoiceManagementManagedBean = partChoiceManagementManagedBean;
    }

}
