/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.SupportTicketEntitySessionBeanLocal;
import entity.SupportTicketEntity;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Koh Wen Jie
 */
@Path("Support")
public class SupportResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal;

    public SupportResource() {
        sessionBeanLookup = new SessionBeanLookup();
        supportTicketEntitySessionBeanLocal = sessionBeanLookup.lookupSupportTicketEntitySessionBeanLocal();
    }

    @Path("retrieveSupportTickets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSupportTickets(@QueryParam("email") String email) {
        try {
            List<SupportTicketEntity> supportTicketEntities = supportTicketEntitySessionBeanLocal.retrieveAllSupportTicketsByEmail(email);

            GenericEntity<List<SupportTicketEntity>> genericEntity = new GenericEntity<List<SupportTicketEntity>>(supportTicketEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
