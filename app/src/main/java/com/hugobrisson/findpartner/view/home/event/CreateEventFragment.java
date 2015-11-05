package com.hugobrisson.findpartner.view.home.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.PlaceArrayAdapter;
import com.hugobrisson.findpartner.custom.EventItem;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.DialogType;
import com.hugobrisson.findpartner.utils.IDialogCallBack;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.common.PickerDialog;
import com.hugobrisson.findpartner.view.enrollement.SignInActivity_;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.hugobrisson.findpartner.view.home.sport.FragmentSportList_;
import com.rey.material.widget.CheckBox;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * Created by hugo on 04/11/2015.
 */
@EFragment(R.layout.fragment_create_event)
public class CreateEventFragment extends FragmentController {

    @ViewById(R.id.sport_item)
    EventItem sportItem;

    @ViewById(R.id.calendar_item)
    EventItem calendarItem;

    @ViewById(R.id.time_start_item)
    EventItem timeStartItem;

    @ViewById(R.id.time_end_item)
    EventItem timeEndItem;

    @ViewById(R.id.partner_max_item)
    EventItem partnerMaxItem;

    @ViewById(R.id.partner_already_item)
    EventItem partnerAlreadyItem;

    @ViewById(R.id.price_item)
    EventItem priceItem;

    @ViewById(R.id.autoCompleteTextView)
    AutoCompleteTextView mAutoCompleteAddress;

    @ViewById(R.id.cb_free)
    CheckBox cbFree;

    private Sport mSport;

    private GoogleApiClient mGoogleApiClient;

    private PlaceArrayAdapter mPlaceArrayAdapter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @AfterInject
    void create() {
        if (getActivity() instanceof HomeActivity_) {
            HomeActivity_ mHomeActivity = (HomeActivity_) getActivity();
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(mHomeActivity, 0, new GoogleApiClient.OnConnectionFailedListener() {
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

    @AfterViews
    void configure() {
        if (sportItem.getText().equals("Sport") && mSport != null) {
            sportItem.setText(mSport.getName());
        }
        mAutoCompleteAddress.setThreshold(3);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getActivity(), R.layout.address_item, BOUNDS_MOUNTAIN_VIEW, null);
        mAutoCompleteAddress.setAdapter(mPlaceArrayAdapter);
    }

    @Click({R.id.sport_item, R.id.calendar_item, R.id.time_start_item, R.id.time_end_item, R.id.partner_max_item, R.id.partner_already_item, R.id.price_item})
    void click(View view) {
        switch (view.getId()) {
            case R.id.sport_item:
                mActivityListener.changeFragment(new FragmentSportList_());
                break;
            case R.id.calendar_item:
                showPickerDialog(calendarItem, DialogType.CALENDAR);
                break;
            case R.id.time_start_item:
                showPickerDialog(timeStartItem, DialogType.TIME);
                break;
            case R.id.time_end_item:
                showPickerDialog(timeEndItem, DialogType.TIME);
                break;
            case R.id.partner_max_item:
                showPickerDialog(partnerMaxItem, DialogType.NUMBER);
                break;
            case R.id.partner_already_item:
                showPickerDialog(partnerAlreadyItem, DialogType.NUMBER);
                break;
            case R.id.price_item:

                break;
        }
    }

    /**
     * @param sport
     */
    public void updateSportItem(Sport sport) {
        mSport = sport;
    }


    /**
     * @param eventItem
     * @param dialogType
     */
    private void showPickerDialog(final EventItem eventItem, DialogType dialogType) {
        PickerDialog pickerDialog = new PickerDialog(getActivity(), dialogType, "Partenaire ", new IDialogCallBack() {
            @Override
            public void onResultDatePicker(int day, int month, int year) {
                eventItem.setText(String.valueOf(day) + "/" + (month + 1) + "/" + year);
            }

            @Override
            public void onResultTimePicker(int hour, int minute) {
                eventItem.setText(String.valueOf(new StringBuilder().append(hour).append(":").append(minute)));
            }

            @Override
            public void onResultNumberPicker(int number) {
                eventItem.setText(String.valueOf(number));
            }
        });
        pickerDialog.show();

    }
}
