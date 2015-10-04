package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * Created by hugo on 04/06/2015.
 */
public class DisplayManager {

    private int mSize[] = {72, 96, 144, 192};

    public int getSize(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        int size;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                //MDPI
                size = mSize[0];
                break;
            case DisplayMetrics.DENSITY_HIGH:
                size = mSize[1];
                //HDPI
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                size = mSize[2];
                //XHDPI
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                size = mSize[3];
                //XXHDPI
            default:
                size = 256;
                break;
        }
        return size;
    }
}
