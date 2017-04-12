package at.bugconsult.restexample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;

/**
 * Created by Dominik on 11.04.2017.
 */
@Path("/auth")
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/token")
    public Response refreshToken(@HeaderParam("Authorization") String token) {

        try {
            // check if token valid and refresh expiration date if valid
            Calendar currentDate = Calendar.getInstance();

            // throws SignatureException if invalid
            Claims claims = Jwts.parser()
                    .requireIssuer("bugconsult")
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();

            // might allow refreshing up to a longer timeframe (7 days)
            // now just check the expiration date
            if (claims.getExpiration().after(currentDate.getTime()))
                throw new Exception();

            //refresh the token
            String refreshToken = issueToken(claims.getSubject());

            // return the refreshed token
            return Response.ok(refreshToken).build();

        } catch (Exception e) {
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

        // 15 minutes seems to be the standard
        // renewal must be initiated from the client and server renews tokens not older than 7 days
        // if not done within 7 days a new authentication is necessary
        Calendar currentDate = Calendar.getInstance();
        Calendar expiryDate = (Calendar) currentDate.clone();
        expiryDate.add(Calendar.MINUTE, 15);  // TODO token time should be configured

        String jwt = Jwts.builder()
                .setIssuer("bugconsult") // TODO should be configured somewhere
                .setIssuedAt(currentDate.getTime())
                .setSubject(username)
                .setExpiration(expiryDate.getTime())
                .signWith(SignatureAlgorithm.HS512, "secret") //TODO the secret key should be somewhere configured
                .compact();

        return jwt;
    }
}
