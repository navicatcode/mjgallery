package com.mjgallery.mjgallery.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.utils.GetDeviceId;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerSplashComponent;
import com.mjgallery.mjgallery.mvp.contract.SplashContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.presenter.SplashPresenter;
import com.mjgallery.mjgallery.widget.CircleTextProgressbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.APP_HOMES;
import static com.mjgallery.mjgallery.app.AppConstants.APP_START_IMG;
import static com.mjgallery.mjgallery.app.AppConstants.APP_STRINGS;
import static com.mjgallery.mjgallery.app.AppConstants.isOneStart;


/**
 * ================================================
 * Description:启动页界面
 * <p>
 * Created by MVPArmsTemplate on 09/10/2019 13:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Nullable
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @Nullable
    @BindView(R.id.cpb_splash)
    CircleTextProgressbar cpb;

    private Disposable subscribe;
    private Disposable disposeStartPicture;
    private Disposable disposeShareUrl;
    private boolean isJumped = false;
    int year = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (isOneStart) {
            ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
            killMyself();
            return;
        }
        if (!TextUtils.isEmpty(PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(APP_START_IMG, ""))) {
            Glide.with(SplashActivity.this).load(PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(APP_START_IMG, "")).into(ivSplash);
        }
        mPresenter.getAdvert(0);
        getDeviceId();
        mPresenter.getPictureMenu();
        startToMain();

        cpb.setOutLineColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.colorPrimary));
        cpb.setOutLineWidth(2);
        cpb.setProgressLineWidth(2);
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
        ArmsUtils.snackbarText(message);
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
    public void getHomeBanner(List<HomeBanner> resultBeanList) {
        if (resultBeanList != null && resultBeanList.size() > 0) {
            HomeBanner homeBanner = resultBeanList.get(0);
            String appStartImg = (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(APP_START_IMG, ""));
            if (homeBanner != null) {
                ivSplash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(homeBanner.getUrl())){
                            Uri uri = Uri.parse(homeBanner.getUrl());
                            if(uri.isAbsolute()){
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    }
                });
                if (TextUtils.isEmpty(appStartImg)) {
                    Glide.with(SplashActivity.this).load(homeBanner.getImgPath()).into(ivSplash);
                }
                PreferenceUtil.getInstance(BaseApplication.getInstance()).putString(APP_START_IMG, homeBanner.getImgPath());
            }
        }

    }

    @Override
    public void getSmallPictrues(List<HomeBean> homeBeanList) {
        APP_HOMES.clear();
        APP_HOMES.addAll(homeBeanList);
    }

    @Override
    public void getPictureMenu(List<String> stringList) {
        APP_STRINGS.clear();
        APP_STRINGS.addAll(stringList);
        if (stringList.size() > 0) {
            getDiscovery();
        }
    }


    public void getDeviceId() {
        // 应用唯一标识的方案
        // 优先->AndroidId 次级->Mac地址 次次级->uniqueId存本地
        // 检测方式 sp 内部存储 外部存储
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取保存在sd中的 设备唯一标识符
                    String readDeviceID = GetDeviceId.readDeviceID(SplashActivity.this);
                    //获取缓存在  sharepreference 里面的 设备唯一标识
                    String string = SPUtils.getInstance().getString("token");
                    //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
                    if (string != null) {
                        //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
                        if (!TextUtils.isEmpty(readDeviceID) && string != readDeviceID) {
                            // 取有效地 app缓存 进行更新操作
                            if (TextUtils.isEmpty(readDeviceID) && !TextUtils.isEmpty(string)) {
                                readDeviceID = string;
                                GetDeviceId.saveDeviceID(readDeviceID, SplashActivity.this);
                            }
                        }
                    }
                    // app 没有缓存 (这种情况只会发生在第一次启动的时候)
                    if (TextUtils.isEmpty(readDeviceID)) {
                        //保存设备id
                        readDeviceID = GetDeviceId.getDeviceId(SplashActivity.this);
                    }
                    //之后后再次更新app 的缓存
                    SPUtils.getInstance().put("deviceId", readDeviceID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void startToMain() {

        cpb.setProgressLineWidth(10);
        cpb.setOutLineColor(Color.GRAY);
        cpb.setProgressColor(Color.GRAY);

        // 5秒跳转首页
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscribe = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        if (aLong >= 5 && !isJumped) {
                            if (subscribe != null)
                                subscribe.dispose();
                            if (cpb != null) {
                                cpb.setVisibility(View.GONE);
                            }
                            ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
                            isJumped = true;
                            finish();
                        } else {
                            if (cpb!=null) {
                                cpb.setProgress(80 - aLong.intValue() * 20);
                                cpb.setText(4 - aLong.intValue() + ArmsUtils.getString(BaseApplication.getInstance(), R.string.main_splash_img_txt));
                                cpb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (isJumped) return;
                                        ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
                                        if (subscribe != null)
                                            subscribe.dispose();
                                        isJumped = true;
                                        finish();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 得到首页下面列表数据
     */
    private void getDiscovery() {
        Map<String, Object> map = new HashMap<>();
        map.put("color", "1");
        map.put("pageIndex", 0);
        map.put("pageSize", 15);
        if (year != 0) {
            map.put("year", year);
        }
        mPresenter.requestDiscovery(map);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, 0);//去掉Activity切换间的动画
    }
}
