package com.one.hotchpotch.net;


import com.one.base.BaseGankResponse;
import com.one.base.BaseHttpResult;
import com.one.hotchpotch.bean.Article;
import com.one.hotchpotch.bean.User;
import com.one.net.RetrofitHttp;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/8/9 10:49
 */
public class ApiHelper {
    private static RetrofitHttp retrofitHttp;

    private ApiHelper() {
        retrofitHttp = RetrofitHttp.getInstance();
    }

    public static ApiHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final ApiHelper INSTANCE = new ApiHelper();
    }

    public Flowable<BaseGankResponse<List<Article>>> getArticles(int i, int j) {
        return retrofitHttp.createService(ApiService.class, ApiService.GAN_IO).getArticles(i, j);
    }

    public Observable<BaseHttpResult<User>> register(String name, String pwd) {
        return retrofitHttp.createService(ApiService.class).register(name, pwd);
    }
}
