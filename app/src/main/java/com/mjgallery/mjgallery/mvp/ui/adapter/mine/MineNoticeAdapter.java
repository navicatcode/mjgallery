package com.mjgallery.mjgallery.mvp.ui.adapter.mine;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineNoticeBean;

import java.util.List;

/**
 * 个人中心的通知适配器
 */
public class MineNoticeAdapter extends BaseQuickAdapter<MineNoticeBean, BaseViewHolder> {
    IHisInformationClickListener iHisInformationClickListener;

    public MineNoticeAdapter(int layoutResId, @Nullable List<MineNoticeBean> data, IHisInformationClickListener iHisInformationClickListener) {
        super(layoutResId, data);
        this.iHisInformationClickListener = iHisInformationClickListener;

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineNoticeBean item) {
        TextView tvTitleNoticeOne = helper.getView(R.id.tvTitleNoticeOne);
        ImageView ivUserImg = helper.getView(R.id.ivUserImg);
        TextView tvNoticeTime = helper.getView(R.id.tvNoticeTime);
        RelativeLayout rlCommentContent = helper.getView(R.id.rlCommentContent);
        tvNoticeTime.setText(DateUtils.timedate(item.getCreateTime()));
        String notice = ArmsUtils.getString(BaseApplication.getInstance(),R.string.mine_notice_title);
        if (item.getAction() != 5) {
            notice = item.getNickname() + "：";
        }
        SpannableStringBuilder style = new SpannableStringBuilder(notice + item.getContent());
        style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0AAFFA)), 0,
                notice.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitleNoticeOne.setText(style);
        GlideUtil.imageNormalCircleLoader(ivUserImg, null, R.drawable.mine_user_normal_img);
        if(!TextUtils.isEmpty(item.getAvatarUrl())){
            GlideUtil.imageNormalCircleLoader(ivUserImg, item.getAvatarUrl(), R.drawable.mine_user_normal_img);
        }

        ivUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iHisInformationClickListener != null) {
                    iHisInformationClickListener.onHisInformation(String.valueOf(item.getSenderId()));
                }
            }
        });

        rlCommentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iHisInformationClickListener.getOnClickIntent(item.getRelatedType(),item.getRelatedId());
            }
        });
    }


    public interface IHisInformationClickListener {
        void onHisInformation(String userId);

        void getOnClickIntent(int relatedType,int relatedId);
    }
}
