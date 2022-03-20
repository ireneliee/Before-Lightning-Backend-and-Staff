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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import util.exception.DeleteProductEntityException;
import util.exception.ProductEntityNotFoundException;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Named(value = "deleteProductManagedBean")
@ViewScoped
public class DeleteProductManagedBean implements Serializable {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @Inject
    private ProductManagementManagedBean productManagementManagedBean;

    private ProductEntity productEntityToDelete;

    public DeleteProductManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        productEntityToDelete = null;
    }

    public void deleteProduct(ActionEvent event) {
        try {
            productEntitySessionBeanLocal.deleteProductEntity(productEntityToDelete.getProductEntityId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Deleted Product!", null));

        } catch (ProductEntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product Cannot Be Found", null));
        } catch (DeleteProductEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Delete Product!" + ex.getMessage(), null));
        }
        productManagementManagedBean.initializeState();
    }

    public ProductEntity getProductEntityToDelete() {
        return productEntityToDelete;
    }

    public void setProductEntityToDelete(ProductEntity productEntityToDelete) {
        this.productEntityToDelete = productEntityToDelete;
    }

}
