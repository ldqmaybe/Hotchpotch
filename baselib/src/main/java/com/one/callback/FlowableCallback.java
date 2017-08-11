package com.one.callback;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author Admin
 * @time 2016/7/28 0028.17:18
 */
public abstract class FlowableCallback<T> extends ResourceSubscriber<T> {

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String error);

    @Override
    public void onComplete() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        //网络判断
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFailure(BaseException.getErrorMsg(e));
    }
}
