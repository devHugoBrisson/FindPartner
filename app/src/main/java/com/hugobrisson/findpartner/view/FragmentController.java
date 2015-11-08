package com.hugobrisson.findpartner.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;


import com.hugobrisson.findpartner.manager.ErrorAccountManager;
import com.hugobrisson.findpartner.manager.SnackBarManager;
import com.hugobrisson.findpartner.manager.UserManager;
import com.hugobrisson.findpartner.utils.ActivityListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by hugobrisson on 20/10/15.
 */
@EFragment
public class FragmentController extends Fragment {

    protected ActivityListener mActivityListener;

    @Bean
    protected ErrorAccountManager errorAccountManager;

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

    protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
