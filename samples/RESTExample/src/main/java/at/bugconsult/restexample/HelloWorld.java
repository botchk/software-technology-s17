package at.bugconsult.restexample;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dominik on 10.04.2017.
 */
@Path("/hello")
public class HelloWorld {

    @Inject
    @AuthenticatedUser
    UserAccount authenticatedUser;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Secured
    public String getMessage() {
        return "Hello world from" + authenticatedUser.getUsername();
    }
}
