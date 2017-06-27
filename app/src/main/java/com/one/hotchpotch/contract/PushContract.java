package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.Articles;

/**
 * Created by admin on 2017/6/27.
 */

public class PushContract {
    public interface View {
        void onSuccess(Articles articles);

        void onFailure(String error);
    }

    public interface Presenter {
        void getArticles(int counts, int page);
    }
}
