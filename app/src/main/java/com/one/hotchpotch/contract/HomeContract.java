package com.one.hotchpotch.contract;

import com.one.hotchpotch.bean.GankBean;

import java.util.List;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/6/27 17:12
 */
public class HomeContract {
    public interface View {
        void onSuccess(List<GankBean> welfares);

        void onFailure(Throwable e);
    }

    public interface Presenter {
        /**
         * 获取福利
         *
         * @param counts 每页数量
         * @param page   第几页
         */
        void getWelfares(int counts, int page);
    }
}
