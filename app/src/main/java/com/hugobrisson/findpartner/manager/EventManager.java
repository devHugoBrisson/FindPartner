package com.hugobrisson.findpartner.manager;

import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.model.SportUser;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.utils.IEventCallBack;
import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.utils.ISportCallBack;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by hugobrisson on 05/11/15.
 */
@EBean
public class EventManager {

    /**
     *
     */
    public void getAllFromServer(final IEventCallBack iEventCallBack) {
        ParseQuery<Event> query = new ParseQuery<>(Event.class);
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null && list.size() != 0) {
                    iEventCallBack.getEvents(list);
                }
            }
        });
    }

    /**
     * @param event
     * @param iParseCallBack
     */
    public void saveServer(final Event event, final IParseCallBack iParseCallBack) {
        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    iParseCallBack.onSuccess();
                } else {
                    iParseCallBack.onFailure();
                }
            }
        });
    }
}
