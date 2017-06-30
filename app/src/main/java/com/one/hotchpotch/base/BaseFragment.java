package com.one.hotchpotch.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.hotchpotch.utils.HideUtil;
import com.one.hotchpotch.utils.ToolbarUtils;

import butterknife.ButterKnife;

/**
 * Fragment的基类
 * Created by Administrator on 2016/2/4.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private View baseView;
    public T mPresenter;
    private ProgressDialog dialog;
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

    public void showDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("加载中...");
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            dialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (baseView == null) {
            baseView = getView(inflater);
            isPrepared = true;
            ButterKnife.bind(getActivity());
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
        if (mPresenter != null)
            mPresenter.attachView(this);
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
        if (mPresenter != null)
            mPresenter.detachView();
        ButterKnife.unbind(this);
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

    protected abstract LoadMode getMode();

    protected abstract View getView(LayoutInflater inflater);

    protected void loadData() {

    }
}
