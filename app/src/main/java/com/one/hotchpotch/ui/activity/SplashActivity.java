package com.one.hotchpotch.ui.activity;

import android.os.Handler;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.navigation.Navigator;

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
        new Handler().postDelayed(() -> {
            Navigator.getInstance().navigate2Main(SplashActivity.this);
            finish();
        }, 500);
    }

}
