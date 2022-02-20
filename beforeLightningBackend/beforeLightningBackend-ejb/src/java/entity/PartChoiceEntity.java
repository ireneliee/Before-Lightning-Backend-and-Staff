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
import javax.persistence.JoinColumn;
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
    private Long partChoiceId;
    
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String specification;
    
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
    
    @ManyToMany
    private List<PartChoiceEntity> leftSuitablePartChoices;
    
    @ManyToMany
    private List<PartChoiceEntity> rightSuitablePartChoices;
    
    @ManyToMany
    private List<PromotionEntity> promotions;
    
    public PartChoiceEntity() {
        this.rightSuitablePartChoices = new ArrayList<>();
        this.leftSuitablePartChoices = new ArrayList<>();
        this.promotions = new ArrayList<>();
    }


    public PartChoiceEntity(String specification, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String partOverview, String partDescription) {
        this();
        this.specification = specification;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
        this.brand = brand;
        this.price = price;
        this.partOverview = partOverview;
        this.partDescription = partDescription;
        

    }

    
    public PartChoiceEntity(String specification, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String partDescription) {
        this();
        this.specification = specification;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
        this.brand = brand;
        this.price = price;
        this.partDescription = partDescription;
    }
    
    
    
    

    public Long getPartChoiceId() {
        return partChoiceId;
    }

    public void setPartChoiceId(Long partChoiceId) {
        this.partChoiceId = partChoiceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partChoiceId != null ? partChoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partChoiceId fields are not set
        if (!(object instanceof PartChoiceEntity)) {
            return false;
        }
        PartChoiceEntity other = (PartChoiceEntity) object;
        if ((this.partChoiceId == null && other.partChoiceId != null) || (this.partChoiceId != null && !this.partChoiceId.equals(other.partChoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartChoiceEntity[ id=" + partChoiceId + " ]";
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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

    public List<PartChoiceEntity> getLeftSuitablePartChoices() {
        return leftSuitablePartChoices;
    }

    public void setLeftSuitablePartChoices(List<PartChoiceEntity> leftSuitablePartChoices) {
        this.leftSuitablePartChoices = leftSuitablePartChoices;
    }

    public List<PartChoiceEntity> getRightSuitablePartChoices() {
        return rightSuitablePartChoices;
    }

    public void setRightSuitablePartChoices(List<PartChoiceEntity> rightSuitablePartChoices) {
        this.rightSuitablePartChoices = rightSuitablePartChoices;
    }

    public List<PromotionEntity> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionEntity> promotions) {
        this.promotions = promotions;
    }
    
}