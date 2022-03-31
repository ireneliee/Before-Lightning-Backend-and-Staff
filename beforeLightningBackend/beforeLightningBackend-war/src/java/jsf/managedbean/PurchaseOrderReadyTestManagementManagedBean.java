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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author srinivas
 */
@Named(value = "purchaseOrderReadyTestManagementManagedBean")
@ViewScoped
public class PurchaseOrderReadyTestManagementManagedBean implements Serializable {

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;

    @Inject
    private ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean;

    private List<PurchaseOrderEntity> listOfAllReadyPurchaseOrders;
    private List<PurchaseOrderEntity> filteredListOfAllReadyPurchasOrders;

    /**
     * Creates a new instance of PurchaseOrderReadyTestManagementManagedBean
     */
    public PurchaseOrderReadyTestManagementManagedBean() {
    }
    
        @PostConstruct
    public void postConstruct() {
        initialiseState();
    }

    public void initialiseState() {
        this.setListOfAllReadyPurchaseOrders(purchaseOrderEntitySessionBean.retrieveReadyAllPurchaseOrders());
        setFilteredListOfAllReadyPurchasOrders(new ArrayList<>());

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

    /**
     * @return the listOfAllReadyPurchaseOrders
     */
    public List<PurchaseOrderEntity> getListOfAllReadyPurchaseOrders() {
        return listOfAllReadyPurchaseOrders;
    }

    /**
     * @param listOfAllReadyPurchaseOrders the listOfAllReadyPurchaseOrders to set
     */
    public void setListOfAllReadyPurchaseOrders(List<PurchaseOrderEntity> listOfAllReadyPurchaseOrders) {
        this.listOfAllReadyPurchaseOrders = listOfAllReadyPurchaseOrders;
    }

    /**
     * @return the filteredListOfAllReadyPurchasOrders
     */
    public List<PurchaseOrderEntity> getFilteredListOfAllReadyPurchasOrders() {
        return filteredListOfAllReadyPurchasOrders;
    }

    /**
     * @param filteredListOfAllReadyPurchasOrders the filteredListOfAllReadyPurchasOrders to set
     */
    public void setFilteredListOfAllReadyPurchasOrders(List<PurchaseOrderEntity> filteredListOfAllReadyPurchasOrders) {
        this.filteredListOfAllReadyPurchasOrders = filteredListOfAllReadyPurchasOrders;
    }
    
    
}
