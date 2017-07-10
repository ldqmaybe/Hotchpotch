package com.one.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.utils.PrefUtils;

import butterknife.ButterKnife;

@Route(path = "/module/login")
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void onViewClicked(View v) {
        ARouter.getInstance().build( "/app/main").navigation();
        PrefUtils.putString("login","Login");
    }
}
