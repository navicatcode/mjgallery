package com.mjgallery.mjgallery.mvp.ui.activity.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.mjgallery.mjgallery.di.component.DaggerAccountUpdloginPwdInfoComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdloginPwdInfoContract;
import com.mjgallery.mjgallery.mvp.presenter.account.AccountUpdloginPwdInfoPresenter;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:修改登录密码
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 *
 * @author 鲁班
 * ================================================
 */
public class AccountUpdLoginPwdInfoActivity extends MJBaseActivity<AccountUpdloginPwdInfoPresenter> implements AccountUpdloginPwdInfoContract.View {

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
    @BindView(R.id.etLoginPwd1)
    EditText etLoginPwd1;
    @BindView(R.id.ivShowStatus1)
    ImageView ivShowStatus1;
    @BindView(R.id.etLoginPwd2)
    EditText etLoginPwd2;
    @BindView(R.id.ivShowStatus2)
    ImageView ivShowStatus2;
    @BindView(R.id.etLoginPwd3)
    EditText etLoginPwd3;
    @BindView(R.id.ivShowStatus3)
    ImageView ivShowStatus3;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountUpdloginPwdInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_updlogin_pwd_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.account_upd_login_pwd1));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        TextChangedListener();
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



    @OnClick({R.id.ivTopReturn,R.id.ivShowStatus1, R.id.ivShowStatus2, R.id.ivShowStatus3, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivShowStatus1:
                setPwdStatus(etLoginPwd1,ivShowStatus1);
                break;
            case R.id.ivShowStatus2:
                setPwdStatus(etLoginPwd2,ivShowStatus2);
                break;
            case R.id.ivShowStatus3:
                setPwdStatus(etLoginPwd3,ivShowStatus3);
                break;
            case R.id.tv_sure:
                if(!isLengthBool(etLoginPwd1) ||   !isLengthBool(etLoginPwd2) || !isLengthBool(etLoginPwd3)){

                    showMessage(this.getString(R.string.set_pwd_eror));
                    return;
                }

                String oldPassword=etLoginPwd1.getText().toString().trim();
                String newPassword=etLoginPwd2.getText().toString().trim();
                if(oldPassword.equals(newPassword)){
                    showMessage(this.getString(R.string.oldpwd_newpwd_hint));
                    return;
                }
                String token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        getString(AppConstants.TOKEN, "");

                Map<String,Object> map=new HashMap<>();
                map.put("token",token);
                map.put("oldPassword",oldPassword);
                map.put("newPassword",newPassword);
                mPresenter.requestChangeLoginPassword(map);
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
     * 设置输入框监听
     */
    public void TextChangedListener(){

        etLoginPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length()>=6 && editable.toString().trim().equals(etLoginPwd3.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });

        etLoginPwd3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length()>=6 && editable.toString().trim().equals(etLoginPwd2.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });
    };


    public boolean isLengthBool(EditText et){
        if(et.getText().toString().length() >=6 && et.getText().toString().length()<=15){
            return true;
        }
        return false;
    }

    @Override
    public void onChangeLoginPassword(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.request_ok));
                String token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        getString(AppConstants.TOKEN, "");
                mPresenter.requestLoginOut(token);
            } else {
                showMessage(baseResponse.getMessage());
            }

        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }

    @Override
    public void onLoginOut(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        remove(AppConstants.TOKEN);
                EventBus.getDefault().post(new UpDateEvent());
                killMyself();
            }
            showMessage(baseResponse.getMessage());
        }
    }
}
