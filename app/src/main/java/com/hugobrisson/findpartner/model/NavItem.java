package com.hugobrisson.findpartner.model;

import android.graphics.drawable.Drawable;

/**
 * Created by hugo on 22/07/2015.
 */
public class NavItem {

    Drawable mIcon;
    String mTitle;

    public NavItem(Drawable icon, String title) {
        this.mIcon = icon;
        this.mTitle = title;
    }

    public NavItem() {
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }


}
