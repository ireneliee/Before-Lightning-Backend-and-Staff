/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.PartEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.UnableToAddPartChoiceToPartException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updatePartPartChoicesManagedBean")
@ViewScoped
public class UpdatePartPartChoicesManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @Inject
    private PartManagementManagedBean partManagementManagedBean;
    /**
     * Creates a new instance of UpdatePartPartChoicesManagedBean
     */
    private PartEntity partEntityPartChoicesToUpdate;
    private List<PartChoiceEntity> listOfAvailablePartChoiceEntities;
    private List<PartChoiceEntity> newListOfSelectedPartChoiceEntities;

    public UpdatePartPartChoicesManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();

    }

    public void initializeState() {
        listOfAvailablePartChoiceEntities = partChoiceEntitySessionBeanLocal.retrieveAllPartChoiceEntities();
        listOfAvailablePartChoiceEntities.removeIf(pc -> pc.getPartChoiceName().contains("Chassis"));
        newListOfSelectedPartChoiceEntities = new ArrayList<>();
    }

    public void updatePartPartChoices(ActionEvent event) {
        try {
            partEntitySessionBeanLocal.updatePartPartChoices(newListOfSelectedPartChoiceEntities, partEntityPartChoicesToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Part's Part Chociess", null));
            initializeState();
            getPartManagementManagedBean().initializeState();
        } catch (UnableToAddPartChoiceToPartException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Part Choices In Part", null));
        }
    }

    /**
     * @return the partManagementManagedBean
     */
    public PartManagementManagedBean getPartManagementManagedBean() {
        return partManagementManagedBean;
    }

    /**
     * @param partManagementManagedBean the partManagementManagedBean to set
     */
    public void setPartManagementManagedBean(PartManagementManagedBean partManagementManagedBean) {
        this.partManagementManagedBean = partManagementManagedBean;
    }

    /**
     * @return the partEntityPartChoicesToUpdate
     */
    public PartEntity getPartEntityPartChoicesToUpdate() {
        return partEntityPartChoicesToUpdate;
    }

    /**
     * @param partEntityPartChoicesToUpdate the partEntityPartChoicesToUpdate to
     * set
     */
    public void setPartEntityPartChoicesToUpdate(PartEntity partEntityPartChoicesToUpdate) {
        this.partEntityPartChoicesToUpdate = partEntityPartChoicesToUpdate;
    }

    /**
     * @return the listOfAvailablePartChoiceEntities
     */
    public List<PartChoiceEntity> getListOfAvailablePartChoiceEntities() {
        return listOfAvailablePartChoiceEntities;
    }

    /**
     * @param listOfAvailablePartChoiceEntities the
     * listOfAvailablePartChoiceEntities to set
     */
    public void setListOfAvailablePartChoiceEntities(List<PartChoiceEntity> listOfAvailablePartChoiceEntities) {
        this.listOfAvailablePartChoiceEntities = listOfAvailablePartChoiceEntities;
    }

    /**
     * @return the newListOfSelectedPartChoiceEntities
     */
    public List<PartChoiceEntity> getNewListOfSelectedPartChoiceEntities() {
        return newListOfSelectedPartChoiceEntities;
    }

    /**
     * @param newListOfSelectedPartChoiceEntities the
     * newListOfSelectedPartChoiceEntities to set
     */
    public void setNewListOfSelectedPartChoiceEntities(List<PartChoiceEntity> newListOfSelectedPartChoiceEntities) {
        this.newListOfSelectedPartChoiceEntities = newListOfSelectedPartChoiceEntities;
    }

}
