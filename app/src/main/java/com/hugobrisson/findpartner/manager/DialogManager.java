package com.hugobrisson.findpartner.manager;

import android.support.v4.app.FragmentManager;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.IDialogCallBack;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import org.androidannotations.annotations.EBean;

/**
 * Created by hugo on 16/07/2015.
 */
@EBean
public class DialogManager {

    public enum Dialog {
        SIMPLE
    }

    /**
     * @param dialog
     * @param title
     * @param message
     * @param positive
     * @param negative
     */
    public void showDialog(Dialog dialog, String title, String message, String positive, String negative, FragmentManager fragmentManager, final IDialogCallBack iDialogCallBack) {

        com.rey.material.app.Dialog.Builder builder = null;
        switch (dialog) {
            case SIMPLE:
                builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                      //  iDialogCallBack.positiveAction();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                     //   iDialogCallBack.negativeAction();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                ((SimpleDialog.Builder) builder).message(message)
                        .title(title)
                        .positiveAction(positive)
                        .negativeAction(negative);
                break;
        }
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(fragmentManager, null);
    }
}
