package sthioul.olivier.zikub;

/**
 * Created by florian on 19/09/2017.
 */

public class User {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String pseudo) {
        this.username = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}
