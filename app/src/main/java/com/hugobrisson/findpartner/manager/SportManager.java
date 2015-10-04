package com.hugobrisson.findpartner.manager;

import android.content.Context;
import android.widget.Toast;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.utils.ISportCallBack;
import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.model.SportUser;
import com.hugobrisson.findpartner.model.User;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


import java.util.List;

/**
 * Created by hugo on 16/06/2015.
 */
@EBean
public class SportManager {

    public enum SportTag {
        FAVORITES
    }

    @RootContext
    Context context;


    /**
     *
     */
    public void getAllSportFromServer(final ISportCallBack iSportCallBack) {
        ParseQuery<Sport> query = new ParseQuery<>(Sport.class);
        query.findInBackground(new FindCallback<Sport>() {
            @Override
            public void done(List<Sport> list, ParseException e) {
                if (e == null && list.size() != 0) {
                    iSportCallBack.getSports(list);
                }
            }
        });
    }

    /**
     * Insert in local data.
     *
     * @param sport    one sport
     * @param sportTag key for database
     */
    public void insertToLocal(Sport sport, SportTag sportTag) {
        sport.pinInBackground(sportTag.toString(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(context, context.getString(R.string.error_insert_data_in_base), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Delete to local data.
     *
     * @param sport    one sport
     * @param sportTag key for database
     */
    public void deleteToLocal(Sport sport, SportTag sportTag) {
        sport.unpinInBackground(sportTag.toString(), new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(context, context.getString(R.string.error_delete_data_in_base), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * insert or delete favorites sport if this sport is ic_check or not.
     *
     * @param sport a sport
     */
    public void insertOrDeleteFavorites(Sport sport) {
        if (sport.isCheck()) {
            insertToLocal(sport, SportTag.FAVORITES);
        } else {
            deleteToLocal(sport, SportTag.FAVORITES);
        }
    }

    /**
     *
     * @param sports
     * @param iParseCallBack
     */
    public void saveServer(final List<Sport> sports, final IParseCallBack iParseCallBack) {
        final int[] iSuccess = {0};
        final int[] iFail = {0};
        for (Sport sport : sports) {
            SportUser sportUser = new SportUser();
            sportUser.setUserId(User.getCurrentUser());
            sportUser.setSportId(sport);
            sportUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    iSuccess[0]++;
                    if (e == null) {
                        if (sports.size() == iSuccess[0] && iFail[0] == 0) {
                            iParseCallBack.onSuccess();
                        } else {
                            iParseCallBack.onFailure();
                        }
                    } else {
                        iFail[0]++;
                    }
                }
            });
        }
    }

    /**
     * @param iSportCallBack
     */
    public void getFavoritesSportLocal(final ISportCallBack iSportCallBack) {
        ParseQuery<Sport> query = new ParseQuery<>(Sport.class);
        query.fromLocalDatastore();
        query.fromPin(SportManager.SportTag.FAVORITES.toString());
        query.findInBackground(new FindCallback<Sport>() {
            @Override
            public void done(List<Sport> list, ParseException e) {
                if (e == null) {
                    iSportCallBack.getSports(list);
                }
            }
        });
    }
}
