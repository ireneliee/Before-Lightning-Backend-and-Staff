/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class ShoppingCartEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartEntityId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PurchaseOrderLineItemEntity> purchaseOrderLineItemEntities;

    public ShoppingCartEntity() {
        this.purchaseOrderLineItemEntities = new ArrayList<PurchaseOrderLineItemEntity>();
    }

    public Long getShoppingCartEntityId() {
        return shoppingCartEntityId;
    }

    public void setShoppingCartEntityId(Long shoppingCartEntityId) {
        this.shoppingCartEntityId = shoppingCartEntityId;
    }

    /**
     * @return the purchaseOrderLineItemEntities
     */
    public List<PurchaseOrderLineItemEntity> getPurchaseOrderLineItemEntities() {
        return purchaseOrderLineItemEntities;
    }

    /**
     * @param purchaseOrderLineItemEntities the purchaseOrderLineItemEntities to
     * set
     */
    public void setPurchaseOrderLineItemEntities(List<PurchaseOrderLineItemEntity> purchaseOrderLineItemEntities) {
        this.purchaseOrderLineItemEntities = purchaseOrderLineItemEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoppingCartEntityId != null ? shoppingCartEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the shoppingCartEntityId fields are not set
        if (!(object instanceof ShoppingCartEntity)) {
            return false;
        }
        ShoppingCartEntity other = (ShoppingCartEntity) object;
        if ((this.shoppingCartEntityId == null && other.shoppingCartEntityId != null) || (this.shoppingCartEntityId != null && !this.shoppingCartEntityId.equals(other.shoppingCartEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ShoppingCartEntity[ id=" + shoppingCartEntityId + " ]";
    }

}
