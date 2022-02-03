/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private MemberEntity memberEntity;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private AddressEntity addressEntity;
    @OneToMany(fetch = FetchType.LAZY)
    private List<PurchaseOrderLineItemEntity> purchaseOrderLineItemEntities;

    public PurchaseOrderEntity() {
        this.purchaseOrderLineItemEntities = new ArrayList<PurchaseOrderLineItemEntity>();
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

    /**
     * @return the memberEntity
     */
    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    /**
     * @param memberEntity the memberEntity to set
     */
    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    /**
     * @return the addressEntity
     */
    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    /**
     * @param addressEntity the addressEntity to set
     */
    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    /**
     * @return the purchaseOrderLineItemEntities
     */
    public List<PurchaseOrderLineItemEntity> getPurchaseOrderLineItemEntities() {
        return purchaseOrderLineItemEntities;
    }

    /**
     * @param purchaseOrderLineItemEntities the purchaseOrderLineItemEntities to
     * set
     */
    public void setPurchaseOrderLineItemEntities(List<PurchaseOrderLineItemEntity> purchaseOrderLineItemEntities) {
        this.purchaseOrderLineItemEntities = purchaseOrderLineItemEntities;
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
