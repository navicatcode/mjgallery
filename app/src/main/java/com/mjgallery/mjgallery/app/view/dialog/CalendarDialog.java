package com.mjgallery.mjgallery.app.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.CalendarViewDelegate;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.ui.adapter.OpenPrizeAdapter;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.widget.PopupWindow.CommonPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
public class CalendarDialog extends BaseDialog implements
        CalendarView.OnMonthChangeListener,
        CalendarView.OnCalendarSelectListener,
        CommonPopupWindow.ViewInterface {
    private CalendarView mCalendarView;
    private LinearLayout llCalendarYear;
    private LinearLayout llCalendarMonth;
    private TextView mTvCalendarMonth;
    private TextView mTvCalendarYear;
    private TextView tvChaZhaoYear;
    private TextView tvChaZhaoMonth;
    private Context mContext;
    private int mYear, mMonth;
    private CommonPopupWindow popupWindow;
    private List<Integer> openPrizeYearList = new ArrayList<>();
    private List<Integer> openPrizeMonthList = new ArrayList<>();
    private OpenPrizeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int onClickYear = 0;
    private ImageView ivChaZhaoYear;
    private ImageView ivChaZhaoMonth;
    List<String> dateStrList;
    CalendarMonthChangeListener calendarMonthChangeListener;

    public CalendarDialog(Activity activity,List<String> dateStrList,CalendarMonthChangeListener calendarMonthChangeListener) {
        super(activity);
        mContext = activity;
        this.dateStrList=dateStrList;
        this.calendarMonthChangeListener=calendarMonthChangeListener;
        init();

    }


    private void init() {
        setContentView(R.layout.dialog_calendar);
        mCalendarView = findViewById(R.id.calendarView);
        llCalendarYear = findViewById(R.id.llCalendarYear);
        llCalendarMonth = findViewById(R.id.llCalendarMonth);
        mTvCalendarYear = findViewById(R.id.tvCalendarYear);
        mTvCalendarMonth = findViewById(R.id.tvCalendarMonth);
        tvChaZhaoYear = findViewById(R.id.tvChaZhaoYear);
        tvChaZhaoMonth = findViewById(R.id.tvChaZhaoMonth);
        ivChaZhaoYear = findViewById(R.id.ivChaZhaoYear);
        ivChaZhaoMonth = findViewById(R.id.ivChaZhaoMonth);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);

        CalendarViewDelegate.color = mContext.getResources().getColor(R.color.color_333333);//初始化日历默认字体颜色
        mCalendarView.getmDelegate().setMonthViewScrollable(true);//默认月份左滑右滑

        initData();
        initEvent();
    }

    private void initEvent() {
        llCalendarYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickYear = 1;
                downPopupWindow(view);
            }
        });
        llCalendarMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickYear = 2;
                downPopupWindow(view);
            }
        });
    }

    private void downPopupWindow(View v) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        popupWindow = new CommonPopupWindow.Builder(mContext)
                .setView(R.layout.pop_menu)
                .setWidthAndHeight(llCalendarYear.getWidth(), 480)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(true)
                .create();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void onMonthChange(int year, int month) {
        this.mYear = year;
        this.mMonth = month;
        mTvCalendarYear.setText(year + " 年");
        mTvCalendarMonth.setText(Utils.getMonthByInt(month) + " 月");
        change(year, month);
        if(onClickYear ==1){
            setTextStyle(tvChaZhaoYear,ivChaZhaoYear,true);
        }else{
            tvChaZhaoYear.setText(""+year);
        }

        if(onClickYear == 2){
            setTextStyle(tvChaZhaoMonth,ivChaZhaoMonth,true);
        }else{
            if (month < 10) {
                tvChaZhaoMonth.setText("0" + month);
            } else {
                tvChaZhaoMonth.setText("" + month);
            }
        }
        calendarMonthChangeListener.changeMonthData(year,month);
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Log.e("ddd","CCCCC");
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //只有选中单个日期，才请求获取指定日的数据
        if(isClick){
            calendarMonthChangeListener.lotteryRecord(calendar.getYear(),calendar.getMonth(),calendar.getDay());
        }
    }

    private void change(int year, int month) {
        if (year < mCalendarView.getCurYear() || (year == mCalendarView.getCurYear() && month < mCalendarView.getCurMonth())) {
            mCalendarView.getmDelegate().setCurrentMonthTextColor(mContext.getResources().getColor(R.color.gray));
//            mCalendarView.getmDelegate().setMonthViewScrollable(true);//禁止月视图滚动后就不能滚动了
        } else if (year == mCalendarView.getCurYear() && month == mCalendarView.getCurMonth()) {
            initData();
            mCalendarView.getmDelegate().setCurrentMonthTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            mCalendarView.getmDelegate().setCurrentMonthTextColor(mContext.getResources().getColor(R.color.black));
        }
    }


    public void showCalendarDialog() {
        mCalendarView.scrollToCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), 1);
        mCalendarView.clearSingleSelect();//清除单选
        initData();
        super.show();
    }

    private void initData() {
        mYear = mCalendarView.getCurYear();
        mMonth = mCalendarView.getCurMonth();
        mTvCalendarYear.setText(mYear + " 年");
        mTvCalendarMonth.setText(Utils.getMonthByInt(mMonth) + " 月");
        tvChaZhaoYear.setText("" + mYear);
        if (mMonth < 10) {
            tvChaZhaoMonth.setText("0" + mMonth);
        } else {
            tvChaZhaoMonth.setText("" + mMonth);
        }
        for (int i = mYear; i > mYear - 10; i--) {
            openPrizeYearList.add(i);
        }
        for (int i = 12; i > 0; i--) {
            openPrizeMonthList.add(i);
        }
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        for (int i = 1; i < mCalendarView.getCurDay(); i++) {//当月之前已经过了的日子置为白圈灰色字体 之后默认黑色
            map.put(getSchemeCalendar(year, month, i, mContext.getResources().getColor(R.color.white), "多").toString(),
                    getSchemeCalendar(year, month, i, mContext.getResources().getColor(R.color.white), "多"));
        }

//        for(int i=0;i<dateStrList.size();i++){
//            int day=Integer.parseInt(dateStrList.get(i).substring(8,10));
//            map.put(getSchemeCalendar(year, month, day, 0xFF13acff, "假").toString(),
//                    getSchemeCalendar(year, month, day, 0xFF13acff, "假"));
//        }

        mCalendarView.setSchemeDate(map);
    }

    public void random(List<String> dateList) {
        Map<String, Calendar> map = new HashMap<>();

        boolean isYearEqu=Integer.parseInt(tvChaZhaoYear.getText().toString())==mCalendarView.getCurYear();
        boolean isMonthEqu=Integer.parseInt(tvChaZhaoMonth.getText().toString())==mCalendarView.getCurMonth();
        if(isYearEqu &&  isMonthEqu){
            for (int i = 1; i < mCalendarView.getCurDay(); i++) {//当月之前已经过了的日子置为白圈灰色字体 之后默认黑色
                map.put(getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), i, mContext.getResources().getColor(R.color.white), "多").toString(),
                        getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), i, mContext.getResources().getColor(R.color.white), "多"));
            }
        }
        if(dateList.size()>0){
            int year=Integer.parseInt(dateList.get(0).substring(0,4));
            int month=Integer.parseInt(dateList.get(0).substring(5,7));

            for(int i=0;i<dateList.size();i++){
                int day=Integer.parseInt(dateList.get(i).substring(8,10));
                map.put(getSchemeCalendar(year, month, day, 0xFF13acff, "假").toString(),
                        getSchemeCalendar(year, month, day, 0xFF13acff, "假"));
            }
        }
        mCalendarView.setSchemeDate(map);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.pop_menu:
                RecyclerView rv_open_prize = view.findViewById(R.id.rv_open_prize);
                mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                int selectYear = Integer.parseInt(mTvCalendarYear.getText().toString().split(" ")[0]);
                if (onClickYear == 1) {//年
                    mAdapter = new OpenPrizeAdapter(mContext, openPrizeYearList);
                } else if (onClickYear == 2) {//月
                    openPrizeMonthList.clear();
                    for (int i = 12; i > 0; i--) {
                        openPrizeMonthList.add(i);
                    }
                    mAdapter = new OpenPrizeAdapter(mContext, openPrizeMonthList);
                    // }
                }

                rv_open_prize.setLayoutManager(mLayoutManager);
                rv_open_prize.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new OpenPrizeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int integer) {
                        if (onClickYear == 1) {
                            mTvCalendarYear.setText(integer + " 年");
                        } else if (onClickYear == 2) {
                            mTvCalendarMonth.setText(Utils.getMonthByInt(integer) + " 月");
                        }
                        int _year = Integer.parseInt(mTvCalendarYear.getText().toString().split(" ")[0]);
                        int _month = Utils.getMonthByString(mTvCalendarMonth.getText().toString().split(" ")[0]);
                        tvChaZhaoYear.setText("" + _year);
                        if (_month < 10) {
                            tvChaZhaoMonth.setText("0" + _month);
                        } else {
                            tvChaZhaoMonth.setText("" + _month);
                        }

                        mCalendarView.scrollToCalendar(_year, _month, 1);
                        mCalendarView.clearSingleSelect();//清除单选
                        popupWindow.dismiss();
                        if(_year == mCalendarView.getCurYear() && _month == mCalendarView.getCurMonth()){
                            initData();
                        }else
                            calendarMonthChangeListener.changeMonthData(_year,_month);
                    }
                });
                break;
        }
    }


    //设置年与月的颜色
    public void setTextStyle(TextView tv1,ImageView iv1,boolean status){

        if(status){
            tv1.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0EB4FE));
            iv1.setImageResource(R.drawable.calendar_dui_gou_click);
        }else{
            tv1.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_999999));
            iv1.setImageResource(R.drawable.calendar_dui_gou);
        }

    }

    //每次show出Dialog时，初始一下年与月的颜色
    public void initStyle(){
        setTextStyle(tvChaZhaoYear,ivChaZhaoYear,false);
        setTextStyle(tvChaZhaoMonth,ivChaZhaoMonth,false);
    }

    public interface  CalendarMonthChangeListener{
        //app按月份查询开奖日期接口
        void changeMonthData(int year, int month);
        //app开奖记录接口
        void lotteryRecord(int year, int month, int day);
    };

}
