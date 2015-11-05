package com.hugobrisson.findpartner.view.enrollement;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.Tools;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;


import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by hugobrisson on 20/10/15.
 */
@EFragment(R.layout.fragment_log_in)
public class LoginFragment extends FragmentController {

    @ViewById(R.id.layout_logo)
    LinearLayout llLogo;

    @ViewById(R.id.scrollView_connect)
    ScrollView svConnect;

    @ViewById(R.id.tf_email)
    EditText tfEmail;

    @ViewById(R.id.tf_password)
    EditText tfPassword;

    @ViewById(R.id.tt_forgotten_password)
    TextView ttForgotPassword;

    @ViewById(R.id.tt_sign_in)
    TextView ttSignIn;

    @ViewById(R.id.bt_connect)
    Button btConnect;

    @ViewById(R.id.progress)
    ProgressView pv_linear;

    @StringRes(R.string.error_invalid_connect)
    String errorConnect;

    @Click(R.id.bt_connect)
    void clickConnect() {
        String email = tfEmail.getText().toString();
        String password = tfPassword.getText().toString();

        if (Tools.isNetworkAvailable(getActivity())) {
            pv_linear.start();
            ParseUser.logInInBackground(email, password, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        if (user != null) {
                            pv_linear.stop();
                            mActivityListener.changeActivity(new HomeActivity_());
                        } else {
                            pv_linear.stop();
                            Toast.makeText(getActivity(), errorConnect, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pv_linear.stop();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Click(R.id.tt_sign_in)
    void clickSignIn() {
        mActivityListener.changeFragment(new SignInFragment_());
    }
}
