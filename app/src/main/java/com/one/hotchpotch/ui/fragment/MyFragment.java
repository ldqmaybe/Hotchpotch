package com.one.hotchpotch.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.hotchpotch.R;
import com.one.hotchpotch.base.BaseFragment;
import com.one.hotchpotch.bean.Articles;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.contract.MyContract;
import com.one.hotchpotch.presenter.ArticlePresenter;
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
    public void onSuccess(Articles articles) {

    }

    @Override
    public void onFailure(String error) {

    }

}
