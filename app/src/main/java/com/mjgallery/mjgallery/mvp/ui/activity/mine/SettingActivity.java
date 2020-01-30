package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.DataCleanManager;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.app.view.dialog.DownloadPicturesDialog;
import com.mjgallery.mjgallery.app.view.dialog.download.VersionDownloadDialog;
import com.mjgallery.mjgallery.app.view.dialog.download.VersionUpdateDialog;
import com.mjgallery.mjgallery.app.view.dialog.home.LoginOutDialog;
import com.mjgallery.mjgallery.di.component.DaggerSettingComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.language.CommSharedUtil;
import com.mjgallery.mjgallery.language.LanguageType;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.mvp.contract.setting.SettingContract;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;
import com.mjgallery.mjgallery.mvp.presenter.setting.SettingPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.MainActivity;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.cIsSwitchLanguage;


/**
 * ================================================
 * Description:设置界面
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends MJBaseActivity<SettingPresenter> implements
        SettingContract.View, LoginOutDialog.ILoginOutConfirm, VersionUpdateDialog.UpdateDownloadListener {

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
    @BindView(R.id.rlAccountSecurity)
    RelativeLayout rlAccountSecurity;
    @BindView(R.id.rlMessage)
    RelativeLayout rlMessage;
    @BindView(R.id.bg01)
    TextView bg01;
    @BindView(R.id.rlPrivacy)
    RelativeLayout rlPrivacy;
    @BindView(R.id.bg02)
    TextView bg02;
    @BindView(R.id.tvClearCache)
    TextView tvClearCache;
    @BindView(R.id.rlClearCache)
    RelativeLayout rlClearCache;
    @BindView(R.id.rlNightModeImg1)
    ImageView rlNightModeImg1;
    @BindView(R.id.rlNightModeImg2)
    ImageView rlNightModeImg2;
    @BindView(R.id.rlNightMode)
    RelativeLayout rlNightMode;
    @BindView(R.id.bg04)
    TextView bg04;
    @BindView(R.id.ivLanguageJT)
    ImageView ivLanguageJT;
    @BindView(R.id.ivLanguageFT)
    ImageView ivLanguageFT;
    @BindView(R.id.language_switching)
    RelativeLayout languageSwitching;
    @BindView(R.id.tvVersionNo)
    TextView tvVersionNo;
    @BindView(R.id.tvVersionSign)
    TextView tvVersionSign;
    @BindView(R.id.rlVersionInformation)
    RelativeLayout rlVersionInformation;
    @BindView(R.id.bg03)
    TextView bg03;
    @BindView(R.id.rlFeedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.rlOutLogin)
    RelativeLayout rlOutLogin;

    //退出登录dialog
    LoginOutDialog mLoginOutDialog;
    //检测有新更新版本的dialog
    VersionUpdateDialog versionUpdateDialog;
    //显示下载新版本进度的dialog
    VersionDownloadDialog versionDownloadDialog;
    //清理缓存dialog
    DownloadPicturesDialog clearDialog;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.setting));
        mLoginOutDialog = new LoginOutDialog(this, this);
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        versionNoShow();
        setTvClearTheCache();
        clearDialog = new DownloadPicturesDialog(this, ArmsUtils.getString(BaseApplication.getInstance(), R.string.clear_laji));
        versionUpdateDialog = new VersionUpdateDialog(this, this);
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        if (TextUtils.isEmpty(PreferenceUtil.getInstance
                (BaseApplication.getInstance()).getString(AppConstants.TOKEN, ""))) {
            rlOutLogin.setVisibility(View.GONE);
        } else {
            rlOutLogin.setVisibility(View.VISIBLE);
        }

        showInitSetting();
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

    private void setTvClearTheCache() {
        try {
            tvClearCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ivTopReturn, R.id.rlOutLogin, R.id.rlAccountSecurity, R.id.rlMessage, R.id.rlPrivacy, R.id.rlClearCache, R.id.rlVersionInformation, R.id.rlFeedback,
            R.id.ivLanguageJT, R.id.ivLanguageFT, R.id.rlNightModeImg1, R.id.rlNightModeImg2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                if (cIsSwitchLanguage) {
                    toOtherActivity(MainActivity.class);
                }
                killMyself();
                break;
            case R.id.rlAccountSecurity:
                //账户安全
                if (TextUtils.isEmpty(token))
                    exitView();
                else
                    toOtherActivity(AccountSafeActivity.class, getIntent().getExtras());
                break;
            case R.id.rlMessage:
                //消息提醒
                toOtherActivity(MessageWarnSettingActivity.class);
                break;
            case R.id.rlPrivacy:
                //隐私
                if (TextUtils.isEmpty(token))
                    exitView();
                else
                    toOtherActivity(MeConncealActivity.class);
                break;
            case R.id.rlClearCache:
                //清理缓存
                if (!clearDialog.isShowing())
                    clearDialog.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        clearCache();
                    }
                }, 2000);   //2秒

                break;
            case R.id.rlNightModeImg1:
                //日间模式
                setNightStatusUI(rlNightModeImg1, rlNightModeImg2, AppConstants.STATUS_COLSE);
                break;
            case R.id.rlNightModeImg2:
                //夜间模式
                setNightStatusUI(rlNightModeImg1, rlNightModeImg2, AppConstants.STATUS_OPEN);
                break;
            case R.id.ivLanguageJT:
                //切换成繁体
                setLanguageStatus(ivLanguageJT, ivLanguageFT, LanguageType.LANGUAGE_RW);
                break;
            case R.id.ivLanguageFT:
                //切换成简体
                setLanguageStatus(ivLanguageJT, ivLanguageFT, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
                break;
            case R.id.rlVersionInformation:
                //检测版本
                checkVersionUpdate();
                break;
            case R.id.rlFeedback:
                //意见反馈
                if (TextUtils.isEmpty(token))
                    exitView();
                else
                    toOtherActivity(FeedbackInfoActivity.class);
                break;
            case R.id.rlOutLogin:
                //退出登录
                if (mLoginOutDialog != null && !mLoginOutDialog.isShowing()) {
                    mLoginOutDialog.show();
                }
                break;
        }
    }

    private void startActivity(Class classzz) {
        Intent intent = new Intent(this, classzz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }


    /**
     * 清理缓存
     */
    public void clearCache() {
        //清除缓存
        try {
            DataCleanManager.clearAllCache(this);
            tvClearCache.setText(DataCleanManager.getTotalCacheSize(this));
            clearDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLoginOut(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0 || baseResponse.getCode() == -5 || baseResponse.getCode() == -2) {
                if (mLoginOutDialog != null) {
                    mLoginOutDialog.dismiss();
                }

                PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        remove(AppConstants.TOKEN);
                exitView();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    /**
     * 退回首页，并提示登录
     */
    public void exitView() {
        EventBus.getDefault().post(new UpDateEvent());
        killMyself();
    }

    @Override
    protected void requestData() {

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


    /**
     * 显示初始设置(简繁切换和夜间模式)
     */
    public void showInitSetting() {
        //---显示当前设置的日、夜模式
        int type = CommSharedUtil.getInstance(this).getInt(AppConstants.nightOpen, AppConstants.STATUS_COLSE);
        setNightStatusUI(rlNightModeImg1, rlNightModeImg2, type);

        //---显示当前设置的语言
        int languageType2 = CommSharedUtil.getInstance(this).getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_RW);
        showLanguageUI(ivLanguageJT, ivLanguageFT, languageType2);
    }

    //切换夜间及日间模式
    public void setNightStatusUI(ImageView iv1, ImageView iv2, int type) {

        CommSharedUtil.getInstance(this).putInt(AppConstants.nightOpen, type);

        if (type == AppConstants.STATUS_OPEN) {//打开夜间模式
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.GONE);
        } else {
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击切换设置语言
     */
    public void setLanguageStatus(ImageView iv1, ImageView iv2, int languageType) {
        cIsSwitchLanguage = true;
        //保存记录及刷新ui
        showLanguageUI(iv1, iv2, languageType);
        //下面要增加让更换的语言立即UI生效的代码
        startActivity(SettingActivity.class);
    }

    //显示切换语言后的ui
    public void showLanguageUI(ImageView iv1, ImageView iv2, int languageType) {
        MultiLanguageUtil.getInstance().updateLanguage(languageType);

        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.GONE);
        } else {
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onLoginOutConfirm() {
        mPresenter.requestLoginOut(token);
    }

    public void checkVersionUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "0");
        map.put("versionCode", Utils.getLocalVersion(this) + "");
        mPresenter.requestVersionUpdate(map);
    }

    /**
     * 检测更新接口的结果回调
     */
    @Override
    public void onVersionUpdate(VersionUpdateBean versionUpdateBean) {
        if (versionUpdateBean == null) {
            showMessage("当前已是最新版本！");
            return;
        }
        if (versionUpdateDialog != null && !versionUpdateDialog.isShowing()) {
            versionUpdateDialog.show(versionUpdateBean);
        }
    }


    /**
     * 显示下载进度的弹窗dialog
     *
     * @param apkUrl
     */
    @Override
    public void updateDownload(String apkUrl) {
        if (versionDownloadDialog == null) {
            versionDownloadDialog = new VersionDownloadDialog(this);
        }
        if (!versionDownloadDialog.isShowing()) {
            versionDownloadDialog.show(apkUrl);
        }
    }

    /**
     * 显示 首页检测并传过来的版本号
     */
    public void versionNoShow() {
        if (MainActivity.newVersionNo == null) {
            tvVersionNo.setText("（v" + Utils.getLocalVersionName(this) + "）" + ArmsUtils.getString(BaseApplication.getInstance(), R.string.version_update_hint1));
            tvVersionSign.setVisibility(View.GONE);
        } else {
            tvVersionNo.setText("（" + MainActivity.newVersionNo + "）" + ArmsUtils.getString(BaseApplication.getInstance(), R.string.version_update_hint2));
            tvVersionSign.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //判断是否为修改了语言点击返回键，是则跳转回首页，不是则返回
            if (cIsSwitchLanguage) {
                toOtherActivity(MainActivity.class);
            }
            killMyself();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginOutDialog != null) mLoginOutDialog.dismiss();
        if (versionUpdateDialog != null) versionUpdateDialog.dismiss();
        if (clearDialog != null) clearDialog.dismiss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
