package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SPUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.app.view.dialog.BillingInstructionsDialog;
import com.mjgallery.mjgallery.app.view.dialog.InvitedPlayersDialog;
import com.mjgallery.mjgallery.app.view.dialog.WithdrawDepositDialog;
import com.mjgallery.mjgallery.di.component.DaggerMyWalletComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.MyWalletContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MeMyAccount;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.InvitedPlayersDialogBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.MyWalletPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawDepositStatusActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.mywallet.VIPRewardsFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.mywallet.VIPWithdrawFragment;
import com.mjgallery.mjgallery.widget.UIImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的钱包界面
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 *
 * @author 鲁班
 * ================================================
 */
public class MyWalletActivity extends MJBaseActivity<MyWalletPresenter> implements MyWalletContract.View,
        WithdrawDepositDialog.IWithdrawDepositListener, InvitedPlayersDialog.IInvitedPlayersSelect {


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
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvAccountBalance)
    TextView tvAccountBalance;
    @BindView(R.id.tvWithdraw)
    TextView tvWithdraw;
    @BindView(R.id.tvOrderWithdraw)
    TextView tvOrderWithdraw;
    @BindView(R.id.tvMineInstructions)
    UIImageView tvMineInstructions;
    @BindView(R.id.tvMethodPayment)
    TextView tvMethodPayment;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.btnFenXian)
    TextView btnFenXian;
    @BindView(R.id.llFenXian)
    LinearLayout llFenXian;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = new String[2];
    private MyPagerAdapter mAdapter;
    BillingInstructionsDialog billingInstructionsDialog;

    UserInformation userInformation;
    WithdrawDepositDialog wdDialog;
    InvitedPlayersDialog mInvitedPlayersDialog;
    public int timeType = 1;
    VIPRewardsFragment vipRewardsFragment;
    VIPWithdrawFragment vipWithdrawFragment;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyWalletComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_wallet; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_wallet));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        tvActivityNperStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.to_day));
        tvActivityNperStatus.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        GlideUtil.loadNormalImage(ivActivityNper, R.drawable.xiafang_img);
        billingInstructionsDialog = new BillingInstructionsDialog(this);
        wdDialog = new WithdrawDepositDialog(this, this);
        userInformation = (UserInformation) getIntent().getExtras().get("userInformation");
        mInvitedPlayersDialog = new InvitedPlayersDialog(this, this);

        mFragments.clear();
        mTitles[0] = ArmsUtils.getString(BaseApplication.getInstance(), R.string.wallet_vip_rewards);
        mTitles[1] = ArmsUtils.getString(BaseApplication.getInstance(), R.string.wallet_vip_withdraw);
        vipRewardsFragment=VIPRewardsFragment.newInstance();
        vipWithdrawFragment=VIPWithdrawFragment.newInstance();
        mFragments.add(vipRewardsFragment);
        mFragments.add(vipWithdrawFragment);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setTextUnselectColor(R.color.black);
        requestData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void requestData() {
        mPresenter.requestMeMyAccount(token);
    }


    @OnClick({R.id.ivTopReturn, R.id.tvWithdraw, R.id.btnFenXian, R.id.tvMineInstructions, R.id.rlActivityNperStatus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tvWithdraw:
                withdrawDeposit();
                break;
            case R.id.btnFenXian:
                mPresenter.externalStorage();
                break;
            case R.id.tvMineInstructions:
                if (billingInstructionsDialog != null && !billingInstructionsDialog.isShowing()) {
                    billingInstructionsDialog.show();
                }
                break;
            case R.id.ivActivityNper://做筛选处理,筛选的弹窗
            case R.id.rlActivityNperStatus:
                ivActivityNper.animate().setDuration(500).rotation(180).start();
                if (mInvitedPlayersDialog != null && !mInvitedPlayersDialog.isShowing()) {
                    mInvitedPlayersDialog.showBottomView(llAll, timeType);
                }
                break;
        }
    }

    /**
     * Event刷新钱包信息
     *
     * @param upDateEvent
     */
    @Subscriber(mode = ThreadMode.MAIN)
    public void onUpDateEvent(UpDateEvent upDateEvent) {
        requestData();
        if(!upDateEvent.isLoginBool){
            vipRewardsFragment.getListDate(timeType);
            vipWithdrawFragment.getListDate(timeType);
        }

    }

    @Override
    public void onMeMyAccount(BaseResponse<MeMyAccount> response) {
        MeMyAccount meMyAccount = response.getResult();
        tvAccountBalance.setText(meMyAccount.getUserBalanceAmount());
        tvOrderWithdraw.setText("¥" + meMyAccount.getPassWithdrawAmount());

        switch (meMyAccount.getSettlement()) {
            case 0:
                tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.is_not_set));
                break;
            case 1:
                tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.daily));
                break;
            case 2:
                tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.monthly_statement));
                break;
        }
        if (!TextUtils.isEmpty(meMyAccount.getMoneyCount()) ) {
            double moneyCount=Double.parseDouble(meMyAccount.getMoneyCount());
            if (moneyCount>=1000.0)
                llFenXian.setVisibility(View.VISIBLE);

        }else{
            llFenXian.setVisibility(View.GONE);
        }
    }


    //**------------------------筛选查询范围begin ----***

    /**
     * 选择查询范围
     *
     * @param invitedPlayersDialogBean
     */
    @Override
    public void onClickListenerSelect(InvitedPlayersDialogBean invitedPlayersDialogBean) {
        if (invitedPlayersDialogBean != null) {
            timeType = invitedPlayersDialogBean.getStatusType();
            tvActivityNperStatus.setText(invitedPlayersDialogBean.getInvitedPlayersStatus());
            invitedPlayersDialogDismiss();
            vipRewardsFragment.getListDate(timeType);
            vipWithdrawFragment.getListDate(timeType);
        }

    }


    private void invitedPlayersDialogDismiss() {
        if (mInvitedPlayersDialog != null && mInvitedPlayersDialog.isShowing()) {
            mInvitedPlayersDialog.dismiss();
        }
    }

    @Override
    public void onClickListenerDismiss() {
        invitedPlayersDialogDismiss();
        ivActivityNper.animate().setDuration(500).rotation(0).start();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


    }
    //------------------------筛选查询范围end ----


    //------------------------提现begin ----

    /**
     * 提现，点击提交请求的入口
     */
    protected void withdrawDeposit() {
        //判断是否登录，如果未登录则让他登录
        if (isLoginStatus()) {
            if (userInformation != null) {
                //如果提现资料已填完，刚进入下一步判断
                if (userInformation.getResultType() == 0) {
                    mPresenter.requestMeWithdrawMsg(token);
                } else if (userInformation.getResultType() == 1) {
                    //如果提现资料未填完，刚跳转填写资料页面
                    toOtherActivity(WithdrawActivity.class);
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.zhi_liao_hint));
                }
            } else {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.zhi_liao_error));
            }
        }
    }

    /**
     * 获取提现信息状态接口--回调结果
     *
     * @param response
     */
    @Override
    public void onMeWithdrawMsg(BaseResponse<WithdrawDepositBean> response) {
        if (response.getCode() == 0) {

            WithdrawDepositBean withdrawDepositBean = response.getResult();
            //如果是审核状态或未读结果，刚跳转到审核进度页面
            if (withdrawDepositBean != null && (withdrawDepositBean.getVerifyResultStatus() == 0 || withdrawDepositBean.getMsg() == 0)) {

                skipWithdrawDepositStatus(withdrawDepositBean);
            } else {
                //如果是提现完成或失败，且已读信息下，或者第一次提现没有任何提现记录下，弹出输入提现金额的dialog
                wdDialog.showDialog(userInformation);
            }
        } else {
            showMessage(response.getMessage());
        }

    }

    /**
     * 跳转到提现进度状态Activity
     *
     * @param withdrawDepositBean
     */
    public void skipWithdrawDepositStatus(WithdrawDepositBean withdrawDepositBean) {
        if (wdDialog != null) {
            wdDialog.etClear();
            wdDialog.dismiss();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("verifyResultStatus", withdrawDepositBean.getVerifyResultStatus());
        bundle.putString("formatTime", withdrawDepositBean.getFormatTime());
        bundle.putString("orderNo", withdrawDepositBean.getOrderNo());
        toOtherActivity(WithdrawDepositStatusActivity.class, bundle);
    }

    /**
     * 提交要提现的金额--发送请求
     *
     * @param password
     * @param money
     */
    @Override
    public void onSubmit(String password, String money) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("password", password);
        map.put("money", money);
        mPresenter.requestMeWithdraw(map);
    }

    @Override
    public void onShowMessage(String showMessage) {
        ArmsUtils.makeText(this, showMessage);
    }

    /**
     * 提现金额接口--回调结果
     */
    @Override
    public void onMeWithdraw(BaseResponse<WithdrawDepositBean> response) {
        if (response.getResult() != null && response.getCode() == 0) {

            WithdrawDepositBean withdrawDepositBean = response.getResult();
            skipWithdrawDepositStatus(withdrawDepositBean);
        } else {
            ArmsUtils.makeText(this, TW2CN.getInstance(this).toLocalStringS2T(response.getMessage()));
        }

    }
    //------------------------提现end ----

    //------------------------分享begin ----

    @Override
    public void onDoShare() {
        String shareUrl = SPUtils.getInstance().getString("shareUrl", "http://www.xgmj.com/");
        ShareUtils.doShare(this, shareUrl,false);
    }


    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }
    //分享end ----

}
