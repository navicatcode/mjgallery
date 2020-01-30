package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;

import java.util.List;

public class LotteryDataContentItemTwoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LotteryDataContentItemTwoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tvQiuTitle, item);
    }
}
