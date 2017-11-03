package com.one.base;


import com.one.net.RetrofitHttp;
import com.one.net.RxManager;

/**
 * Presenter的基类
 *
 * @author LinDingQiang
 * @time 2016/11/25 10:31
 */
public abstract class BasePresenter<V> {
    public V mView;
    protected RxManager mRxManage;
    protected RetrofitHttp mRetrofit;

    public BasePresenter() {
        this.mRxManage = new RxManager();
        this.mRetrofit = RetrofitHttp.getInstance();
    }

    /**
     * presenter与view绑定
     *
     * @param view 要绑定的view，activity or fragment 实现了它
     */
    void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除presenter与view绑定
     */
    void detachView() {
        if (mView != null) {
            mView = null;
        }
        mRxManage.unSubscribe();
    }
}
