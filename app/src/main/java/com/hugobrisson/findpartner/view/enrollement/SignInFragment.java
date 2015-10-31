package com.hugobrisson.findpartner.view.enrollement;

import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.res.StringRes;

import android.widget.CompoundButton;
import android.widget.EditText;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.ButtonProgress;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.manager.ErrorManager;
import com.hugobrisson.findpartner.manager.SnackBarManager;
import com.hugobrisson.findpartner.manager.UserManager;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.view.FragmentController;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;

import org.androidannotations.annotations.Bean;
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

    @ViewById(R.id.rb_man)
    RadioButton rbMan;

    @ViewById(R.id.rb_woman)
    RadioButton rbWoman;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @ViewById(R.id.bt_progress)
    ButtonProgress buttonProgress;

    @ViewById(R.id.cb_cgu)
    CheckBox cbCgu;

    @StringRes(R.string.error_cgu)
    String errorCGU;

    @Bean
    SnackBarManager snackBarManager;

    @Bean
    ErrorManager errorManager;

    @Bean
    UserManager userManager;

    @Bean
    FragmentTransitionManager fragmentManager;

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
        String confirmPassword = etConfirmPassword.getText().toString();
        boolean isMan = rbMan.isChecked();

        if (errorManager.allErrorSignIn(getView(), etMail, etName, etSurname, etPassword, etConfirmPassword)) {
            if (!cbCgu.isChecked()) {
                snackBarManager.show(getView(), errorCGU);
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
                        snackBarManager.show(getView(), "erreur lors de l'envoi au serveur");

                    }
                });
            }
        }
    }


}
