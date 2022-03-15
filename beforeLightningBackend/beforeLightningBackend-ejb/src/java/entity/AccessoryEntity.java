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
    
    @Column(length = 5000)
    @Size(max = 5000)
    private String description;

 

    public AccessoryEntity(String superclassName) {
        super(superclassName);
        this.accessoryItem = new ArrayList<>();
    }

    public AccessoryEntity() {
    }

    public AccessoryEntity(String superclassName, String accessoryName) {
        this(superclassName);
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
        hash += (productTypeEntityId != null ? productTypeEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productTypeEntityId fields are not set
        if (!(object instanceof AccessoryEntity)) {
            return false;
        }
        AccessoryEntity other = (AccessoryEntity) object;
        if ((this.productTypeEntityId == null && other.productTypeEntityId != null) || (this.productTypeEntityId != null && !this.productTypeEntityId.equals(other.productTypeEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Accessory[ id=" + productTypeEntityId + " ]";
    }

    public List<AccessoryItemEntity> getAccessoryItem() {
        return accessoryItem;
    }

    public void setAccessoryItem(List<AccessoryItemEntity> accessoryItem) {
        this.accessoryItem = accessoryItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
