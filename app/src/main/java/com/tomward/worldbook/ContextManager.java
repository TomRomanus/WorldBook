package com.tomward.worldbook;

import android.app.Application;
import android.content.Context;

public class ContextManager extends Application {
    private static ContextManager instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
    public static ContextManager getInstance(){
        return instance;
    }
}
