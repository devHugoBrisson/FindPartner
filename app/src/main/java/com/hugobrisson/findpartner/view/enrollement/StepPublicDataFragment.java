package com.hugobrisson.findpartner.view.enrollement;

import android.app.DatePickerDialog;
import android.app.Fragment;

import org.androidannotations.annotations.res.StringRes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hugobrisson.findpartner.adapter.PlaceArrayAdapter;
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

    @ViewById(R.id.autoCompleteTextView)
    AutoCompleteTextView mAutoCompleteAdress;

    @ViewById(R.id.bt_progress)
    ButtonProgress buttonProgress;

    @StringRes(R.string.sign_second_step)
    String title;

    User newUser;

    private int clientId = 0;

    private GoogleApiClient mGoogleApiClient;

    private PlaceArrayAdapter mPlaceArrayAdapter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @AfterInject()
    void inject() {
        getActivity().setTitle(title);
        if (getActivity() instanceof SignInActivity_) {
            SignInActivity_ mSignInActivity = (SignInActivity_) getActivity();
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(mSignInActivity, clientId, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                        }
                    })
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    })
                    .build();
        }
    }

    @AfterViews()
    void configure() {
        newUser = (User) User.getCurrentUser();
        if (newUser == null) {
            //TODO error and come back fragment.
        }
        mAutoCompleteAdress.setThreshold(3);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getActivity(), R.layout.address_item, BOUNDS_MOUNTAIN_VIEW, null);
        mAutoCompleteAdress.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i("test", "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i("test", "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("test", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
        }
    };


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


    @Click(R.id.bt_float_step)
    void click() {
        buttonProgress.start();

        String birthDate = ttBirthdate.getText().toString();
        boolean isMan = true;
        boolean isWoman = true;

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
                    // fragmentManager.changeFragment(getFragmentManager(), StepPublicDataFragment.this, new StepSportFragment_(), newUser.getObjectId());
                }

                @Override
                public void onFailure() {
                    buttonProgress.stop();
                }
            });
        }
    }
}


