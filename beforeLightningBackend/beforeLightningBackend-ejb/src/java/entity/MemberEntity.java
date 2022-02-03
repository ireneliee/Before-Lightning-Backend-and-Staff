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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class MemberEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    @Column(nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    private String contact;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ShoppingCartEntity shoppingCart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberEntity")
    private List<CreditCardEntity> creditCardEntities;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AddressEntity> addressEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberEntity")
    private List<PurchaseOrderEntity> purchaseOrderEntities;

    public MemberEntity() {
        this.creditCardEntities = new ArrayList<CreditCardEntity>();
        this.addressEntities = new ArrayList<AddressEntity>();
        this.purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
    }

    public MemberEntity(String email, String contact, String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
        this.email = email;
        this.contact = contact;
        this.creditCardEntities = new ArrayList<CreditCardEntity>();
        this.addressEntities = new ArrayList<AddressEntity>();
        this.purchaseOrderEntities = new ArrayList<PurchaseOrderEntity>();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the shoppingCart
     */
    public ShoppingCartEntity getShoppingCart() {
        return shoppingCart;
    }

    /**
     * @param shoppingCart the shoppingCart to set
     */
    public void setShoppingCart(ShoppingCartEntity shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * @return the creditCardEntities
     */
    public List<CreditCardEntity> getCreditCardEntities() {
        return creditCardEntities;
    }

    /**
     * @param creditCardEntities the creditCardEntities to set
     */
    public void setCreditCardEntities(List<CreditCardEntity> creditCardEntities) {
        this.creditCardEntities = creditCardEntities;
    }

    /**
     * @return the addressEntities
     */
    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    /**
     * @param addressEntities the addressEntities to set
     */
    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    /**
     * @return the purchaseOrderEntities
     */
    public List<PurchaseOrderEntity> getPurchaseOrderEntities() {
        return purchaseOrderEntities;
    }

    /**
     * @param purchaseOrderEntities the purchaseOrderEntities to set
     */
    public void setPurchaseOrderEntities(List<PurchaseOrderEntity> purchaseOrderEntities) {
        this.purchaseOrderEntities = purchaseOrderEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getUserEntityId() != null ? this.getUserEntityId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberId fields are not set
        if (!(object instanceof MemberEntity)) {
            return false;
        }
        MemberEntity other = (MemberEntity) object;
        if ((this.getUserEntityId() == null && other.getUserEntityId() != null) || (this.getUserEntityId() != null && !this.getUserEntityId().equals(other.getUserEntityId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MemberEntity[ id=" + this.getUserEntityId() + " ]";
    }

}
