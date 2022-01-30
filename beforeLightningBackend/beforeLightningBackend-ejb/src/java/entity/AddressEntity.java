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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressEntityId;
    @Column(nullable = false, length = 400)
    @NotNull
    @Size(min = 1, max = 400)
    private String block;
    @Column(nullable = false, length = 400)
    @NotNull
    @Size(min = 1, max = 400)
    private String unit;
    @Column(nullable = false, length = 400)
    @NotNull
    @Size(min = 1, max = 400)
    private String postalCode;
    @Column(nullable = false, length = 400)
    @NotNull
    @Size(min = 1, max = 400)
    private String country;

    public AddressEntity() {
    }

    public AddressEntity(String block, String unit, String postalCode, String country) {
        this();
        this.block = block;
        this.unit = unit;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Long getAddressEntityId() {
        return addressEntityId;
    }

    public void setAddressEntityId(Long addressEntityId) {
        this.addressEntityId = addressEntityId;
    }

    /**
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressEntityId != null ? addressEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the addressEntityId fields are not set
        if (!(object instanceof AddressEntity)) {
            return false;
        }
        AddressEntity other = (AddressEntity) object;
        if ((this.addressEntityId == null && other.addressEntityId != null) || (this.addressEntityId != null && !this.addressEntityId.equals(other.addressEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AddressEntity[ id=" + addressEntityId + " ]";
    }

}
