package at.bugconsult.restexample;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Dominik on 11.04.2017.
 *
 * Authenticaton filter that checks all requests before handing them to the endpoints
 */
@Provider // TODO dont know what this does but seems necessary
@Priority(Priorities.AUTHENTICATION)
@Secured
public class AuthenticationFilter implements ContainerRequestFilter{

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        // the token should be in the authorization header of the http request
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // check if the authorization header is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // extract the token for validation
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // validate token, throws exception if invalid
            validateToken(token);

        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        // check if token was issued by the server and if it is expired
        // throw exception if invalid
    }
}
