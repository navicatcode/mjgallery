package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.share.ShareFileUtils;
import com.mjgallery.mjgallery.event.CloseWXEvent;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.EventBus;

import butterknife.OnClick;

/**
 * 登陆的弹窗
 */
public class LoginDialog extends BaseDialog implements View.OnClickListener {
    LinearLayout rlLogin;
    LinearLayout llAll;
    TextView tvPhoneLogin;
    TextView tvForgotPassword;
    LinearLayout llAllLogin;
    private IWXAPI api;
    private boolean isJump = false;//是否跳转
    ILoginDialogClickListener iLoginDialogClickListener;

    public LoginDialog(Activity activity, ILoginDialogClickListener iLoginDialogClickListener) {
        super(activity);
        init();
        this.iLoginDialogClickListener = iLoginDialogClickListener;
        regToWx();
        setCanceledOnTouchOutside(true);
    }

    private void init() {
        setContentView(R.layout.dialog_login);
        rlLogin = findViewById(R.id.rlLogin);
        llAll = findViewById(R.id.llAll);
        tvPhoneLogin = findViewById(R.id.tvPhoneLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        llAllLogin = findViewById(R.id.llAllLogin);
        rlLogin.setOnClickListener(this);
        tvPhoneLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        llAllLogin.setOnClickListener(this);
        llAll.setOnClickListener(this);
    }

    @OnClick({R.id.rlLogin, R.id.tvPhoneLogin, R.id.tvForgotPassword, R.id.llAllLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLogin:
                if (!ShareFileUtils.isAppInstall(BaseApplication.getInstance(), "com.tencent.mm")) {
                    ArmsUtils.makeText(getContext(),"你的手机未安装微信");
                    break;
                }
                AppConstants.isShare = false;
                loginByWeChat();
                if (iLoginDialogClickListener != null) {
                    isJump = true;
                    iLoginDialogClickListener.onLoginByWeChat();
                }
                break;
            case R.id.tvPhoneLogin:
                if (iLoginDialogClickListener != null) {
                    isJump = true;
                    iLoginDialogClickListener.onPhoneLogin();
                }
                break;
            case R.id.tvForgotPassword:
                if (iLoginDialogClickListener != null) {
                    isJump = true;
                    iLoginDialogClickListener.onPhoneRegistratio();
                }
                break;
            case R.id.llAll:

                break;
            case R.id.llAllLogin:


                break;
        }
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(getContext(), AppConstants.APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(AppConstants.APP_ID);
    }

    private void loginByWeChat() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "app_wechat";
        api.sendReq(req);
    }


    public void showDialog() {
        showTop();
        isJump = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            isJump = false;
            EventBus.getDefault().post(new CloseWXEvent());
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


    public interface ILoginDialogClickListener {
        void onLoginByWeChat();

        void onPhoneRegistratio();

        void onPhoneLogin();

        void onDismiss();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (!isJump) {
            if (iLoginDialogClickListener != null) {
                iLoginDialogClickListener.onDismiss();
            }
        }
    }
}
