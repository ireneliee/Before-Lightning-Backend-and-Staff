/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;
    
    public ProductEntitySessionBean() {
    }
    
    
    public ProductEntity createNewProduct(ProductEntity newProduct) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException {
        // not done yet
        // create new Product here 
        // need to persist the part & part choices here as well
        // persist product first (without part and part choices, then persist part, then persist part choices)
        
        return null;
    
    }

    
    
}
