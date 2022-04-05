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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class AccessoryItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessoryItemEntityId;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String accessoryItemName;
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantityOnHand;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer reorderQuantity;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String brand;
    @Column(nullable = false)
    @DecimalMin("1.00")
    @DecimalMax("9999.00")
    @NotNull
    private BigDecimal price;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;
    @NotNull
    private Boolean isDisabled;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private AccessoryEntity accessoryEntity;
    @ManyToMany
    private List<PromotionEntity> promotionEntities;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntities;

    public AccessoryItemEntity() {
		this.promotionEntities = new ArrayList<>();
        this.reviewEntities = new ArrayList<>();
        this.imageLink = "";
        this.isDisabled = false;
    }

    public AccessoryItemEntity(String accessoryItemName, String skuCode, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String description) {
        this();
        this.accessoryItemName = accessoryItemName;
        this.skuCode = skuCode;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
        this.brand = brand;
        this.price = price;
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessoryItemEntityId != null ? accessoryItemEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the accessoryItemEntityId fields are not set
        if (!(object instanceof AccessoryItemEntity)) {
            return false;
        }
        AccessoryItemEntity other = (AccessoryItemEntity) object;
        if ((this.accessoryItemEntityId == null && other.accessoryItemEntityId != null) || (this.accessoryItemEntityId != null && !this.accessoryItemEntityId.equals(other.accessoryItemEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AccessoryItemEntity[ accessoryItemEntityId=" + accessoryItemEntityId + " ]";
    }

    public Long getAccessoryItemEntityId() {
        return accessoryItemEntityId;
    }

    public void setAccessoryItemEntityId(Long accessoryItemEntityId) {
        this.accessoryItemEntityId = accessoryItemEntityId;
    }

    public String getAccessoryItemName() {
        return accessoryItemName;
    }

    public void setAccessoryItemName(String accessoryItemName) {
        this.accessoryItemName = accessoryItemName;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AccessoryEntity getAccessoryEntity() {
        return accessoryEntity;
    }

    public void setAccessoryEntity(AccessoryEntity accessoryEntity) {
        this.accessoryEntity = accessoryEntity;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<PromotionEntity> getPromotionEntities() {
        return promotionEntities;
    }

    public void setPromotionEntities(List<PromotionEntity> promotionEntities) {
        this.promotionEntities = promotionEntities;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * @return the reorderQuantity
     */
    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    /**
     * @param reorderQuantity the reorderQuantity to set
     */
    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReviewEntity> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(List<ReviewEntity> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }

}
