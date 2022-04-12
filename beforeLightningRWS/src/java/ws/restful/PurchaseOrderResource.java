package ws.restful;

import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.AddressEntity;
import entity.DeliverySlotEntity;
import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.enumeration.PurchaseOrderStatusEnum;
import util.exception.CreateNewPurchaseOrderException;
import util.exception.MemberEntityNotFoundException;
import ws.datamodel.CreatePurchaseOrderReq;

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
            List<PurchaseOrderEntity> listOfPurchaseOrder = purchaseOrderEntitySessionBean.retrievePurchaseOrderByUsername(username);
            listOfPurchaseOrder.stream().forEach(x -> unmarshallSinglePurchaseOrder(x));

            GenericEntity<List<PurchaseOrderEntity>> genericEntity = new GenericEntity<List<PurchaseOrderEntity>>(listOfPurchaseOrder) {
            };
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error has occured while retrieving purchase orders").build();
        }
    }

    private void unmarshallSinglePurchaseOrder(PurchaseOrderEntity po) {
        System.out.println("===========Unmarshalling single purchase order===============");
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
        if (pol.getPartChoiceEntities() != null) {
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
        memberToUnmarshall.getCreditCards().clear();
        System.out.println("  ");
    }

    @Path("createNewPurchaseOrder")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewPurchaseOrder(CreatePurchaseOrderReq purchaseOrderReq) {
        System.out.println("THIS IS THE RECEIVED REQUEST");
        System.out.println(purchaseOrderReq.getAddress());
//        System.out.println(purchaseOrderReq.getDeliverySlot());
        System.out.println(purchaseOrderReq.getDeliveryType());
        System.out.println(purchaseOrderReq.getListOfLineItems());
        System.out.println(purchaseOrderReq.getMemberUsername());
        System.out.println(purchaseOrderReq.getTotalPrice());
        System.out.println(purchaseOrderReq.getDay());
        System.out.println(purchaseOrderReq.getMonth());
        System.out.println(purchaseOrderReq.getYear());
        System.out.println(purchaseOrderReq.getHours());
//        System.out.println("This is the working date: " + deliveryDate.toString());
        System.out.println(purchaseOrderReq.getDeliveryOption());
        System.out.println("================= RETRIEVED PURCHASE ORDER ITEMS ================");

        if (purchaseOrderReq != null) {
            List<PurchaseOrderLineItemEntity> listOfLineItems = purchaseOrderReq.getListOfLineItems();
//            DeliverySlotEntity deliverySlot = purchaseOrderReq.getDeliverySlot();
            String memberUsername = purchaseOrderReq.getMemberUsername();
            AddressEntity address = purchaseOrderReq.getAddress();
            String deliveryType = purchaseOrderReq.getDeliveryType();
            BigDecimal totalPrice = purchaseOrderReq.getTotalPrice();
            Integer day = purchaseOrderReq.getDay();
            Integer month = purchaseOrderReq.getMonth();
            Integer year = purchaseOrderReq.getYear();
            Integer hours = purchaseOrderReq.getHours();
            LocalDateTime deliveryDate = LocalDateTime.of(year, Month.of(month + 1), day, hours, 0);
            System.out.println("The date is " + deliveryDate.toString());
            String deliveryOption = purchaseOrderReq.getDeliveryOption();

////            LocalDateTime deliveryDate = LocalDateTime.parse(purchaseOrderReq.getDate().subSequence(0, 23) + "+08:00");
//            String deliveryOption = purchaseOrderReq.getDeliveryOption();
//            Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(purchaseOrderReq.getDate().subSequence(0, 23) + "+08:00");
//            System.out.println("========================================");
//            System.out.println(cal.toString()); 
//            Date obj = cal.getTime();
//            System.out.println(obj.toString());
////            String objString = obj.();
////            LocalDateTime deliveryDate = LocalDateTime.
////            System.out.println(deliveryDate.toString());
//            System.out.println("========================================");
            try {
//                deliverySlot
                PurchaseOrderEntity po = purchaseOrderEntitySessionBean.createNewPurchaseOrderRWS(memberUsername, listOfLineItems, address, deliveryType, totalPrice, deliveryDate, deliveryOption);
//                
                System.out.println("CREATED PO ID: " + po.getPurchaseOrderEntityId());
                return Response.status(Response.Status.OK).entity(po.getPurchaseOrderEntityId()).build();

            } catch (MemberEntityNotFoundException | CreateNewPurchaseOrderException ex) {
                System.out.println("Error in RWS purchase order resource" + ex.getMessage());
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create Purchase Order!").build();

        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create Purchase Order!").build();
        }
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
