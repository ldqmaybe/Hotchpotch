package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.ObservableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.contract.ArticleWebContract;
import com.one.hotchpotch.ui.activity.ArticleWebActivity;

import io.reactivex.Observable;

/**
 * author: LinDingQiang<br/>
 * created on: 2017/6/27 16:01<br/>
 * description:
 */
public class ArticleWebPresenter extends BasePresenter<ArticleWebActivity> implements ArticleWebContract.Presenter {

    @Override
    public void getProgress(int progress) {
        mRxManage.add(Observable.just(progress)
                .compose(SchedulerUtils.<Integer>obsercable())
                .subscribeWith(new ObservableCallback<Integer>() {
                    @Override
                    protected void onSuccess(Integer integer) {
                        mView.onSuccess(integer);
                    }

                    @Override
                    protected void onFailure(String error) {
                        mView.onFailure(error);
                    }
                }));
    }
}
