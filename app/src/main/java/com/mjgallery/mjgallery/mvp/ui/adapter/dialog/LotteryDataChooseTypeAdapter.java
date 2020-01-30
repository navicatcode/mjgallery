package com.mjgallery.mjgallery.mvp.ui.adapter.dialog;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.LotteryDataChooseTypeBean;

import java.util.List;

public class LotteryDataChooseTypeAdapter extends BaseQuickAdapter<LotteryDataChooseTypeBean, BaseViewHolder> {
    public LotteryDataChooseTypeAdapter(int layoutResId, @Nullable List<LotteryDataChooseTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LotteryDataChooseTypeBean item) {
        TextView tvInvitedPlayersDialogItemTitle = helper.getView(R.id.tvInvitedPlayersDialogItemTitle);
        LinearLayout llInvitedPlayersDialogItem = helper.getView(R.id.llInvitedPlayersDialogItem);
        tvInvitedPlayersDialogItemTitle.setTextColor(ArmsUtils.getColor(helper.itemView.getContext(), R.color.color_0AAFFA));
        if(item.getLotteryDataChooseType()==5){
            llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_normal_buttom));
        }else if(item.getLotteryDataChooseType()==0){
            llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_normal_top));
        }else {
            llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_normal_content));
        }
        if (item.isSelect()) {
            if(item.getLotteryDataChooseType()==5){
                llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_buttom));
            }else if(item.getLotteryDataChooseType()==0){
                llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_top));
            }else {
                llInvitedPlayersDialogItem.setBackground(ArmsUtils.getDrawablebyResource(helper.itemView.getContext(), R.drawable.invited_players_search_content));
            }

            tvInvitedPlayersDialogItemTitle.setTextColor(ArmsUtils.getColor(helper.itemView.getContext(), R.color.white));
        }
        tvInvitedPlayersDialogItemTitle.setText(item.getLotteryDataChooseTypeTitle());
    }
}
