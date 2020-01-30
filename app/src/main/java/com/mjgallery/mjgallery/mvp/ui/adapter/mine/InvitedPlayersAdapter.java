package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mjgallery.mjgallery.mvp.model.bean.InvitedPlayersBean;

import java.util.List;

public class InvitedPlayersAdapter extends BaseQuickAdapter<InvitedPlayersBean, BaseViewHolder> {
    InvitedPlayersAdapterListener mInvitedPlayersAdapterListener;

    public InvitedPlayersAdapter(int layoutResId, @Nullable List<InvitedPlayersBean> data, InvitedPlayersAdapterListener mInvitedPlayersAdapterListener) {
        super(layoutResId, data);
        this.mInvitedPlayersAdapterListener = mInvitedPlayersAdapterListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InvitedPlayersBean item) {
        TextView tvSHOUZhiMingXi = helper.getView(R.id.tvSHOUZhiMingXi);
        TextView tvShouChang = helper.getView(R.id.tvShouChang);
        LinearLayout llItemAll = helper.getView(R.id.llItemAll);
        TextView tvWoDeFenSi = helper.getView(R.id.tvWoDeFenSi);
        TextView tvMyAttentionInviteCode = helper.getView(R.id.tvMyAttentionInviteCode);
        TextView tvMyAttentionUserName = helper.getView(R.id.tvMyAttentionUserName);
        ImageView ivMyAttentionUserImg = helper.getView(R.id.ivMyAttentionUserImg);
        RelativeLayout rlCancelAttention = helper.getView(R.id.rlCancelAttention);
        RelativeLayout rlAddAttention = helper.getView(R.id.rlAddAttention);
        tvSHOUZhiMingXi.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.a_secret));
        if (item.getPrivacy() != 0) {
            tvSHOUZhiMingXi.setText(item.getSysBrokerageAmount() + "");
        }
        tvShouChang.setText(item.getHeLike() + "");
        tvWoDeFenSi.setText(item.getHeFans() + "");
        tvMyAttentionInviteCode.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.invite_code) + item.getInviteCode());
        tvMyAttentionUserName.setText(item.getNickName());
        if (TextUtils.isEmpty(item.getHeadImg())) {
            GlideUtil.loadCircleImage(ivMyAttentionUserImg, R.drawable.mine_user_normal_img);
        } else {
            GlideUtil.loadCircleImage(ivMyAttentionUserImg, item.getHeadImg(), R.drawable.mine_user_normal_img);
        }
        rlCancelAttention.setVisibility(View.GONE);
        rlAddAttention.setVisibility(View.GONE);
        if (item.getType() == 3) {
            rlCancelAttention.setVisibility(View.VISIBLE);
        } else {
            rlAddAttention.setVisibility(View.VISIBLE);
        }
        rlCancelAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInvitedPlayersAdapterListener != null) {
                    mInvitedPlayersAdapterListener.onInvitedPlayersAdapterCancelAttentionClick(item);
                }
            }
        });
        rlAddAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInvitedPlayersAdapterListener != null) {
                    mInvitedPlayersAdapterListener.onInvitedPlayersAdapterAddAttentionClick(item);
                }
            }
        });
        llItemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInvitedPlayersAdapterListener != null) {
                    mInvitedPlayersAdapterListener.onInvitedSwitchClick(item.getUserId());
                }
            }
        });
    }


    public interface InvitedPlayersAdapterListener {
        void onInvitedPlayersAdapterAddAttentionClick(InvitedPlayersBean item);

        void onInvitedPlayersAdapterCancelAttentionClick(InvitedPlayersBean item);

        void onInvitedSwitchClick(int userID);

    }
}
