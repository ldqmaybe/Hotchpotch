package com.one.login.ui;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.BaseActivity;
import com.one.login.R;
import com.one.utils.PrefUtils;

@Route(path = "/module/login")
public class LoginActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    public void onViewClicked(View v) {
        ARouter.getInstance().build("/app/main").navigation();
        PrefUtils.putString("login", "Login");
    }
}
