/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import entity.ForumPostEntity;
import entity.ReplyEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.ForumPostNotFoundException;

/**
 *
 * @author irene
 */
@Named(value = "viewForumManagedBean")
@ViewScoped
public class ViewForumManagedBean implements Serializable {

    @EJB
    private ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal;

    private ForumPostEntity forumEntityToView;
    private List<ReplyEntity> listOfComments;
    private ReplyEntity replyEntityToUpdate;
    
    public ViewForumManagedBean() {
        forumEntityToView = new ForumPostEntity();
        listOfComments = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
    
    }
    
    public void retrieveReplies(ActionEvent event) {
        ForumPostEntity forumEntityToView = (ForumPostEntity) event.getComponent().getAttributes().get("forumPostToView");
        listOfComments = forumEntityToView.getReplies();
    }
    
    
    
public void banForum(ActionEvent event) {
    forumEntityToView.setIsBanned(true);
        try {
            forumPostsEntitySessionBeanLocal.changeBannedStatus(forumEntityToView);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum is banned successfully", null));
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
}

public void unbanForum(ActionEvent event) {
    forumEntityToView.setIsBanned(false);
        try {
            forumPostsEntitySessionBeanLocal.changeBannedStatus(forumEntityToView);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum is unbanned successfully", null));
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
}
    

    public ForumPostEntity getForumEntityToView() {
        return forumEntityToView;
    }

    public void setForumEntityToView(ForumPostEntity forumEntityToView) {
        this.forumEntityToView = forumEntityToView;
    }

    public List<ReplyEntity> getListOfComments() {
        return listOfComments;
    }

    public void setListOfComments(List<ReplyEntity> listOfComments) {
        this.listOfComments = listOfComments;
    }

    public ReplyEntity getReplyEntityToUpdate() {
        return replyEntityToUpdate;
    }

    public void setReplyEntityToUpdate(ReplyEntity replyEntityToUpdate) {
        this.replyEntityToUpdate = replyEntityToUpdate;
    }

    public ForumPostsEntitySessionBeanLocal getForumPostsEntitySessionBeanLocal() {
        return forumPostsEntitySessionBeanLocal;
    }

    public void setForumPostsEntitySessionBeanLocal(ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal) {
        this.forumPostsEntitySessionBeanLocal = forumPostsEntitySessionBeanLocal;
    }
    
}
