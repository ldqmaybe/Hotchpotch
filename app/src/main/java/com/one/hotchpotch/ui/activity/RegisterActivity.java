package com.one.hotchpotch.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.User;
import com.one.hotchpotch.contract.RegisterContract;
import com.one.hotchpotch.navigation.Navigator;
import com.one.hotchpotch.presenter.RegisterPresenter;
import com.one.utils.ToastUtils;
import com.one.utils.ToolbarUtils;

/**
 * @description 注册
 * @author LinDingQiang
 * @time 2019/8/19  15:17
 * @email dingqiang.l@verifone.cn
 */
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
        Navigator.getInstance().navigate2Main(RegisterActivity.this);
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

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}
