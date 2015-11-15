package com.hugobrisson.findpartner.view.home.event;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.ViewPagerAdapter;
import com.hugobrisson.findpartner.utils.Tools;
import com.hugobrisson.findpartner.view.FragmentController;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_tab_event)
public class EventFragment extends FragmentController {

    @ViewById(R.id.tab_event)
    TabLayout mTabEvent;

    @ViewById(R.id.vp_event)
    ViewPager mVpEvent;

    @AfterInject()
    void create() {

    }

    @AfterViews()
    void configure() {
        setupViewPager();
        mTabEvent.setupWithViewPager(mVpEvent);
    }

    @Click(R.id.bt_float_step)
    void click() {
        if (Tools.isNetworkAvailable(getActivity())) {
            mActivityListener.changeFragment(new CreateEventFragment_());
        }
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(getFragment(1), "CREER");
        adapter.addFragment(getFragment(2), "A VENIR");
        adapter.addFragment(getFragment(3), "EN ATTENTE");

        mVpEvent.setAdapter(adapter);
    }

    private Fragment getFragment(int value) {
        Fragment fragment = new EventListFragment_();
        Bundle bundle = new Bundle();
        bundle.putInt("event", value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
