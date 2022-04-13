/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.SupportTicketEntitySessionBeanLocal;
import entity.MemberEntity;
import entity.SupportTicketEntity;
import java.util.List;
import javax.ws.rs.Consumes;
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
	private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

	public SupportResource() {
		sessionBeanLookup = new SessionBeanLookup();
		supportTicketEntitySessionBeanLocal = sessionBeanLookup.lookupSupportTicketEntitySessionBeanLocal();
		memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
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

	@Path("retrieveMySupportTicketsByEmail")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSupportTicketsByEmail(@QueryParam("email") String email) {
		if (email != null) {
			List<SupportTicketEntity> supportTickets = supportTicketEntitySessionBeanLocal.retrieveAllSupportTicketsByEmail(email);
			//handleSupportTicketsUnmarshalling(forumPosts);
			GenericEntity<List<SupportTicketEntity>> genericEntity = new GenericEntity<List<SupportTicketEntity>>(supportTickets) {
			};
			return Response.status(Response.Status.OK).entity(genericEntity).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Email not provided").build();
		}
	}

	@Path("createNewSupportTicket")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSupportTicket(@QueryParam("username") String username,
			@QueryParam("issue") String issue) {
		System.out.println(":: rws supportresource createsupportticket :: ");
		System.out.println("Username is" + username);
		System.out.println("Issue is" + issue);
		if (username != null && issue != null) {
			try {
				MemberEntity member = memberEntitySessionBeanLocal.retrieveMemberEntityByUsername(username);
				String email = member.getEmail();
				SupportTicketEntity newSupportTicketToMake = new SupportTicketEntity(email, issue);
				SupportTicketEntity newlyCreatedSupportTicket = supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(newSupportTicketToMake);
				Long newSupportTicketId = newlyCreatedSupportTicket.getSupportTicketId();
				return Response.status(Response.Status.OK).entity(newSupportTicketId).build();
			} catch (Exception ex) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new support ticket request").build();
		}
	}
	
		@Path("createSupportTicketWithEmail")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSupportTicketWithEmail(@QueryParam("email") String email,
			@QueryParam("issue") String issue) {
		System.out.println(":: rws supportresource createsupportticket :: ");
		System.out.println("email is" + email);
		System.out.println("Issue is" + issue);
		if (email != null && issue != null) {
			try {
				SupportTicketEntity newSupportTicketToMake = new SupportTicketEntity(email, issue);
				SupportTicketEntity newlyCreatedSupportTicket = supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(newSupportTicketToMake);
				Long newSupportTicketId = newlyCreatedSupportTicket.getSupportTicketId();
				return Response.status(Response.Status.OK).entity(newSupportTicketId).build();
			} catch (Exception ex) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new support ticket request").build();
		}
	}
}
