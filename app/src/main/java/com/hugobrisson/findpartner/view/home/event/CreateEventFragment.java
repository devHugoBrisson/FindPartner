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
import com.hugobrisson.findpartner.utils.DialogType;
import com.hugobrisson.findpartner.utils.IDialogCallBack;
import com.hugobrisson.findpartner.utils.IDoubleEventListener;
import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.common.PickerDialog;
import com.hugobrisson.findpartner.view.home.location.FragmentAddressList_;
import com.hugobrisson.findpartner.view.home.sport.FragmentSportList_;
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

    @ViewById(R.id.cb_free)
    CheckBox cbFree;

    @ViewById(R.id.progress_wait)
    ProgressView progressView;

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
                mActivityListener.changeFragment(new FragmentSportList_());
                break;
            case R.id.calendar_item:
                showPickerDialog(calendarItem, DialogType.CALENDAR);
                break;
            case R.id.address_item:
                mActivityListener.changeFragment(new FragmentAddressList_());
                break;
            case R.id.price_item:
                showPickerDialog(priceItem, DialogType.NUMBER);
                break;
            case R.id.bt_float_step:
                clickSaveEvent();
                break;
        }
    }

    @CheckedChange(R.id.cb_free)
    void check(boolean isCheck) {
        if (isCheck) {
            priceItem.setVisibility(View.VISIBLE);
        } else {
            priceItem.setVisibility(View.GONE);
        }
    }

    private void clickSaveEvent() {
        String title = etTitleEvent.getText().toString();
        title = title.replace(title.substring(0, 1), title.substring(0, 1).toUpperCase());
        String desc = etDescEvent.getText().toString();
        desc = desc.replace(desc.substring(0, 1), desc.substring(0, 1).toUpperCase());

        if (errorEventManager.errorEvent(getView(), title, mSport, calendarItem, timeItem, mPlace, partnerItem, cbFree.isChecked(), priceItem)) {
            progressView.start();
            mEvent.setName(title);
            mEvent.setDesc(desc);
            mEvent.setOwnerId(ParseUser.getCurrentUser());
            mEvent.setSportId(mSport);
            mEvent.setDateStart(concatDate(calendarItem.getText(), timeItem.getText(true)));
            mEvent.setDateEnd(concatDate(calendarItem.getText(), timeItem.getText(false)));
            mEvent.setAddress(mPlace.getAddress().toString());
            mEvent.setLocation(new ParseGeoPoint(mPlace.getLatLng().latitude, mPlace.getLatLng().longitude));
            mEvent.setNbAlreadyPartner(Integer.parseInt(partnerItem.getText(true)));
            mEvent.setNbPartner(Integer.parseInt(partnerItem.getText(false)));
            mEvent.setIsFree(!cbFree.isChecked());
            if (cbFree.isChecked()) {
                mEvent.setPrice(Integer.parseInt(priceItem.getText()));
            } else {
                mEvent.setPrice(0);
            }
            //TODO if error
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

    private Date concatDate(String date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date + " " + time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    private String formatDate(int day, int month, int year) {
        String months;
        String days;
        month++;
        months = String.valueOf(month);
        days = String.valueOf(day);
        if (months.length() == 1) {
            months = "0" + month;
        }
        if (days.length() == 1) {
            days = "0" + day;
        }
        return String.valueOf(days) + "/" + (months) + "/" + year;
    }

    private String formatTime(int hour, int minute) {
        String hours;
        String minutes;
        hours = String.valueOf(hour);
        minutes = String.valueOf(minute);
        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        return String.valueOf(hours) + ":" + (minutes);
    }

    /**
     * show dialog depending to dialogType.
     *
     * @param eventItem
     * @param dialogType
     */
    private void showPickerDialog(final EventItem eventItem, DialogType dialogType) {
        PickerDialog pickerDialog = new PickerDialog(getActivity(), dialogType, "Partenaire ", new IDialogCallBack() {
            @Override
            public void onResultDatePicker(int day, int month, int year) {
                eventItem.setText(formatDate(day, month, year));
                mDataEvent[0] = eventItem.getText();
            }

            @Override
            public void onResultTimePicker(int hour, int minute) {
                eventItem.setText(formatTime(hour, minute));
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
        PickerDialog pickerDialog = new PickerDialog(getActivity(), dialogType, "Partenaire ", new IDialogCallBack() {
            @Override
            public void onResultDatePicker(int day, int month, int year) {
            }

            @Override
            public void onResultTimePicker(int hour, int minute) {
                if (isFirstEvent) {
                    timeItem.setText(formatTime(hour, minute), true);
                    mDataEvent[1] = timeItem.getText(true);
                } else {
                    timeItem.setText(formatTime(hour, minute), false);
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
