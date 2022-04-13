/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.DeliverySlotEntity;
import entity.PurchaseOrderEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.DeliveryStatusEnum;
import util.enumeration.PurchaseOrderStatusEnum;
import util.exception.AddressEntityNotFoundException;
import util.exception.DeliverySlotEntityNotFoundException;
import util.exception.PurchaseOrderEntityNotFoundException;

/**
 *
 * @author srinivas
 */
@Stateless
public class DeliverySlotSessionBean implements DeliverySlotSessionBeanLocal {

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBean;
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Long createOutStoreDelivery(Long purchaseOrderId, Long AddressId, DeliverySlotEntity delivery) throws AddressEntityNotFoundException, PurchaseOrderEntityNotFoundException {
        PurchaseOrderEntity po = purchaseOrderEntitySessionBean.retrievePurchaseOrderEntityByPurchaseOrderEntityId(purchaseOrderId);
        AddressEntity add = memberEntitySessionBean.retrieveAddressEntityByAddressEntityId(AddressId);
        delivery.setDeliveryOrder(po);
        delivery.setAddress(add);

        em.persist(delivery);
        em.flush();
        return delivery.getDeliverySlotId();
    }

    public Long createInStoreDelivery(Long purchaseOrderId, DeliverySlotEntity delivery) throws PurchaseOrderEntityNotFoundException {
        PurchaseOrderEntity po = purchaseOrderEntitySessionBean.retrievePurchaseOrderEntityByPurchaseOrderEntityId(purchaseOrderId);
        delivery.setDeliveryOrder(po);

        em.persist(delivery);
        em.flush();
        return delivery.getDeliverySlotId();
    }

    public DeliverySlotEntity retrieveDeliverySlotByDeliverySlotId(Long DeliveryId) throws DeliverySlotEntityNotFoundException {
        DeliverySlotEntity item = em.find(DeliverySlotEntity.class, DeliveryId);
        if (item != null) {
            item.getAddress();
            item.getDeliveryOrder();
            return item;
        } else {
            throw new DeliverySlotEntityNotFoundException("DeliverySlotId ID " + DeliveryId + " does not exist!");
        }

    }
        public List<DeliverySlotEntity> retrieveDeliverySlotsByAddressId(Long addressId)  {
        Query query = em.createQuery("SELECT d FROM DeliverySlotEntity d WHERE d.address.addressEntityId = :addressId");
        query.setParameter("addressId", addressId);
        List<DeliverySlotEntity> allItems = query.getResultList();
        for (DeliverySlotEntity item : allItems) {

            item.getAddress();
            item.getDeliveryOrder();
        }
        return allItems;

    }

    public List<DeliverySlotEntity> retrieveAllOutStoreDelivery() {
        Query query = em.createQuery("SELECT d FROM DeliverySlotEntity d WHERE d.deliveryStatus = :statusName");
        query.setParameter("statusName", DeliveryStatusEnum.OUTSTORE);

        List<DeliverySlotEntity> allItems = query.getResultList();
        for (DeliverySlotEntity item : allItems) {

            item.getAddress();
            item.getDeliveryOrder();
        }
        return allItems;

    }

    public List<DeliverySlotEntity> retrieveAllInStoreDelivery() {
        Query query = em.createQuery("SELECT d FROM DeliverySlotEntity d WHERE d.deliveryStatus = :statusName");
        query.setParameter("statusName", DeliveryStatusEnum.INSTORE);

        List<DeliverySlotEntity> allItems = query.getResultList();
        for (DeliverySlotEntity item : allItems) {

            item.getAddress();
            item.getDeliveryOrder();
        }
        return allItems;

    }

    public List<DeliverySlotEntity> retrieveAllCompletedDelivery() {
        Query query = em.createQuery("SELECT d FROM DeliverySlotEntity d WHERE d.deliveryStatus = :statusName");
        query.setParameter("statusName", DeliveryStatusEnum.COMPLETE);

        List<DeliverySlotEntity> allItems = query.getResultList();
        for (DeliverySlotEntity item : allItems) {

            item.getAddress();
            item.getDeliveryOrder();
        }
        return allItems;

    }

    public void completeDelivery(Long deliveryId) throws DeliverySlotEntityNotFoundException {
        DeliverySlotEntity delivery = retrieveDeliverySlotByDeliverySlotId(deliveryId);
        PurchaseOrderEntity po = delivery.getDeliveryOrder();

        po.setPurchaseOrderStatus(PurchaseOrderStatusEnum.COMPLETE);
        delivery.setTimeOfArrival(LocalDateTime.now());
        delivery.setDeliveryStatus(DeliveryStatusEnum.COMPLETE);

    }

}
