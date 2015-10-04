package com.hugobrisson.findpartner.model;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by hugo on 26/05/2015.
 */

public class User extends ParseUser {

    public String getFirstName() {
        return getString("firstName");
    }

    public void setFirstName(String value) {
        put("firstName", value);
    }

    public String getLastName() {
        return getString("lastName");
    }

    public void setLastName(String value) {
        put("lastName", value);
    }

    public ParseGeoPoint getHomeLocation() {
        return getParseGeoPoint("homeLocation");
    }

    public void setHomeLocation(ParseGeoPoint value) {
        put("homeLocation", value);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String value) {
        put("address", value);
    }

    public String getGender() {
        return getString("gender");
    }

    public void setGender(String value) {
        put("gender", value);
    }

    public Date getBirthDate() {
        return getDate("birthDate");
    }

    public void setBirthdate(Date value) {
        put("birthDate", value);
    }

    public String getMotivation() {
        return getString("motivation");
    }

    public void setMotivation(String value) {
        put("motivation", value);
    }
}
