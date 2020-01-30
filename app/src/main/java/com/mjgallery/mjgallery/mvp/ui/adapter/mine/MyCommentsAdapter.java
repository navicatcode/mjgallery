package com.mjgallery.mjgallery.mvp.ui.adapter.mine;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyCommentsBean;

import java.util.List;

public class MyCommentsAdapter extends BaseQuickAdapter<MyCommentsBean, BaseViewHolder> {

    OnCommentsAdapterInterface onCommentsAdapterInterface;

    public MyCommentsAdapter(int layoutResId, @Nullable List<MyCommentsBean> data,OnCommentsAdapterInterface onCommentsAdapterInterface) {
        super(layoutResId, data);
        this.onCommentsAdapterInterface=onCommentsAdapterInterface;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyCommentsBean item) {
        TextView tvMyCommentsContent=helper.getView(R.id.tvMyCommentsContent);
        ImageView ivMyCommentsImg=helper.getView(R.id.ivMyCommentsImg);
        TextView tvMyCommentsMiaoshu=helper.getView(R.id.tvMyCommentsMiaoshu);
        TextView tvMyCommentsUserName=helper.getView(R.id.tvMyCommentsUserName);
        TextView tvMyCommentsUserTime=helper.getView(R.id.tvMyCommentsUserTime);
        ImageView ivMyCommentsUserImg=helper.getView(R.id.ivMyCommentsUserImg);
        TextView ivMyCommentsTitle=helper.getView(R.id.ivMyCommentsTitle);
        LinearLayout llMyCommentsContent=helper.getView(R.id.llMyCommentsContent);
        if (!TextUtils.isEmpty(item.getFilePath())) {
            GlideUtil.loadNormalImage(ivMyCommentsImg, item.getFilePath(), R.drawable.icon_loading);
        } else {

            GlideUtil.loadNormalImage(ivMyCommentsImg, R.drawable.icon_loading);
        }

        GlideUtil.loadCircleImage(ivMyCommentsUserImg,item.getHeadImg(),R.drawable.mine_user_normal_img);
        tvMyCommentsContent.setText(item.getContent());
        tvMyCommentsMiaoshu.setText(item.getContent());
        tvMyCommentsUserName.setText(item.getMyName());
        ivMyCommentsTitle.setText(item.getTitle());
        tvMyCommentsUserTime.setText(DateUtils.timeslashMyMessageime(item.getCommentTime()));

        ivMyCommentsUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommentsAdapterInterface.onImgOnclik();
            }
        });

        llMyCommentsContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentsAdapterInterface.onCommentsContentOnclik(item.getType(),item.getPicId());
            }
        });
    }


    public interface OnCommentsAdapterInterface{

        public void onImgOnclik();

        public void onCommentsContentOnclik(int type,int picId);
    }
}
