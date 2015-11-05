package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;

/**
 * Created by hugo on 04/11/2015.
 */
public class EventItem extends LinearLayout {

    private ImageView mIcon;
    private TextView mTextView;

    public EventItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_event_item, this);
        mIcon = (ImageView) findViewById(R.id.icon_item);
        mTextView = (TextView) findViewById(R.id.desc_item);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EventItem);
            Drawable icon = a.getDrawable(R.styleable.EventItem_eventIcon);
            String text = a.getString(R.styleable.EventItem_eventText);

            if (icon != null) {
                mIcon.setImageDrawable(icon);
            }
            mTextView.setText(text);
        }
    }

    public String getText() {
        return mTextView.getText().toString();
    }

    public void setText(String text) {
        mTextView.setText(text);
    }
}
