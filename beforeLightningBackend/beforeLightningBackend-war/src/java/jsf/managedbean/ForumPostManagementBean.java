/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import entity.ForumPostEntity;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

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
    
    public ForumPostManagementBean() {
    }
    
    public void postConstruct() {
    }
    
    public void setBanned(ActionEvent event) {
        
    }
    
}
