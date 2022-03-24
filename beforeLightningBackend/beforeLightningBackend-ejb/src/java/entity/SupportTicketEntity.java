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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.SupportTicketStatusEnum;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class SupportTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportTicketId;

    @Email
    @NotNull
    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 400)
    @NotNull
    @Size(min = 1, max = 400)
    private String issue;
    @Enumerated
    @NotNull
    @Column(nullable = false)
    private SupportTicketStatusEnum supportTicketStatus;

    public SupportTicketEntity() {
       this.supportTicketStatus = SupportTicketStatusEnum.OPEN;
    }

    public SupportTicketEntity(String email, String issue) {
        this();
        this.email = email;
        this.issue = issue;
    }

    public Long getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTIcketId(Long supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    /**
     * @return the issue
     */
    public String getIssue() {
        return issue;
    }

    /**
     * @param issue the issue to set
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return the supportTicketStatus
     */
    public SupportTicketStatusEnum getSupportTicketStatus() {
        return supportTicketStatus;
    }

    /**
     * @param supportTicketStatus the supportTicketStatus to set
     */
    public void setSupportTicketStatus(SupportTicketStatusEnum supportTicketStatus) {
        this.supportTicketStatus = supportTicketStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supportTicketId != null ? supportTicketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the supportTicketId fields are not set
        if (!(object instanceof SupportTicketEntity)) {
            return false;
        }
        SupportTicketEntity other = (SupportTicketEntity) object;
        if ((this.supportTicketId == null && other.supportTicketId != null) || (this.supportTicketId != null && !this.supportTicketId.equals(other.supportTicketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SupportTicketEntity[ id=" + supportTicketId + " ]";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
