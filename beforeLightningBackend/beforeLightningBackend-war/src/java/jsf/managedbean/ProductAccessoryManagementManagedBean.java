/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "productAccessoryManagementManagedBean")
@ViewScoped
public class ProductAccessoryManagementManagedBean implements Serializable {

    @EJB(name = "PartChoiceEntitySessionBeanLocal")
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB(name = "AccessoryEntitySessionBeanLocal")
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    private List<ProductEntity> listOfProductEntities;
    private List<ProductEntity> filteredListOfProductEntities;

    private List<AccessoryItemEntity> listOfAccessoryItemEntities;
    private List<AccessoryItemEntity> filteredListOfAccessoryItemEntities;

    /**
     * Creates a new instance of ProductManagementManagedBean
     */
    public ProductAccessoryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        listOfProductEntities = productEntitySessionBeanLocal.retrieveAllProductEntities();
        listOfAccessoryItemEntities = accessoryItemEntitySessionBeanLocal.retrieveAllAccessoryItemEntities();
    }

    public List<ProductEntity> getListOfProductEntities() {
        return listOfProductEntities;
    }

    public void setListOfProductEntities(List<ProductEntity> listOfProductEntities) {
        this.listOfProductEntities = listOfProductEntities;
    }

    public List<AccessoryItemEntity> getListOfAccessoryItemEntities() {
        return listOfAccessoryItemEntities;
    }

    public void setListOfAccessoryItemEntities(List<AccessoryItemEntity> listOfAccessoryItemEntities) {
        this.listOfAccessoryItemEntities = listOfAccessoryItemEntities;
    }

    public List<ProductEntity> getFilteredListOfProductEntities() {
        return filteredListOfProductEntities;
    }

    public void setFilteredListOfProductEntities(List<ProductEntity> filteredListOfProductEntities) {
        this.filteredListOfProductEntities = filteredListOfProductEntities;
    }

    public List<AccessoryItemEntity> getFilteredListOfAccessoryItemEntities() {
        return filteredListOfAccessoryItemEntities;
    }

    public void setFilteredListOfAccessoryItemEntities(List<AccessoryItemEntity> filteredListOfAccessoryItemEntities) {
        this.filteredListOfAccessoryItemEntities = filteredListOfAccessoryItemEntities;
    }

}
