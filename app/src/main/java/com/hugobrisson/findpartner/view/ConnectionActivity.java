package com.hugobrisson.findpartner.view;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.view.enrollement.SignInActivity_;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rey.material.widget.Button;

import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;


@EActivity(R.layout.activity_connection)
public class ConnectionActivity extends AppCompatActivity {

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

    @AfterInject
    void create() {

        if (ParseUser.getCurrentUser() != null) {
            Toast.makeText(ConnectionActivity.this, "OK", Toast.LENGTH_LONG).show();
            HomeActivity_.intent(this).start();
            finish();
        } else {
            Toast.makeText(ConnectionActivity.this, "KO", Toast.LENGTH_LONG).show();

        }
    }

    @Click(R.id.bt_connect)
    void clickConnect() {
        if (isNetworkAvailable()) {
            pv_linear.start();
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("email", tfEmail.getText().toString());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    if (e == null) {
                        if (parseUsers.size() == 1) {
                            ParseUser parseUser = parseUsers.get(0);
                            ParseUser.logInInBackground(parseUser.getUsername(), tfPassword.getText().toString(), new LogInCallback() {
                                public void done(ParseUser user, ParseException e) {
                                    if (e == null) {
                                        if (user != null) {
                                            //startActivity
                                            pv_linear.stop();
                                            Toast.makeText(ConnectionActivity.this, "OK", Toast.LENGTH_LONG).show();
                                        } else {
                                            pv_linear.stop();
                                            Toast.makeText(ConnectionActivity.this, "KO", Toast.LENGTH_LONG).show();
                                            // Signup failed. Look at the ParseException to see what happened.
                                        }
                                    } else {
                                        pv_linear.stop();
                                        Toast.makeText(ConnectionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            pv_linear.stop();
                            Toast.makeText(ConnectionActivity.this, getString(R.string.error_invalid_connect), Toast.LENGTH_LONG).show();
                            //error
                        }
                    } else {
                        pv_linear.stop();
                        Toast.makeText(ConnectionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        //error
                    }
                }
            });
        } else {
            Toast.makeText(ConnectionActivity.this, getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Click(R.id.tt_sign_in)
    void clickSignIn() {
        SignInActivity_.intent(this).start();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise ic_check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

}

