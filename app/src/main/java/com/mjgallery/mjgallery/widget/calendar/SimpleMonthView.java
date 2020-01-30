package com.mjgallery.mjgallery.widget.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarViewDelegate;
import com.haibin.calendarview.MonthView;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class SimpleMonthView extends MonthView {

    private int mRadius;
    private Context context;
    private int mPadding;
    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();

    /**
     * 圆点半径
     */
    private float mPointRadius;

    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();

    public SimpleMonthView(Context context) {
        super(context);
        this.context = context;
        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);
        mPadding = ArmsUtils.dip2px(getContext(), 6);
        mPointRadius =  ArmsUtils.dip2px(getContext(), 0);

        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(ArmsUtils.getColor(getContext(),R.color.color_ff8181));

    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        float cx = x + mItemWidth / 2f - dip2px(16);
        float cy = y + mItemHeight / 2f - dip2px(16);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dang_qian_ri_qi);//将drawable下的图片用canvas画出来
        canvas.drawBitmap(bmp, cx, cy, null);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        boolean isSelected = isSelected(calendar);
        if (isSelected) {
            mPointPaint.setColor(Color.WHITE);
        } else {
            mPointPaint.setColor(Color.GRAY);
        }

        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            mCurMonthTextPaint.setColor(CalendarViewDelegate.color);
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint : calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @return
     */

    public int dip2px(float dipValue) {

        final float scale = this.context.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);

    }
}
