package com.one.hotchpotch.presenter;

import com.one.hotchpotch.base.BasePresenter;
import com.one.hotchpotch.bean.Articles;
import com.one.hotchpotch.contract.HomeContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.net.RequestCallback;
import com.one.hotchpotch.ui.fragment.HomeFragment;

/**
 * Created by admin on 2017/6/27.
 */

public class HomePresenter extends BasePresenter<HomeFragment> implements HomeContract.Presenter {

    @Override
    public void getArticles(int counts,int page) {

        mRxManage.add(getService(ApiService.class).getArticles(counts,page), new RequestCallback<Articles>() {
            @Override
            public void onStart() {
                super.onStart();
                mView.showDialog();
            }

            @Override
            protected void onSuccess(Articles articles) {
                mView.onSuccess(articles);
            }

            @Override
            protected void onFailure(String error) {
                mView.dismissDialog();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mView.dismissDialog();
            }
        });
    }
}
