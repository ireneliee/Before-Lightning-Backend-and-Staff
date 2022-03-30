/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.ForumPostEntity;

/**
 *
 * @author irene
 */
public class CreateNewForumPostReq {
    private String username;
    private String password;
    private ForumPostEntity forumPost;
    
    public CreateNewForumPostReq() {}
    
    public CreateNewForumPostReq(String username, String password, ForumPostEntity forumPost) {
        this.username = username;
        this.password = password;
        this.forumPost = forumPost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForumPostEntity getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPostEntity forumPost) {
        this.forumPost = forumPost;
    }
    
    
    
}
