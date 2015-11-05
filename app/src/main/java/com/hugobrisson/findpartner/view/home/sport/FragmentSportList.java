package com.hugobrisson.findpartner.view.home.sport;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.SportAdapter;
import com.hugobrisson.findpartner.adapter.SportAdapterGrid;
import com.hugobrisson.findpartner.manager.SportManager;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.ActivityListener;
import com.hugobrisson.findpartner.utils.IParseObjectListener;
import com.hugobrisson.findpartner.utils.ISportCallBack;
import com.hugobrisson.findpartner.utils.ResultFragmentListener;
import com.hugobrisson.findpartner.view.FragmentController;
import com.parse.ParseObject;
import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by hugo on 04/11/2015.
 */
@EFragment(R.layout.fragment_sport_list)
public class FragmentSportList extends FragmentController {


    @ViewById(R.id.et_search)
    EditText etSearch;

    @ViewById(R.id.list_sport)
    RecyclerView recyclerView;

    @ViewById(R.id.progress_wait)
    ProgressView progressView;

    @Bean
    SportManager sportManager;

    private SportAdapter sportAdapter;

    private ResultFragmentListener resultFragmentListener;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            sportAdapter.sortList(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private IParseObjectListener iParseObjectListener = new IParseObjectListener() {
        @Override
        public void onItemCLick(ParseObject parseObject) {
            resultFragmentListener.sendEventSport((Sport) parseObject);
            getFragmentManager().popBackStack();
        }
    };

    @AfterViews()
    void configure() {
        progressView.start();
        etSearch.addTextChangedListener(watcher);
        sportManager.getAllSportFromServer(new ISportCallBack() {
            @Override
            public void getSports(List<Sport> sports) {
                init(sports);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            resultFragmentListener = (ResultFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityListener");
        }
    }

    /**
     * @param sports
     */
    private void init(List<Sport> sports) {
        sportAdapter = new SportAdapter(getActivity(), sports, iParseObjectListener);
        progressView.stop();
        progressView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(sportAdapter);
    }

}
