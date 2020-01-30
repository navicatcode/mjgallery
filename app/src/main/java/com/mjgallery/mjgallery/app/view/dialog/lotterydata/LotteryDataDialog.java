package com.mjgallery.mjgallery.app.view.dialog.lotterydata;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.SystemUtil;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.LotteryDataChooseTypeBean;
import com.mjgallery.mjgallery.mvp.ui.adapter.dialog.LotteryDataChooseTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class LotteryDataDialog extends BaseDialog {
    RecyclerView mRecyclerView;
    List<LotteryDataChooseTypeBean> lotteryDataChooseTypeBeans;
    LotteryDataChooseTypeAdapter mLotteryDataChooseTypeAdapter;
    ILotteryDataListener mILotteryDataListener;

    public LotteryDataDialog(Activity activity, ILotteryDataListener mILotteryDataListener) {
        super(activity, R.style.MyDialogStyleBottomTransparent);
        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.mILotteryDataListener=mILotteryDataListener;
        initView();
        setCanceledOnTouchOutside(true);
    }

    private void initView() {
        setContentView(R.layout.dialog_lottery_data);
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        lotteryDataChooseTypeBeans = new ArrayList<>();
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.all), 0, false));
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.lottery_data_01), 1, false));
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.lottery_data_02), 2, false));
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.lottery_data_03), 3, false));
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.lottery_data_04), 4, false));
        lotteryDataChooseTypeBeans.add(new LotteryDataChooseTypeBean(ArmsUtils.getString(getContext(), R.string.lottery_data_05), 5, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLotteryDataChooseTypeAdapter = new LotteryDataChooseTypeAdapter(R.layout.invited_players, lotteryDataChooseTypeBeans );
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mLotteryDataChooseTypeAdapter);
        mLotteryDataChooseTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mILotteryDataListener != null && lotteryDataChooseTypeBeans.size() > position) {
                    mILotteryDataListener.onLotteryDataSelect(lotteryDataChooseTypeBeans.get(position));
                }
            }
        });
    }

    public void showBottomView(int status) {
        //小米的这个型号做特殊的处理
        if ("Xiaomi".equals(SystemUtil.getDeviceBrand()) && "MI 9".equals(SystemUtil.getSystemModel()) ) {
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("samsung".equals(SystemUtil.getDeviceBrand()) && "SM-A105G".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("HUAWEI".equals(SystemUtil.getDeviceBrand()) && "ELE-L29".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("HUAWEI".equals(SystemUtil.getDeviceBrand()) && "MAR-LX2".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else {
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 11);
        }
        for (int i = 0; i < lotteryDataChooseTypeBeans.size(); i++) {
            LotteryDataChooseTypeBean lotteryDataChooseTypeBean = lotteryDataChooseTypeBeans.get(i);
            if (lotteryDataChooseTypeBean.getLotteryDataChooseType() == status) {
                lotteryDataChooseTypeBean.setSelect(true);
            } else {
                lotteryDataChooseTypeBean.setSelect(false);
            }
        }
        if (mLotteryDataChooseTypeAdapter != null) {
            mLotteryDataChooseTypeAdapter.notifyDataSetChanged();
        }
        show();
    }


    public interface ILotteryDataListener {
        void onLotteryDataSelect(LotteryDataChooseTypeBean lotteryDataChooseTypeBean);

        void onClickListenerDismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mILotteryDataListener != null) {
            mILotteryDataListener.onClickListenerDismiss();
        }
    }
}
