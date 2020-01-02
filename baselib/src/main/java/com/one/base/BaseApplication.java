package com.one.base;

import android.app.Application;
import android.content.Context;

/**
 * author: LinDingQiang<br/>
 * created on: 2017/7/10 17:35<br/>
 * description:
 */
public class BaseApplication extends Application {
    private static Application sInstance;

    public static Context getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
