package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;

import java.util.List;

public class DiscoveryDataGridAdapter extends BaseQuickAdapter<DiscoveryDataBean, BaseViewHolder> {
    public DiscoveryDataGridAdapter(int layoutResId, @Nullable List<DiscoveryDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryDataBean item) {
        TextView tvDiscoveryDataTitle = helper.getView(R.id.tvDiscoveryDataTitle);
        tvDiscoveryDataTitle.setText(item.getName());
    }
}
