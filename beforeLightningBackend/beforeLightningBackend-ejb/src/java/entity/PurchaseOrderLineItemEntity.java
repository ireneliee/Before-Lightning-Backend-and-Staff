/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.PurchaseOrderLineItemStatusEnum;

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
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String lineItemName;
    @NotNull
    @Column(nullable = false)
    @Min(1)
    private Integer quantity;
    @DecimalMin("1.00")
    @DecimalMax("9999.00")
    @NotNull
    @Column(nullable = false)
    private BigDecimal subTotalPrice;
    @Enumerated
    @NotNull
    @Column(nullable = false)
    private PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus;

    public PurchaseOrderLineItemEntity() {
    }

    public PurchaseOrderLineItemEntity(String lineItemName, Integer quantity, BigDecimal subTotalPrice, PurchaseOrderLineItemStatusEnum purchaseOrderLineItemStatus) {
        this();
        this.lineItemName = lineItemName;
        this.quantity = quantity;
        this.subTotalPrice = subTotalPrice;
        this.purchaseOrderLineItemStatus = purchaseOrderLineItemStatus;
    }

    public Long getPurchaseOrderLineItemEntityId() {
        return purchaseOrderLineItemEntityId;
    }

    public void setPurchaseOrderLineItemEntityId(Long purchaseOrderLineItemEntityId) {
        this.purchaseOrderLineItemEntityId = purchaseOrderLineItemEntityId;
    }

    /**
     * @return the lineItemName
     */
    public String getLineItemName() {
        return lineItemName;
    }

    /**
     * @param lineItemName the lineItemName to set
     */
    public void setLineItemName(String lineItemName) {
        this.lineItemName = lineItemName;
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
        return subTotalPrice;
    }

    /**
     * @param subTotalPrice the subTotalPrice to set
     */
    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
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

}
