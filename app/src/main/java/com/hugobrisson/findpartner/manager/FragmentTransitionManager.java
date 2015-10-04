package com.hugobrisson.findpartner.manager;

import android.app.FragmentManager;
import android.content.Context;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.hugobrisson.findpartner.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by hugo on 18/06/2015.
 */
@EBean
public class FragmentTransitionManager {

    @RootContext
    Context context;

    /**
     * change fragment in activity.
     *
     * @param fragmentManager fragmentManager
     * @param contextFragment call to fragment use
     * @param fragment        go to the newt fragment
     */
    public void changeFragment(FragmentManager fragmentManager, Fragment contextFragment, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.remove(contextFragment).commit();
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment contextFragment, Fragment fragment, String extra) {
        Bundle bundle = new Bundle();
        bundle.putString("objectId", extra);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.remove(contextFragment).commit();
    }

    public void initActivity(android.app.FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
