package com.one.callback;

import com.one.base.BaseGankResponse;
import com.one.base.BaseHttpResult;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * description:
 *
 * @author: LinDingQiang
 * @created on: 2017/8/9 15:50
 */
public class SchedulerUtils {
    /**
     * Observable 线程转换,不包含BaseHttpResult
     *
     * @param <T> 数据源
     * @return ObservableTransformer类型
     */
    public static <T> ObservableTransformer<T, T> obsercable() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 线程转换+
     * 在BaseHttpResult的情况下，将BaseHttpResult类型转换成T类型
     *
     * @param <T> data数据源
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseHttpResult<T>, T> observableBaseResponse() {
        return new ObservableTransformer<BaseHttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseHttpResult<T>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseHttpResult<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(@NonNull BaseHttpResult<T> result) throws Exception {
                                if (result.getCode() == 0) {
                                    //服务器正确返回
                                    return createObservableData(result.getData());
                                } else {
                                    //服务器不正确返回
                                    return Observable.error(new Exception(result.getMsg()));
                                }
                            }
                        });
            }
        };
    }

    /**
     * 返回data数据
     *
     * @param t 转换后的数据
     * @param <T>  转换后的数据类型
     * @return data数据
     */
    private static <T> Observable<T> createObservableData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

//************************************************************Flowable************************************************************

    /**
     * Observable 线程转换,不包含BaseHttpResult
     *
     * @param <T> 数据源
     * @return FlowableTransformer类型
     */
    public static <T> FlowableTransformer<T, T> flowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 线程转换+
     * 在BaseHttpResult的情况下，将BaseHttpResult类型转换成T类型
     *
     * @param <T> data数据源
     * @return ObservableTransformer
     */
    public static <T> FlowableTransformer<BaseGankResponse<T>, T> flowableBaseReponse() {
        return new FlowableTransformer<BaseGankResponse<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<BaseGankResponse<T>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseGankResponse<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(@NonNull BaseGankResponse<T> result) throws Exception {
                                if (result.isError()) {
                                    //服务器不正确返回
                                    return Flowable.error(new Exception("error"));
                                } else {
                                    //服务器正确返回
                                    return createFlowableData(result.getResults());
                                }
                            }
                        });
            }
        };
    }

    /**
     * 返回data数据,默认背压BackpressureStrategy.BUFFER
     *
     * @param t 转换后的数据
     * @param <T>  转换后的数据类型
     * @return data数据
     */
    private static <T> Flowable<T> createFlowableData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
