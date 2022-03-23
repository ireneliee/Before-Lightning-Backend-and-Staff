/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
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
@Named(value = "accessoryItemManagementManagedBean")
@ViewScoped
public class AccessoryItemManagementManagedBean implements Serializable {

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @Inject
    private ViewAccessoryItemManagedBean viewAccessoryItemManagedBean;
    @Inject
    private UpdateAccessoryItemManagedBean updateAccessoryItemManagedBean;
    @Inject
    private UpdateAccessoryItemAccessoryManagedBean updateAccessoryItemAccessoryManagedBean;
    @Inject
    private DeleteAccessoryItemManagedBean deleteAccessoryItemManagedBean;
    @Inject
    private DisableAccessoryItemManagedBean disableAccessoryItemManagedBean;

    private List<AccessoryItemEntity> listOfAccessoryItemEntities;
    private List<AccessoryItemEntity> filteredListOfAccessoryItemEntities;

    /**
     * Creates a new instance of AccessoryItemManagementManagedBean
     */
    public AccessoryItemManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfAccessoryItemEntities = accessoryItemEntitySessionBeanLocal.retrieveAllAccessoryItemEntities();
    }

    public List<AccessoryItemEntity> getListOfAccessoryItemEntities() {
        return listOfAccessoryItemEntities;
    }

    public void setListOfAccessoryItemEntities(List<AccessoryItemEntity> listOfAccessoryItemEntities) {
        this.listOfAccessoryItemEntities = listOfAccessoryItemEntities;
    }

    public List<AccessoryItemEntity> getFilteredListOfAccessoryItemEntities() {
        return filteredListOfAccessoryItemEntities;
    }

    public void setFilteredListOfAccessoryItemEntities(List<AccessoryItemEntity> filteredListOfAccessoryItemEntities) {
        this.filteredListOfAccessoryItemEntities = filteredListOfAccessoryItemEntities;
    }

    public ViewAccessoryItemManagedBean getViewAccessoryItemManagedBean() {
        return viewAccessoryItemManagedBean;
    }

    public void setViewAccessoryItemManagedBean(ViewAccessoryItemManagedBean viewAccessoryItemManagedBean) {
        this.viewAccessoryItemManagedBean = viewAccessoryItemManagedBean;
    }

    public UpdateAccessoryItemManagedBean getUpdateAccessoryItemManagedBean() {
        return updateAccessoryItemManagedBean;
    }

    public void setUpdateAccessoryItemManagedBean(UpdateAccessoryItemManagedBean updateAccessoryItemManagedBean) {
        this.updateAccessoryItemManagedBean = updateAccessoryItemManagedBean;
    }

    public DeleteAccessoryItemManagedBean getDeleteAccessoryItemManagedBean() {
        return deleteAccessoryItemManagedBean;
    }

    public void setDeleteAccessoryItemManagedBean(DeleteAccessoryItemManagedBean deleteAccessoryItemManagedBean) {
        this.deleteAccessoryItemManagedBean = deleteAccessoryItemManagedBean;
    }

    public DisableAccessoryItemManagedBean getDisableAccessoryItemManagedBean() {
        return disableAccessoryItemManagedBean;
    }

    public void setDisableAccessoryItemManagedBean(DisableAccessoryItemManagedBean disableAccessoryItemManagedBean) {
        this.disableAccessoryItemManagedBean = disableAccessoryItemManagedBean;
    }

    public UpdateAccessoryItemAccessoryManagedBean getUpdateAccessoryItemAccessoryManagedBean() {
        return updateAccessoryItemAccessoryManagedBean;
    }

    public void setUpdateAccessoryItemAccessoryManagedBean(UpdateAccessoryItemAccessoryManagedBean updateAccessoryItemAccessoryManagedBean) {
        this.updateAccessoryItemAccessoryManagedBean = updateAccessoryItemAccessoryManagedBean;
    }

}
