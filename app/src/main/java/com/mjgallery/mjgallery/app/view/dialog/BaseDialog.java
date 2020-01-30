package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.event.CloseWXEvent;

import org.simple.eventbus.EventBus;


/**
 * Created by hello on 2017/9/8.
 * Function: 通用Dialog基类
 */

public class BaseDialog extends Dialog {

    public LinearLayout linearLayoutRoot;
    private View contentView;

    public BaseDialog(Activity activity) {
        this(activity, R.style.MyDialogStyleBottom);
    }

    public BaseDialog() {
        this((Activity) Utils.getTopActivityOrApp());
    }

    public BaseDialog(@NonNull Activity context, @StyleRes int themeResId) {

        super(context, themeResId);
        if (context != null) {
            try {
                setOwnerActivity(context);
                baseInit();
            } catch (Exception ignored) {
            }
        }
    }

    private void baseInit() {
        linearLayoutRoot = new LinearLayout(getContext());
        linearLayoutRoot.setBackgroundColor(Color.parseColor("#00000000"));
        linearLayoutRoot.setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
    }



    //在指定view下显示时 上部分灰色区域点击无反应
    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel) {
            linearLayoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CloseWXEvent());

                    dismiss();
                }
            });
        }
    }

    // ---------------------show gravity

    /**
     * 设置窗口显示的位置
     */
    public BaseDialog setGrativity(int gravity) {
        Window window = getWindow();
        if (window != null) window.setGravity(gravity);
        return this;
    }

    public void showTop() {
        setGrativity(Gravity.TOP);

        show();
    }

    public void showBottom() {
        setGrativity(Gravity.BOTTOM);
        show();
    }

    @Override
    public void show() {
        try {
            if (getOwnerActivity() != null && !getOwnerActivity().isFinishing()) {
                super.show();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 显示在指定控件的下方位置
     */
    public void showViewBottom(View v) {
        if (v == null) {
            show();
            return;
        }

        int height = v.getMeasuredHeight();

        int[] location = new int[2];
        v.getLocationInWindow(location);//若是普通activity,则y坐标为可见的状态栏高度+可见的标题栏高度+view左上角到标题栏底部的距离.
        //减去状态栏高度

        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        Resources resources = v.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }


        boolean isSamSung = false;//三星s8适配 18.5:9屏幕


        int showTopPadding;
        if (isSamSung) {
            showTopPadding = location[1] + height;
        } else {
            showTopPadding = location[1] - statusBarHeight + height;
        }

        paddingTop(showTopPadding);
        showTop();
    }

    /**
     * 设置窗口的显示和隐藏动画
     */
    public void setAnimations(int resId) {
        getWindow().setWindowAnimations(resId);
    }

    // -----------------------padding

    public BaseDialog paddingTop(int top) {
        linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), top,
                linearLayoutRoot.getPaddingRight(), linearLayoutRoot.getPaddingBottom());
        return this;
    }

    public BaseDialog paddingBottom(int bottom) {
        linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), linearLayoutRoot.getPaddingTop(),
                linearLayoutRoot.getPaddingRight(), bottom);
        return this;
    }

    public BaseDialog paddingLeft(int left) {
        linearLayoutRoot.setPadding(left, linearLayoutRoot.getPaddingTop(),
                linearLayoutRoot.getPaddingRight(), linearLayoutRoot.getPaddingBottom());
        return this;
    }

    public BaseDialog paddingRight(int right) {
        linearLayoutRoot.setPadding(linearLayoutRoot.getPaddingLeft(), linearLayoutRoot.getPaddingTop(),
                right, linearLayoutRoot.getPaddingBottom());
        return this;
    }

    public BaseDialog paddings(int paddings) {
        linearLayoutRoot.setPadding(paddings, paddings, paddings, paddings);
        return this;
    }

    /**
     * 设置窗口上下左右的边距
     */
    public BaseDialog padding(int left, int top, int right, int bottom) {
        linearLayoutRoot.setPadding(left, top, right, bottom);
        return this;
    }

    protected BaseDialog setDialogView(View view, ViewGroup.LayoutParams params) {
        contentView = view;
        wrapperView(contentView);
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.setContentView(linearLayoutRoot, params);
        //设置全屏生效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return this;
    }

    protected void wrapperView(View view) {
        linearLayoutRoot.removeAllViews();
        linearLayoutRoot.addView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
    }

    // ------------------------setContentView

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        this.setContentView(view, null);
    }

    public void setContentView(int layoutResID, ViewGroup.LayoutParams params) {
        View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        this.setContentView(view, params);
    }

    @Override
    public void setContentView(View view) {
        this.setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setDialogView(view, params);
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception ignored) {
        }
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
        getContext().startActivity(intent);
    }


    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, event)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
