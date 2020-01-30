package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.content.Context;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountBean;

import java.util.List;

public class MyAccountAdapter extends BaseQuickAdapter<MyAccountBean, BaseViewHolder> {
    private Context mContext;

    IMyAccountClick iMyAccountClick;

    public MyAccountAdapter(int layoutResId, @Nullable List<MyAccountBean> data, Context context, IMyAccountClick iMyAccountClick) {
        super(layoutResId, data);
        this.mContext = context;
        this.iMyAccountClick = iMyAccountClick;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyAccountBean item) {
        LinearLayout llMyAccountYear = helper.getView(R.id.llMyAccountYear);
        TextView tvMyAccountYear = helper.getView(R.id.tvMyAccountYear);
        TextView tvTotalSpending = helper.getView(R.id.tvTotalSpending);
        TextView tvTotalRevenue = helper.getView(R.id.tvTotalRevenue);
        RecyclerView mRecyclerViewMyAccountItem = helper.getView(R.id.mRecyclerViewMyAccountItem);
        tvTotalRevenue.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_adapter_yitle01) + item.getTotalRevenue());
        tvTotalSpending.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_account_adapter_yitle02)  + item.getTotalSpending());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyAccountItemAdapter myAccountItemAdapter = new MyAccountItemAdapter(R.layout.adapter_my_account_item, item.getPaymentDetailsBeanList());
        mRecyclerViewMyAccountItem.setLayoutManager(linearLayoutManager);
        mRecyclerViewMyAccountItem.setAdapter(myAccountItemAdapter);
        tvMyAccountYear.setText(item.getYearsMonth().substring(item.getYearsMonth().length() - 2) + "月");   //截取);
        llMyAccountYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMyAccountClick!=null){
                    iMyAccountClick.onMyAccountYear();
                }
            }
        });

    }

    public interface IMyAccountClick {
        void onMyAccountYear();
    }
}
