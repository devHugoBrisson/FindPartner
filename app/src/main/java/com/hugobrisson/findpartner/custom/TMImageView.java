package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hugobrisson.findpartner.R;

/**
 * Created by hugo on 11/11/2015.
 */
public class TMImageView extends ImageView {
    public TMImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TMImageView);
            int color = a.getColor(R.styleable.TMImageView_colorIcon, Color.WHITE);
            ColorFilter cf = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
            getDrawable().setColorFilter(cf);
            a.recycle();
        }
    }

    public void setIconColor(int color) {
        setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
