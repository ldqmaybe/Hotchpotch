package com.one.hotchpotch.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.one.base.BaseFragment;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.contract.MyContract;
import com.one.hotchpotch.presenter.MyPresenter;

public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {

    private View view;

    @Override
    protected MyPresenter initPresenter() {
        return mPresenter = new MyPresenter();
    }

    @Override
    protected LoadMode getMode() {
        return LoadMode.LAZY_LOAD_ONE;
    }

    @Override
    protected View getView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_setting,null,false);
        return view;
    }

    @Override
    protected void loadData() {
        mPresenter.getArticles(10,1);
    }

    @Override
    public void onSuccess(GankBean articles) {

    }

    @Override
    public void onFailure(Throwable error) {

    }

}
