package com.hugobrisson.findpartner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;

/**
 * Created by hugobrisson on 20/10/15.
 */
public class Tools {

    /**
     * verify network available.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise ic_check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(context, context.getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
