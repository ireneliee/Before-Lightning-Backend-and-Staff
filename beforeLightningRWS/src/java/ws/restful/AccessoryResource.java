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
import entity.PromotionEntity;
import java.util.List;
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

            // =============== UNMARSHALLING =============
            for (AccessoryEntity accessory : accessoryEntities) {
                System.out.println("here");
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
                }
                System.out.println("OUT HERE NOW");
            }
            System.out.println("SENDING OUT Accessories.... TOTAL SIZE: " + accessoryEntities.size());

            GenericEntity<List<AccessoryEntity>> genericEntity = new GenericEntity<List<AccessoryEntity>>(accessoryEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
