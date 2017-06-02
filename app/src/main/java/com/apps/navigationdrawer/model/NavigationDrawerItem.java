package com.apps.navigationdrawer.model;

/**
 * Created by Kavitha on 6/2/2017.
 */

public class NavigationDrawerItem {

    private boolean nav_showNotify;
    private String nav_title;

    public boolean isNav_showNotify() {
        return nav_showNotify;
    }

    public void setNav_showNotify(boolean nav_showNotify) {
        this.nav_showNotify = nav_showNotify;
    }

    public String getNav_title() {
        return nav_title;
    }

    public void setNav_title(String nav_title) {
        this.nav_title = nav_title;
    }
}
