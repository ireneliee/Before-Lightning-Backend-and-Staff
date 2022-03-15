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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kaiyu
 */
@Entity
public class AccessoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessoryEntityId;
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    @NotNull
    private String accessoryName;
    @NotNull
    private String description;
    @NotNull
    private Boolean isDisabled;

    @JoinColumn(nullable = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accessoryEntity")
    private List<AccessoryItemEntity> accessoryItemEntities;

    public AccessoryEntity() {
        this.accessoryItemEntities = new ArrayList<>();
        this.description = "";
        this.isDisabled = false;
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
        hash += (accessoryEntityId != null ? accessoryEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productTypeEntityId fields are not set
        if (!(object instanceof AccessoryEntity)) {
            return false;
        }
        AccessoryEntity other = (AccessoryEntity) object;
        if ((this.accessoryEntityId == null && other.accessoryEntityId != null) || (this.accessoryEntityId != null && !this.accessoryEntityId.equals(other.accessoryEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Accessory[ id=" + accessoryEntityId + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccessoryEntityId() {
        return accessoryEntityId;
    }

    public void setAccessoryEntityId(Long accessoryEntityId) {
        this.accessoryEntityId = accessoryEntityId;
    }

    public List<AccessoryItemEntity> getAccessoryItemEntities() {
        return accessoryItemEntities;
    }

    public void setAccessoryItemEntities(List<AccessoryItemEntity> accessoryItemEntities) {
        this.accessoryItemEntities = accessoryItemEntities;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

}
