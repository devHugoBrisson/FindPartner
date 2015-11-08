package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.EventDoubleItem;
import com.hugobrisson.findpartner.custom.EventItem;
import com.hugobrisson.findpartner.model.Sport;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hugo on 08/11/2015.
 */
public class ErrorEventManager {

    @RootContext
    Context context;

    @Bean
    SnackBarManager snackBarManager;

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
     * verify the Username.
     *
     * @param sport
     * @return
     */
    public boolean validSport(Sport sport) {
        return sport != null;
    }

    public boolean validDate(EventItem eventItem) {
        Date strDate;
        if (!eventItem.getText().equals(eventItem.getTitle())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                strDate = sdf.parse(eventItem.getText());
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            Date date = new Date();
            if (date.after(strDate) || date.equals(strDate)) {
                return true;
            }
        }
        return false;
    }

    public boolean validDateNoOut(String date) {
        Date strDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Date currentDate = new Date();
        if (currentDate.after(strDate) || currentDate.equals(strDate)) {
            return true;
        }
        return false;
    }

    public boolean validTime(EventDoubleItem eventDoubleItem) {
        if (!eventDoubleItem.getText(true).equals(eventDoubleItem.getTitleFirstItem()) && !eventDoubleItem.getText(false).equals(eventDoubleItem.getmTitleSecondItem())) {
            return true;
        }
        return false;
    }

    public boolean validTimeWithDate(EventDoubleItem eventDoubleItem, String Date) {
return true;
    }


    /**
     * verify password is validate.
     *
     * @param password
     * @return
     */
    public boolean validPassword(String password) {
        return !"".equals(password);
    }

    /**
     * verify if its the same password.
     *
     * @param password
     * @param confirmPassword
     * @return
     */
    public boolean notEqualsPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * verify birthDate is validate.
     *
     * @param birthDate
     * @return
     */
    public boolean validBirthDate(String birthDate) {
        return !birthDate.equals(context.getResources().getString(R.string.birth_date));
    }

    /**
     * looks all error for private data.
     * @return
     *
    public boolean allErrorSignIn(EditText mail, EditText name, EditText surname, EditText password, EditText confirmPassword) {
        TextInputLayout til;
        if (!validateMail(mail.getText().toString())) {
            til = (TextInputLayout) mail.getParent();
            til.setErrorEnabled(true);
            til.setError(errorMail);
        } else if (!validUsername(name.getText().toString())) {
            til = (TextInputLayout) name.getParent();
            til.setErrorEnabled(true);
            til.setError(errorName);
        } else if (!validUsername(surname.getText().toString())) {
            til = (TextInputLayout) surname.getParent();
            til.setErrorEnabled(true);
            til.setError(errorSurname);
        } else if (!validPassword(password.getText().toString())) {
            til = (TextInputLayout) password.getParent();
            til.setErrorEnabled(true);
            til.setError(errorPassword);
        } else if (!notEqualsPassword(password.getText().toString(), confirmPassword.getText().toString())) {
            til = (TextInputLayout) confirmPassword.getParent();
            til.setErrorEnabled(true);
            til.setError(errorConfirmPassword);
        } else {
            mCurrentTextInputLayout = null;
            return true;
        }

        mCurrentTextInputLayout = til;
        return false;
    }

    /**
     * ic_check internet connection.
     *
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise ic_check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
