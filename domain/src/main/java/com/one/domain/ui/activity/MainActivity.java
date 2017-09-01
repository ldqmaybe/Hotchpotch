package com.one.domain.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.one.base.BaseActivity;
import com.one.domain.R;
import com.one.domain.ui.fragment.FinancialFragment;
import com.one.domain.ui.fragment.HomeFragment;
import com.one.domain.ui.fragment.MainFragmentAdapter;
import com.one.domain.ui.fragment.MyFragment;
import com.one.domain.ui.fragment.ShouKuanFragment;
import com.one.domain.widget.NavItem;

@Route(path = "/app/domain")
public class MainActivity extends BaseActivity {
    private Fragment[] fragments;
    private ViewPager mViewPager;
    private MainFragmentAdapter fragmentAdapter;
    private LinearLayout bottomBar;
    private int lastPosition;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomBar = (LinearLayout) findViewById(R.id.bottom);

        fragments = new Fragment[]{new HomeFragment(), new ShouKuanFragment(), new FinancialFragment(), new MyFragment()};
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setOffscreenPageLimit(1);

        for (int i = 0; i < bottomBar.getChildCount(); i++) {
            ((NavItem) bottomBar.getChildAt(i)).setOnClickListener(new NavItemOnClickListener(i));
        }
        ((NavItem) bottomBar.getChildAt(0)).updateItemAlpha(1);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int lastState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0) {
                ((NavItem) bottomBar.getChildAt(position)).updateItemAlpha(1 - positionOffset);
                ((NavItem) bottomBar.getChildAt(position + 1)).updateItemAlpha(positionOffset);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (lastState != 2) {
                ((NavItem) bottomBar.getChildAt(position)).updateItemAlpha(1);
                ((NavItem) bottomBar.getChildAt(lastPosition)).updateItemAlpha(0);
            }
            lastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            lastState = state;
            boolean isCanClick = (state == 0);
            int l = bottomBar.getChildCount();
            for (int i = 0; i < l; i++) {
                bottomBar.getChildAt(i).setClickable(isCanClick);
            }
        }
    }

    private class NavItemOnClickListener implements View.OnClickListener {
        private int i;

        NavItemOnClickListener(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(i, false);
        }
    }

}
