package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.PhoneFormatCheckUtils;
import com.mjgallery.mjgallery.app.utils.SystemUtil;
import com.mjgallery.mjgallery.event.CloseWXEvent;
import com.mjgallery.mjgallery.widget.CountDownTextView;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;

/**
 * 手机号注册
 */
public class PhoneRegistratioDialog extends BaseDialog implements View.OnClickListener {
    EditText etChangePasswordPhone;
    CountDownTextView countDownTextView;
    TextView tvChangePasswordPhone;
    EditText etChangePasswordCode;
    EditText etChangePasswordOne;
    TextView tvChangePasswordPwdOne;
    EditText etChangePasswordTwo;
    TextView tvChangePasswordPwdTwo;
    EditText etInviteCode;
    TextView tvChangePasswordInviteCode;
    LinearLayout llAllTwo;
    TextView btnChangePassword;
    LinearLayout llAll;
    private String tokens;

    IPhoneRegistratio iPhoneRegistratio;

    public PhoneRegistratioDialog(@NonNull Activity context, IPhoneRegistratio iPhoneRegistratio) {
        super(context);
        this.iPhoneRegistratio = iPhoneRegistratio;
        init();
        setCanceledOnTouchOutside(true);
    }


    private void init() {
        setContentView(R.layout.dialog_phone_registratio);
        etChangePasswordPhone = findViewById(R.id.etChangePasswordPhone);
        tvChangePasswordPwdOne = findViewById(R.id.tvChangePasswordPwdOne);
        countDownTextView = findViewById(R.id.countDownTextView);
        tvChangePasswordPhone = findViewById(R.id.tvChangePasswordPhone);
        etChangePasswordCode = findViewById(R.id.etChangePasswordCode);
        etChangePasswordOne = findViewById(R.id.etChangePasswordOne);
        etChangePasswordTwo = findViewById(R.id.etChangePasswordTwo);
        tvChangePasswordPwdTwo = findViewById(R.id.tvChangePasswordPwdTwo);
        etInviteCode = findViewById(R.id.etInviteCode);
        tvChangePasswordInviteCode = findViewById(R.id.tvChangePasswordInviteCode);
        llAllTwo = findViewById(R.id.llAllTwo);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        llAll = findViewById(R.id.llAll);
        llAllTwo.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        llAll.setOnClickListener(this);
        btnChangePassword.setEnabled(false);
        setChangedListener();
        countDownTextView
                        .setNormalText("获取验证码")
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
                                countDownTextView
                                        .setNormalText("获取验证码")
                                        .setCountDownText("", "s");
                                codeCountDownTextViewEnabled();
                            }
                        }).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(etChangePasswordPhone.getText().toString()) ||
                                        !PhoneFormatCheckUtils.isChinaPhoneLegal(etChangePasswordPhone.getText().toString())) {
                                    ArmsUtils.makeText(getContext(),"电话号码不正确");
                                    return;
                                } else {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("mobile", etChangePasswordPhone.getText().toString());
                                    map.put("type", "11");
                                    countDownTextView.setEnabled(false);
                                    if (iPhoneRegistratio != null) {
                                        iPhoneRegistratio.onCompleteSendCode(map);
                                    }
                                }
            }
        });
    }


    public void show(String token) {
        tokens = token;
        tvChangePasswordPwdOne.setVisibility(View.INVISIBLE);
        tvChangePasswordPhone.setVisibility(View.INVISIBLE);
        tvChangePasswordPwdTwo.setVisibility(View.INVISIBLE);
        setBtnEnabled(false);
        showTop();

    }

    private void setBtnEnabled(boolean isEnabled) {
        btnChangePassword.setEnabled(isEnabled);
    }


    private void setChangedListener() {
        etChangePasswordPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (PhoneFormatCheckUtils.isChinaPhoneLegal(s.toString())) {
                        tvChangePasswordPhone.setVisibility(View.INVISIBLE);
                        if (!TextUtils.isEmpty(etChangePasswordCode.getText().toString())
                                && !TextUtils.isEmpty(etChangePasswordOne.getText().toString())
                                && !TextUtils.isEmpty(etChangePasswordTwo.getText().toString())
                                && etChangePasswordOne.getText().toString().length() >= 6
                                && etChangePasswordOne.getText().toString().
                                equals(etChangePasswordTwo.getText().toString())) {
                            setBtnEnabled(true);
                        } else {
                            setBtnEnabled(false);
                        }
                    } else {
                        tvChangePasswordPhone.setVisibility(View.VISIBLE);
                        setBtnEnabled(false);
                    }

                } else {
                    setBtnEnabled(false);
                }
            }
        });
        etChangePasswordCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (!TextUtils.isEmpty(etChangePasswordPhone.getText().toString())
                            && !TextUtils.isEmpty(etChangePasswordOne.getText().toString())
                            && !TextUtils.isEmpty(etChangePasswordTwo.getText().toString())
                            && PhoneFormatCheckUtils.isChinaPhoneLegal(etChangePasswordPhone.getText().toString())
                            && etChangePasswordOne.getText().toString().length() >= 6
                            && etChangePasswordOne.getText().toString().
                            equals(etChangePasswordTwo.getText().toString())) {
                        setBtnEnabled(true);
                    } else {
                        setBtnEnabled(false);
                    }


                } else {
                    setBtnEnabled(false);
                }
            }
        });
        etChangePasswordOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (s.toString().length() >= 6) {
                        tvChangePasswordPwdOne.setVisibility(View.INVISIBLE);
                        if (s.toString().equals(etChangePasswordTwo.getText().toString()) && !TextUtils.isEmpty(etChangePasswordTwo.getText().toString())) {
                            if (!TextUtils.isEmpty(etChangePasswordPhone.getText().toString())
                                    && !TextUtils.isEmpty(etChangePasswordCode.getText().toString()) &&
                                    PhoneFormatCheckUtils.isChinaPhoneLegal(etChangePasswordPhone.getText().toString())) {
                                setBtnEnabled(true);
                            } else {
                                setBtnEnabled(false);
                            }
                        } else {
                            tvChangePasswordPwdTwo.setVisibility(View.INVISIBLE);
                        }


                    } else {
                        tvChangePasswordPwdOne.setVisibility(View.VISIBLE);
                    }

                } else {
                    setBtnEnabled(false);
                }
            }
        });
        etChangePasswordTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (s.toString().length() >= 6 && s.toString().
                            equals(etChangePasswordOne.getText().toString())) {
                        tvChangePasswordPwdTwo.setVisibility(View.INVISIBLE);
                        if (!TextUtils.isEmpty(etChangePasswordPhone.getText().toString())
                                && !TextUtils.isEmpty(etChangePasswordCode.getText().toString())
                                && PhoneFormatCheckUtils.isChinaPhoneLegal(etChangePasswordPhone.getText().toString())) {
                            setBtnEnabled(true);
                        } else {
                            setBtnEnabled(false);
                        }

                    } else {
                        tvChangePasswordPwdTwo.setVisibility(View.VISIBLE);
                    }
                } else {
                    setBtnEnabled(false);
                }
            }
        });

    }

    @OnClick({R.id.llAllTwo, R.id.btnChangePassword, R.id.llAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llAllTwo:

                break;
            case R.id.btnChangePassword:
                Map<String, Object> map = new HashMap<>();
                map.put("password", etChangePasswordOne.getText().toString());
                map.put("mobile", etChangePasswordPhone.getText().toString());
                map.put("validateCode", etChangePasswordCode.getText().toString());
                map.put("inviteCode", etInviteCode.getText().toString());
                map.put("deviceId", SPUtils.getInstance().getString("deviceId"));
                map.put("model", SystemUtil.getDeviceName());
                map.put("osType", "1");
                if (iPhoneRegistratio != null) {
                    iPhoneRegistratio.onPhoneRegister(map);
                }
                break;
            case R.id.llAll:

                break;
        }
    }


    public interface IPhoneRegistratio {
        void onCompleteSendCode(Map<String, Object> map);

        void onPhoneRegister(Map<String, Object> map);

        void onCompleteRegistratioDismiss();
    }

    //验证码倒计时
    public void codeStart() {
        countDownTextView.startCountDown(60);
    }

    //验证码倒计时
    public void codeCountDownTextViewEnabled() {
        countDownTextView.setEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            EventBus.getDefault().post(new CloseWXEvent());
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (iPhoneRegistratio != null) {
            iPhoneRegistratio.onCompleteRegistratioDismiss();
        }
    }
}
