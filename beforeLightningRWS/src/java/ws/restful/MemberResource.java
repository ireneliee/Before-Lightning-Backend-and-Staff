/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.CreditCardEntity;
import entity.MemberEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import util.exception.AddressEntityNotFoundException;
import util.exception.CreditCardEntityNotFoundException;
import util.exception.DeleteAddressEntityException;
import util.exception.DeleteCreditCardEntityException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberEntityNotFoundException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.UnknownPersistenceException;
import ws.datamodel.CreateNewAddressReq;
import ws.datamodel.CreateNewCreditCardReq;
import ws.datamodel.CreateNewMemberReq;
import ws.datamodel.UpdateMemberReq;

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

        System.out.println("============= LOGIN MEMBER =============");
        System.out.println(username);
        System.out.println(password);
        try {
            MemberEntity memberEntity = memberEntitySessionBeanLocal.memberEntityLogin(username, password);
            System.out.println("********** MemberResource.memberLogin(): Member " + memberEntity.getUsername() + " login remotely via web service");
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
            System.out.println("======================================");
            System.out.println("credit card size " + memberEntity.getCreditCards().size());
            for (CreditCardEntity card : memberEntity.getCreditCards()) {
                System.out.println("cardId:" + card.getCreditCardEntityId());
                System.out.println("cardNum:" + card.getCreditCardNumber());
                System.out.println("cardExpDate:" + card.getExpiryDate());
            }
            for (AddressEntity add : memberEntity.getAddresses()) {
                System.out.println("AddressId:" + add.getAddressEntityId());
                System.out.println("Address block:" + add.getBlock());
                System.out.println("Address country:" + add.getCountry());
            }
            System.out.println("sending out " + memberEntity);
            System.out.println("======================================");
            return Response.status(Status.OK).entity(memberEntity).build();
        } catch (InvalidLoginCredentialException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewMember(CreateNewMemberReq createNewMemberReq) {
        System.out.println("======== CALLING RWS CREATE NEW MEMBER ========");

        AddressEntity reqAddress = createNewMemberReq.getAddress();

        String username = createNewMemberReq.getUsername();
        String password = createNewMemberReq.getPassword();
        String firstname = createNewMemberReq.getFirstname();
        String lastname = createNewMemberReq.getLastname();
        String email = createNewMemberReq.getEmail();
        String contact = createNewMemberReq.getContact();
        String imageLink = createNewMemberReq.getImageLink();
        System.out.println("REQ MEMBER===");
        System.out.println(username);
        System.out.println(password);
        System.out.println(firstname);
        System.out.println(lastname);
        System.out.println(email);
        System.out.println(contact);
        System.out.println(imageLink);
        System.out.println("=======");

        MemberEntity member = new MemberEntity(username, password, firstname, lastname, email, contact);
//        member.setUsername(reqMember.getUsername());
//        member.setPassword(reqMember.getPassword());
//        member.setFirstname(reqMember.getFirstname());
//        member.setLastname(reqMember.getLastname());
//        member.setContact(reqMember.getContact());
//        member.setEmail(reqMember.getEmail());
        if (imageLink != "" && imageLink != null) {
            member.setImageLink(imageLink);
        }

        System.out.println("============MEMBER============");
//        System.out.println(member.getUserEntityId());
        System.out.println(member.getUsername());
        System.out.println(member.getPassword());
        System.out.println(member.getFirstname());
        System.out.println(member.getLastname());
        System.out.println(member.getContact());
        System.out.println(member.getEmail());

        AddressEntity address = new AddressEntity();
        address.setCountry(reqAddress.getCountry());
        address.setBlock(reqAddress.getBlock());
        address.setPostalCode(reqAddress.getPostalCode());
        address.setUnit(reqAddress.getUnit());

        System.out.println("============ADDRESS============");
//        System.out.println(address.getAddressEntityId());
        System.out.println(address.getCountry());
        System.out.println(address.getBlock());
        System.out.println(address.getUnit());
        System.out.println(address.getPostalCode());

        if (createNewMemberReq != null) {
            Long memberEntityId;
            try {
                memberEntityId = memberEntitySessionBeanLocal.createNewMemberEntity(member, address);
                System.out.println("********** MemberResource.createNewMember(): Member " + memberEntityId);

                MemberEntity createdMember = memberEntitySessionBeanLocal.retrieveMemberEntityByMemberEntityId(memberEntityId);

                return Response.status(Response.Status.OK).entity(createdMember).build();

            } catch (MemberEntityUsernameExistException | InputDataValidationException | UnknownPersistenceException | AddressEntityNotFoundException ex) {
                System.out.println(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new Member request").build();
        }
    }

    @Path("updateMember")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMemberDetails(UpdateMemberReq updateMemberReq) {
        System.out.println("Username received: " + updateMemberReq.getUsername());

        if (updateMemberReq != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(updateMemberReq.getUsername());

                memberEntity.setFirstname(updateMemberReq.getFirstname());
                memberEntity.setLastname(updateMemberReq.getLastname());
                memberEntity.setContact(updateMemberReq.getContact());
                memberEntity.setEmail(updateMemberReq.getEmail());
                memberEntity.setImageLink(updateMemberReq.getImageLink());

                return Response.status(Response.Status.OK).build();

            } catch (MemberEntityNotFoundException ex) {

                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();

            }
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();
        }
    }

    @Path("addAddress")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAddress(CreateNewAddressReq createNewAddressReq) {

        if (createNewAddressReq != null) {

            try {
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setBlock(createNewAddressReq.getBlock());
                addressEntity.setCountry(createNewAddressReq.getCountry());
                addressEntity.setPostalCode(createNewAddressReq.getPostalCode());
                addressEntity.setUnit(createNewAddressReq.getUnit());

                memberEntitySessionBeanLocal.addAddress(Long.parseLong(createNewAddressReq.getMemberId()), addressEntity);
                return Response.status(Response.Status.OK).build();
            } catch (InputDataValidationException | AddressEntityNotFoundException | UnknownPersistenceException | MemberEntityNotFoundException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }

        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();

        }
    }

    @Path("addCreditCard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCreditCard(CreateNewCreditCardReq createNewCreditCardReq) {

        if (createNewCreditCardReq != null) {

            try {
                CreditCardEntity card = new CreditCardEntity();
                card.setCreditCardNumber(Long.parseLong(createNewCreditCardReq.getCreditCardNumber()));
                card.setExpiryDate(createNewCreditCardReq.getExpiryDate());
                card.setNameOnCard(createNewCreditCardReq.getNameOnCard());
                memberEntitySessionBeanLocal.createNewCreditCardEntity(Long.parseLong(createNewCreditCardReq.getMemberId()), card);

                return Response.status(Response.Status.OK).build();
            } catch (InputDataValidationException | UnknownPersistenceException | MemberEntityNotFoundException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }

        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();

        }
    }

    @Path("deleteCreditCard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCard(@QueryParam("creditCardId") String creditCardId, @QueryParam("memberId") String memberId) {

        if (creditCardId != null && memberId != null) {

            try {
                memberEntitySessionBeanLocal.deleteCreditCardEntity(Long.parseLong(memberId), Long.parseLong(creditCardId));

                return Response.status(Response.Status.OK).build();
            } catch (CreditCardEntityNotFoundException | DeleteCreditCardEntityException | MemberEntityNotFoundException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();

            }

        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();

        }
    }
    
        @Path("deleteAddress")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@QueryParam("addressId") String addressId, @QueryParam("memberId") String memberId) {

        if (addressId != null && memberId != null) {

         
            try {
                memberEntitySessionBeanLocal.deleteAddressEntity(Long.parseLong(memberId), Long.parseLong(addressId));

                return Response.status(Response.Status.OK).build();
            } catch (AddressEntityNotFoundException | DeleteAddressEntityException | MemberEntityNotFoundException ex) {
                return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
           

            

        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured").build();

        }
    }

    @Path("retrieveMemberById")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMemberById(@QueryParam("memberEntityId") String memberEntityId) {
        if (memberEntityId != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByMemberEntityId(Long.parseLong(memberEntityId));
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

                return Response.status(Status.OK).entity(memberEntity).build();

            } catch (MemberEntityNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Member ID is not valid").build();

            }

        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Member ID not provided.").build();
        }

    }
    
    @Path("retrieveMemberByUsername")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMemberByUsername(@QueryParam("username") String username) {
        if (username != null) {
            try {
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(username);
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

                return Response.status(Status.OK).entity(memberEntity).build();

            } catch (MemberEntityNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Member username is not valid").build();

            }

        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Member ID not provided.").build();
        }

    }
}
