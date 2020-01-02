package com.one.hotchpotch.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * 会生成GlideApp，glide库和注解必须在主Module的build.gradle下依赖
 *
 * @author LinDingQiang
 * @time 2019/12/30 14:58
 * @email dingqiang.l@verifone.cn
 */
@GlideModule
public class GlobalGlideConfig extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        /*
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        builder.setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE));
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(3)
                .build();
        //内存缓冲:下面3中设置都可自定义大小，以及完全自定义实现
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        //Bitmap 池
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
        //磁盘缓存
        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }
}
