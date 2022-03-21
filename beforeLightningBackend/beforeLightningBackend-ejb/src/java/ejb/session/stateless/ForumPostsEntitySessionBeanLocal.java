/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.ReplyEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;

/**
 *
 * @author irene
 */
@Local
public interface ForumPostsEntitySessionBeanLocal {

    public ForumPostEntity viewForumPostDetails(ForumPostEntity post) throws ForumPostNotFoundException;

    public void changeBannedStatus(ForumPostEntity post) throws ForumPostNotFoundException;

    public void changeVisibility(ForumPostEntity post) throws ForumPostNotFoundException;

    public void updateContent(ForumPostEntity post) throws ForumPostNotFoundException;

    public void dislikes(ForumPostEntity post, MemberEntity member) throws MemberEntityNotFoundException, ForumPostNotFoundException;

    public void likes(ForumPostEntity post, MemberEntity member) throws MemberEntityNotFoundException, ForumPostNotFoundException;

    public List<ForumPostEntity> retrieveAllForumPosts();

    public List<ForumPostEntity> retrieveForumPostByUsername(String username);

    public Long createNewForumPostEntity(ForumPostEntity newPost) throws InputDataValidationException, MemberEntityNotFoundException;

    public Long createNewReplyEntity(ReplyEntity newComment, Long forumPostId) throws ForumPostNotFoundException, MemberEntityNotFoundException, InputDataValidationException;

    public List<ReplyEntity> retrieveReplyByForumId(Long forumPostId) throws ForumPostNotFoundException;

    public void updateReplyEntity(ReplyEntity updatedReply);

    
}
