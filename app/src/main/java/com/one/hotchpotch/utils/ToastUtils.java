package com.one.hotchpotch.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.one.base.common.BaseApplication;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/6/29 10:19 <br/>
 * description:Toast工具类
 */
public class ToastUtils {

    /**
     * 获取当前application对象
     * @return application对象
     */
    public static Context getContext() {
        return BaseApplication.getInstance();
    }

    /**
     * 获取资源
     * @return Resources
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 显示时间为short的toast
     * @param msg 需要显示的内容
     */
    public static void showShortToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 显示时间为short的toast
     * @param resid 需要显示的内容
     */
    public static void showShortToast(int resid) {
        showShortToast(getContext().getResources().getString(resid));
    }

    /**
     * 显示时间为Long的toast
     * @param msg 需要显示的内容
     */
    public static void showLongToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示时间为Long的toast
     * @param resid 需要显示的内容
     */
    public static void showLongToast(int resid) {
        showLongToast(getContext().getResources().getString(resid));
    }
}
