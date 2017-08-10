package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.FlowableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.bean.Article;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.net.ApiHelper;
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
        mRxManage.add(ApiHelper.getInstance().getArticles(counts, page)
                .compose(SchedulerUtils.<List<Article>>flowableBaseReponse())
                .subscribeWith(new FlowableCallback<List<Article>>() {
                    @Override
                    protected void onSuccess(List<Article> articles) {
                        mView.onSuccess(articles);
                    }

                    @Override
                    protected void onFailure(String error) {
                        mView.onFailure(error);
                    }
                }));
    }

}
