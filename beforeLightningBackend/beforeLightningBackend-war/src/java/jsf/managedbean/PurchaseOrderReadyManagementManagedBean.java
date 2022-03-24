/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.PurchaseOrderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author srinivas
 */
@Named(value = "purchaseOrderReadyManagementManagedBean")
@ViewScoped
public class PurchaseOrderReadyManagementManagedBean implements Serializable {

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;
    
    @Inject
    private ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean;
    
    private List<PurchaseOrderEntity> listOfAllPurchaseOrders;
    private List<PurchaseOrderEntity> filteredListOfAllPurchasOrders;

    /**
     * Creates a new instance of PurchaseOrderManagementManagedBean
     */
    public PurchaseOrderReadyManagementManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setListOfAllPurchaseOrders(purchaseOrderEntitySessionBean.retrieveReadyAllPurchaseOrders());
        setFilteredListOfAllPurchasOrders(new ArrayList<>());
    }

    /**
     * @return the listOfAllPurchaseOrders
     */
    public List<PurchaseOrderEntity> getListOfAllPurchaseOrders() {
        return listOfAllPurchaseOrders;
    }

    /**
     * @param listOfAllPurchaseOrders the listOfAllPurchaseOrders to set
     */
    public void setListOfAllPurchaseOrders(List<PurchaseOrderEntity> listOfAllPurchaseOrders) {
        this.listOfAllPurchaseOrders = listOfAllPurchaseOrders;
    }

    /**
     * @return the filteredListOfAllPurchasOrders
     */
    public List<PurchaseOrderEntity> getFilteredListOfAllPurchasOrders() {
        return filteredListOfAllPurchasOrders;
    }

    /**
     * @param filteredListOfAllPurchasOrders the filteredListOfAllPurchasOrders to set
     */
    public void setFilteredListOfAllPurchasOrders(List<PurchaseOrderEntity> filteredListOfAllPurchasOrders) {
        this.filteredListOfAllPurchasOrders = filteredListOfAllPurchasOrders;
    }

    /**
     * @return the viewPurchaseOrderManagedBean
     */
    public ViewPurchaseOrderManagedBean getViewPurchaseOrderManagedBean() {
        return viewPurchaseOrderManagedBean;
    }

    /**
     * @param viewPurchaseOrderManagedBean the viewPurchaseOrderManagedBean to set
     */
    public void setViewPurchaseOrderManagedBean(ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean) {
        this.viewPurchaseOrderManagedBean = viewPurchaseOrderManagedBean;
    }
    
}
