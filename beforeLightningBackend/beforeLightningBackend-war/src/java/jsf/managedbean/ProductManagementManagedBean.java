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
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "productManagementManagedBean")
@ViewScoped
public class ProductManagementManagedBean implements Serializable {

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

    @Inject
    private ViewProductManagedBean viewProductManagedBean;

    private List<ProductEntity> listOfProductEntities;
    private List<ProductEntity> filteredListOfProductEntities;

    /**
     * Creates a new instance of ProductManagementManagedBean
     */
    public ProductManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        listOfProductEntities = productEntitySessionBeanLocal.retrieveAllProductEntities();
    }

    public List<ProductEntity> getListOfProductEntities() {
        return listOfProductEntities;
    }

    public void setListOfProductEntities(List<ProductEntity> listOfProductEntities) {
        this.listOfProductEntities = listOfProductEntities;
    }

    public List<ProductEntity> getFilteredListOfProductEntities() {
        return filteredListOfProductEntities;
    }

    public void setFilteredListOfProductEntities(List<ProductEntity> filteredListOfProductEntities) {
        this.filteredListOfProductEntities = filteredListOfProductEntities;
    }

    public ViewProductManagedBean getViewProductManagedBean() {
        return viewProductManagedBean;
    }

    public void setViewProductManagedBean(ViewProductManagedBean viewProductManagedBean) {
        this.viewProductManagedBean = viewProductManagedBean;
    }
}
