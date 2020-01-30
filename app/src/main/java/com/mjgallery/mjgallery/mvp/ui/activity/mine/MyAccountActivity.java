package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.di.component.DaggerMyAccountComponent;
import com.mjgallery.mjgallery.header.CustomMyAccountRefreshHeader;
import com.mjgallery.mjgallery.header.CustomRefreshFooter;
import com.mjgallery.mjgallery.mvp.contract.mine.MyAccountContract;
import com.mjgallery.mjgallery.mvp.model.bean.AndroidGetAccountXBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountUserBean;
import com.mjgallery.mjgallery.mvp.model.bean.PaymentDetailsBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.MyAccountPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.MyAccountUserAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MyAccountAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的账户界面
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class MyAccountActivity extends MJBaseActivity<MyAccountPresenter> implements
        MyAccountContract.View, MyAccountAdapter.IMyAccountClick {
    int pageIndex = 0;
    MyAccountAdapter myAccountAdapter;
    List<MyAccountBean> myAccountBeans;
    String dateTime = "";
    List<MyAccountUserBean.ListBean> listBeanList;
    MyAccountUserAdapter myAccountUserAdapter;
    boolean isOne = true;
    TimePickerView pvCustomLunar;
    String mMonth;
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
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvAccountBalance)
    TextView tvAccountBalance;
    @BindView(R.id.ivMineUserImg)
    ImageView ivMineUserImg;
    @BindView(R.id.tvOrderWithdraw)
    TextView tvOrderWithdraw;
    @BindView(R.id.tvMethodPayment)
    TextView tvMethodPayment;
    @BindView(R.id.tvPaymentCode)
    TextView tvPaymentCode;
    @BindView(R.id.tvMonth)
    TextView tvMonth;
    @BindView(R.id.tvMonthTime)
    TextView tvMonthTime;
    @BindView(R.id.tvMonthMoney)
    TextView tvMonthMoney;
    @BindView(R.id.llMonth)
    LinearLayout llMonth;
    @BindView(R.id.tvDay)
    TextView tvDay;
    @BindView(R.id.tvDayTime)
    TextView tvDayTime;
    @BindView(R.id.tvDayMoney)
    TextView tvDayMoney;
    @BindView(R.id.llDay)
    LinearLayout llDay;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRecyclerViewMyAccount)
    RecyclerView mRecyclerViewMyAccount;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    List<String> stringData;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAccountComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_account;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        myAccountBeans = new ArrayList<>();
        listBeanList = new ArrayList<>();
        myAccountUserAdapter = new MyAccountUserAdapter(R.layout.adapter_my_account_user_item,
                listBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManagerUser = new LinearLayoutManager(this);
        stringData = new ArrayList<>();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerUser.setOrientation(LinearLayoutManager.VERTICAL);
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        if (getIntent().getExtras() != null && getIntent().getExtras().getString("headImg") != null) {
            GlideUtil.loadCircleImage(ivMineUserImg, getIntent().getExtras().getString("headImg"), R.drawable.mine_user_normal_img);
        }
        mRecyclerView.setLayoutManager(linearLayoutManagerUser);
        mRecyclerView.setAdapter(myAccountUserAdapter);
        myAccountAdapter = new MyAccountAdapter(R.layout.adapter_my_account_title_item,
                myAccountBeans, this, this);
        mSmartRefreshLayout.setRefreshHeader(new CustomMyAccountRefreshHeader(this));
        mSmartRefreshLayout.setRefreshFooter(new CustomRefreshFooter(this, ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_load_ing)));
        myAccountAdapter.setEmptyView(R.layout.include_ifound_error, mSmartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                isLogin();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                mMonth = "";
                pageIndex = 0;
                isLogin();
            }
        });
        mRecyclerViewMyAccount.setLayoutManager(linearLayoutManager);
        mRecyclerViewMyAccount.setAdapter(myAccountAdapter);
        initLunarPicker();
        isLogin();
    }


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
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


    @OnClick({R.id.ivTopReturn, R.id.tvTopTitle, R.id.ivTopTitle, R.id.ivMineUserImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tvTopTitle:
                break;
            case R.id.ivTopTitle:
                break;
            case R.id.ivMineUserImg:
                Bundle bundle = new Bundle();
                bundle.putString("userId", getIntent().getExtras().getString("userId"));
                isToOtherActivity(HisInformationActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onAndroidGetAccount(AndroidGetAccountXBean androidGetAccountXBean) {
        if (androidGetAccountXBean != null) {
            processData(androidGetAccountXBean);
        }
    }

    @Override
    public void onMyAccountUser(MyAccountUserBean myAccountUserBean) {
        if (myAccountUserBean != null) {
            tvAccountBalance.setText(myAccountUserBean.getUserBalanceAmount() + "");
            tvOrderWithdraw.setText(myAccountUserBean.getPassWithdrawAmount() + "");
            switch (myAccountUserBean.getSettlement()) {
                case 0:
                    tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.is_not_set));
                    llMonth.setVisibility(View.GONE);
                    llDay.setVisibility(View.GONE);
                    break;
                case 1:
                    tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.daily));
                    llMonth.setVisibility(View.GONE);
                    llDay.setVisibility(View.VISIBLE);
                    tvDayMoney.setText(myAccountUserBean.getAmount() + "");
                    tvDayTime.setText(myAccountUserBean.getTime());
                    break;
                case 2:
                    tvMethodPayment.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_mine_statement));
                    llMonth.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.GONE);
                    tvMonthMoney.setText(myAccountUserBean.getAmount() + "");
                    tvMonthTime.setText(myAccountUserBean.getTime());
                    break;
            }
            int resultType=getIntent().getExtras().getInt("resultType");
            if(resultType!=0){
                tvPaymentCode.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.is_not_set));
            }
            listBeanList.clear();
            if (myAccountUserBean != null && myAccountUserBean.getList() != null) {
                listBeanList.addAll(myAccountUserBean.getList());
            }
            if (myAccountUserAdapter != null) {
                myAccountUserAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (isOne) {
            mPresenter.requestUserData(map);
            isOne = false;
        }
        if (!TextUtils.isEmpty(mMonth)) {
            map.put("Month", mMonth.trim());
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        mPresenter.requestData(map);
    }


    private void processData(AndroidGetAccountXBean androidGetAccountXBean) {
        int inObject = 0;// 判断需要创建的对象 MyAccount
        stringData.clear();
        if (pageIndex == 0) {
            dateTime = "";
            myAccountBeans.clear();
        }
        for (int i = 0; i < androidGetAccountXBean.getAndroidlist().size(); i++) {
            AndroidGetAccountXBean.AndroidlistBean androidlistBean =
                    androidGetAccountXBean.getAndroidlist().get(i);
            String dateTimeNew = androidlistBean.getDate();
            //判断是否是下一个月或者是新的月份
            if (TextUtils.isEmpty(dateTime) || !dateTimeNew.equals(dateTime)) {
                dateTime = dateTimeNew;
                stringData.add(dateTimeNew);
            }

        }

        for (int i = 0; i < stringData.size(); i++) {
            MyAccountBean myAccountBean = new MyAccountBean();
            List<PaymentDetailsBean> paymentDetailsBeanList = new ArrayList<>();
            String dateTimeNew = stringData.get(i);
            for (int k = 0; k < androidGetAccountXBean.getAndroidlist().size(); k++) {
                AndroidGetAccountXBean.AndroidlistBean androidlistBean =
                        androidGetAccountXBean.getAndroidlist().get(k);
                if (dateTimeNew.equals(androidlistBean.getDate())) {
                    PaymentDetailsBean paymentDetailsBean = new PaymentDetailsBean();
                    paymentDetailsBean.setAmount(androidlistBean.getAmount());
                    paymentDetailsBean.setTime(androidlistBean.getTime());
                    paymentDetailsBean.setType(androidlistBean.getType());
                    paymentDetailsBean.setDate(androidlistBean.getDate());
                    paymentDetailsBeanList.add(paymentDetailsBean);
                    for (int j = 0; j < androidGetAccountXBean.getTotla().size(); j++) {
                        AndroidGetAccountXBean.TotlaBean totlaBean =
                                androidGetAccountXBean.getTotla().get(j);
                        if (dateTimeNew.equals(totlaBean.getTime())) {
                            myAccountBean.setTotalRevenue(totlaBean.getJust());
                            myAccountBean.setTotalSpending(totlaBean.getBack());
                            myAccountBean.setYearsMonth(totlaBean.getTime());
                        }
                    }
                }
            }
            myAccountBean.setPaymentDetailsBeanList(paymentDetailsBeanList);
            myAccountBeans.add(myAccountBean);
        }


        if (myAccountAdapter != null) {
            myAccountAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                pageIndex = 0;
                mMonth = getTimeTwo(date);
                isLogin();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time_two, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final RelativeLayout tvSubmit = (RelativeLayout) v.findViewById(R.id.rlFinsh);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setContentTextSize(16)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2f)
                .setDividerColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_00000000))
                .setBgColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_ECECEC))
                .isCyclic(true)
                .isDialog(true)
                .setRangDate(startDate, selectedDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(true)
                .build();
    }

    @Override
    public void onMyAccountYear() {
        pvCustomLunar.show();
    }

    private String getTimeTwo(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM ");
        return format.format(date);
    }


}
