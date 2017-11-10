package com.one.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.one.exception.MyException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/11/6  16:35<br/>
 * description : please enter your class description
 */
public class MyResponseConvert<T> implements Converter<ResponseBody, T> {
    private Gson gson = null;
    private TypeAdapter<T> adapter = null;

    public MyResponseConvert(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);

            int status = json.optInt("status");
            String desc = json.optString("desc", "");
            boolean error = json.optBoolean("error",true);
            if (status ==1000) {    //**********************服务器1 **************************
                if (json.has("data")) {
                    Object data = json.get("data");
                    body = data.toString();
                    return adapter.fromJson(body);
                }else {
                    throw new MyException( desc);
                }
            } else  if (!error){    //**********************服务器2 **************************
                if (json.has("results")) {
                    Object data = json.get("results");
                    body = data.toString();
                    return adapter.fromJson(body);
                }else {
                    throw  new MyException( desc);
                }
            } else {
                throw new MyException( desc);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
