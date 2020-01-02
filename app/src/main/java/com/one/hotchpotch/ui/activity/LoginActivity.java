package com.one.hotchpotch.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.User;
import com.one.hotchpotch.contract.LoginContract;
import com.one.hotchpotch.navigation.Navigator;
import com.one.hotchpotch.presenter.LoginPresenter;
import com.one.utils.AppManager;
import com.one.utils.ToastUtils;

/**
 * @description 登录
 * @author LinDingQiang
 * @time 2019/8/19  15:17
 * @email dingqiang.l@verifone.cn
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {
    private TextView tvFind;
    private TextView tvPwd;
    private TextInputEditText etName, etPassword;
    private Button btnLogin;

    @Override
    protected LoginPresenter initPresenter() {
        return mPresenter = new LoginPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tvFind = (TextView) findViewById(R.id.tv_find_pwd);
        tvPwd = (TextView) findViewById(R.id.tv_regist);
        etName = findView(R.id.reg_user_name);
        etPassword = findView(R.id.reg_user_pwd);
        btnLogin = findView(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        tvPwd.setOnClickListener(this);
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
        int viewId = v.getId();
        if (viewId == R.id.btn_login) {
            mPresenter.login(etName.getText().toString().trim(), etPassword.getText().toString().trim());
        } else if (viewId == R.id.tv_find_pwd) {
            Navigator.getInstance().navigate2Main(LoginActivity.this);
            finish();
        } else if (viewId == R.id.tv_regist) {
            Navigator.getInstance().navigate2Register(LoginActivity.this);
        }

    }

    @Override
    public void onSuccess(User user) {
        Navigator.getInstance().navigate2Main(LoginActivity.this);
    }

    @Override
    public void onFailure(String error) {
        ToastUtils.showShortToast(error);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent( context, LoginActivity.class );
    }
}
