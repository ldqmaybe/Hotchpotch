package com.one.hotchpotch.presenter;

import com.one.hotchpotch.base.BasePresenter;
import com.one.hotchpotch.bean.User;
import com.one.hotchpotch.contract.RegistContract;
import com.one.hotchpotch.net.HotchpotchService;
import com.one.hotchpotch.net.RequestCallback;
import com.one.hotchpotch.ui.activity.RegisterActivity;

/**
 * Created by admin on 2017/6/27.
 */

public class RegistPresenter extends BasePresenter<RegisterActivity> implements RegistContract.Presenter {
    @Override
    public void regist(String name, String pwd) {
        mRxManage.addSubscription1(getHotchpotchService(HotchpotchService.class).register(name, pwd), new RequestCallback<User>() {
            @Override
            protected void onSuccess(User user) {
                mView.onSuccess(user);
            }

            @Override
            protected void onFailure(String error) {
                mView.onFailure(error);
            }
        });
    }
}
