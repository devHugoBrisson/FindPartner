package com.hugobrisson.findpartner.view.enrollement;


import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.utils.ISportCallBack;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.custom.ButtonProgress;
import com.hugobrisson.findpartner.manager.SportManager;
import com.hugobrisson.findpartner.adapter.SportAdapter;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.view.home.HomeActivity;
import com.hugobrisson.findpartner.view.home.HomeActivity_;
import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by hugo on 07/06/2015.
 */
@EFragment(R.layout.fragment_step_sport)
public class StepSportFragment extends Fragment {

    @ViewById(R.id.gv_sports)
    RecyclerView gvSports;

    @ViewById(R.id.progress_wait)
    ProgressView progressView;

    @ViewById(R.id.bt_float_step)
    FloatingActionButton button;

    @ViewById(R.id.bt_progress)
    ButtonProgress buttonProgress;

    @Bean
    SportManager sportManager;

    @FragmentArg("objectId")
    String userId;


    @AfterInject()
    void inject() {
        getActivity().setTitle(getString(R.string.sign_third_step));
    }


    @AfterViews()
    void configure() {
        progressView.start();
        sportManager.getAllSportFromServer(new ISportCallBack() {
            @Override
            public void getSports(List<Sport> sports) {
                init(sports);

            }
        });
    }


    @Click(R.id.bt_float_step)
    void click() {
        buttonProgress.start();
        sportManager.getFavoritesSportLocal(new ISportCallBack() {
            @Override
            public void getSports(List<Sport> sports) {
                if (sports.size() != 0) {
                    sportManager.saveServer(sports, new IParseCallBack() {
                        @Override
                        public void onSuccess() {
                            buttonProgress.stop();
                            HomeActivity_.intent(StepSportFragment.this).start();
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure() {
                            buttonProgress.stop();
                        }
                    });
                } else {
                    buttonProgress.stop();
                }
            }
        });
    }

    /**
     * @param sports
     */
    private void init(List<Sport> sports) {
        {
            SportAdapter sportAdapter = new SportAdapter(getActivity(), sports);
            progressView.stop();
            progressView.setVisibility(View.GONE);
            gvSports.setHasFixedSize(true);
            gvSports.setAdapter(sportAdapter);
            button.setVisibility(View.VISIBLE);
        }
    }


}
