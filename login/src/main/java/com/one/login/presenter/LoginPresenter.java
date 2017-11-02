package com.one.login.presenter;

import com.one.base.BasePresenter;
import com.one.callback.ObservableCallback;
import com.one.callback.SchedulerUtils;
import com.one.login.contract.LoginContract;
import com.one.login.entity.User;
import com.one.login.service.LoginApi;
import com.one.login.ui.LoginActivity;
import com.one.login.utils.SafeUtil;
import com.one.utils.LogUtils;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/10/31  14:32<br/>
 * description : please enter your class description
 */
public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {
    @Override
    public void login(String username, String password) {
        password = SafeUtil.shortMD5(password);
        LogUtils.i(password);
        mRxManage.add(getService(LoginApi.class, LoginApi.HOTCH_URL).login(username, password)
                .compose(SchedulerUtils.<User>observableBaseResponse())
                .subscribeWith(new ObservableCallback<User>() {
                    @Override
                    protected void onSuccess(User user) {
                        mView.onSuccess(user);
                    }

                    @Override
                    protected void onFailure(String error) {
                        mView.onFailure(error);
                    }
                })

        );
    }
}
