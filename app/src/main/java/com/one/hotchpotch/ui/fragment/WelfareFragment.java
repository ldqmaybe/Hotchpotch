package com.one.hotchpotch.ui.fragment;

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
import com.one.hotchpotch.contract.HomeContract;
import com.one.hotchpotch.glide.GlideUtil;
import com.one.hotchpotch.presenter.WelfarePresenter;
import com.one.utils.LogUtils;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 福利
 */
public class WelfareFragment extends BaseFragment<WelfarePresenter> implements HomeContract.View, BaseQuickAdapter.RequestLoadMoreListener {
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
    protected WelfarePresenter initPresenter() {
        return mPresenter = new WelfarePresenter();
    }

    @Override
    protected LoadMode getMode() {
        return LoadMode.LAZY_LOAD_ONE;
    }

    @Override
    protected View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_welfare, null, false);
        initView(view);
        initAdapter();
        return view;
    }

    private void initView(View view) {
        recycle = view.findViewById(R.id.article_rv);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPtrFrame = view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame = view.findViewById(R.id.rotate_header_list_view_frame);
    }

    private void initAdapter() {
        adapter = new BaseQuickAdapter<GankBean, BaseViewHolder>(R.layout.welfare_item) {
            @Override
            protected void convert(BaseViewHolder helper, GankBean article) {
                ImageView imageView = helper.getView(R.id.imageView);
                imageView.setVisibility(View.VISIBLE);
                GlideUtil.load(getActivity(),article.getUrl(),imageView);
            }
        };
        recycle.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recycle);
    }

    @Override
    protected void loadData() {
        LogUtils.i("WelfareFragment:loadData ");
        mPtrFrame.post(() -> mPtrFrame.autoRefresh(true));
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mPresenter.getWelfares(counts, page);
            }
        });
    }


    @Override
    public void onSuccess(List<GankBean> welfares) {
        if (welfares == null || welfares.size() == 0) {
            adapter.setEmptyView(R.layout.empty);
            mPtrFrame.refreshComplete();
            return;
        }
        if (page == 1) {
            adapter.setNewData(welfares);
            adapter.disableLoadMoreIfNotFullPage();
            mPtrFrame.refreshComplete();
        } else {
            adapter.addData(welfares);
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
            mPresenter.getWelfares(counts, page);
            LogUtils.i("WelfareFragment:onLoadMoreRequested ");
        }
    }
}
