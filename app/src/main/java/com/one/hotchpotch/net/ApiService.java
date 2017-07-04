package com.one.hotchpotch.net;


import com.one.hotchpotch.base.BaseHttpResult;
import com.one.hotchpotch.bean.Articles;
import com.one.hotchpotch.bean.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * app接口
 *
 * @author Admin
 * @time 2016/11/24 0024.14:13
 */
public interface ApiService {
    /**
     * 干活集中营的接口
     */
    String GAN_IO = "http://gank.io/api/";

    //    http://gank.io/api/data/Android/10/1
    @GET("data/Android/{counts}/{page}")
    Observable<Articles> getArticles(@Path("counts") int counts, @Path("page") int page);


    /**
     * 自己搭建服务器的接口
     */
    String HOTCHPOTCH_URL = "http://192.168.199.217:9080/Hotchpotch/";

    @FormUrlEncoded
    @POST("LoginServlet")
    Observable<BaseHttpResult<User>> register(@Field("username") String username, @Field("password") String password);
}
