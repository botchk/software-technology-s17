package at.bugconsult.restexample2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by Dominik on 11.04.2017.
 *
 * Authenticaton filter that checks all requests before handing them to the endpoints
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@Secured
public class AuthenticationFilter implements ContainerRequestFilter{

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        // the token should be in the authorization header of the http request
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // check if the authorization header is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // extract the token for validation
        String token = authorizationHeader.substring(7).trim(); // get token after Bearer

        try {
            // validate token, throws exception if invalid
            validateToken(token);

        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {

        Calendar currentDate = Calendar.getInstance();

        // throws SignatureException if invalid
        // expiration date gets checked by parser automatically
        Claims claims = Jwts.parser()
                .requireIssuer("bugconsult")
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();

        //if (claims.getExpiration().after(currentDate.getTime()))
        //    throw new Exception();

        // if token is valid fire the userAuthenticatedEvent to set the user
        userAuthenticatedEvent.fire(claims.getSubject());
    }
}
