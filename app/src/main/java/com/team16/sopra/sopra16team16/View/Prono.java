package com.team16.sopra.sopra16team16.View;

import android.app.Application;
import android.content.Context;



public class Prono extends Application{

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
