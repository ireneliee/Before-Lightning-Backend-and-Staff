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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "partChoiceManagementManagedBean")
@ViewScoped
public class PartChoiceManagementManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @Inject
    private ViewPartChoiceManagedBean viewPartChoiceManagedBean;
    
    private List<PartChoiceEntity> listOfPartChoiceEntities;
    private List<PartChoiceEntity> filteredListOfPartChoiceEntities;
    
    public PartChoiceManagementManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        initializeState();
    }
    
    public void initializeState() {
        listOfPartChoiceEntities = partChoiceEntitySessionBeanLocal.retrieveAllPartChoiceEntities();
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
     * @param filteredListOfPartChoiceEntities the filteredListOfPartChoiceEntities to set
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


}
