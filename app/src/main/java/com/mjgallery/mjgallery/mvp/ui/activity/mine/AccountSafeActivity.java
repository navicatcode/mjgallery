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

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.di.component.DaggerAccountSafeComponent;
import com.mjgallery.mjgallery.event.SavePhoneEvent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.account.AccountSafeContract;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.presenter.account.AccountSafePresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.setting.AccountUpdLoginPwdInfoActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.setting.AccountUpdPhoneInfoActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.setting.AccountUpdtransPwdInfoActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:账户安全界面
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * @author 鲁班
 * ================================================
 */
public class AccountSafeActivity extends MJBaseActivity<AccountSafePresenter> implements AccountSafeContract.View {

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
    @BindView(R.id.tvPhoneNo)
    TextView tvPhoneNo;
    @BindView(R.id.llUpdPhone)
    LinearLayout llUpdPhone;
    @BindView(R.id.llUpdLoginPwd)
    LinearLayout llUpdLoginPwd;
    @BindView(R.id.tvPwdStatus)
    TextView tvPwdStatus;
    @BindView(R.id.tvUpdateHint)
    TextView tvUpdateHint;
    @BindView(R.id.llUpdTransPwd)
    LinearLayout llUpdTransPwd;
    @BindView(R.id.llUpdWeiXin)
    LinearLayout llUpdWeiXin;
    @BindView(R.id.tvBanDingStatus)
    TextView tvBanDingStatus;
    @BindView(R.id.tvBanDingStatus2)
    TextView tvBanDingStatus2;
    @BindView(R.id.tvBanDingHint)
    TextView tvBanDingHint;
    @BindView(R.id.llUpdLiaoTanShi)
    LinearLayout llUpdLiaoTanShi;
    @BindView(R.id.llUpdateInfo)
    LinearLayout llUpdateInfo;
    String initString="未设置";
    UserInformation userInformation;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountSafeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_safe_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_account_safe1));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        initString=ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_account_safe8);
        Bundle bun=getIntent().getExtras();
        if(bun!=null){
            userInformation=(UserInformation)getIntent().getExtras().get("userInformation");
            if(userInformation!=null){
                String moblile=userInformation.getMobile();
                if(!TextUtils.isEmpty(moblile))
                    tvPhoneNo.setText(moblile);
                else
                    tvPhoneNo.setText(initString);

                //判断支付密码是否设置
                if(userInformation.getMsgPassword() == 0){
                    tvPwdStatus.setText(initString);
                    tvUpdateHint.setText(initString);
                }else{
                    tvPwdStatus.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_account_safe6));
                    tvUpdateHint.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_account_safe4));
                }
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
        finish();
    }

    @Override
    protected void requestData() {

    }



    @OnClick({R.id.ivTopReturn, R.id.llUpdPhone, R.id.llUpdLoginPwd, R.id.llUpdTransPwd,
            R.id.llUpdWeiXin, R.id.llUpdLiaoTanShi,R.id.llUpdateInfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.llUpdPhone:
                if(initString.equals(tvPhoneNo.getText().toString())){
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.bandi_phone_hint));

                }else if(tvPhoneNo.getText().toString().length()<10){
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.bandi_trans_pwd_hint2));
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putString("mobile",tvPhoneNo.getText().toString());
                    toOtherActivity(AccountUpdPhoneInfoActivity.class,bundle);
                }
                break;
            case R.id.llUpdLoginPwd:
                toOtherActivity(AccountUpdLoginPwdInfoActivity.class);
                break;
            case R.id.llUpdTransPwd:
                updateTransPwd();
                break;
            case R.id.llUpdWeiXin:
                break;
            case R.id.llUpdLiaoTanShi:
                break;
            case R.id.llUpdateInfo:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("gender", userInformation.getGender());
                bundle1.putString("headImg", userInformation.getHeadImg());
                bundle1.putString("nikeName", userInformation.getNikeName());
                bundle1.putString("birthday", userInformation.getBirthday());
                isToOtherActivity(PerfectInformationActivity.class, bundle1);
                break;
        }
    }

    private void updateTransPwd() {
        if (initString.equals(tvPwdStatus.getText().toString())) {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.bandi_trans_pwd_hint));
        }else{
            Bundle bundle2 = new Bundle();
            bundle2.putString("mobile", tvPhoneNo.getText().toString().trim());
            toOtherActivity(AccountUpdtransPwdInfoActivity.class, bundle2);
        }
    }

    /**
     * 重载toOtherActivity
     *
     * @param cls 跳转activity
     */
    protected <T> void toOtherActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(BaseApplication.getInstance(), cls);
        startActivity(intent);
    }



    @Subscriber(mode = ThreadMode.MAIN)
    public void onSavePhoneEvent(SavePhoneEvent savePhoneEvent) {
        if(!TextUtils.isEmpty(savePhoneEvent.getPhoneNuber()))
            tvPhoneNo.setText(savePhoneEvent.getPhoneNuber());
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onUpDateEvent(UpDateEvent upDateEvent) {
        EventBus.getDefault().post(new UpDateEvent());
        killMyself();
    }
}
