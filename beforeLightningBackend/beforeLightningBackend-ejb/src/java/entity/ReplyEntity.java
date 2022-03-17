/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 *
 * @author irene
 */
@Entity
public class ReplyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyEntityId;
    
    @Column(nullable = false, length = 10000)
    @Size(max = 10000)
    private String content;
    
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;
    
    @Column(nullable= false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private Boolean isVisible;
    
    @Column(nullable = false)
    private Boolean isBanned;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private MemberEntity author;

    public Long getReplyEntityId() {
        return replyEntityId;
    }
    
    
    

    public ReplyEntity() {
    }
    
    

    public void setReplyEntityId(Long replyEntityId) {
        this.replyEntityId = replyEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (replyEntityId != null ? replyEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the replyEntityId fields are not set
        if (!(object instanceof ReplyEntity)) {
            return false;
        }
        ReplyEntity other = (ReplyEntity) object;
        if ((this.replyEntityId == null && other.replyEntityId != null) || (this.replyEntityId != null && !this.replyEntityId.equals(other.replyEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReplyEntity[ id=" + replyEntityId + " ]";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    public MemberEntity getAuthor() {
        return author;
    }

    public void setAuthor(MemberEntity author) {
        this.author = author;
    }
    
}
