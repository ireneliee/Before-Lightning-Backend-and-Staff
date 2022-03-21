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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import util.exception.PartNameNotFoundException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "partChoiceManagementManagedBean")
@ViewScoped
public class PartChoiceManagementManagedBean implements Serializable {

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private ViewPartChoiceManagedBean viewPartChoiceManagedBean;

    @Inject
    private DisablePartChoiceManagedBean disablePartChoiceManagedBean;

    @Inject
    private UpdatePartChoiceManagedBean updatePartChoiceManagedBean;

    @Inject
    private DeletePartChoiceManagedBean deletePartChoiceManagedBean;

    @Inject
    private UpdateLinkedPartChoicesManagedBean updateLinkedPartChoicesManagedBean;

    @Inject
    private UpdateChassisLinkedPartChoicesManagedBean updateChassisLinkedPartChoicesManagedBean;

    private List<PartChoiceEntity> listOfChassisPartChoiceEntities;
    private List<PartChoiceEntity> filteredListOfChassisPartChoiceEntities;

    private List<PartChoiceEntity> listOfPartChoiceEntities;
    private List<PartChoiceEntity> filteredListOfPartChoiceEntities;

    public PartChoiceManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        try {
            listOfChassisPartChoiceEntities = partEntitySessionBeanLocal.retrievePartEntityByPartName("Chassis").getPartChoiceEntities();
        } catch (PartNameNotFoundException ex) {
            Logger.getLogger(PartChoiceManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        listOfPartChoiceEntities = partChoiceEntitySessionBeanLocal.retrieveAllPartChoiceEntities();
        listOfPartChoiceEntities.removeIf(pc -> listOfChassisPartChoiceEntities.contains(pc));
    }

    /**
     * @return the listOfPartChoiceEntities
     */
    public List<PartChoiceEntity> getListOfPartChoiceEntities() {
        return listOfPartChoiceEntities;
    }

    /**
     * @param listOfPartChoiceEntities the listOfPartChoiceEntities to set
     */
    public void setListOfPartChoiceEntities(List<PartChoiceEntity> listOfPartChoiceEntities) {
        this.listOfPartChoiceEntities = listOfPartChoiceEntities;
    }

    /**
     * @return the filteredListOfPartChoiceEntities
     */
    public List<PartChoiceEntity> getFilteredListOfPartChoiceEntities() {
        return filteredListOfPartChoiceEntities;
    }

    /**
     * @param filteredListOfPartChoiceEntities the
     * filteredListOfPartChoiceEntities to set
     */
    public void setFilteredListOfPartChoiceEntities(List<PartChoiceEntity> filteredListOfPartChoiceEntities) {
        this.filteredListOfPartChoiceEntities = filteredListOfPartChoiceEntities;
    }

    public ViewPartChoiceManagedBean getViewPartChoiceManagedBean() {
        return viewPartChoiceManagedBean;
    }

    public void setViewPartChoiceManagedBean(ViewPartChoiceManagedBean viewPartChoiceManagedBean) {
        this.viewPartChoiceManagedBean = viewPartChoiceManagedBean;
    }

    public UpdatePartChoiceManagedBean getUpdatePartChoiceManagedBean() {
        return updatePartChoiceManagedBean;
    }

    public void setUpdatePartChoiceManagedBean(UpdatePartChoiceManagedBean updatePartChoiceManagedBean) {
        this.updatePartChoiceManagedBean = updatePartChoiceManagedBean;
    }

    public DisablePartChoiceManagedBean getDisablePartChoiceManagedBean() {
        return disablePartChoiceManagedBean;
    }

    public void setDisablePartChoiceManagedBean(DisablePartChoiceManagedBean disablePartChoiceManagedBean) {
        this.disablePartChoiceManagedBean = disablePartChoiceManagedBean;
    }

    public DeletePartChoiceManagedBean getDeletePartChoiceManagedBean() {
        return deletePartChoiceManagedBean;
    }

    public void setDeletePartChoiceManagedBean(DeletePartChoiceManagedBean deletePartChoiceManagedBean) {
        this.deletePartChoiceManagedBean = deletePartChoiceManagedBean;
    }

    public UpdateLinkedPartChoicesManagedBean getUpdateLinkedPartChoicesManagedBean() {
        return updateLinkedPartChoicesManagedBean;
    }

    public void setUpdateLinkedPartChoicesManagedBean(UpdateLinkedPartChoicesManagedBean updateLinkedPartChoicesManagedBean) {
        this.updateLinkedPartChoicesManagedBean = updateLinkedPartChoicesManagedBean;
    }

    public List<PartChoiceEntity> getListOfChassisPartChoiceEntities() {
        return listOfChassisPartChoiceEntities;
    }

    public void setListOfChassisPartChoiceEntities(List<PartChoiceEntity> listOfChassisPartChoiceEntities) {
        this.listOfChassisPartChoiceEntities = listOfChassisPartChoiceEntities;
    }

    public List<PartChoiceEntity> getFilteredListOfChassisPartChoiceEntities() {
        return filteredListOfChassisPartChoiceEntities;
    }

    public void setFilteredListOfChassisPartChoiceEntities(List<PartChoiceEntity> filteredListOfChassisPartChoiceEntities) {
        this.filteredListOfChassisPartChoiceEntities = filteredListOfChassisPartChoiceEntities;
    }

    public UpdateChassisLinkedPartChoicesManagedBean getUpdateChassisLinkedPartChoicesManagedBean() {
        return updateChassisLinkedPartChoicesManagedBean;
    }

    public void setUpdateChassisLinkedPartChoicesManagedBean(UpdateChassisLinkedPartChoicesManagedBean updateChassisLinkedPartChoicesManagedBean) {
        this.updateChassisLinkedPartChoicesManagedBean = updateChassisLinkedPartChoicesManagedBean;
    }

}
