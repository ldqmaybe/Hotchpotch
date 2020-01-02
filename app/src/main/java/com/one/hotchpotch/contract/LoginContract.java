package com.one.hotchpotch.contract;


import com.one.hotchpotch.bean.User;

/**
 * author: LinDingQiang <br/>
 * created on: 2017/10/31  14:33<br/>
 * description : please enter your class description
 */
public interface LoginContract {
    interface View {
        void onSuccess(User user);

        void onFailure(String error);
    }

    interface Presenter {
        void login(String username, String password);
    }
}
