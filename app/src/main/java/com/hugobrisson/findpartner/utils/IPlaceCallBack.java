package com.hugobrisson.findpartner.utils;

import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.List;

/**
 * Created by hugo on 07/11/2015.
 */
public interface IPlaceCallBack {
    void onSuccess(List<AutocompletePrediction> autocompletePredictionList);

    void onFailed();
}
