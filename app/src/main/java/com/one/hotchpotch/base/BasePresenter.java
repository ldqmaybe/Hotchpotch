package com.one.hotchpotch.base;


import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.net.RetrofitHttp;
import com.one.hotchpotch.net.RxManager;

/**
 * @author Admin
 * @time 2016/11/25 0025.10:31
 */
public abstract class BasePresenter<V> {
    public V mView;
    public RxManager mRxManage;

    public BasePresenter() {
        this.mRxManage = new RxManager();
    }

    public ApiService getService(Class<ApiService> cls) {
        return RetrofitHttp.getInstance().createService(cls);
    }

    public void attachView(V view) {
        this.mView = (V) view;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
        mRxManage.clear();
    }
}
