package com.mjgallery.mjgallery.mvp.ui.adapter;


import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountUserBean;

import java.util.List;

public class MyAccountUserAdapter extends BaseQuickAdapter<MyAccountUserBean.ListBean, BaseViewHolder> {

    public MyAccountUserAdapter(int layoutResId, @Nullable List<MyAccountUserBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyAccountUserBean.ListBean item) {
        TextView tvType = helper.getView(R.id.tvType);
        TextView tvTime = helper.getView(R.id.tvTime);
        TextView tvMoney = helper.getView(R.id.tvMoney);
        tvMoney.setText(item.getAmount() + "");
        tvTime.setText(item.getTime());
        switch (item.getType()) {
//            0、未知 1、收入 2、支出 3、奖励
            case 0:
                tvType.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_item_01));
                break;
            case 1:
                tvType.setText(
                        ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_item_02));
                break;
            case 2:
                tvType.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_item_03));
                break;
            case 3:
                tvType.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_item_04));
                break;
        }
    }
}
