package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.ObservableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.contract.ArticleWebContract;
import com.one.hotchpotch.ui.activity.ArticleWebActivity;
import com.one.utils.LogUtils;

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
                .compose(SchedulerUtils.<Integer>observable())
                .subscribeWith(new ObservableCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (null == mView || mView.isDestroyed()) {
                            LogUtils.i("tag", "view is destroyed");
                            return;
                        }
                        LogUtils.i("tag", mView.isDestroyed() + "");
                        mView.onSuccess(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onFailure(e);
                    }
                }));
    }
}
