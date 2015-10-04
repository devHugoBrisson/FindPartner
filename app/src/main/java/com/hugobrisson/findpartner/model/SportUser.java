package com.hugobrisson.findpartner.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by hugo on 15/07/2015.
 */
@ParseClassName("SportUser")
public class SportUser extends ParseObject {

    public SportUser() {
    }

    public Object getUserId() {
        return getString("UserID");
    }

    public void setUserId(Object userId) {
        put("UserID", userId);
    }

    public Object getSportId() {
        return getString("SportID");
    }

    public void setSportId(Object sportId) {
        put("SportID", sportId);
    }
}
