package com.mjgallery.mjgallery.mvp.ui.activity.mine.signinreward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.CalendarViewDelegate;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveErrorDialog;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveSuccessDialog;
import com.mjgallery.mjgallery.app.view.dialog.signinreward.SignInRewardDetailsDialog;
import com.mjgallery.mjgallery.app.view.dialog.signinreward.SignInRewardErrorDialog;
import com.mjgallery.mjgallery.di.component.DaggerSignInRewardComponent;
import com.mjgallery.mjgallery.mvp.contract.mine.signinreward.SignInRewardContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRewardBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.signinreward.SignInRewardPresenter;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:签到奖励
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SignInRewardActivity extends BaseActivity<SignInRewardPresenter> implements
        SignInRewardContract.View, CalendarView.OnMonthChangeListener,
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
    @BindView(R.id.btnQueDing)
    TextView btnQueDing;
    @BindView(R.id.rlQueDing)
    RelativeLayout rlQueDing;
    @BindView(R.id.ivRightHomeSearch)
    ImageView ivRightHomeSearch;
    @BindView(R.id.llTopReturnAll)
    RelativeLayout llTopReturnAll;
    @BindView(R.id.tvSignInMonth)
    TextView tvSignInMonth;
    @BindView(R.id.tvSignInToDay)
    TextView tvSignInToDay;
    String year;
    String month;
    String monthTwo;
    @BindView(R.id.mSignInRewardProgressBar)
    ProgressBar mSignInRewardProgressBar;
    @BindView(R.id.ivSignInRewardToDay3)
    UIImageView ivSignInRewardToDay3;
    @BindView(R.id.ivSignInRewardToDay10)
    UIImageView ivSignInRewardToDay10;
    @BindView(R.id.ivSignInRewardToDay20)
    UIImageView ivSignInRewardToDay20;
    @BindView(R.id.ivSignInRewardToDayTotal)
    UIImageView ivSignInRewardToDayTotal;
    @BindView(R.id.tvToDayTotal)
    TextView tvToDayTotal;
    int dayCount = 0;
    @BindView(R.id.btn3ToDayYes)
    Button btn3ToDayYes;
    @BindView(R.id.btn3ToDayNo)
    Button btn3ToDayNo;
    @BindView(R.id.btn10ToDayYes)
    Button btn10ToDayYes;
    @BindView(R.id.btn10DayNo)
    Button btn10DayNo;
    @BindView(R.id.btn20ToDayYes)
    Button btn20ToDayYes;
    @BindView(R.id.btn20ToDayNo)
    Button btn20ToDayNo;
    @BindView(R.id.btnTotalToDayYes)
    Button btnTotalToDayYes;
    @BindView(R.id.btnTotalToDayNo)
    Button btnTotalToDayNo;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.ivSignInRewardLeft)
    UIImageView ivSignInRewardLeft;
    @BindView(R.id.tvSignInRewardYears)
    TextView tvSignInRewardYears;
    @BindView(R.id.ivSignInRewardRight)
    UIImageView ivSignInRewardRight;
    @BindView(R.id.rlSignInRewar)
    RelativeLayout rlSignInRewar;
    @BindView(R.id.btnRewards)
    Button btnRewards;
    @BindView(R.id.btnRewardsNo)
    Button btnRewardsNo;
    MyVIPReceiveSuccessDialog mMyVIPReceiveSuccessDialog;


    MyVIPReceiveErrorDialog mMyVIPReceiveErrorDialog;
    SignInRewardDetailsDialog mSignInRewardDetailsDialog;
    SignInRewardErrorDialog mSignInRewardErrorDialog;
    Calendar mCalendar;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignInRewardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_in_reward; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mMyVIPReceiveSuccessDialog = new MyVIPReceiveSuccessDialog(this);
        mMyVIPReceiveErrorDialog = new MyVIPReceiveErrorDialog(this);


        mSignInRewardDetailsDialog = new SignInRewardDetailsDialog(this);
        mSignInRewardErrorDialog = new SignInRewardErrorDialog(this);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        mCalendar = Calendar.getInstance();
        llTopReturnAll.setBackgroundColor(ArmsUtils.getColor(getBaseContext(), R.color.color_0EB4FE));
        GlideUtil.imageNormalLoader(ivTopReturn, null, R.drawable.activity_return_while_img);
        tvTopTitle.setText(ArmsUtils.getString(getBaseContext(), R.string.sign_in_reward));
        tvTopTitle.setTextColor(ArmsUtils.getColor(getBaseContext(), R.color.white));
        tvSignInMonth.setText(DateUtils.getChineseMonth(getBaseContext()) + ArmsUtils.getString(getBaseContext(), R.string.mine_my_qiedao));
        month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
        if (Integer.valueOf(month) < 10) {
            monthTwo = "0" + month;
        }

        year = DateUtils.getChineseYear(getBaseContext());
        mCalendar.setTime(mCalendar.getTime());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ArmsUtils.getString(getBaseContext(), R.string.sign_in_reward_title_01));
        dayCount = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        stringBuffer.append(dayCount);
        stringBuffer.append(ArmsUtils.getString(getBaseContext(), R.string.sign_in_reward_title_02));
        tvSignInToDay.setText(stringBuffer.toString());
        mSignInRewardProgressBar.setMax(dayCount);
        tvToDayTotal.setText(dayCount + ArmsUtils.getString(getBaseContext(), R.string.day));
        CalendarViewDelegate.color = getResources().getColor(R.color.color_333333);//初始化日历默认字体颜色
        mCalendarView.getmDelegate().setMonthViewScrollable(true);//默认月份左滑右滑
        StringBuffer stringBufferInRewardYears = new StringBuffer();
        stringBufferInRewardYears.append(year);
        stringBufferInRewardYears.append(ArmsUtils.getString(getBaseContext(), R.string.year));
        stringBufferInRewardYears.append(DateUtils.getChineseMonthThree(getBaseContext()));
        stringBufferInRewardYears.append("月");
        tvSignInRewardYears.setText(stringBufferInRewardYears.toString());
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        setSignRecord();
        getRewardView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
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


    @OnClick({R.id.ivTopReturn, R.id.btnRewards, R.id.ivSignInRewardLeft, R.id.ivSignInRewardRight, R.id.ivSignInRewardToDay3, R.id.ivSignInRewardToDay10, R.id.ivSignInRewardToDay20, R.id.ivSignInRewardToDayTotal, R.id.btn3ToDayYes, R.id.btn3ToDayNo, R.id.btn10ToDayYes, R.id.btn10DayNo, R.id.btn20ToDayYes, R.id.btn20ToDayNo, R.id.btnTotalToDayYes, R.id.btnTotalToDayNo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivSignInRewardToDay3:
                getRewardView(0, 0, 0, 3);
                break;
            case R.id.btnRewards:
                getReward(0);
                break;
            case R.id.ivSignInRewardToDay10:
                getRewardView(0, 0, 0, 10);
                break;
            case R.id.ivSignInRewardToDay20:
                getRewardView(0, 0, 0, 20);
                break;
            case R.id.ivSignInRewardToDayTotal:
                getRewardView(0, 0, 0, dayCount);
                break;
            case R.id.btn3ToDayYes:
                getReward(3);
                break;
            case R.id.btn10ToDayYes:
                getReward(10);
                break;
            case R.id.btn20ToDayYes:
                getReward(20);
                break;
            case R.id.btnTotalToDayYes:
                getReward(dayCount);
                break;
            case R.id.ivSignInRewardLeft:
                mCalendarView.scrollToPre(true);
                break;
            case R.id.ivSignInRewardRight:
                mCalendarView.scrollToNext(true);
                break;
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        this.year = String.valueOf(year);
        this.month = String.valueOf(month);
        StringBuffer stringBufferInRewardYears = new StringBuffer();
        stringBufferInRewardYears.append(year);
        stringBufferInRewardYears.append(ArmsUtils.getString(getBaseContext(), R.string.year));
        stringBufferInRewardYears.append(month);
        stringBufferInRewardYears.append(ArmsUtils.getString(getBaseContext(), R.string.month));
        tvSignInRewardYears.setText(stringBufferInRewardYears.toString());
        setSignRecord();
    }

    @Override
    public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        if (calendar.getMonth() == mCalendar.get(Calendar.MONTH) + 1) {
            getRewardView(calendar.getYear(), calendar.getMonth(), calendar.getDay(), 0);
        }

    }


    @Override
    public void onSignRecord(SignRecordBean signRecordBean) {
        //开奖记录接口回调
        if (signRecordBean != null) {
            //判断是否是左右滑动是的年份和月份
            if (signRecordBean.getMonth().equals(month) && signRecordBean.getYear().equals(year)) {
                int compareToMonth = monthTwo.compareTo(DateUtils.getChineseMonthTwo(getBaseContext()));
                int compareToYear = year.compareTo(DateUtils.getChineseYear(getBaseContext()));
                Log.e("onSignRecord===", "====compareToMonth=" + compareToMonth);
                Log.e("onSignRecord===", "====compareToYear=" + compareToYear);
                if (compareToYear == 0) {
                    //年份是当前年
                    //判断月份是否小于等于当前月
                    if (compareToMonth == 0) {
                        setSchemeCalendar(signRecordBean.getArray(), true);
                    } else if (compareToMonth < 0) {
                        setSchemeCalendar(signRecordBean.getArray(), false);
                    }
                } else if (compareToYear < 0) {
                    //年份小于当前年
                    setSchemeCalendar(signRecordBean.getArray(), false);
                } else {
                    //比当前年大 不处理
                }
            }
        }

    }

    @Override
    public void onRewardView(RewardViewBean rewardViewBean) {
        if (rewardViewBean != null) {
            setBtnVisibility(btnRewards, btnRewardsNo, rewardViewBean.isSignStatus());
            mSignInRewardProgressBar.setProgress(rewardViewBean.getTotalSignCount());
            int totalSignCount = rewardViewBean.getTotalSignCount();
            for (int i = 0; i < rewardViewBean.getAccumulateSign().size(); i++) {
                int signStatus = rewardViewBean.getAccumulateSign().get(i);
                switch (i) {
                    case 0:
                        if (totalSignCount < 3) {
                            btn3ToDayYes.setVisibility(View.GONE);
                            btn3ToDayNo.setVisibility(View.GONE);
                        } else {
                            setBtnVisibility(btn3ToDayYes, btn3ToDayNo, signStatus);
                        }

                        break;
                    case 1:
                        if (totalSignCount < 10) {
                            btn10ToDayYes.setVisibility(View.GONE);
                            btn10DayNo.setVisibility(View.GONE);
                        } else {
                            setBtnVisibility(btn10ToDayYes, btn10DayNo, signStatus);
                        }

                        break;
                    case 2:
                        if (totalSignCount < 20) {
                            btn20ToDayYes.setVisibility(View.GONE);
                            btn20ToDayNo.setVisibility(View.GONE);
                        } else {
                            setBtnVisibility(btn20ToDayYes, btn20ToDayNo, signStatus);
                        }
                        break;
                    case 3:
                        if (totalSignCount < dayCount) {
                            btnTotalToDayYes.setVisibility(View.GONE);
                            btnTotalToDayNo.setVisibility(View.GONE);
                        } else {
                            setBtnVisibility(btnTotalToDayYes, btnTotalToDayNo, signStatus);
                        }

                        break;
                }
            }

        }
    }

    @Override
    public void onReward(BaseResponse baseResponse) {
        if (baseResponse.getCode() == 0) {
            RewardBean rewardBean = (RewardBean) baseResponse.getResult();
            int type = 2;
            if (rewardBean != null) {
                switch (rewardBean.getAccumulateDays()) {
                    case 0:
                        type = 2;
                        setBtnVisibility(btnRewards, btnRewardsNo, true);
                        break;
                    case 3:
                        type = 3;
                        setBtnVisibility(btn3ToDayYes, btn3ToDayNo, 1);
                        break;
                    case 10:
                        type = 3;
                        setBtnVisibility(btn10ToDayYes, btn10DayNo, 1);
                        break;
                    case 20:
                        type = 3;
                        setBtnVisibility(btn20ToDayYes, btn20ToDayNo, 1);
                        break;
                    default:
                        type = 3;
                        setBtnVisibility(btnTotalToDayYes, btnTotalToDayNo, 1);
                        break;
                }
                if (mMyVIPReceiveSuccessDialog != null && !mMyVIPReceiveSuccessDialog.isShowing()) {
                    mMyVIPReceiveSuccessDialog.showMyVIPReceiveSuccessDialogTop(rewardBean.getName(), type);
                    getRewardView();
                    month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
                    year = DateUtils.getChineseYear(getBaseContext());
                    setSignRecord();
                }
            }
        } else {
            if (mMyVIPReceiveErrorDialog != null && !mMyVIPReceiveErrorDialog.isShowing()) {
                mMyVIPReceiveErrorDialog.showMyVIPReceiveErrorDialogTop();
            }
        }
    }

    @Override
    public void onSignReward(SignRewardBean signRewardBean) {
        if (signRewardBean != null) {
            int type = 1;
            switch (signRewardBean.getAccumulateDays()) {
                case 0:
                    type = 1;
                    break;
                default:
                    type = 2;
                    break;
            }
            if (mSignInRewardDetailsDialog != null && !mSignInRewardDetailsDialog.isShowing()) {
                mSignInRewardDetailsDialog.showSignInRewardDetailsDialog(type, signRewardBean.getAccumulateDays()
                        , signRewardBean.getName(), signRewardBean.getImg());
            }
        }
    }

    @Override
    public void onSignRewardError(String errorMessage) {
        if (mSignInRewardErrorDialog != null && !mSignInRewardErrorDialog.isShowing()) {
            mSignInRewardErrorDialog.showSignInRewardErrorDialog(errorMessage);
        }
    }


    /**
     * 设置view显示和隐藏
     *
     * @param viewOne
     * @param viewTwo
     * @param signStatus
     */
    private void setBtnVisibility(View viewOne, View viewTwo, int signStatus) {
        if (signStatus != 0) {
            viewOne.setVisibility(View.GONE);
            viewTwo.setVisibility(View.VISIBLE);
        } else {
            viewOne.setVisibility(View.VISIBLE);
            viewTwo.setVisibility(View.GONE);
        }

    }


    /**
     * 设置view显示和隐藏
     *
     * @param viewOne
     * @param viewTwo
     * @param signStatus
     */
    private void setBtnVisibility(View viewOne, View viewTwo, boolean isSignStatus) {
        if (isSignStatus) {
            viewOne.setVisibility(View.GONE);
            viewTwo.setVisibility(View.VISIBLE);
        } else {
            viewOne.setVisibility(View.VISIBLE);
            viewTwo.setVisibility(View.GONE);
        }

    }

    /**
     * 设置签到签到和没有签到颜色
     */
    private void setSchemeCalendar(List<SignRecordBean.ArrayBean> arrayBeanList, boolean isMonth) {
        if (arrayBeanList != null && arrayBeanList.size() > 0) {
            Map<String, com.haibin.calendarview.Calendar> map = new HashMap<>();
            for (int i = 0; i < arrayBeanList.size(); i++) {
                SignRecordBean.ArrayBean arrayBean = arrayBeanList.get(i);
                int today = i + 1;
                if (isMonth) {
                    if (today < mCalendarView.getCurDay()) {
                        if (arrayBean.getIsSign() != 0) {
                            if (arrayBean.getIsSpecial() == 1) {
                                //已签到
                                map.put(getSchemeCalendar(Integer.valueOf(year),
                                        Integer.valueOf(month), today, getResources().getColor(R.color.trans),
                                        "签到奖励").toString(),
                                        getSchemeCalendar(Integer.valueOf(year),
                                                Integer.valueOf(month),
                                                today, getResources().getColor(R.color.trans), "签到奖励"));
                            } else {
                                //已签到
                                map.put(getSchemeCalendar(Integer.valueOf(year),
                                        Integer.valueOf(month), today, getResources().getColor(R.color.trans),
                                        "真").toString(),
                                        getSchemeCalendar(Integer.valueOf(year),
                                                Integer.valueOf(month),
                                                today, getResources().getColor(R.color.trans),
                                                "真"));
                            }

                        } else {
                            if (arrayBean.getIsSpecial() == 1) {
                                //未签到
                                map.put(getSchemeCalendar(Integer.valueOf(year),
                                        Integer.valueOf(month), today,
                                        getResources().getColor(R.color.color_e8e8e8), "签到").toString(),
                                        getSchemeCalendar(Integer.valueOf(year),
                                                Integer.valueOf(month), today,
                                                getResources().getColor(R.color.color_e8e8e8),
                                                "签到"));
                            } else {
                                //未签到
                                map.put(getSchemeCalendar(Integer.valueOf(year),
                                        Integer.valueOf(month), today,
                                        getResources().getColor(R.color.color_e8e8e8), "假").toString(),
                                        getSchemeCalendar(Integer.valueOf(year),
                                                Integer.valueOf(month), today,
                                                getResources().getColor(R.color.color_e8e8e8), "假"));
                            }

                        }
                    } else if (today == mCalendarView.getCurDay()) {
                        if (arrayBean.getIsSign() != 0) {
                            //已签到
                            map.put(getSchemeCalendar(Integer.valueOf(year),
                                    Integer.valueOf(month), today, getResources().getColor(R.color.trans),
                                    "真").toString(),
                                    getSchemeCalendar(Integer.valueOf(year),
                                            Integer.valueOf(month),
                                            today, getResources().getColor(R.color.trans),
                                            "真"));
                        }
                    } else {
                        if (arrayBean.getIsSpecial() == 1) {
                            //未签到
                            if (today != mCalendarView.getCurDay()) {
                                map.put(getSchemeCalendar(Integer.valueOf(year),
                                        Integer.valueOf(month), today,
                                        getResources().getColor(R.color.trans), "签到").toString(),
                                        getSchemeCalendar(Integer.valueOf(year),
                                                Integer.valueOf(month), today,
                                                getResources().getColor(R.color.trans), "签到"));
                            }
                        }
                    }
                } else {
                    if (arrayBean.getIsSign() != 0) {
                        if (arrayBean.getIsSpecial() == 1) {
                            //已签到
                            map.put(getSchemeCalendar(Integer.valueOf(year),
                                    Integer.valueOf(month), today, getResources().getColor(R.color.trans),
                                    "签到奖励").toString(),
                                    getSchemeCalendar(Integer.valueOf(year),
                                            Integer.valueOf(month),
                                            i + 1, getResources().getColor(R.color.trans), "签到奖励"));
                        } else {
                            map.put(getSchemeCalendar(Integer.valueOf(year),
                                    Integer.valueOf(month), today, getResources().getColor(R.color.trans),
                                    "真").toString(),
                                    getSchemeCalendar(Integer.valueOf(year),
                                            Integer.valueOf(month),
                                            i + 1, getResources().getColor(R.color.trans), "真"));
                        }

                    } else {
                        if (arrayBean.getIsSpecial() == 1) {
                            //未签到
                            map.put(getSchemeCalendar(Integer.valueOf(year),
                                    Integer.valueOf(month), today,
                                    getResources().getColor(R.color.trans), "签到").toString(),
                                    getSchemeCalendar(Integer.valueOf(year),
                                            Integer.valueOf(month), today,
                                            getResources().getColor(R.color.trans), "签到"));
                        } else {
                            //未签到
                            map.put(getSchemeCalendar(Integer.valueOf(year),
                                    Integer.valueOf(month), today,
                                    getResources().getColor(R.color.trans), "假").toString(),
                                    getSchemeCalendar(Integer.valueOf(year),
                                            Integer.valueOf(month), today,
                                            getResources().getColor(R.color.trans), "假"));
                        }

                    }
                }

            }
            mCalendarView.setSchemeDate(map);
        }
    }

    private com.haibin.calendarview.Calendar getSchemeCalendar(int year, int month, int day,
                                                               int color, String text
    ) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    /**
     * 请求签到记录数据
     */
    private void setSignRecord() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
        map.put("year", year);
        map.put("month", month);
        mPresenter.getSignRecord(map);
    }


    /**
     * 查看奖励
     */
    private void getRewardView(int year, int month, int day, int accumulateDays) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
        if (accumulateDays != 0) {
            map.put("accumulateDays", accumulateDays);
        } else {
            map.put("year", year);
            map.put("month", month);
            map.put("day", day);
        }
        mPresenter.getSignReward(map);
    }


    /**
     * 累积签到整体展示
     */
    private void getRewardView() {
        mPresenter.getRewardView(PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
    }


    private void getReward(int accumulateDays) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
        if (accumulateDays != 0) {
            map.put("accumulateDays", accumulateDays);
        }
        mPresenter.getReward(map);

    }
}
