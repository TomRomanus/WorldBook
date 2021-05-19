package com.tomward.worldbook;

import android.app.Application;
import android.content.Context;

public class ContextManager extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }
    public static Context getAppContext(){
        return mContext;
    }
}
