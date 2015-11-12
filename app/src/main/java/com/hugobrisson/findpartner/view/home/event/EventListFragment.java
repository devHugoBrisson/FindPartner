package com.hugobrisson.findpartner.view.home.event;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.EventAdapter;
import com.hugobrisson.findpartner.adapter.SportAdapter;
import com.hugobrisson.findpartner.manager.EventManager;
import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.utils.IEventCallBack;
import com.hugobrisson.findpartner.utils.IParseObjectListener;
import com.hugobrisson.findpartner.utils.Tools;
import com.hugobrisson.findpartner.view.FragmentController;
import com.parse.ParseObject;
import com.rey.material.widget.ProgressView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 11/11/2015.
 */
@EFragment(R.layout.fragment_list_global)
public class EventListFragment extends FragmentController {

    @ViewById(R.id.rv_global)
    RecyclerView mRecyclerView;

    @ViewById(R.id.progress_wait)
    ProgressView mProgressView;

    @Bean
    EventManager eventManager;

    @FragmentArg("event")
    int typeEvent;

    private EventAdapter mEventAdapter;

    private List<Event> mEvents = new ArrayList<>();

    private EventManager.EventTag mEventTag;

    private IParseObjectListener mIParseObjectListener = new IParseObjectListener() {
        @Override
        public void onItemCLick(ParseObject parseObject) {
            Event event = (Event) parseObject;
            switch (mEventTag) {
                case MINE:
                    break;
                case FUTURE:
                    break;
                case WAITING:
                    break;
            }
        }
    };

    @AfterInject()
    void create() {

    }


    @AfterViews()
    void configure() {
        mProgressView.start();
        EventManager.EventTag eventTag = null;
        switch (typeEvent) {
            case 1:
                eventTag = EventManager.EventTag.MINE;
                break;
            case 2:
                eventTag = EventManager.EventTag.FUTURE;
                break;
            case 3:
                eventTag = EventManager.EventTag.WAITING;
                break;

        }

        eventManager.getEventLocal(eventTag, new IEventCallBack() {
            @Override
            public void getEvents(List<Event> events) {
                if (events.size() > 0) {
                    init(events);
                }
            }
        });

        if (Tools.isNetworkAvailable(getActivity())) {
            eventManager.getFromServer(eventTag, new IEventCallBack() {
                @Override
                public void getEvents(List<Event> events) {
                    if (events.size() > 0) {
                        init(events);
                    } else if (mEvents.size() == 0) {
                        mProgressView.stop();
                        // TODO SHOW ERROR IN RECYCLERVIEW
                    }
                }
            });
        }
    }

    /**
     * @param events
     */
    private void init(List<Event> events) {
        mEvents.clear();
        mEvents = events;
        mEventAdapter = new EventAdapter(getActivity(), events, mIParseObjectListener);
        mProgressView.stop();
        mProgressView.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mEventAdapter);
    }

}
