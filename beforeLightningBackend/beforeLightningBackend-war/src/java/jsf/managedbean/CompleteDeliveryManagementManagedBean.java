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
@Named(value = "completeDeliveryManagementManagedBean")
@ViewScoped
public class CompleteDeliveryManagementManagedBean implements Serializable {

    @EJB
    private DeliverySlotSessionBeanLocal deliverySlotSessionBean;
    private List<DeliverySlotEntity> listOfCompleteDeliveries;
    private List<DeliverySlotEntity> filteredListOfCompleteDeliveries;

    /**
     * Creates a new instance of CompleteDeliveryManagementManagedBean
     */
    public CompleteDeliveryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        setListOfCompleteDeliveries(deliverySlotSessionBean.retrieveAllCompletedDelivery());
    }

    /**
     * @return the listOfCompleteDeliveries
     */
    public List<DeliverySlotEntity> getListOfCompleteDeliveries() {
        return listOfCompleteDeliveries;
    }

    /**
     * @param listOfCompleteDeliveries the listOfCompleteDeliveries to set
     */
    public void setListOfCompleteDeliveries(List<DeliverySlotEntity> listOfCompleteDeliveries) {
        this.listOfCompleteDeliveries = listOfCompleteDeliveries;
    }

    /**
     * @return the filteredListOfCompleteDeliveries
     */
    public List<DeliverySlotEntity> getFilteredListOfCompleteDeliveries() {
        return filteredListOfCompleteDeliveries;
    }

    /**
     * @param filteredListOfCompleteDeliveries the filteredListOfCompleteDeliveries to set
     */
    public void setFilteredListOfCompleteDeliveries(List<DeliverySlotEntity> filteredListOfCompleteDeliveries) {
        this.filteredListOfCompleteDeliveries = filteredListOfCompleteDeliveries;
    }

}
