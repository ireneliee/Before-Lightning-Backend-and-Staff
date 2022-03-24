/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.PurchaseOrderEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author srinivas
 */
@Named(value = "readyToShipManagedBean")
@ViewScoped
public class ReadyToShipManagedBean implements Serializable {

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;
    
    @Inject
    private PurchaseOrderProgressManagementManagedBean orderProgressManagementManagedBean;
    private PurchaseOrderEntity purchaseOrderToUpdate;
    /**
     * Creates a new instance of ReadyToShipManagedBean
     */
    public ReadyToShipManagedBean() {
    }
    
        @PostConstruct
    public void postConstruct() {
    }
    
    public void changeToReady(ActionEvent event) {
        purchaseOrderEntitySessionBean.changeToReady(purchaseOrderToUpdate.getPurchaseOrderEntityId());
        orderProgressManagementManagedBean.initialiseState();
    }

    /**
     * @return the purchaseOrderEntitySessionBean
     */
    public PurchaseOrderEntitySessionBeanLocal getPurchaseOrderEntitySessionBean() {
        return purchaseOrderEntitySessionBean;
    }

    /**
     * @param purchaseOrderEntitySessionBean the purchaseOrderEntitySessionBean to set
     */
    public void setPurchaseOrderEntitySessionBean(PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean) {
        this.purchaseOrderEntitySessionBean = purchaseOrderEntitySessionBean;
    }

    /**
     * @return the orderProgressManagementManagedBean
     */
    public PurchaseOrderProgressManagementManagedBean getOrderProgressManagementManagedBean() {
        return orderProgressManagementManagedBean;
    }

    /**
     * @param orderProgressManagementManagedBean the orderProgressManagementManagedBean to set
     */
    public void setOrderProgressManagementManagedBean(PurchaseOrderProgressManagementManagedBean orderProgressManagementManagedBean) {
        this.orderProgressManagementManagedBean = orderProgressManagementManagedBean;
    }

    /**
     * @return the purchaseOrderToUpdate
     */
    public PurchaseOrderEntity getPurchaseOrderToUpdate() {
        return purchaseOrderToUpdate;
    }

    /**
     * @param purchaseOrderToUpdate the purchaseOrderToUpdate to set
     */
    public void setPurchaseOrderToUpdate(PurchaseOrderEntity purchaseOrderToUpdate) {
        this.purchaseOrderToUpdate = purchaseOrderToUpdate;
    }
    
}
