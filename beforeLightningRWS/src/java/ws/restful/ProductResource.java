/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.PartChoiceEntity;
import entity.PartEntity;
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
@Path("Products")
public class ProductResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final ProductEntitySessionBeanLocal productEntitySessionBeanLocal;
    private final PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    public ProductResource() {
        sessionBeanLookup = new SessionBeanLookup();
        productEntitySessionBeanLocal = sessionBeanLookup.lookupProductEntitySessionBeanLocal();
        partChoiceEntitySessionBeanLocal = sessionBeanLookup.lookupPartChoiceEntitySessionBeanLocal();
    }

//    @Path("retrieveAllProductsToSell")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveAllProductsToSell() {
//        try {
//            System.out.println(" ============== RETRIEVE PRODUCTS TO SELL ===============");
//            List<ProductEntity> productEntities = productEntitySessionBeanLocal.retrieveAllProductEntitiesThatCanSell();
//            for (ProductEntity product : productEntities) {
////                //REMOVING unrelated chassis part choice
////                for (PartEntity part : product.getPartEntities()) {
////                    if (part.getPartName().equals("Chassis")) {
////                        List<PartChoiceEntity> newList = new ArrayList<>();
////                        for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
////                            if (pce.getPartChoiceName().equals(product.getProductName() + " Chassis")) {
////                                newList.add(pce);
////                                break;
////                            }
////                        }
////                        part.setPartChoiceEntities(newList);
////                        break;
////                    }
////                }
////
////                //REMOVING unrelated part choices that are not compatible with chassis part choice
////                PartChoiceEntity chassisPartChoice = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(product.getProductName() + " Chassis");
////                for (PartEntity part : product.getPartEntities()) {
////                    if (!part.getPartName().equals("Chassis")) {
////                        List<PartChoiceEntity> newList = new ArrayList<>();
////                        for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
////                            if (chassisPartChoice.getCompatiblePartsPartChoiceEntities().contains(pce)) {
////                                newList.add(pce);
////                            }
////                        }
////                        part.setPartChoiceEntities(newList);
////                    }
////                }
//                
//                // =============== UNMARSHALLING =============
//                for (PartEntity part : product.getPartEntities()) {
//                    part.getProductEntities().clear();
//                    for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
//                        for (PartChoiceEntity compatiblePce : pce.getCompatibleChassisPartChoiceEntities()) {
//                            compatiblePce.getCompatiblePartsPartChoiceEntities().clear();
//                        }
//                        for (PartChoiceEntity compatiblePce : pce.getCompatiblePartsPartChoiceEntities()) {
//                            compatiblePce.getCompatibleChassisPartChoiceEntities().clear();
//                        }
//
//                        for (PromotionEntity promo : pce.getPromotionEntities()) {
//                            promo.getAccessoryItemEntities().clear();
//                            promo.getPartChoiceEntities().clear();
//                        }
//                    }
//                }
//            }
//            System.out.println("SENDING OUT PRODUCTS.... TOTAL SIZE: " + productEntities.size());
//
//            GenericEntity<List<ProductEntity>> genericEntity = new GenericEntity<List<ProductEntity>>(productEntities) {
//            };
//
//            return Response.status(Response.Status.OK).entity(genericEntity).build();
//        } catch (Exception ex) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
//        }
//    }
    @Path("retrieveAllProductsToSell")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductsToSell() {
        try {
            System.out.println(" ============== RETRIEVE PRODUCTS TO SELL ===============");
            List<ProductEntity> productEntities = productEntitySessionBeanLocal.retrieveAllProductEntitiesThatCanSell();
            // =============== UNMARSHALLING =============
            for (ProductEntity product : productEntities) {
                product.getReviewEntities().size();
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

    @Path("retrieveProductToSellById/{productId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProductToSellById(@PathParam("productId") Long productId) {
        System.out.println(" ============== RETRIEVE PRODUCT BY ID TO SELL ===============");

        try {
            List<ProductEntity> productEntities = productEntitySessionBeanLocal.retrieveAllProductEntitiesThatCanSell();
            ProductEntity productEntityToSend;
            // =============== UNMARSHALLING =============
            for (ProductEntity product : productEntities) {
                System.out.println("check equality");
                if (product.getProductEntityId().equals(productId)) {
                    //REMOVING unrelated chassis part choice
                    for (PartEntity part : product.getPartEntities()) {
                        if (part.getPartName().equals("Chassis")) {
                            List<PartChoiceEntity> newList = new ArrayList<>();
                            for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
                                if (pce.getPartChoiceName().equals(product.getProductName() + " Chassis")) {
                                    newList.add(pce);
                                    break;
                                }
                            }
                            part.setPartChoiceEntities(newList);
                            break;
                        }
                    }

                    //REMOVING unrelated part choices that are not compatible with chassis part choice
                    PartChoiceEntity chassisPartChoice = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(product.getProductName() + " Chassis");
                    for (PartEntity part : product.getPartEntities()) {
                        if (!part.getPartName().equals("Chassis")) {
                            List<PartChoiceEntity> newList = new ArrayList<>();
                            for (PartChoiceEntity pce : part.getPartChoiceEntities()) {
                                if (chassisPartChoice.getCompatiblePartsPartChoiceEntities().contains(pce)) {
                                    newList.add(pce);
                                }
                            }
                            part.setPartChoiceEntities(newList);
                        }
                    }
                    System.out.println("product found, now unmarshalling");
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
                    productEntityToSend = product;
                    System.out.println("SENDING OUT PRODUCT.... PRODUCT ID: " + productEntityToSend.getProductEntityId());
//                    GenericEntity<ProductEntity> genericEntity = new GenericEntity<ProductEntity>(productEntityToSend) {
//                    };
                    return Response.status(Response.Status.OK).entity(productEntityToSend).build();
                }
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("UNABLE TO FIND PRODUCT").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
