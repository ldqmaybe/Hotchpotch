package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.bean.Articles;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.ui.fragment.ArticleFragment;
import com.one.net.RequestCallback;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 11:30
 */
public class ArticlePresenter extends BasePresenter<ArticleFragment> implements ArticleContract.Presenter {

    @Override
    public void getArticles(int counts, int page) {

        mRxManage.add(getService(ApiService.class, ApiService.GAN_IO).getArticles(counts, page), new RequestCallback<Articles>() {
            @Override
            public void onStart() {
                super.onStart();
//                mView.showDialog();
            }

            @Override
            protected void onSuccess(Articles articles) {
                mView.onSuccess(articles);
            }

            @Override
            protected void onFailure(String error) {
//                mView.dismissDialog();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
//                mView.dismissDialog();
            }
        });
    }
}
