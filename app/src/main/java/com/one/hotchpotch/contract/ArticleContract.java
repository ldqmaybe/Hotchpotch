package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.Article;

import java.util.List;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 17:12
 */
public class ArticleContract {
    public interface View {
        void onSuccess(List<Article> articles);

        void onFailure(Throwable e);
    }

    public interface Presenter {
        void getArticles(int counts,int page);
    }
}
