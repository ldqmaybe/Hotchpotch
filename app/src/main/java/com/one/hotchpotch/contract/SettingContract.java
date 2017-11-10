package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.Article;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 17:12
 */
public class SettingContract {
    public interface View {
        void onSuccess(Article articles);

        void onFailure(Throwable e);
    }

    public interface Presenter {
        void getArticles(int counts, int page);
    }
}
