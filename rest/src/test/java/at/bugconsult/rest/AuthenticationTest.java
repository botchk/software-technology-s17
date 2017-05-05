package at.bugconsult.rest;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthenticationTest extends JerseyTest {

    @Override
    protected Application configure() {
        Configuration configuration = new Configuration("secret");
        return new ResourceConfig().register(new AuthenticationEndpoint(configuration));
    }

    @Test
    public void authenticate_success_test() {
        Credentials credentials = new Credentials();
        credentials.setUsername("testuser1");
        credentials.setPassword("1234");

        Response response = target("auth/authenticate").request().post(Entity.json(credentials));
        Assert.assertEquals("Should return status code 200", 200, response.getStatus());
        Assert.assertTrue(response.getHeaders().get("Content-Type").get(0).equals(MediaType.APPLICATION_JSON));

        String responseBody = response.readEntity(String.class);

        Claims claims = Jwts.parser()
                .requireIssuer("bugconsult")
                .setSigningKey("secret")
                .parseClaimsJws(responseBody)
                .getBody();
    }

    @Test
    public void authenticate_wrong_password_test() {
        Credentials credentials = new Credentials();
        credentials.setUsername("testuser1");
        credentials.setPassword("wrong");

        Response response = target("auth/authenticate").request().post(Entity.json(credentials));
        Assert.assertEquals("Should return status code 401", 401, response.getStatus());
        Assert.assertTrue(response.getHeaders().get("Content-Type").get(0).equals(MediaType.APPLICATION_JSON));
    }

    @Test
    public void authenticate_unknown_user_test() {
        Credentials credentials = new Credentials();
        credentials.setUsername("unknown");
        credentials.setPassword("1234");

        Response response = target("auth/authenticate").request().post(Entity.json(credentials));
        Assert.assertEquals("Should return status code 401", 401, response.getStatus());
        Assert.assertTrue(response.getHeaders().get("Content-Type").get(0).equals(MediaType.APPLICATION_JSON));
    }
}
