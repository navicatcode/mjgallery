package com.mjgallery.mjgallery.mvp.ui.adapter.search;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchItemBean;

import java.util.List;

public class SearchItemAdapter extends BaseQuickAdapter<SearchItemBean, BaseViewHolder> {
    public SearchItemAdapter(int layoutResId, @Nullable List<SearchItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchItemBean item) {

    }
}
