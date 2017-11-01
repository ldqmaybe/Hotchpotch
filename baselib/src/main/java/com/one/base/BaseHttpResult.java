package com.one.base;

/**
 * @author Admin
 * @time 2016/11/24 0024.14:16
 */
public class BaseHttpResult<T> {
    private int status;
    private String desc;
    private T data;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
