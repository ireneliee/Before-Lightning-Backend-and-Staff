/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author irene
 */
@Stateless
public class StatisticSessionBean implements StatisticSessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    public StatisticSessionBean() {
    
    }
    public Map<ProductEntity, Integer> retrieveProductPurchased() {
        Map<ProductEntity, Integer> productsPurchased = new HashMap<>();
        Query queryToRetrieveAllProduct = em.createQuery("SELECT p FROM ProductEntity p");
        List<ProductEntity> listOfProducts = queryToRetrieveAllProduct.getResultList();
        Query queryToRetrieveAllPurchaseOrderLineItem = em.createQuery("SELECT p FROM PurchaseOrderLineItemEntity p");
        List<PurchaseOrderLineItemEntity> listOfPurchaseOrders = queryToRetrieveAllPurchaseOrderLineItem.getResultList();
        Integer quantity = 0;
        
        for(ProductEntity p: listOfProducts) {
            quantity = 0;
            for(PurchaseOrderLineItemEntity po: listOfPurchaseOrders) {
                if((po.getProductEntity() != null) && po.getProductEntity().equals(p)) {
                    Integer purchasedQuantity = po.getQuantity();
                    quantity = quantity + purchasedQuantity;
                }
                
                productsPurchased.put(p, quantity);
                 
            }
        }
        return productsPurchased;
    }
    public void persist(Object object) {
        em.persist(object);
    }
    
    
}
