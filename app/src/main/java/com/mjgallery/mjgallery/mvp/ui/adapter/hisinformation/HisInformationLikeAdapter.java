package com.mjgallery.mjgallery.mvp.ui.adapter.hisinformation;


import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationLikeBean;

import java.math.BigDecimal;
import java.util.List;

import static com.mjgallery.mjgallery.app.AppConstants.FOUND;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_DATA;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_VIDEO;
import static com.mjgallery.mjgallery.app.AppConstants.HOME;


public class HisInformationLikeAdapter extends BaseMultiItemQuickAdapter<HisInformationLikeBean, BaseViewHolder> {
    int actualItemWidth;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HisInformationLikeAdapter(List<HisInformationLikeBean> data, int actualItemWidth) {
        super(data);
        this.actualItemWidth = actualItemWidth;
        addItemType(FOUND_DATA, R.layout.adapter_hisinformation_like_data_item);
        addItemType(FOUND_VIDEO, R.layout.adapter_hisinformation_like_video_item);
        addItemType(FOUND, R.layout.adapter_hisinformation_like_item);
        addItemType(HOME, R.layout.adapter_hisinformation_like_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HisInformationLikeBean item) {
        switch (helper.getItemViewType()) {
            case FOUND:
            case HOME:
                if (item.getClickCount() > 10000) {
                    BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
                    BigDecimal bigDecimalChu = new BigDecimal(10000);
                    float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale
                            (1, BigDecimal.ROUND_DOWN).floatValue();
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
                if (!TextUtils.isEmpty(item.getFilePath())) {
                    GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getFilePath()
                            , R.drawable.icon_loading,
                            item.getWidth() / 2, item.getHeight() / 2);
                }
                break;
            case FOUND_VIDEO://视频暂时没有接入
                break;
            case FOUND_DATA://资料界面
                if (item.getClickCount() > 10000) {
                    BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
                    BigDecimal bigDecimalChu = new BigDecimal(10000);
                    float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                    helper.setText(R.id.tvItemChaKan, clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
                } else {
                    helper.setText(R.id.tvItemChaKan, item.getClickCount() + "");
                }
                TextView tvItemMessageData = helper.getView(R.id.tvItemMessage);
                TextView tvItemDiscoveryData = helper.getView(R.id.tvItemDiscovery);
                TextView tvDiscoveryItemTitleData = helper.getView(R.id.tvDiscoveryItemTitle);
                tvItemDiscoveryData.setText(item.getThumbUpCount() + "");
                tvItemMessageData.setText(item.getCommentCount() + "");
                tvDiscoveryItemTitleData.setText(item.getDescription());
                break;

        }
    }

//    public HisInformationLikeAdapter(int layoutResId, @Nullable List<HisInformationLikeBean> data, int actualItemWidth) {
//        super(layoutResId, data);
//        this.actualItemWidth = actualItemWidth;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HisInformationLikeBean item) {
//
//
//
//
//    }


}
