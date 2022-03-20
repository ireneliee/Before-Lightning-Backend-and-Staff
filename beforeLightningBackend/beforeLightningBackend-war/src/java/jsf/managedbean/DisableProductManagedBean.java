/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.ProductEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
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
@Named(value = "disableProductManagedBean")
@ViewScoped
public class DisableProductManagedBean implements Serializable {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @Inject
    private ProductManagementManagedBean productManagementManagedBean;

    private ProductEntity productEntityToDisable;

    public DisableProductManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeState();
    }

    public void initializeState() {
        productEntityToDisable = null;
    }

    public void disableProduct(ActionEvent event) {
        Boolean initialState = productEntityToDisable.getIsDisabled();
        try {
            productEntitySessionBeanLocal.toggleDisableProductEntity(productEntityToDisable.getProductEntityId());
        } catch (UpdateProductEntityException ex) {
            if (initialState == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Enable Product", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable To Disable Product", null));
            }
        }
        if (initialState == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Enabled Product!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Disabled Product!", null));
        }
        productManagementManagedBean.initializeState();
    }

    public ProductEntity getProductEntityToDisable() {
        return productEntityToDisable;
    }

    public void setProductEntityToDisable(ProductEntity productEntityToDisable) {
        this.productEntityToDisable = productEntityToDisable;
    }

}
