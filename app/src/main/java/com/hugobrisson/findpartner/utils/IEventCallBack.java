package com.hugobrisson.findpartner.utils;

import com.hugobrisson.findpartner.model.Event;
import java.util.List;

/**
 * Created by hugobrisson on 05/11/15.
 */
public interface IEventCallBack {
    void getEvents(List<Event> events);
}
