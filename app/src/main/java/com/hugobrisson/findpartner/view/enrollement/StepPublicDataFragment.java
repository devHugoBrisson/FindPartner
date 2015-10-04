package com.hugobrisson.findpartner.view.enrollement;

import android.app.DatePickerDialog;
import android.app.Fragment;

import org.androidannotations.annotations.res.StringRes;

import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.R;


import com.hugobrisson.findpartner.custom.ButtonProgress;
import com.hugobrisson.findpartner.manager.ErrorManager;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.manager.SnackBarManager;
import com.hugobrisson.findpartner.manager.UserManager;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.utils.DatePickerFragment;

import com.rey.material.widget.RadioButton;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 28/05/2015.
 */
@EFragment(R.layout.fragment_public_data_two)
public class StepPublicDataFragment extends Fragment {

    @Bean
    FragmentTransitionManager fragmentManager;

    @Bean
    ErrorManager errorManager;

    @Bean
    SnackBarManager snackBarManager;

    @Bean
    UserManager userManager;

    @ViewById(R.id.tt_birthdate)
    TextView ttBirthdate;

    @ViewById(R.id.rb_man)
    RadioButton rbMan;

    @ViewById(R.id.rb_woman)
    RadioButton rbWoman;

    @ViewById(R.id.autoCompleteTextView)
    AutoCompleteTextView acttAddress;

    @ViewById(R.id.bt_progress)
    ButtonProgress buttonProgress;

    @StringRes(R.string.sign_second_step)
    String title;

    User newUser;

    @AfterInject()
    void inject() {
        getActivity().setTitle(title);
    }

    @AfterViews()
    void configure() {
        newUser = (User) User.getCurrentUser();
        if (newUser == null) {
            //TODO error and come back fragment.
        }
    }

    @Click(R.id.tt_birthdate)
    void clickedBirthDate() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), null);
        datePickerFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month;
                String day;
                monthOfYear++;
                month = String.valueOf(monthOfYear);
                day = String.valueOf(dayOfMonth);
                if (month.length() == 1) {
                    month = "0" + month;
                } else if (day.length() == 1) {
                    day = "0" + day;
                }
                ttBirthdate.setText(day + "/" + month + "/" + year);
            }
        });

    }

    @CheckedChange({R.id.rb_man, R.id.rb_woman})
    void checkedChangedOnSomeCheckBoxs(CompoundButton rbButton, boolean isChecked) {
        if (isChecked) {
            rbMan.setChecked(rbMan == rbButton);
            rbWoman.setChecked(rbWoman == rbButton);
        }
    }

    @Click(R.id.bt_float_step)
    void click() {
        buttonProgress.start();

        String birthDate = ttBirthdate.getText().toString();
        boolean isMan = rbMan.isChecked();
        boolean isWoman = rbWoman.isChecked();

        if (errorManager.allErrorForPublicData(getView(), birthDate, isMan, isWoman)) {
            SimpleDateFormat textFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date myDate = null;
            try {
                myDate = textFormat.parse(birthDate);
            } catch (Exception e) {
                //TODO anything
            }

            newUser.setBirthdate(myDate);
            if (isMan) {
                newUser.setGender("Homme");
            } else {
                newUser.setGender("Femme");
            }

            userManager.saveServer(newUser, new IParseCallBack() {
                @Override
                public void onSuccess() {
                    buttonProgress.stop();
                    fragmentManager.changeFragment(getFragmentManager(), StepPublicDataFragment.this, new StepSportFragment_(), newUser.getObjectId());
                }

                @Override
                public void onFailure() {
                    buttonProgress.stop();
                }
            });
        }
    }
}


