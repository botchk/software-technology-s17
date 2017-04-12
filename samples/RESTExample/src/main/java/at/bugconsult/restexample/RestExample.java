package at.bugconsult.restexample;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Dominik on 12.04.2017.
 */
public class RestExample extends ResourceConfig{
    public RestExample() {
        register(new RestExampleBinder());
        packages(true, "at.bugconsult.restexample");
    }
}
