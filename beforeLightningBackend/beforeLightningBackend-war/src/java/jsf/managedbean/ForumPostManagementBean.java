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
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.ForumPostNotFoundException;

/**
 *
 * @author irene
 */
@Named(value = "forumPostManagementBean")
@ViewScoped
public class ForumPostManagementBean implements Serializable {

    @EJB
    private ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal;

    private List<ForumPostEntity> forumPosts;
    private ForumPostEntity selectedForumPostEntityToUpdate;
    private List<ReplyEntity> listOfComments;
    private List<ForumPostEntity> filteredForumPosts;
    private ReplyEntity replyEntityToUpdate;
    private DateTimeFormatter formatter;
    private DateTimeFormatter formatterTime;
    
    @Inject
    private ViewForumManagedBean viewForumManagedBean;

    public ForumPostManagementBean() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    }
    
    @PostConstruct
    public void postConstruct() {
        forumPosts = forumPostsEntitySessionBeanLocal.retrieveAllForumPosts();
    }

    public void banForum(ActionEvent event) {
        selectedForumPostEntityToUpdate = (ForumPostEntity) event.getComponent().getAttributes().get("forumPostToBan");
        selectedForumPostEntityToUpdate.setIsBanned(true);
        System.out.println("erorrr");
        try {
            forumPostsEntitySessionBeanLocal.changeBannedStatus(selectedForumPostEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum post has been successfully banned", null));
            selectedForumPostEntityToUpdate = null;
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void unbanForum(ActionEvent event) {
        selectedForumPostEntityToUpdate = (ForumPostEntity) event.getComponent().getAttributes().get("forumPostToUnban");
        selectedForumPostEntityToUpdate.setIsBanned(false);
        try {
            forumPostsEntitySessionBeanLocal.changeBannedStatus(selectedForumPostEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum post has been successfully unbanned", null));
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    

    public List<ReplyEntity> getListOfComments() {
        return listOfComments;
    }

    public void setListOfComments(List<ReplyEntity> listOfComments) {
        this.listOfComments = listOfComments;
    }


    public ForumPostsEntitySessionBeanLocal getForumPostsEntitySessionBeanLocal() {
        return forumPostsEntitySessionBeanLocal;
    }

    public void setForumPostsEntitySessionBeanLocal(ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal) {
        this.forumPostsEntitySessionBeanLocal = forumPostsEntitySessionBeanLocal;
    }

    public List<ForumPostEntity> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<ForumPostEntity> forumPosts) {
        this.forumPosts = forumPosts;
    }

    public ForumPostEntity getSelectedForumPostEntityToUpdate() {
        return selectedForumPostEntityToUpdate;
    }

    public void setSelectedForumPostEntityToUpdate(ForumPostEntity selectedForumPostEntityToUpdate) {
        this.selectedForumPostEntityToUpdate = selectedForumPostEntityToUpdate;
    }

    public List<ForumPostEntity> getFilteredForumPosts() {
        return filteredForumPosts;
    }

    public void setFilteredForumPosts(List<ForumPostEntity> filteredForumPosts) {
        this.filteredForumPosts = filteredForumPosts;
    }


    public ReplyEntity getReplyEntityToUpdate() {
        return replyEntityToUpdate;
    }

    public void setReplyEntityToUpdate(ReplyEntity replyEntityToUpdate) {
        this.replyEntityToUpdate = replyEntityToUpdate;
    }

    public ViewForumManagedBean getViewForumManagedBean() {
        return viewForumManagedBean;
    }

    public void setViewForumManagedBean(ViewForumManagedBean viewForumManagedBean) {
        this.viewForumManagedBean = viewForumManagedBean;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatterTime() {
        return formatterTime;
    }

    public void setFormatterTime(DateTimeFormatter formatterTime) {
        this.formatterTime = formatterTime;
    }

}
