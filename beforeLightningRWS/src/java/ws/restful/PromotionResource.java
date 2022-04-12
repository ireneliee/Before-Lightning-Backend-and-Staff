/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author irene
 */
@Path("Promotion")
public class PromotionResource {

    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;
    private final SessionBeanLookup sessionBeanLookup;
    private final PromotionEntitySessionBeanLocal promotionEntitySessionBeanLocal;
    
    public PromotionResource() {
        sessionBeanLookup = new SessionBeanLookup();
        promotionEntitySessionBeanLocal= sessionBeanLookup.promotionEntitySessionBean;
    }
    
    @Path("retrieveAllOngoingPromotion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllOngoingPromotion() {
        System.out.println("============Retrieving all ongoing promotion===============");
        List<PromotionEntity> listOfPromotionEntities = promotionEntitySessionBeanLocal.retrieveAllOngoingPromotion();
        listOfPromotionEntities.forEach(x -> unmarshallSinglePromotionEntity(x));
        GenericEntity<List<PromotionEntity>> genericEntity = new GenericEntity<List<PromotionEntity>>(listOfPromotionEntities) {};
        return Response.status(Response.Status.OK).entity(genericEntity).build();
    }
    
    private void unmarshallSinglePromotionEntity(PromotionEntity p) {
        List<PartChoiceEntity> listOfPartChoices = p.getPartChoiceEntities();
        listOfPartChoices.forEach(x -> unmarshallSinglePartChoiceEntity(x));
        
        List<AccessoryItemEntity> listOfAccessories = p.getAccessoryItemEntities();
        listOfAccessories.forEach(x -> unmarshallSingleAccessoryItemEntity(x));
        
        
    }
    
    
    private void unmarshallSingleAccessoryItemEntity(AccessoryItemEntity ae) {
        //System.out.println("=============Unmarshalling single promotion========================");
        ae.getPromotionEntities().clear();
        ae.getReviewEntities().clear();
        ae.setAccessoryEntity(null);
    }

    private void unmarshallSinglePartChoiceEntity(PartChoiceEntity pc) {
        //System.out.println("===============Unmarshalling single part choice=================");
        pc.getCompatiblePartsPartChoiceEntities().clear();
        pc.getCompatibleChassisPartChoiceEntities().clear();
        pc.getPromotionEntities().clear();
    }

}
