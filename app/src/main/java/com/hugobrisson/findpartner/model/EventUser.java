package com.hugobrisson.findpartner.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by hugo on 11/11/2015.
 */
@ParseClassName("ActivityUser")
public class EventUser extends ParseObject {

    public Object getActivityId() {
        return getString("ActivityID");
    }

    public void setActivityId(Object activity) {
        put("ActivityID", activity);
    }

    public Object getUserId() {
        return getString("UserID");
    }

    public void setUserId(Object user) {
        put("UserID", user);
    }

    public boolean isConfirmedOwner() {
        return getBoolean("isConfirmedOwner");
    }

    public void setIsConfirmedOwner(boolean value) {
        put("isConfirmedOwner", value);
    }

}
