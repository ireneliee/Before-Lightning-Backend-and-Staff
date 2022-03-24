/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import entity.PurchaseOrderEntity;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author irene
 */
@Local
public interface StatisticSessionBeanLocal {

    public Map<ProductEntity, Integer> retrieveProductPurchased();

    public Map<String, List<PurchaseOrderEntity>> retrievePurchaseOrdersBasedOnMonth();
    
}
