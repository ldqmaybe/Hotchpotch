package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.contract.HomeContract;
import com.one.hotchpotch.ui.fragment.HomeFragment;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 11:26
 */
public class HomePresenter extends BasePresenter<HomeFragment> implements HomeContract.Presenter {

    @Override
    public void getArticles(int counts, int page) {
    }
}
