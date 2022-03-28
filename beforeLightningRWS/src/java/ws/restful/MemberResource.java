/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.CreditCardEntity;
import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.PromotionEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Koh Wen Jie
 */
@Path("Member")
public class MemberResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    public MemberResource() {
        sessionBeanLookup = new SessionBeanLookup();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("retrieveMembers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllMembers() {
        try {
            List<MemberEntity> memberEntities = memberEntitySessionBeanLocal.retrieveAllMemberEntities();

            GenericEntity<List<MemberEntity>> genericEntity = new GenericEntity<List<MemberEntity>>(memberEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("memberLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberLogin(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            MemberEntity memberEntity = memberEntitySessionBeanLocal.memberEntityLogin(username, password);
            System.out.println("********** MemberResource.memberLogin(): Member " + memberEntity.getUsername() + " login remotely via web service");
            memberEntity.setPassword("00000000");
            memberEntity.setSalt("00000000000000000000000000000000");
            memberEntity.getAddresses().size();
            memberEntity.getPurchaseOrders().clear();
            if (memberEntity.getShoppingCart() != null) {
                for (PurchaseOrderLineItemEntity poli : memberEntity.getShoppingCart().getPurchaseOrderLineItemEntities()) {
                    poli.getPartChoiceEntities().clear();
                    poli.setAccessoryItemEntity(null);
                    poli.setProductEntity(null);
                }
            }
            memberEntity.getForumPosts().clear();
            memberEntity.getForumReplies().clear();
            memberEntity.getPostsDisliked().clear();
            memberEntity.getPostsLiked().clear();
            System.out.println("======================================");
            System.out.println("sending out " + memberEntity);
            System.out.println("======================================");
            return Response.status(Status.OK).entity(memberEntity).build();
        } catch (InvalidLoginCredentialException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}

//            if (!memberEntity.getCreditCards().isEmpty()) {
//                for (CreditCardEntity cc : memberEntity.getCreditCards()) {
//                    cc.setMemberEntity(null);
//                }
//            }
//            if (!memberEntity.getPurchaseOrders().isEmpty()) {
//                for (PurchaseOrderEntity po : memberEntity.getPurchaseOrders()) {
//                    po.setMember(null);
//                    for (PurchaseOrderLineItemEntity poli : po.getPurchaseOrderLineItems()) {
//                        if (!poli.getAccessoryItemEntity().equals(null)) {
//                            poli.getAccessoryItemEntity().getAccessoryEntity().getAccessoryItemEntities().clear();
//                            if (!poli.getAccessoryItemEntity().getPromotionEntities().isEmpty()) {
//                                for (PromotionEntity promo : poli.getAccessoryItemEntity().getPromotionEntities()) {
//                                    promo.getAccessoryItemEntities().clear();
//                                    promo.getPartChoiceEntities().clear();
//                                }
//                            }
//                        }
//                        
//                        if (!poli.getProductEntity().equals(null)) {
//                            for (PartEntity part : poli.getProductEntity().getPartEntities()) {
//                                part.getProductEntities().clear();
//                                for (PartChoiceEntity pc : part.getPartChoiceEntities()) {
//                                    pc.getCompatibleChassisPartChoiceEntities().clear();
//                                    pc.getCompatiblePartsPartChoiceEntities().clear();
//                                    pc.getPromotionEntities().clear();
//                                    
//                                }
//                            }
//                        }
//                        if (!poli.getPartChoiceEntities().isEmpty()) {
//                            for (PartChoiceEntity pc :poli.getPartChoiceEntities()) {
//                                pc.getCompatibleChassisPartChoiceEntities().clear();
//                                pc.getCompatiblePartsPartChoiceEntities().clear();
//                                pc.getPromotionEntities().clear();
//                            }
//                        }
//                    }
//                }
//            }
