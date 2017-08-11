package com.one.callback;

import io.reactivex.observers.DisposableObserver;

/**
 * @author Admin
 * @time 2016/7/28 0028.17:18
 */
public abstract class ObservableCallback<T> extends DisposableObserver<T> {

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String error);

    @Override
    protected void onStart() {
        super.onStart();
        //网络判断
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T response) {
            onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFailure(BaseException.getErrorMsg(e));
    }
}
