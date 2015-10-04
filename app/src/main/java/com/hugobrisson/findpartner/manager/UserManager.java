package com.hugobrisson.findpartner.manager;

import com.hugobrisson.findpartner.utils.IParseCallBack;
import com.hugobrisson.findpartner.model.User;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.androidannotations.annotations.EBean;

/**
 * Created by hugo on 15/07/2015.
 */
@EBean
public class UserManager {

    public enum UserTag {
        NEWUSER;
    }

    /**
     * First save for Parse.
     * @param user
     * @param iParseCallBack
     */
    public void signUp(final User user, final IParseCallBack iParseCallBack) {
        user.signUpInBackground(new SignUpCallback() {
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
     * @param user
     * @param iParseCallBack
     */
    public void saveLocal(final User user, final IParseCallBack iParseCallBack) {
        user.unpinInBackground(UserTag.NEWUSER.toString(), new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    user.pinInBackground(UserTag.NEWUSER.toString(), new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                iParseCallBack.onSuccess();
                            } else {
                                iParseCallBack.onFailure();
                            }
                        }
                    });
                } else {
                    iParseCallBack.onFailure();
                }
            }
        });
    }

    /**
     * @param user
     * @param iParseCallBack
     */
    public void saveServer(final User user, final IParseCallBack iParseCallBack) {
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    user.unpinInBackground(UserTag.NEWUSER.toString(), new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                iParseCallBack.onSuccess();
                            } else {
                                iParseCallBack.onFailure();
                            }
                        }
                    });
                } else {
                    iParseCallBack.onFailure();
                }
            }
        });
    }

    /**
     * @return
     */
    public User getFirstLocal() {
        ParseQuery<User> query = new ParseQuery<>(User.class);
        query.fromLocalDatastore();
        query.fromPin(UserTag.NEWUSER.toString());
        try {
            return query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
