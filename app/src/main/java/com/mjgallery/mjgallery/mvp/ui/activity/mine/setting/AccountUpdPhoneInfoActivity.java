package com.mjgallery.mjgallery.mvp.ui.activity.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PhoneFormatCheckUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerAccountUpdPhoneInfoComponent;
import com.mjgallery.mjgallery.event.SavePhoneEvent;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdPhoneInfoContract;
import com.mjgallery.mjgallery.mvp.presenter.account.AccountUpdPhoneInfoPresenter;
import com.mjgallery.mjgallery.widget.CountDownTextView;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:修改手机号
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 13:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 *
 * @author 鲁班
 * ================================================
 */
public class AccountUpdPhoneInfoActivity extends MJBaseActivity<AccountUpdPhoneInfoPresenter> implements AccountUpdPhoneInfoContract.View {

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
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tvCountDown)
    CountDownTextView tvCountDown;
    @BindView(R.id.etUserPhone)
    EditText etUserPhone;
    String mobile;//原先绑定的用户手机号码
    @BindView(R.id.tvPhoneHint)
    TextView tvPhoneHint;
    //手机号码类型，true为旧手机，false为新手机
    boolean phoneNoType = true;
    @BindView(R.id.etIdentifyingCode)
    EditText etIdentifyingCode;
    //true为获取过验证码
    boolean sendCodeBool=false;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountUpdPhoneInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_upd_phone_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_phone1));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        mobile = getIntent().getExtras().getString("mobile");
        etUserPhone.setText(mobile.substring(0,3) + "******"+mobile.substring(9));

        etUserPhone.setEnabled(false);
        tvCountDown.setNormalText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_phone6))
                .setCountDownText("", "s")
                .setCloseKeepCountDown(false)//关闭页面保持倒计时开关
                .setCountDownClickable(false)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setIntervalUnit(TimeUnit.SECONDS)
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
                    }
                })
                .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                    @Override
                    public void onTick(long untilFinished) {
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", 16);
                        if (phoneNoType) {
                            //获取旧手机号的验证码
                            map.put("mobile", mobile);
                        } else {
                            //获取新手机号的验证码
                            if (TextUtils.isEmpty(etUserPhone.getText().toString())
                                    || !PhoneFormatCheckUtils.isChinaPhoneLegal(etUserPhone.getText().toString())) {
                                showMessage("请输入正确的手机号码");
                                return;
                            }
                            map.put("mobile", etUserPhone.getText().toString());
                        }

                        //下面发送验证码
                        mPresenter.requestSendCode(map);

                    }
                });
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


    @OnClick({R.id.ivTopReturn, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tv_sure:

                if (etIdentifyingCode.getText().toString().length()<=0){
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_change_password_input_code));
                    return;
                }else if(!sendCodeBool){
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.get_send_code_hint));
                    return;
                }

                String token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        getString(AppConstants.TOKEN, "");

                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("mobile", etUserPhone.getText().toString());
                map.put("validateCode", etIdentifyingCode.getText().toString());
                if (phoneNoType) {
                    map.put("type", 0);
                    map.put("mobile", mobile);

                } else {
                    map.put("mobile", etUserPhone.getText().toString());
                    map.put("type", 1);
                }

                mPresenter.requestChangeModifyHone(map);
                break;
        }
    }

    @Override
    public void onSendCode(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                sendCodeBool=true;
                tvCountDown.startCountDown(60);
            } else {
                showMessage(baseResponse.getMessage());
            }

        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }

    }

    @Override
    public void onChangeModifyHone(BaseResponse baseResponse) {

        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                //如果是旧手机，清下UI,让客户输入新手机号码
                if (phoneNoType) {
                    phoneNoType = false;
                    etUserPhone.setText("");
                    etIdentifyingCode.setText("");
                    tvPhoneHint.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_phone4));
                    tvSure.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_login_pwd7));
                    tvCountDown.startCountDown(0);
                    etUserPhone.setEnabled(true);
                } else {
                    showMessage(baseResponse.getMessage());
                    EventBus.getDefault().post(new SavePhoneEvent(etUserPhone.getText().toString().trim()));
                    killMyself();
                }

            } else {
                showMessage(baseResponse.getMessage());
            }
        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }

    }
}
