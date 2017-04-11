package at.bugconsult.restexample;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Dominik on 11.04.2017.
 */
@Path("/authentication")
public class AuthenticationEndpoint {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response registerUser(UserAccount userAccount) {
        try {
            // register a new user
            register(userAccount.getFirstname(), userAccount.getLastname(), userAccount.getUsername(),
                     userAccount.getEmail(), userAccount.getPassword());

            return Response.ok().build();

        } catch (Exception e) {
            // return status code 409 CONFLICT on exception
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/authenticate")
    public Response authenticateUser(Credentials credentials) {

        try {
            // authenticate the user
            authenticate(credentials.getUsername(), credentials.getPassword());

            // if authentication was successful issue a token for the user
            String token = issueToken(credentials.getUsername());

            // return the token in 200 OK response
            return Response.ok(token).build();

        } catch (Exception e) {
            // on failure return 401 UNAUTHORIZED response
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void register(String firstname, String lastname, String username, String email, String password) throws Exception {
        // check against database if user if data is unique
        // create new account in database
    }

    private void authenticate(String username, String password) throws Exception {
        // check against database, if not valid throw exception
        // could provide more failure information like wrong password, unknown username ...
    }

    private String issueToken(String username) {
        // generate token for user and associate it with the user
        // see JSON Web Token (JWT)
        return "1234";
    }
}
