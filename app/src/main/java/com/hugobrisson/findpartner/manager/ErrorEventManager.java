package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.view.View;

import com.google.android.gms.location.places.Place;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.EventDoubleItem;
import com.hugobrisson.findpartner.custom.EventItem;
import com.hugobrisson.findpartner.model.Sport;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 08/11/2015.
 */
@EBean
public class ErrorEventManager {

    @RootContext
    Context context;

    @Bean
    SnackBarManager snackBarManager;

    @StringRes(R.string.error_no_title)
    String errorNoTitle;

    @StringRes(R.string.error_no_sport)
    String errorNoSport;

    @StringRes(R.string.error_no_date)
    String errorNoDate;

    @StringRes(R.string.error_date_out)
    String errorDateOut;

    @StringRes(R.string.error_time)
    String errorTime;

    @StringRes(R.string.error_no_place)
    String errorNoPlace;

    @StringRes(R.string.error_partner)
    String errorPartner;

    @StringRes(R.string.error_price)
    String errorPrice;


    /**
     * verify title.
     *
     * @param title
     * @return
     */
    private boolean validTitle(String title) {
        return !"".equals(title);
    }

    /**
     * verify sport.
     *
     * @param sport
     * @return
     */
    private boolean validSport(Sport sport) {
        return sport != null;
    }

    /**
     * verify date.
     *
     * @param eventItem
     * @return
     */
    private boolean validDate(EventItem eventItem) {
        Date strDate;
        Date currentDate;
        if (!eventItem.getText().equals(eventItem.getTitle())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                strDate = sdf.parse(eventItem.getText());
                currentDate = new Date();
                String strCurrentDate = sdf.format(currentDate);
                currentDate = sdf.parse(strCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            if (strDate.after(currentDate) || currentDate.equals(strDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * verify time
     *
     * @param eventDoubleItem
     * @return
     */
    private boolean validTime(EventDoubleItem eventDoubleItem) {
        Date dateStart;
        Date dateEnd;
        if (!eventDoubleItem.getText(true).equals(eventDoubleItem.getTitleFirstItem()) && !eventDoubleItem.getText(false).equals(eventDoubleItem.getmTitleSecondItem())) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            try {
                dateStart = sdf.parse(eventDoubleItem.getText(true));
                dateEnd = sdf.parse(eventDoubleItem.getText(false));
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            if (dateEnd.getTime() > dateStart.getTime()) {
                return true;
            }
        }
        return false;
    }

    /**
     * verify the start time begin after end time.
     *
     * @param time
     * @param date
     * @return
     */
    private boolean validTimeWithDate(String time, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date currentDate = new Date();
        try {
            Date concatDate = dateFormat.parse(date + " " + time);
            if (concatDate.after(currentDate)) {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * verify place.
     *
     * @param place
     * @return
     */
    private boolean validPlace(Place place) {
        return place != null;
    }

    /**
     * verify the already partner number is inferior to max partner number.
     *
     * @param eventDoubleItem
     * @return
     */
    private boolean validPartner(EventDoubleItem eventDoubleItem) {
        if (!eventDoubleItem.getText(true).equals(eventDoubleItem.getTitleFirstItem()) && !eventDoubleItem.getText(false).equals(eventDoubleItem.getmTitleSecondItem())) {
            int numberAlready = Integer.parseInt(eventDoubleItem.getText(true));
            int numberMax = Integer.parseInt(eventDoubleItem.getText(false));
            return numberMax > numberAlready;
        }
        return false;
    }

    /**
     * @param eventItem
     * @return
     */
    private boolean validPrice(EventItem eventItem) {
        return !eventItem.getText().equals(eventItem.getTitle()) && !eventItem.getText().equals("0");
    }

    /**
     * looks like all error for event model.
     *
     * @param view
     * @param sport
     * @param calendarItem
     * @param timeItem
     * @param place
     * @param partnerItem
     * @param isChecked
     * @param priceItem
     * @return
     */
    public boolean errorEvent(View view, String title, Sport sport, EventItem calendarItem, EventDoubleItem timeItem, Place place, EventDoubleItem partnerItem, boolean isChecked, EventItem priceItem) {
        if (!validTitle(title)) {
            snackBarManager.show(view, errorNoTitle);
        } else if (!validSport(sport)) {
            snackBarManager.show(view, errorNoSport);
        } else if (!validDate(calendarItem)) {
            snackBarManager.show(view, errorNoDate);
        } else if (!validTime(timeItem)) {
            snackBarManager.show(view, errorTime);
        } else if (!validTimeWithDate(timeItem.getText(true), calendarItem.getText())) {
            snackBarManager.show(view, errorTime);
        } else if (!validPlace(place)) {
            snackBarManager.show(view, errorNoPlace);
        } else if (!validPartner(partnerItem)) {
            snackBarManager.show(view, errorPartner);
        } else if (isChecked && !validPrice(priceItem)) {
            snackBarManager.show(view, errorPrice);
        } else {
            return true;
        }
        return false;
    }
}
