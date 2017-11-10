package com.one.login.ui;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.one.base.BaseActivity;
import com.one.login.R;
import com.one.login.contract.RegisterContract;
import com.one.login.entity.User;
import com.one.login.presenter.RegisterPresenter;
import com.one.utils.ToastUtils;
import com.one.utils.ToolbarUtils;

@Route(path = "/module/register")
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, View.OnClickListener {
    private TextInputEditText etName, etPws;

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ToolbarUtils.setTitle("注册");
        etName = findView(R.id.reg_user_name);
        etPws = findView(R.id.reg_user_pwd);
        Button btnReg = findView(R.id.btn_user_reg);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onSuccess(User user) {
        ARouter.getInstance().build("/app/main").navigation();
        finish();
    }

    @Override
    public void onFailure(String error) {
        ToastUtils.showShortToast(error);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String pwd = etPws.getText().toString().trim();
        mPresenter.register(name, pwd);
    }
}
