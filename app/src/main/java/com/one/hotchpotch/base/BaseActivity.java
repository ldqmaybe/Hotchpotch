package com.one.hotchpotch.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.one.hotchpotch.R;
import com.one.hotchpotch.utils.AppManager;
import com.one.hotchpotch.utils.HideUtil;

import butterknife.ButterKnife;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private TextView toolbar_title;
    private TextView toolbar_right;
    private TextView toolbar_left;
    private Toolbar toolbar;


    public T mPresenter;
    public Context mContext;
    private ProgressDialog dialog;

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected T initPresenter() {
        return mPresenter;
    }
//    public abstract T initPresenter();

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
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 处理Intent，防止开发人员没做Intent判空
     */
    protected void handleIntent(Intent intent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        AppManager.getInstance().addActivityTOStack(this);
//        setStatusBar();
        mContext = this;
        ButterKnife.bind(this);
        HideUtil.init(this);
        this.mPresenter = initPresenter();
        this.initBindingView();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right = (TextView) findViewById(R.id.toolbar_right);
        toolbar_left = (TextView) findViewById(R.id.toolbar_left);
//        if (toolbar != null) {
//            //将Toolbar显示到界面
//            setSupportActionBar(toolbar);
//        }
//        if (toolbar_title != null) {
//            //getTitle()的值是activity的android:lable属性值
//            toolbar_title.setText(getTitle());
//            //设置默认的标题不显示
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }


        this.initView();
        //强制在基类Intent判空
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if(null != getToolbar() && isShowBacking()){
            showBack();
        }
    }
    /**
     * this Activity of tool bar.
     * 获取头部.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }
    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     * @return
     */
    protected boolean isShowBacking(){
        return true;
    }
    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * 获取头部标题的TextView
     * @return
     */
    public TextView getToolbarTitle(){
        return toolbar_title;
    }
    /**
     * 获取头部标题的TextView
     * @return
     */
    public TextView getSubTitle(){
        return toolbar_right;
    }
    /**
     * 设置头部标题
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if(toolbar_title != null){
            toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText(title);
        }else{
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }
    /**
     * 设置头部右边标题
     * @param title
     */
    public void setTitleRight(CharSequence title) {
        if(toolbar_right != null){
            toolbar_right.setVisibility(View.VISIBLE);
            toolbar_right.setText(title);
        }else{
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }



//    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(com.cn.baselib.R.color.colorPrimary));
//    }

    /**
     * presenter与view绑定
     */
    private void initBindingView() {
        if (mPresenter != null)
            mPresenter.attachView(this);
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

    public void showDialog(String text) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中...");
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            dialog.dismiss();
        }
    }
}
