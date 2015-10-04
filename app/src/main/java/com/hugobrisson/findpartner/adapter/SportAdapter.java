package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.manager.SportManager;
import com.hugobrisson.findpartner.manager.DisplayManager;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.model.Sport;
import com.parse.ParseImageView;

import java.util.List;

/**
 * Created by hugo on 05/06/2015.
 */
public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    Context context;

    List<Sport> sports;

    int size;

    public SportAdapter(Context context, List<Sport> sports) {
        this.sports = sports;
        this.context = context;
        size = new DisplayManager().getSize(context);

    }

    @Override
    public SportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item, parent, false);
        return new SportViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final SportViewHolder holder, int position) {
        final Sport sport = sports.get(position);
        holder.pivSport.setMaxWidth(size);
        holder.pivSport.setMaxHeight(size);
        holder.pivSport.setPlaceholder(context.getResources().getDrawable(R.mipmap.ic_default_sport));

        if (sport.getParseFile("image") != null) {
            holder.pivSport.setParseFile(sport.getParseFile("image"));
            holder.pivSport.loadInBackground();
        }

        holder.llSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
                Animation animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                if (sport.isCheck()) {
                    holder.ivCheck.setAnimation(animFadeOut);
                    holder.ivCheck.setVisibility(View.INVISIBLE);
                    sport.setCheck(false);
                } else {
                    holder.ivCheck.setAnimation(animFadeIn);
                    holder.ivCheck.setVisibility(View.VISIBLE);
                    sport.setCheck(true);
                }
                new SportManager().insertOrDeleteFavorites(sport);
            }
        });

        holder.ttNameSport.setText(sport.getName());

    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public static class SportViewHolder extends RecyclerView.ViewHolder {

        protected ParseImageView pivSport;
        protected ImageView ivCheck;
        protected LinearLayout llSport;
        protected TextView ttNameSport;

        public SportViewHolder(View itemView) {
            super(itemView);
            llSport = (LinearLayout) itemView.findViewById(R.id.ll_sport);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check_sport);
            pivSport = (ParseImageView) itemView.findViewById(R.id.iv_sport);
            ttNameSport = (TextView) itemView.findViewById(R.id.tt_name_sport);
        }
    }

}
