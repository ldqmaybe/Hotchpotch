package com.one.hotchpotch.base;


import com.one.hotchpotch.net.ArticleService;
import com.one.hotchpotch.net.HotchpotchService;
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

    public ArticleService getArticleService(Class<ArticleService> cls) {
        return RetrofitHttp.getInstance().createArticleService(cls);
    }
    public HotchpotchService getHotchpotchService(Class<HotchpotchService> cls) {
        return RetrofitHttp.getInstance().createHotchpotchService(cls);
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
