/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kaiyu
 */
@Entity
public class AccessoryItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessoryItemId;
    @Column(nullable = false)
    private String accessoryItemName;
    @Column(nullable = false)
    private Integer quantityOnHand;
    @Column(nullable = false)
    @DecimalMin("1.00")
    @DecimalMax("9999.00")
    @NotNull
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AccessoryEntity accessory;

    @ManyToMany
    private List<PromotionEntity> promotions;

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getAccessoryItemName() {
        return accessoryItemName;
    }

    public void setAccessoryItemName(String accessoryItemName) {
        this.accessoryItemName = accessoryItemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getAccessoryItemId() {
        return accessoryItemId;
    }

    public void setAccessoryItemId(Long accessoryItemId) {
        this.accessoryItemId = accessoryItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessoryItemId != null ? accessoryItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the accessoryItemId fields are not set
        if (!(object instanceof AccessoryItemEntity)) {
            return false;
        }
        AccessoryItemEntity other = (AccessoryItemEntity) object;
        if ((this.accessoryItemId == null && other.accessoryItemId != null) || (this.accessoryItemId != null && !this.accessoryItemId.equals(other.accessoryItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AccessoryItem[ id=" + accessoryItemId + " ]";
    }

    public AccessoryEntity getAccessory() {
        return accessory;
    }

    public void setAccessory(AccessoryEntity accessory) {
        this.accessory = accessory;
    }

    /**
     * @return the promotions
     */
    public List<PromotionEntity> getPromotions() {
        return promotions;
    }

    /**
     * @param promotions the promotions to set
     */
    public void setPromotions(List<PromotionEntity> promotions) {
        this.promotions = promotions;
    }
}
