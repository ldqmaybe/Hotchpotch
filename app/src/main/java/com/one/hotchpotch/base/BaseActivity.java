package com.one.hotchpotch.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.one.hotchpotch.utils.AppManager;
import com.one.hotchpotch.utils.HideUtil;
import com.one.hotchpotch.utils.ToolbarUtils;

import butterknife.ButterKnife;

/**
 * Fragment的基类
 * @param <T>
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public T mPresenter;
    public Context mContext;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        AppManager.getInstance().addActivityTOStack(this);
        mContext = this;
        ButterKnife.bind(this);
        HideUtil.init(this);
        ToolbarUtils.init(this);
        this.mPresenter = initPresenter();
        this.initBindingView();

        this.initView();
        //强制在基类Intent判空
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
    }
    /**
     * onDestroy中销毁presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        ButterKnife.unbind(this);
        AppManager.getInstance().finishActivity(this);
    }

    /**
     * presenter与view绑定
     */
    private void initBindingView() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    /**
     * 显示加载
     *
     * @param text 加载框中的内容
     */
    public void showDialog(String text) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(text);
        dialog.show();
    }

    /**
     * 隐藏加载
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            dialog.dismiss();
        }
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected T initPresenter() {
        return mPresenter;
    }

    /**
     * 绑定布局
     */
    protected abstract int setLayoutId();

    /**
     * 初始化页面
     */
    protected abstract void initView();

    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 处理Intent，防止开发人员没做Intent判空
     */
    protected void handleIntent(Intent intent) {
    }

}
