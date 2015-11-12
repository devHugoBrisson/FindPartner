package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.TMImageView;
import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.DateConverter;
import com.hugobrisson.findpartner.utils.IParseObjectListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by hugo on 11/11/2015.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context mContext;
    List<Event> mEvents;
    IParseObjectListener mIParseObjectListener;

    public EventAdapter(Context context, List<Event> events, IParseObjectListener iParseObjectListener) {
        this.mEvents = events;
        this.mContext = context;
        this.mIParseObjectListener = iParseObjectListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        final Event event = mEvents.get(position);
        holder.tvName.setText(event.getName());
        holder.tvPartner.setText(String.valueOf(event.getNbAlreadyPartner() + "/" + event.getNbPartner()));
        holder.tvDate.setText(DateConverter.dateToString(event.getDateStart()));
        holder.tvTime.setText(DateConverter.timeToString(event.getDateStart()) + " - " + DateConverter.timeToString(event.getDateEnd()));
        final Sport sport = (Sport) event.getParseObject("SportID");
        sport.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    if (sport.getParseFile("image") != null) {
                        holder.ivSport.setParseFile(sport.getParseFile("image"));
                        holder.ivSport.loadInBackground();
                    }
                }
            }
        });

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIParseObjectListener.onItemCLick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        protected RelativeLayout rlItem;
        protected ParseImageView ivSport;
        protected TextView tvNotification;
        protected TextView tvName;
        protected TextView tvPartner;
        protected TextView tvDate;
        protected TextView tvTime;
        protected RelativeLayout rlState;
        protected TMImageView ivBgState;
        protected TMImageView ivState;

        public EventViewHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ivSport = (ParseImageView) itemView.findViewById(R.id.iv_sport);
            tvNotification = (TextView) itemView.findViewById(R.id.tv_notification);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPartner = (TextView) itemView.findViewById(R.id.tv_nb_partner);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            rlState = (RelativeLayout) itemView.findViewById(R.id.rl_event_state);
            ivBgState = (TMImageView) itemView.findViewById(R.id.iv_background_state);
            ivState = (TMImageView) itemView.findViewById(R.id.iv_icon_state);
        }
    }
}
