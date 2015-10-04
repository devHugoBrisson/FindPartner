package com.hugobrisson.findpartner.manager;

import android.support.design.widget.Snackbar;
import android.view.View;

import org.androidannotations.annotations.EBean;

/**
 * Created by hugo on 27/06/2015.
 */
@EBean
public class SnackBarManager {

    public void show(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}
