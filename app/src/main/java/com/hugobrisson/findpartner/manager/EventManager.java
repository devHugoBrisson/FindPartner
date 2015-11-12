package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.widget.Toast;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.EventUser;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.model.SportUser;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.utils.DateConverter;
import com.hugobrisson.findpartner.utils.IEventCallBack;
import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.utils.ISportCallBack;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hugobrisson on 05/11/15.
 */
@EBean
public class EventManager {

    public enum EventTag {
        MINE, FUTURE, WAITING
    }

    @RootContext
    Context mContext;

    /**
     *
     */
    public void getFromServer(EventTag eventTag, final IEventCallBack iEventCallBack) {
        switch (eventTag) {
            case MINE:
                getMyEvents(iEventCallBack);
                break;
            case FUTURE:
                getOtherEvents(false, iEventCallBack);
                break;
            case WAITING:
                getOtherEvents(true, iEventCallBack);
        }
    }

    private void getMyEvents(final IEventCallBack iEventCallBack) {
        ParseQuery<Event> query = new ParseQuery<>(Event.class);
       query.whereEqualTo("OwnerID", ParseUser.getCurrentUser());
       query.whereGreaterThan("EndDate", Calendar.getInstance().getTime());
       query.orderByAscending("StartDate");
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null && list.size() != 0) {
                    iEventCallBack.getEvents(list);
                    insertToLocal(list, EventTag.MINE);
                }
            }
        });
    }

    private void getOtherEvents(final boolean isWaiting, final IEventCallBack iEventCallBack) {
        ParseQuery<EventUser> query = new ParseQuery<>(EventUser.class);
        query.whereEqualTo("userID", ParseUser.getCurrentUser());
        query.whereGreaterThan("isConfirmedOwner", isWaiting);
        query.findInBackground(new FindCallback<EventUser>() {
            @Override
            public void done(List<EventUser> list, ParseException e) {
                if (e == null && list.size() != 0) {
                    if (isWaiting) {
                        getEventWithId(list, EventTag.WAITING, iEventCallBack);
                    } else {
                        getEventWithId(list, EventTag.FUTURE, iEventCallBack);
                    }
                }
            }
        });
    }

    private void getEventWithId(final List<EventUser> eventUsers, final EventTag eventTag, final IEventCallBack iEventCallBack) {
        ParseQuery<Event> query = new ParseQuery<>(Event.class);
        query.whereGreaterThan("EndDate", Calendar.getInstance());
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                List<Event> events = new ArrayList<>();
                for (EventUser eventUser : eventUsers) {
                    for (Event event : list) {
                        if (eventUser.getParseObject("activityID") == event) {
                            insertToLocal(event, eventTag);
                            events.add(event);
                        }
                    }
                }
                iEventCallBack.getEvents(events);
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

    /**
     * Insert in local data.
     *
     * @param event    one sport
     * @param eventTag key for database
     */
    public void insertToLocal(Event event, EventTag eventTag) {
        event.pinInBackground(eventTag.toString(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(mContext, mContext.getString(R.string.error_insert_data_in_base), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Insert in local data.
     *
     * @param events   one sport
     * @param eventTag key for database
     */
    public void insertToLocal(List<Event> events, EventTag eventTag) {
        for (Event event : events) {
            insertToLocal(event, eventTag);
        }
    }


    /**
     * get event local
     *
     * @param iEventCallBack
     */
    public void getEventLocal(EventTag eventTag, final IEventCallBack iEventCallBack) {
        ParseQuery<Event> query = new ParseQuery<>(Event.class);
        query.fromLocalDatastore();
        query.fromPin(eventTag.toString());
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    iEventCallBack.getEvents(list);
                }
            }
        });
    }

    /**
     * Delete to local data.
     *
     * @param event    one sport
     * @param eventTag key for database
     */
    public void deleteToLocal(Event event, EventTag eventTag) {
        event.unpinInBackground(eventTag.toString(), new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(mContext, mContext.getString(R.string.error_delete_data_in_base), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
