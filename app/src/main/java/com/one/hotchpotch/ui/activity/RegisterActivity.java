package com.one.hotchpotch.ui.activity;

import android.widget.Button;
import android.widget.EditText;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.User;
import com.one.hotchpotch.contract.RegistContract;
import com.one.hotchpotch.presenter.RegistPresenter;
import com.one.utils.LogUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegistPresenter> implements RegistContract.View {

    @Bind(R.id.tv_name)
    EditText tvName;
    @Bind(R.id.tv_pwd)
    EditText tvPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    protected RegistPresenter initPresenter() {
        return new RegistPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onSuccess(User user) {
        LogUtils.i(user.toString());
    }

    @Override
    public void onFailure(String error) {
        LogUtils.i(error);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.regist(tvName.getText().toString().trim(),tvPwd.getText().toString().trim());
    }
}
