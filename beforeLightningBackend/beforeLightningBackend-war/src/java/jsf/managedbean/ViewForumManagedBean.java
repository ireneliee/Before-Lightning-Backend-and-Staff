/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import entity.ForumPostEntity;
import entity.ReplyEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

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
    }
    
    @PostConstruct
    public void postConstruct() {
        listOfComments = forumEntityToView.getReplies();
    }
    
    public void setBanned(ActionEvent event) {
        replyEntityToUpdate.setIsBanned(true);
        forumPostsEntitySessionBeanLocal.updateReplyEntity(replyEntityToUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reply has been banned. It will be unviewable by the public.", null));
    
    }
    
    public void setUnbanned(ActionEvent event) {
        replyEntityToUpdate.setIsBanned(false);
        forumPostsEntitySessionBeanLocal.updateReplyEntity(replyEntityToUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reply has been unbanned. It will be viewable by the public.", null));
    
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
