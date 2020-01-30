package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

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
import com.mjgallery.mjgallery.app.utils.PersonalGetTask;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.DailyTaskBean;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.List;

public class MyEverydayListAdapter extends BaseQuickAdapter<DailyTaskBean, BaseViewHolder> {

    PersonalGetTask personalGetTask;
    public MyEverydayListAdapter(int layoutResId, @Nullable List<DailyTaskBean> data, boolean isType,PersonalGetTask personalGetTask) {
        super(layoutResId, data);
        this.personalGetTask=personalGetTask;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DailyTaskBean item) {

        TextView tvTitle = helper.getView(R.id.tvTitle);
        TextView tvDescribe = helper.getView(R.id.tvDescribe);
        TextView rlCancelAttentionText = helper.getView(R.id.rlCancelAttentionText);
        ImageView ivStatusImg = helper.getView(R.id.ivStatusImg);
        UIImageView ivPersonalDialog = helper.getView(R.id.ivPersonalDialog);
        RelativeLayout rlCancelAttention=helper.getView(R.id.rlCancelAttention);

        //后台内容只有简体，如果当前app为繁体，则换成繁体
        tvDescribe.setText(TW2CN.getInstance(mContext).toLocalStringS2T(item.getContent()+""));
        String name= item.getRewardName();
        tvTitle.setText(TW2CN.getInstance(mContext).toLocalStringS2T(name+""));

        //由于只能文本做为判断，而后台传的文本只有简体，所以直接写死文本在代码中判断
        if("邀请奖励".equals(name)){
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.personal_type_04);
        }else if("阅读奖励".equals(name)){
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.personal_type_05);
        }else if("发帖奖励".equals(name)){
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.personal_type_06);
        }else if("分享奖励".equals(name)){
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.personal_type_08);
        }else{
            GlideUtil.loadCircleImage(ivStatusImg, R.drawable.personal_type_07);
        }

        int idDo=item.getIsDo();
        if(idDo==2){//已领取
            rlCancelAttention.setBackgroundResource(R.drawable.personal_type_btn);
            rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.to_receive));
            rlCancelAttentionText.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0EB4FE));
        }else {//立即领取
            rlCancelAttentionText.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
            rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.personal_immediately_receive));
            if(idDo==1){//立即领取
                rlCancelAttention.setBackgroundResource(R.drawable.item_oaaffa_bg);
            }else{//还没达到领取要求
                rlCancelAttention.setBackgroundResource(R.drawable.item_aeaeae_bg_click);
            }
        }


        rlCancelAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idDo==1){
                    personalGetTask.getTaskOnClick(item.getId(),name,false,helper.getLayoutPosition());
                }

            }
        });

        ivPersonalDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalGetTask.getShowDialog(name);
            }
        });
    }


}
