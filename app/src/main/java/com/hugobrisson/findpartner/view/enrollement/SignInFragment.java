package com.hugobrisson.findpartner.view.enrollement;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.res.StringRes;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.ButtonProgress;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by hugo on 28/05/2015.
 */
@EFragment(R.layout.fragment_sign_in)
public class SignInFragment extends FragmentController {

    @ViewById(R.id.et_email)
    EditText etMail;

    @ViewById(R.id.et_name)
    EditText etName;

    @ViewById(R.id.et_surname)
    EditText etSurname;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @ViewById(R.id.rb_man)
    RadioButton rbMan;

    @ViewById(R.id.rb_woman)
    RadioButton rbWoman;

    @ViewById(R.id.bt_progress)
    ButtonProgress buttonProgress;

    @ViewById(R.id.cb_cgu)
    CheckBox cbCgu;

    @StringRes(R.string.error_cgu)
    String errorCGU;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            TextInputLayout mCurrentTextInputLayout = errorAccountManager.getCurrentTextInputLayout();
            if (mCurrentTextInputLayout != null) {
                if (mCurrentTextInputLayout.isErrorEnabled()) {
                    mCurrentTextInputLayout.setError(null);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @AfterViews()
    void configure() {
        etMail.addTextChangedListener(watcher);
        etName.addTextChangedListener(watcher);
        etSurname.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        etConfirmPassword.addTextChangedListener(watcher);
    }

    @CheckedChange({R.id.rb_man, R.id.rb_woman})
    void checkedChangedOnSomeCheckBox(CompoundButton rbButton, boolean isChecked) {
        if (isChecked) {
            rbMan.setChecked(rbMan == rbButton);
            rbWoman.setChecked(rbWoman == rbButton);
        }
    }

    @Click(R.id.bt_float_step)
    void click() {
        String mail = etMail.getText().toString();
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String password = etPassword.getText().toString();
        boolean isMan = rbMan.isChecked();

        if (errorAccountManager.allErrorSignIn(etMail, etName, etSurname, etPassword, etConfirmPassword)) {
            if (!cbCgu.isChecked()) {
              //  snackBarManager.show(getView(), errorCGU);
            } else {
                buttonProgress.start();
                final User user = new User();
                user.setUsername(mail);
                user.setEmail(mail);
                user.setPassword(password);
                user.setFirstName(surname);
                user.setLastName(name);
                if (isMan) {
                    user.setGender("Homme");
                } else {
                    user.setGender("Femme");
                }

                userManager.signUp(user, new IParseCallBack() {
                    @Override
                    public void onSuccess() {
                        buttonProgress.stop();
                        mActivityListener.changeActivity(new HomeActivity_());
                    }

                    @Override
                    public void onFailure() {
                        buttonProgress.stop();
                     //   snackBarManager.show(getView(), "erreur lors de l'envoi au serveur");

                    }
                });
            }
        }
    }


}
