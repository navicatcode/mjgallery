package com.mjgallery.mjgallery.header;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mjgallery.mjgallery.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Created by solo on 2018/2/1.
 * SmartRefreshLayout的自定义Footer
 */

public class CustomRefreshFooter extends LinearLayout implements RefreshFooter {

    public CustomRefreshFooter(Context context, String text) {
        super(context);
        View view = View.inflate(context, R.layout.widget_custom_refresh_footer, this);
        TextView tv = (TextView) findViewById(R.id.tv_refresh_footer);
        tv.setText(text);
    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Scale;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }


    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }


    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
