/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "liveAccessoryManagementManagedBean")
@ViewScoped
public class LiveAccessoryManagementManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    private List<AccessoryItemEntity> listOfLiveAccessoryItemEntities;
    private List<AccessoryItemEntity> filteredListOfLiveAccessoryItemEntities;

    /**
     * Creates a new instance of LiveAccessoryManagementManagedBean
     */
    public LiveAccessoryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfLiveAccessoryItemEntities = accessoryItemEntitySessionBeanLocal.retrieveAllAccessoryItemEntitiesThatCanSell();
    }



    public AccessoryEntitySessionBeanLocal getAccessoryEntitySessionBeanLocal() {
        return accessoryEntitySessionBeanLocal;
    }

    public void setAccessoryEntitySessionBeanLocal(AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal) {
        this.accessoryEntitySessionBeanLocal = accessoryEntitySessionBeanLocal;
    }

    public List<AccessoryItemEntity> getListOfLiveAccessoryItemEntities() {
        return listOfLiveAccessoryItemEntities;
    }

    public void setListOfLiveAccessoryItemEntities(List<AccessoryItemEntity> listOfLiveAccessoryItemEntities) {
        this.listOfLiveAccessoryItemEntities = listOfLiveAccessoryItemEntities;
    }

    public List<AccessoryItemEntity> getFilteredListOfLiveAccessoryItemEntities() {
        return filteredListOfLiveAccessoryItemEntities;
    }

    public void setFilteredListOfLiveAccessoryItemEntities(List<AccessoryItemEntity> filteredListOfLiveAccessoryItemEntities) {
        this.filteredListOfLiveAccessoryItemEntities = filteredListOfLiveAccessoryItemEntities;
    }
}
