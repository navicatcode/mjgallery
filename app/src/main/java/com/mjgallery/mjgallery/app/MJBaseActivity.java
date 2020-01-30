package com.mjgallery.mjgallery.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.mvp.ui.activity.LoginTransparentActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * 自定义BaseActivity
 *
 * @param <P>
 */
public abstract class MJBaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    protected String token;
    protected KProgressHUD mKProgressHUD;
    protected boolean isRequesData = true;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }



    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            Log.i("版本是8.0也，======", "onCreate fixOrientation when Oreo, result = " + result);
        }
        super.onCreate(savedInstanceState);
        GlideUtil.verifyStoragePermissions(this);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        Log.e("我的token == ",token);
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setSize(ArmsUtils.dip2px(this, 35), ArmsUtils.dip2px(this, 35));
        initData(savedInstanceState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        if (mKProgressHUD != null) mKProgressHUD.dismiss();//释放资源

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
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link com.jess.arms.base.BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }


    protected void showLoadingAnimationDialog() {
        if (mKProgressHUD != null ) {
            mKProgressHUD.show();
        }
    }


    protected void dismissLoadingAnimationDialog() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
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

    protected abstract void requestData();


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
        int status = PreferenceUtil.getInstance(this).getInt(AppConstants.musicOpen, AppConstants.STATUS_OPEN);
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

    /**
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyboard(view, ev)) {
                hideInput();
                if(view!=null){
                    hideSoftInput(view.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    //关闭输入框
    public void hideInput() {
    }



    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        Log.e("isShouldHideKeyboard===", "AppCompatEditText====" + v);
        if (v != null && (v instanceof AppCompatEditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = ArmsUtils.getScreenWidth(getApplication());
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                Log.e("isShouldHideKeyboard===", "false====");
                return false;
            } else {
                Log.e("isShouldHideKeyboard===", "true====");
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
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

    private boolean isTranslucentOrFloating(){
        boolean isTranslucentOrFloating = false;
        try {
            int [] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation(){
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
