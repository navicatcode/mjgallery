package com.mjgallery.mjgallery.mvp.ui.adapter.discovery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryTitleBean;

import java.util.List;

public class DiscoveryTitleAdapter extends BaseQuickAdapter<DiscoveryTitleBean, BaseViewHolder> {
    public DiscoveryTitleAdapter(int layoutResId, @Nullable List<DiscoveryTitleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryTitleBean item) {
        TextView tvDiscoveryTitle = helper.getView(R.id.tvDiscoveryTitle);
        TextView tvDiscoveryTitleOne = helper.getView(R.id.tvDiscoveryTitleOne);
        tvDiscoveryTitle.setVisibility(View.GONE);
        tvDiscoveryTitleOne.setVisibility(View.GONE);
        tvDiscoveryTitle.setText(item.getDiscoveryTitle());
        if (item.isPlacedTop()) {
            tvDiscoveryTitleOne.setVisibility(View.VISIBLE);
            tvDiscoveryTitleOne.setText(item.getDiscoveryTitle());
        } else {
            tvDiscoveryTitle.setVisibility(View.VISIBLE);
            tvDiscoveryTitle.setText(item.getDiscoveryTitle());
        }
    }
}
