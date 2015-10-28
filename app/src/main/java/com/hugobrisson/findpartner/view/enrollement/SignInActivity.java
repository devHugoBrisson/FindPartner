package com.hugobrisson.findpartner.view.enrollement;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.manager.DialogManager;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.utils.ActivityListener;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_sign_in)
public class SignInActivity extends AppCompatActivity implements ActivityListener {

    @Bean
    DialogManager dialogManager;

    @Bean
    FragmentTransitionManager fragmentManager;

    @AfterInject
    void create() {
        if (ParseUser.getCurrentUser() != null) {
            Toast.makeText(SignInActivity.this, "OK", Toast.LENGTH_LONG).show();
            HomeActivity_.intent(this).start();
            finish();
        } else {
            Toast.makeText(SignInActivity.this, "KO", Toast.LENGTH_LONG).show();
        }
    }

    @AfterViews
    void configure() {
        fragmentManager.initFragment(getSupportFragmentManager(), new LoginFragment_());
    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager.changeFragment(getSupportFragmentManager(), fragment);
    }

    @Override
    public void changeActivity(Activity activity) {
        startActivity(new Intent(this, activity.getClass()));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}

