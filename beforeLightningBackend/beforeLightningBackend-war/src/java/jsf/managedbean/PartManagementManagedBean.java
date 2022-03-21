/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.PartEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "partManagementManagedBean")
@ViewScoped
public class PartManagementManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @Inject
    private ViewPartManagedBean viewPartManagedBean;

    @Inject
    private UpdatePartManagedBean updatePartManagedBean;

    @Inject
    private DisablePartManagedBean disablePartManagedBean;

    @Inject
    private DeletePartManagedBean deletePartManagedBean;

    @Inject
    private UpdatePartPartChoicesManagedBean updatePartPartChoicesManagedBean;

    private List<PartEntity> listOfPartEntities;
    private List<PartEntity> filteredListOfPartEntities;
    private String chassis;

    public PartManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setListOfPartEntities(partEntitySessionBeanLocal.retrieveAllPartEntities());
        chassis = "Chassis";
    }

    /**
     * @return the listOfPartEntities
     */
    public List<PartEntity> getListOfPartEntities() {
        return listOfPartEntities;
    }

    /**
     * @param listOfPartEntities the listOfPartEntities to set
     */
    public void setListOfPartEntities(List<PartEntity> listOfPartEntities) {
        this.listOfPartEntities = listOfPartEntities;
    }

    /**
     * @return the filteredListOfPartEntities
     */
    public List<PartEntity> getFilteredListOfPartEntities() {
        return filteredListOfPartEntities;
    }

    /**
     * @param filteredListOfPartEntities the filteredListOfPartEntities to set
     */
    public void setFilteredListOfPartEntities(List<PartEntity> filteredListOfPartEntities) {
        this.filteredListOfPartEntities = filteredListOfPartEntities;
    }

    public ViewPartManagedBean getViewPartManagedBean() {
        return viewPartManagedBean;
    }

    public void setViewPartManagedBean(ViewPartManagedBean viewPartManagedBean) {
        this.viewPartManagedBean = viewPartManagedBean;
    }

    public DisablePartManagedBean getDisablePartManagedBean() {
        return disablePartManagedBean;
    }

    public void setDisablePartManagedBean(DisablePartManagedBean disablePartManagedBean) {
        this.disablePartManagedBean = disablePartManagedBean;
    }

    public UpdatePartManagedBean getUpdatePartManagedBean() {
        return updatePartManagedBean;
    }

    public void setUpdatePartManagedBean(UpdatePartManagedBean updatePartManagedBean) {
        this.updatePartManagedBean = updatePartManagedBean;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public DeletePartManagedBean getDeletePartManagedBean() {
        return deletePartManagedBean;
    }

    public void setDeletePartManagedBean(DeletePartManagedBean deletePartManagedBean) {
        this.deletePartManagedBean = deletePartManagedBean;
    }

    public UpdatePartPartChoicesManagedBean getUpdatePartPartChoicesManagedBean() {
        return updatePartPartChoicesManagedBean;
    }

    public void setUpdatePartPartChoicesManagedBean(UpdatePartPartChoicesManagedBean updatePartPartChoicesManagedBean) {
        this.updatePartPartChoicesManagedBean = updatePartPartChoicesManagedBean;
    }
}
