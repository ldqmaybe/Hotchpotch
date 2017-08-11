package com.one.callback;

import java.net.SocketException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/8/10 17:46
 */
class BaseException {
    /**
     * 根据Throwable 的类型返回响应的error信息
     * @param e Throwable类型
     * @return error信息
     */
    static String getErrorMsg(Throwable e) {
        String errorMsg;
        if (e instanceof UnknownHostException) {
            errorMsg = "无网络，请设置网络";
        } else if (e instanceof SocketException) {
            errorMsg = "系统繁忙，请稍后再试";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 504) {
                errorMsg = "网络不给力";
            } else {
                errorMsg = httpException.getMessage();
            }
        } else {
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }
}
