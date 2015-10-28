package com.hugobrisson.findpartner.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hugobrisson.findpartner.R;

import org.androidannotations.annotations.EBean;

/**
 * Created by hugo on 18/06/2015.
 */
@EBean
public class FragmentTransitionManager {

    /**
     * change fragment in activity.
     *
     * @param fragmentManager fragmentManager
     * @param fragment        go to the next fragment
     */
    public void changeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment contextFragment, Fragment fragment, String extra) {
        Bundle bundle = new Bundle();
        bundle.putString("objectId", extra);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.remove(contextFragment).commit();
    }

    /**
     * change fragment in activity.
     *
     * @param fragmentManager fragmentManager
     * @param fragment        go to the next fragment
     */
    public void initFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
}
