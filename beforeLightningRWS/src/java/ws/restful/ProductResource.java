/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
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
@Path("Products")
public class ProductResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    public ProductResource() {
        sessionBeanLookup = new SessionBeanLookup();
        productEntitySessionBeanLocal = sessionBeanLookup.lookupProductEntitySessionBeanLocal();
    }

    @Path("retrieveAllProductsToSell")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductsToSell() {
        try {
            System.out.println(" ============== RETRIEVE PRODUCTS TO SELL ===============");
            List<ProductEntity> productEntities = productEntitySessionBeanLocal.retrieveAllProductEntitiesThatCanSell();
            // =============== UNMARSHALLING =============
            for (ProductEntity product : productEntities) {
                for (PartEntity part : product.getPartEntities()) {
                    part.getProductEntities().clear();
                    for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
                        for (PartChoiceEntity compatiblePce : pce.getCompatibleChassisPartChoiceEntities()) {
                            compatiblePce.getCompatiblePartsPartChoiceEntities().clear();
                        }
                        for (PartChoiceEntity compatiblePce : pce.getCompatiblePartsPartChoiceEntities()) {
                            compatiblePce.getCompatibleChassisPartChoiceEntities().clear();
                        }

                        for (PromotionEntity promo : pce.getPromotionEntities()) {
                            promo.getAccessoryItemEntities().clear();
                            promo.getPartChoiceEntities().clear();
                        }

                    }
                }
            }
            System.out.println("SENDING OUT PRODUCTS.... TOTAL SIZE: " + productEntities.size());

            GenericEntity<List<ProductEntity>> genericEntity = new GenericEntity<List<ProductEntity>>(productEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
