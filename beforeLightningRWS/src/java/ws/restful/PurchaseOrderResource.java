package ws.restful;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("PurchaseOrder")

public class PurchaseOrderResource {

    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;
    private final SessionBeanLookup sessionBeanLookup;
    private final PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBean;

    public PurchaseOrderResource() {
        sessionBeanLookup = new SessionBeanLookup();
        purchaseOrderEntitySessionBean = sessionBeanLookup.lookupPurchaseOrderEntitySessionBeanLocal();
    }

    @Path("retrievePurchaseOrderByUsername")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPurchaseOrderByUsername(@QueryParam("username") String username) {
        System.out.println("============Calling retrieve purchase order in server side =============");
        if (username != null) {
            List<PurchaseOrderEntity> listOfPurchaseOrder = purchaseOrderEntitySessionBean.retrieveAllPurchaseOrders();
            listOfPurchaseOrder.stream().forEach(x -> unmarshallSinglePurchaseOrder(x));

            GenericEntity<List<PurchaseOrderEntity>> genericEntity = new GenericEntity<List<PurchaseOrderEntity>>(listOfPurchaseOrder) {
            };
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error has occured while retrieving purchase orders").build();
        }
    }
    
    @Path("createNewPurchaseOrder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewPurchaseOrder(List<PurchaseOrderLineItemEntity> listOfLineItems) {
        if (listOfLineItems != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create Purchase Order!").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create Purchase Order!").build();
        }
    }

    private void unmarshallSinglePurchaseOrder(PurchaseOrderEntity po) {
        //System.out.println("===========Unmarshalling single purchase order===============");
        // po - member relationship
        MemberEntity m = po.getMember();
        umarshallSingleMember(m);

        // po - polineitem relationship
        List<PurchaseOrderLineItemEntity> listOfPurchaseOrderLineItem = po.getPurchaseOrderLineItems();
        listOfPurchaseOrderLineItem.stream().forEach(x -> unmarshallSinglePurchaseOrderLineItem(x));
    }

    private void unmarshallSinglePurchaseOrderLineItem(PurchaseOrderLineItemEntity pol) {
        //System.out.println("================Unmarshalling single purchase order line item===============");
        // relationship with product entity
        //System.out.println("================Handling product===============");
        if (pol.getProductEntity() != null) {
            ProductEntity pe = pol.getProductEntity();
            pe.getPartEntities().clear();
            pe.getReviewEntities().clear();
        }

        //relationship with part choice entity
        //System.out.println("================Handling part choice===============");
        if (pol.getPartChoiceEntities()!= null) {
            List<PartChoiceEntity> pcList = pol.getPartChoiceEntities();
            pcList.stream().forEach(pc -> unmarshallSinglePartChoiceEntity(pc));
        }

        //relationship with accessory entity
        //System.out.println("================Handling accessory===============");
        if (pol.getAccessoryItemEntity() != null) {
            AccessoryItemEntity ae = pol.getAccessoryItemEntity();
            unmarshallSingleAccessoryItemEntity(ae);
        }

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

    private void umarshallSingleMember(MemberEntity memberToUnmarshall) {
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

    public UriInfo getContext() {
        return context;
    }

    public void setContext(UriInfo context) {
        this.context = context;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public SessionBeanLookup getSessionBeanLookup() {
        return sessionBeanLookup;
    }

    public PurchaseOrderEntitySessionBeanLocal getPurchaseOrderEntitySessionBean() {
        return purchaseOrderEntitySessionBean;
    }

}
