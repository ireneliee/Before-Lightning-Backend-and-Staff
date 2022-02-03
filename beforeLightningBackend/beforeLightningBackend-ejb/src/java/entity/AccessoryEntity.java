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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accessory")
    private List<AccessoryItemEntity> accessoryItem;

    @ManyToMany
    private List<PromotionEntity> promotionEntities;

    public AccessoryEntity() {
        super();
        this.accessoryItem = new ArrayList<>();
        this.promotionEntities = new ArrayList<>();
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessoryEntity)) {
            return false;
        }
        AccessoryEntity other = (AccessoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Accessory[ id=" + id + " ]";
    }

    public List<AccessoryItemEntity> getAccessoryItem() {
        return accessoryItem;
    }

    public void setAccessoryItem(List<AccessoryItemEntity> accessoryItem) {
        this.accessoryItem = accessoryItem;
    }

    /**
     * @return the promotionEntities
     */
    public List<PromotionEntity> getPromotionEntities() {
        return promotionEntities;
    }

    /**
     * @param promotionEntities the promotionEntities to set
     */
    public void setPromotionEntities(List<PromotionEntity> promotionEntities) {
        this.promotionEntities = promotionEntities;
    }

}
