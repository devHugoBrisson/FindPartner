package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugobrisson.findpartner.R;

import com.hugobrisson.findpartner.custom.RatingBar;
import com.hugobrisson.findpartner.model.NavItem;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.utils.DrawerListener;

import java.util.List;

/**
 * Created by hugo on 19/07/2015.
 */
public class NavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context mContext;

    List<NavItem> mNavItems;

    User mUser;

    Item mOlderItem;

    DrawerListener mDrawerListener;


    public NavAdapter(Context context, User user, List<NavItem> navItems, DrawerListener drawerListener) {
        this.mNavItems = navItems;
        this.mContext = context;
        this.mUser = user;
        this.mDrawerListener = drawerListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item;
        if (viewType == TYPE_HEADER) {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false);
            return new Header(item);
        } else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item, parent, false);
            return new Item(item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Header) {
            Header header = (Header) holder;
            header.mName.setText(mUser.getFirstName() + " " + mUser.getLastName());
            header.mMail.setText(mUser.getEmail());
            header.mRate.setText("2.5");
            header.mRatingBar.setRate(2.5);
        } else {
            final NavItem navItem = getItem(position);
            final Item item = (Item) holder;
            item.mTitle.setText(navItem.getTitle());
            item.mIcon.setImageDrawable(navItem.getIcon());
            item.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOlderItem != null) {
                        mOlderItem.mItem.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                    item.mItem.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
                    mDrawerListener.OnItemClick(position);
                    mOlderItem = item;
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private NavItem getItem(int position) {
        return mNavItems.get(position - 1);
    }

    @Override
    public int getItemCount() {
        return mNavItems.size();
    }

    class Item extends RecyclerView.ViewHolder {
        LinearLayout mItem;
        ImageView mIcon;
        TextView mTitle;

        public Item(View itemView) {
            super(itemView);
            mItem = (LinearLayout) itemView.findViewById(R.id.ll_nav_item);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mTitle = (TextView) itemView.findViewById(R.id.tt_title);
        }
    }

    class Header extends RecyclerView.ViewHolder {
        ImageView mProfile;
        RatingBar mRatingBar;
        TextView mRate;
        TextView mName;
        TextView mMail;

        public Header(View itemView) {
            super(itemView);
            mProfile = (ImageView) itemView.findViewById(R.id.iv_profile);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            mRate = (TextView) itemView.findViewById(R.id.tt_rate);
            mName = (TextView) itemView.findViewById(R.id.tt_name);
            mMail = (TextView) itemView.findViewById(R.id.tt_mail);

        }
    }
}
