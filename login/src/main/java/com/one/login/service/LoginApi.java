package com.one.login.service;

import com.one.base.BaseHttpResult;
import com.one.login.entity.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/10/31  14:38<br/>
 * description : please enter your class description
 */
public interface LoginApi {
    String HOTCH_URL = "http://192.168.199.129:8080/hotch/";

    @FormUrlEncoded
    @POST("login")
    Observable<BaseHttpResult<User>> login(@Field("name")String username, @Field("password")String password);
    @FormUrlEncoded
    @POST("register")
    Observable<BaseHttpResult<User>> register(@Field("name")String username, @Field("password")String password);
}
