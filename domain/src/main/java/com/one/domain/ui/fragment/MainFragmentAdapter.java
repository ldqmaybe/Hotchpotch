package com.one.domain.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2016/7/4 10:33
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    public MainFragmentAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
