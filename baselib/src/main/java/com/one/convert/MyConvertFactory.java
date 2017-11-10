package com.one.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/11/6  16:34<br/>
 * description : please enter your class description
 */
public class MyConvertFactory extends Converter.Factory {
    public static MyConvertFactory create() {
        return create(new Gson());
    }

    public static MyConvertFactory create(Gson gson) {
        return new MyConvertFactory(gson);
    }

    private final Gson gson;

    private MyConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyResponseConvert<>(gson, adapter);
    }
}
