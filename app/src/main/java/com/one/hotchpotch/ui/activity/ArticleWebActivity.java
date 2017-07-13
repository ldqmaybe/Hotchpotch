package com.one.hotchpotch.ui.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.widget.NumberProgressBar;
import com.one.utils.ToolbarUtils;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 文章
 */
public class ArticleWebActivity extends BaseActivity {
    @Bind(R.id.wvBoss)
    WebView wvBoss;
    @Bind(R.id.numberProgressBar)
    NumberProgressBar numberProgressBar;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_article_web;
    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        ToolbarUtils.setTitle(title);
        ToolbarUtils.setLeft(R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleWebActivity.this.finish();
            }
        });

        WebSettings setting = wvBoss.getSettings();
        setting.setJavaScriptEnabled(true);
        // 注册javascript对象，local_obj用于javascript调用
        //webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        setting.setDomStorageEnabled(true);// 开启webview的DOM Storage数据存储
        setting.setSupportZoom(true);// 开启webview的缩放
        wvBoss.requestFocus();
        setting.setUseWideViewPort(true);// 设置此属性，可任意缩放webview
        setting.setLoadWithOverviewMode(true);
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setDefaultTextEncodingName("utf-8");
        wvBoss.loadUrl(url);
        wvBoss.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                initObservable(newProgress);
            }

        });
        wvBoss.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
    }

    private void initObservable(final int newProgress) {
        Observable.just(newProgress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        numberProgressBar.setVisibility(View.VISIBLE);
                        numberProgressBar.setProgress(integer);
                        if (integer >= 100) {
                            numberProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
