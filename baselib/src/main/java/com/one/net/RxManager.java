package com.one.net;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于管理单个presenter的Rxjava相关代码的生命周期处理
 *
 * @author LinDingQiang
 * @time 2016/7/28 14:40
 */
public class RxManager {
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 没有BaseEntity的情况下
     *
     * @param disposable 事件源
     */
    public void add(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 将事件源与CompositeSubscription解除绑定，避免出现内存泄露
     */
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
