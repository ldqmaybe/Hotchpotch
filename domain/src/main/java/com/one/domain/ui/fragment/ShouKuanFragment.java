package com.one.domain.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;

import com.one.base.BaseFragment;
import com.one.domain.R;
import com.one.utils.LogUtils;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class ShouKuanFragment extends BaseFragment {

    @Override
    protected LoadMode getMode() {
        return LoadMode.LAZY_LOAD_ONE;
    }

    @Override
    protected View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_shou_kuan,null,false);
        LogUtils.i(this.getClass().getSimpleName());
        return view;
    }
}
