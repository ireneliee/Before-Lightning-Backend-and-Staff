/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
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
@Path("Employee")
public class EmployeeResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    public EmployeeResource() {
        sessionBeanLookup = new SessionBeanLookup();
        employeeEntitySessionBeanLocal = sessionBeanLookup.lookupEmployeeEntitySessionBeanLocal();
    }

    @Path("retrieveEmployees")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllEmployees() {
        try {
            List<EmployeeEntity> employeeEntities = employeeEntitySessionBeanLocal.retrieveAllEmployees();

            GenericEntity<List<EmployeeEntity>> genericEntity = new GenericEntity<List<EmployeeEntity>>(employeeEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
