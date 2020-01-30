package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryDataListBean;

import java.util.List;

public class LotteryDataContentAdapter extends BaseQuickAdapter<LotteryDataListBean.LotteryDataBean, BaseViewHolder> {
    String typeName;

    public LotteryDataContentAdapter(int layoutResId, @Nullable List<LotteryDataListBean.LotteryDataBean> data, String typeName) {
        super(layoutResId, data);
        this.typeName = typeName;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LotteryDataListBean.LotteryDataBean item) {
        ImageView ivLotteryDataItem = helper.getView(R.id.ivLotteryDataItem);
        ivLotteryDataItem.setVisibility(View.GONE);
        if(helper.getLayoutPosition()>0){
            ivLotteryDataItem.setVisibility(View.VISIBLE);
        }
        TextView tvLotteryDataContentO1 = helper.getView(R.id.tvLotteryDataContentO1);
        TextView tvLotteryDataContentO2 = helper.getView(R.id.tvLotteryDataContentO2);
        TextView tvLotteryDataContentO3 = helper.getView(R.id.tvLotteryDataContentO3);
        String continuousPeriodUnopened = (item.getContinuousPeriodUnopened() < 10) ? ("0" + item.getContinuousPeriodUnopened()) : ("" + item.getContinuousPeriodUnopened());
        StringBuffer stringBufferOne = new StringBuffer();
        stringBufferOne.append(ArmsUtils.getString(helper.itemView.getContext(), R.string.lottery_data_item_content_01));
        stringBufferOne.append(continuousPeriodUnopened);
        stringBufferOne.append(ArmsUtils.getString(helper.itemView.getContext(), R.string.lottery_data_item_content_02));
        tvLotteryDataContentO1.setText(stringBufferOne.toString());
        StringBuffer stringBufferTwo = new StringBuffer();
        stringBufferTwo.append(ArmsUtils.getString(helper.itemView.getContext(), R.string.lottery_data_item_content_03));
        stringBufferTwo.append(item.getYear() + "");
        stringBufferTwo.append(ArmsUtils.getString(helper.itemView.getContext(), R.string.year));
        String preiodStr = (item.getLatestPeriod() < 10) ? ("0" + item.getLatestPeriod()) : ("" + item.getLatestPeriod());
        stringBufferTwo.append(preiodStr);
        stringBufferTwo.append(ArmsUtils.getString(helper.itemView.getContext(), R.string.lottery_data_item_content_04));
        tvLotteryDataContentO2.setText(stringBufferTwo.toString());
        tvLotteryDataContentO3.setText(item.getSx());
        if (item.getMaxPeriodUnopened() != null) {
            String preiod01 = (item.getMaxPeriodUnopened().getPeriod() < 10) ?
                    ("0" + item.getMaxPeriodUnopened().getPeriod()) : ("" + item.getMaxPeriodUnopened().getPeriod());
            helper.setText(R.id.tvLotteryDataContent03qishu, preiod01);
            helper.setText(R.id.tvLotteryDataContent03riqi,
                    item.getMaxPeriodUnopened().getBeginPeriod() +"-"+
                            item.getMaxPeriodUnopened().getEndPeriod());
        }
        if (item.getMaxPeriodContinuousOpen() != null) {
            String preiod02 = (item.getMaxPeriodContinuousOpen().getPeriod() < 10) ? ("0" + item.getMaxPeriodContinuousOpen().getPeriod()) : ("" + item.getMaxPeriodContinuousOpen().getPeriod());
            helper.setText(R.id.tvLotteryDataContent04qishu, preiod02);
            helper.setText(R.id.tvLotteryDataContent04riqi,
                    item.getMaxPeriodContinuousOpen().getBeginPeriod() +"-"+
                            item.getMaxPeriodContinuousOpen().getEndPeriod());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(helper.itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView mRecyclerView = helper.getView(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (item.getSxList() != null && item.getSxList().size() > 0) {
            LotteryDataContentItemTwoAdapter lotteryDataContentItemTwoAdapter =
                    new LotteryDataContentItemTwoAdapter(R.layout.lottery_data_item_content_item, item.getSxList());
            mRecyclerView.setAdapter(lotteryDataContentItemTwoAdapter);
        }
        if (item.getNumber() != null && item.getNumber().size() > 0) {
            LotteryDataContentItemOneAdapter lotteryDataContentItemOneAdapter =
                    new LotteryDataContentItemOneAdapter(R.layout.lottery_data_item_content_item, item.getNumber());
            mRecyclerView.setAdapter(lotteryDataContentItemOneAdapter);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemViewCacheSize(200);
        linearLayoutManager.setInitialPrefetchItemCount(10);
    }
}
