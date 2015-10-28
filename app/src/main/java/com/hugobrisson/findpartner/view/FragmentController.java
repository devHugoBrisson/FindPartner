package com.hugobrisson.findpartner.view;

import android.app.Activity;
import android.support.v4.app.Fragment;


import com.hugobrisson.findpartner.utils.ActivityListener;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;

/**
 * Created by hugobrisson on 20/10/15.
 */
@EFragment
public class FragmentController extends Fragment {

    protected ActivityListener mActivityListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mActivityListener = (ActivityListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ActivityListener");
        }
    }

}
