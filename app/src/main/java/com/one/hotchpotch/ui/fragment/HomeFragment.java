package com.one.hotchpotch.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.one.base.BaseFragment;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.Article;
import com.one.hotchpotch.contract.HomeContract;
import com.one.hotchpotch.presenter.HomePresenter;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private View view;

    @Override
    protected HomePresenter initPresenter() {
        return mPresenter = new HomePresenter();
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
    public void onSuccess(Article articles) {

    }

    @Override
    public void onFailure(String error) {

    }


}
