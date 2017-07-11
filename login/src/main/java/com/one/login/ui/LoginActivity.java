package com.one.login.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.login.R;
import com.one.utils.AppManager;
import com.one.utils.PrefUtils;

import butterknife.ButterKnife;

@Route(path = "/module/login")
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppManager.getInstance().addActivityTOStack(this);
        ButterKnife.bind(this);
    }

    public void onViewClicked(View v) {
        ARouter.getInstance().build( "/app/main").navigation();
     PrefUtils.putString("login","Login");
    }

    @Override
    protected void onPause() {
        super.onPause();
     AppManager.getInstance().finishActivity(this);
    }
}
