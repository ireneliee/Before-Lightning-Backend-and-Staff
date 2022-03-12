/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductTypeSessionBeanLocal;
import entity.ProductTypeEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

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

    
    public ProductTypeManagementManagedBean() {
        newProductTypeEntity = new ProductTypeEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        listOfProductTypeEntities = productTypeSessionBeanLocal.retrieveAllProductTypeEntities();
    }
    
}
