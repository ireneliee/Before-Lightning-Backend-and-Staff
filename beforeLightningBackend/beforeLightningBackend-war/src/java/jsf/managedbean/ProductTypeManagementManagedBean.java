/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductTypeSessionBeanLocal;
import entity.ProductTypeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.DeleteProductTypeException;
import util.exception.InputDataValidationException;
import util.exception.ProductTypeEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Named(value = "productTypeManagementManagedBean")
@RequestScoped
public class ProductTypeManagementManagedBean {

    @EJB
    private ProductTypeSessionBeanLocal productTypeSessionBeanLocal;
    private ProductTypeEntity newProductTypeEntity;
    private List<ProductTypeEntity> listOfProductTypeEntities;
    private ProductTypeEntity selectedProductTypeToUpdate;
    private ProductTypeEntity selectedProductTypeToDelete;
    private String updatedName;

    public ProductTypeManagementManagedBean() {
        newProductTypeEntity = new ProductTypeEntity();
    }

    @PostConstruct
    public void postConstruct() {
        listOfProductTypeEntities = productTypeSessionBeanLocal.retrieveAllProductTypeEntities();
    }

    public ProductTypeSessionBeanLocal getProductTypeSessionBeanLocal() {
        return productTypeSessionBeanLocal;
    }

    public void createNewProductType(ActionEvent event) {
        try {
            ProductTypeEntity pt = productTypeSessionBeanLocal.createNewProductTypeEntity(newProductTypeEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product type created successfully (Product type ID: " + pt.getProductTypeEntityId() + ")", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product type: " + ex.getMessage(), null));
        }
    }

    public void updateProductName(ActionEvent event) {
        selectedProductTypeToUpdate = (ProductTypeEntity) event.getComponent().getAttributes().get("productTypeEntityToUpdate");
        if (updatedName != null && selectedProductTypeToUpdate != null) {
            try {
                selectedProductTypeToUpdate.setProductTypeName(updatedName);
                productTypeSessionBeanLocal.updateProductType(newProductTypeEntity);
            } catch (ProductTypeEntityNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the product type: " + ex.getMessage(), null));
            }
        }
    }
    
    public void deleteProductType(ActionEvent event) {
        selectedProductTypeToDelete = (ProductTypeEntity) event.getComponent().getAttributes().get("productTypeEntityToDelete");
        if(selectedProductTypeToDelete != null) {
            try {
                productTypeSessionBeanLocal.deleteProductType(selectedProductTypeToDelete.getProductTypeEntityId());
            } catch (DeleteProductTypeException|ProductTypeEntityNotFoundException ex) {
                Logger.getLogger(ProductTypeManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setProductTypeSessionBeanLocal(ProductTypeSessionBeanLocal productTypeSessionBeanLocal) {
        this.productTypeSessionBeanLocal = productTypeSessionBeanLocal;
    }

    public ProductTypeEntity getNewProductTypeEntity() {
        return newProductTypeEntity;
    }

    public void setNewProductTypeEntity(ProductTypeEntity newProductTypeEntity) {
        this.newProductTypeEntity = newProductTypeEntity;
    }

    public List<ProductTypeEntity> getListOfProductTypeEntities() {
        return listOfProductTypeEntities;
    }

    public void setListOfProductTypeEntities(List<ProductTypeEntity> listOfProductTypeEntities) {
        this.listOfProductTypeEntities = listOfProductTypeEntities;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    public ProductTypeEntity getSelectedProductTypeToUpdate() {
        return selectedProductTypeToUpdate;
    }

    public void setSelectedProductTypeToUpdate(ProductTypeEntity selectedProductTypeToUpdate) {
        this.selectedProductTypeToUpdate = selectedProductTypeToUpdate;
    }

    public ProductTypeEntity getSelectedProductTypeToDelete() {
        return selectedProductTypeToDelete;
    }

    public void setSelectedProductTypeToDelete(ProductTypeEntity selectedProductTypeToDelete) {
        this.selectedProductTypeToDelete = selectedProductTypeToDelete;
    }

}
