/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.AddressEntity;
import entity.DeliverySlotEntity;
import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.ProductEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.DeliveryStatusEnum;
import util.enumeration.PurchaseOrderLineItemTypeEnum;
import util.enumeration.PurchaseOrderStatusEnum;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AddressEntityNotFoundException;
import util.exception.CreateNewPurchaseOrderException;
import util.exception.MemberEntityNotFoundException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.PurchaseOrderEntityNotFoundException;

/**
 *
 * @author srinivas
 */
@Stateless
public class PurchaseOrderEntitySessionBean implements PurchaseOrderEntitySessionBeanLocal {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "AccessoryItemEntitySessionBeanLocal")
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB(name = "DeliverySlotSessionBeanLocal")
    private DeliverySlotSessionBeanLocal deliverySlotSessionBeanLocal;

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

    private String generateUniqueReferenceNumber() {
        Double serialNumber = (Math.random() * 100000000);
        //need to check not in used
        return Integer.toString(serialNumber.intValue());
    }

    private Integer generateUniqueSerialNumber() {
        Double serialNumber = (Math.random() * 100000000);
        //need to check not in used
        return serialNumber.intValue();
    }
//DeliverySlotEntity deliverySlot, 
//LocalDateTime deliveryDate, String deliveryOption)
    @Override
    public PurchaseOrderEntity createNewPurchaseOrderRWS(String username, List<PurchaseOrderLineItemEntity> listOfLineItems, AddressEntity address, String deliveryType, BigDecimal totalPrice, LocalDateTime deliveryDate, String deliveryOption)  throws MemberEntityNotFoundException, CreateNewPurchaseOrderException {
        System.out.println("CALLED SESSION BEAN METHOD createNewPurchaseOrderRWS");

        if (!listOfLineItems.isEmpty()) {
            System.out.println("not empty list");
            MemberEntity member = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(username);
            System.out.println("Member found : " + member.getUsername());
            System.out.println("--------------------------------");
            BigDecimal finalPrice = totalPrice;
            if (deliveryType.equals("Express")) {
                finalPrice = finalPrice.add(new BigDecimal(10));
            }

            PurchaseOrderEntity newPurchaseOrderEntity = new PurchaseOrderEntity(generateUniqueReferenceNumber(), finalPrice, LocalDateTime.now(), PurchaseOrderStatusEnum.IN_PROGRESS);
            System.out.println("Created PO of ID: " + newPurchaseOrderEntity.getPurchaseOrderEntityId());
            System.out.println("Created PO of ref: " + newPurchaseOrderEntity.getReferenceNumber());

            for (PurchaseOrderLineItemEntity poli : listOfLineItems) {
                System.out.println("IN LOOP FOR UNMANAGED POLI");

                PurchaseOrderLineItemEntity newPoli = new PurchaseOrderLineItemEntity();

                if (poli.getPurchaseOrderLineItemTypeEnum() == PurchaseOrderLineItemTypeEnum.ACCESSORY) {
                    //Line item contains a accessory item
                    System.out.println("LINE ITEM IS FOR ACCESSORY");
                    newPoli.setPurchaseOrderLineItemTypeEnum(PurchaseOrderLineItemTypeEnum.ACCESSORY);
                    try {
                        AccessoryItemEntity managedAccItem = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemById(poli.getAccessoryItemEntity().getAccessoryItemEntityId());
                        newPoli.setAccessoryItemEntity(managedAccItem);
                        managedAccItem.setQuantityOnHand(managedAccItem.getQuantityOnHand() - poli.getQuantity());
                        System.out.println("SET QUANTITY PROPERLY IN ACCESSORY");
                    } catch (AccessoryItemEntityNotFoundException ex) {
                        System.out.println("Cant find the accessory Item of ID: " + poli.getAccessoryItemEntity().getAccessoryItemEntityId());
                    }
                    newPoli.setCosmeticImageLink("");
                    System.out.println("END OF MAKING LINE ITEM FOR ACCESSORY");

                } else {
                    try {
                        //Line item contains a build
                        System.out.println("LINE ITEM IS FOR BUILD");
                        ProductEntity managedProduct = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(poli.getProductEntity().getProductEntityId());
                        System.out.println("Product Found: " + managedProduct.getProductEntityId());

                        newPoli.setPurchaseOrderLineItemTypeEnum(PurchaseOrderLineItemTypeEnum.BUILD);
                        newPoli.setProductEntity(managedProduct);
                        newPoli.setCosmeticImageLink(poli.getCosmeticImageLink());

                        for (PartChoiceEntity pc : poli.getPartChoiceEntities()) {
                            System.out.println("Part CHoice to Find (UNMANAGED)" + pc.getPartChoiceEntityId());
                            try {
                                PartChoiceEntity managedPc = partChoiceEntitySessionBean.retrievePartChoiceEntityByPartChoiceEntityId(pc.getPartChoiceEntityId());
                                System.out.println("Found managed Part Choice: " + managedPc.getPartChoiceEntityId());
                                newPoli.getPartChoiceEntities().add(managedPc);
                                managedPc.setQuantityOnHand(managedPc.getQuantityOnHand() - poli.getQuantity());

                            } catch (PartChoiceEntityNotFoundException ex) {
                                System.out.println("CANT FIND PART CHOICE");
                            }
                            System.out.println("END OF SETTING QUANTITY FOR PC");
                        }
                        System.out.println("END OF MAKING LINE ITEM FOR BUILD");
                    } catch (ProductEntityNotFoundException ex) {
                        System.out.println("CANT FIND PRODUCT");
                    }
                }

                newPoli.setSerialNumber(generateUniqueSerialNumber());
                newPoli.setQuantity(poli.getQuantity());
                em.persist(newPoli);
                em.flush();
                System.out.println("Persisted LINE ITEM: " + newPoli.getPurchaseOrderLineItemEntityId());
                newPurchaseOrderEntity.getPurchaseOrderLineItems().add(newPoli);
                System.out.println("List of Line Item size: " + newPurchaseOrderEntity.getPurchaseOrderLineItems().size());
            }
            System.out.println("==========================================================");
            System.out.println("outside the whole loop trying to persist the PO");
            newPurchaseOrderEntity.setMember(member);
            member.getPurchaseOrders().add(newPurchaseOrderEntity);
            em.persist(newPurchaseOrderEntity);
            em.flush();
            System.out.println("List of Line Item size in managed PO: " + newPurchaseOrderEntity.getPurchaseOrderLineItems().size());
            
//            CREATING DELIVERY
            DeliveryStatusEnum delEnum;
            if (deliveryOption.equals("OUTSTORE")) {
                delEnum = DeliveryStatusEnum.OUTSTORE;
            } else {
                delEnum = DeliveryStatusEnum.INSTORE;
            }

            DeliverySlotEntity newDeliverySlot = new DeliverySlotEntity(delEnum, deliveryDate);
            if (newDeliverySlot.getDeliveryStatus() == DeliveryStatusEnum.OUTSTORE) {
                try {
                    deliverySlotSessionBeanLocal.createOutStoreDelivery(newPurchaseOrderEntity.getPurchaseOrderEntityId(), address.getAddressEntityId(), newDeliverySlot);
                } catch (AddressEntityNotFoundException | PurchaseOrderEntityNotFoundException ex) {
                    System.out.println("not working");
                }
            } else {
                try {
                    deliverySlotSessionBeanLocal.createInStoreDelivery(newPurchaseOrderEntity.getPurchaseOrderEntityId(), newDeliverySlot);
                } catch (PurchaseOrderEntityNotFoundException ex) {
                    System.out.println("not working 2");
                }
            }
            return newPurchaseOrderEntity;
        } else {
            throw new CreateNewPurchaseOrderException("UNABLE TO CREATE PURCHASE ORDER");
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
        String queryInString = "SELECT po FROM PurchaseOrderEntity po WHERE po.member.username = :iMemberUsername ORDER BY po.dateCreated DESC";
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

    public void changeToComplete(Long purchaseOrderId) throws PurchaseOrderEntityNotFoundException {
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
