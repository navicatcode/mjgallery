package com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.WithdrawResultInterFace;
import com.mjgallery.mjgallery.di.component.DaggerWthdrawalComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawContract;
import com.mjgallery.mjgallery.mvp.presenter.withdraw.WithdrawPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.FmPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw.WithdrawPasswordFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw.WithdrawPayFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw.WithdrawSettlementFragment;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;



/**
 * ================================================
 * Description:提现资料
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * @author 鲁班
 */

public class WithdrawActivity extends MJBaseActivity<WithdrawPresenter> implements WithdrawContract.View, WithdrawResultInterFace {

    private static final String TAG = "WithdrawActivity";
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
    @BindView(R.id.tb_withdraw)
    SlidingTabLayout mTbWithdraw;
    @BindView(R.id.vp_withdraw)
    ViewPager mVpWithdraw;
    private FmPagerAdapter pagerAdapter;
    private String[] titles = new String[]{ArmsUtils.getString(BaseApplication.getInstance(),R.string.method_payment),
            ArmsUtils.getString(BaseApplication.getInstance(),R.string.set_payment_method),
            ArmsUtils.getString(BaseApplication.getInstance(),R.string.trade_password)};
    private ArrayList<Fragment> mFragments;
    FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWthdrawalComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wthdrawal; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdrawal));
        tvTopTitle.setTextColor(getResources().getColor(R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        initTabLayout();

    }

    private void initTabLayout() {

        mFragments = new ArrayList<>();
        mFragments.add(WithdrawSettlementFragment.newInstance(this));
        mFragments.add(WithdrawPayFragment.newInstance(this));
        mFragments.add(WithdrawPasswordFragment.newInstance(this));
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragments, titles);
        mVpWithdraw.setAdapter(fragmentPagerAdapter);
        mTbWithdraw.setViewPager(mVpWithdraw);
        mTbWithdraw.setDividerColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_F2F2F2));
        mTbWithdraw.setDividerWidth(1);
        mTbWithdraw.setDividerPadding(6f);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        EventBus.getDefault().post(new UpDateEvent(true));
        finish();
    }

    @Override
    protected void requestData() {

    }


    @OnClick({R.id.ivTopReturn, R.id.ivTopRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivTopRight:
                break;
        }
    }


    @Override
    public void onWithdrawResultMsg(BaseResponse<String> baseResponse) {
        if (baseResponse.getCode() == 0) {
            if("0".equals(baseResponse.getResult())) {
                showMessage(this.getString(R.string.submit_ok));
                if (mTbWithdraw.getCurrentTab() == 0)
                    mTbWithdraw.setCurrentTab(1);
                else if (mTbWithdraw.getCurrentTab() == 1)
                    mTbWithdraw.setCurrentTab(2);
            }else{
                showMessage(this.getString(R.string.tiXieZiLiao));
                killMyself();
            }

        }else{
            showMessage(this.getString(R.string.submit_error));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WithdrawSettlementFragment.wrResultInfter=null;
        WithdrawPasswordFragment.wrResultInfter=null;
        WithdrawPayFragment.wrResultInfter=null;
    }
}
