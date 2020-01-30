package com.mjgallery.mjgallery.mvp.ui.adapter.myvip;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.mvp.model.bean.mine.myvip.MyVIPMessageBean;

import java.util.List;

public class MyVIPMessageListAdapter extends BaseQuickAdapter<MyVIPMessageBean.ObjBean, BaseViewHolder> {
    public MyVIPMessageListAdapter(int layoutResId, @Nullable List<MyVIPMessageBean.ObjBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyVIPMessageBean.ObjBean item) {
        LinearLayout llMyVIPItemBg = helper.getView(R.id.llMyVIPItemBg);
        if ((helper.getLayoutPosition() + 1) % 2 == 0) {
            llMyVIPItemBg.setBackgroundColor(ArmsUtils.getColor(helper.itemView.getContext(), R.color.color_F2F2F2));
        } else {
            llMyVIPItemBg.setBackgroundColor(ArmsUtils.getColor(helper.itemView.getContext(), R.color.white));
        }
        helper.setText(R.id.tvVIPItem01, TW2CN.getInstance(mContext).toLocalStringS2T(item.getVipGrade()));
        helper.setText(R.id.tvVIPItem02, TW2CN.getInstance(mContext).toLocalStringS2T(item.getVipTitle()));
        helper.setText(R.id.tvVIPItem04, TW2CN.getInstance(mContext).toLocalStringS2T(item.getVipReward()));
        helper.setText(R.id.tvVIPItem03, item.getVipIntegral()+"");
        if (item.getIsRevice() == 0) {
            helper.setText(R.id.tvVIPItem05, ArmsUtils.getString(helper.itemView.getContext(), R.string.my_vip_lsit_item_01));
        } else {
            helper.setText(R.id.tvVIPItem05, ArmsUtils.getString(helper.itemView.getContext(), R.string.my_vip_lsit_item_02));
        }
    }
}
