package com.mjgallery.mjgallery.widget.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarViewDelegate;
import com.haibin.calendarview.MonthView;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class SignInRewardMonthView extends MonthView {

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

    public SignInRewardMonthView(Context context) {
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
        mPointRadius = ArmsUtils.dip2px(getContext(), 0);

        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(ArmsUtils.getColor(getContext(), R.color.color_0EB4FE));

    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 7 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.getScheme().equals("签到奖励")) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.already_signed_in_img);//将drawable下的图片用canvas画出来
            canvas.drawBitmap(bmp, (float) (cx - mPadding / 2.1), (float) (cy + mPadding * 3), null);
            Bitmap bmpQianDao = BitmapFactory.decodeResource(getResources(), R.drawable.duo_zhong_jiang_li_img);//将drawable下的图片用canvas画出来
            canvas.drawBitmap(bmpQianDao, (float) (cx - mPadding * 1.5), (float) (cy - mPadding * 1.7), mPointPaint);
        } else if (calendar.getScheme().equals("真")) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.already_signed_in_img);//将drawable下的图片用canvas画出来
            canvas.drawBitmap(bmp, (float) (cx - mPadding / 2.1), (float) (cy + mPadding * 3), null);
        } else if (calendar.getScheme().equals("签到")) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.duo_zhong_jiang_li_img);//将drawable下的图片用canvas画出来
            canvas.drawBitmap(bmp, (float) (cx - mPadding * 1.5), (float) (cy - mPadding * 1.7), mPointPaint);
        } else {
            boolean isSelected = isSelected(calendar);
            if (isSelected) {
                mPointPaint.setColor(ArmsUtils.getColor(context, R.color.color_e8e8e8));
            } else {
                mPointPaint.setColor(ArmsUtils.getColor(context, R.color.color_e8e8e8));
            }
            canvas.drawCircle(cx, cy, mRadius, mPointPaint);
        }

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.isCurrentDay()) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }
            if (isSelected) {
                if(!calendar.getScheme().equals("签到奖励") && !calendar.getScheme().equals("签到")){
                    if (calendar.isCurrentDay()) {
                        mSelectTextPaint.setColor(ArmsUtils.getColor(context,R.color.white));
                        canvas.drawText(String.valueOf(calendar.getDay()),
                                cx,
                                baselineY,
                                mSelectTextPaint);
                    }else {
                        mSelectTextPaint.setColor(ArmsUtils.getColor(context,R.color.color_333333));
                        canvas.drawText(String.valueOf(calendar.getDay()),
                                cx,
                                baselineY,
                                mSelectTextPaint);
                    }

                } else {
                    if (calendar.isCurrentDay()) {
                        mSelectTextPaint.setColor(ArmsUtils.getColor(context,R.color.white));
                        canvas.drawText(String.valueOf(calendar.getDay()),
                                cx,
                                baselineY,
                                mSelectTextPaint);
                    }
                }

            } else if (hasScheme) {
                Log.e("onDrawText=====","onDrawText====calendar.getScheme().equals(\"签到奖励\")==="+calendar.getScheme().equals("签到奖励"));
                Log.e("onDrawText=====","onDrawText====calendar.getScheme().equals(\"签到\")==="+calendar.getScheme().equals("签到"));
                if(!calendar.getScheme().equals("签到奖励") && !calendar.getScheme().equals("签到")){
                    mSelectTextPaint.setColor(ArmsUtils.getColor(context,R.color.color_333333));
                    canvas.drawText(String.valueOf(calendar.getDay()),
                            cx,
                            baselineY,
                            calendar.isCurrentDay() ? mCurDayTextPaint :
                                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
                }
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
