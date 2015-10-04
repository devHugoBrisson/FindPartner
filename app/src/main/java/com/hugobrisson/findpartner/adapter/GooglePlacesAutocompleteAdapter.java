package com.hugobrisson.findpartner.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.manager.MapsManager;

import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by hugo on 02/08/2015.
 */
public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
    private List<String> resultList;


    public GooglePlacesAutocompleteAdapter(Context context) {
        super(context, R.layout.address_item);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }


    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = MapsManager.autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

}

