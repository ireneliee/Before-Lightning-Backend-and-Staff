/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author kaiyu
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long productTypeEntityId;
    @Column(nullable = false)
    private String productTypeName;

    public ProductTypeEntity() {
    }

    public ProductTypeEntity(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Long getProductTypeEntityId() {
        return productTypeEntityId;
    }

    public void setProductTypeEntityId(Long productTypeEntityId) {
        this.productTypeEntityId = productTypeEntityId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
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
        if (!(object instanceof ProductTypeEntity)) {
            return false;
        }
        ProductTypeEntity other = (ProductTypeEntity) object;
        if ((this.productTypeEntityId == null && other.productTypeEntityId != null) || (this.productTypeEntityId != null && !this.productTypeEntityId.equals(other.productTypeEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductType[ id=" + productTypeEntityId + " ]";
    }

}
