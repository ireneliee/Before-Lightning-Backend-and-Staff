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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.security.CryptographicHelper;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class MemberEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    
    @OneToMany(mappedBy = "author")
    private List<ForumPostEntity> forumPosts;
    
    @OneToMany(mappedBy = "author")
    private List<ReplyEntity> forumReplies;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<ForumPostEntity> postsLiked;
    
    @Column(nullable = false)
    private Boolean isActive;

    public MemberEntity() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        this.creditCards = new ArrayList<CreditCardEntity>();
        this.addresses = new ArrayList<AddressEntity>();
        this.purchaseOrders = new ArrayList<PurchaseOrderEntity>();
        this.isActive = true;
    }

    public MemberEntity(String username, String password, String firstname, String lastname, String email, String contact) {
        super(username, password, firstname, lastname, email, contact);
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        this.creditCards = new ArrayList<CreditCardEntity>();
        this.addresses = new ArrayList<AddressEntity>();
        this.purchaseOrders = new ArrayList<PurchaseOrderEntity>();
        this.setPassword(password);
        this.isActive = true;
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

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public void setPassword(String password) {
        if (password != null) {
            super.setPassword(CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt)));

        } else {
            super.setPassword(CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(null)));
        } //To change body of generated methods, choose Tools | Templates.
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

    public List<ForumPostEntity> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<ForumPostEntity> forumPosts) {
        this.forumPosts = forumPosts;
    }

    public List<ForumPostEntity> getPostsLiked() {
        return postsLiked;
    }

    public void setPostsLiked(List<ForumPostEntity> postsLiked) {
        this.postsLiked = postsLiked;
    }

    public List<ReplyEntity> getForumReplies() {
        return forumReplies;
    }

    public void setForumReplies(List<ReplyEntity> forumReplies) {
        this.forumReplies = forumReplies;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
