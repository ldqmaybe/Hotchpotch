package com.one.hotchpotch.net;



import com.one.hotchpotch.base.BaseHttpResult;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理单个presenter的Rxjava相关代码的生命周期处理
 *
 * @author LinDingQiang
 * @time 2016/7/28 14:40
 */
public class RxManager {
    private CompositeSubscription mSubscription;

    /**
     * 没有BaseEntity的情况下
     *
     * @param observable 事件源
     * @param subscriber 事件源的监听者-观察者
     */
    public void add(Observable observable, Subscriber subscriber) {
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 有BaseEntity的情况下
     *
     * @param observable 事件源
     * @param subscriber 事件源的监听者-观察者
     * @param <T> data的泛型
     */
    public <T> void adds(Observable<BaseHttpResult<T>> observable, Subscriber<T> subscriber) {
        if (null == mSubscription) {
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(observable.map(new HttpResultFunc<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 将事件源与CompositeSubscription解除绑定，避免出现内存泄露
     */
    public void unSubscribe() {
        if (null != mSubscription && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    /**
     * 用来统一处理Http的result,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private static class HttpResultFunc<T> implements Func1<BaseHttpResult<T>, T> {

        @Override
        public T call(BaseHttpResult<T> baseHttpResult) {
            if (baseHttpResult.getCode()==0) {
                return baseHttpResult.getData();
            }else {
                //在这里处理请求返回的错误信息（）
                //将错误信息抛给Subscriber.onError()统一处理
                throw new RuntimeException(baseHttpResult.getMsg());
            }

        }
    }

}
