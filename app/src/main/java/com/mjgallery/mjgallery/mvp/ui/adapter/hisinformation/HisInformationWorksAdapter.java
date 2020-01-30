package com.mjgallery.mjgallery.mvp.ui.adapter.hisinformation;


import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationWorksBean;

import java.math.BigDecimal;
import java.util.List;

public class HisInformationWorksAdapter extends BaseQuickAdapter<HisInformationWorksBean, BaseViewHolder> {

    int actualItemWidth;

    public HisInformationWorksAdapter(int layoutResId, @Nullable List<HisInformationWorksBean> data, int actualItemWidth) {
        super(layoutResId, data);
        this.actualItemWidth = actualItemWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, HisInformationWorksBean item) {
        if (item.getClickCount() > 10000) {
            BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
            BigDecimal bigDecimalChu = new BigDecimal(10000);
            float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
            helper.setText(R.id.tvItemChaKan, clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
        } else {
            helper.setText(R.id.tvItemChaKan, item.getClickCount() + "");
        }
        TextView tvItemMessage = helper.getView(R.id.tvItemMessage);
        TextView tvItemDiscovery = helper.getView(R.id.tvItemDiscovery);
        ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
        tvItemDiscovery.setText(item.getThumbUpCount() + "");
        tvItemMessage.setText(item.getCommentCount() + "");

        ViewGroup.LayoutParams layoutParams = ivDiscoveryItemImg.getLayoutParams();
        int height = item.getHeight();
        int width = item.getWidth();
        layoutParams.height = (int) (actualItemWidth / (width * 1.0 / height));
        GlideUtil.loadNormalImage(ivDiscoveryItemImg, R.drawable.icon_loading);
        if(!TextUtils.isEmpty(item.getFilePath())){
            Log.e("HisInformationWorksAdapter","HisInformationWorksAdapter==item.getFilePath()=="+item.getFilePath());
            GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getFilePath()
                    , R.drawable.icon_loading,
                    item.getWidth() / 2, item.getHeight() / 2);
        }

    }
}
