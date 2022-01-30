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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class CreditCardEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCardEntityId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Max(9999999999999999L)
    @Min(1000000000000000L)
    private Long creditCardNumber;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 3, max = 32)
    private String nameOnCard;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 4, max = 7)
    private String expiryDate;

    public CreditCardEntity() {
    }

    public CreditCardEntity(Long creditCardNumber, String nameOnCard, String expiryDate) {
        this();
        this.creditCardNumber = creditCardNumber;
        this.nameOnCard = nameOnCard;
        this.expiryDate = expiryDate;
    }

    public Long getCreditCardEntityId() {
        return creditCardEntityId;
    }

    public void setCreditCardEntityId(Long creditCardEntityId) {
        this.creditCardEntityId = creditCardEntityId;
    }

    /**
     * @return the creditCardNumber
     */
    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber the creditCardNumber to set
     */
    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * @return the nameOnCard
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * @param nameOnCard the nameOnCard to set
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditCardEntityId != null ? creditCardEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the creditCardEntityId fields are not set
        if (!(object instanceof CreditCardEntity)) {
            return false;
        }
        CreditCardEntity other = (CreditCardEntity) object;
        if ((this.creditCardEntityId == null && other.creditCardEntityId != null) || (this.creditCardEntityId != null && !this.creditCardEntityId.equals(other.creditCardEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditCardEntity[ id=" + creditCardEntityId + " ]";
    }

}
