package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.IParseObjectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 04/11/2015.
 */
public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    Context context;

    IParseObjectListener iParseObjectListener;

    List<Sport> sports;

    List<Sport> sortSports = new ArrayList<>();

    List<Sport> oldSports;


    public SportAdapter(Context context, List<Sport> sports, IParseObjectListener iParseObjectListener) {
        this.sports = sports;
        this.oldSports = sports;
        this.context = context;
        this.iParseObjectListener = iParseObjectListener;
    }

    public void sortList(String charSequence) {
        sortSports.clear();
        sports = oldSports;
        if (!"".equals(charSequence)) {
            for (Sport sport : sports) {
                if (sport.getName().toLowerCase().startsWith(charSequence.toLowerCase())) {
                    sortSports.add(sport);
                }
            }
            if (sortSports != null && sortSports.size() > 0) {
                sports = sortSports;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public SportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item, parent, false);
        return new SportViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SportViewHolder holder, int position) {
        final Sport sport = sports.get(position);
        holder.tvNameSport.setText(sport.getName());
        holder.llSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iParseObjectListener.onItemCLick(sport);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public static class SportViewHolder extends RecyclerView.ViewHolder {

        protected LinearLayout llSport;
        protected TextView tvNameSport;

        public SportViewHolder(View itemView) {
            super(itemView);
            llSport = (LinearLayout) itemView.findViewById(R.id.ll_sport);
            tvNameSport = (TextView) itemView.findViewById(R.id.tv_name_sport);
        }
    }
}
