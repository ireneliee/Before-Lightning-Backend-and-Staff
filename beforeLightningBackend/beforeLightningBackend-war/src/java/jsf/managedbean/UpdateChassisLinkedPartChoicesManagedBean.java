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
@Named(value = "updateChassisLinkedPartChoicesManagedBean")
@ViewScoped
public class UpdateChassisLinkedPartChoicesManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private PartChoiceManagementManagedBean partChoiceManagementManagedBean;

    private List<PartChoiceEntity> newListOfSelectedPartChoiceEntities;
    private List<PartChoiceEntity> listOfAvailablePartChoiceEntities;
    private PartChoiceEntity chassisPartChoiceEntityPartChoicesToUpdate;

    /**
     * Creates a new instance of UpdateChassisLinkedPartChoicesManagedBean
     */
    public UpdateChassisLinkedPartChoicesManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        try {
            listOfAvailablePartChoiceEntities = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis").getPartChoiceEntities();
        } catch (PartNameNotFoundException ex) {
            Logger.getLogger(PartChoiceManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        listOfAvailablePartChoiceEntities = partChoiceEntitySessionBeanLocal.retrieveAllPartChoiceEntities();
        listOfAvailablePartChoiceEntities.removeIf(pc -> pc.getPartChoiceName().contains("Chassis"));
        newListOfSelectedPartChoiceEntities = new ArrayList<>();
    }

    public void updateChassisLinkedPartChoices(ActionEvent event) {
        try {
            partChoiceEntitySessionBeanLocal.updateLinkedPartChoiceEntities(newListOfSelectedPartChoiceEntities, chassisPartChoiceEntityPartChoicesToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updates Linked Part Choices", null));

        } catch (UnableToLinkPartChoicesException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Linked Part Choices", null));
        }
        initializeState();
        partChoiceManagementManagedBean.initializeState();
    }

    public PartChoiceManagementManagedBean getPartChoiceManagementManagedBean() {
        return partChoiceManagementManagedBean;
    }

    public void setPartChoiceManagementManagedBean(PartChoiceManagementManagedBean partChoiceManagementManagedBean) {
        this.partChoiceManagementManagedBean = partChoiceManagementManagedBean;
    }

    public List<PartChoiceEntity> getNewListOfSelectedPartChoiceEntities() {
        return newListOfSelectedPartChoiceEntities;
    }

    public void setNewListOfSelectedPartChoiceEntities(List<PartChoiceEntity> newListOfSelectedPartChoiceEntities) {
        this.newListOfSelectedPartChoiceEntities = newListOfSelectedPartChoiceEntities;
    }

    public List<PartChoiceEntity> getListOfAvailablePartChoiceEntities() {
        return listOfAvailablePartChoiceEntities;
    }

    public void setListOfAvailablePartChoiceEntities(List<PartChoiceEntity> listOfAvailablePartChoiceEntities) {
        this.listOfAvailablePartChoiceEntities = listOfAvailablePartChoiceEntities;
    }

    public PartChoiceEntity getChassisPartChoiceEntityPartChoicesToUpdate() {
        return chassisPartChoiceEntityPartChoicesToUpdate;
    }

    public void setChassisPartChoiceEntityPartChoicesToUpdate(PartChoiceEntity chassisPartChoiceEntityPartChoicesToUpdate) {
        this.chassisPartChoiceEntityPartChoicesToUpdate = chassisPartChoiceEntityPartChoicesToUpdate;
    }

}
