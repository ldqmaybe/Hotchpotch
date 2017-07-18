package com.one.base;

/**
 * @author Admin
 * @time 2016/11/24 0024.14:16
 */
public class BaseHttpResult<T> {
    private int code;
    private String msg;
    private T data;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
