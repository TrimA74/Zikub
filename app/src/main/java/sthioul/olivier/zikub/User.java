package sthioul.olivier.zikub;

import android.app.Application;

import java.util.List;

/**
 * Created by florian on 19/09/2017.
 */

public class User {
    private String username;

    private String password;

    private List<Integer> playlist;

    private List<User> friends;



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

    public List<Integer> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Integer> playlist) {
        this.playlist = playlist;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }




}
