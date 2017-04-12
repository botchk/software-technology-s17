package at.bugconsult.restexample;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by Dominik on 12.04.2017.
 */
public class RestExampleBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticatedUserProducer.class).to(AuthenticatedUserProducer.class);
        bind(AuthenticationFilter.class).to(AuthenticationFilter.class);
    }
}
