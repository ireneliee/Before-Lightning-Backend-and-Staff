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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.PurchaseOrderEntityNotFoundException;

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
    private List<PurchaseOrderEntity> filteredListOfAllPurchaseOrders;

    /**
     * Creates a new instance of PurchaseOrderManagementManagedBean
     */
    public PurchaseOrderReadyManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initialiseState();
    }

    public void initialiseState() {
        setListOfAllPurchaseOrders(purchaseOrderEntitySessionBean.retrieveReadyAllPurchaseOrders());
        System.out.println("list size" + listOfAllPurchaseOrders.size());
        setFilteredListOfAllPurchaseOrders(new ArrayList<>());

    }

    public void completeOrder(ActionEvent event) {
        System.out.println("complete order was called");
        PurchaseOrderEntity po = (PurchaseOrderEntity)event.getComponent().getAttributes().get("orderToComplete");
        try {
            purchaseOrderEntitySessionBean.changeToComplete(po.getPurchaseOrderEntityId());
        } catch (PurchaseOrderEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        initialiseState();
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
     * @return the filteredListOfAllPurchaseOrders
     */
    public List<PurchaseOrderEntity> getFilteredListOfAllPurchaseOrders() {
        return filteredListOfAllPurchaseOrders;
    }

    /**
     * @param filteredListOfAllPurchaseOrders the filteredListOfAllPurchaseOrders
     * to set
     */
    public void setFilteredListOfAllPurchaseOrders(List<PurchaseOrderEntity> filteredListOfAllPurchaseOrders) {
        this.filteredListOfAllPurchaseOrders = filteredListOfAllPurchaseOrders;
    }

    /**
     * @return the viewPurchaseOrderManagedBean
     */
    public ViewPurchaseOrderManagedBean getViewPurchaseOrderManagedBean() {
        return viewPurchaseOrderManagedBean;
    }

    /**
     * @param viewPurchaseOrderManagedBean the viewPurchaseOrderManagedBean to
     * set
     */
    public void setViewPurchaseOrderManagedBean(ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean) {
        this.viewPurchaseOrderManagedBean = viewPurchaseOrderManagedBean;
    }

}
