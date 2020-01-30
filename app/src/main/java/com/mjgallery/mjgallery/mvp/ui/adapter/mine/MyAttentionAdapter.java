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
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAttentionItemBean;

import java.util.List;

public class MyAttentionAdapter extends BaseQuickAdapter<MyAttentionItemBean, BaseViewHolder> {
    ICancelAttention iCancelAttention;
    boolean isType = false;

    public MyAttentionAdapter(int layoutResId, @Nullable List<MyAttentionItemBean> data, ICancelAttention iCancelAttention, boolean isType) {
        super(layoutResId, data);
        this.iCancelAttention = iCancelAttention;
        this.isType = isType;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyAttentionItemBean item) {
        TextView tvSHOUZhiMingXi = helper.getView(R.id.tvSHOUZhiMingXi);
        TextView tvShouChang = helper.getView(R.id.tvShouChang);
        TextView tvWoDeFenSi = helper.getView(R.id.tvWoDeFenSi);
        TextView tvMyAttentionInviteCode = helper.getView(R.id.tvMyAttentionInviteCode);
        TextView tvMyAttentionUserName = helper.getView(R.id.tvMyAttentionUserName);
        TextView rlCancelAttentionText = helper.getView(R.id.rlCancelAttentionText);
        ImageView ivMyAttentionUserImg = helper.getView(R.id.ivMyAttentionUserImg);
        RelativeLayout rlCancelAttention = helper.getView(R.id.rlCancelAttention);
        LinearLayout llItemAll=helper.getView(R.id.llItemAll);
        tvSHOUZhiMingXi.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.a_secret));
        if (item.getPrivacy() != 0) {
            tvSHOUZhiMingXi.setText(item.getSysBrokerageAmount() + "");
        }
        tvShouChang.setText(item.getHeLike() + "");
        tvWoDeFenSi.setText(item.getHeFans() + "");
        tvMyAttentionInviteCode.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.invite_code) + item.getInviteCode());
        tvMyAttentionUserName.setText(item.getNickName());
        if (TextUtils.isEmpty(item.getHeadImg())) {
            GlideUtil.loadCircleImage(ivMyAttentionUserImg, R.drawable.mine_user_normal_img);
        } else {
            GlideUtil.loadCircleImage(ivMyAttentionUserImg, item.getHeadImg(), R.drawable.mine_user_normal_img);
        }
        if (isType) {
            rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.cancel_attention));

        } else {
            if (item.getType() == 3) {
                rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.has_mutual_concern));
            } else {
                rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.add_the_attention));
            }

        }
        rlCancelAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iCancelAttention != null) {
                    iCancelAttention.onCancelAttention(item.getUserId(), item.getType() );
                }
            }
        });

        llItemAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                iCancelAttention.onHisInformation(item.getUserId()+"");
            }
        });
    }

    public interface ICancelAttention {
        void onCancelAttention(int id, int type);
        void onHisInformation(String userId);
    }
}
