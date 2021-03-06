/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.CreditCardEntity;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.PurchaseOrderLineItemEntity;
import entity.ReplyEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;
import ws.datamodel.CreateNewReplyReq;
import ws.datamodel.UpdateForumReq;

/**
 *
 * @author irene
 */
@Path("ForumPosts")
public class ForumResource {

    @Context
    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    private final ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    public ForumResource() {
        sessionBeanLookup = new SessionBeanLookup();
        forumPostsEntitySessionBeanLocal = sessionBeanLookup.lookupForumPostsEntitySessionBeanLocal();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();

    }

    @Path("retrieveAllForumPosts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumPosts() {
        try {
            List<ForumPostEntity> forumPosts = forumPostsEntitySessionBeanLocal.retrieveAllViewableForumPost();
            handleForumPostsUnmarshalling(forumPosts);
            GenericEntity<List<ForumPostEntity>> genericEntity = new GenericEntity<List<ForumPostEntity>>(forumPosts) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("createNewReply")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postReply(CreateNewReplyReq createNewReplyReq) {
        System.out.println("Username received: " + createNewReplyReq.getUsername());
        System.out.println("Content received: " + createNewReplyReq.getContent());

        if (createNewReplyReq != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(createNewReplyReq.getUsername());

                try {
                    ForumPostEntity f = forumPostsEntitySessionBeanLocal.retrieveForumPostById(createNewReplyReq.getForumId());

                    ReplyEntity newReply = new ReplyEntity(createNewReplyReq.getContent(), memberEntity, f);

                    try {

                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(newReply, f.getForumPostEntityId());

                        return Response.status(Response.Status.OK).build();

                    } catch (InputDataValidationException ex) {

                        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                    }

                } catch (ForumPostNotFoundException ex) {
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                }
            } catch (MemberEntityNotFoundException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Request body can't be retrieved").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateForumPost(UpdateForumReq updateForumPostReq
    ) {
        System.out.println("Username received: " + updateForumPostReq.getUsername());
        System.out.println("Content received: " + updateForumPostReq.getContent());
        System.out.println("Visibility received: " + updateForumPostReq.getVisibility());

        if (updateForumPostReq != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(updateForumPostReq.getUsername());

                try {
                    ForumPostEntity f = forumPostsEntitySessionBeanLocal.retrieveForumPostById(updateForumPostReq.getForumPostId());

                    if (!f.getAuthor().equals(memberEntity)) {

                        return Response.status(Status.UNAUTHORIZED).entity("You are not supposed to change posts that belong to other people").build();
                    }

                    f.setContent(updateForumPostReq.getContent());

                    f.setIsVisible(updateForumPostReq.getVisibility());

                    forumPostsEntitySessionBeanLocal.updateContent(f);
                    forumPostsEntitySessionBeanLocal.changeVisibility(f);

                    return Response.status(Response.Status.OK).build();

                } catch (ForumPostNotFoundException ex) {

                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();

                }
            } catch (MemberEntityNotFoundException ex) {

                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();

            }
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();
        }
    }

    @Path("retrieveForumPostById")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForumByForumId(@QueryParam("forumId") String forumId
    ) {
        if (forumId != null) {
            try {
                ForumPostEntity forumToRetrieve = forumPostsEntitySessionBeanLocal.retrieveForumPostById(Long.parseLong(forumId));
                List<ForumPostEntity> listOfForumPosts = new ArrayList<>();
                listOfForumPosts.add(forumToRetrieve);
                handleForumPostsUnmarshalling(listOfForumPosts);
                GenericEntity<ForumPostEntity> genericEntity = new GenericEntity<ForumPostEntity>(forumToRetrieve) {
                };
                return Response.status(Status.OK).entity(genericEntity).build();
            } catch (ForumPostNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Forum ID is not valid").build();

            }

        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Forum ID not provided.").build();
        }

    }

    @Path("changeLikes")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeLikes(@QueryParam("postId") String postId, @QueryParam("username") String username) {
        System.out.println("Change likes in rws called.");
        //System.out.println("Reach a");
        try {
            Long postIdInLong = Long.parseLong(postId);
            //System.out.println("Reach b");
            forumPostsEntitySessionBeanLocal.changeLikes(postIdInLong, username);
            //System.out.println("Reach c");
            return Response.status(Status.OK).entity(postIdInLong).build();

        } catch (MemberEntityNotFoundException ex) {
            //System.out.println("Reach d");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Member entity not found.").build();

        } catch (ForumPostNotFoundException ex) {
            //System.out.println("Reach e");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Forum post not found.").build();
        }
    }

    @Path("checkUserLikes")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUserLikes(@QueryParam("postId") String postId, @QueryParam("username") String username) {

        System.out.println("Check if user likes in RWS called");
        try {
            Long postIdInLong = Long.parseLong(postId);
            //System.out.println("Reach b");
            boolean checkResult = forumPostsEntitySessionBeanLocal.userLikes(postIdInLong, username);
            //System.out.println("Reach c");
            return Response.status(Status.OK).entity(checkResult).build();

        } catch (MemberEntityNotFoundException ex) {
            //System.out.println("Reach d");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Member entity not found.").build();

        } catch (ForumPostNotFoundException ex) {
            //System.out.println("Reach e");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Forum post not found.").build();
        }
    }

    @Path("retrieveMyForumPosts")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveForumPostsByUsername(@QueryParam("username") String username
    ) {
        if (username != null) {
            List<ForumPostEntity> forumPosts = forumPostsEntitySessionBeanLocal.retrieveForumPostByUsername(username);
            handleForumPostsUnmarshalling(forumPosts);
            GenericEntity<List<ForumPostEntity>> genericEntity = new GenericEntity<List<ForumPostEntity>>(forumPosts) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Username not provided").build();
        }
    }

    @Path("createNewForum")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForumPost(@QueryParam("username") String username,
            @QueryParam("title") String title,
            @QueryParam("content") String content,
            @QueryParam("filename") String filename
    ) {
        System.out.println("Username is" + username);
        System.out.println("Title is" + title);
        System.out.println("Content is" + content);
        if (username != null && title != null && content != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(username);
                ForumPostEntity newForumToMake = new ForumPostEntity(title, content, filename, memberEntity);
                Long forumPost = forumPostsEntitySessionBeanLocal.createNewForumPostEntity(newForumToMake);

                return Response.status(Response.Status.OK).entity(forumPost).build();

            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new forum request").build();
        }
    }

    private void handleMemberUnmarshalling(MemberEntity memberEntity) {
        memberEntity.setPassword("00000000");
        memberEntity.setSalt("00000000000000000000000000000000");
        memberEntity.getAddresses().size();
        memberEntity.getPurchaseOrders().clear();

        for (CreditCardEntity card : memberEntity.getCreditCards()) {
            card.setMemberEntity(null);
        }
        if (memberEntity.getShoppingCart() != null) {
            for (PurchaseOrderLineItemEntity poli : memberEntity.getShoppingCart().getPurchaseOrderLineItemEntities()) {
                poli.getPartChoiceEntities().clear();
                poli.setAccessoryItemEntity(null);
                poli.setProductEntity(null);
            }
        }
        memberEntity.getForumPosts().clear();
        memberEntity.getForumReplies().clear();
        //memberEntity.getPostsDisliked().clear();
        memberEntity.getPostsLiked().clear();
    }

    private void handleForumPostsUnmarshalling(List<ForumPostEntity> forumPosts) {
        for (ForumPostEntity f : forumPosts) {
            List<ReplyEntity> replies = f.getReplies();
            for (ReplyEntity r : replies) {
                MemberEntity replyAuthor = r.getAuthor();
                handleMemberUnmarshalling(replyAuthor);
                r.setForumPost(null);
            }

            List<MemberEntity> listOfUserWhoLikesThePost = f.getUserWhoLikes();
            for (MemberEntity m : listOfUserWhoLikesThePost) {
                handleMemberUnmarshalling(m);
            }

//            List<MemberEntity> listOfUserWhoDislikesThePost = f.getUserWhoDislikes();
//            for (MemberEntity m : listOfUserWhoDislikesThePost) {
//                handleMemberUnmarshalling(m);
//            }
            MemberEntity forumAuthor = f.getAuthor();
            handleMemberUnmarshalling(forumAuthor);
        }
    }

}
