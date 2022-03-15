/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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


@Entity
public class PartEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partId;
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String partName;
    @Column(nullable = false)
    @NotNull
    private String description;
    
    @ManyToMany
    private List<ProductEntity> products;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PartChoiceEntity> partChoices;
    
    public PartEntity() {
        this.products = new ArrayList<>();
        this.partChoices = new ArrayList<>();
    }
    
    public PartEntity(String partName, String description) {
        this();
        this.partName = partName;
        this.description = description;
    }
    
    

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partId != null ? partId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the partId fields are not set
        if (!(object instanceof PartEntity)) {
            return false;
        }
        PartEntity other = (PartEntity) object;
        if ((this.partId == null && other.partId != null) || (this.partId != null && !this.partId.equals(other.partId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartEntity[ id=" + partId + " ]";
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<PartChoiceEntity> getPartChoices() {
        return partChoices;
    }

    public void setPartChoices(List<PartChoiceEntity> partChoices) {
        this.partChoices = partChoices;
    }
    
}
