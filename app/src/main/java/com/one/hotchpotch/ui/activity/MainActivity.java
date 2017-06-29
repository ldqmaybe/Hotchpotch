package com.one.hotchpotch.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.one.hotchpotch.R;
import com.one.hotchpotch.adapter.PagerAdapter;
import com.one.hotchpotch.base.BaseActivity;
import com.one.hotchpotch.ui.fragment.ArticleFragment;
import com.one.hotchpotch.ui.fragment.HomeFragment;
import com.one.hotchpotch.ui.fragment.MyFragment;
import com.one.hotchpotch.ui.fragment.PushFragment;
import com.one.hotchpotch.ui.fragment.SettingFragment;
import com.one.hotchpotch.utils.ToolbarUtils;
import com.one.hotchpotch.widget.ColorFlipPagerTitleView;

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

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_indicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private static final String[] CHANNELS = new String[]{"文章", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ToolbarUtils.setTitle(getResources().getString(R.string.app_name));
        ToolbarUtils.setLeft(R.mipmap.ic_back);
        initViewPager();
        initIndicator();
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
}
