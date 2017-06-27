package com.one.hotchpotch.net;


import com.one.hotchpotch.bean.Articles;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Admin
 * @time 2016/11/24 0024.14:13
 */
public interface ArticleService {
    String ARTICLE_URL = "http://gank.io/api/";
    //    http://gank.io/api/data/Android/10/1
    @GET("data/Android/{counts}/{page}")
    Observable<Articles> getArticles(@Path("counts") int counts,@Path("page") int page);
}
