package sthioul.olivier.zikub;

import android.app.Application;

/**
 * Created by olivier on 17/10/2017.
 */

public class GlobalClass  extends Application {

    private User user;
    private User currrentUser;

    public User getCurrrentUser() {
        return currrentUser;
    }

    public void setCurrrentUser(User currrentUser) {
        this.currrentUser = currrentUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
