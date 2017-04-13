package at.bugconsult.restexample2;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dominik on 10.04.2017.
 */
@Path("/hello")
@Secured
public class HelloWorld {

    @Inject
    @AuthenticatedUser
    UserAccount authenticatedUser;

    @GET
    @Path("/1")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage1() {
        return "Worker Hello world from " + authenticatedUser.getUsername();
    }

    @GET
    @Path("/2")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured(Role.Boss)
    public String getMessage2() {
        return "Boss Hello world from " + authenticatedUser.getUsername();
    }
}