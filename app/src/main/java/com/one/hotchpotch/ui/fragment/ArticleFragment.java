package com.one.hotchpotch.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.base.BaseFragment;
import com.one.hotchpotch.R;
import com.one.hotchpotch.bean.GankBean;
import com.one.hotchpotch.contract.ArticleContract;
import com.one.hotchpotch.glide.GlideUtil;
import com.one.hotchpotch.presenter.ArticlePresenter;
import com.one.hotchpotch.ui.activity.ArticleWebActivity;
import com.one.utils.StringUtils;

import java.util.List;

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
    private BaseQuickAdapter<GankBean, BaseViewHolder> adapter;
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
        View view = inflater.inflate(R.layout.fragment_article, null);
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
        recycle = view.findViewById(R.id.article_rv);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycle.addItemDecoration(new ArticleItemDecortion(getActivity()));

        mPtrFrame = view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.post(() -> mPtrFrame.autoRefresh(true));
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.getArticles(counts, page);
                    }
                });
            }
        });
    }

    private void initAdapter() {
        adapter = new BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.article_item) {
            @Override
            protected void convert(BaseViewHolder helper, GankBean article) {
                helper.setText(R.id.article_title, article.getDesc())
                        .setText(R.id.article_who, article.getWho())
                        .setText(R.id.article_type, article.getType());
                helper.setText(R.id.article_time, StringUtils.getFormattedDate(article.getPublishedAt().split("T")[0], "yyyy-MM-dd", "yyyy年MM月dd日"));
                ImageView imageView = helper.getView(R.id.imageView);

                imageView.setVisibility(View.GONE);
                if (article.getImages() != null && article.getImages().size() > 0) {
                    imageView.setVisibility(View.VISIBLE);
//                    Glide.with(ArticleFragment.this) .load(article.getImages().get(0)) .into(imageView);
                    GlideUtil.load(getActivity(),article.getImages().get(0),imageView);
                }
            }
        };
        recycle.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recycle);
    }

    private void initItemClick() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            GankBean article = (GankBean) adapter.getData().get(position);
            Intent intent = new Intent(getActivity(), ArticleWebActivity.class);
            intent.putExtra("url", article.getUrl());
            intent.putExtra("title", article.getDesc());
            startActivity(intent);
        });
    }

    @Override
    public void onSuccess(List<GankBean> articles) {
        if (articles == null || articles.size() == 0) {
            adapter.setEmptyView(R.layout.empty);
            mPtrFrame.refreshComplete();
            return;
        }
        if (page == 1) {
            adapter.setNewData(articles);
            adapter.disableLoadMoreIfNotFullPage();
            mPtrFrame.refreshComplete();
        } else {
            adapter.addData(articles);
            adapter.loadMoreComplete();
        }
        page++;
    }

    @Override
    public void onFailure(Throwable error) {
        mPtrFrame.refreshComplete();
        adapter.loadMoreFail();
    }

    @Override
    public void onLoadMoreRequested() {
        mPtrFrame.refreshComplete();
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            mPresenter.getArticles(counts, page);
        }
    }
}
