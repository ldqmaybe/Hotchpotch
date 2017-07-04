package com.one.hotchpotch;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2017/6/26.
 */

public class MyApp extends Application {

    private static MyApp sInstance;
    public static Context getInstance() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
