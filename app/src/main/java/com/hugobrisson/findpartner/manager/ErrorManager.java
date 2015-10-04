package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.res.StringRes;

import android.view.View;

import com.hugobrisson.findpartner.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hugo on 27/06/2015.
 */
@EBean
public class ErrorManager {

    private Pattern pattern;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @RootContext
    Context context;

    @Bean
    SnackBarManager snackBarManager;

    @StringRes(R.string.error_mail)
    String errorMail;

    @StringRes(R.string.error_username)
    String errorUsername;

    @StringRes(R.string.error_password)
    String errorPassword;

    @StringRes(R.string.error_birthdate)
    String errorBirthDate;

    @StringRes(R.string.error_gender)
    String errorGender;

    @AfterInject
    void inject() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression.
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validateMail(String hex) {

        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    /**
     * verify the Username.
     *
     * @param name
     * @return
     */
    public boolean validUsername(String name, String surname) {
        if (!"".equals(name) && name.length() >= 2 && !"".equals(surname) && surname.length() >= 2) {
            return true;
        }
        return false;
    }

    /**
     * verify password is validate.
     *
     * @param password
     * @param confirmPassword
     * @return
     */
    public boolean validPassword(String password, String confirmPassword) {
        if ("".equals(password) || "".equals(confirmPassword)) {
            return false;
        } else if (!password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }

    /**
     * verify birthDate is validate.
     *
     * @param birthDate
     * @return
     */
    public boolean validBirthDate(String birthDate) {
        if (birthDate.equals(context.getResources().getString(R.string.birth_date))) {
            return false;
        }
        return true;
    }

    /**
     * verify gender is validate.
     *
     * @param isMan
     * @param isWoman
     * @return
     */
    public boolean validGender(boolean isMan, boolean isWoman) {
        if (!isMan && !isWoman) {
            return false;
        }
        return true;
    }

    /**
     * looks all error for private data.
     *
     * @param view
     * @param mail
     * @param name
     * @param surname
     * @param password
     * @param confirmPassword
     * @return
     */
    public boolean allErrorForPrivateData(View view, String mail, String name, String surname, String password, String confirmPassword) {
        if (!validateMail(mail)) {
            snackBarManager.show(view, errorMail);
        } else if (!validUsername(name, surname)) {
            snackBarManager.show(view, errorUsername);
        } else if (!validPassword(password, confirmPassword)) {
            snackBarManager.show(view, errorPassword);
        } else {
            return true;
        }

        return false;
    }

    /**
     * looks all error for public data.
     *
     * @param view
     * @param birthDate
     * @param isMan
     * @param isWoman
     * @return
     */
    public boolean allErrorForPublicData(View view, String birthDate, boolean isMan, boolean isWoman) {
        if (!validBirthDate(birthDate)) {
            snackBarManager.show(view, errorBirthDate);
        } else if (!validGender(isMan, isWoman)) {
            snackBarManager.show(view, errorGender);
        } else {
            return true;
        }
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
