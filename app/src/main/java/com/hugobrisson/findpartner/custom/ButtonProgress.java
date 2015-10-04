package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.hugobrisson.findpartner.R;
import com.rey.material.widget.ProgressView;

/**
 * Created by hugo on 15/07/2015.
 */
public class ButtonProgress extends LinearLayout {

    private ProgressView progressView;

    public ButtonProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_button_progress,this);

        progressView = (ProgressView) findViewById(R.id.progress_wait);
    }

    public void start() {
        progressView.start();

    }

    public void stop() {
        progressView.stop();
    }

}
