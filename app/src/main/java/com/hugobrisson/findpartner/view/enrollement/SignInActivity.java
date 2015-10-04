package com.hugobrisson.findpartner.view.enrollement;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.manager.DialogManager;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.utils.IDialogCallBack;
import com.rey.material.app.DialogFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;


/**
 * Created by hugo on 27/05/2015.
 */

@EActivity(R.layout.activity_sign_in)
public class SignInActivity extends ActionBarActivity {

    public enum Step {
        StepOne, StepTwo, StepThree,
    }

    @Bean
    DialogManager dialogManager;

    @Bean
    FragmentTransitionManager fragmentManager;

    @AfterViews()
    void configure() {
        fragmentManager.initActivity(getFragmentManager(), new StepPrivateDataFragment_());
    }

    @Override
    public void onBackPressed() {
        dialogManager.showDialog(DialogManager.Dialog.SIMPLE, "Perte d'informations", "Si vous continuez, Toutes vos informations saisies précédement vont être perdues.", "Ok", "Annuler", getSupportFragmentManager(), new IDialogCallBack() {
            @Override
            public void positiveAction() {
                Toast.makeText(getApplicationContext(), "positive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void negativeAction() {
                Toast.makeText(getApplicationContext(), "negative", Toast.LENGTH_LONG).show();
            }
        });
    }
}
