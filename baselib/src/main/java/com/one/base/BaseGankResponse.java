package com.one.base;

/**
 * description:干货集中营base bean
 *
 * @author: LinDingQiang
 * @created on: 2017/8/10 16:08
 */
public class BaseGankResponse<T> {
    //false为正确返回数据
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
