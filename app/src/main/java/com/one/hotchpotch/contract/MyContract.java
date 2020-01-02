package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.GankBean;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 17:12
 */
public class MyContract {
    public interface View {
        void onSuccess(GankBean articles);

        void onFailure(Throwable e);
    }

    public interface Presenter {
        void getArticles(int counts, int page);
    }
}
