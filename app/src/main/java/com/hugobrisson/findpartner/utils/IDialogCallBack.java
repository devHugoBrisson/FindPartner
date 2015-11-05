package com.hugobrisson.findpartner.utils;

/**
 * Created by hugo on 16/07/2015.
 */
public interface IDialogCallBack {

    void onResultDatePicker(int day, int month, int year);

    void onResultTimePicker(int hour, int minute);

    void onResultNumberPicker(int number);
}
