package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.contract.SettingContract;
import com.one.hotchpotch.ui.fragment.SettingFragment;

/**
 * Created by admin on 2017/6/27.
 */

public class SettingPresenter extends BasePresenter<SettingFragment> implements SettingContract.Presenter {

    @Override
    public void getArticles(int counts,int page) {}
}
