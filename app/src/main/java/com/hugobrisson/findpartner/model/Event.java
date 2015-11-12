package com.hugobrisson.findpartner.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by hugo on 05/11/2015.
 */
@ParseClassName("Activity")
public class Event extends ParseObject {

    public String getName() {
        return getString("Name");
    }

    public void setName(String value) {
        put("Name", value);
    }

    public String getDesc() {
        return getString("Details");
    }

    public void setDesc(String value) {
        put("Details", value);
    }

    public String getAddress() {
        return getString("Details");
    }

    public void setAddress(String value) {
        put("Address", value);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("Location");
    }

    public void setLocation(ParseGeoPoint value) {
        put("Location", value);
    }

    public Object getOwnerId() {
        return getString("OwnerID");
    }

    public void setOwnerId(Object userId) {
        put("OwnerID", userId);
    }

    public Object getSportId() {
        return getString("SportID");
    }

    public void setSportId(Object sportId) {
        put("SportID", sportId);
    }

    public Object getStatusId() {
        return getString("statusID");
    }

    public void setStatusId(Object statusId) {
        put("statusID", statusId);
    }

    public Date getDateStart() {
        return getDate("StartDate");
    }

    public void setDateStart(Date value) {
        put("StartDate", value);
    }

    public Date getDateEnd() {
        return getDate("EndDate");
    }

    public void setDateEnd(Date value) {
        put("EndDate", value);
    }

    public boolean isPayable() {
        return getBoolean("isPayable");
    }

    public void setIsPayable(boolean value) {
        put("isPayable", value);
    }

    public int getNbPartner() {
        return getInt("nbPartner");
    }

    public void setNbPartner(int value) {
        put("nbPartner", value);

    }

    public int getNbAlreadyPartner() {
        return getInt("nbAlreadyPartner");
    }

    public void setNbAlreadyPartner(int value) {
        put("nbAlreadyPartner", value);
    }

    public int getPrice() {
        return getInt("price");
    }

    public void setPrice(int value) {
        put("price", value);
    }
}


