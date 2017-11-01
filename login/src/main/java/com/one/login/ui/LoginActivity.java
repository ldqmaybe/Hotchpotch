package com.one.login.ui;

import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.BaseActivity;
import com.one.login.R;
import com.one.login.contract.LoginContract;
import com.one.login.entity.User;
import com.one.login.presenter.LoginPresenter;
import com.one.utils.AppManager;
import com.one.utils.ToastUtils;

@Route(path = "/module/login")
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
            ARouter.getInstance().build("/app/main").navigation();
        } else if (viewId == R.id.tv_regist) {
            ARouter.getInstance().build("/module/register").navigation();
        }

    }

    @Override
    public void onSuccess(User user) {
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    public void onFailure(String error) {
        ToastUtils.showShortToast(error);
    }
}
