package com.one.login.ui;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.BaseActivity;
import com.one.login.R;
import com.one.utils.AppManager;
import com.one.utils.PrefUtils;

@Route(path = "/module/login")
public class LoginActivity extends BaseActivity implements View.OnClickListener {
private TextView tvFind;
private TextView tvPwd;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tvFind= (TextView) findViewById(R.id.tv_find_pwd);
        tvPwd= (TextView) findViewById(R.id.tv_regist);
        tvFind.setOnClickListener(this);
//        tvPwd.setOnClickListener(this);
    }

    public void loginBtnOnClick(View v) {
        ARouter.getInstance().build("/app/main").navigation();
        PrefUtils.putString("login", "Login");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回按钮。2秒内连续点击则退出
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            AppManager.getInstance().onKeyDown();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        ARouter.getInstance().build("/app/domain").navigation();
    }
}
