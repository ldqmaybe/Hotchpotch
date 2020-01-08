package com.one.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.one.utils.AppManager;
import com.one.utils.HideUtil;
import com.one.utils.LogUtils;
import com.one.utils.StatusBarUtil;
import com.one.utils.ToastUtils;
import com.one.utils.ToolbarUtils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Activity的基类
 *
 * @param <T>
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public T mPresenter;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        AppManager.getInstance().addActivityToStack(this);
        setStatusBar();
        mContext = this;
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
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        AppManager.getInstance().finishActivity(this);
    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
    }

    /**
     * presenter与view绑定
     */
    private void initBindingView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        LogUtils.i("onPermissionsGranted:获取成功的权限=" + perms);
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        StringBuilder sb = new StringBuilder();
        for (String perm : perms) {
            sb.append(perm).append("\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ToastUtils.showShortToast("已拒绝权限" + sb + "并不再询问");
            new AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("允许")
                    .setNegativeButton("拒绝")
                    .build()
                    .show();
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
