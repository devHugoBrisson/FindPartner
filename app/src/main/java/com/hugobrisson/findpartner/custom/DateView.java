package com.hugobrisson.findpartner.custom;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.DatePickerFragment;


/**
 * Created by hugobrisson on 07/10/15.
 */
public class DateView extends LinearLayout {

    private TextView tvDate;

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_date_view, this);
        tvDate = (TextView) findViewById(R.id.tv_date);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateView);
            String dateText = a.getString(R.styleable.DateView_customText);
            tvDate.setText(dateText);
            a.recycle();
        }
    }

    public void setDate(String dateText) {
        tvDate.setText(dateText);
    }

    public String getDate() {
        return tvDate.getText().toString();
    }

    public void showDatePicker(FragmentManager fragmentManager, DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(fragmentManager, null);
        datePickerFragment.setOnDateSetListener(onDateSetListener);
    }
}
