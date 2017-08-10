package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.User;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/30 17:12
 */
public class RegistContract {
    public interface View {
        void onSuccess(User user);

        void onFailure(String error);
    }

    public interface Presenter {
        void regist(String name, String pwd);
    }
}
