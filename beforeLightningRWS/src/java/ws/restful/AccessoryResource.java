/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.ProductEntity;
import entity.PromotionEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Koh Wen Jie
 */
@Path("Accessory")
public class AccessoryResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    public AccessoryResource() {
        sessionBeanLookup = new SessionBeanLookup();
        accessoryEntitySessionBeanLocal = sessionBeanLookup.lookupAccessoryEntitySessionBeanLocal();
    }

    @Path("retrieveAllAccessoryToSell")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAccessoryToSell() {
        try {
            System.out.println(" ============== RETRIEVE Accessory TO SELL ===============");
            List<AccessoryEntity> accessoryEntities = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntitiesNotDisabled();
            System.out.println("PROCESSING SIZE: " + accessoryEntities.size());
            List<AccessoryEntity> listOfAccessoryEntitiesToSend = new ArrayList<>();
            
            // =============== UNMARSHALLING =============
            for (AccessoryEntity accessory : accessoryEntities) {
                System.out.println("here");
                
                List<AccessoryItemEntity> listOfAccessoryItemEntitiesToSend = new ArrayList<>();
                
                for (AccessoryItemEntity accessoryItem : accessory.getAccessoryItemEntities()) {
                    System.out.println("here 2");
                    accessoryItem.setAccessoryEntity(null);
                    System.out.println("here 3");
                    accessoryItem.getReviewEntities().size();

                    for (PromotionEntity promo : accessoryItem.getPromotionEntities()) {
                        System.out.println("here 4");
                        promo.getAccessoryItemEntities().clear();
                        promo.getPartChoiceEntities().clear();
                        System.out.println("here 5");
                    }
                    System.out.println("hereeeee");
                    if(accessoryItem.getIsDisabled() == false) {
                        listOfAccessoryItemEntitiesToSend.add(accessoryItem);
                    }
                }
                System.out.println("OUT HERE NOW");
                
                if (!listOfAccessoryItemEntitiesToSend.isEmpty()) {
                    accessory.setAccessoryItemEntities(listOfAccessoryItemEntitiesToSend);
                    listOfAccessoryEntitiesToSend.add(accessory);
                }
            }
            System.out.println("SENDING OUT Accessories.... TOTAL SIZE: " + listOfAccessoryEntitiesToSend.size());

            GenericEntity<List<AccessoryEntity>> genericEntity = new GenericEntity<List<AccessoryEntity>>(listOfAccessoryEntitiesToSend) {
            };
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveAccessoryToSellById/{accessoryId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAccessoryToSellById(@PathParam("accessoryId") Long accessoryId) {
        try {
            System.out.println(" ============== RETRIEVE Accessory TO SELL ===============");
            List<AccessoryEntity> accessoryEntities = accessoryEntitySessionBeanLocal.retrieveAllAccessoryEntitiesNotDisabled();
            System.out.println("PROCESSING SIZE: " + accessoryEntities.size());

            // =============== UNMARSHALLING =============
            for (AccessoryEntity accessory : accessoryEntities) {
                System.out.println("here");
                if (accessory.getAccessoryEntityId().equals(accessoryId)) {
                    List<AccessoryItemEntity> listOfAccessoryItemsToSell = new ArrayList<>();
                    for (AccessoryItemEntity accessoryItem : accessory.getAccessoryItemEntities()) {
                        System.out.println("here 2");
                        accessoryItem.setAccessoryEntity(null);
                        System.out.println("here 3");

                        for (PromotionEntity promo : accessoryItem.getPromotionEntities()) {
                            System.out.println("here 4");
                            promo.getAccessoryItemEntities().clear();
                            promo.getPartChoiceEntities().clear();
                            System.out.println("here 5");
                        }
                        System.out.println("hereeeee");
                        if (accessoryItem.getIsDisabled() == false) {
                            listOfAccessoryItemsToSell.add(accessoryItem);
                        }
                    }
                    accessory.setAccessoryItemEntities(listOfAccessoryItemsToSell);
                    System.out.println("SENDING ACCESSORY: ");
                    System.out.println(accessory);
                    return Response.status(Response.Status.OK).entity(accessory).build();
                }
            }

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("UNABLE TO FIND ACCESSORY").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
