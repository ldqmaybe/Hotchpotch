package com.one.hotchpotch.presenter;

import com.one.base.BasePresenter;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.contract.MyContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.ui.fragment.MyFragment;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2017/6/27 10:55
 */
public class MyPresenter extends BasePresenter<MyFragment> implements MyContract.Presenter {

    @Override
    public void getArticles(int counts,int page) {}

    public void testRxjava2Zip(){
        Observable observable1 = mRetrofit.createService(ApiService.class).test();
        Observable observable2 = mRetrofit.createService(ApiService.class).test();
        Observable.zip(observable1, observable2, new BiFunction<GankBean, GankBean, String>() {
            @Override
            public String apply(@NonNull GankBean mobileAddress, @NonNull GankBean categoryResult) throws Exception {
                return "合并后的数据为：手机归属地：";
            }
        })
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {

            }
        });
    }
}
