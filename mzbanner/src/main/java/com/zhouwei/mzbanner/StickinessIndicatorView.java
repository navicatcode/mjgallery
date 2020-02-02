package com.zhouwei.mzbanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by cpt on 2018/8/7.
 * 不知道叫啥效果的指示器
 */
public class StickinessIndicatorView extends View {
    float mPositionOffset = 0f;//一开始是没移动的，所以是0
    float mAllOffset = 0f;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mSelectColor = 0xffff4d4d;//255,77,77
    private int mNormalColor = 0x80FFFFFF;//216,216,216
    private int mLineHeight = DrawUtils.dip2px(10);
    private int mLineSelectWidth = DrawUtils.dip2px(34);
    private int mLineNormalWidth = DrawUtils.dip2px(12);
    private int mIntervalWidth = mLineSelectWidth - mLineNormalWidth;//选中长度和未选中长度之差
    private int mRadius = DrawUtils.dip2px(8);
    private int mPadding = DrawUtils.dip2px(12);
    private int mCount = 0;
    private int mStartX = 0;//开始的X坐标
    private int mStartY = 0;//开始的Y坐标
    private int mLeftSelect;//动画过程中的左边的index
    private int mRightSelect;//动画过程中的右边的index
    private int count = 1;

    public StickinessIndicatorView(Context context) {
        this(context, null);
    }

    public StickinessIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickinessIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mStartX = mWidth / 2 - (mLineSelectWidth + mLineNormalWidth + mPadding * this.count) / 2;
        mStartY = (mHeight - mLineHeight) / 2;
    }


    public void setStartX() {
        mStartX = mWidth / 2 - (mLineSelectWidth + mLineNormalWidth + mPadding * this.count) / 2;
    }

    public void setViewPager(ViewPager viewPager, int count) {
        if (viewPager.getAdapter() == null) return;
        this.mCount = count;
        invalidate();
    }


    public void setOnPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mAllOffset = positionOffset + position;
        mLeftSelect = (int) mAllOffset;
        mRightSelect = mLeftSelect + 1;
        mPositionOffset = positionOffset;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF;
        for (int i = 0; i < mCount; i++) {
            rectF = new RectF();
            if (i == 0) {
                rectF.left = mStartX;
            } else {
                if (i > mLeftSelect) {
                    rectF.left = mStartX + (i * mPadding) + (mLineSelectWidth - (i == mRightSelect ? (mPositionOffset * mIntervalWidth) : 0) + mLineNormalWidth * (i - 1));
                } else {
                    rectF.left = mStartX + (i * mPadding) + (mLineNormalWidth * i);
                }
            }

            if (i > mLeftSelect) {
                rectF.right = mStartX + (i * mPadding) + (mLineSelectWidth + mLineNormalWidth * i);
            } else if (i < mLeftSelect) {
                rectF.right = mStartX + (i * mPadding) + (mLineNormalWidth * (i + 1));
            } else {
                rectF.right = mStartX + (i * mPadding) + (i == mLeftSelect ? mLineSelectWidth - (mPositionOffset * mIntervalWidth) : mLineNormalWidth) + (i * mLineNormalWidth);
            }

            rectF.top = mStartY;
            rectF.bottom = mStartY + mLineHeight;

            //255,77,77 216,216,216
            //255-216 = 39
            //216-77  = 139

            if (i == mLeftSelect) {
                mPaint.setColor(mNormalColor);
            } else if (i == mRightSelect) {
                mPaint.setColor(mNormalColor);
            } else {
                mPaint.setColor(mNormalColor);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
            } else {
                canvas.drawRect(rectF, mPaint);
            }
        }
    }
}
