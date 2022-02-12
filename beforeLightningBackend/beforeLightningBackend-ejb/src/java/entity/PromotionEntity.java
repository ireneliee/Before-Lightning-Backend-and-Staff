/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 *
 * @author irene
 */
@Entity
public class PromotionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionEntityId;

    @Column(nullable = false)
    @NotNull
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull
    private LocalDate endDate;

    @Column(scale = 2)
    @DecimalMin("0.00")
    private Double discount;

    @Column(precision = 11, scale = 2)
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal discountedPrice;

    @ManyToMany
    private List<PartChoiceEntity> partChoices;

    @ManyToMany
    private List<AccessoryEntity> accessories;

    public PromotionEntity() {
        this.partChoices = new ArrayList<>();
    }

    public PromotionEntity(LocalDate startDate, LocalDate endDate, Double discount, BigDecimal discountedPrice) {
        this();
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
    }

    public Long getPromotionEntityId() {
        return promotionEntityId;
    }

    public void setPromotionEntityId(Long promotionEntityId) {
        this.promotionEntityId = promotionEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionEntityId != null ? promotionEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promotionEntityId fields are not set
        if (!(object instanceof PromotionEntity)) {
            return false;
        }
        PromotionEntity other = (PromotionEntity) object;
        if ((this.promotionEntityId == null && other.promotionEntityId != null) || (this.promotionEntityId != null && !this.promotionEntityId.equals(other.promotionEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PromotionEntity[ id=" + promotionEntityId + " ]";
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public List<PartChoiceEntity> getPartChoices() {
        return partChoices;
    }

    public void setPartChoices(List<PartChoiceEntity> partChoices) {
        this.partChoices = partChoices;
    }

    public List<AccessoryEntity> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoryEntity> accessories) {
        this.accessories = accessories;
    }

}
