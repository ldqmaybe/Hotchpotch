package com.one.callback;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author Admin
 * @time 2016/7/28 0028.17:18
 */
public abstract class ObservableCallback<T> extends DisposableObserver<T> {

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String error);

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
        String msg;
        if (e instanceof UnknownHostException) {
            msg = "无网络，请设置网络";
        } else if (e instanceof SocketException) {
            msg = "系统繁忙，请稍后再试";
        } else if (e instanceof ConnectException) {
            msg = "无法连接服务器，请检查您的网络状态";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 504) {
                msg = "网络不给力";
            } else {
                msg = httpException.getMessage();
            }
        } else {
            msg = e.getMessage();
        }
        onFailure(msg);
    }
}
