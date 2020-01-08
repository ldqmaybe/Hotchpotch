package com.one.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.utils.HideUtil;
import com.one.utils.LogUtils;
import com.one.utils.ToastUtils;
import com.one.utils.ToolbarUtils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Fragment的基类
 * Created by Administrator on 2016/2/4.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements EasyPermissions.PermissionCallbacks {
    private View baseView;
    public T mPresenter;
    /**
     * 标记是否记载过
     */
    private boolean isLoaded;
    /**
     * 标记界面是否绘制完毕
     */
    private boolean isPrepared;
    /**
     * 判断用户时候看到界面
     */
    private boolean isVisible = false;
    protected BaseActivity mActivity;


    private LoadMode mode = LoadMode.UNLAZY_LOAD_DATA;

    public enum LoadMode {
        LAZY_LOAD_MORE(0), LAZY_LOAD_ONE(1), UNLAZY_LOAD_DATA(2);
        int state;

        private LoadMode(int state) {
            this.state = state;
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (baseView == null) {
            baseView = getView(inflater);
            isPrepared = true;
            HideUtil.init(getActivity());
            ToolbarUtils.init((AppCompatActivity) getActivity());
            this.mPresenter = initPresenter();
            this.initBindingView();
            onVisible();
        }

        ViewGroup viewGroup = (ViewGroup) baseView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(baseView);
        }
        //强制在基类Intent判空
        if (null != getActivity().getIntent()) {
            handleIntent(getActivity().getIntent());
        }
        return baseView;
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected T initPresenter() {
        return mPresenter;
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
     * 处理Intent，防止开发人员没做Intent判空
     */
    protected void handleIntent(Intent intent) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unLazyLoadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    /**
     * onDestroy中销毁presenter
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void onVisible() {

        //取得加载模式

        mode = getMode();

        //如果用户没有看到界面就不加载
        if (!isVisible) {
            return;
        }

        //如果界面没有加载完成，就不继续下去
        if (!isPrepared) {
            return;
        }

        lazyLoadMore();

        if (isLoaded) {
            return;
        }

        lazyLoadOne();
        isLoaded = true;
    }

    protected void onInVisible() {

    }

    /***
     * 懒加载，并加载多次
     */
    protected void lazyLoadMore() {
        if (mode != LoadMode.LAZY_LOAD_MORE) {
            return;
        }

        loadData();
    }

    /***
     * 懒加载，并只加载一次
     */
    protected void lazyLoadOne() {
        if (mode != LoadMode.LAZY_LOAD_ONE) {
            return;
        }

        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 非懒加载，跟随onActivityCreated生命周期加载
     */
    protected void unLazyLoadData() {
        if (mode != LoadMode.UNLAZY_LOAD_DATA) {
            return;
        }

        loadData();
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

    protected abstract LoadMode getMode();

    protected abstract View getView(LayoutInflater inflater);

    protected void loadData() {

    }
}
