package com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.CalendarViewDelegate;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.ui.adapter.OpenPrizeAdapter;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.di.component.DaggerLotteryDateComponent;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryDateContract;
import com.mjgallery.mjgallery.mvp.presenter.lottery.LotteryDatePresenter;
import com.mjgallery.mjgallery.widget.PopupWindow.CommonPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:开奖日期界面
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 22:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class LotteryDateActivity extends MJBaseActivity<LotteryDatePresenter> implements LotteryDateContract.View,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnCalendarSelectListener {


    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.ivTopTitle)
    ImageView ivTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.ivTopRight)
    TextView ivTopRight;
    @BindView(R.id.ivTopRightImg)
    ImageView ivTopRightImg;
    @BindView(R.id.tvTopRightImgYear)
    TextView tvTopRightImgYear;
    @BindView(R.id.rlTopRight)
    RelativeLayout rlTopRight;
    @BindView(R.id.tvChaZhaoYear)
    TextView tvChaZhaoYear;
    @BindView(R.id.llCalendarTopTwo)
    LinearLayout llCalendarTopTwo;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.tv_open_prize)
    TextView tvOpenPrize;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.llXiangZuo)
    LinearLayout llXiangZuo;
    @BindView(R.id.llXiangYou)
    LinearLayout llXiangYou;
    @BindView(R.id.tvMonthEnglish)
    TextView tvMonthEnglish;
    private int mYear, mMonth;
    private int onClickYear = 0;
    List<String> dateStrList=new ArrayList<>();
    private List<Integer> openPrizeYearList = new ArrayList<>();
    private List<Integer> openPrizeMonthList = new ArrayList<>();
    private CommonPopupWindow popupWindow;
    private RecyclerView.LayoutManager mLayoutManager;
    private OpenPrizeAdapter mAdapter;
    boolean initStatus=true;//初始状态记录，第一次请求数据后改为false
    boolean openingStatus=false;//true时表示今天是开奖日期，false不是
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLotteryDateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lottery_date; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date));
        tvTopTitle.setVisibility(View.VISIBLE);

        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);

        CalendarViewDelegate.color = getResources().getColor(R.color.color_333333);//初始化日历默认字体颜色
        mCalendarView.getmDelegate().setMonthViewScrollable(true);//默认月份左滑右滑
        setInitData();
        //默认请求当前的年份和月份的中奖日期
        mPresenter.requestPlanListByMonth(DateUtils.getYear()+"",DateUtils.getMonth()+"");
    }


    private void setInitData() {
        this.mYear = mCalendarView.getCurYear();
        this.mMonth = mCalendarView.getCurMonth();
        tvChaZhaoYear.setText(mYear+"年"+mMonth+"月");
        setEnglishMonth();
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
            map.put(getSchemeCalendar(year, month, i, getResources().getColor(R.color.white), "多").toString(),
                    getSchemeCalendar(year, month, i, getResources().getColor(R.color.white), "多"));
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
    public void onMonthChange(int year, int month) {
        this.mYear = year;
        this.mMonth = month;
        change(year, month);
        tvChaZhaoYear.setText(year+"年"+month+"月");
        setEnglishMonth();
        mPresenter.requestPlanListByMonth(year+"",month+"");
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Log.e("ddd","CCCCC");
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //选中单个日期,判断选中的日期是开奖日，还是非开奖日
        if(isClick){
            String dateNow=new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTimeInMillis());
            for(String dateStr:dateStrList) {
                if (dateNow.equals(dateStr)){
                    tvOpenPrize.setText(dateStr + ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt2));
                    return;
                }
            }
            tvOpenPrize.setText(dateNow+ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt3));
        }
    }

    private void change(int year, int month) {
        if (year < mCalendarView.getCurYear() || (year == mCalendarView.getCurYear() && month < mCalendarView.getCurMonth())) {
            mCalendarView.getmDelegate().setCurrentMonthTextColor(this.getResources().getColor(R.color.gray));
//            mCalendarView.getmDelegate().setMonthViewScrollable(true);//禁止月视图滚动后就不能滚动了
        } else if (year == mCalendarView.getCurYear() && month == mCalendarView.getCurMonth()) {
            setInitData();
            mCalendarView.getmDelegate().setCurrentMonthTextColor(this.getResources().getColor(R.color.black));
        } else {
            mCalendarView.getmDelegate().setCurrentMonthTextColor(this.getResources().getColor(R.color.black));
        }
    }

    public void random() {
        Map<String, Calendar> map = new HashMap<>();
        boolean isYearEqu=(mYear==mCalendarView.getCurYear());
        boolean isMonthEqu=(mMonth==mCalendarView.getCurMonth());
        if(isYearEqu &&  isMonthEqu){
            for (int i = 1; i < mCalendarView.getCurDay(); i++) {//当月之前已经过了的日子置为白圈灰色字体 之后默认黑色
                map.put(getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), i, getResources().getColor(R.color.white), "多").toString(),
                        getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), i, getResources().getColor(R.color.white), "多"));
            }
        }
        if(dateStrList.size()>0){
            int year=Integer.parseInt(dateStrList.get(0).substring(0,4));
            int month=Integer.parseInt(dateStrList.get(0).substring(5,7));
            for(int i=0;i<dateStrList.size();i++){

                int day=Integer.parseInt(dateStrList.get(i).substring(8,10));

                boolean isBool=(year==mCalendarView.getCurYear()) && (month==mCalendarView.getCurMonth()) && day == mCalendarView.getCurDay();
                if(!isBool){//开奖日不是今天，才给它加上蓝圈
                  map.put(getSchemeCalendar(year, month, day, 0xFF13acff, "假").toString(),
                          getSchemeCalendar(year, month, day, 0xFF13acff, "假"));
                }
                //如果今天是开奖日，标记一下
                if(isBool){
                    openingStatus=true;
                }

            }
        }

        if(initStatus){
            if(openingStatus)
                tvOpenPrize.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt1)+DateUtils.getCurrentTimeToday()+
                        ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt2));
            else
                tvOpenPrize.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt1)+DateUtils.getCurrentTimeToday()+
                        ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_date_txt3));
        }
        initStatus=false;

        mCalendarView.setSchemeDate(map);
    }

    @Override
    public void onPlanListByMonth(List<String> dayList) {

        dateStrList.clear();
        if(dayList!=null){
            dateStrList.addAll(dayList);
            random();
        }
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        if (message == null)
            return;
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.ivTopReturn,R.id.llXiangZuo,R.id.llXiangYou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.llXiangZuo:
                mCalendarView.scrollToPre(true);
                break;
            case R.id.llXiangYou:
                mCalendarView.scrollToNext(true);
                break;
        }
    }



    @Override
    protected void requestData() {

    }


    public void setEnglishMonth(){
        switch (mMonth){
            case 1:
                tvMonthEnglish.setText("JAN");
                break;
            case 2:
                tvMonthEnglish.setText("FEB");
                break;
            case 3:
                tvMonthEnglish.setText("MAR");
                break;
            case 4:
                tvMonthEnglish.setText("APR");
                break;
            case 5:
                tvMonthEnglish.setText("MAY");
                break;
            case 6:
                tvMonthEnglish.setText("JUN");
                break;
            case 7:
                tvMonthEnglish.setText("JUL");
                break;
            case 8:
                tvMonthEnglish.setText("AUG");
                break;
            case 9:
                tvMonthEnglish.setText("SEP");
                break;
            case 10:
                tvMonthEnglish.setText("OCT");
                break;
            case 11:
                tvMonthEnglish.setText("NOV");
                break;
            case 12:
                tvMonthEnglish.setText("DEC");
                break;

        }
    }

}
