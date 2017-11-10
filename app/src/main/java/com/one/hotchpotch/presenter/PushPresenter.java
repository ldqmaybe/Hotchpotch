package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.contract.PushContract;
import com.one.hotchpotch.ui.fragment.PushFragment;

/**
 * description:
 * 使用flatMap进行多次网络请求
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 16:02
 */
public class PushPresenter extends BasePresenter<PushFragment> implements PushContract.Presenter {

    @Override
    public void getArticles(int counts, int page) {
    }
}
