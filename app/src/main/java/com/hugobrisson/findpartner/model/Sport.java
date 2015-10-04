package com.hugobrisson.findpartner.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by hugo on 29/05/2015.
 */
@ParseClassName("Sport")
public class Sport extends ParseObject {

    private Bitmap image;
    private boolean isCheck = false;

    public Sport() {

    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

}

