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
import util.security.CryptographicHelper;

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
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ShoppingCartEntity shoppingCart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberEntity")
    private List<CreditCardEntity> creditCards;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AddressEntity> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<PurchaseOrderEntity> purchaseOrders;

    public MemberEntity() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        this.creditCards = new ArrayList<CreditCardEntity>();
        this.addresses = new ArrayList<AddressEntity>();
        this.purchaseOrders = new ArrayList<PurchaseOrderEntity>();
    }

    public MemberEntity(String email, String contact, String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        this.email = email;
        this.contact = contact;
        this.creditCards = new ArrayList<CreditCardEntity>();
        this.addresses = new ArrayList<AddressEntity>();
        this.purchaseOrders = new ArrayList<PurchaseOrderEntity>();
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
     * @return the creditCards
     */
    public List<CreditCardEntity> getCreditCards() {
        return creditCards;
    }

    /**
     * @param creditCards the creditCards to set
     */
    public void setCreditCards(List<CreditCardEntity> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * @return the addresses
     */
    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return the purchaseOrders
     */
    public List<PurchaseOrderEntity> getPurchaseOrders() {
        return purchaseOrders;
    }

    /**
     * @param purchaseOrders the purchaseOrders to set
     */
    public void setPurchaseOrders(List<PurchaseOrderEntity> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
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
