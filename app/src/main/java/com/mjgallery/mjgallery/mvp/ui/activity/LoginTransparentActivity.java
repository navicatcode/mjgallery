package com.mjgallery.mjgallery.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.receiver.JPushCustomeReceiver;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.ChangePasswordDialog;
import com.mjgallery.mjgallery.app.view.dialog.CompleteRegistratioDialog;
import com.mjgallery.mjgallery.app.view.dialog.LoginDialog;
import com.mjgallery.mjgallery.app.view.dialog.MobilePhoneLoginDialog;
import com.mjgallery.mjgallery.app.view.dialog.PhoneRegistratioDialog;
import com.mjgallery.mjgallery.di.component.DaggerLoginTransparentComponent;
import com.mjgallery.mjgallery.event.CloseWXEvent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.login.LoginTransparentContract;
import com.mjgallery.mjgallery.mvp.model.bean.LoginResponse;
import com.mjgallery.mjgallery.mvp.presenter.login.LoginTransparentPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.loadPublicKey;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.publicKeyDecrtypt;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.publicKeyEncrypt;


/**
 * ================================================
 * Description:透明的登陆Activity
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class LoginTransparentActivity extends MJBaseActivity<LoginTransparentPresenter>
        implements LoginTransparentContract.View, LoginDialog.ILoginDialogClickListener
        , ChangePasswordDialog.IChangePassword, MobilePhoneLoginDialog.IMobilePhoneLogin,
        PhoneRegistratioDialog.IPhoneRegistratio {

    LoginDialog loginDialog;  // 登陆
    ChangePasswordDialog changePasswordDialog;    //忘记密码
    MobilePhoneLoginDialog mobilePhoneLoginDialog;         //账号密码登陆
    PhoneRegistratioDialog phoneRegistratioDialog; //手机号注册
    @BindView(R.id.llAll)
    LinearLayout llAll;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginTransparentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_transparent; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loginDialog = new LoginDialog(this, this);
        changePasswordDialog = new ChangePasswordDialog(this, this);
        mobilePhoneLoginDialog = new MobilePhoneLoginDialog(this, this);
        phoneRegistratioDialog = new PhoneRegistratioDialog(this,this);
        showLoginDialog();
        llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissChangePasswordDialog();
                dismissLoginDialog();
                dismissMobilePhoneLoginDialog();
                killMyself();
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
        ArmsUtils.makeText(getBaseContext(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        dismissLoadingAnimationDialog();
        finish();
    }




    @Subscriber(mode = ThreadMode.MAIN)
    public void onCloseWXEvent(CloseWXEvent closeWXEvent) {
        dismissChangePasswordDialog();
        dismissLoginDialog();
        dismissMobilePhoneLoginDialog();
        dismissPhoneRegistratioDialog();
        killMyself();
    }


    /**
     * 关闭修改密码弹窗
     */
    private void dismissChangePasswordDialog() {
        if (changePasswordDialog != null && changePasswordDialog.isShowing()) {
            changePasswordDialog.dismiss();
        }
    }

    /**
     * 关闭修改手机号注册弹窗
     */
    private void dismissPhoneRegistratioDialog() {
        if (phoneRegistratioDialog != null && phoneRegistratioDialog.isShowing()) {
            phoneRegistratioDialog.dismiss();
        }
    }

    /**
     * 关闭一键登陆弹窗
     */
    private void dismissLoginDialog() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }


    /**
     * 关闭手机登陆弹窗
     */
    private void dismissMobilePhoneLoginDialog() {
        if (mobilePhoneLoginDialog != null && mobilePhoneLoginDialog.isShowing()) {
            mobilePhoneLoginDialog.dismiss();
        }
    }

    /**
     * 关闭修改密码弹窗
     */
    private void showChangePasswordDialog() {
        if (changePasswordDialog != null && !changePasswordDialog.isShowing()) {
            changePasswordDialog.showDialog();
        }
    }


    /**
     * 显示一键登陆弹窗
     */
    private void showLoginDialog() {
        if (loginDialog != null && !loginDialog.isShowing()) {
            loginDialog.showDialog();
        }
    }


    /**
     * 显示手机登陆弹窗
     */
    private void showMobilePhoneLoginDialog() {
        if (mobilePhoneLoginDialog != null && !mobilePhoneLoginDialog.isShowing()) {
            mobilePhoneLoginDialog.showDialog();
        }
    }

    @Override
    public void onLoginByWeChat() {
        dismissLoginDialog();
        killMyself();
    }

    @Override
    public void onPhoneRegistratio() {
        dismissLoginDialog();
        if (phoneRegistratioDialog != null && !phoneRegistratioDialog.isShowing()) {
            phoneRegistratioDialog.show(token);
        }
    }

    public void onForgotPassword() {//跳转到忘记密码
        dismissLoginDialog();
        showChangePasswordDialog();
    }

    @Override
    public void onPhoneLogin() { //点击账号密码登陆
        dismissLoginDialog();
        showMobilePhoneLoginDialog();
    }

    @Override
    public void onDismiss() {
        dismissChangePasswordDialog();
        dismissLoginDialog();
        dismissMobilePhoneLoginDialog();
        killMyself();
    }

    @Override
    public void onChangePassword(Map<String, Object> map) {//修改密码
        mPresenter.requestForgetPassword(map);
    }

    @Override
    public void onChangePasswordDismiss() { //关闭当前弹窗
        dismissChangePasswordDialog();
        dismissLoginDialog();
        dismissMobilePhoneLoginDialog();
        killMyself();
    }

    @Override
    public void onSendCode(Map<String, Object> map) {
        mPresenter.requestSendCode(map);
    }


    @Override
    public void onLoginForgotPassword() { //跳转到忘记你密码
        dismissMobilePhoneLoginDialog();
        showChangePasswordDialog();
    }

    @Override
    public void onBtnLogin(Map<String, Object> map) { //请求登陆接口
        mPresenter.requestLogin(map);
    }

    @Override
    public void onCloseMobilePhoneLoginDialog() { //关闭弹窗
        dismissChangePasswordDialog();
        dismissLoginDialog();
        dismissMobilePhoneLoginDialog();
        killMyself();
    }

    @Override
    public void onSendCode(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                if (changePasswordDialog != null && changePasswordDialog.isShowing()) {
                    changePasswordDialog.startCountDown();
                }
                if (phoneRegistratioDialog != null && phoneRegistratioDialog.isShowing()) {
                    phoneRegistratioDialog.codeStart();
                }
            } else {

                if (phoneRegistratioDialog != null && phoneRegistratioDialog.isShowing()) {
                    phoneRegistratioDialog.codeCountDownTextViewEnabled();
                }
                ArmsUtils.makeText(this,(baseResponse.getMessage())+"");
            }


        } else {
            ArmsUtils.makeText(this,ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }

    @Override
    public void onPasswordLogin(LoginResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0 &&  baseResponse.getResult()!=null) {
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.USERID, baseResponse.getResult().getUserId());
                //设置极光消息推送
                JPushCustomeReceiver.setJPushMessTS(this,baseResponse.getResult().getUserId());
                try {
                    RSAPublicKey publicKey = null;
                    publicKey = loadPublicKey("publicKey.keystore");
                    String token = new String(Base64.encode(publicKeyEncrypt(publicKey, publicKeyDecrtypt(publicKey, Base64.decode(baseResponse.getResult().getToken(), Base64.NO_WRAP))), Base64.NO_WRAP));
                    PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.TOKEN, token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissChangePasswordDialog();
                dismissLoginDialog();
                dismissMobilePhoneLoginDialog();
                EventBus.getDefault().post(new UpDateEvent(true));

                killMyself();
            } else {
                ArmsUtils.makeText(this,baseResponse.getMessage()+"");
            }


        } else {
            ArmsUtils.makeText(this,ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }

    @Override
    public void onForgetPassword(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                dismissChangePasswordDialog();
                dismissLoginDialog();
                dismissMobilePhoneLoginDialog();
                killMyself();
            } else {
                ArmsUtils.makeText(this,baseResponse.getMessage()+"");
            }

        } else {
            ArmsUtils.makeText(this,ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }



    @Override
    protected void requestData() {

    }

    @Override
    public void onCompleteSendCode(Map<String, Object> map) {
        mPresenter.requestSendCode(map);
    }

    @Override
    public void onPhoneRegister(Map<String, Object> map) {
        mPresenter.requestRegistern(map);
    }

    @Override
    public void onCompleteRegistratioDismiss() {
        if (phoneRegistratioDialog != null && phoneRegistratioDialog.isShowing()) {
            phoneRegistratioDialog.dismiss();
        }
        killMyself();
    }

    @Override
    public void onPhoneRegister(LoginResponse baseResponse) {
        if (baseResponse == null || baseResponse.getResult() == null) {
            if (baseResponse != null) {
                showMessage(baseResponse.getMessage());
                finish();
            }
            return;
        }
        try {
            baseResponse.getResult().getToken().getBytes("UTF-8");
            token = baseResponse.getResult().getToken();
            RSAPublicKey publicKey = loadPublicKey("publicKey.keystore");
            token = new String(Base64.encode(publicKeyEncrypt(publicKey, publicKeyDecrtypt(publicKey, Base64.decode(token, Base64.NO_WRAP))), Base64.NO_WRAP));
            if (baseResponse.getCode() == 0) {
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.USERID, baseResponse.getResult().getUserId());
                //设置极光消息推送
                JPushCustomeReceiver.setJPushMessTS(this, baseResponse.getResult().getUserId());
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.TOKEN, token);
                EventBus.getDefault().post(new UpDateEvent());
                killMyself();
            }else{
                ArmsUtils.makeText(this,baseResponse.getMessage()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
