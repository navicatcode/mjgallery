package com.mjgallery.mjgallery.mvp.ui.activity.mine.myvip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveErrorDialog;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveSuccessDialog;
import com.mjgallery.mjgallery.di.component.DaggerMyVIPComponent;
import com.mjgallery.mjgallery.mvp.contract.mine.myvip.MyVIPContract;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.mine.myvip.MyVIPMessageBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;
import com.mjgallery.mjgallery.mvp.presenter.mine.myvip.MyVIPPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.myvip.MyVIPMessageListAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的VIP
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyVIPActivity extends BaseActivity<MyVIPPresenter> implements MyVIPContract.View {

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
    @BindView(R.id.ivUserImg)
    ImageView ivUserImg;
    @BindView(R.id.tvUserNickName)
    TextView tvUserNickName;
    @BindView(R.id.tvUserVIPLevel)
    TextView tvUserVIPLevel;
    @BindView(R.id.tvUserVIPTitle)
    TextView tvUserVIPTitle;
    @BindView(R.id.tvUserVIPGrowthValue)
    TextView tvUserVIPGrowthValue;
    @BindView(R.id.tvUserVIPConvertibleProportion)
    TextView tvUserVIPConvertibleProportion;
    @BindView(R.id.tvUserTopUpVIP)
    TextView tvUserTopUpVIP;
    @BindView(R.id.tvUserVIPLevelLeft)
    TextView tvUserVIPLevelLeft;
    @BindView(R.id.tvUserVIPLevelRight)
    TextView tvUserVIPLevelRight;
    @BindView(R.id.tvPromotionRewards)
    TextView tvPromotionRewards;
    @BindView(R.id.rlImmediatelyReceive)
    RelativeLayout rlImmediatelyReceive;
    MyVIPReceiveSuccessDialog mMyVIPReceiveSuccessDialog;
    MyVIPReceiveErrorDialog mMyVIPReceiveErrorDialog;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    UserInformation userInformation;
    @BindView(R.id.llTopReturnAll)
    RelativeLayout llTopReturnAll;
    @BindView(R.id.llUserVIP)
    LinearLayout llUserVIP;
    @BindView(R.id.mVIPLevelProgressBar)
    ProgressBar mVIPLevelProgressBar;
    @BindView(R.id.tvVIPLevelProgressBar)
    TextView tvVIPLevelProgressBar;
    MyVIPMessageListAdapter mMyVIPMessageListAdapter;
    List<MyVIPMessageBean.ObjBean> mMyVIPMessageBeanItems;
    MyVIPMessageBean mMyVIPMessageBean;
    @BindView(R.id.llPromotionRewards)
    LinearLayout llPromotionRewards;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyVIPComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_vip; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mMyVIPMessageBeanItems = new ArrayList<>();
        mMyVIPMessageListAdapter = new MyVIPMessageListAdapter(R.layout.my_vip_item, mMyVIPMessageBeanItems);
        tvTopTitle.setText(ArmsUtils.getString(getBaseContext(), R.string.mine_my_vip));
        tvTopTitle.setTextColor(ArmsUtils.getColor(getBaseContext(), R.color.white));
        GlideUtil.imageNormalLoader(ivTopReturn, null, R.drawable.activity_return_while_img);
        mMyVIPReceiveSuccessDialog = new MyVIPReceiveSuccessDialog(this);
        mMyVIPReceiveErrorDialog = new MyVIPReceiveErrorDialog(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPresenter.requestMyVip(PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMyVIPMessageListAdapter);
        onNewIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            userInformation = (UserInformation) intent.getSerializableExtra("userInformation");
            if (userInformation != null) {
                GlideUtil.imageNormalLoader(ivTopReturn, null, R.drawable.activity_return_while_img);
                GlideUtil.imageNormalCircleLoader(ivUserImg, userInformation.getHeadImg(), R.drawable.mine_user_normal_img);
                tvUserNickName.setText(userInformation.getNikeName());
                Obj userVip = userInformation.getObj();
                if (userVip != null) {
                    tvUserVIPLevelLeft.setText(userVip.getVipGrade());
                    if (userVip.getVipGrade().toLowerCase().equals("vip0")) {
                        llUserVIP.setVisibility(View.GONE);
                        llPromotionRewards.setVisibility(View.GONE);
                    } else {
                        setUserVIPLevel(userVip.getVipGrade());
                        setUserVIPTitle(Utils.getUserLevelName(userVip.getCode()));
                    }
                }
            }
        }
    }


    /**
     * 设置vip等级
     *
     * @param userVIPLevel
     */
    private void setUserVIPLevel(String userVIPLevel) {
        StringBuffer stringBufferVIPLevel = new StringBuffer();
        stringBufferVIPLevel.append(ArmsUtils.getString(getBaseContext(), R.string.vip_level));
        stringBufferVIPLevel.append(userVIPLevel);
        tvUserVIPLevel.setText(stringBufferVIPLevel.toString());
    }


    /**
     * 设置vip头衔
     */
    private void setUserVIPTitle(String userVIPTitle) {
        StringBuffer stringBufferVITittle = new StringBuffer();
        stringBufferVITittle.append(ArmsUtils.getString(getBaseContext(), R.string.vip_vip_title));
        stringBufferVITittle.append(userVIPTitle);
        tvUserVIPTitle.setText(stringBufferVITittle.toString());
    }


    /**
     * 设置成长值
     *
     * @param userVIPGrowthValue
     */
    private void setUserGrowthValue(String userVIPGrowthValue) {
        StringBuffer stringVIPGrowthValue = new StringBuffer();
        stringVIPGrowthValue.append(ArmsUtils.getString(getBaseContext(), R.string.vip_growth_value));
        stringVIPGrowthValue.append(userVIPGrowthValue);
        tvUserVIPGrowthValue.setText(stringVIPGrowthValue.toString());
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


    @OnClick({R.id.ivTopReturn, R.id.rlImmediatelyReceive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.rlImmediatelyReceive:
                if (mMyVIPMessageBean != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
                    map.put("rewardId", mMyVIPMessageBean.getId());
                    mPresenter.getMyVipMessage(map);
                }
                break;
        }
    }

    @Override
    public void onMyVip(MyVIPMessageBean myVIPMessageBean) {
        mMyVIPMessageBean = null;
        mMyVIPMessageBean = myVIPMessageBean;
        if (myVIPMessageBean != null) {
            setUserGrowthValue(mMyVIPMessageBean.getNext());
            tvUserVIPLevelRight.setText(mMyVIPMessageBean.getNextVipGrade());
            mVIPLevelProgressBar.setMax(Integer.parseInt(mMyVIPMessageBean.getNext()));
            mVIPLevelProgressBar.setProgress(Integer.parseInt(mMyVIPMessageBean.getNow()));
            setMyVIPMessageBean();
            setUserVIPLevelProgressBar(mMyVIPMessageBean);
            mMyVIPMessageBeanItems.clear();
            if (myVIPMessageBean.getObj() != null && myVIPMessageBean.getObj().size() > 0) {
                mMyVIPMessageBeanItems.addAll(myVIPMessageBean.getObj());
            }
            if (mMyVIPMessageListAdapter != null) {
                mMyVIPMessageListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onMyVipMessage(BaseResponse<MyVIPMessageBean> myVIPMessageBeanBaseResponse) {
        if (myVIPMessageBeanBaseResponse.getCode() == 0) {
            mMyVIPMessageBean = null;
            if (myVIPMessageBeanBaseResponse.getResult() != null) {
                mMyVIPMessageBean = myVIPMessageBeanBaseResponse.getResult();
                setMyVIPMessageBean();
                updateMyVIPMessageListAdapter(mMyVIPMessageBean.getNowVipGrade());
                if (mMyVIPReceiveSuccessDialog != null && !mMyVIPReceiveSuccessDialog.isShowing()) {
                    mMyVIPReceiveSuccessDialog.showMyVIPReceiveSuccessDialogTop(mMyVIPMessageBean.getNowVipReward(), 1);
                }

            }

        } else {
            if (mMyVIPReceiveErrorDialog != null && !mMyVIPReceiveErrorDialog.isShowing()) {
                mMyVIPReceiveErrorDialog.showMyVIPReceiveErrorDialogTop();
            }
        }
    }

    private void updateMyVIPMessageListAdapter(String vipLevel) {
        for (int i = 0; i < mMyVIPMessageBeanItems.size(); i++) {
            MyVIPMessageBean.ObjBean objBean = mMyVIPMessageBeanItems.get(i);
            if (objBean.getVipGrade().equals(vipLevel)) {
                objBean.setIsRevice(1);
                mMyVIPMessageBeanItems.set(i, objBean);
                continue;
            }
        }
        if (mMyVIPMessageListAdapter != null) {
            mMyVIPMessageListAdapter.notifyDataSetChanged();
        }
    }


    private void setMyVIPMessageBean() {
        if (mMyVIPMessageBean != null) {
            setPromotionRewards(mMyVIPMessageBean);
            rlImmediatelyReceive.setEnabled(mMyVIPMessageBean.getIsAvailable() == 0);
        }
    }


    /**
     * 设置进度条上面的进度和当前的百分比
     *
     * @param myVIPMessageBean
     */
    private void setUserVIPLevelProgressBar(MyVIPMessageBean myVIPMessageBean) {
        BigDecimal bigDecimalMax = new BigDecimal(myVIPMessageBean.getNext());
        BigDecimal bigDecimalProgress = new BigDecimal(myVIPMessageBean.getNow());
        BigDecimal percentage = bigDecimalProgress.divide(bigDecimalMax).setScale(2, BigDecimal.ROUND_DOWN);
        Log.e("setUserVIPLevelProgressBar=====", "percentage=====" + percentage);
        StringBuffer stringBufferProgressBar = new StringBuffer();
        stringBufferProgressBar.append(myVIPMessageBean.getNow());
        stringBufferProgressBar.append("/");
        stringBufferProgressBar.append(myVIPMessageBean.getNext());
        stringBufferProgressBar.append("(");
        stringBufferProgressBar.append(percentage.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).intValue());
        stringBufferProgressBar.append("%");
        stringBufferProgressBar.append(")");
        tvVIPLevelProgressBar.setText(stringBufferProgressBar.toString());
        //tvUserVIPConvertibleProportion.setText(ArmsUtils.getString(getBaseContext(), R.string.my_vip_title)+percentage.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).intValue()+"%");
    }

    private void setPromotionRewards(MyVIPMessageBean myVIPMessageBean) {
        if (userInformation != null && userInformation.getObj() != null) {
            Obj userVip = userInformation.getObj();
            StringBuffer stringBufferPromotionRewards = new StringBuffer();
            stringBufferPromotionRewards.append(ArmsUtils.getString(getBaseContext(), R.string.my_vip_title_01));
            stringBufferPromotionRewards.append(userInformation.getNikeName());
            stringBufferPromotionRewards.append(ArmsUtils.getString(getBaseContext(), R.string.my_vip_title_02));
            stringBufferPromotionRewards.append(ArmsUtils.getString(getBaseContext(), R.string.my_vip_title_03));
            stringBufferPromotionRewards.append(userVip.getVipGrade());
            stringBufferPromotionRewards.append("(");
            stringBufferPromotionRewards.append(Utils.getUserLevelName(userVip.getCode()));
            stringBufferPromotionRewards.append(")，");
            stringBufferPromotionRewards.append(ArmsUtils.getString(getBaseContext(), R.string.my_vip_title_04));
            if (myVIPMessageBean.getObj() != null && myVIPMessageBean.getObj().size() > 0) {
                stringBufferPromotionRewards.append(myVIPMessageBean.getRewardCommtent());
            } else {
                stringBufferPromotionRewards.append(myVIPMessageBean.getNextRewardCommtent());
            }

            stringBufferPromotionRewards.append("。");
            tvPromotionRewards.setText(stringBufferPromotionRewards.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
