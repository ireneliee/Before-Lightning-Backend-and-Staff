/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 *
 * @author irene
 */
@Entity
public class ForumPostEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumPostEntityId;
    
    @Column(nullable = false, length = 256)
    @Size(max = 256)
    private String title;
    
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
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MemberEntity> userWhoLikes;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MemberEntity> userWhoDislikes;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private MemberEntity author;
    
    @OneToMany(mappedBy = "forumPost")
    private List<ReplyEntity> replies;

    public ForumPostEntity(String title, String content, String imageLink, MemberEntity author) {
        this();
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
        this.author = author;
    }
    
    public ForumPostEntity(String title, String content,MemberEntity author) {
        this();
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public ForumPostEntity() {
        timestamp = LocalDateTime.now();
        isVisible = true;
        isBanned = false;
        userWhoLikes = new ArrayList<>();
        userWhoDislikes = new ArrayList<>();
    }
    

    public Long getForumPostEntityId() {
        return forumPostEntityId;
    }

    public void setForumPostEntityId(Long forumPostEntityId) {
        this.forumPostEntityId = forumPostEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumPostEntityId != null ? forumPostEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the forumPostEntityId fields are not set
        if (!(object instanceof ForumPostEntity)) {
            return false;
        }
        ForumPostEntity other = (ForumPostEntity) object;
        if ((this.forumPostEntityId == null && other.forumPostEntityId != null) || (this.forumPostEntityId != null && !this.forumPostEntityId.equals(other.forumPostEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumPostEntity[ id=" + forumPostEntityId + " ]";
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MemberEntity> getUserWhoLikes() {
        return userWhoLikes;
    }

    public void setUserWhoLikes(List<MemberEntity> userWhoLikes) {
        this.userWhoLikes = userWhoLikes;
    }

    public List<MemberEntity> getUserWhoDislikes() {
        return userWhoDislikes;
    }

    public void setUserWhoDislikes(List<MemberEntity> userWhoDislikes) {
        this.userWhoDislikes = userWhoDislikes;
    }

    public List<ReplyEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyEntity> replies) {
        this.replies = replies;
    }
    
}
