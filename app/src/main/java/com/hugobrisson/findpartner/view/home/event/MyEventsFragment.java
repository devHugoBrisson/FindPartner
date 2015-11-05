package com.hugobrisson.findpartner.view.home.event;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.utils.Tools;
import com.hugobrisson.findpartner.view.FragmentController;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by hugo on 04/11/2015.
 */
@EFragment(R.layout.fragment_events)
public class MyEventsFragment extends FragmentController{


    @Click(R.id.bt_float_step)
    void click(){
        if(Tools.isNetworkAvailable(getActivity())){
            mActivityListener.changeFragment(new CreateEventFragment_());
        }
    }
}
