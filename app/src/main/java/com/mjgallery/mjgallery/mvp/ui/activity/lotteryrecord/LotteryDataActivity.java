package com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.view.dialog.lotterydata.LotteryDataDialog;
import com.mjgallery.mjgallery.di.component.DaggerLotteryDataComponent;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryDataContract;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryDataListBean;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.LotteryDataChooseTypeBean;
import com.mjgallery.mjgallery.mvp.presenter.lottery.LotteryDataPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.lottery.LotteryDataAdapter;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:开奖数据
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LotteryDataActivity extends MJBaseActivity<LotteryDataPresenter> implements LotteryDataContract.View, LotteryDataDialog.ILotteryDataListener {

    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.tvActivityNperStatus)
    TextView tvActivityNperStatus;
    @BindView(R.id.ivActivityNper)
    UIImageView ivActivityNper;
    @BindView(R.id.rlActivityNperStatus)
    RelativeLayout rlActivityNperStatus;
    @BindView(R.id.llAll)
    LinearLayout llAll;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    LotteryDataDialog mLotteryDataDialog;
    int timeType = 0;
    List<LotteryDataListBean> lotteryDataListBeans;
    LotteryDataAdapter mLotteryDataAdapter;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLotteryDataComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lottery_data; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(getBaseContext(), R.string.lottery_data));
        tvTopTitle.setTextColor(ArmsUtils.getColor(getBaseContext(), R.color.color_0AAFFA));
        tvActivityNperStatus.setText(ArmsUtils.getString(getBaseContext(), R.string.all));
        mLotteryDataDialog=new LotteryDataDialog(this,this);
        timeType=0;
        lotteryDataListBeans = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setInitialPrefetchItemCount(50);
        mLotteryDataAdapter = new LotteryDataAdapter(R.layout.lottery_data_item_title, lotteryDataListBeans);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mLotteryDataAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemViewCacheSize(200);
        getLotteryData();
    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.ivTopReturn, R.id.tvActivityNperStatus, R.id.ivActivityNper, R.id.rlActivityNperStatus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tvActivityNperStatus:
            case R.id.ivActivityNper:
            case R.id.rlActivityNperStatus:
                ivActivityNper.animate().setDuration(500).rotation(180).start();
                if(mLotteryDataDialog!=null && !mLotteryDataDialog.isShowing()){
                    mLotteryDataDialog.showBottomView(timeType);
                }
                break;
        }
    }

    @Override
    public void onLotteryDataSelect(LotteryDataChooseTypeBean lotteryDataChooseTypeBean) {
        if(lotteryDataChooseTypeBean!=null){
            timeType=lotteryDataChooseTypeBean.getLotteryDataChooseType();
            tvActivityNperStatus.setText(lotteryDataChooseTypeBean.getLotteryDataChooseTypeTitle());
            lotteryDataDialogDismiss();
            getLotteryData();
        }
    }

    @Override
    public void onClickListenerDismiss() {
        ivActivityNper.animate().setDuration(500).rotation(0).start();
        lotteryDataDialogDismiss();
    }


    private void lotteryDataDialogDismiss() {
        if (mLotteryDataDialog != null && mLotteryDataDialog.isShowing()) {
            mLotteryDataDialog.dismiss();
        }
    }

    @Override
    public void onLotteryData(List<LotteryDataListBean> listBeans) {
        lotteryDataListBeans.clear();
        if (listBeans != null && listBeans.size() > 0) {
            lotteryDataListBeans.addAll(listBeans);
        }
        if (mLotteryDataAdapter != null) {
            mLotteryDataAdapter.notifyDataSetChanged();
        }

    }

    private void getLotteryData() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", timeType);
        mPresenter.getLotteryData(map);
    }

    @Override
    protected void requestData() {

    }
}
