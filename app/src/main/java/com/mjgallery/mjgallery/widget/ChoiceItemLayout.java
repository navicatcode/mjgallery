package com.mjgallery.mjgallery.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.mjgallery.mjgallery.R;

/**
 * 列表单选
 */

public class ChoiceItemLayout extends LinearLayout implements Checkable {

    private boolean mChecked;

    public ChoiceItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundResource(checked ? R.drawable.shape_border_golden_bg_gray : android.R.color.transparent);
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
