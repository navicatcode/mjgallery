package com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.app.view.dialog.CalendarDialog;
import com.mjgallery.mjgallery.di.component.DaggerLotteryRecordComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryRecordContract;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.lottery.LotteryRecordOptimizeBean;
import com.mjgallery.mjgallery.mvp.presenter.lottery.LotteryRecordPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.lottery.LotteryOptimizeRecordAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.lottery.LotteryRecordAdapter;
import com.mjgallery.mjgallery.widget.stickyitemdecoration.OnStickyChangeListener;
import com.mjgallery.mjgallery.widget.stickyitemdecoration.StickyHeadContainer;
import com.mjgallery.mjgallery.widget.stickyitemdecoration.StickyItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.TYPE_HEAD;


/**
 * ================================================
 * Description:开奖记录界面
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 15:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class LotteryRecordActivity extends MJBaseActivity<LotteryRecordPresenter>
        implements LotteryRecordContract.View, CalendarDialog.CalendarMonthChangeListener {

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
    @BindView(R.id.mPinnedHeaderRecyclerView)
    RecyclerView mPinnedHeaderRecyclerView;
    LotteryRecordAdapter lotteryRecordAdapter;
    LotteryOptimizeRecordAdapter lotteryOptimizeRecordAdapter;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    int pageIndex = 0;
    int pageSize = 15;
    @BindView(R.id.ivTopRightImg)
    ImageView ivTopRightImg;
    @BindView(R.id.rlTopRight)
    RelativeLayout rlTopRight;
    @BindView(R.id.flKaJianNull)
    FrameLayout flKaJianNull;
    List<LotteryRecordBean> dataList;
    CalendarDialog calendarDialog;
    List<LotteryRecordOptimizeBean> lotteryRecordOptimizeBeans;
    @BindView(R.id.tvTopRightImgYear)
    TextView tvTopRightImgYear;
    @BindView(R.id.tvLotteryRecordYear)
    TextView tvLotteryRecordYear;
    @BindView(R.id.tvLotteryRecordJangXu)
    TextView tvLotteryRecordJangXu;
    @BindView(R.id.tvLotteryRecordShengXu)
    TextView tvLotteryRecordShengXu;
    @BindView(R.id.rlLotteryRecordJangXu)
    RelativeLayout rlLotteryRecordJangXu;
    @BindView(R.id.tvLotteryRecordWuXing)
    TextView tvLotteryRecordWuXing;
    @BindView(R.id.rlLotteryRecordWuXing)
    RelativeLayout rlLotteryRecordWuXing;
    @BindView(R.id.mStickyHeadContainer)
    StickyHeadContainer mStickyHeadContainer;
    private int mStickyPosition;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLotteryRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lottery_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivTopRightImg.setVisibility(View.VISIBLE);
        dataList = new ArrayList<>();
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_record));
        tvTopRightImgYear.setText(DateUtils.getChineseYear(getBaseContext()));
        lotteryRecordOptimizeBeans = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        mPinnedHeaderRecyclerView.setLayoutManager(layoutManager);
        lotteryRecordAdapter = new LotteryRecordAdapter();
        lotteryOptimizeRecordAdapter = new LotteryOptimizeRecordAdapter(lotteryRecordOptimizeBeans);
        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(mStickyHeadContainer, TYPE_HEAD);
        stickyItemDecoration.setOnStickyChangeListener(new OnStickyChangeListener() {
            @Override
            public void onScrollable(int offset) {
                mStickyHeadContainer.scrollChild(offset);
                mStickyHeadContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onInVisible() {
                mStickyHeadContainer.reset();
                mStickyHeadContainer.setVisibility(View.INVISIBLE);
            }
        });
        mPinnedHeaderRecyclerView.addItemDecoration(stickyItemDecoration);
        mPinnedHeaderRecyclerView.setAdapter(lotteryOptimizeRecordAdapter);
        mStickyHeadContainer.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                if (lotteryRecordOptimizeBeans.size() > pos) {
                    mStickyPosition = pos;
                    LotteryRecordOptimizeBean lotteryRecordOptimizeBean = lotteryRecordOptimizeBeans.get(pos);
                    int year = lotteryRecordOptimizeBean.getYear();
                    tvLotteryRecordYear.setText(year + ArmsUtils.getString(BaseApplication.getInstance(),R.string.annual_drawing_record));
                    if (lotteryRecordOptimizeBean.isWuXing()) {
                        tvLotteryRecordWuXing.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0AAFFA));
                    } else {
                        tvLotteryRecordWuXing.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_7E7E7E));
                    }
                    switch (lotteryRecordOptimizeBean.getType()) {
                        case 0:
                            tvLotteryRecordJangXu.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.descending_order));
                            tvLotteryRecordShengXu.setVisibility(View.GONE);
                            tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            tvLotteryRecordShengXu.setVisibility(View.VISIBLE);
                            tvLotteryRecordJangXu.setVisibility(View.GONE);
                            break;
                        case 2:
                            tvLotteryRecordShengXu.setVisibility(View.GONE);
                            tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
        smartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getApplication()));
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                requestLotteryRecordData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = pageIndex + 0;
                requestLotteryRecordData();
            }
        });
        requestLotteryRecordData();
        calendarDialog = new CalendarDialog(this, null, this);
        //默认请求当前的年份和月份的中奖日期
        //mPresenter.requestPlanListByMonth(DateUtils.getYear()+"",DateUtils.getMonth()+"");
    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if (message == null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(), message);
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


    @Override
    public void getRecord(List<LotteryRecordBean> lotteryRecordBeans) {
        rlTopRight.setVisibility(View.VISIBLE);
        if (calendarDialog != null && calendarDialog.isShowing())
            calendarDialog.dismiss();

        if (pageIndex == 0) {
            lotteryRecordOptimizeBeans.clear();
            dataList.clear();
        }
        if (lotteryRecordBeans != null && lotteryRecordBeans.size() > 0) {
            if (pageIndex == 0) {
                setLotteryRecordOptimizeBeansData(lotteryRecordBeans);
                dataList.addAll(lotteryRecordBeans);
            } else {
                setLotteryRecordOptimizeBeansData(lotteryRecordBeans);
                int year = dataList.get(dataList.size() - 1).getYear();
                List<LotteryRecordBean.LotteryRecordDtosBean> list = dataList.get(dataList.size() - 1).getLotteryRecordDtos();
                for (int i = 0; i < lotteryRecordBeans.size(); i++) {
                    LotteryRecordBean lotteryRecordBean = lotteryRecordBeans.get(i);
                    if (year == lotteryRecordBean.getYear()) {
                        for (int k = 0; k < lotteryRecordBean.getLotteryRecordDtos().size(); k++) {
                            LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean = lotteryRecordBean.getLotteryRecordDtos().get(i);
                            list.add(lotteryRecordDtosBean);
                        }
                    } else {
                        dataList.add(lotteryRecordBean);
                    }
                }
            }

        }
        lotteryRecordAdapter.setData(dataList);
        //判断当没有数据时，显示没有数据时的ui
        if (lotteryRecordOptimizeBeans.size() > 0) {
            flKaJianNull.setVisibility(View.GONE);
            smartRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            flKaJianNull.setVisibility(View.VISIBLE);
            smartRefreshLayout.setVisibility(View.GONE);
        }
        if (lotteryOptimizeRecordAdapter != null) {
            lotteryOptimizeRecordAdapter.notifyDataSetChanged();
        }
    }


    private void setLotteryRecordOptimizeBeansData(List<LotteryRecordBean> lotteryRecordBeans) {
        for (int i = 0; i < lotteryRecordBeans.size(); i++) {
            LotteryRecordBean lotteryRecordBean = lotteryRecordBeans.get(i);
            boolean isWuXing = false;
            int type = 0;
            if (lotteryRecordOptimizeBeans.size() == 0) {
                LotteryRecordOptimizeBean lotteryRecordOptimizeBean = new LotteryRecordOptimizeBean();
                lotteryRecordOptimizeBean.setItemType(TYPE_HEAD);
                lotteryRecordOptimizeBean.setYear(lotteryRecordBean.getYear());
                lotteryRecordOptimizeBeans.add(lotteryRecordOptimizeBean);
                isWuXing = false;
                type = 0;
            } else {
                if (lotteryRecordOptimizeBeans.size() > 0) {
                    LotteryRecordOptimizeBean lotteryRecordOptimize = lotteryRecordOptimizeBeans.get(lotteryRecordOptimizeBeans.size() - 1);
                    int year = lotteryRecordOptimize.getYear();
                    if (year != lotteryRecordBean.getYear()) {
                        LotteryRecordOptimizeBean lotteryRecordOptimizeBean = new LotteryRecordOptimizeBean();
                        lotteryRecordOptimizeBean.setItemType(TYPE_HEAD);
                        lotteryRecordOptimizeBean.setYear(lotteryRecordBean.getYear());
                        lotteryRecordOptimizeBeans.add(lotteryRecordOptimizeBean);
                        isWuXing = false;
                        type = 0;
                    } else {
                        isWuXing = lotteryRecordOptimize.isWuXing();
                        type = lotteryRecordOptimize.getType();
                    }
                }
            }
            for (int k = 0; k < lotteryRecordBean.getLotteryRecordDtos().size(); k++) {
                LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean = lotteryRecordBean.getLotteryRecordDtos().get(k);
                LotteryRecordOptimizeBean lotteryRecordOptimizeBeanItem = new LotteryRecordOptimizeBean();
                lotteryRecordOptimizeBeanItem.setItemType(AppConstants.TYPE_CONTENT);
                lotteryRecordOptimizeBeanItem.setType(type);
                lotteryRecordOptimizeBeanItem.setWuXing(isWuXing);
                lotteryRecordOptimizeBeanItem.setCreateTime(lotteryRecordDtosBean.getCreateTime());
                lotteryRecordOptimizeBeanItem.setLotteryTime(lotteryRecordDtosBean.getLotteryTime());
                lotteryRecordOptimizeBeanItem.setFirstSx(lotteryRecordDtosBean.getFirstSx());
                lotteryRecordOptimizeBeanItem.setId(lotteryRecordDtosBean.getId());
                lotteryRecordOptimizeBeanItem.setNumbers(lotteryRecordDtosBean.getNumbers());
                lotteryRecordOptimizeBeanItem.setSx(lotteryRecordDtosBean.getSx());
                lotteryRecordOptimizeBeanItem.setWx(lotteryRecordDtosBean.getWx());
                lotteryRecordOptimizeBeanItem.setPeriod(lotteryRecordDtosBean.getPeriod());
                lotteryRecordOptimizeBeanItem.setYear(lotteryRecordDtosBean.getYear());
                lotteryRecordOptimizeBeans.add(lotteryRecordOptimizeBeanItem);
            }
        }
    }


    @Override
    public void onPlanListByMonth(List<String> dayList) {
        rlTopRight.setVisibility(View.VISIBLE);
        List<String> dateStrList = new ArrayList<>();
        dateStrList.addAll(dayList);
        if (calendarDialog == null)
            calendarDialog = new CalendarDialog(this, dateStrList, this);
        else {
            if (calendarDialog != null && calendarDialog.isShowing()) {
                calendarDialog.random(dateStrList);
            }
        }
    }

    private void requestLotteryRecordData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        mPresenter.requestLotteryRecordData(map);
    }


    @OnClick({R.id.ivTopRight, R.id.rlLotteryRecordWuXing, R.id.tvLotteryRecordJangXu, R.id.tvLotteryRecordShengXu, R.id.ivTopRightImg, R.id.ivTopReturn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivTopRightImg:
                if (calendarDialog != null && !calendarDialog.isShowing()) {
                    GlideUtil.loadNormalImage(ivTopRightImg, R.drawable.ri_li_click);
                    calendarDialog.initStyle();
                    calendarDialog.setCanceledOnTouchOutside(true);
                    calendarDialog.showCalendarDialog();
                }
                break;
            case R.id.rlLotteryRecordWuXing:
                if (lotteryOptimizeRecordAdapter != null) {
                    if (lotteryRecordOptimizeBeans.size() > mStickyPosition) {
                        int year = lotteryRecordOptimizeBeans.get(mStickyPosition).getYear();
                        lotteryOptimizeRecordAdapter.updateWuXiang(year);
                    }
                }
                break;
            case R.id.tvLotteryRecordJangXu:
                break;
            case R.id.tvLotteryRecordShengXu:
                break;

        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void changeMonthData(int year, int month) {
        //mPresenter.requestPlanListByMonth(year+"",month+"");
    }

    @Override
    public void lotteryRecord(int year, int month, int day) {
        pageIndex = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("year", "" + year);
        map.put("month", "0" + month);
        map.put("day", "" + day);
        mPresenter.requestLotteryRecordData(map);
    }

}
