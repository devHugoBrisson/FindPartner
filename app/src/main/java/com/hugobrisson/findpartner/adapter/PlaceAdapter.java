package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.model.PlaceAutocomplete;
import com.hugobrisson.findpartner.utils.IPlaceCallBack;
import com.hugobrisson.findpartner.utils.IPlaceListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hugo on 06/11/2015.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> implements Filterable {

    private static final String TAG = "PlaceArrayAdapter";
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private IPlaceListener mIPlaceListener;
    private LatLngBounds mBounds;
    private List<PlaceAutocomplete> mPlaces;

    public PlaceAdapter(Context context, LatLngBounds bounds, IPlaceListener iPlaceListener) {
        this.mContext = context;
        this.mBounds = bounds;
        this.mPlaces = new ArrayList<>();
        this.mIPlaceListener = iPlaceListener;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            mGoogleApiClient = null;
        } else {
            mGoogleApiClient = googleApiClient;
        }
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new PlaceViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        final PlaceAutocomplete place = mPlaces.get(position);
        holder.tvAddressPlace.setText(place.getDescription());
        holder.llPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIPlaceListener.onItemClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    mPlaces = getPredictions(constraint);
                    if (mPlaces != null) {
                        // Results
                        results.values = mPlaces;
                        results.count = mPlaces.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.

                }
            }
        };
        return filter;

    }


    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {
        if (mGoogleApiClient != null) {
            Log.i(TAG, "Executing autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                            mBounds, null);
            // Wait for predictions, set the timeout.
            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Toast.makeText(mContext, "Error: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting place predictions: " + status
                        .toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();

                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(),
                        prediction.getDescription()));
            }
            // Buffer release
            autocompletePredictions.release();
            return resultList;
        }
        Log.e(TAG, "Google API client is not connected.");
        return null;
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout llPlace;
        protected TextView tvAddressPlace;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            llPlace = (LinearLayout) itemView.findViewById(R.id.ll_place);
            tvAddressPlace = (TextView) itemView.findViewById(R.id.tv_address_place);
        }
    }
}
