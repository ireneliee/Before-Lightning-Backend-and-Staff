package ws.datamodel;

import entity.AddressEntity;
import entity.DeliverySlotEntity;
import entity.PurchaseOrderLineItemEntity;
import java.math.BigDecimal;
import java.util.List;

public class CreatePurchaseOrderReq {

    private List<PurchaseOrderLineItemEntity> listOfLineItems;
//    private DeliverySlotEntity deliverySlot;
    private String memberUsername;
    private AddressEntity address;
    private String deliveryType;
    private BigDecimal totalPrice;
    private Integer day;
    private Integer month;
    private Integer year;
    private Integer hours;
    private String deliveryOption;

    public CreatePurchaseOrderReq() {
    }
//DeliverySlotEntity deliverySlot,

    public CreatePurchaseOrderReq(List<PurchaseOrderLineItemEntity> listOfLineItems, String memberUsername, AddressEntity address, String deliveryType, BigDecimal totalPrice, Integer day, Integer month, Integer year, Integer hours, String deliveryOption) {
        this.listOfLineItems = listOfLineItems;
//        this.deliverySlot = deliverySlot;
        this.memberUsername = memberUsername;
        this.address = address;
        this.deliveryType = deliveryType;
        this.totalPrice = totalPrice;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hours = hours;
        this.deliveryOption = deliveryOption;
    }

    public List<PurchaseOrderLineItemEntity> getListOfLineItems() {
        return listOfLineItems;
    }

    public void setListOfLineItems(List<PurchaseOrderLineItemEntity> listOfLineItems) {
        this.listOfLineItems = listOfLineItems;
    }

//    public DeliverySlotEntity getDeliverySlot() {
//        return deliverySlot;
//    }
//
//    public void setDeliverySlot(DeliverySlotEntity deliverySlot) {
//        this.deliverySlot = deliverySlot;
//    }
    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
