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


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.lqr.emoji.LQREmotionKit;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.CrashHandler;
import com.mjgallery.mjgallery.app.utils.SystemUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;


/**
 * ================================================
 * MVPArms 是一个整合了大量主流开源项目的 Android MVP 快速搭建框架, 其中包含 Dagger2、Retrofit、RxJava 以及
 * RxLifecycle、RxCache 等 Rx 系三方库, 并且提供 UI 自适应方案, 本框架将它们结合起来, 并全部使用 Dagger2 管理
 * 并提供给开发者使用, 使用本框架开发您的项目, 就意味着您已经拥有一个 MVP + Dagger2 + Retrofit + RxJava 项目
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">请配合官方 Wiki 文档学习本框架</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog">更新日志, 升级必看!</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/Issues">常见 Issues, 踩坑必看!</a>
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">MVPArms 官方组件化方案 ArmsComponent, 进阶指南!</a>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BaseApplication extends MultiDexApplication implements App {

    private static BaseApplication instance;
    private AppLifecycles mAppDelegate;
    //这里我就不写管理类了,捡个懒,直接在 Application 中管理单例 Okhttp
    private OkHttpClient mOkHttpClient;

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化


    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
        //初始化表情控件
        LQREmotionKit.init(instance,
                (context, path, imageView) ->
                        Glide.with(context).load(path).
                                apply(new RequestOptions().centerCrop().
                                        diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                                        dontAnimate()).into(imageView));
        this.mOkHttpClient = ProgressManager.getInstance().with(new OkHttpClient.Builder())
                .build();


        Log.e("getSystemModel===","getSystemMsgUtil.getDeviceBrand()==="+ SystemUtil.getDeviceBrand());
        Log.e("getSystemModel===","getSystemModel==="+ SystemUtil.getSystemModel());
//        try {
//            RSAEncrypt.mainAA();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用, {@link AppComponent} 接口中声明的方法所返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     * @see ArmsUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s",
                mAppDelegate.getClass().getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }



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
}
