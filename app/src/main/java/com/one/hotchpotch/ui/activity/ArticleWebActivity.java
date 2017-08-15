package com.one.hotchpotch.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.one.base.BaseActivity;
import com.one.hotchpotch.R;
import com.one.hotchpotch.contract.ArticleWebContract;
import com.one.hotchpotch.presenter.ArticleWebPresenter;
import com.one.hotchpotch.widget.NumberProgressBar;
import com.one.utils.LogUtils;
import com.one.utils.ToastUtils;
import com.one.utils.ToolbarUtils;

import butterknife.Bind;

/**
 * author: LinDingQiang<br/>
 * created on: 2017/6/27 16:01<br/>
 * description:
 */
public class ArticleWebActivity extends BaseActivity<ArticleWebPresenter> implements ArticleWebContract.View {
    @Bind(R.id.wvBoss)
    WebView wvBoss;
    @Bind(R.id.numberProgressBar)
    NumberProgressBar numberProgressBar;

    @Override
    protected ArticleWebPresenter initPresenter() {
        return new ArticleWebPresenter();
    }

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
                mPresenter.getProgress(newProgress);
            }

        });
        wvBoss.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
    }

    @Override
    public void onSuccess(Integer progress) {

        numberProgressBar.setVisibility(View.VISIBLE);
        numberProgressBar.setProgress(progress);
        if (progress >= 100) {
            numberProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(String error) {
        LogUtils.i(error);
        ToastUtils.showShortToast(error);
    }
}
