package at.bugconsult.restexample2;

import java.io.Serializable;

/**
 * Created by Dominik on 11.04.2017.
 */
public class Credentials implements Serializable{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
