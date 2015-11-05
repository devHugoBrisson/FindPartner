package com.hugobrisson.findpartner.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


import com.hugobrisson.findpartner.manager.ErrorManager;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.manager.SnackBarManager;
import com.hugobrisson.findpartner.manager.UserManager;
import com.hugobrisson.findpartner.utils.ActivityListener;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by hugobrisson on 20/10/15.
 */
@EFragment
public class FragmentController extends Fragment {

    protected ActivityListener mActivityListener;

    @Bean
    protected ErrorManager errorManager;

    @Bean
    protected UserManager userManager;

    @Bean
    protected SnackBarManager snackBarManager;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityListener = (ActivityListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityListener");
        }
    }
}
