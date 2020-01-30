package com.mjgallery.mjgallery.app;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.mvp.ui.activity.LoginTransparentActivity;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


public abstract class MJBaseFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    protected Context mContext;
    protected KProgressHUD mKProgressHUD;
    protected boolean isRequesData = true;
    protected String token;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    private Cache<String, Object> mCache;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        Log.e("我的token == ", token);
        mKProgressHUD = KProgressHUD.create(mContext).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setSize(ArmsUtils.dip2px(getContext(), 35), ArmsUtils.dip2px(getContext(), 35));
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (mKProgressHUD != null) mKProgressHUD.dismiss();//释放资源
        this.mPresenter = null;

    }




    @Override
    public void onResume() {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        super.onResume();

    }
    protected void showLoadingAnimationDialog() {
        if (mKProgressHUD != null ) {
            mKProgressHUD.dismiss();
            mKProgressHUD.show();
        }
    }


    protected void dismissLoadingAnimationDialog() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为使用 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }


    /**
     * 单独的登陆判断，如果没有登陆,不去请求网络数据
     *
     * @return
     */
    protected boolean isLoginFragment() {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            toOtherActivity(LoginTransparentActivity.class);
            return false;
        } else {
            return true;
        }
    }


    /**
     * 单独的登陆判断，如果没有登陆,不去请求网络数据
     *
     * @return
     */
    protected void isLogin() {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            toOtherActivity(LoginTransparentActivity.class);
            return;
        } else {
            requestData();
        }
    }


    /**
     * 得到登陆状态
     *
     * @return
     */
    protected boolean isLoginStatus() {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            toOtherActivity(LoginTransparentActivity.class);
            return false;
        }
        return true;
    }


    /**
     * 跳转处理
     * 当前是需要登陆
     *
     * @param cls
     * @param bdl
     */
    protected void isToOtherActivity(Class<?> cls, Bundle bdl) {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            toOtherActivity(LoginTransparentActivity.class);
            return;
        } else {
            if (bdl == null) {
                toOtherActivity(cls);
            } else {
                toOtherActivity(cls, bdl);
            }
        }
    }


    //播放音频文件
    public void playMusic(int MusicId) {
        int status = PreferenceUtil.getInstance(getContext()).getInt(AppConstants.musicOpen, AppConstants.STATUS_OPEN);
        if (status == AppConstants.STATUS_OPEN) {
            MediaPlayer.create(BaseApplication.getInstance(), MusicId).start();
        }
    }

    /**
     * 重载toOtherActivity
     *
     * @param cls 跳转activity
     */
    protected <T> void toOtherActivity(Class<?> cls, Bundle bdl) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(BaseApplication.getInstance(), cls);
        intent.putExtras(bdl);
        startActivity(intent);
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


    protected void requestData() {
    }


    protected String getStrings(int resId) {
        return ArmsUtils.getString(getContext(), resId);
    }

    protected void hideLoading() {
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onRemoveTokenEvent(RemoveTokenEvent removeTokenEvent) {
        if (removeTokenEvent == null) {
            return;
        }
        if (removeTokenEvent.getCode() == -5 || removeTokenEvent.getCode() == -2) {
            PreferenceUtil.getInstance(BaseApplication.getInstance()).remove("token");
            token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                    getString(AppConstants.TOKEN, "");
            isLoginStatus();
        }
    }


}
