package com.one.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitHttp初始化类，内部封装OkHttp和Retrofit
 *
 * @author Admin
 * @time 2016/11/24 0024.14:23
 */
public class RetrofitHttp {
    private static String BASE_URL = "http://gank.io/api/";
    private static final int DEFAULT_TIMEOUT = 5;

    private static class SingleHolder {
        private static final RetrofitHttp INSTANCE = new RetrofitHttp();
    }

    /**
     * 获取RetrofitHttp实例
     *
     * @return RetrofitHttp实例
     */
    public static RetrofitHttp getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 初始化并且获取OkHttp
     *
     * @return OkHttpClient
     */
    private OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor())//添加拦截器
                .build();

    }

    /**
     * 有参方法<br/>
     * 初始化并且获取Retrofit
     *
     * @param url 自定义有效的url
     * @return Retrofit
     */
    private Retrofit getRetrfit(String url) {
        if (url != null) {
            BASE_URL = url;
        }
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttp())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    /**
     *无参方法<br/>
     * 创建和获取Service接口，默认url为{@link #BASE_URL}
     *
     * @param serviceClass Service的类对象
     * @param <S>          Service泛型，本项目的Service接口为AipService.class
     * @return Service
     */
    public <S> S createService(Class<S> serviceClass) {
        return getRetrfit(null).create(serviceClass);
    }
    /**
     * 有参方法<br/>
     * 创建和获取Service接口，默认url为{@link #BASE_URL}
     *
     * @param serviceClass Service的类对象
     * @param url          自定义有效的url
     * @param <S>          Service泛型，本项目的Service接口为AipService.class
     * @return Service
     */
    public <S> S createService(Class<S> serviceClass, String url) {
        return getRetrfit(url).create(serviceClass);
    }
}
