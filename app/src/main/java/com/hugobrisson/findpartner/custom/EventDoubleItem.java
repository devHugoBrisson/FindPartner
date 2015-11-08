package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.DialogType;
import com.hugobrisson.findpartner.utils.IDoubleEventListener;

/**
 * Created by hugo on 07/11/2015.
 */
public class EventDoubleItem extends LinearLayout {

    private TextView mFirstItem;
    private TextView mSecondItem;
    private IDoubleEventListener mIDoubleEventListener;
    private DialogType mDialogType;
    private Context mContext;
    private String mTitleFirstItem;
    private String mTitleSecondItem;

    public EventDoubleItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_event_double_item, this);
        ImageView mIcon = (ImageView) findViewById(R.id.icon_item);
        mFirstItem = (TextView) findViewById(R.id.first_item);
        mSecondItem = (TextView) findViewById(R.id.second_item);
        TextView mSeparatorItem = (TextView) findViewById(R.id.separator_text);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EventDoubleItem);
            Drawable icon = a.getDrawable(R.styleable.EventDoubleItem_doubleEventIcon);
            String firstText = a.getString(R.styleable.EventDoubleItem_eventFirstText);
            String secondText = a.getString(R.styleable.EventDoubleItem_eventSecondText);
            String separatorText = a.getString(R.styleable.EventDoubleItem_eventSeparatorText);

            mIcon.setImageDrawable(icon);
            mFirstItem.setText(firstText);
            mSecondItem.setText(secondText);
            mSeparatorItem.setText(separatorText);
            mTitleFirstItem = firstText;
            mTitleSecondItem = secondText;

        }

        mFirstItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDoubleEventListener.OnItemClick(mDialogType, true);
            }
        });

        mSecondItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDoubleEventListener.OnItemClick(mDialogType, false);
            }
        });
    }


    public void setListenerAndDialogType(IDoubleEventListener iDoubleEventListener, DialogType dialogType) {
        mIDoubleEventListener = iDoubleEventListener;
        mDialogType = dialogType;
    }

    public void setText(String text, boolean isFirstEvent) {
        if (isFirstEvent) {
            mFirstItem.setText(text);
            mFirstItem.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            mSecondItem.setText(text);
            mSecondItem.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    public String getText(boolean isFirstEvent) {
        if (isFirstEvent) {
            return mFirstItem.getText().toString();
        } else {
            return mSecondItem.getText().toString();
        }
    }

    public String getTitleFirstItem() {
        return mTitleFirstItem;
    }

    public String getmTitleSecondItem() {
        return mTitleSecondItem;
    }

}

