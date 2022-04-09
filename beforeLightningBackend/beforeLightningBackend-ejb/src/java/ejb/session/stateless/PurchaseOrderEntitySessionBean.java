/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.PurchaseOrderLineItemTypeEnum;
import util.enumeration.PurchaseOrderStatusEnum;
import util.exception.CreateNewPurchaseOrderException;
import util.exception.MemberEntityNotFoundException;
import util.exception.PurchaseOrderEntityNotFoundException;

/**
 *
 * @author srinivas
 */
@Stateless
public class PurchaseOrderEntitySessionBean implements PurchaseOrderEntitySessionBeanLocal {

    @EJB
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean;

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    public PurchaseOrderEntitySessionBean() {
    }

    @Override
    public PurchaseOrderEntity createNewPurchaseOrder(Long memberId, PurchaseOrderEntity newPurchaseOrderEntity) throws MemberEntityNotFoundException, CreateNewPurchaseOrderException {
        if (newPurchaseOrderEntity != null) {

            MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByMemberEntityId(memberId);
            newPurchaseOrderEntity.setMember(memberEntity);
            memberEntity.getPurchaseOrders().add(newPurchaseOrderEntity);

            em.persist(newPurchaseOrderEntity);

            for (PurchaseOrderLineItemEntity p : newPurchaseOrderEntity.getPurchaseOrderLineItems()) {
                em.persist(p);
                if (p.getPurchaseOrderLineItemTypeEnum() == PurchaseOrderLineItemTypeEnum.ACCESSORY) {
                    Integer quantity = p.getAccessoryItemEntity().getQuantityOnHand();
                    Integer boughtQuantity = p.getQuantity();
                    p.getAccessoryItemEntity().setQuantityOnHand(quantity - boughtQuantity);
                } else {
                    for (PartChoiceEntity managedpc : p.getPartChoiceEntities()) {
                        Integer quantity = managedpc.getQuantityOnHand();
                        Integer boughtQuantity = p.getQuantity();
                        managedpc.setQuantityOnHand(quantity - boughtQuantity);
                    }

                }
            }

            em.flush();

            return newPurchaseOrderEntity;

        } else {
            throw new CreateNewPurchaseOrderException("Sale transaction information not provided");
        }
    }

    public void refundPurchaseOrder(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException {
        PurchaseOrderEntity po = retrievePurchaseOrderEntityByPurchaseOrderEntityId(purchaseOrderId);
        po.setPurchaseOrderStatus(PurchaseOrderStatusEnum.REFUNDED);
        for (PurchaseOrderLineItemEntity p : po.getPurchaseOrderLineItems()) {
            if (p.getPurchaseOrderLineItemTypeEnum() == PurchaseOrderLineItemTypeEnum.ACCESSORY) {
                Integer quantity = p.getAccessoryItemEntity().getQuantityOnHand();
                Integer boughtQuantity = p.getQuantity();
                p.getAccessoryItemEntity().setQuantityOnHand(quantity + boughtQuantity);
            } else {
                for (PartChoiceEntity managedpc : p.getPartChoiceEntities()) {
                    Integer quantity = managedpc.getQuantityOnHand();
                    Integer boughtQuantity = p.getQuantity();
                    managedpc.setQuantityOnHand(quantity + boughtQuantity);
                }
            }
        }

    }
    
    @Override
    public List<PurchaseOrderEntity> retrievePurchaseOrderByUsername(String username) {
        String queryInString = "SELECT po FROM PurchaseOrderEntity po WHERE po.member = :iMemberUsername";
        Query query = em.createQuery(queryInString);
        query.setParameter("iMemberUsername", username);
        return query.getResultList();
    }

    @Override
    public List<PurchaseOrderEntity> retrieveAllPurchaseOrders() {
        Query query = em.createQuery("SELECT po FROM PurchaseOrderEntity po");
        List<PurchaseOrderEntity> allItems = query.getResultList();
        for (PurchaseOrderEntity item : allItems) {

            item.getPurchaseOrderLineItems().size();
//            System.out.println(item.getPurchaseOrderLineItems().size());
        }
        return allItems;
    }

    @Override
    public List<PurchaseOrderEntity> retrieveProgressAllPurchaseOrders() {
        Query query = em.createQuery("SELECT po FROM PurchaseOrderEntity po WHERE po.purchaseOrderStatus = :statusName");
        query.setParameter("statusName", PurchaseOrderStatusEnum.IN_PROGRESS);

        List<PurchaseOrderEntity> allItems = query.getResultList();
        for (PurchaseOrderEntity item : allItems) {
            item.getMember();
            item.getPurchaseOrderLineItems().size();
//            System.out.println(item.getPurchaseOrderLineItems().size());
        }
        return allItems;
    }

    @Override
    public List<PurchaseOrderEntity> retrieveReadyAllPurchaseOrders() {
        Query query = em.createQuery("SELECT po FROM PurchaseOrderEntity po WHERE po.purchaseOrderStatus = :statusName");
        query.setParameter("statusName", PurchaseOrderStatusEnum.READY_FOR_SHIPMENT);

        List<PurchaseOrderEntity> allItems = query.getResultList();
        for (PurchaseOrderEntity item : allItems) {

            item.getPurchaseOrderLineItems().size();
//            System.out.println(item.getPurchaseOrderLineItems().size());
        }
        return allItems;
    }

    @Override
    public List<PurchaseOrderEntity> retrieveCompletedAllPurchaseOrders() {
        Query query = em.createQuery("SELECT po FROM PurchaseOrderEntity po WHERE po.purchaseOrderStatus = :statusName");
        query.setParameter("statusName", PurchaseOrderStatusEnum.COMPLETE);

        List<PurchaseOrderEntity> allItems = query.getResultList();
        for (PurchaseOrderEntity item : allItems) {

            item.getPurchaseOrderLineItems().size();
//            System.out.println(item.getPurchaseOrderLineItems().size());
        }
        return allItems;
    }

    public List<PurchaseOrderEntity> retrieveRefundedAllPurchaseOrders() {
        Query query = em.createQuery("SELECT po FROM PurchaseOrderEntity po WHERE po.purchaseOrderStatus = :statusName");
        query.setParameter("statusName", PurchaseOrderStatusEnum.REFUNDED);

        List<PurchaseOrderEntity> allItems = query.getResultList();
        for (PurchaseOrderEntity item : allItems) {

            item.getPurchaseOrderLineItems().size();
//            System.out.println(item.getPurchaseOrderLineItems().size());
        }
        return allItems;
    }

    @Override
    public PurchaseOrderEntity retrievePurchaseOrderEntityByPurchaseOrderEntityId(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException {
        PurchaseOrderEntity purchaseOrderEntity = em.find(PurchaseOrderEntity.class, purchaseOrderId);

        if (purchaseOrderEntity != null) {
            purchaseOrderEntity.getPurchaseOrderLineItems().size();

            return purchaseOrderEntity;
        } else {
            throw new PurchaseOrderEntityNotFoundException("Sale Transaction ID " + purchaseOrderId + " does not exist!");
        }
    }

    public void changeToReady(Long purchaseOrderId) {
        PurchaseOrderEntity purchaseOrderEntity = em.find(PurchaseOrderEntity.class, purchaseOrderId);
        purchaseOrderEntity.setPurchaseOrderStatus(PurchaseOrderStatusEnum.READY_FOR_SHIPMENT);

    }

    public void changeToComplete(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException{
        PurchaseOrderEntity purchaseOrderEntity = retrievePurchaseOrderEntityByPurchaseOrderEntityId(purchaseOrderId);
        purchaseOrderEntity.setPurchaseOrderStatus(PurchaseOrderStatusEnum.COMPLETE);

    }

    public void changeToRefund(Long purchaseOrderId) {
        PurchaseOrderEntity purchaseOrderEntity = em.find(PurchaseOrderEntity.class, purchaseOrderId);
        purchaseOrderEntity.setPurchaseOrderStatus(PurchaseOrderStatusEnum.REFUNDED);

    }

    @Override
    public void updatePurchaseOrder(PurchaseOrderEntity purchaseOrderEntity) {
        em.merge(purchaseOrderEntity);
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrderEntity purchaseOrderEntity) {
        throw new UnsupportedOperationException();
    }
}
