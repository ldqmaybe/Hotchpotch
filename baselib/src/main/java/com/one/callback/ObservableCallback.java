package com.one.callback;

import android.content.Context;

import com.one.exception.ApiErrorHelper;
import com.one.exception.MyException;
import com.one.utils.LoadingUtils;
import com.one.utils.NetworkUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * @author Admin
 * @time 2016/7/28 0028.17:18
 */
public abstract class ObservableCallback<T> extends DisposableObserver<T> {
    private Context context;

    public ObservableCallback(Context context) {
        this.context = context;
    }
    public ObservableCallback() {
    }
    public abstract void onSuccess(T t);

    @Override
    protected void onStart() {
        super.onStart();
        //网络判断
        if (!NetworkUtils.isConnected()) {
            this.onError(new MyException("无网络连接"));
            if (!isDisposed()) {
                dispose();
            }
            return;
        }
        if (context !=null){
            //开启加载动画
            LoadingUtils.show(context);
        }

    }

    @Override
    public void onComplete() {
            LoadingUtils.dismiss();
    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
            LoadingUtils.dismiss();
        ApiErrorHelper.handleCommonError(e);
    }
}
