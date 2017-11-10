package com.one.exception;

import com.one.utils.ToastUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/11/6  17:53<br/>
 * description : please enter your class description
 */
public class ApiErrorHelper {

    public static void handleCommonError(Throwable e) {
        String msg ;
        if (e instanceof HttpException) {
            msg = "服务暂不可用";
        } else if (e instanceof SocketTimeoutException) {
            msg = "连接超时";
        } else if (e instanceof IOException) {
            msg = "连接失败";
        } else if (e instanceof MyException) {
            MyException apiException = (MyException) e;
            msg = apiException.getMsg();;
        } else {
            msg = "未知错误";
        }
        ToastUtils.showShortToast(msg);
    }


}
