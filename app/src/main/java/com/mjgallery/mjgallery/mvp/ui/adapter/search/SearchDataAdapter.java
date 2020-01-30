package com.mjgallery.mjgallery.mvp.ui.adapter.search;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;

import java.util.List;

public class SearchDataAdapter extends BaseQuickAdapter<DiscoveryDataBean, BaseViewHolder> {
    ISearchData mISearchData;

    public SearchDataAdapter(int layoutResId, @Nullable List<DiscoveryDataBean> data, ISearchData mISearchData) {
        super(layoutResId, data);
        this.mISearchData = mISearchData;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryDataBean item) {
        TextView tvSearchDrawingsItemTitle = helper.getView(R.id.tvSearchDrawingsItemTitle);
        LinearLayout llSearchDrawingsItemTitle = helper.getView(R.id.llSearchDrawingsItemTitle);
        tvSearchDrawingsItemTitle.setText(item.getName());
        llSearchDrawingsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mISearchData != null) {
                    mISearchData.onSearchDataClick(item.getId(), item.getName());
                }
            }
        });
    }

    public interface ISearchData {
        void onSearchDataClick(int id, String title);
    }
}
