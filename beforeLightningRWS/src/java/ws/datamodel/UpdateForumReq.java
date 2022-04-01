/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author irene
 */
public class UpdateForumReq {
    private Long forumPostId;
    private String username;
    private String content;
    private Boolean visibility;

    public UpdateForumReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Long getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(Long ForumPostId) {
        this.forumPostId = ForumPostId;
    }
    
    
    
    
    
}
