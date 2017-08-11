package com.one.hotchpotch.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.adapter.PagerAdapter;
import com.one.hotchpotch.ui.fragment.ArticleFragment;
import com.one.hotchpotch.ui.fragment.HomeFragment;
import com.one.hotchpotch.ui.fragment.MyFragment;
import com.one.hotchpotch.ui.fragment.PushFragment;
import com.one.hotchpotch.ui.fragment.SettingFragment;
import com.one.hotchpotch.widget.ColorFlipPagerTitleView;
import com.one.utils.AppManager;
import com.one.utils.LogUtils;
import com.one.utils.PrefUtils;
import com.one.utils.ToolbarUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

@Route(path = "/app/main")
public class MainActivity extends BaseActivity {

    @Bind(R.id.main_indicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    private static final String[] CHANNELS = new String[]{"文章", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private long onKeyBackTime;
    private long onShowToastTime;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        toolbar = ToolbarUtils.getToolbar();
        ToolbarUtils.setTitle(getResources().getString(R.string.app_name));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initViewPager();
        initIndicator();
//        DrawerLayout的适配
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //将侧边栏顶部延伸至status bar
            drawer.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawer.setClipToPadding(false);
        }
        LogUtils.i(PrefUtils.getString("login"));
    }

    private void initViewPager() {
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getFragments()));
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>(mDataList.size());
        fragments.add(new ArticleFragment());
        fragments.add(new HomeFragment());
        fragments.add(new MyFragment());
        fragments.add(new PushFragment());
        fragments.add(new SettingFragment());
        return fragments;
    }


    private void initIndicator() {
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#00c853"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#00c853"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private static final long WAITTIME = 2000;
    long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回按钮。2秒内连续点击则退出
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            AppManager.getInstance().onKeyDown();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
