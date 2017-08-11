package com.one.hotchpotch.ui.activity;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.BaseActivity;
import com.one.hotchpotch.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/module/login").navigation();
                finish();
            }
        }, 1000);
    }

}
