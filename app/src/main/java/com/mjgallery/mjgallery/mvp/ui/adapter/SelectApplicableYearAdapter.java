package com.mjgallery.mjgallery.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.YearsBean;

import java.util.List;

public class SelectApplicableYearAdapter extends BaseQuickAdapter<YearsBean, BaseViewHolder> {
    public SelectApplicableYearAdapter(int layoutResId, @Nullable List<YearsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, YearsBean item) {
        TextView tvSelectApplicableTitle=helper.getView(R.id.tvSelectApplicableTitle);
        TextView tvSelectApplicableTwoTitle=helper.getView(R.id.tvSelectApplicableTwoTitle);
        tvSelectApplicableTitle.setVisibility(View.GONE);
        tvSelectApplicableTwoTitle.setVisibility(View.GONE);
        tvSelectApplicableTitle.setText(item.getYear()+ ArmsUtils.getString(BaseApplication.getInstance(),R.string.year));
        tvSelectApplicableTwoTitle.setText(item.getYear()+ ArmsUtils.getString(BaseApplication.getInstance(),R.string.year));
        if(item.isSelect()){
            tvSelectApplicableTwoTitle.setVisibility(View.VISIBLE);
        }else {
            tvSelectApplicableTitle.setVisibility(View.VISIBLE);
        }
    }
}
