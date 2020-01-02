package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.FlowableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.contract.HomeContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.ui.fragment.WelfareFragment;
import com.one.net.RetrofitHttp;

import java.util.List;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 11:26
 */
public class WelfarePresenter extends BasePresenter<WelfareFragment> implements HomeContract.Presenter {

    @Override
    public void getWelfares(int counts, int page) {
        mRxManage.add(
                RetrofitHttp.getInstance().createService(ApiService.class).getWelfareFlowable(counts, page)
                        .compose(SchedulerUtils.flowableMapBaseResponse())
                        .subscribeWith(new FlowableCallback<List<GankBean>>(null) {
                            @Override
                            public void onSuccess(List<GankBean> welfareBeans) {
                                mView.onSuccess(welfareBeans);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mView.onFailure(e);
                            }
                        }));
    }
}
