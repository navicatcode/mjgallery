package com.mjgallery.mjgallery.mvp.ui.adapter.dialog;

import android.graphics.Color;

import android.view.View;
import android.widget.RelativeLayout;
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

public class YearsAdapter extends BaseQuickAdapter<YearsBean, BaseViewHolder> {
    List<YearsBean> data;

    IBaseQuickAdapterClickListener iBaseQuickAdapterClickListener;

    public YearsAdapter(int layoutResId, @Nullable List<YearsBean> data, IBaseQuickAdapterClickListener iBaseQuickAdapterClickListener) {
        super(layoutResId, data);
        this.data = data;
        this.iBaseQuickAdapterClickListener = iBaseQuickAdapterClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, YearsBean item) {
        TextView tvYearBg = helper.getView(R.id.tvYearBg);
        RelativeLayout rlYearBb = helper.getView(R.id.rlYearBb);
        tvYearBg.setText(item.getYear());
        rlYearBb.setBackgroundColor(Color.parseColor("#00000000"));
        tvYearBg.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        int indexSelect = 0;
        for (int i = 0; i < data.size(); i++) {
            YearsBean yearsBean = data.get(i);
            if (yearsBean.isSelect()) {
                indexSelect = i;
                break;
            }
        }
        if (data.size() == 1) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_two));
        } else if (item.isSelect()) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_two));
        } else if (indexSelect == data.size() - 1 && item.getYear().equals(data.get(indexSelect - 1).getYear())) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_one));
        } else if (indexSelect == 0 && data.size() > 1 && item.getYear().equals(data.get(indexSelect + 1).getYear())) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_three));
        } else if (data.size() > 0 && indexSelect != 0 && data.get(indexSelect - 1).getYear().equals(item.getYear())) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_one));
        } else if (data.size() > 0 && indexSelect != 0 && data.size() > indexSelect + 1 && data.get(indexSelect + 1).getYear()
                .equals(item.getYear())) {
            rlYearBb.setBackground(ArmsUtils.getDrawablebyResource(BaseApplication.getInstance(),R.drawable.bg_select_three));
        }
        if (item.isSelect()) {
            tvYearBg.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0EB4FE));
        }
        rlYearBb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iBaseQuickAdapterClickListener != null) {
                    iBaseQuickAdapterClickListener.onClickListener(item);
                }
            }
        });
    }

    public interface IBaseQuickAdapterClickListener {
        void onClickListener(YearsBean item);
    }



}
