package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;

import java.util.List;

public class DiscoveryDataListAdapter extends BaseQuickAdapter<DiscoveryDataBean, BaseViewHolder> {
    public DiscoveryDataListAdapter(int layoutResId, @Nullable List<DiscoveryDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryDataBean item) {
        TextView tvDiscoveryDataListTitle = helper.getView(R.id.tvDiscoveryDataListTitle);
        tvDiscoveryDataListTitle.setText(item.getName());
    }
}
