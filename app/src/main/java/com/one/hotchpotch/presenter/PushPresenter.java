package com.one.hotchpotch.presenter;

import com.one.base.BaseGankResponse;
import com.one.base.BasePresenter;
import com.one.callback.SchedulerUtils;
import com.one.hotchpotch.bean.Article;
import com.one.hotchpotch.contract.PushContract;
import com.one.hotchpotch.net.ApiService;
import com.one.hotchpotch.ui.fragment.PushFragment;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
    public void testRxjava2FlatMap(){
        mRxManage.add(mRetrofit.createService(ApiService.class).getArticlesObservable(1, 2)//第一次网络请求，比如注册
                .compose(SchedulerUtils.<BaseGankResponse<List<Article>>>observable())
                .flatMap(new Function<BaseGankResponse<List<Article>>, ObservableSource<Article>>() {//使用flatMap发射第二次请求，去登录
                    @Override
                    public ObservableSource<Article> apply(@NonNull BaseGankResponse<List<Article>> articleList) throws Exception {
                        if (true) {
                            return mRetrofit.createService(ApiService.class).test();//第二次请求，比如去登录
                        } else {
                            mView.onFailure("error");
                            return null;
                        }

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        //第二次请求成功执行
                        mView.onSuccess(article);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //   第二次请求失败执行
                    }
                }));
    }
}
