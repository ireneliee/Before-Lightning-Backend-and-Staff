/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import entity.ReviewEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author srinivas
 */
@Named(value = "viewPurchaseOrderManagedBean")
@ViewScoped
public class ViewPurchaseOrderManagedBean implements Serializable {
    
    private PurchaseOrderEntity purchaseOrderToView;
    private List<PurchaseOrderLineItemEntity> listOfLineItems;
    private List<PurchaseOrderLineItemEntity> filteredList;
    /**
     * Creates a new instance of ViewPurchaseOrderManagedBean
     */
    public ViewPurchaseOrderManagedBean() {
    }
    
        @PostConstruct
    public void postConstruct() {
        this.purchaseOrderToView = new PurchaseOrderEntity();
        listOfLineItems = new ArrayList<>();
        filteredList = new ArrayList<>();
    }

 
    public PurchaseOrderEntity getPurchaseOrderToView() {
        return purchaseOrderToView;
    }

    /**
     * @param purchaseOrderToView the purchaseOrderToView to set
     */
    public void setPurchaseOrderToView(PurchaseOrderEntity purchaseOrderToView) {
        this.purchaseOrderToView = purchaseOrderToView;
        setListOfLineItems(purchaseOrderToView.getPurchaseOrderLineItems());
        System.out.println(purchaseOrderToView.getPurchaseOrderEntityId());
        System.out.println(purchaseOrderToView.getPurchaseOrderLineItems().isEmpty());
    }

    /**
     * @return the listOfLineItems
     */
    public List<PurchaseOrderLineItemEntity> getListOfLineItems() {
        return listOfLineItems;
    }

    /**
     * @param listOfLineItems the listOfLineItems to set
     */
    public void setListOfLineItems(List<PurchaseOrderLineItemEntity> listOfLineItems) {
        this.listOfLineItems = listOfLineItems;
    }

    /**
     * @return the filteredList
     */
    public List<PurchaseOrderLineItemEntity> getFilteredList() {
        return filteredList;
    }

    /**
     * @param filteredList the filteredList to set
     */
    public void setFilteredList(List<PurchaseOrderLineItemEntity> filteredList) {
        this.filteredList = filteredList;
    }
    
}
