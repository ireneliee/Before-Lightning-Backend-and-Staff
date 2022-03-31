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

    public List<PurchaseOrderEntity> retrieveProgressAllPurchaseOrders();

    public List<PurchaseOrderEntity> retrieveReadyAllPurchaseOrders();

    public List<PurchaseOrderEntity> retrieveCompletedAllPurchaseOrders();

    public List<PurchaseOrderEntity> retrieveRefundedAllPurchaseOrders();

    public void changeToReady(Long purchaseOrderId);

    public void changeToComplete(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException;

    public void changeToRefund(Long purchaseOrderId);

    public void refundPurchaseOrder(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException;
    
}
