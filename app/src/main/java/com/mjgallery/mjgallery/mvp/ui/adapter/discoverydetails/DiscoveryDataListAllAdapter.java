package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.language.CommSharedUtil;
import com.mjgallery.mjgallery.language.LanguageType;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataAllBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;

import java.util.List;

public class DiscoveryDataListAllAdapter extends BaseQuickAdapter<DiscoveryDataAllBean, BaseViewHolder> {
    public DiscoveryDataListAllAdapter(int layoutResId, @Nullable List<DiscoveryDataAllBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryDataAllBean item) {
        DiscoveryDataBean discoveryDataBean = item.getDiscoveryDataBean();
        TextView tvDiscoveryDataListTitle = helper.getView(R.id.tvDiscoveryDataListTitle);
        ImageView ivDiscoveryDataListOne = helper.getView(R.id.ivDiscoveryDataListOne);
        ImageView ivDiscoveryDataListTwo = helper.getView(R.id.ivDiscoveryDataListTwo);
        ivDiscoveryDataListOne.setVisibility(View.GONE);
        ivDiscoveryDataListTwo.setVisibility(View.GONE);
        int status = CommSharedUtil.getInstance(BaseApplication.getInstance()).getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_RW);
        tvDiscoveryDataListTitle.setText(discoveryDataBean.getName());
        if (discoveryDataBean.isHotInfo() && discoveryDataBean.isNewInfo()) {
            GlideUtil.loadNormalImage(ivDiscoveryDataListOne, R.drawable.latest_img);
            ivDiscoveryDataListOne.setVisibility(View.VISIBLE);
            if(status!=2){
                GlideUtil.loadNormalImage(ivDiscoveryDataListTwo, R.drawable.hottest_two_img);
            }else {
                GlideUtil.loadNormalImage(ivDiscoveryDataListTwo, R.drawable.hottest_img);
            }

            ivDiscoveryDataListTwo.setVisibility(View.VISIBLE);
        } else if (discoveryDataBean.isNewInfo() || discoveryDataBean.isHotInfo()) {
            ivDiscoveryDataListOne.setVisibility(View.VISIBLE);
            if (discoveryDataBean.isHotInfo()) {
                if(status!=2){
                    GlideUtil.loadNormalImage(ivDiscoveryDataListOne, R.drawable.hottest_two_img);
                }else {
                    GlideUtil.loadNormalImage(ivDiscoveryDataListOne, R.drawable.hottest_img);
                }

            } else {
                GlideUtil.loadNormalImage(ivDiscoveryDataListOne, R.drawable.latest_img);
            }
        }
    }
}
