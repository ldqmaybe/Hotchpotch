package com.one.hotchpotch.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.hotchpotch.R;
import com.one.hotchpotch.base.BaseFragment;
import com.one.hotchpotch.bean.Articles;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.presenter.ArticlePresenter;
import com.one.hotchpotch.ui.activity.ArticleWebActivity;
import com.one.hotchpotch.widget.ArticleItemDecortion;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 文章
 * Created by admin on 2017/6/26.
 */
public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private PtrClassicFrameLayout mPtrFrame;
    private RecyclerView recycle;
    private View view;
    private BaseQuickAdapter adapter;
    /**
     * 一次加载多少
     */
    private int counts = 10;
    /**
     * 加载第几页
     */
    private int page = 1;
    private static final int PAGE_SIZE = 1;

    @Override
    protected ArticlePresenter initPresenter() {
        return mPresenter = new ArticlePresenter();
    }

    @Override
    protected LoadMode getMode() {
        return LoadMode.LAZY_LOAD_ONE;
    }

    @Override
    protected View getView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_article, null);
        initViews(view);
        initAdapter();
        initItemClick();
        return view;
    }

    /**
     * 初始化view
     *
     * @param view
     */
    private void initViews(View view) {
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 1000);
        recycle = (RecyclerView) view.findViewById(R.id.article_rv);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.addItemDecoration(new ArticleItemDecortion(getActivity()));
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.getArticles(counts, page);
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        adapter = new BaseQuickAdapter<Articles.Article, BaseViewHolder>(R.layout.article_item) {
            @Override
            protected void convert(BaseViewHolder helper, Articles.Article article) {
                helper.setText(R.id.article_title, article.getDesc()).setText(R.id.article_who, "作者：" + article.getWho()).setText(R.id.article_time,"发布时间："+article.getPublishedAt().split("T")[0]);
            }
        };
        recycle.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recycle);
    }

    private void initItemClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Articles.Article article = (Articles.Article) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), ArticleWebActivity.class);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getDesc());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess(Articles articles) {
        if (articles == null) {
            adapter.setEmptyView(R.layout.empty);
            mPtrFrame.refreshComplete();
            return;
        }
        if (page == 1) {
            adapter.setNewData(articles.getResults());
            adapter.disableLoadMoreIfNotFullPage(recycle);
            mPtrFrame.refreshComplete();
        } else {
            adapter.addData(articles.getResults());
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onFailure(String error) {
        mPtrFrame.refreshComplete();
        adapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData().size() < PAGE_SIZE) {
                    adapter.loadMoreEnd(true);
                } else {
                    page++;
                    mPresenter.getArticles(counts, page);
                }
            }
        }, 1000);
    }
}
