package com.mjgallery.mjgallery.mvp.ui.adapter.home;

import android.content.Context;
import android.graphics.Color;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.MuLuBean;

import java.util.List;

public class MuLuAdapter extends BaseQuickAdapter<MuLuBean, BaseViewHolder> {


    private Context context;

    public MuLuAdapter(Context context, int layoutResId, @Nullable List<MuLuBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MuLuBean item) {
        TextView tvSeCai = helper.getView(R.id.tvSeCai);
        LinearLayout llAll = helper.getView(R.id.llAll);
        TextView tvYear = helper.getView(R.id.tvYear);
        tvSeCai.setText(item.getTitle());
        tvYear.setText(item.getYear() + "");
        tvSeCai.setTextColor(Color.parseColor("#7E7E7E"));
        tvYear.setTextColor(Color.parseColor("#7E7E7E"));
        llAll.setBackgroundColor(Color.parseColor("#00000000"));
        if (item.isSelect()) {
            tvSeCai.setTextColor(Color.parseColor("#0093E8"));
            tvYear.setTextColor(Color.parseColor("#0093E8"));
            llAll.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(), R.drawable.table_select));
        }
    }
}
