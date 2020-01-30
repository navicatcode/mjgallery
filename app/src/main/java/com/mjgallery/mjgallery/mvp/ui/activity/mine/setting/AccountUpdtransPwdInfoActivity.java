package com.mjgallery.mjgallery.mvp.ui.activity.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerAccountUpdtransPwdInfoComponent;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdtransPwdInfoContract;
import com.mjgallery.mjgallery.mvp.presenter.account.AccountUpdtransPwdInfoPresenter;
import com.mjgallery.mjgallery.widget.CountDownTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:修改交易密码
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 *
 * @author 鲁班
 * ================================================
 */
public class AccountUpdtransPwdInfoActivity extends MJBaseActivity<AccountUpdtransPwdInfoPresenter> implements AccountUpdtransPwdInfoContract.View {

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
    @BindView(R.id.etNewPwd1)
    EditText etNewPwd1;
    @BindView(R.id.ivShowStatus1)
    ImageView ivShowStatus1;
    @BindView(R.id.etNewPwd2)
    EditText etNewPwd2;
    @BindView(R.id.ivShowStatus2)
    ImageView ivShowStatus2;
    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;
    @BindView(R.id.tvCountDown)
    CountDownTextView tvCountDown;
    @BindView(R.id.etInputCode)
    EditText etInputCode;
    String mobile;
    //true为获取过验证码
    boolean sendCodeBool=false;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountUpdtransPwdInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_updtrans_pwd_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_trans_pwd1));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        mobile = getIntent().getExtras().getString("mobile");
        etPhoneNo.setText(mobile.substring(0,3) + "******"+mobile.substring(9));
        etPhoneNo.setEnabled(false);
        setCountDown();
        TextChangedListener();

    }


    /**
     * 设置输入框监听
     */
    public void TextChangedListener(){
        etNewPwd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwdStr=editable.toString().trim();
                if (!TextUtils.isEmpty(pwdStr) && pwdStr.length()>=6 && pwdStr.equals(etNewPwd2.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });

        etNewPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwdStr=editable.toString().trim();
                if (!TextUtils.isEmpty(pwdStr) && pwdStr.length()>=6 && pwdStr.equals(etNewPwd1.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });
    };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ivTopReturn,R.id.ivShowStatus1, R.id.ivShowStatus2,R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivShowStatus1:
                setPwdStatus(etNewPwd1,ivShowStatus1);
                break;
            case R.id.ivShowStatus2:
                setPwdStatus(etNewPwd2,ivShowStatus2);
                break;
            case R.id.tv_sure:

                if (etInputCode.getText().toString().length()<=0){
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
                map.put("password", etNewPwd1.getText().toString().trim());
                map.put("mobile", mobile);
                map.put("validateCode",etInputCode.getText().toString().trim());
                mPresenter.requestChangePayPassword(map);
                break;
        }
    }


    /**
     * 设置输入密码的状态可见与不可见
     */
    public void setPwdStatus(EditText et,ImageView iv){
        if(et.getInputType()== InputType.TYPE_CLASS_TEXT){
            iv.setImageResource(R.drawable.no_pwd);
            et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            et .setTransformationMethod(PasswordTransformationMethod.getInstance ());
        }else{
            //如果之前是不可见,改为可见
            iv.setImageResource(R.drawable.yes_pwd);
            et.setInputType(InputType. TYPE_CLASS_TEXT);
            et .setTransformationMethod(HideReturnsTransformationMethod.getInstance ());

        }
    }


    /**
     * 获取验证码定时器绑定
     */
    public void setCountDown(){
        tvCountDown.setNormalText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_phone6))
                .setCountDownText("", "s")
                .setCloseKeepCountDown(false)//关闭页面保持倒计时开关
                .setCountDownClickable(false)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setIntervalUnit(TimeUnit.SECONDS)
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
//                        Toast.makeText(MainActivity.this, "开始计时", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                    @Override
                    public void onTick(long untilFinished) {
//                        Log.e("------", "onTick: " + untilFinished);
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
//                        Toast.makeText(MainActivity.this, "倒计时完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", 15);
                        map.put("mobile", mobile);
                        //下面发送验证码
                        mPresenter.requestSendCode(map);

                    }
                });
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

    /**
     * 提交新交易密码后的回调
     * @param baseResponse
     */
    @Override
    public void onChangePayPassword(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.request_ok));
                finish();
            } else {
                showMessage(baseResponse.getMessage());
            }

        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }
}
