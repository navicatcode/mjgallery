package com.mjgallery.mjgallery.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.MarginLottery;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.BannerGlideImageLoader;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.app.utils.JChineseConvertorUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.app.view.dialog.AnnouncementDialog;
import com.mjgallery.mjgallery.di.component.DaggerHomeComponent;
import com.mjgallery.mjgallery.event.SocketDataEvent;
import com.mjgallery.mjgallery.event.SocketStartEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.home.HomeContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.MuLuBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.LotteryPlan;
import com.mjgallery.mjgallery.mvp.presenter.home.HomePresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.SearchActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryDataActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryDateActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryRecordActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.discovery.DiscoveryAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.MuLuAdapter;
import com.mjgallery.mjgallery.widget.MarqueeView;
import com.mjgallery.mjgallery.widget.UIImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMShareAPI;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.utils.Utils.setNumbersBg;


/**
 * ================================================
 * Description:首页界面
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends MJBaseFragment<HomePresenter> implements HomeContract.View, DiscoveryAdapter.IDiscoveryClickListener {

    MuLuAdapter muLuAdapter;
    List<MuLuBean> muLuBeanList;
    String muLuTitle = ArmsUtils.getString(BaseApplication.getInstance(),R.string.color);
    int type = 1;
    DiscoveryAdapter discoveryAdapter;
    List<HomeBean> homeBeanList;
    AnnouncementDialog announcementDialog;
    @BindView(R.id.ivHomeSearch)
    ImageView ivHomeSearch;
    @BindView(R.id.ivHomeTop)
    ImageView ivHomeTop;
    @BindView(R.id.ivHomeShare)
    ImageView ivHomeShare;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ivAnnouncement)
    ImageView ivAnnouncement;
    @BindView(R.id.tvAnnouncement)
    MarqueeView tvAnnouncement;
    @BindView(R.id.ivHomeLottery01)
    ImageView ivHomeLottery01;
    @BindView(R.id.tvHomeLottery01)
    TextView tvHomeLottery01;
    @BindView(R.id.ivHomeLottery02)
    ImageView ivHomeLottery02;
    @BindView(R.id.tvHomeLottery02)
    TextView tvHomeLottery02;
    @BindView(R.id.ivHomeLottery03)
    ImageView ivHomeLottery03;
    @BindView(R.id.tvHomeLottery03)
    TextView tvHomeLottery03;
    @BindView(R.id.ivHomeLottery04)
    ImageView ivHomeLottery04;
    @BindView(R.id.tvHomeLottery04)
    TextView tvHomeLottery04;
    @BindView(R.id.ivHomeLottery05)
    ImageView ivHomeLottery05;
    @BindView(R.id.tvHomeLottery05)
    TextView tvHomeLottery05;
    @BindView(R.id.ivHomeLottery06)
    ImageView ivHomeLottery06;
    @BindView(R.id.tvHomeLottery06)
    TextView tvHomeLottery06;
    @BindView(R.id.ivHomeLottery07)
    ImageView ivHomeLottery07;
    @BindView(R.id.tvHomeLottery07)
    TextView tvHomeLottery07;
    @BindView(R.id.tvLotteryName01)
    TextView tvLotteryName01;
    @BindView(R.id.tvLotteryName02)
    TextView tvLotteryName02;
    @BindView(R.id.tvLotteryName03)
    TextView tvLotteryName03;
    @BindView(R.id.tvLotteryName04)
    TextView tvLotteryName04;
    @BindView(R.id.tvLotteryName05)
    TextView tvLotteryName05;
    @BindView(R.id.tvLotteryName06)
    TextView tvLotteryName06;
    @BindView(R.id.tvLotteryName07)
    TextView tvLotteryName07;
    @BindView(R.id.ivHomeBlackAndWhite)
    ImageView ivHomeBlackAndWhite;
    @BindView(R.id.recyclerViewMuLu)
    RecyclerView recyclerViewMuLu;
    @BindView(R.id.recyclerViewTwo)
    RecyclerView recyclerViewTwo;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    int pageIndex = 0;
    String year = "0";
    @BindView(R.id.ivHomeCaise)
    ImageView ivHomeCaise;
    List<String> strings;
    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.llLiuHe)
    LinearLayout llLiuHe;
    @BindView(R.id.tvLotteryPlan)
    TextView tvLotteryPlan;
    @BindView(R.id.tvPeriodCode)
    TextView tvPeriodCode;
    // 初始化定时器
    Timer timer;
    String preiodStr;//开奖期数
    String lotteryTime;//开奖时间
    boolean isRefreshLotter = true;
    int indexQingQiuCeShu = 0;
    int indexUpdateLotty = 0;
    @BindView(R.id.ivLotteryRecord)
    UIImageView ivLotteryRecord;
    @BindView(R.id.rlLotteryRecord)
    RelativeLayout rlLotteryRecord;
    @BindView(R.id.ivLotteryDate)
    UIImageView ivLotteryDate;
    @BindView(R.id.rlLotteryDate)
    RelativeLayout rlLotteryDate;
    @BindView(R.id.ivLotteryData)
    UIImageView ivLotteryData;
    @BindView(R.id.rlLotteryData)
    RelativeLayout rlLotteryData;
    int lotteryHour = 21;
    int lotteryMinuteStart = 25;
    int lotteryMinuteStartTwo = 41;
    int lotteryMinuteEnd = 40;
    int lotteryMinuteEndTwo = 45;
    int period = 0;
    boolean socketBool = true;//如果为false，则不再判断开奖时间差多少，不再管socket连接
    String lotteryTime2=null;//开奖时间2，用于在某个时间段点，请求获取下一期开奖时间
    public static List<String> ipList=new ArrayList<>();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Utils.setTopImg(getContext(), ivHomeTop, true);
        //初始化view的数据和集合
        initView();
        //初始化监听
        initClickListener();
        //首页第一次请求
        requestHomeDada();

    }


    /**
     * 初始化view的数据和集合
     */
    private void initView() {
        ivTopReturn.setVisibility(View.GONE);
        //定时器
        timer = new Timer();
        //设置搜索按钮为显示
        ivHomeSearch.setVisibility(View.VISIBLE);
        //临时的年份的集合
        strings = new ArrayList<>();
        //创建公告详情弹窗
        announcementDialog = new AnnouncementDialog(getActivity());
        //可切换年份的对象
        muLuBeanList = new ArrayList<>();
        //初始化recyclerView的LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        muLuAdapter = new MuLuAdapter(getContext(), R.layout.mulu_item, muLuBeanList);
        //初始化切换年份的适配器
        recyclerViewMuLu.setLayoutManager(linearLayoutManager);
        recyclerViewMuLu.setAdapter(muLuAdapter);
        //首页图片结合初始化
        homeBeanList = new ArrayList<>();
        //设置刷新控件头部布局
        smartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        //初始化图片列表适配器
        discoveryAdapter = new DiscoveryAdapter(getContext(), R.layout.discovery_item, homeBeanList, this, getActualWidth());
        //设置成为瀑布流
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewTwo.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewTwo.setAdapter(discoveryAdapter);
        // 设置没有数据的界面
        discoveryAdapter.setEmptyView(R.layout.discovery_item_error, recyclerViewTwo);
        //添加图片详情适配器的加载动画
        discoveryAdapter.openLoadAnimation();
    }

    /**
     * 初始化监听
     */

    private void initClickListener() {
        //年份选择点击
        muLuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //添加判断 集合的个数一定大于当前下标数，并且出现下标越界
                if (position < muLuBeanList.size()) {
                    //把状态切换默认的
                    for (int i = 0; i < muLuBeanList.size(); i++) {
                        MuLuBean muLuBean = muLuBeanList.get(i);
                        muLuBean.setSelect(false);
                    }
                    //设置为选中
                    MuLuBean muLuBeanTwo = muLuBeanList.get(position);
                    muLuBeanTwo.setSelect(true);
                    muLuBeanList.set(position, muLuBeanTwo);
                    //得到选中的年份
                    year = muLuBeanTwo.getYear();
                    if (muLuAdapter != null) {
                        muLuAdapter.notifyDataSetChanged();
                    }
                    //请求图片数据,并且把当前分页数据设为0（启始页）
                    pageIndex = 0;
                    //点击播放音效
                    playMusic(R.raw.yearitemselected);
                    //请求图片详情数据
                    getDiscovery();
                }
            }
        });
        //公告点击
        tvAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前公告弹窗是否不为并且 不是显示的状态，然后加载详情数据
                if (announcementDialog != null && !announcementDialog.isShowing()) {
                    announcementDialog.showDialog(tvAnnouncement.getContent());
                }
            }
        });

        //上拉加载,下拉刷新
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                getDiscovery();
                smartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                requestRefreshHomeDada();
                smartRefreshLayout.finishRefresh();
            }
        });

    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        //显示加载的弹窗
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        //关闭刷新控件的刷新和加载
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadMore();
            smartRefreshLayout.finishRefresh();
        }
        //关闭加载弹窗
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if (message == null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getHomeBanner(List<HomeBanner> resultBeanList) {
        if(resultBeanList!=null && resultBeanList.size()>0){
            banner.setImageLoader(new BannerGlideImageLoader());
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片集合
            banner.setImages(resultBeanList);
            //banner设置方法全部调用完毕时最后调用

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if(!TextUtils.isEmpty(resultBeanList.get(position).getUrl())){
                        Uri uri = Uri.parse(resultBeanList.get(position).getUrl());
                        if(uri.isAbsolute()){
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }

                }
            });
            banner.start();
        }

    }

    @Override
    public void getNoticeBanner(List<String> noticeStringList) {
        List<String> noticeList = noticeStringList;
        if (noticeList != null && noticeList.size() > 0) {
            tvAnnouncement.setContent(noticeList);
            tvAnnouncement.setTextDistance(100);//设置间距
            tvAnnouncement.setTextSpeed(2);//设置速度
        }
    }

    @Override
    public void getPictureMenu(List<String> stringList) {
        setPictureMenu(stringList);
    }

    /**
     * 设置年份数据
     *
     * @param stringList
     */
    private void setPictureMenu(List<String> stringList) {
        strings.clear();
        strings.addAll(stringList);
        pageIndex = 0;
        muLuBeanList.clear();
        if (stringList != null && stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                MuLuBean muLuBean = new MuLuBean();
                if (i == 0) {
//                    year = stringList.get(i);
                    muLuBean.setSelect(true);
                } else {
                    muLuBean.setSelect(false);
                }
                muLuBean.setTitle(muLuTitle);
                muLuBean.setYear(stringList.get(i));
                muLuBean.setType(type);
                muLuBeanList.add(muLuBean);
            }
        }

        if (muLuAdapter != null) {
            muLuAdapter.notifyDataSetChanged();
        }
        if (AppConstants.APP_HOMES.size() > 0) {
            setSmallPictrues(AppConstants.APP_HOMES);
            AppConstants.APP_HOMES.clear();
            return;
        }
        getDiscovery();
    }


    /**
     * 设置图片列表数据
     *
     * @param list
     */
    private void setSmallPictrues(List<HomeBean> list) {
        if (pageIndex == 0) {
            homeBeanList.clear();
        }
        if (list != null && list.size() > 0) {
            homeBeanList.addAll(list);
        }
        if (discoveryAdapter != null) {
            if (pageIndex == 0) {
                discoveryAdapter.notifyDataSetChanged();
            } else {
                discoveryAdapter.notifyItemChanged(0 + discoveryAdapter.getHeaderLayoutCount(), discoveryAdapter.ITEM_0_PAYLOAD);
            }

        }
    }


    //设置当前的图片请求类型
    private void setMuLuAdapter(int type) {
        this.type = type;
        if (muLuBeanList != null && muLuBeanList.size() > 0) {
            for (int i = 0; i < muLuBeanList.size(); i++) {
                MuLuBean muLuBean = muLuBeanList.get(i);
                muLuBean.setTitle(muLuTitle);
                muLuBean.setType(type);
                muLuBeanList.set(i, muLuBean);
            }
        }
        //请求图片详情数据
        getDiscovery();
        if (muLuAdapter != null) {
            muLuAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void getSmallPictrues(List<HomeBean> list) {
        setSmallPictrues(list);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDoShare() {
        String shareUrl = SPUtils.getInstance().getString("shareUrl", "http://www.xgmj.com/");
        ShareUtils.doShare(getActivity(), shareUrl,false);
    }

    /**
     * 获取下一次开奖时间的结果回调
     * @param lotteryPlan
     */
    @Override
    public void onLotteryPlan(LotteryPlan lotteryPlan) {
        if (lotteryPlan != null) {
            //如果期数小于100，则前面补个0
            period = lotteryPlan.getPeriod();
            String preiodStr = (period < 100) ? ("0" + period) : ("" + period);
            //拼出xxxx年xx月xx日xx时xx分
            lotteryTime = lotteryPlan.getLotteryTime();
            String lotteryTimeStr = lotteryTime.substring(0, 4) + "年" + lotteryTime.substring(5, 7) + "月" + lotteryTime.substring(8, 10) +
                    "日" + "21时" + "30分 ";
            //根据日期算出属于星期几
            String xinqiStr = DateUtils.dateToWeek(lotteryTime.substring(0, 10));
            //UI展示最终结果
            tvLotteryPlan.setText("第" + preiodStr + "期: " + lotteryTimeStr + xinqiStr);
            if (!isRefreshLotter) {
                isRefreshLotter = true;
                this.preiodStr = preiodStr;
                setLotteryImg();
                tvPeriodCode.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_one)
                        + preiodStr + ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_two));
            }
            stopTimer();
            scheduleTimer();
        } else {
            tvLotteryPlan.setText("未查询到下一期开奖时间哦");
        }

    }

    /**
     * 获取最近一次开奖记录的结果回调
     */
    @Override
    public void onRecord(List<LotteryRecordBean> lotteryRecordBeans) {
        if (lotteryRecordBeans != null && lotteryRecordBeans.size() > 0) {
            List<LotteryRecordBean.LotteryRecordDtosBean> recordBean = lotteryRecordBeans.get(0).getLotteryRecordDtos();
            //获取六合期数
            int period = recordBean.get(0).getPeriod();
            preiodStr = (period < 100) ?  ("0" + period) : ("" + period);
            tvPeriodCode.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_one)
                    + preiodStr + ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_two));
            setRecord(recordBean.get(0).getSx(), recordBean.get(0).getWx(), recordBean.get(0).getNumbers());
            sendLotteryPlan();
        }
    }

    private void sendLotteryPlan() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("term", preiodStr);
        mPresenter.getLotteryPlan(map);
    }

    @Override
    public void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean) {
        //根据期数和日期得到开奖记录
        int period = lotteryRecordDtosBean.getPeriod();
        preiodStr = (period < 100) ? ("0" + period) : ("" + period);
        tvPeriodCode.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_one) +
                preiodStr + ArmsUtils.getString(BaseApplication.getInstance(),R.string.home_period_title_two));
        setLotteryImg();
        setRecord(lotteryRecordDtosBean.getSx(), lotteryRecordDtosBean.getWx(), lotteryRecordDtosBean.getNumbers());

        sendLotteryPlan();
    }

    /**
     * 获取socket连接的ip
     * @param baseResponse
     */
    @Override
    public void onChangeGetIpList(BaseResponse<List<String>> baseResponse) {
        List<String> list=baseResponse.getResult();
        if(list!=null && list.size()>0){
            if(ipList.size()>0)
                ipList.clear();

            ipList.addAll(list);
        }

    }


    /**
     * 设置开奖 设置球号和颜色
     *
     * @param sxList
     * @param wxList
     * @param numBersList
     */
    private void setRecord(List<String> sxList, List<String> wxList, List<String> numBersList) {
        if (sxList.size() == wxList.size() && wxList.size() == numBersList.size()) {
            for (int i = 0; i < numBersList.size(); i++) {
                String s = numBersList.get(i);
                if (!TextUtils.isEmpty(s)) {
                    int index = Integer.valueOf(s);
                    String lottery = (index < 10) ? ("0" + index) : ("" + index);
                    switch (i) {
                        case 0:
                            tvHomeLottery01.setText(lottery);
                            tvLotteryName01.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery01, setNumbersBg(index));
                            break;
                        case 1:
                            tvHomeLottery02.setText(lottery);
                            tvLotteryName02.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery02, setNumbersBg(index));
                            break;
                        case 2:
                            tvHomeLottery03.setText(lottery);
                            tvLotteryName03.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery03, setNumbersBg(index));
                            break;
                        case 3:
                            tvHomeLottery04.setText(lottery);
                            tvLotteryName04.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery04, setNumbersBg(index));
                            break;
                        case 4:
                            tvHomeLottery05.setText(lottery);
                            tvLotteryName05.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery05, setNumbersBg(index));
                            break;
                        case 5:
                            tvHomeLottery06.setText(lottery);
                            tvLotteryName06.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery06, setNumbersBg(index));
                            break;
                        case 6:
                            tvHomeLottery07.setText(lottery);
                            tvLotteryName07.setText(sxList.get(i) + "/" + wxList.get(i));
                            GlideUtil.loadCircleImage(ivHomeLottery07, setNumbersBg(index));
                            break;
                    }
                }
            }
        }
    }

    /**
     * 点击跳转到详情页
     * @param homeBean
     */
    @Override
    public void onDiscoveryItem(HomeBean homeBean) {
        if (homeBean != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("picId", homeBean.getPicId());
            bundle.putStringArrayList("yearString", (ArrayList<String>) strings);
            bundle.putString("year", String.valueOf(homeBean.getYear()));
            bundle.putInt("termsByYear", homeBean.getTerms());
            //跳转到详情
            toOtherActivity(HomePictureDetailsActivity.class, bundle);
        }
    }


    @OnClick({R.id.ivHomeCaise, R.id.llLiuHe, R.id.ivHomeShare, R.id.ivHomeSearch, R.id.ivHomeBlackAndWhite, R.id.ivLotteryRecord, R.id.rlLotteryRecord, R.id.ivLotteryDate, R.id.rlLotteryDate, R.id.ivLotteryData, R.id.rlLotteryData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLotteryRecord:
            case R.id.rlLotteryRecord:
                toOtherActivity(LotteryRecordActivity.class);
                break;
            case R.id.ivLotteryDate:
            case R.id.rlLotteryDate:
                toOtherActivity(LotteryDateActivity.class);
                break;
            case R.id.ivLotteryData:
            case R.id.rlLotteryData:
                toOtherActivity(LotteryDataActivity.class);
                break;
            case R.id.ivHomeSearch:
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("yearString", (ArrayList<String>) strings);
                toOtherActivity(SearchActivity.class, bundle);
                break;
            case R.id.ivHomeCaise:
                ivHomeCaise.setVisibility(View.GONE);
                ivHomeBlackAndWhite.setVisibility(View.VISIBLE);
                muLuTitle = ArmsUtils.getString(BaseApplication.getInstance(),R.string.black_and_white);
                playMusic(R.raw.yearitemselected);
                pageIndex = 0;
                setMuLuAdapter(2);

                break;
            case R.id.ivHomeBlackAndWhite:
                ivHomeCaise.setVisibility(View.VISIBLE);
                ivHomeBlackAndWhite.setVisibility(View.GONE);
                muLuTitle = ArmsUtils.getString(BaseApplication.getInstance(),R.string.color);
                playMusic(R.raw.yearitemselected);
                pageIndex = 0;
                setMuLuAdapter(1);

                break;
            case R.id.ivHomeShare:
                mPresenter.externalStorage();
                break;
        }
    }




    /**
     * 请求图片详情数据
     */
    private void getDiscovery() {
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        Map<String, Object> map = new HashMap<>();
        map.put("color", type);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        if(!TextUtils.isEmpty(token))
            map.put("token",token);
        if (!year.equals("0")) {
            map.put("year", year);
        }
        //发送图片列表的请求到后台
        mPresenter.requestDiscovery(map);
    }


    /**+
     * 获取屏幕的宽度-左右的兼具
     *
     * @return
     */
    public int getActualWidth() {
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int marginWidth = SizeUtils.dp2px(8);
        return point.x / 2 - marginWidth;
    }

    /**
     * 刷新 控件的数据请求
     */
    private void requestRefreshHomeDada() {
        //发送轮播图的请求到后台
        mPresenter.getAdvert(1);
        //发送公告的请求到后台
        mPresenter.getNotice();
        if (TextUtils.isEmpty(preiodStr)) {
            //发送请求开奖的请求到后台
            mPresenter.getRecord();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("period", preiodStr);
            //发送年份和期数的请求到后台
            mPresenter.getLotteryRecordDtosBean(map);
        }
        //请求图片详情数据
        getDiscovery();
    }


    public HomePresenter getMyPresenter(){
        return mPresenter;
    }

    /**
     * 首页第一次请求
     */
    private void requestHomeDada() {
        //获取IP
        mPresenter.getChangeGetIpList();

        pageIndex = 0;
        //发送轮播图的请求到后台
        mPresenter.getAdvert(1);
        //发送公告的请求到后台
        mPresenter.getNotice();
        if (TextUtils.isEmpty(preiodStr)) {
            //发送请求开奖的请求到后台
            mPresenter.getRecord();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("period", preiodStr);
            //发送年份和期数的请求到后台
            mPresenter.getLotteryRecordDtosBean(map);
        }

        if (AppConstants.APP_STRINGS.size() > 0) {
            setPictureMenu(AppConstants.APP_STRINGS);
            AppConstants.APP_STRINGS.clear();
            return;
        }
        //请求年份的数据
        mPresenter.getPictureMenu();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            scheduleTimer();
        } else {
            stopTimer();
        }
    }


    @Subscriber(mode = ThreadMode.MAIN)
    public void onSocketDataEvent(SocketDataEvent socketDataEvent) {
        setLotteryImg();
        List<MarginLottery.LotteryNumber> lotteryNumberList = socketDataEvent.getLotteryNumbers();
        if (lotteryNumberList != null && lotteryNumberList.size() > 0) {
            //获取'生肖'与'五行'集合
            List<String> sxList = new ArrayList<>();
            List<String> wxList = new ArrayList<>();
            //获取中奖号码集合
            List<String> numBersList = new ArrayList<>();
            for (MarginLottery.LotteryNumber lotteryNumber : lotteryNumberList) {
                sxList.add(lotteryNumber.getSx());
                wxList.add(lotteryNumber.getWx());
                numBersList.add(lotteryNumber.getNumber());
            }
            setRecord(sxList, wxList, numBersList);
        }
    }


    /**
     * 设置开奖的默认状态
     */
    private void setLotteryImg() {
        GlideUtil.loadCircleImage(ivHomeLottery01, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery02, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery03, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery04, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery05, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery06, R.drawable.home_sheng_xiao_bg1_img);
        GlideUtil.loadCircleImage(ivHomeLottery07, R.drawable.home_sheng_xiao_bg1_img);
        tvHomeLottery01.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.is));
        tvHomeLottery02.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.in));
        tvHomeLottery03.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.open));
        tvHomeLottery04.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.prize));
        tvHomeLottery05.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.please));
        tvHomeLottery06.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.a_little));
        tvHomeLottery07.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.after));
        tvLotteryName01.setText("-/-");
        tvLotteryName02.setText("-/-");
        tvLotteryName03.setText("-/-");
        tvLotteryName04.setText("-/-");
        tvLotteryName05.setText("-/-");
        tvLotteryName06.setText("-/-");
        tvLotteryName07.setText("-/-");
    }


    private void scheduleTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                timerHandler.sendMessage(message);
            }
        }, 5000, 5000);
    }


    Handler timerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 1:
                    if (!TextUtils.isEmpty(lotteryTime)) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");     // 北京时间小时
                        SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("HH");     // 北京时间小时
                        SimpleDateFormat simpleDateFormatMinute = new SimpleDateFormat("mm");     // 北京时间小时
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
                        simpleDateFormatHour.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
                        simpleDateFormatMinute.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
                        //当前日期
                        String timeToday = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                        //当前时间小时
                        int currentTimeHour = Integer.valueOf(simpleDateFormatHour.format(new Date(System.currentTimeMillis())));
                        //当前时间分钟
                        int currentTimeMinute = Integer.valueOf(simpleDateFormatMinute.format(new Date(System.currentTimeMillis())));
                        //如果在开奖的前后5分钟，没发出过连接，就要发出连接
                        if (timeToday.equals(lotteryTime) && currentTimeHour
                                == lotteryHour && currentTimeMinute >= lotteryMinuteStart
                                && currentTimeMinute <= lotteryMinuteEnd) {
                            if (socketBool) {
                                Log.e("HomeFragment", "开奖前五分钟");
                                EventBus.getDefault().post(new SocketStartEvent(1));
                                socketBool = false;
                            }
                        } else {
                            socketBool = true;
                            EventBus.getDefault().post(new SocketStartEvent(0));
                            Log.e("HomeFragment", "关闭了socket连接......");
                        }
                        if (timeToday.equals(lotteryTime) && currentTimeHour == lotteryHour
                                && currentTimeMinute >= lotteryMinuteStart &&
                                currentTimeMinute <= lotteryMinuteEnd) {
                            isRefreshLotter = false;
                            if (indexQingQiuCeShu == 0) {
                                Log.e("timerHandler", "timeToHour==getLotteryPlan=25-40分的进来了。。");
                                indexQingQiuCeShu = indexQingQiuCeShu + 1;
                                if(TextUtils.isEmpty(lotteryTime2)){
                                    lotteryTime2=lotteryTime;
                                    Log.i("timerHandler", "lotteryTime2=lotteryTime=======");
                                }
                                sendLotteryPlan();
                                Log.i("timerHandler", "timeToHour==getLotteryPlan=");
                            }
                        } else {
                            indexQingQiuCeShu = 0;
                            if (timeToday.equals(lotteryTime2) && currentTimeHour == lotteryHour
                                    && currentTimeMinute >= lotteryMinuteStartTwo &&
                                    currentTimeMinute <= lotteryMinuteEndTwo) {
                                Log.i("timerHandler", "timeToHour==getLotteryPlan======1111111111111=======");
                                if (indexUpdateLotty == 0) {
                                    Log.i("timerHandler", "timeToHour==getLotteryPlan======22222222222222=======");
                                    indexUpdateLotty = indexUpdateLotty + 1;
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("year", DateUtils.getYear());
                                    map.put("period", period);
                                    //发送年份和期数的请求到后台
                                    mPresenter.getLotteryRecordDtosBean(map);
                                }

                            } else {
                                lotteryTime2=null;
                                indexUpdateLotty = 0;

                            }
                        }
                    }

                    break;
            }
            return false;
        }
    });


    // 停止定时器
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
    }
}
