package com.one.base;

import com.google.gson.annotations.SerializedName;

/**
 * base bean
 * @author Admin
 * @time 2016/11/24 0024.14:16
 */
public class BaseHttpResult<T> {
    private int status;
    private String desc;
    @SerializedName(value = "data", alternate = {"object", "resp", "notes", "xxx"})
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
