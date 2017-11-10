package com.one.hotchpotch.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.one.base.BaseFragment;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.Article;
import com.one.hotchpotch.contract.PushContract;
import com.one.hotchpotch.presenter.PushPresenter;

public class PushFragment extends BaseFragment<PushPresenter> implements PushContract.View {

    private View view;

    @Override
    protected PushPresenter initPresenter() {
        return mPresenter = new PushPresenter();
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
    public void onFailure(Throwable error) {

    }


}
