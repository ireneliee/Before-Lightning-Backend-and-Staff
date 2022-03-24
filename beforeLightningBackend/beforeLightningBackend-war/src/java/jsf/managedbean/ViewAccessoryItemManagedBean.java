/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.AccessoryItemEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "viewAccessoryItemManagedBean")
@ViewScoped
public class ViewAccessoryItemManagedBean implements Serializable {

    private AccessoryItemEntity accessoryItemEntityToView;

    @Inject
    private AccessoryItemManagementManagedBean accessoryItemManagementManagedBean;

    /**
     * Creates a new instance of ViewAccessoryItemManagedBean
     */
    public ViewAccessoryItemManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        
    }

    public AccessoryItemManagementManagedBean getAccessoryItemManagementManagedBean() {
        return accessoryItemManagementManagedBean;
    }

    public void setAccessoryItemManagementManagedBean(AccessoryItemManagementManagedBean accessoryItemManagementManagedBean) {
        this.accessoryItemManagementManagedBean = accessoryItemManagementManagedBean;
    }

    public AccessoryItemEntity getAccessoryItemEntityToView() {
        return accessoryItemEntityToView;
    }

    public void setAccessoryItemEntityToView(AccessoryItemEntity accessoryItemEntityToView) {
        this.accessoryItemEntityToView = accessoryItemEntityToView;
    }

}
