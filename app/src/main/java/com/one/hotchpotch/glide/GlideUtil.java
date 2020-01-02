package com.one.hotchpotch.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.one.hotchpotch.R;

/**
 * @author LinDingQiang
 * @time 2019/12/31 16:28
 * @email dingqiang.l@verifone.cn
 */
public class GlideUtil {

    private static RequestOptions options;

    /**
     * 加载普通的图片
     *
     * @param context   上下文
     * @param url       图片url地址
     * @param imageView ImageView
     */
    public static void load(Context context, String url, ImageView imageView) {
        options = new RequestOptions()
                .placeholder(R.mipmap.placeholder)//图片加载出来前，显示的图片
                .fallback(R.mipmap.placeholder) //url为空的时候,显示的图片
                .error(R.mipmap.placeholder);//图片加载失败后，显示的图片
        GlideApp.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       图片url地址
     * @param imageView ImageView
     */
    public static void loadRound(Context context, String url, ImageView imageView) {
        options = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.placeholder)//图片加载出来前，显示的图片
                .fallback(R.mipmap.placeholder) //url为空的时候,显示的图片
                .error(R.mipmap.placeholder);//图片加载失败后，显示的图片
        GlideApp.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param id        图片DrawableRes
     * @param imageView ImageView
     */
    public static void loadRound(Context context, @RawRes @DrawableRes @Nullable Integer id, ImageView imageView) {
        options = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.placeholder)//图片加载出来前，显示的图片
                .fallback(R.mipmap.placeholder) //url为空的时候,显示的图片
                .error(R.mipmap.placeholder);//图片加载失败后，显示的图片
        GlideApp.with(context).load(id).apply(options).into(imageView);
    }


}
