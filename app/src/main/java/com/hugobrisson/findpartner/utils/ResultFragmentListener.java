package com.hugobrisson.findpartner.utils;

import com.google.android.gms.location.places.Place;
import com.hugobrisson.findpartner.model.Sport;

/**
 * Created by hugo on 04/11/2015.
 */
public interface ResultFragmentListener {
    void sendEventSport(Sport sport);

    void sendEventPlace(Place place);
}
