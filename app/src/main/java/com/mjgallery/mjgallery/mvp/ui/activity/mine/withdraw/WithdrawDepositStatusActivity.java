package com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerWithdrawDepositStatusComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawDepositStatusContract;
import com.mjgallery.mjgallery.mvp.presenter.withdraw.WithdrawDepositStatusPresenter;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description:提现进度
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 21:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author 鲁班
 */
public class WithdrawDepositStatusActivity extends MJBaseActivity<WithdrawDepositStatusPresenter> implements WithdrawDepositStatusContract.View {

    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.ivTopTitle)
    ImageView ivTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.ivTopRight)
    TextView ivTopRight;
    @BindView(R.id.ivTopRightImg)
    ImageView ivTopRightImg;
    @BindView(R.id.btnQueDing)
    TextView btnQueDing;
    @BindView(R.id.rlQueDing)
    RelativeLayout rlQueDing;
    @BindView(R.id.ivRightHomeSearch)
    ImageView ivRightHomeSearch;
    @BindView(R.id.tvWithdrawDepositTime1)
    TextView tvWithdrawDepositTime1;
    @BindView(R.id.tvWithdrawDepositTime2)
    TextView tvWithdrawDepositTime2;
    @BindView(R.id.llWithdrawStatus1)
    LinearLayout llWithdrawStatus1;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.llWithdrawStatus2)
    LinearLayout llWithdrawStatus2;
    @BindView(R.id.llWithdrawStatus3)
    LinearLayout llWithdrawStatus3;
    @BindView(R.id.llWithdrawDepositBack)
    LinearLayout llWithdrawDepositBack;
    @BindView(R.id.tv_sure1)
    TextView tvSure1;
    @BindView(R.id.gifImgView)
    GifImageView gifImgView;
    int status=0;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawDepositStatusComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdraw_deposit_status; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_withdrawal_status));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        //提现状态，0审核中 1不通过 2审核通过
        status=getIntent().getExtras().getInt("verifyResultStatus");
        if (status==0){//如果是审核中
            String dateTime=getIntent().getExtras().getString("formatTime");
            tvWithdrawDepositTime1.setText(dateTime);
            tvWithdrawDepositTime2.setText(dateTime);
        }
        setShowUI(status);

    }

    /**
     * 显示提现的结果，所对应的UI效果
     * @param status
     */
    public void setShowUI(int status) {
        if (status == 0) {//提现中
            llWithdrawStatus1.setVisibility(View.VISIBLE);
            llWithdrawStatus2.setVisibility(View.GONE);
            llWithdrawStatus3.setVisibility(View.GONE);
            llWithdrawDepositBack.setBackgroundResource(R.drawable.tixie_back);
        } else {
            llWithdrawStatus1.setVisibility(View.GONE);
            llWithdrawDepositBack.setBackgroundResource(R.drawable.tixie_reluse);
            if (status == 1) {//提现失败
                llWithdrawStatus2.setVisibility(View.VISIBLE);
                llWithdrawStatus3.setVisibility(View.GONE);
            } else {//提现成功
                gifImgView.setBackgroundResource(R.drawable.withdraw_succeed_gif);
                llWithdrawStatus2.setVisibility(View.GONE);
                llWithdrawStatus3.setVisibility(View.VISIBLE);

                //控制gif只显示1.2秒左右
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        gifImgView.setVisibility(View.GONE);
                    }
                }, 1200);

            }
        }
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
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        dismissLoadingAnimationDialog();
        EventBus.getDefault().post(new UpDateEvent(false));
        finish();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivTopReturn, R.id.tv_sure,R.id.tv_sure1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                //如果是审核中，则直接返回，否则与下面和选项一点，进行网络请求
                if(status==0){
                   killMyself();
                   return;
                }
            case R.id.tv_sure:
            case R.id.tv_sure1:
                //发送已读取这条提现结果
                Map<String, Object> map = new HashMap<>();
                map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        getString("token", ""));
                map.put("msgStatus", 1);
                map.put("orderNo", getIntent().getExtras().getString("orderNo"));
                mPresenter.requestMeWithdraw(map);
                break;
        }
    }

    @Override
    public void onMeWithdrawUpMsg(BaseResponse response) {
        if (response != null && response.getCode()==0) {

        }else{
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
        killMyself();
    }

}
