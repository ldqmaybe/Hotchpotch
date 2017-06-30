package com.one.hotchpotch.net;

import com.one.hotchpotch.base.BaseHttpResult;
import com.one.hotchpotch.bean.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 自己搭建的服务器接口
 * Created by ${User} on 2017/6/30.
 */
public interface HotchpotchService {
    //    http://localhost:9080/Hotchpotch/LoginServlet?username=a3&password=123
    String HOTCHPOTCH_URL = "http://192.168.199.217:9080/Hotchpotch/";
    @FormUrlEncoded
    @POST("LoginServlet")
    Observable<BaseHttpResult<User>> register(@Field("username") String username, @Field("password") String password);
}
