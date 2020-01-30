package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.mvp.model.bean.VIPWithdrawListBean;

import java.util.List;

public class MyVIPWithdrawListAdapter extends BaseQuickAdapter<VIPWithdrawListBean, BaseViewHolder> {
    boolean isType;

    public MyVIPWithdrawListAdapter(int layoutResId, @Nullable List<VIPWithdrawListBean> data, boolean isType) {
        super(layoutResId, data);
        this.isType = isType;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VIPWithdrawListBean item) {
        TextView tvStatus = helper.getView(R.id.tvStatus);
        TextView tvDate = helper.getView(R.id.tvDate);
        TextView tvMoney = helper.getView(R.id.tvMoney);
        TextView tvOrderNo = helper.getView(R.id.tvOrderNo);
        ImageView ivStatusImg = helper.getView(R.id.ivStatusImg);

        tvDate.setText(item.getTime() + "");
        tvMoney.setText(item.getAmount() + "");


        if (isType) {//会员奖励
            if(item.getRewardName()!=null)
                tvStatus.setText("" + TW2CN.getInstance(mContext).toLocalStringS2T(item.getRewardName()));
            else
                tvStatus.setText("" + TW2CN.getInstance(mContext).toLocalStringS2T("其它收益"));
            tvOrderNo.setVisibility(View.GONE);
            tvMoney.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.color_FF5722));
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.mine_user_normal_img);

            if (item.getRewardCode() == 1)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_1);
            else if (item.getRewardCode() == 2)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_2);
            else if (item.getRewardCode() == 3)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_3);
            else if (item.getRewardCode() == 4)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_4);
            else if (item.getRewardCode() == 5)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_5);
            else if (item.getRewardCode() == 7)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_6);
            else if (item.getRewardCode() == 8)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_7);
            else if (item.getRewardCode() == 9)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_8);
            else if (item.getRewardCode() == 10)
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_9);
            else
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.vip_award_img_5);
            ;

        } else {//会员提现

            tvOrderNo.setText(item.getOrderNo());
            tvOrderNo.setVisibility(View.VISIBLE);

            if (item.getVerifyResultStatus() == 0) {//提现中
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.withdraw_status2);
                tvMoney.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.color_0AAFFA));
                tvStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.withdraw_status0));
            } else if (item.getVerifyResultStatus() == 2) {//提现成功
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.withdraw_status1);
                tvMoney.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.color_0AAFFA));
                tvStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.withdraw_status2));
            } else {//提现失败
                GlideUtil.loadCircleImage(ivStatusImg, R.drawable.withdraw_status3);
                tvMoney.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.colorBlack));
                tvStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.withdraw_status1));
            }
        }
    }

}
