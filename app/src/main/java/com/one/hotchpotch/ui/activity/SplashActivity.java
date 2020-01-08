package com.one.hotchpotch.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.glide.GlideUtil;
import com.one.hotchpotch.navigation.Navigator;
import com.one.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity {
    private AlphaAnimation alphaAnimation = null;
    private ImageView logo;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        logo = findViewById(R.id.logo);
        GlideUtil.loadRound(this, R.mipmap.logo, logo);
        alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.i("SplashActivity", "onAnimationEnd");
                Navigator.getInstance().navigate2Main(SplashActivity.this);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        checkPermission();
    }

    private void checkPermission() {
        /*
         * 6.0以下版本(系统自动申请) 不会弹框
         * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
         */
        String[] perms = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        EasyPermissions.requestPermissions(this, "优了个秀应用需要以下权限，请允许", 0, (String[]) Arrays.copyOf(perms, perms.length));
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List perms) {
        LogUtils.i("SplashActivity", "requestCode = " + requestCode);
        if (requestCode == 0) {
            LogUtils.i("SplashActivity", "perms = " + perms.size());
            if (!perms.isEmpty()) {
                for (Object perm : perms) {
                    Log.i("SplashActivity", "onPermissionsGranted: perm=" + perm);
                }
                if (perms.contains(Manifest.permission.READ_PHONE_STATE) && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    LogUtils.i("SplashActivity", "alphaAnimation = " + alphaAnimation);
                    if (alphaAnimation != null) {
                        LogUtils.i("SplashActivity", "startAnimation");
                        logo.startAnimation(alphaAnimation);
                    }
                }
            }
        }
    }
}
