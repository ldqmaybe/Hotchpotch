package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.contract.PushContract;
import com.one.hotchpotch.ui.fragment.PushFragment;

/**
 * Created by admin on 2017/6/27.
 */

public class PushPresenter extends BasePresenter<PushFragment> implements PushContract.Presenter {

    @Override
    public void getArticles(int counts,int page) {}
}
