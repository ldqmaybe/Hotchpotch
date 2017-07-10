package com.one.hotchpotch.base;


import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.net.RetrofitHttp;
import com.one.hotchpotch.net.RxManager;

/**
 * Presenter的基类
 *
 * @author LinDingQiang
 * @time 2016/11/25 10:31
 */
public abstract class BasePresenter<V> {
    public V mView;
    protected RxManager mRxManage;

    public BasePresenter() {
        this.mRxManage = new RxManager();
    }

    /**
     *  无url参数方法<br>
     *  初始化Service
     * @param cls Service的类对象
     * @return ApiService
     */
    protected ApiService getService(Class<ApiService> cls) {
        return RetrofitHttp.getInstance().createService(cls);
    }

    /**
     *  有url参数方法<br>初始化Service
     * @param cls  Service的类对象
     * @param url  自定义有效的url
     * @return ApiService
     */
    protected ApiService getService(Class<ApiService> cls, String url) {
        return RetrofitHttp.getInstance().createService(cls, url);
    }
    /**
     * presenter与view绑定
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
