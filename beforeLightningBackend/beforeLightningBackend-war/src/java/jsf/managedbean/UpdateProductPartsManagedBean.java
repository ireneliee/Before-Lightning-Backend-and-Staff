/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.PartEntity;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "updateProductPartsManagedBean")
@ViewScoped
public class UpdateProductPartsManagedBean implements Serializable {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "PartEntitySessionBeanLocal")
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;
    
    @Inject
    private ProductManagementManagedBean productManagementManagedBean;

    /**
     * Creates a new instance of UpdateProductPartsManagedBean
     */
    private ProductEntity productEntityPartsToUpdate;
    private List<PartEntity> listOfAvailablePartEntities;
    private List<PartEntity> newListOfSelectedPartEntities;

    public UpdateProductPartsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();

    }

    public void initializeState() {
        productEntityPartsToUpdate = new ProductEntity();
        listOfAvailablePartEntities = partEntitySessionBeanLocal.retrieveAllPartEntities();
        listOfAvailablePartEntities.removeIf(part -> part.getPartName().equals("Chassis"));
        newListOfSelectedPartEntities = new ArrayList<>();
    }

    public void updateProductParts(ActionEvent event) {
        try {
            productEntitySessionBeanLocal.updateProductEntitiyWithNewParts(newListOfSelectedPartEntities, productEntityPartsToUpdate);
        } catch (UpdateProductEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Update Product's Parts ", null));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated Product's Parts", null));
        initializeState();
        productManagementManagedBean.initializeState();
    }

    /**
     * @return the productEntityPartsToUpdate
     */
    public ProductEntity getProductEntityPartsToUpdate() {
        return productEntityPartsToUpdate;
    }

    /**
     * @param productEntityPartsToUpdate the productEntityPartsToUpdate to set
     */
    public void setProductEntityPartsToUpdate(ProductEntity productEntityPartsToUpdate) {
        this.productEntityPartsToUpdate = productEntityPartsToUpdate;
    }

    /**
     * @return the listOfAvailablePartEntities
     */
    public List<PartEntity> getListOfAvailablePartEntities() {
        return listOfAvailablePartEntities;
    }

    /**
     * @param listOfAvailablePartEntities the listOfAvailablePartEntities to set
     */
    public void setListOfAvailablePartEntities(List<PartEntity> listOfAvailablePartEntities) {
        this.listOfAvailablePartEntities = listOfAvailablePartEntities;
    }

    /**
     * @return the newListOfSelectedPartEntities
     */
    public List<PartEntity> getNewListOfSelectedPartEntities() {
        return newListOfSelectedPartEntities;
    }

    /**
     * @param newListOfSelectedPartEntities the newListOfSelectedPartEntities to
     * set
     */
    public void setNewListOfSelectedPartEntities(List<PartEntity> newListOfSelectedPartEntities) {
        this.newListOfSelectedPartEntities = newListOfSelectedPartEntities;
    }

}
