/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PurchaseOrderEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPurchaseOrderException;
import util.exception.MemberEntityNotFoundException;
import util.exception.PurchaseOrderEntityNotFoundException;

/**
 *
 * @author srinivas
 */
@Local
public interface PurchaseOrderEntitySessionBeanLocal {

    public void deletePurchaseOrder(PurchaseOrderEntity purchaseOrderEntity);

    public void updatePurchaseOrder(PurchaseOrderEntity purchaseOrderEntity);

    public List<PurchaseOrderEntity> retrieveAllPurchaseOrders();

    public PurchaseOrderEntity createNewPurchaseOrder(Long memberId, PurchaseOrderEntity newPurchaseOrderEntity) throws MemberEntityNotFoundException, CreateNewPurchaseOrderException;

    public PurchaseOrderEntity retrievePurchaseOrderEntityByPurchaseOrderEntityId(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException;
    
}
