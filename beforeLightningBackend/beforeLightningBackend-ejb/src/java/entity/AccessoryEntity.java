/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kaiyu
 */
@Entity
public class AccessoryEntity extends ProductTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    @NotNull
    private String accessoryName;

    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "accessory")
    private List<AccessoryItemEntity> accessoryItem;

    @ManyToMany
    private List<PromotionEntity> promotions;

    public AccessoryEntity() {
        super();
        this.accessoryItem = new ArrayList<>();
        this.promotions = new ArrayList<>();
    }

    public AccessoryEntity(String accessoryName) {
        this();
        this.accessoryName = accessoryName;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productTypeId != null ? productTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productTypeId fields are not set
        if (!(object instanceof AccessoryEntity)) {
            return false;
        }
        AccessoryEntity other = (AccessoryEntity) object;
        if ((this.productTypeId == null && other.productTypeId != null) || (this.productTypeId != null && !this.productTypeId.equals(other.productTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Accessory[ id=" + productTypeId + " ]";
    }

    public List<AccessoryItemEntity> getAccessoryItem() {
        return accessoryItem;
    }

    public void setAccessoryItem(List<AccessoryItemEntity> accessoryItem) {
        this.accessoryItem = accessoryItem;
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
