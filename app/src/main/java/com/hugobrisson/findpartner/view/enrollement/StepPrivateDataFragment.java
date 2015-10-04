package com.hugobrisson.findpartner.view.enrollement;

import android.app.Fragment;

import org.androidannotations.annotations.res.StringRes;

import android.widget.EditText;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.ButtonProgress;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.manager.ErrorManager;
import com.hugobrisson.findpartner.manager.SnackBarManager;
import com.hugobrisson.findpartner.manager.UserManager;
import com.hugobrisson.findpartner.model.User;
import com.rey.material.widget.CheckBox;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by hugo on 28/05/2015.
 */
@EFragment(R.layout.fragment_private_data_one)
public class StepPrivateDataFragment extends Fragment {

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

    @AfterInject()
    void configure() {
        getActivity().setTitle(getString(R.string.sign_first_step));
    }

    @Click(R.id.bt_float_step)
    void click() {
        String mail = etMail.getText().toString();
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (errorManager.allErrorForPrivateData(getView(), mail, name, surname, password, confirmPassword)) {
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

                userManager.signUp(user, new IParseCallBack() {
                    @Override
                    public void onSuccess() {
                        buttonProgress.stop();
                        fragmentManager.changeFragment(getFragmentManager(), StepPrivateDataFragment.this, new StepPublicDataFragment_());
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
