package com.mjgallery.mjgallery.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;

import java.util.List;

public class SelectApplicableYearItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SelectApplicableYearItemAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        TextView tvYearItem = helper.getView(R.id.tvYearItem);
        String qishu = (Integer.valueOf(item) < 10) ? "0" + item : item;
        tvYearItem.setText("第" + qishu + "期");
    }
}
