package com.mjgallery.mjgallery.mvp.ui.adapter.main;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeViewBean;

import java.math.BigDecimal;
import java.util.List;

public class DiscoveryViewAdapter extends BaseQuickAdapter<HomeViewBean, BaseViewHolder> {
    IDiscoveryClickListener iDiscoveryClickListener;
    int actualItemWidth;

    public DiscoveryViewAdapter(int layoutResId,
                                @Nullable List<HomeViewBean> data,
                                IDiscoveryClickListener iDiscoveryClickListener,
                                int actualItemWidth) {
        super(layoutResId, data);
        this.iDiscoveryClickListener = iDiscoveryClickListener;
        this.actualItemWidth = actualItemWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeViewBean item) {
        CardView cardView = helper.getView(R.id.cardView);
        TextView tvDiscoveryItemTitle = helper.getView(R.id.tvDiscoveryItemTitle);
        if (item.getClickCount() > 10000) {
            BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
            BigDecimal bigDecimalChu = new BigDecimal(10000);
            float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
            helper.setText(R.id.tvItemChaKan,clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
        } else {
            helper.setText(R.id.tvItemChaKan,item.getClickCount() + "");
        }
        TextView tvItemMessage = helper.getView(R.id.tvItemMessage);
        TextView tvItemDiscovery = helper.getView(R.id.tvItemDiscovery);
        ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
        tvItemDiscovery.setText(item.getThumbUpCount() + "");
        tvItemMessage.setText(item.getCommentCount() + "");
        tvDiscoveryItemTitle.setText(item.getDescription() + "");


        if (item.getShowPictureDo() != null && !TextUtils.isEmpty(item.getShowPictureDo().getFilePath())) {
            ViewGroup.LayoutParams layoutParams = ivDiscoveryItemImg.getLayoutParams();
            int height = item.getShowPictureDo().getHeight();
            int width = item.getShowPictureDo().getWidth();
            layoutParams.height = (int) (actualItemWidth / (width * 1.0 / height));
            GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getShowPictureDo().getFilePath()
                    , R.drawable.icon_loading);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryClickListener != null) {
                    iDiscoveryClickListener.onDiscoveryItem(item);
                }
            }
        });

    }

    public interface IDiscoveryClickListener {
        void onDiscoveryItem(HomeViewBean item);
    }
}
