/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "purchaseOrderProgressManagementManagedBean")
@ViewScoped
public class PurchaseOrderProgressManagementManagedBean implements Serializable {

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;

    @Inject
    private ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean;

    @Inject
    private ReadyToShipManagedBean readyToShipManagedBean;

    private List<PurchaseOrderEntity> listOfAllPurchaseOrders;

    private List<PurchaseOrderEntity> filteredListOfAllPurchaseOrders;

    /**
     * Creates a new instance of PurchaseOrderManagementManagedBean
     */
    public PurchaseOrderProgressManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initialiseState();
    }

    public void initialiseState() {
        setFilteredListOfAllPurchaseOrders(new ArrayList<>());
        setListOfAllPurchaseOrders(purchaseOrderEntitySessionBean.retrieveProgressAllPurchaseOrders());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PurchaseOrderEntityConverter_purchaseOrderEntities", listOfAllPurchaseOrders);
        System.out.println("TESTING");
        for (PurchaseOrderEntity po : listOfAllPurchaseOrders) {
            System.out.println("REF : " + po.getReferenceNumber());
            for (PurchaseOrderLineItemEntity poli : po.getPurchaseOrderLineItems()) {
                System.out.println("---" + poli.getSerialNumber());
            }
            System.out.println("TOTAL SIZE: " + po.getPurchaseOrderLineItems().size());
        }
    }

    public void refundOrder(ActionEvent event) {
        PurchaseOrderEntity po = (PurchaseOrderEntity) event.getComponent().getAttributes().get("orderToRefund");
        try {
            purchaseOrderEntitySessionBean.refundPurchaseOrder(po.getPurchaseOrderEntityId());
        } catch (PurchaseOrderEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        initialiseState();
    }

    public void changeToReady(ActionEvent event) {
        PurchaseOrderEntity po = (PurchaseOrderEntity) event.getComponent().getAttributes().get("orderToReady");

        purchaseOrderEntitySessionBean.changeToReady(po.getPurchaseOrderEntityId());
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
     * @return the viewPurchaseOrderManagedBean
     */
    public ViewPurchaseOrderManagedBean getViewPurchaseOrderManagedBean() {
        System.out.println("============= GETTING THE VIEW PO MANAGED BEAN ===================");
        return viewPurchaseOrderManagedBean;
    }

    /**
     * @param viewPurchaseOrderManagedBean the viewPurchaseOrderManagedBean to
     * set
     */
    public void setViewPurchaseOrderManagedBean(ViewPurchaseOrderManagedBean viewPurchaseOrderManagedBean) {
        this.viewPurchaseOrderManagedBean = viewPurchaseOrderManagedBean;
    }

    /**
     * @return the readyToShipManagedBean
     */
    public ReadyToShipManagedBean getReadyToShipManagedBean() {
        return readyToShipManagedBean;
    }

    /**
     * @param readyToShipManagedBean the readyToShipManagedBean to set
     */
    public void setReadyToShipManagedBean(ReadyToShipManagedBean readyToShipManagedBean) {
        this.readyToShipManagedBean = readyToShipManagedBean;
    }

    public List<PurchaseOrderEntity> getFilteredListOfAllPurchaseOrders() {
        return filteredListOfAllPurchaseOrders;
    }

    public void setFilteredListOfAllPurchaseOrders(List<PurchaseOrderEntity> filteredListOfAllPurchaseOrders) {
        this.filteredListOfAllPurchaseOrders = filteredListOfAllPurchaseOrders;
    }

}
