/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class PurchaseOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderEntityId;
    @DecimalMin("1.00")
    @DecimalMax("9999.00")
    @NotNull
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateCreated;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime datePaid;

    public PurchaseOrderEntity() {
    }

    public PurchaseOrderEntity(BigDecimal totalPrice, LocalDateTime dateCreated, LocalDateTime datePaid) {
        this();
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
        this.datePaid = datePaid;
    }

    public Long getPurchaseOrderEntityId() {
        return purchaseOrderEntityId;
    }

    public void setPurchaseOrderEntityId(Long purchaseOrderEntityId) {
        this.purchaseOrderEntityId = purchaseOrderEntityId;
    }

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the dateCreated
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the datePaid
     */
    public LocalDateTime getDatePaid() {
        return datePaid;
    }

    /**
     * @param datePaid the datePaid to set
     */
    public void setDatePaid(LocalDateTime datePaid) {
        this.datePaid = datePaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchaseOrderEntityId != null ? purchaseOrderEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the purchaseOrderEntityId fields are not set
        if (!(object instanceof PurchaseOrderEntity)) {
            return false;
        }
        PurchaseOrderEntity other = (PurchaseOrderEntity) object;
        if ((this.purchaseOrderEntityId == null && other.purchaseOrderEntityId != null) || (this.purchaseOrderEntityId != null && !this.purchaseOrderEntityId.equals(other.purchaseOrderEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchaseOrderEntity[ id=" + purchaseOrderEntityId + " ]";
    }

}
