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
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;

/**
 *
 * @author irene
 */
@Stateless
public class ForumPostsEntitySessionBean implements ForumPostsEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ForumPostsEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    public List<ForumPostEntity> retrieveAllViewableForumPost() {
        String queryInString = "SELECT f FROM ForumPostEntity f WHERE f.isVisible = :iVisible AND f.isBanned = :iBanned";
        Query query = em.createQuery(queryInString);
        query.setParameter("iVisible", true);
        query.setParameter("iBanned", false);
        return query.getResultList();
    }

    @Override
    public Long createNewReplyEntity(ReplyEntity newComment, Long forumPostId) throws ForumPostNotFoundException, MemberEntityNotFoundException, InputDataValidationException {
        ForumPostEntity forumPost = em.find(ForumPostEntity.class, forumPostId);
        if (forumPost == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found. ");
        }


        Set<ConstraintViolation<ReplyEntity>> constraintViolations = validator.validate(newComment);

        if (constraintViolations.isEmpty()) {
            MemberEntity authorMember = em.find(MemberEntity.class, newComment.getAuthor().getUserEntityId());
            if (authorMember == null) {
                throw new MemberEntityNotFoundException("Member entity cannot be found!");
            }
            newComment.setAuthor(authorMember);
            em.persist(newComment);
            authorMember.getForumReplies().add(newComment);
            
                    
            forumPost.getReplies().add(newComment);
            newComment.setForumPost(forumPost);
            
            em.flush();
            return newComment.getReplyEntityId();
            
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForReply(constraintViolations));
        }

    }
    
    

    @Override
    public Long createNewForumPostEntity(ForumPostEntity newPost) throws InputDataValidationException, MemberEntityNotFoundException {
        Set<ConstraintViolation<ForumPostEntity>> constraintViolations = validator.validate(newPost);

        if (constraintViolations.isEmpty()) {
            MemberEntity authorMember = em.find(MemberEntity.class, newPost.getAuthor().getUserEntityId());
            if (authorMember == null) {
                throw new MemberEntityNotFoundException("Member entity cannot be found!");
            }
            newPost.setAuthor(authorMember);
            em.persist(newPost);

            authorMember.getForumPosts().add(newPost);

            em.flush();
            return newPost.getForumPostEntityId();

        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<ForumPostEntity> retrieveForumPostByUsername(String username) {
        String queryInString = "SELECT f FROM ForumPostEntity f WHERE f.author.username = :iUsername";
        Query query = em.createQuery(queryInString);
        query.setParameter("iUsername", username);
        return query.getResultList();
    }

    @Override
    public List<ForumPostEntity> retrieveAllForumPosts() {
        String queryInString = "SELECT f FROM ForumPostEntity f";
        Query query = em.createQuery(queryInString);
        return query.getResultList();
    }
    
    @Override
    public List<ReplyEntity> retrieveReplyByForumId(Long forumPostId) throws ForumPostNotFoundException {
        ForumPostEntity fp = em.find(ForumPostEntity.class, forumPostId);
        if(fp == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found. ");
        } else {
            String queryInString = "SELECT r FROM ReplyEntity r WHERE r.forumPost = :iForumPost";
            Query query = em.createQuery(queryInString);
            query.setParameter("iForumPost", fp);
            return query.getResultList();
        }
    }
    
    @Override
    public void updateReplyEntity(ReplyEntity updatedReply) {
        ReplyEntity re = em.find(ReplyEntity.class, updatedReply.getReplyEntityId());
        re.setContent(updatedReply.getContent());
        re.setImageLink(updatedReply.getImageLink());
        re.setIsVisible(updatedReply.getIsVisible());
        re.setIsBanned(updatedReply.getIsBanned());
        
    }

    @Override
    public void likes(ForumPostEntity post, MemberEntity member) throws MemberEntityNotFoundException, ForumPostNotFoundException {
        MemberEntity likeMember = em.find(MemberEntity.class, post.getAuthor().getUserEntityId());
        if (likeMember == null) {
            throw new MemberEntityNotFoundException("Member entity cannot be found!");
        }
        ForumPostEntity postLiked = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postLiked == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }

        likeMember.getPostsLiked().add(postLiked);
        postLiked.getUserWhoLikes().add(likeMember);

    }

    @Override
    public void dislikes(ForumPostEntity post, MemberEntity member) throws MemberEntityNotFoundException, ForumPostNotFoundException {
        MemberEntity dislikeMember = em.find(MemberEntity.class, post.getAuthor().getUserEntityId());
        if (dislikeMember == null) {
            throw new MemberEntityNotFoundException("Member entity cannot be found!");
        }
        ForumPostEntity postDisliked = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postDisliked == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }

        dislikeMember.getPostsLiked().add(postDisliked);
        postDisliked.getUserWhoLikes().add(dislikeMember);

    }

    @Override
    public void updateContent(ForumPostEntity post) throws ForumPostNotFoundException {
        ForumPostEntity postToUpdate = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postToUpdate == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
        postToUpdate.setImageLink(post.getImageLink());
    }

    @Override
    public void changeVisibility(ForumPostEntity post) throws ForumPostNotFoundException {
        ForumPostEntity postToUpdate = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postToUpdate == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }
        postToUpdate.setIsVisible(post.getIsVisible());
    }

    @Override
    public void changeBannedStatus(ForumPostEntity post) throws ForumPostNotFoundException {
        ForumPostEntity postToUpdate = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postToUpdate == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }
        postToUpdate.setIsBanned(post.getIsBanned());
    }

    @Override
    public ForumPostEntity viewForumPostDetails(ForumPostEntity post) throws ForumPostNotFoundException {
        ForumPostEntity postToView = em.find(ForumPostEntity.class, post.getForumPostEntityId());
        if (postToView == null) {
            throw new ForumPostNotFoundException("Forum post cannot be found!");
        }
        return postToView;

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ForumPostEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
    
     private String prepareInputDataValidationErrorsMessageForReply(Set<ConstraintViolation<ReplyEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
