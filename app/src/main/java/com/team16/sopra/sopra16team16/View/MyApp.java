package com.team16.sopra.sopra16team16.View;

import android.app.Application;
import android.content.Context;

/**
 * Created by moo on 11/20/16.
 */

// TODO / change gender Strings to images
// TODO / add id for deleting
// TODO / comment code
// TODO / delete the ugly EDIT TEXT entries from database

public class MyApp extends Application{

    private static Context mContext;


    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  instance = this;
        mContext = getApplicationContext();
    }
}
