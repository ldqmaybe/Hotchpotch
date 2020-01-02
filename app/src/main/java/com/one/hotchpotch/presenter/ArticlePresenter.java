package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.ObservableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.ui.fragment.ArticleFragment;

import java.util.List;


/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 11:30
 */
public class ArticlePresenter extends BasePresenter<ArticleFragment> implements ArticleContract.Presenter {

    @Override
    public void getArticles(int counts, int page) {
        mRxManage.add(mRetrofit.createService(ApiService.class).getArticlesObservable(counts, page)
                .compose(SchedulerUtils.observableMapBaseResponse())
                .subscribeWith(new ObservableCallback<List<GankBean>>(null) {
                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(List<GankBean> articles) {
                        mView.onSuccess(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onFailure(e);
                    }
                }));
//        mRxManage.add(getService(ApiService.class).getArticlesFlowable(counts, page)
//                .compose(SchedulerUtils.<List<GankBean>>flowableBaseResponse())
//                .subscribeWith(new FlowableCallback<List<GankBean>>() {
//                    @Override
//                    protected void onSuccess(List<GankBean> articles) {
//                        mView.onSuccess(articles);
//                    }
//
//                    @Override
//                    protected void onFailure(String error) {
//                        mView.onFailure(error);
//                    }
//                }));
    }

}
