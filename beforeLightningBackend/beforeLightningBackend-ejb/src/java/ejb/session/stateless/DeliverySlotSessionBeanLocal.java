/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DeliverySlotEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AddressEntityNotFoundException;
import util.exception.DeliverySlotEntityNotFoundException;
import util.exception.PurchaseOrderEntityNotFoundException;

/**
 *
 * @author srinivas
 */
@Local
public interface DeliverySlotSessionBeanLocal {

    public Long createOutStoreDelivery(Long purchaseOrderId, Long AddressId, DeliverySlotEntity delivery) throws AddressEntityNotFoundException, PurchaseOrderEntityNotFoundException;

    public Long createInStoreDelivery(Long purchaseOrderId, DeliverySlotEntity delivery) throws PurchaseOrderEntityNotFoundException;

    public DeliverySlotEntity retrieveDeliverySlotByDeliverySlotId(Long DeliveryId) throws DeliverySlotEntityNotFoundException;

    public List<DeliverySlotEntity> retrieveAllOutStoreDelivery();

    public List<DeliverySlotEntity> retrieveAllInStoreDelivery();

    public List<DeliverySlotEntity> retrieveAllCompletedDelivery();

    public void completeDelivery(Long deliveryId) throws DeliverySlotEntityNotFoundException;
    
}
