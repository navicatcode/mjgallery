/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mjgallery.mjgallery.app;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fengchen.uistatus.UiStatusManager;
import com.fengchen.uistatus.UiStatusNetworkStatusProvider;
import com.fengchen.uistatus.annotation.UiStatus;
import com.fengchen.uistatus.controller.IUiStatusController;
import com.fengchen.uistatus.listener.OnCompatRetryListener;
import com.fengchen.uistatus.listener.OnRequestNetworkStatusEvent;
import com.fengchen.uistatus.listener.OnRetryListener;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.BuildConfig;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.CrashHandler;
import com.mjgallery.mjgallery.app.utils.DeviceIDUtils;
import com.mjgallery.mjgallery.app.utils.GetDeviceId;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.networkmanager.NetworkManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import butterknife.ButterKnife;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    //static 代码段可以防止内存泄露
    static {

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
//          MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(application);
            //LeakCanary 内存泄露检查
            //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
            //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
//            ArmsUtils.obtainAppComponentFromContext(application).extras()
//                    .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
//                            , LeakCanary.INSTANCE);
        }

        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        JPushInterface.init(application);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(BaseApplication.getInstance());
        builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        JPushInterface.setPushNotificationBuilder(1, builder);
        GetDeviceId.init(application);
        MultiLanguageUtil.init(BaseApplication.getInstance());
        // 添加中文城市词典
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(application)));

        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(application, "5d60ddef3fc19588b3000ede", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "64f4f6a6285f6b7aa1663375ee3e717a");
        PlatformConfig.setWeixin("wxe3bb94b2a02e502c", "05e987cbb0c280cff99f2020a18af31b");
        PlatformConfig.setQQZone("1109948382", "c7394704798a158208a74ab60104f0ba");
        if(TextUtils.isEmpty(PreferenceUtil.getInstance(application).getString(AppConstants.DEVICE_TOKEN,""))){
            PreferenceUtil.getInstance(application).putString(AppConstants.DEVICE_TOKEN, DeviceIDUtils.getDeviceId(application));
        }

        UiStatusManager.getInstance()
                .setWidgetMargin(UiStatus.WIDGET_NETWORK_ERROR, 48 * 3, 0)
                .setWidgetMargin(UiStatus.WIDGET_ELFIN, 48 * 3, 0)
                .setWidgetMargin(UiStatus.WIDGET_FLOAT, 0, 0)
//                .addUiStatusConfig(UiStatus.LOADING, R.layout.ui_status_layout_loading)//加载中.
                .addUiStatusConfig(UiStatus.NETWORK_ERROR, R.layout.ui_status_layout_network_error, R.id.tv_network_error_retry
                        , null
//                        new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "网络错误重试", Toast.LENGTH_LONG).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        controller.changeUiStatus(UiStatus.LOAD_ERROR);
//                                    }
//                                }, 1000);
//                            }
//                        }
                )//网络错误.
//                .addUiStatusConfig(UiStatus.LOAD_ERROR, R.layout.ui_status_layout_load_error, R.id.tv_load_error_retry
//                        , new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "加载失败重试", Toast.LENGTH_SHORT).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        controller.changeUiStatus(UiStatus.EMPTY);
//                                    }
//                                }, 1000);
//                            }
//                        })//加载失败.
//                .addUiStatusConfig(UiStatus.EMPTY, R.layout.ui_status_layout_empty, R.id.tv_empty_retry
//                        , new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "空布局重试", Toast.LENGTH_SHORT).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        controller.changeUiStatus(UiStatus.NOT_FOUND);
//                                    }
//                                }, 1000);
//                            }
//                        })//空布局.
//                .addUiStatusConfig(UiStatus.NOT_FOUND, R.layout.ui_status_layout_not_found, R.id.tv_not_found_retry
//                        , new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "未找到内容重试", Toast.LENGTH_SHORT).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        controller.changeUiStatus(UiStatus.CONTENT);
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                controller.changeUiStatus(UiStatus.WIDGET_ELFIN);
//                                            }
//                                        }, 1000);
//                                    }
//                                }, 1000);
//                            }
//                        })//未找到内容布局.
//                .addUiStatusConfig(UiStatus.WIDGET_ELFIN, R.layout.ui_status_layout_hint, R.id.tv_hint_retry
//                        , new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "提示内容重试", Toast.LENGTH_SHORT).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                controller.changeUiStatus(UiStatus.WIDGET_ELFIN);
//                                            }
//                                        }, 1000);
//                                    }
//                                }, 1000);
//                            }
//                        })//提示布局.
//                .addUiStatusConfig(UiStatus.WIDGET_NETWORK_ERROR, R.layout.widget_ui_status_network_error_widget, R.id.tv_check_network, new OnRetryListener() {
//                    @Override
//                    public void onUiStatusRetry(Object target, IUiStatusController controller, View trigger) {
//                        Toast.makeText(trigger.getContext(), "检查网络设置", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addUiStatusConfig(UiStatus.WIDGET_FLOAT, R.layout.widget_ui_status__widget_float, R.id.tv_float, new OnRetryListener() {
//                    @Override
//                    public void onUiStatusRetry(Object target, IUiStatusController controller, View trigger) {
//                        Toast.makeText(trigger.getContext(), "我是Float", Toast.LENGTH_SHORT).show();
//                    }
//                })
                .setOnCompatRetryListener(new OnCompatRetryListener() {
                    @Override
                    public void onUiStatusRetry(int uiStatus, @NonNull Object target, final @NonNull IUiStatusController controller, @NonNull View trigger) {
                        Log.i("--", "全局设置" + uiStatus);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                controller.changeUiStatus(UiStatus.LOAD_ERROR);
                            }
                        }, 1000);
                    }
                });

        UiStatusNetworkStatusProvider.getInstance()
                .registerOnRequestNetworkStatusEvent(new OnRequestNetworkStatusEvent() {
                    @Override
                    public boolean onRequestNetworkStatus(@NonNull Context context) {
                        return NetworkManager.isConnected(context);
                    }
                });
        //        UMConfigure.init(application, "5d01a74d570df318eb000c3d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

}
