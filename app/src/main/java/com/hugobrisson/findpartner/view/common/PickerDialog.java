package com.hugobrisson.findpartner.view.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.DialogType;
import com.hugobrisson.findpartner.utils.IDialogCallBack;

import java.util.Calendar;

/**
 * Created by hugo on 05/11/2015.
 */
public class PickerDialog extends Dialog {

    private Context mContext;
    private String mTitle;
    private IDialogCallBack mIDialogCallBack;
    private DialogType mDialogType;

    public PickerDialog(Context context, DialogType dialogType, String title, IDialogCallBack iDialogCallBack) {
        super(context);
        mTitle = title;
        mIDialogCallBack = iDialogCallBack;
        mDialogType = dialogType;
        initDialog();
    }

    private void initDialog() {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switch (mDialogType) {
            case CALENDAR:
                dateDialog();
                break;
            case TIME:
                timeDialog();
                break;
            case NUMBER:
                numberDialog();
                break;
        }

    }

    private void dateDialog() {
        this.setContentView(R.layout.fragment_date_picker);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        final DatePicker picker = (DatePicker) findViewById(R.id.datePicker);
        AppCompatButton appCompatButton = (AppCompatButton) findViewById(R.id.bt_done);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDialogCallBack.onResultDatePicker(picker.getDayOfMonth(), picker.getMonth(), picker.getYear());
                dismiss();
            }
        });
        textView.setText(mTitle);
    }

    private void timeDialog() {
        this.setContentView(R.layout.fragment_time_picker);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        final TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
        picker.setIs24HourView(true);
        AppCompatButton appCompatButton = (AppCompatButton) findViewById(R.id.bt_done);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDialogCallBack.onResultTimePicker(picker.getCurrentHour(),picker.getCurrentMinute());
                dismiss();
            }
        });
        textView.setText(mTitle);
    }

    private void numberDialog() {
        this.setContentView(R.layout.fragment_number_picker);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        AppCompatButton appCompatButton = (AppCompatButton) findViewById(R.id.bt_done);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDialogCallBack.onResultNumberPicker(numberPicker.getValue());
                dismiss();
            }
        });
        textView.setText(mTitle);
        numberPicker.setMaxValue(20); // max value 100
        numberPicker.setMinValue(0);   // min value 0
        numberPicker.setWrapSelectorWheel(false);
    }
}
