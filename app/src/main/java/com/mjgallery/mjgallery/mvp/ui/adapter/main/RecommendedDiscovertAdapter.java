package com.mjgallery.mjgallery.mvp.ui.adapter.main;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeViewBean;

import java.math.BigDecimal;
import java.util.List;

public class RecommendedDiscovertAdapter extends BaseMultiItemQuickAdapter<HomeViewBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    IDiscoveryClickListener iDiscoveryClickListener;
    int actualItemWidth;

    public RecommendedDiscovertAdapter(List<HomeViewBean> data
            ,IDiscoveryClickListener iDiscoveryClickListener,
                                       int actualItemWidth) {
        super(data);
        //  0-图片，1-视频，2-资料
        this.iDiscoveryClickListener = iDiscoveryClickListener;
        this.actualItemWidth = actualItemWidth;
        addItemType(0, R.layout.discovery_view_item);
        addItemType(1, R.layout.discovery_view_vodeo_item);
        addItemType(2, R.layout.discovery_view_data_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeViewBean item) {
        ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
        switch (item.getType()) {
            case 0://图片布局
                ivDiscoveryItemImg.setVisibility(View.VISIBLE);
                CardView cardView = helper.getView(R.id.cardView);
                TextView tvDiscoveryItemTitle = helper.getView(R.id.tvDiscoveryItemTitle);
                if (item.getClickCount() > 10000) {
                    BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
                    BigDecimal bigDecimalChu = new BigDecimal(10000);
                    float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                    helper.setText(R.id.tvItemChaKan,clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
                } else {
                    helper.setText(R.id.tvItemChaKan,item.getClickCount() + "");
                }
                TextView tvItemMessage = helper.getView(R.id.tvItemMessage);
                TextView tvItemDiscovery = helper.getView(R.id.tvItemDiscovery);
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
                break;
            case 1://视频布局
                break;
            case 2://资料布局
                ivDiscoveryItemImg.setVisibility(View.GONE);
                CardView cardViewData = helper.getView(R.id.cardView);
                TextView tvDiscoveryItemTitleData = helper.getView(R.id.tvDiscoveryItemTitle);
                if (item.getClickCount() > 10000) {
                    BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
                    BigDecimal bigDecimalChu = new BigDecimal(10000);
                    float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                    helper.setText(R.id.tvItemChaKan,clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
                } else {
                    helper.setText(R.id.tvItemChaKan,item.getClickCount() + "");
                }
                TextView tvItemMessageData = helper.getView(R.id.tvItemMessage);
                TextView tvItemDiscoveryData = helper.getView(R.id.tvItemDiscovery);
                tvItemDiscoveryData.setText(item.getThumbUpCount() + "");
                tvItemMessageData.setText(item.getCommentCount() + "");
                tvDiscoveryItemTitleData.setText(item.getDescription() + "");
                cardViewData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iDiscoveryClickListener != null) {
                            iDiscoveryClickListener.onDiscoveryDataItem(item);
                        }
                    }
                });
                break;
        }
    }


    public interface IDiscoveryClickListener {
        void onDiscoveryItem(HomeViewBean item);

        void onDiscoveryDataItem(HomeViewBean item);
    }

}
