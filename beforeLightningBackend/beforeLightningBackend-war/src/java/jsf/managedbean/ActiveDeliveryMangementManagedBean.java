/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.DeliverySlotSessionBeanLocal;
import entity.DeliverySlotEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author srinivas
 */
@Named(value = "activeDeliveryMangementManagedBean")
@ViewScoped
public class ActiveDeliveryMangementManagedBean implements Serializable {

    @EJB
    private DeliverySlotSessionBeanLocal deliverySlotSessionBean;
    private List<DeliverySlotEntity> listOfInStoreDeliveries;
    private List<DeliverySlotEntity> filteredListOfInStoreDeliveries;
    
    private List<DeliverySlotEntity> listOfOutStoreDeliveries;
    private List<DeliverySlotEntity> filteredListOfOutStoreDeliveries;
    /**
     * Creates a new instance of ActiveDeliveryMangementManagedBean
     */
    public ActiveDeliveryMangementManagedBean() {
    }
    
        @PostConstruct
    public void postConstruct() {
        initializeState();
    }
    
    public void initializeState() {
        setListOfInStoreDeliveries(deliverySlotSessionBean.retrieveAllInStoreDelivery());
        setListOfOutStoreDeliveries(deliverySlotSessionBean.retrieveAllOutStoreDelivery());
    }

    /**
     * @return the listOfInStoreDeliveries
     */
    public List<DeliverySlotEntity> getListOfInStoreDeliveries() {
        return listOfInStoreDeliveries;
    }

    /**
     * @param listOfInStoreDeliveries the listOfInStoreDeliveries to set
     */
    public void setListOfInStoreDeliveries(List<DeliverySlotEntity> listOfInStoreDeliveries) {
        this.listOfInStoreDeliveries = listOfInStoreDeliveries;
    }

    /**
     * @return the filteredListOfInStoreDeliveries
     */
    public List<DeliverySlotEntity> getFilteredListOfInStoreDeliveries() {
        return filteredListOfInStoreDeliveries;
    }

    /**
     * @param filteredListOfInStoreDeliveries the filteredListOfInStoreDeliveries to set
     */
    public void setFilteredListOfInStoreDeliveries(List<DeliverySlotEntity> filteredListOfInStoreDeliveries) {
        this.filteredListOfInStoreDeliveries = filteredListOfInStoreDeliveries;
    }

    /**
     * @return the listOfOutStoreDeliveries
     */
    public List<DeliverySlotEntity> getListOfOutStoreDeliveries() {
        return listOfOutStoreDeliveries;
    }

    /**
     * @param listOfOutStoreDeliveries the listOfOutStoreDeliveries to set
     */
    public void setListOfOutStoreDeliveries(List<DeliverySlotEntity> listOfOutStoreDeliveries) {
        this.listOfOutStoreDeliveries = listOfOutStoreDeliveries;
    }

    /**
     * @return the filteredListOfOutStoreDeliveries
     */
    public List<DeliverySlotEntity> getFilteredListOfOutStoreDeliveries() {
        return filteredListOfOutStoreDeliveries;
    }

    /**
     * @param filteredListOfOutStoreDeliveries the filteredListOfOutStoreDeliveries to set
     */
    public void setFilteredListOfOutStoreDeliveries(List<DeliverySlotEntity> filteredListOfOutStoreDeliveries) {
        this.filteredListOfOutStoreDeliveries = filteredListOfOutStoreDeliveries;
    }
    
}
