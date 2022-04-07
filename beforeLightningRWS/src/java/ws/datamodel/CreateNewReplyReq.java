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
public class CreateNewReplyReq {
    
    private String username;
    private String content;
    private Long forumId;

    public CreateNewReplyReq(String username, String content, Long forumId) {
        this.username = username;
        this.content = content;
        this.forumId = forumId;
    }

    public CreateNewReplyReq() {
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

    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }
    
}
