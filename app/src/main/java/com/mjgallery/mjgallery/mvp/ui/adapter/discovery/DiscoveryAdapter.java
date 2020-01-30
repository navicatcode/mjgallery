package com.mjgallery.mjgallery.mvp.ui.adapter.discovery;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;

import java.math.BigDecimal;
import java.util.List;

public class DiscoveryAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {
    private Context context;
    int actualItemWidth;
    public static final int TITLE_PAYLOAD = 899;
    public static final int CONTENT_PAYLOAD = 900;
    public static final int ITEM_0_PAYLOAD = 901;

    IDiscoveryClickListener iDiscoveryClickListener;


    protected boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public DiscoveryAdapter(Context context, int layoutResId, @Nullable List<HomeBean> data, IDiscoveryClickListener iDiscoveryClickListener, int actualItemWidth) {
        super(layoutResId, data);
        this.context = context;
        this.actualItemWidth = actualItemWidth;
        this.iDiscoveryClickListener = iDiscoveryClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean item) {
        CardView cardView = helper.getView(R.id.cardView);
        ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
        if (item.getBrowsingVolume() > 10000) {
            BigDecimal bigDecimalClickCount = new BigDecimal(item.getBrowsingVolume());
            BigDecimal bigDecimalChu = new BigDecimal(10000);
            float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
            helper.setText(R.id.tvItemChaKan,clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
        } else {
            helper.setText(R.id.tvItemChaKan,item.getBrowsingVolume() + "");
        }
        helper.setText(R.id.tvDiscoveryItemTitle,item.getPicName() + "");
        helper.setText(R.id.tvItemMessage,item.getPicComment() + "");
        helper.setText(R.id.tvItemDiscovery,item.getSupportCount() + "");
        ViewGroup.LayoutParams layoutParams = ivDiscoveryItemImg.getLayoutParams();
        int height = item.getSmallHeight();
        int width = item.getSmallWidth();
        layoutParams.height = (int) (actualItemWidth / (width * 1.0 / height));
        if (!TextUtils.isEmpty(item.getFilePathYs())) {
            // 这里可以用Glide等网络图片加载库
            GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getFilePathYs()
                    , R.drawable.icon_loading);
        } else {
            ivDiscoveryItemImg.setImageResource(R.drawable.icon_loading);
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


    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, HomeBean item, @NonNull List<Object> payloads) {
        for (Object p : payloads) {
            int payload = (int) p;
            if (payload == TITLE_PAYLOAD) {  // if only title change（如果标题变化了）
                helper.setText(R.id.tvDiscoveryItemTitle,item.getPicName() + "");
            } else if (payload == CONTENT_PAYLOAD) {  // if only content change（如果内容变化了）
                CardView cardView = helper.getView(R.id.cardView);
                ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
                helper.setText(R.id.tvItemChaKan,item.getBrowsingVolume() + "");
                helper.setText(R.id.tvItemMessage,item.getPicComment() + "");
                helper.setText(R.id.tvItemDiscovery,item.getSupportCount() + "");
                ViewGroup.LayoutParams layoutParams = ivDiscoveryItemImg.getLayoutParams();
                int height = item.getSmallHeight();
                int width = item.getSmallWidth();
                layoutParams.height = (int) (actualItemWidth / (width * 1.0 / height));
                if (!TextUtils.isEmpty(item.getFilePathYs())) {
                    // 这里可以用Glide等网络图片加载库
                    GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getFilePathYs()
                            , R.drawable.icon_loading);
                } else {
                    ivDiscoveryItemImg.setImageResource(R.drawable.icon_loading);
                }

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iDiscoveryClickListener != null) {
                            iDiscoveryClickListener.onDiscoveryItem(item);
                        }
                    }
                });
            } else if (payload == ITEM_0_PAYLOAD) {
                CardView cardView = helper.getView(R.id.cardView);
                ImageView ivDiscoveryItemImg = helper.getView(R.id.ivDiscoveryItemImg);
                helper.setText(R.id.tvDiscoveryItemTitle,item.getPicName() + "");
                helper.setText(R.id.tvItemChaKan,item.getBrowsingVolume() + "");
                helper.setText(R.id.tvItemMessage,item.getPicComment() + "");
                helper.setText(R.id.tvItemDiscovery,item.getSupportCount() + "");
                ViewGroup.LayoutParams layoutParams = ivDiscoveryItemImg.getLayoutParams();
                int height = item.getSmallHeight();
                int width = item.getSmallWidth();
                layoutParams.height = (int) (actualItemWidth / (width * 1.0 / height));
                if (!TextUtils.isEmpty(item.getFilePathYs())) {
                    // 这里可以用Glide等网络图片加载库
                    GlideUtil.loadNormalImage(ivDiscoveryItemImg, item.getFilePathYs()
                            , R.drawable.icon_loading);
                } else {
                    ivDiscoveryItemImg.setImageResource(R.drawable.icon_loading);
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
        }
    }

    public interface IDiscoveryClickListener {
        void onDiscoveryItem(HomeBean item);
    }
}
