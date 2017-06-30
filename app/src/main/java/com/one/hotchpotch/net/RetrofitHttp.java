package com.one.hotchpotch.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Admin
 * @time 2016/11/24 0024.14:23
 */
public class RetrofitHttp {
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit aticleRetrofit;

    private static class SingleHolder {
        private static final RetrofitHttp INSTANCE = new RetrofitHttp();
    }

    public static RetrofitHttp getInstance() {
        return SingleHolder.INSTANCE;
    }

    private OkHttpClient getOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor())//添加拦截器
                .build();
        return okHttpClient;

    }

    private Retrofit getArticleRetrfit() {
        if (aticleRetrofit == null) {
            aticleRetrofit = new Retrofit.Builder()
                    .baseUrl(ArticleService.ARTICLE_URL)
                    .client(getOkHttp())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return aticleRetrofit;
    }
    private Retrofit getHotchpotchRetrfit() {
        if (aticleRetrofit == null) {
            aticleRetrofit = new Retrofit.Builder()
                    .baseUrl(HotchpotchService.HOTCHPOTCH_URL)
                    .client(getOkHttp())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return aticleRetrofit;
    }

    public <S> S createArticleService(Class<S> serviceClass) {
        return getArticleRetrfit().create(serviceClass);
    }
    public <S> S createHotchpotchService(Class<S> serviceClass) {
        return getHotchpotchRetrfit().create(serviceClass);
    }
}
