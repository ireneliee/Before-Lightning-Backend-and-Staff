/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class PartEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partEntityId;
    @Column(nullable = false)
    @NotNull
    private String partName;
    @Column(nullable = false)
    @NotNull
    private String description;

    @ManyToMany
    private List<ProductEntity> productEntities;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PartChoiceEntity> partChoiceEntities;



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPartEntityId() != null ? getPartEntityId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partEntityId fields are not set
        if (!(object instanceof PartEntity)) {
            return false;
        }
        PartEntity other = (PartEntity) object;
        if ((this.getPartEntityId() == null && other.getPartEntityId() != null) || (this.getPartEntityId() != null && !this.partEntityId.equals(other.partEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartEntity[ partEntityId=" + getPartEntityId() + " ]";
    }

    /**
     * @return the partEntityId
     */
    public Long getPartEntityId() {
        return partEntityId;
    }

    /**
     * @param partEntityId the partEntityId to set
     */
    public void setPartEntityId(Long partEntityId) {
        this.partEntityId = partEntityId;
    }

    /**
     * @return the partName
     */
    public String getPartName() {
        return partName;
    }

    /**
     * @param partName the partName to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
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

    /**
     * @return the productEntities
     */
    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    /**
     * @param productEntities the productEntities to set
     */
    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    /**
     * @return the partChoiceEntities
     */
    public List<PartChoiceEntity> getPartChoiceEntities() {
        return partChoiceEntities;
    }

    /**
     * @param partChoiceEntities the partChoiceEntities to set
     */
    public void setPartChoiceEntities(List<PartChoiceEntity> partChoiceEntities) {
        this.partChoiceEntities = partChoiceEntities;
    }

}
