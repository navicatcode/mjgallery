package com.mjgallery.mjgallery.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.language.CommSharedUtil;
import com.mjgallery.mjgallery.language.LanguageType;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.mvp.ui.activity.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static com.jess.arms.utils.ArmsUtils.getResources;


/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {


    static final AdaptScreenArgs ADAPT_SCREEN_ARGS = new AdaptScreenArgs();
    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();

    private final static String PERMISSION_ACTIVITY_CLASS_NAME =
            "com.blankj.utilcode.util.PermissionUtils$PermissionActivity";
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(final Context context) {
        if (context == null) {
            init(getApplicationByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    static ActivityLifecycleImpl getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    static LinkedList<Activity> getActivityList() {
        return ACTIVITY_LIFECYCLE.mActivityList;
    }

    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    static boolean isAppForeground() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }

    static void restoreAdaptScreen() {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        final Activity activity = ACTIVITY_LIFECYCLE.getTopActivity();
        if (activity != null) {
            final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
            if (ADAPT_SCREEN_ARGS.isVerticalSlide) {
                activityDm.density = activityDm.widthPixels / (float) ADAPT_SCREEN_ARGS.sizeInPx;
            } else {
                activityDm.density = activityDm.heightPixels / (float) ADAPT_SCREEN_ARGS.sizeInPx;
            }
            activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
            activityDm.densityDpi = (int) (160 * activityDm.density);

            appDm.density = activityDm.density;
            appDm.scaledDensity = activityDm.scaledDensity;
            appDm.densityDpi = activityDm.densityDpi;
        } else {
            if (ADAPT_SCREEN_ARGS.isVerticalSlide) {
                appDm.density = appDm.widthPixels / (float) ADAPT_SCREEN_ARGS.sizeInPx;
            } else {
                appDm.density = appDm.heightPixels / (float) ADAPT_SCREEN_ARGS.sizeInPx;
            }
            appDm.scaledDensity = appDm.density * (systemDm.scaledDensity / systemDm.density);
            appDm.densityDpi = (int) (160 * appDm.density);
        }
    }

    static void cancelAdaptScreen() {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        final Activity activity = ACTIVITY_LIFECYCLE.getTopActivity();
        if (activity != null) {
            final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
            activityDm.density = systemDm.density;
            activityDm.scaledDensity = systemDm.scaledDensity;
            activityDm.densityDpi = systemDm.densityDpi;
        }
        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi = systemDm.densityDpi;
    }

    static boolean isAdaptScreen() {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = Utils.getApp().getResources().getDisplayMetrics();
        return systemDm.density != appDm.density;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }



    public static String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources(BaseApplication.getInstance()).getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMonthByInt(int key) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "一");
        map.put(2, "二");
        map.put(3, "三");
        map.put(4, "四");
        map.put(5, "五");
        map.put(6, "六");
        map.put(7, "七");
        map.put(8, "八");
        map.put(9, "九");
        map.put(10, "十");
        map.put(11, "十一");
        map.put(12, "十二");
        return map.get(key);
    }

    public static int getMonthByString(String string) {
        Map<String, Integer> map = new HashMap<>();
        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("十", 10);
        map.put("十一", 11);
        map.put("十二", 12);
        return map.get(string);
    }

    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d(ctx.toString(), "本软件的版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d(ctx.toString(), "本软件的版本名：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static void setAlarmParams(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
    }

    /**
     * 得到背景颜色
     *
     * @param numbers
     * @return
     */
    public static int setNumbersBg(int numbers) {
        switch (numbers) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                //红
                return R.drawable.home_sheng_xiao_bg1_img;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                //蓝
                return R.drawable.home_sheng_xiao_bg3_img;
            default:
                //绿
                return R.drawable.home_sheng_xiao_bg2_img;
        }
    }

    public interface OnAppStatusChangedListener {
        void onForeground();

        void onBackground();
    }

    static class AdaptScreenArgs {
        int sizeInPx;
        boolean isVerticalSlide;
    }

    static class ActivityLifecycleImpl implements ActivityLifecycleCallbacks {

        final LinkedList<Activity> mActivityList = new LinkedList<>();
        final HashMap<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();

        private int mForegroundCount = 0;
        private int mConfigCount = 0;

        void addListener(final Object object, final OnAppStatusChangedListener listener) {
            mStatusListenerMap.put(object, listener);
        }

        void removeListener(final Object object) {
            mStatusListenerMap.remove(object);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
            if (mForegroundCount <= 0) {
                postStatus(true);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {/**/}

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    postStatus(false);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
        }

        private void postStatus(final boolean isForeground) {
            if (mStatusListenerMap.isEmpty()) return;
            for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
                if (onAppStatusChangedListener == null) return;
                if (isForeground) {
                    onAppStatusChangedListener.onForeground();
                } else {
                    onAppStatusChangedListener.onBackground();
                }
            }
        }

        Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                final Activity topActivity = mActivityList.getLast();
                if (topActivity != null) {
                    return topActivity;
                }
            }
            Activity topActivityByReflect = getTopActivityByReflect();
            if (topActivityByReflect != null) {
                setTopActivity(topActivityByReflect);
            }
            return topActivityByReflect;
        }

        private void setTopActivity(final Activity activity) {
            if (PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) return;
            if (mActivityList.contains(activity)) {
                if (!mActivityList.getLast().equals(activity)) {
                    mActivityList.remove(activity);
                    mActivityList.addLast(activity);
                }
            } else {
                mActivityList.addLast(activity);
            }
        }

        private Activity getTopActivityByReflect() {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivityList");
                activitiesField.setAccessible(true);
                Map activities = (Map) activitiesField.get(activityThread);
                if (activities == null) return null;
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static final class FileProvider4UtilCode extends FileProvider {

        @Override
        public boolean onCreate() {
            Utils.init(getContext());
            return true;
        }
    }

    /**
     * 设置简繁体下的头部"马经图库"图片切换
     * @param context
     * @param ivHomeTop
     * @param colorBool true为使用白色字体的图片，false为用蓝色字体的
     */
    public static void setTopImg(Context context,ImageView ivHomeTop,boolean colorBool){
        int languageType = CommSharedUtil.getInstance(context).getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_RW);
        //先判断繁简体，再判断颜色
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {

            if(colorBool)
                ivHomeTop.setImageResource(R.drawable.home_top_img);
            else
                ivHomeTop.setImageResource(R.drawable.discovery_top_img);
        }else{

            if(colorBool)
                ivHomeTop.setImageResource(R.drawable.home_top_img_fenti_white);
            else
                ivHomeTop.setImageResource(R.drawable.home_top_img_fenti);
        }

    }

    /**
     * 获得东八区时间
     *
     * @return
     */
    public static String getChinaTime() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取VIP级别代表的名称
     * @param level
     * @return
     */
    public static String getUserLevelName(int level) {
        String levelName ;
        switch (level) {

            case 1:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_1);
                break;
            case 2:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_2);
                break;
            case 3:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_3);
                break;
            case 4:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_4);
                break;
            case 5:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_5);
                break;
            case 6:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_6);
                break;
            case 7:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_7);
                break;
            case 8:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_8);
                break;
            case 9:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_9);
                break;
            case 10:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_10);
                break;
            default:
                levelName = ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip_level_1);
                break;
        }
        return levelName;
    }
}
