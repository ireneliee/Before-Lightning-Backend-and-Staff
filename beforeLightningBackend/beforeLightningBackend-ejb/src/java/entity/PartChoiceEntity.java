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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PartChoiceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partChoiceEntityId;
    @Column(nullable = false, length = 128, unique = true)
    @NotNull
    @Size(max = 128)
    private String partChoiceName;
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
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    @Column(length = 1000)
    @Size(max = 1000)
    private String partOverview;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String partDescription;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;
    @NotNull
    private Boolean isDisabled;

    @ManyToMany
    private List<PartChoiceEntity> compatibleChassisPartChoiceEntities;

    @ManyToMany
    private List<PartChoiceEntity> compatiblePartsPartChoiceEntities;

    @ManyToMany
    private List<PromotionEntity> promotionEntities;

    public PartChoiceEntity() {
        this.compatibleChassisPartChoiceEntities = new ArrayList<>();
        this.compatiblePartsPartChoiceEntities = new ArrayList<>();
        this.promotionEntities = new ArrayList<>();
        this.imageLink = "";
        this.isDisabled = false;
    }

    public PartChoiceEntity(String partChoiceName, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String partOverview, String partDescription) {
        this();
        this.partChoiceName = partChoiceName;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
        this.brand = brand;
        this.price = price;
        this.partOverview = partOverview;
        this.partDescription = partDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partChoiceEntityId != null ? partChoiceEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partChoiceId fields are not set
        if (!(object instanceof PartChoiceEntity)) {
            return false;
        }
        PartChoiceEntity other = (PartChoiceEntity) object;
        if ((this.partChoiceEntityId == null && other.partChoiceEntityId != null) || (this.partChoiceEntityId != null && !this.partChoiceEntityId.equals(other.partChoiceEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartChoiceEntity[ id=" + partChoiceEntityId + " ]";
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPartOverview() {
        return partOverview;
    }

    public void setPartOverview(String partOverview) {
        this.partOverview = partOverview;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<PartChoiceEntity> getCompatibleChassisPartChoiceEntities() {
        return compatibleChassisPartChoiceEntities;
    }

    public void setCompatibleChassisPartChoiceEntities(List<PartChoiceEntity> compatibleChassisPartChoiceEntities) {
        this.compatibleChassisPartChoiceEntities = compatibleChassisPartChoiceEntities;
    }

    public List<PartChoiceEntity> getCompatiblePartsPartChoiceEntities() {
        return compatiblePartsPartChoiceEntities;
    }

    public void setCompatiblePartsPartChoiceEntities(List<PartChoiceEntity> compatiblePartsPartChoiceEntities) {
        this.compatiblePartsPartChoiceEntities = compatiblePartsPartChoiceEntities;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public List<PromotionEntity> getPromotionEntities() {
        return promotionEntities;
    }

    public void setPromotionEntities(List<PromotionEntity> promotionEntities) {
        this.promotionEntities = promotionEntities;
    }

    public String getPartChoiceName() {
        return partChoiceName;
    }

    public void setPartChoiceName(String partChoiceName) {
        this.partChoiceName = partChoiceName;
    }

    public Long getPartChoiceEntityId() {
        return partChoiceEntityId;
    }

    public void setPartChoiceEntityId(Long partChoiceEntityId) {
        this.partChoiceEntityId = partChoiceEntityId;
    }

}
