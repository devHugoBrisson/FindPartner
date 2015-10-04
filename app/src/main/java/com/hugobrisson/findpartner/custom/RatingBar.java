package com.hugobrisson.findpartner.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hugobrisson.findpartner.R;

/**
 * Created by hugo on 22/07/2015.
 */
public class RatingBar extends LinearLayout {

    private ImageView ivStarOne;
    private ImageView ivStarTwo;
    private ImageView ivStarThree;
    private ImageView ivStarFour;
    private ImageView ivStarFive;

    private Drawable icStarMiddleYellow;
    private Drawable icStarYellow;


    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_rating_bar, this);
        ivStarOne = (ImageView) findViewById(R.id.iv_star_one);
        ivStarTwo = (ImageView) findViewById(R.id.iv_star_two);
        ivStarThree = (ImageView) findViewById(R.id.iv_star_three);
        ivStarFour = (ImageView) findViewById(R.id.iv_star_four);
        ivStarFive = (ImageView) findViewById(R.id.iv_star_five);

        icStarMiddleYellow = context.getResources().getDrawable(R.mipmap.ic_star_middle_yellow);
        icStarYellow = context.getResources().getDrawable(R.mipmap.ic_star_yellow);
    }

    public void setRate(double value) {

        if (value > 0 && value < 1) {
            ivStarOne.setImageDrawable(icStarMiddleYellow);
        } else if (value > 1) {
            ivStarOne.setImageDrawable(icStarYellow);
        }

        if (value > 1 && value < 2) {
            ivStarTwo.setImageDrawable(icStarMiddleYellow);
        } else if (value > 2) {
            ivStarTwo.setImageDrawable(icStarYellow);
        }

        if (value > 2 && value < 3) {
            ivStarThree.setImageDrawable(icStarMiddleYellow);
        } else if (value > 3) {
            ivStarThree.setImageDrawable(icStarYellow);
        }

        if (value > 3 && value < 4) {
            ivStarFour.setImageDrawable(icStarMiddleYellow);
        } else if (value > 4) {
            ivStarFour.setImageDrawable(icStarYellow);
        }

        if (value > 4 && value < 5) {
            ivStarFive.setImageDrawable(icStarMiddleYellow);
        } else if (value == 5) {
            ivStarFive.setImageDrawable(icStarYellow);
        }

    }

}
