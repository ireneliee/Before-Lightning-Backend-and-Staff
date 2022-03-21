/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "liveProductManagementManagedBean")
@ViewScoped
public class LiveProductManagementManagedBean implements Serializable {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    /**
     * Creates a new instance of LiveProductManagementManagedBean
     */
    public LiveProductManagementManagedBean() {
    }
    private List<ProductEntity> listOfLiveProductEntities;
    private List<ProductEntity> filteredListOfLiveProductEntities;

    @PostConstruct
    public void postConstruct() {
        listOfLiveProductEntities = productEntitySessionBeanLocal.retrieveAllProductEntitiesThatCanSell();
        filteredListOfLiveProductEntities = new ArrayList<>();
    }

    /**
     * @return the listOfLiveProductEntities
     */
    public List<ProductEntity> getListOfLiveProductEntities() {
        return listOfLiveProductEntities;
    }

    /**
     * @param listOfLiveProductEntities the listOfLiveProductEntities to set
     */
    public void setListOfLiveProductEntities(List<ProductEntity> listOfLiveProductEntities) {
        this.listOfLiveProductEntities = listOfLiveProductEntities;
    }

    /**
     * @return the filteredListOfLiveProductEntities
     */
    public List<ProductEntity> getFilteredListOfLiveProductEntities() {
        return filteredListOfLiveProductEntities;
    }

    /**
     * @param filteredListOfLiveProductEntities the filteredListOfLiveProductEntities to set
     */
    public void setFilteredListOfLiveProductEntities(List<ProductEntity> filteredListOfLiveProductEntities) {
        this.filteredListOfLiveProductEntities = filteredListOfLiveProductEntities;
    }
}
