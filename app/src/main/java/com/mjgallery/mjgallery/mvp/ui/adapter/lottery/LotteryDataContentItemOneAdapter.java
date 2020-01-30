package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;

import java.util.List;

public class LotteryDataContentItemOneAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public LotteryDataContentItemOneAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        String continuousPeriodUnopened = (item < 10) ? ("0" + item) : ("" + item);
        helper.setText(R.id.tvQiuTitle, continuousPeriodUnopened);
    }
}
