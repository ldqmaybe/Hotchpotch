package com.one.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * author: LinDingQiang<br/>
 * created on: 2017/7/10 17:35<br/>
 * description:
 */
public class BaseApplication extends Application {
    private static Application sInstance;

    public static Context getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initBugly();
        initArouter();
    }

    private void initArouter() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(sInstance); // 尽可能早，推荐在Application中初始化
    }

    private void initBugly() {
//        第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//            输出详细的Bugly SDK的Log；
//            每一条Crash都会被立即上报；
//            自定义日志将会在Logcat中输出。
//            建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(sInstance, "90518d896a", true);
    }
}
