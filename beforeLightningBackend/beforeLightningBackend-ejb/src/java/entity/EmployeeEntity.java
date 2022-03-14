/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import util.enumeration.EmployeeAccessRightEnum;
import util.security.CryptographicHelper;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class EmployeeEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private EmployeeAccessRightEnum employeeAccessRight;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    public EmployeeEntity() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public EmployeeEntity(EmployeeAccessRightEnum employeeAccessRight, String username, String password, String firstname, String lastname, String email, String contact) {
        super(username, password, firstname, lastname, email, contact);
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        this.employeeAccessRight = employeeAccessRight;
        this.setPassword(password);
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
        // TODO: Warning - this method won't work in the case the employeeId fields are not set
        if (!(object instanceof EmployeeEntity)) {
            return false;
        }
        EmployeeEntity other = (EmployeeEntity) object;
        if ((this.getUserEntityId() == null && other.getUserEntityId() != null) || (this.getUserEntityId() != null && !this.getUserEntityId().equals(other.getUserEntityId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EmployeeEntity[ id=" + this.getUserEntityId() + " ]";
    }

    public EmployeeAccessRightEnum getEmployeeAccessRight() {
        return employeeAccessRight;
    }

    public void setEmployeeAccessRight(EmployeeAccessRightEnum employeeAccessRight) {
        this.employeeAccessRight = employeeAccessRight;
    }

}
