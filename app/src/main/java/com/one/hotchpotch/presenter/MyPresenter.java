package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.contract.MyContract;
import com.one.hotchpotch.ui.fragment.MyFragment;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 10:55
 */
public class MyPresenter extends BasePresenter<MyFragment> implements MyContract.Presenter {

    @Override
    public void getArticles(int counts,int page) {}
}
