/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.PurchaseOrderLineItemStatusEnum;
import util.enumeration.PurchaseOrderLineItemTypeEnum;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class PurchaseOrderLineItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderLineItemEntityId;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer serialNumber;
    @NotNull
    @Column(nullable = false)
    @Min(1)
    private Integer quantity;
//    @DecimalMin("1.00")
//    @DecimalMax("99999.00")
//    @NotNull
//    @Column(nullable = false)
//    private BigDecimal subTotalPrice;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String cosmeticImageLink;
    @Enumerated
    @NotNull
    @Column(nullable = false)
    private PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus;
    @Enumerated
    @NotNull
    @Column(nullable = false)
    private PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrderLineItem")
    private List<SupportTicketEntity> supportTicketEntities;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    private ProductEntity productEntity;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    private AccessoryItemEntity accessoryItemEntity;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PartChoiceEntity> partChoiceEntities;

    public PurchaseOrderLineItemEntity() {
        this.supportTicketEntities = new ArrayList<>();
        this.partChoiceEntities = new ArrayList<>();
    }

    public PurchaseOrderLineItemEntity(Integer serialNumber, Integer quantity, String cosmeticImageLink, PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus, PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum) {
        this();
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.cosmeticImageLink = cosmeticImageLink;
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
        this.purchaseOrderLineItemTypeEnum = purchaseOrderLineItemTypeEnum;
    }

    public PurchaseOrderLineItemEntity(Integer serialNumber, Integer quantity, PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus, PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum, AccessoryItemEntity item) {
        this();
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
        this.purchaseOrderLineItemTypeEnum = purchaseOrderLineItemTypeEnum;
        this.accessoryItemEntity = item;
        this.cosmeticImageLink = "";
    }

    public PurchaseOrderLineItemEntity(Integer serialNumber, Integer quantity, PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus, PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum, ProductEntity product) {
        this();
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
        this.purchaseOrderLineItemTypeEnum = purchaseOrderLineItemTypeEnum;
        this.productEntity = product;
        this.cosmeticImageLink = "";
    }

    public PurchaseOrderLineItemEntity(Integer serialNumber, Integer quantity, BigDecimal subTotalPrice, String cosmeticImageLink, PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus, PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum, ProductEntity productEntity, AccessoryItemEntity accessoryItemEntity) {
        this();
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.cosmeticImageLink = cosmeticImageLink;
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
        this.purchaseOrderLineItemTypeEnum = purchaseOrderLineItemTypeEnum;
        this.productEntity = productEntity;
        this.accessoryItemEntity = accessoryItemEntity;
    }

    public Long getPurchaseOrderLineItemEntityId() {
        return purchaseOrderLineItemEntityId;
    }

    public void setPurchaseOrderLineItemEntityId(Long purchaseOrderLineItemEntityId) {
        this.purchaseOrderLineItemEntityId = purchaseOrderLineItemEntityId;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the subTotalPrice
     */
    public BigDecimal getSubTotalPrice() {
        if (this.purchaseOrderLineItemTypeEnum == PurchaseOrderLineItemTypeEnum.ACCESSORY) {
            return this.accessoryItemEntity.getPrice().multiply(new BigDecimal(quantity));
        } else {
            BigDecimal subprice = BigDecimal.ZERO;
            for (PartChoiceEntity p : partChoiceEntities) {
                subprice = subprice.add(p.getPrice());
            }
            return subprice.multiply(new BigDecimal(quantity));
        }
    }

    /**
     * @return the purchaseOrderLineItemStatus
     */
    public PurchaseOrderLineItemStatusEnum getPurchaseOrderLineItemStatus() {
        return purchaseOrderLineItemStatus;
    }

    /**
     * @param purchaseOrderLineItemStatus the purchaseOrderLineItemStatus to set
     */
    public void setPurchaseOrderLineItemStatus(PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus) {
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
    }

    /**
     * @return the supportTicketEntities
     */
    public List<SupportTicketEntity> getSupportTicketEntities() {
        return supportTicketEntities;
    }

    /**
     * @param supportTicketEntities the supportTicketEntities to set
     */
    public void setSupportTicketEntities(List<SupportTicketEntity> supportTicketEntities) {
        this.supportTicketEntities = supportTicketEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchaseOrderLineItemEntityId != null ? purchaseOrderLineItemEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the purchaseOrderLineItemEntityId fields are not set
        if (!(object instanceof PurchaseOrderLineItemEntity)) {
            return false;
        }
        PurchaseOrderLineItemEntity other = (PurchaseOrderLineItemEntity) object;
        if ((this.purchaseOrderLineItemEntityId == null && other.purchaseOrderLineItemEntityId != null) || (this.purchaseOrderLineItemEntityId != null && !this.purchaseOrderLineItemEntityId.equals(other.purchaseOrderLineItemEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchaseOrderLineItemEntity[ id=" + purchaseOrderLineItemEntityId + " ]";
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the cosmeticImageLink
     */
    public String getCosmeticImageLink() {
        return cosmeticImageLink;
    }

    /**
     * @param cosmeticImageLink the cosmeticImageLink to set
     */
    public void setCosmeticImageLink(String cosmeticImageLink) {
        this.cosmeticImageLink = cosmeticImageLink;
    }

    /**
     * @return the purchaseOrderLineItemTypeEnum
     */
    public PurchaseOrderLineItemTypeEnum getPurchaseOrderLineItemTypeEnum() {
        return purchaseOrderLineItemTypeEnum;
    }

    /**
     * @param purchaseOrderLineItemTypeEnum the purchaseOrderLineItemTypeEnum to
     * set
     */
    public void setPurchaseOrderLineItemTypeEnum(PurchaseOrderLineItemTypeEnum purchaseOrderLineItemTypeEnum) {
        this.purchaseOrderLineItemTypeEnum = purchaseOrderLineItemTypeEnum;
    }

    /**
     * @return the productEntity
     */
    public ProductEntity getProductEntity() {
        return productEntity;
    }

    /**
     * @param productEntity the productEntity to set
     */
    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    /**
     * @return the accessoryItemEntity
     */
    public AccessoryItemEntity getAccessoryItemEntity() {
        return accessoryItemEntity;
    }

    /**
     * @param accessoryItemEntity the accessoryItemEntity to set
     */
    public void setAccessoryItemEntity(AccessoryItemEntity accessoryItemEntity) {
        this.accessoryItemEntity = accessoryItemEntity;
    }

    /**
     * @return the partChoiceEntities
     */
    public List<PartChoiceEntity> getPartChoiceEntities() {
        return partChoiceEntities;
    }

    /**
     * @param partChoiceEntities the partChoiceEntities to set
     */
    public void setPartChoiceEntities(List<PartChoiceEntity> partChoiceEntities) {
        this.partChoiceEntities = partChoiceEntities;
    }

    public String getName() {
        if (purchaseOrderLineItemTypeEnum == PurchaseOrderLineItemTypeEnum.ACCESSORY) {
            return accessoryItemEntity.getAccessoryItemName();
        } else {
            return productEntity.getProductName();
        }
    }

}
