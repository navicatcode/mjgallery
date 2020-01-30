package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryDataListBean;

import java.util.List;

public class LotteryDataAdapter extends BaseQuickAdapter<LotteryDataListBean, BaseViewHolder> {


    public LotteryDataAdapter(int layoutResId, @Nullable List<LotteryDataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LotteryDataListBean item) {
        helper.setText(R.id.tvLotteryDataTitle, item.getTypeName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(helper.itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LotteryDataContentAdapter lotteryDataContentAdapter = new LotteryDataContentAdapter(R.layout.lottery_data_item_content,
                item.getLotteryData(), item.getTypeName());
        RecyclerView mRecyclerView = helper.getView(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(lotteryDataContentAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemViewCacheSize(200);
        linearLayoutManager.setInitialPrefetchItemCount(20);
//        switch (helper.getItemViewType()) {
//            case 0:
//
//                break;
//            case 1:
//                break;
//        }
    }
}
