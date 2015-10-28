package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.hugobrisson.findpartner.R;

/**
 * Created by hugobrisson on 07/10/15.
 */
public class AutoCompletePlacesView extends LinearLayout {

    public AutoCompletePlacesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_date_view, this);
    }
}
