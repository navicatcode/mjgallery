package com.mjgallery.mjgallery.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.receiver.JPushCustomeReceiver;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.SystemUtil;
import com.mjgallery.mjgallery.app.view.dialog.CompleteRegistratioDialog;
import com.mjgallery.mjgallery.di.component.DaggerWXEntryComponent;
import com.mjgallery.mjgallery.event.CloseWXEvent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.WXEntryContract;
import com.mjgallery.mjgallery.mvp.model.bean.LoginResponse;
import com.mjgallery.mjgallery.mvp.presenter.WXEntryPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.loadPublicKey;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.publicKeyDecrtypt;
import static com.mjgallery.mjgallery.rsa.RSAEncrypt.publicKeyEncrypt;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 20:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WXEntryActivity extends MJBaseActivity<WXEntryPresenter> implements
        WXEntryContract.View, IWXAPIEventHandler, CompleteRegistratioDialog.ICompleteRegistratio {
    CompleteRegistratioDialog completeRegistratioDialog;
    private IWXAPI api;
    private BaseResp resp = null;
    private String WX_APP_ID = "wxe3bb94b2a02e502c";
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private String WX_APP_SECRET = "创建应用后得到的APP_SECRET";
    private String token;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWXEntryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wxentry; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
        completeRegistratioDialog = new CompleteRegistratioDialog(this, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        dismissLoadingAnimationDialog();
        finish();
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (AppConstants.isShare) {
                    killMyself();
                    return;
                }
                String code = ((SendAuth.Resp) resp).code;
                System.out.println("==codo=========" + code);
                Map<String, Object> map = new HashMap<>();
                map.put("code", code);
                map.put("deviceId", SPUtils.getInstance().getString("deviceId"));
                map.put("model", SystemUtil.getDeviceName());
                map.put("osType", "1");
                mPresenter.requestLogin(map);
//                /*
//                 * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
//                 */
//                String get_access_token = getCodeRequest(code);
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.post(get_access_token, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
//                        try {
//                            if (!response.equals("")) {
//                                String access_token = response
//                                        .getString("access_token");
//                                String openid = response.getString("openid");
//                                String get_user_info_url = getUserInfo(
//                                        access_token, openid);
//                                getUserInfo(get_user_info_url);
//                            }
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//
//                });

//                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                showMessage(result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                showMessage(result);
                finish();
                break;
            default:
                result = "发送返回";
                showMessage(result);
                finish();
                break;
        }
    }


    @Override
    public void getLogin(LoginResponse baseResponse) {
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
            if (baseResponse.getCode() == -12) {
                if (completeRegistratioDialog != null && !completeRegistratioDialog.isShowing()) {
                    completeRegistratioDialog.showDialog(token);
                }
            } else if (baseResponse.getCode() == 0) {
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.USERID, baseResponse.getResult().getUserId());
                //设置极光消息推送
                JPushCustomeReceiver.setJPushMessTS(this, baseResponse.getResult().getUserId());
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(AppConstants.TOKEN, token);
                EventBus.getDefault().post(new UpDateEvent());
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getSendCode(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                if (completeRegistratioDialog != null) {
                    completeRegistratioDialog.codeStart();
                }
            } else {
                if (completeRegistratioDialog != null) {
                    completeRegistratioDialog.codeCountDownTextViewEnabled();
                }
                ArmsUtils.makeText(this,baseResponse.getMessage()+"");
            }


        } else {
            ArmsUtils.makeText(this,ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }

    @Override
    public void getRegistern(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                if (completeRegistratioDialog != null) {
                    completeRegistratioDialog.dismiss();
                    PreferenceUtil.getInstance(BaseApplication.getInstance()).putString("token", token);
                    EventBus.getDefault().post(new UpDateEvent());
                    finish();
                }
            } else {
                if (baseResponse.getMessage() != null)
                    ArmsUtils.makeText(getBaseContext(), baseResponse.getMessage()+"");
            }


        } else {
            ArmsUtils.makeText(getBaseContext(), "数据异常");
        }
    }

    @Override
    public void onError() {

        ArmsUtils.makeText(getBaseContext(), "网络连接错误!");
        killMyself();
    }

    @Override
    public void onCompleteSendCode(Map<String, Object> map) {
        mPresenter.requestSendCode(map);
    }

    @Override
    public void onCompletRegister(Map<String, Object> map) {
        mPresenter.requestRegistern(map);
    }

    @Override
    public void onCompleteRegistratioDismiss() {
        if (completeRegistratioDialog != null && completeRegistratioDialog.isShowing()) {
            completeRegistratioDialog.dismiss();
        }
        killMyself();
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onCloseWXEvent(CloseWXEvent closeWXEvent) {
        hideLoading();
        if (completeRegistratioDialog != null && completeRegistratioDialog.isShowing()) {
            completeRegistratioDialog.dismiss();
        }
        killMyself();
    }


    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(true){
            Log.e("","");
        }
    }
}
