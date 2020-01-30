package com.mjgallery.mjgallery.mvp.ui.adapter.search;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.mjgallery.mjgallery.mvp.model.bean.search.SearchAllBean;

import java.util.List;

public class SearchAllAdapter extends BaseQuickAdapter<SearchAllBean, BaseViewHolder> {
    public SearchAllAdapter(int layoutResId, @Nullable List<SearchAllBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchAllBean item) {

    }
}
