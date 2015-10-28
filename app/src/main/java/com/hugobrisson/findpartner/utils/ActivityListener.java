package com.hugobrisson.findpartner.utils;


import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by hugobrisson on 20/10/15.
 */
public interface ActivityListener {
    void changeFragment(Fragment fragment);

    void changeActivity(Activity activity);
}
