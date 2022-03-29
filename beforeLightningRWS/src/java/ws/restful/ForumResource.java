/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.PurchaseOrderLineItemEntity;
import entity.ReplyEntity;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

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

    public ForumResource() {
        sessionBeanLookup = new SessionBeanLookup();
        forumPostsEntitySessionBeanLocal = sessionBeanLookup.lookupForumPostsEntitySessionBeanLocal();

    }

    @Path("retrieveAllForumPosts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumPosts() {
        try {
            List<ForumPostEntity> forumPosts = forumPostsEntitySessionBeanLocal.retrieveAllForumPosts();
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
                
                List<MemberEntity> listOfUserWhoDislikesThePost = f.getUserWhoDislikes();
                for(MemberEntity m: listOfUserWhoDislikesThePost) {
                    handleMemberUnmarshalling(m);
                }
                
                MemberEntity forumAuthor = f.getAuthor();
                handleMemberUnmarshalling(forumAuthor);
            }

            GenericEntity<List<ForumPostEntity>> genericEntity = new GenericEntity<List<ForumPostEntity>>(forumPosts) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    private void handleMemberUnmarshalling(MemberEntity memberToUnmarshall) {
        memberToUnmarshall.setPassword("00000000");
        memberToUnmarshall.setSalt("00000000000000000000000000000000");
        memberToUnmarshall.getAddresses().size();
        memberToUnmarshall.getPurchaseOrders().clear();
        if (memberToUnmarshall.getShoppingCart() != null) {
            for (PurchaseOrderLineItemEntity poli : memberToUnmarshall.getShoppingCart().getPurchaseOrderLineItemEntities()) {
                poli.getPartChoiceEntities().clear();
                poli.setAccessoryItemEntity(null);
                poli.setProductEntity(null);
            }
        }

        memberToUnmarshall.getForumPosts().clear();
        memberToUnmarshall.getForumReplies().clear();
        memberToUnmarshall.getPostsDisliked().clear();
        memberToUnmarshall.getPostsLiked().clear();
    }

}
