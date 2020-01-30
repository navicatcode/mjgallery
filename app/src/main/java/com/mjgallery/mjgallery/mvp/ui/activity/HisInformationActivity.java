package com.mjgallery.mjgallery.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerHisInformationComponent;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationContract;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;
import com.mjgallery.mjgallery.mvp.presenter.hisinformation.HisInformationPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.CommentInformationFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.HisInformationDynamicFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.HisInformationLikeFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.HisInformationWorksFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:个人信息
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HisInformationActivity extends MJBaseActivity<HisInformationPresenter> implements HisInformationContract.View {

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
    @BindView(R.id.ivMineUserImg)
    ImageView ivMineUserImg;
    @BindView(R.id.tvHisInformationUserName)
    TextView tvHisInformationUserName;
    @BindView(R.id.tvHisInformationStatus)
    TextView tvHisInformationStatus;
    @BindView(R.id.tvInvitationCode)
    TextView tvInvitationCode;
    @BindView(R.id.tvLikeNumber)
    TextView tvLikeNumber;
    @BindView(R.id.tvSettlementMethod)
    TextView tvSettlementMethod;
    @BindView(R.id.ivHisInformationQRCode)
    ImageView ivHisInformationQRCode;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tvJieSuan)
    TextView tvJieSuan;
    @BindView(R.id.rlCancelAttentionText)
    TextView rlCancelAttentionText;
    @BindView(R.id.rlCancelAttention)
    RelativeLayout rlCancelAttention;
    boolean isAttention = false;
    @BindView(R.id.tvHisInformationLikeNumber)
    TextView tvHisInformationLikeNumber;
    @BindView(R.id.tvHisInformationFans)
    TextView tvHisInformationFans;
    @BindView(R.id.tvVipLevelNumber)
    TextView tvVipLevelNumber;
    @BindView(R.id.tvVipLevelTxt)
    TextView tvVipLevelTxt;
    @BindView(R.id.llVipLevel)
    LinearLayout llVipLevel;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    String userId;
    private final String[] mTitles = {
            ArmsUtils.getString(BaseApplication.getInstance(),R.string.dynamic), ArmsUtils.getString(BaseApplication.getInstance(),R.string.ping_lung_txt),
            ArmsUtils.getString(BaseApplication.getInstance(),R.string.like)
    };
    FragmentPagerAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHisInformationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_his_information; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.his_information));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        tvHisInformationStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            if (!TextUtils.isEmpty(userId)) {
                isLogin();
            }
            String userIdStr = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
            if(userId.equals(userIdStr)){
                rlCancelAttention.setVisibility(View.GONE);
                tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.personal_information));
            }

        }
        mFragments.clear();
        mFragments.add(HisInformationWorksFragment.newInstance(userId));
        mFragments.add(CommentInformationFragment.newInstance(userId));
        mFragments.add(HisInformationLikeFragment.newInstance(userId));
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);
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
        if (message == null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(), message);
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
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("he", userId);
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        mPresenter.requestData(map);
    }

    @Override
    public void onHerMessage(HisInformationBean hisInformationBean) {
        if (hisInformationBean != null) {
            tvHisInformationUserName.setText(hisInformationBean.getNikeName());
            tvInvitationCode.setText(hisInformationBean.getInviteCode());
            tvLikeNumber.setText(hisInformationBean.getFans() + "");
            GlideUtil.loadCircleImage(ivMineUserImg, hisInformationBean.getHeadImg(), R.drawable.mine_user_normal_img);
            Obj obj=hisInformationBean.getObj();
            if(obj!=null && obj.getCode()>0){
                tvVipLevelNumber.setText("V"+obj.getCode());
                tvVipLevelTxt.setText(obj.getVipTitle());
                llVipLevel.setVisibility(View.VISIBLE);
            }else{
                llVipLevel.setVisibility(View.INVISIBLE);
            }
            switch (hisInformationBean.getType()) {
                case 0:
                case 2:
                    isAttention = false;
                    rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.focus_on_one));
                    tvHisInformationStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.he_paid_attention_me));
                    tvHisInformationStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.paying_attention));
                    break;
                case 1:
                case 3:
                    isAttention = true;
                    rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.focus_on_two));
                    tvHisInformationStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.focus_each_other));
                    tvHisInformationStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.i_paid_attention_him));
                    break;


            }
            if (hisInformationBean.getPrivacy() == 0) {
                tvJieSuan.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_monthly_receipts) + hisInformationBean.getAmount() + "");
            } else {
                tvJieSuan.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_monthly_receipts));
            }
            tvHisInformationLikeNumber.setText(hisInformationBean.getMyConcern()+"");
            tvHisInformationFans.setText(hisInformationBean.getFans()+"");
            switch (hisInformationBean.getSettlement()) {
                case 0:
                    tvSettlementMethod.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.is_not_set));
                    break;
                case 1:
                    tvSettlementMethod.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.daily));
                    break;
                case 2:
                    tvSettlementMethod.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.monthly_statement));
                    break;
            }

            Bitmap mBitmap = CodeUtils.createImage(hisInformationBean.getInviteCode(), 56, 56, null);
            ivHisInformationQRCode.setImageBitmap(mBitmap);
        }
    }


    @Override
    public void onCancelUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                requestData();
            }
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                showMessage(baseResponse.getMessage());
            }

        }
    }

    @Override
    public void onConcernUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                requestData();
            }
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                showMessage(baseResponse.getMessage());
            }

        }
    }


    @OnClick({R.id.ivTopReturn, R.id.ivTopTitle, R.id.rlTop, R.id.rlCancelAttention})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivTopTitle:
                break;
            case R.id.rlCancelAttention:
                onPetElvesDetailsFocusOnClick();
                break;
        }
    }


    private void onPetElvesDetailsFocusOnClick() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        if (isAttention) {
            map.put("id", userId);
            mPresenter.getCancelUser(map);
        } else {
            map.put("userId", userId);
            mPresenter.getConcernUser(map);
        }

    }


}
