package com.hugobrisson.findpartner;

import android.app.Application;

import com.hugobrisson.findpartner.model.Event;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.model.SportUser;
import com.hugobrisson.findpartner.model.User;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import org.androidannotations.annotations.EApplication;

/**
 * Created by hugo on 27/05/2015.
 */
@EApplication
public class FindPartnerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
// Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Sport.class);
        ParseObject.registerSubclass(SportUser.class);
        ParseObject.registerSubclass(Event.class);
        Parse.initialize(this, "rXJMbD8EdTILYOtf8yIOWOuH6QED98JMaQlWH0lp", "F6mrFQQRvx89CXX1rRNLuXnOBmqjDN20c70zFubT");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
