package com.one.hotchpotch;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

/**
 * Created by admin on 2017/6/26.
 */

public class MyApp extends Application {

    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
