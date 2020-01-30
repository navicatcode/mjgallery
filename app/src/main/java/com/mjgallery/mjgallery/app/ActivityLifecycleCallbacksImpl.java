package com.mjgallery.mjgallery.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;

import com.fengchen.uistatus.UiStatusController;
import com.fengchen.uistatus.annotation.UiStatus;
import com.mjgallery.mjgallery.app.utils.BarUtils;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.umeng.analytics.MobclickAgent;

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    UiStatusController mUiStatusController;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        MultiLanguageUtil.getInstance().setConfiguration();
        String name = activity.getClass().getName();
        if (!name.contains("com.yalantis.ucrop")) {
            BarUtils.setStatusBarAlpha(activity, 0, true);
            BarUtils.setStatusBarLightMode(activity, true);
        }

//        mUiStatusController = UiStatusController.get().bind(activity);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mUiStatusController.changeUiStatusIgnore(UiStatus.NETWORK_ERROR);
//            }
//        }, 1000);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        MultiLanguageUtil.getInstance().setConfiguration();
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
