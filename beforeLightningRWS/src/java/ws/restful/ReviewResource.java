/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import entity.MemberEntity;
import entity.ReviewEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import ws.datamodel.CreateNewReviewReq;

/**
 *
 * @author irene
 */
@Path("ReviewResource")
public class ReviewResource {

    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    private final ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    public ReviewResource() {
        sessionBeanLookup = new SessionBeanLookup();
        reviewEntitySessionBeanLocal = sessionBeanLookup.lookupReviewEntitySessionBeanLocal();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("createNewReviewForAcc")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewReviewForAcc(CreateNewReviewReq newRevReq) {
        System.out.println("===========Calling create review for acc RWS =================");
        System.out.println("Item ID received: " + newRevReq.getItemId());

        if (newRevReq != null) {
            try {
                String customerUsername = newRevReq.getCustomerUsername();
                Integer rating = newRevReq.getRating();
                String description = newRevReq.getDescription();
                Integer accId = newRevReq.getItemId();
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(customerUsername);
                Long accIdInLong = new Long(accId);

                ReviewEntity newReviewEntity = new ReviewEntity(customerUsername, rating, description);
                try {
                    Long id = reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(newReviewEntity, accIdInLong);
                    return Response.status(Response.Status.OK).entity(id).build();

                } catch (InputDataValidationException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                } catch (UnknownPersistenceException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                }

            } catch (MemberEntityNotFoundException ex) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Request body can't be retrieved").build();
        }

    }

    @Path("createNewReviewForProd")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewReviewEntityForProduct(CreateNewReviewReq newRevReq) {
        System.out.println("===========Calling create review for acc RWS =================");
        System.out.println("Item ID received: " + newRevReq.getItemId());

        if (newRevReq != null) {
            try {
                String customerUsername = newRevReq.getCustomerUsername();
                Integer rating = newRevReq.getRating();
                String description = newRevReq.getDescription();
                Integer prodId = newRevReq.getItemId();
                MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(customerUsername);
                Long prodIdInLong = new Long(prodId);

                ReviewEntity newReviewEntity = new ReviewEntity(customerUsername, rating, description);
                try {
                    Long id = reviewEntitySessionBeanLocal.createNewReviewEntityForProduct(newReviewEntity, prodIdInLong);
                    return Response.status(Response.Status.OK).entity(id).build();

                } catch (InputDataValidationException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                } catch (UnknownPersistenceException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
                }

            } catch (MemberEntityNotFoundException ex) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Request body can't be retrieved").build();
        }

    }

}
