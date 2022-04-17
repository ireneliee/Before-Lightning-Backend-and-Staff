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
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

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

        this.purchaseOrderToView = new PurchaseOrderEntity();
    }
//helo

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfLineItems = purchaseOrderToView.getPurchaseOrderLineItems();

        filteredList = new ArrayList<>();
        purchaseOrderToView = new PurchaseOrderEntity();
    }

    public void test(ActionEvent event) {
        System.out.println("this works");
    }

    public PurchaseOrderEntity getPurchaseOrderToView() {
        System.out.println("============GETTER CALLED=========");
//        setListOfLineItems(purchaseOrderToView.getPurchaseOrderLineItems());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PurchaseOrderLineItemEntityConverter_purchaseOrderLineItemEntities", listOfLineItems);
        System.out.println("this is the PO to view: " + purchaseOrderToView.getPurchaseOrderEntityId());
        System.out.println("this is the size of the PO: " + purchaseOrderToView.getPurchaseOrderLineItems().size());
        return purchaseOrderToView;
    }

    /**
     * @param purchaseOrderToView the purchaseOrderToView to set
     */
    /**
     * @return the listOfLineItems
     */
    public List<PurchaseOrderLineItemEntity> getListOfLineItems() {
        System.out.println("============GET LIST CALLED=========");
        return listOfLineItems;
    }

    /**
     * @param listOfLineItems the listOfLineItems to set
     */
    public void setListOfLineItems(List<PurchaseOrderLineItemEntity> listOfLineItems) {
        System.out.println("============SET LIST CALLED=========");
        this.listOfLineItems = listOfLineItems;
        System.out.println("set line item was called");
    }

    /**
     * @return the filteredList
     */
    public List<PurchaseOrderLineItemEntity> getFilteredList() {
        System.out.println("============GET FILTERED LIST CALLED=========");
        return filteredList;
    }

    /**
     * @param filteredList the filteredList to set
     */
    public void setFilteredList(List<PurchaseOrderLineItemEntity> filteredList) {
        System.out.println("============SET FILTERED LIST CALLED=========");
        this.filteredList = filteredList;
    }

    /**
     * @param purchaseOrderToView the purchaseOrderToView to set
     */
    public void setPurchaseOrderToView(PurchaseOrderEntity purchaseOrderToView) {
        System.out.println("set purchase order was called");
        this.purchaseOrderToView = purchaseOrderToView;
        System.out.println("line item size" + purchaseOrderToView.getPurchaseOrderLineItems().size());
        this.listOfLineItems = purchaseOrderToView.getPurchaseOrderLineItems();
        System.out.println("lineItemAttribute " + listOfLineItems.size());
        for (PurchaseOrderLineItemEntity p : listOfLineItems) {
            System.out.println(p.getName());
        }
    }
}
