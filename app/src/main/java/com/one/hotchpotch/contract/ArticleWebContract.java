package com.one.hotchpotch.contract;

/**
 * author: LinDingQiang<br/>
 * created on: 2017/6/27 16:01<br/>
 * description:
 */
public class ArticleWebContract {
    public interface View {
        void onSuccess(Integer progress);

        void onFailure(String error);
    }

    public interface Presenter {
        void getProgress(int progress);
    }
}
