package ws.datamodel;

import entity.DeliverySlotEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;

public class CreatePurchaseOrderReq {

    private List<PurchaseOrderLineItemEntity> listOfLineItems;
    private DeliverySlotEntity deliverySlot;

    public CreatePurchaseOrderReq() {
    }

    public CreatePurchaseOrderReq(List<PurchaseOrderLineItemEntity> listOfLineItems, DeliverySlotEntity deliverySlot) {
        this.listOfLineItems = listOfLineItems;
        this.deliverySlot = deliverySlot;
    }

    public List<PurchaseOrderLineItemEntity> getListOfLineItems() {
        return listOfLineItems;
    }

    public void setListOfLineItems(List<PurchaseOrderLineItemEntity> listOfLineItems) {
        this.listOfLineItems = listOfLineItems;
    }

    public DeliverySlotEntity getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(DeliverySlotEntity deliverySlot) {
        this.deliverySlot = deliverySlot;
    }
}
