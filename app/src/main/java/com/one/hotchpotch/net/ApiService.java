package com.one.hotchpotch.net;


import com.one.base.BaseGankResponse;
import com.one.base.BaseHttpResult;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.bean.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    /*
    例：
    http://gank.io/api/data/Android/10/1
    http://gank.io/api/data/福利/10/1
    http://gank.io/api/data/iOS/20/2
    http://gank.io/api/data/all/20/2
    */
    /**
     * 获取文章 支持背压
     * @param counts 每页的条数
     * @param page 第几页
     * @return 文章
     */
    @GET("data/all/{counts}/{page}")
    Flowable<BaseGankResponse<List<GankBean>>> getArticlesFlowable(@Path("counts") int counts, @Path("page") int page);
    /**
     * 获取文章 不支持背压
     * @param counts 每页的条数
     * @param page 第几页
     * @return 文章
     */
    @GET("data/all/{counts}/{page}")
    Observable<BaseGankResponse<List<GankBean>>> getArticlesObservable(@Path("counts") int counts, @Path("page") int page);

    /**
     * 获取福利图片 支持背压
     * @param counts 每页的条数
     * @param page 第几页
     * @return 文章
     */
    @GET("data/福利/{counts}/{page}")
    Flowable<BaseGankResponse<List<GankBean>>> getWelfareFlowable(@Path("counts") int counts, @Path("page") int page);

    /**
     * 自己搭建服务器的接口
     */

    @FormUrlEncoded
    @POST("LoginServlet")
    Observable<BaseHttpResult<User>> register(@Field("username") String username, @Field("password") String password);

    Observable<BaseGankResponse<List<GankBean>>>test();
}
