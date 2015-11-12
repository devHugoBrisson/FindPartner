package com.hugobrisson.findpartner.view.home.location;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.PlaceAdapter;
import com.hugobrisson.findpartner.model.PlaceAutocomplete;
import com.hugobrisson.findpartner.utils.IPlaceListener;
import com.hugobrisson.findpartner.utils.ResultFragmentListener;
import com.hugobrisson.findpartner.view.FragmentController;
import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by hugo on 04/11/2015.
 */
@EFragment(R.layout.fragment_address_list)
public class AddressListFragment extends FragmentController {

    @ViewById(R.id.et_search_place)
    EditText mSearchPlace;

    @ViewById(R.id.list_place)
    RecyclerView mRecyclerView;

    @ViewById(R.id.progress_wait)
    ProgressView mProgressView;

    private GoogleApiClient mGoogleApiClient;

    private PlaceAdapter mPlaceAdapter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private ResultFragmentListener mResultFragmentListener;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 1) {
                mPlaceAdapter.getFilter().filter(charSequence);
            }
        }


        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private IPlaceListener iPlaceListener = new IPlaceListener() {
        @Override
        public void onItemClick(PlaceAutocomplete placeAutocomplete) {
            hideSoftKeyboard();
            mProgressView.start();
            final String placeId = String.valueOf(placeAutocomplete.getPlaceId());
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    @AfterInject()
    void create() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        mPlaceAdapter.setGoogleApiClient(mGoogleApiClient);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
    }

    @AfterViews()
    void configure() {
        mSearchPlace.addTextChangedListener(watcher);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlaceAdapter = new PlaceAdapter(getActivity(), BOUNDS_MOUNTAIN_VIEW, iPlaceListener);
        mRecyclerView.setAdapter(mPlaceAdapter);
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            mProgressView.stop();
            if (!places.getStatus().isSuccess()) {
                //TODO ERROR
                return;
            }
            // Selecting the first object buffer.
            Place place = places.get(0);
            mResultFragmentListener.sendEventPlace(place);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mResultFragmentListener = (ResultFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}
