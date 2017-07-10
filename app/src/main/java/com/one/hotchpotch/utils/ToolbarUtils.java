package com.one.hotchpotch.utils;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.one.hotchpotch.R;

/**
 * author: LinDingQiang
 * created on: 2017/6/29 10:19
 * description:
 */
public class ToolbarUtils {
    private static TextView toolbar_title;
    private static TextView toolbar_right;
    private static TextView toolbar_left;
    private static Toolbar toolbar;
    private static AppCompatActivity activity;

    /**
     * 初始化
     *
     * @param activity 上下文
     */
    public static void init(AppCompatActivity activity) {
        new ToolbarUtils(activity);
    }

    private ToolbarUtils(AppCompatActivity activity) {
        ToolbarUtils.activity = activity;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar_title = (TextView) activity.findViewById(R.id.toolbar_title);
        toolbar_right = (TextView) activity.findViewById(R.id.toolbar_right);
        toolbar_left = (TextView) activity.findViewById(R.id.toolbar_left);
    }


    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public static Toolbar getToolbar() {
        return (Toolbar) activity.findViewById(R.id.toolbar);
    }

    /**
     * 获取头部标题的TextView
     *
     * @return 标题
     */
    public static TextView getTitle() {
        return toolbar_title;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return 标题
     */
    public static TextView getRight() {
        return toolbar_right;
    }

    /**
     * 设置头部标题
     *
     * @param title 标题
     */
    public static void setTitle(CharSequence title) {
        if (toolbar_title != null) {
            toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText(title);
        } else {
            getToolbar().setTitle(title);
            activity.setSupportActionBar(getToolbar());
        }
    }

    /**
     * 设置头部左边标题
     *
     * @param title 设置标题
     */
    public static void setLeft(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            toolbar_left.setVisibility(View.VISIBLE);
            toolbar_left.setText(title);
        } else {
            toolbar_left.setVisibility(View.GONE);
        }
    }

    /**
     * 设置头部左边图标
     *
     * @param icon 设置图标
     */
    public static void setLeft(int icon) {
        if (icon != 0) {
            toolbar_left.setVisibility(View.VISIBLE);
            Drawable back = activity.getResources().getDrawable(icon);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            back.setBounds(0, 0, back.getMinimumWidth(), back.getMinimumHeight());
            toolbar_left.setCompoundDrawables(back, null, null, null);
        } else {
            toolbar_left.setVisibility(View.GONE);
        }
    }


    /**
     * 设置头部左边图标和点击监听
     *
     * @param icon     设置图标
     * @param listener 设置监听
     */
    public static void setLeft(int icon, View.OnClickListener listener) {
        if (icon != 0) {
            toolbar_left.setVisibility(View.VISIBLE);
            toolbar_left.setOnClickListener(null == listener ? null : listener);
            Drawable back = activity.getResources().getDrawable(icon);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            back.setBounds(0, 0, back.getMinimumWidth(), back.getMinimumHeight());
            toolbar_left.setCompoundDrawables(back, null, null, null);
        } else {
            toolbar_left.setVisibility(View.GONE);
        }
    }

    /**
     * 设置头部右边标题
     *
     * @param title 设置标题
     */
    public static void setRight(CharSequence title) {
        if (toolbar_right != null) {
            toolbar_right.setVisibility(View.VISIBLE);
            toolbar_right.setText(title);
        } else {
            getToolbar().setTitle(title);
            activity.setSupportActionBar(getToolbar());
        }
    }

    /**
     * 设置头部左边图标和点击监听
     *
     * @param icon     设置图标
     * @param listener 设置监听
     */
    public static void setRight(int icon, View.OnClickListener listener) {
        if (icon != 0) {
            toolbar_right.setVisibility(View.VISIBLE);
            toolbar_right.setOnClickListener(null == listener ? null : listener);
            Drawable back = activity.getResources().getDrawable(icon);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            back.setBounds(0, 0, back.getMinimumWidth(), back.getMinimumHeight());
            toolbar_right.setCompoundDrawables(back, null, null, null);
        } else {
            toolbar_right.setVisibility(View.GONE);
        }
    }
}
