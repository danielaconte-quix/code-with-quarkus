package org.acme.api;

import jdk.jfr.BooleanFlag;
import org.acme.service.PersonSrv;
import org.jboss.resteasy.annotations.Body;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/person")
public class PersonResource {

    @Inject
    PersonSrv personSrv;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFullName(@PathParam("id") int personId) {
        return personSrv.getFullName(personId);
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateFullname(@PathParam("id") int personId, String firstname) {
        personSrv.updateFirstname(personId, firstname);
        return "ok";
    }
}
