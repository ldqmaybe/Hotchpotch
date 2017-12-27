package com.one.net;

import android.text.TextUtils;
import android.util.Log;

import com.one.base.Constant;
import com.one.exception.MyException;
import com.one.utils.LogUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author Admin
 * @time 2016/11/24 0024.14:33
 */
class LoggerInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl newUrl = request.url().newBuilder()//添加url 参数
//                .addQueryParameter("sign", "a0038a2efe62c64daa82addedad5d5d8")
//                .addQueryParameter("yct", "1480328201")//模拟数据
//                .addQueryParameter("shopid", "39")
                .build();
        //http://yuecaninfo.com/services/api_boss.php?cmd=get_date&sign=c691934e14bcf492fbd08ef14f1a0bcb&yct=1480042317&shopid=39
        Log.i("json", newUrl + "");
        if ("POST".equals(request.method())) {
            FormBody.Builder body = requestBody(request);
            request = request.newBuilder().url(newUrl).post(body.build()).build();
        } else if ("GET".equals(request.method())) {
            request = request.newBuilder().url(newUrl).get().build();
        } else {
            request = request.newBuilder().url(newUrl).build();
        }
        printRequestLog(request);
        Response response = null;
        try {
            response = chain.proceed(request);
            printResponseLog(response);
        } catch (SocketTimeoutException e) {
            throw new MyException(Constant.CONNETCTION_ERROR);
        }
        return response;
    }


    /**
     * 构造body参数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private FormBody.Builder requestBody(Request request) throws UnsupportedEncodingException {
        RequestBody requestBody = request.body();
        FormBody.Builder body = new FormBody.Builder();//添加body参数
        body.add("sign", "a0038a2efe62c64daa82addedad5d5d8").
                add("yct", "1480328201").//模拟数据
                add("shopid", "39");
        if (requestBody instanceof FormBody) {
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                String name = URLDecoder.decode(formBody.encodedName(i), "utf-8");
                String value = URLDecoder.decode(formBody.encodedValue(i), "utf-8");
                body.add(name, value);
            }
        }
        return body;
    }
    /**
     * 打印请求输出
     *
     * @param request 请求
     */
    private void printRequestLog(Request request) throws UnsupportedEncodingException {
        RequestBody requestBody = request.body();
        if (requestBody instanceof FormBody) {
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                String name = URLDecoder.decode(formBody.encodedName(i), "utf-8");
                String value = URLDecoder.decode(formBody.encodedValue(i), "utf-8");
                if (!TextUtils.isEmpty(value)) {
                    Log.i("json", "键值对：" + name + "=" + value);
                }
            }
        }
    }

    /**
     * 打印请求返回数据
     *
     * @param response 响应
     */
    private void printResponseLog(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contenType = responseBody.contentType();
        if (contenType != null) {
            charset = contenType.charset(UTF8);
        }
        String msg = buffer.clone().readString(charset).replace("\r\n\r\n", "").replace("\r\n", "");
        LogUtils.i("json", msg);
    }


}
