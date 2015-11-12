package com.hugobrisson.findpartner.view.home.event;

import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.places.Place;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.EventDoubleItem;
import com.hugobrisson.findpartner.custom.EventItem;
import com.hugobrisson.findpartner.manager.ErrorEventManager;
import com.hugobrisson.findpartner.manager.EventManager;
import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.DateConverter;
import com.hugobrisson.findpartner.utils.DialogType;
import com.hugobrisson.findpartner.utils.IDialogCallBack;
import com.hugobrisson.findpartner.utils.IDoubleEventListener;
import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.common.PickerDialog;
import com.hugobrisson.findpartner.view.home.location.AddressListFragment_;
import com.hugobrisson.findpartner.view.home.sport.SportListFragment_;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 04/11/2015.
 */
@EFragment(R.layout.fragment_create_event)
public class CreateEventFragment extends FragmentController {

    @ViewById(R.id.et_title_event)
    EditText etTitleEvent;

    @ViewById(R.id.et_desc_event)
    EditText etDescEvent;

    @ViewById(R.id.sport_item)
    EventItem sportItem;

    @ViewById(R.id.calendar_item)
    EventItem calendarItem;

    @ViewById(R.id.time_item)
    EventDoubleItem timeItem;

    @ViewById(R.id.partner_item)
    EventDoubleItem partnerItem;

    @ViewById(R.id.price_item)
    EventItem priceItem;

    @ViewById(R.id.address_item)
    EventItem addressItem;

    @ViewById(R.id.cb_payable)
    CheckBox cbPayable;

    @ViewById(R.id.progress_wait)
    ProgressView progressView;

    @StringRes(R.string.event_dialog_title_date)
    String titleDate;

    @StringRes(R.string.event_dialog_title_start_time)
    String titleStartTime;

    @StringRes(R.string.event_dialog_title_end_time)
    String titleEndTime;

    @StringRes(R.string.event_dialog_title_already_partner)
    String titleAlreadyPartner;

    @StringRes(R.string.event_dialog_title_max_partner)
    String titleMaxPartner;

    @StringRes(R.string.event_dialog_title_price)
    String titlePrice;

    @Bean
    EventManager eventManager;

    @Bean
    ErrorEventManager errorEventManager;

    private String[] mDataEvent = new String[6];

    private Sport mSport;

    private Place mPlace;

    private Event mEvent = new Event();

    private IDoubleEventListener mIDoubleEventListener = new IDoubleEventListener() {
        @Override
        public void OnItemClick(DialogType dialogType, boolean isFirstEvent) {
            showPickerDialogDoubleEvent(dialogType, isFirstEvent);
        }
    };

    @AfterViews
    void configure() {
        if (mSport != null) {
            sportItem.setText(mSport.getName());
        }
        if (mPlace != null) {
            addressItem.setText(mPlace.getName().toString());
        }

        //restore old data
        int i = 0;
        for (String data : mDataEvent) {
            if (data != null) {
                restoreData(i, data);
            }
            i++;
        }

        timeItem.setListenerAndDialogType(mIDoubleEventListener, DialogType.TIME);
        partnerItem.setListenerAndDialogType(mIDoubleEventListener, DialogType.NUMBER);
    }

    @Click({R.id.sport_item, R.id.calendar_item, R.id.time_item, R.id.address_item, R.id.price_item, R.id.bt_float_step})
    void clickEventItem(View view) {
        switch (view.getId()) {
            case R.id.sport_item:
                mActivityListener.changeFragment(new SportListFragment_());
                break;
            case R.id.calendar_item:
                showPickerDialog(calendarItem, DialogType.CALENDAR, titleDate);
                break;
            case R.id.address_item:
                mActivityListener.changeFragment(new AddressListFragment_());
                break;
            case R.id.price_item:
                showPickerDialog(priceItem, DialogType.NUMBER, titlePrice);
                break;
            case R.id.bt_float_step:
                clickSaveEvent();
                break;
        }
    }

    @CheckedChange(R.id.cb_payable)
    void check(boolean isCheck) {
        if (isCheck) {
            priceItem.setVisibility(View.VISIBLE);
        } else {
            priceItem.setVisibility(View.GONE);
        }
    }

    private void clickSaveEvent() {
        String title = etTitleEvent.getText().toString();
        String desc = etDescEvent.getText().toString();

        if (errorEventManager.errorEvent(getView(), title, mSport, calendarItem, timeItem, mPlace, partnerItem, cbPayable.isChecked(), priceItem)) {
            progressView.start();

            title = title.toUpperCase();
            mEvent.setName(title);
            mEvent.setDesc(desc);
            mEvent.setOwnerId(ParseUser.getCurrentUser());
            mEvent.setSportId(mSport);
            mEvent.setDateStart(DateConverter.concatDate(calendarItem.getText(), timeItem.getText(true)));
            mEvent.setDateEnd(DateConverter.concatDate(calendarItem.getText(), timeItem.getText(false)));
            mEvent.setAddress(mPlace.getAddress().toString());
            mEvent.setLocation(new ParseGeoPoint(mPlace.getLatLng().latitude, mPlace.getLatLng().longitude));
            mEvent.setNbAlreadyPartner(Integer.parseInt(partnerItem.getText(true)));
            mEvent.setNbPartner(Integer.parseInt(partnerItem.getText(false)));
            mEvent.setIsPayable(cbPayable.isChecked());
            if (cbPayable.isChecked()) {
                mEvent.setPrice(Integer.parseInt(priceItem.getText()));
            } else {
                mEvent.setPrice(0);
            }

            eventManager.saveServer(mEvent, new IParseCallBack() {
                @Override
                public void onSuccess() {
                    progressView.stop();
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                @Override
                public void onFailure() {
                    progressView.stop();
                }
            });
        }
    }

    /**
     * @param sport
     */
    public void updateSportItem(Sport sport) {
        mSport = sport;
    }

    /**
     * @param place
     */
    public void updateAddressItem(Place place) {
        mPlace = place;
    }

    /**
     * show dialog depending to dialogType.
     *
     * @param eventItem
     * @param dialogType
     */
    private void showPickerDialog(final EventItem eventItem, DialogType dialogType, String title) {
        PickerDialog pickerDialog = new PickerDialog(getActivity(), dialogType, title, new IDialogCallBack() {
            @Override
            public void onResultDatePicker(int day, int month, int year) {
                eventItem.setText(DateConverter.formatDate(day, month, year));
                mDataEvent[0] = eventItem.getText();
            }

            @Override
            public void onResultTimePicker(int hour, int minute) {
                eventItem.setText(DateConverter.formatTime(hour, minute));
            }

            @Override
            public void onResultNumberPicker(int number) {
                eventItem.setText(String.valueOf(number));
                mDataEvent[5] = eventItem.getText();
            }
        });
        pickerDialog.show();

    }

    /**
     * show dialog depending to dialogType.
     *
     * @param dialogType
     */
    private void showPickerDialogDoubleEvent(DialogType dialogType, final boolean isFirstEvent) {
        String title = null;

        switch (dialogType) {
            case TIME:
                if (isFirstEvent) {
                    title = titleStartTime;
                } else {
                    title = titleEndTime;
                }
                break;
            case NUMBER:
                if (isFirstEvent) {
                    title = titleAlreadyPartner;
                } else {
                    title = titleMaxPartner;
                }
                break;
        }

        PickerDialog pickerDialog = new PickerDialog(getActivity(), dialogType, title, new IDialogCallBack() {
            @Override
            public void onResultDatePicker(int day, int month, int year) {
            }

            @Override
            public void onResultTimePicker(int hour, int minute) {
                if (isFirstEvent) {
                    timeItem.setText(DateConverter.formatTime(hour, minute), true);
                    mDataEvent[1] = timeItem.getText(true);
                } else {
                    timeItem.setText(DateConverter.formatTime(hour, minute), false);
                    mDataEvent[2] = timeItem.getText(false);
                }
            }

            @Override
            public void onResultNumberPicker(int number) {
                if (isFirstEvent) {
                    partnerItem.setText(String.valueOf(number), true);
                    mDataEvent[3] = partnerItem.getText(true);
                } else {
                    partnerItem.setText(String.valueOf(number), false);
                    mDataEvent[4] = partnerItem.getText(false);
                }
            }
        });
        pickerDialog.show();

    }

    /**
     * restore old data before losted focus .
     *
     * @param i
     * @param data
     */
    private void restoreData(int i, String data) {
        switch (i) {
            case 0:
                calendarItem.setText(data);
                break;
            case 1:
                timeItem.setText(data, true);
                break;
            case 2:
                timeItem.setText(data, false);
                break;
            case 3:
                partnerItem.setText(data, true);
                break;
            case 4:
                partnerItem.setText(data, false);
                break;
            case 5:
                priceItem.setText(data);
                break;
        }
    }
}
