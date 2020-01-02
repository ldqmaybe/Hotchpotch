package com.one.hotchpotch.navigation;

import android.content.Context;
import android.content.Intent;

import com.one.hotchpotch.ui.activity.LoginActivity;
import com.one.hotchpotch.ui.activity.MainActivity;
import com.one.hotchpotch.ui.activity.RegisterActivity;
import com.one.utils.LogUtils;

/**
 * @author LinDingQiang
 * @time 2019/8/19 15:01
 * @email dingqiang.l@verifone.cn
 */
public class Navigator {
    private static final String TAG = Navigator.class.getSimpleName();

    private Navigator() {
    }

    private static class Holder {
        static final Navigator navigator = new Navigator();
    }

    public static Navigator getInstance() {
        return Holder.navigator;
    }
    public void navigate2Login(Context context) {
        LogUtils.i( TAG, "navigate2Login: executed." );
        if (context != null) {
            Intent intentToLogin = LoginActivity.getCallingIntent( context );
            context.startActivity( intentToLogin );
        }
    }
    public void navigate2Register(Context context) {
        LogUtils.i( TAG, "navigate2Login: executed." );
        if (context != null) {
            Intent intentToLogin = RegisterActivity.getCallingIntent( context );
            context.startActivity( intentToLogin );
        }
    }
    public void navigate2Main(Context context) {
        LogUtils.i( TAG, "navigate2Login: executed." );
        if (context != null) {
            Intent intentToMain = MainActivity.getCallingIntent( context );
            context.startActivity( intentToMain );
        }
    }

}
