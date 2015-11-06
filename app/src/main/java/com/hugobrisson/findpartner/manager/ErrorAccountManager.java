package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.res.StringRes;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

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
public class ErrorAccountManager {

    private Pattern pattern;
    private TextInputLayout mCurrentTextInputLayout;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @RootContext
    Context context;

    @Bean
    SnackBarManager snackBarManager;

    @StringRes(R.string.error_mail)
    String errorMail;

    @StringRes(R.string.error_name)
    String errorName;

    @StringRes(R.string.error_surname)
    String errorSurname;

    @StringRes(R.string.error_password)
    String errorPassword;

    @StringRes(R.string.error_confirm_password)
    String errorConfirmPassword;

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
    public boolean validUsername(String name) {
        return !"".equals(name) && name.length() >= 2;
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
     *
     * @param mail
     * @param name
     * @param surname
     * @param password
     * @param confirmPassword
     * @return
     */
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
     * get current textInputLayout for disable error.
     * @return
     */
    public TextInputLayout getCurrentTextInputLayout() {
        return mCurrentTextInputLayout;
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
