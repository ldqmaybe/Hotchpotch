package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.callback.ObservableCallback;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.bean.User;
import com.one.hotchpotch.contract.RegistContract;
import com.one.hotchpotch.net.ApiHelper;
import com.one.hotchpotch.ui.activity.RegisterActivity;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 14:53
 */
public class RegistPresenter extends BasePresenter<RegisterActivity> implements RegistContract.Presenter {
    @Override
    public void regist(String name, String pwd) {
        mRxManage.add(ApiHelper.getInstance().register(name, pwd)
                .compose(SchedulerUtils.<User>observableBaseResponse())
                .subscribeWith(new ObservableCallback<User>() {
                    @Override
                    protected void onSuccess(User user) {

                    }

                    @Override
                    protected void onFailure(String error) {

                    }
                }));
    }
}
