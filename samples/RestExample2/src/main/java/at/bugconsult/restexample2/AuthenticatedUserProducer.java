package at.bugconsult.restexample2;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

/**
 * Created by Dominik on 12.04.2017.
 */
@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private UserAccount authenticatedUser;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = findUser(username);
    }

    private UserAccount findUser(final String username) {
        // find username in db and return instance

        return new UserAccount() {{
            setUsername(username);
            setRole(Role.Boss);
        }};
    }
}