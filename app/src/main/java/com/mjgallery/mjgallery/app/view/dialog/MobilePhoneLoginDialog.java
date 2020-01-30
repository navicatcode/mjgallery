package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.PhoneFormatCheckUtils;
import com.mjgallery.mjgallery.event.CloseWXEvent;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class MobilePhoneLoginDialog extends BaseDialog implements View.OnClickListener {
    TextView tvForgotPassword;
    EditText etChangePasswordPhone;
    TextView tvChangePasswordPhone;
    EditText etChangePasswordOne;
    TextView tvChangePasswordPwdOne;
    Button btnLogin;
    RelativeLayout llAll,rlAllTwo;
    LinearLayout llBg;

    IMobilePhoneLogin iMobilePhoneLogin;
    boolean isJump=false;

    public MobilePhoneLoginDialog(Activity activity, IMobilePhoneLogin iMobilePhoneLogin) {
        super(activity);
        this.iMobilePhoneLogin = iMobilePhoneLogin;
        setCanceledOnTouchOutside(true);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_mobile_phone_login);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        llAll = findViewById(R.id.llAll);
        rlAllTwo = findViewById(R.id.rlAllTwo);
        llBg = findViewById(R.id.llBg);
        etChangePasswordPhone = findViewById(R.id.etChangePasswordPhone);
        tvChangePasswordPhone = findViewById(R.id.tvChangePasswordPhone);
        etChangePasswordOne = findViewById(R.id.etChangePasswordOne);
        tvChangePasswordPwdOne = findViewById(R.id.tvChangePasswordPwdOne);
        btnLogin = findViewById(R.id.btnLogin);
        llBg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        llAll.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        rlAllTwo.setOnClickListener(this);
        initTextChangedListener();

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBg:
            case R.id.rlAllTwo:
                break;
            case R.id.tvForgotPassword:
                if (iMobilePhoneLogin != null) {
                    isJump=true;
                    iMobilePhoneLogin.onLoginForgotPassword();
                }
                break;
            case R.id.btnLogin:
                if (TextUtils.isEmpty(etChangePasswordOne.getText().toString()) ||
                        TextUtils.isEmpty(etChangePasswordPhone.getText().toString())) {
                    ArmsUtils.makeText(getContext(),"请输入用户名和密码");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("password", etChangePasswordOne.getText().toString());
                    map.put("mobile", etChangePasswordPhone.getText().toString());
                    if (iMobilePhoneLogin != null) {
                        isJump=true;
                        iMobilePhoneLogin.onBtnLogin(map);
                    }
                }
                break;
            case R.id.llAll:
                if (iMobilePhoneLogin != null) {
                    iMobilePhoneLogin.onCloseMobilePhoneLoginDialog();
                }
                break;
        }
    }

    public void showDialog() {
        tvChangePasswordPwdOne.setVisibility(View.GONE);
        tvChangePasswordPhone.setVisibility(View.GONE);
        isJump=false;

        showTop();

    }


    private void initTextChangedListener() {
        etChangePasswordPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (PhoneFormatCheckUtils.isChinaPhoneLegal(s.toString())) {

                        tvChangePasswordPhone.setVisibility(View.GONE);
                    } else {
                        tvChangePasswordPhone.setVisibility(View.VISIBLE);
                    }
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
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().length() > 5) {

                        tvChangePasswordPwdOne.setVisibility(View.GONE);
                    } else {
                        tvChangePasswordPwdOne.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
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


    public interface IMobilePhoneLogin {
        void onLoginForgotPassword();

        void onBtnLogin(Map<String, Object> map);

        void onCloseMobilePhoneLoginDialog();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (!isJump) {
            if (iMobilePhoneLogin != null) {
                iMobilePhoneLogin.onCloseMobilePhoneLoginDialog();
            }
        }
    }
}
