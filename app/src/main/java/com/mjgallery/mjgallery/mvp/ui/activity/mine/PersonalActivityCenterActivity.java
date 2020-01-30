package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.BannerGlideImageLoader;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PersonalGetTask;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.AnnouncementDialog;
import com.mjgallery.mjgallery.app.view.dialog.AwardRuleDialog;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveErrorDialog;
import com.mjgallery.mjgallery.app.view.dialog.myvip.MyVIPReceiveSuccessDialog;
import com.mjgallery.mjgallery.di.component.DaggerPersonalActivityCenterComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.mine.PersonalActivityCenterContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.DailyTaskBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.NoviceTaskBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.PersonalBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;
import com.mjgallery.mjgallery.mvp.presenter.mine.PersonalActivityCenterPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MyEverydayListAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MyVIPWithdrawListAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.PersonalListViewAdapter;
import com.mjgallery.mjgallery.widget.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:活动中心界面
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PersonalActivityCenterActivity extends MJBaseActivity<PersonalActivityCenterPresenter> implements PersonalActivityCenterContract.View, PersonalGetTask {


    AnnouncementDialog announcementDialog;
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
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ivAnnouncement)
    ImageView ivAnnouncement;
    @BindView(R.id.tvAnnouncement)
    MarqueeView tvAnnouncement;
    @BindView(R.id.listNewbieTask)
    ListView listNewbieTask;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    PersonalListViewAdapter personalListViewAdapter;
    MyEverydayListAdapter myEverydayListAdapter;
    List<NoviceTaskBean> noviceList = new ArrayList<>();
    List<DailyTaskBean> myEverydayItemBeanList = new ArrayList<>();
    int pageIndex = 0;
    AwardRuleDialog awardRuleDialog;
    MyVIPReceiveSuccessDialog mMyVIPReceiveSuccessDialog;
    MyVIPReceiveErrorDialog mMyVIPReceiveErrorDialog;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPersonalActivityCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_personal_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_huodong));
        tvTopTitle.setTextColor(getResources().getColor(R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        awardRuleDialog=new AwardRuleDialog(this);
        mMyVIPReceiveSuccessDialog = new MyVIPReceiveSuccessDialog(this);
        mMyVIPReceiveErrorDialog = new MyVIPReceiveErrorDialog(this);
        //创建公告详情弹窗
        announcementDialog = new AnnouncementDialog(this);

        //新手任务列表初始化
        personalListViewAdapter = new PersonalListViewAdapter(this, noviceList,this);
        listNewbieTask.setAdapter(personalListViewAdapter);

        //日常任务列表初始化
        myEverydayListAdapter = new MyEverydayListAdapter(R.layout.adapter_personal_layout_item,
                myEverydayItemBeanList, true,this);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myEverydayListAdapter);
        myEverydayListAdapter.setEmptyView(R.layout.discovery_item_error, mSmartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                requestData();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                requestData();
            }
        });
        requestData();
    }

    @Override
    protected void requestData() {
        //发送轮播图的请求到后台
        mPresenter.getAdvert(1);
        //发送公告的请求到后台
        mPresenter.getNotice();
        //发送任务列表的请求到后台
        requestDailyTask();
    }

    public void requestDailyTask(){

        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 10);
        mPresenter.requestMeMyTaskAll(map);
    }

    @Override
    public void getHomeBanner(List<HomeBanner> resultBeanList) {
        if (resultBeanList != null && resultBeanList.size() > 0) {
            banner.setImageLoader(new BannerGlideImageLoader());
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片集合
            banner.setImages(resultBeanList);
            //banner设置方法全部调用完毕时最后调用

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (!TextUtils.isEmpty(resultBeanList.get(position).getUrl())) {
                        Uri uri = Uri.parse(resultBeanList.get(position).getUrl());
                        if (uri.isAbsolute()) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }

                }
            });
            banner.start();
        }

    }

    /**
     * 获取跑马灯数据的 ---结果回调
     * @param noticeStringList
     */
    @Override
    public void getNoticeBanner(List<String> noticeStringList) {
        List<String> noticeList = noticeStringList;
        if (noticeList != null && noticeList.size() > 0) {
            tvAnnouncement.setContent(noticeList);
            tvAnnouncement.setTextDistance(100);//设置间距
            tvAnnouncement.setTextSpeed(2);//设置速度
        }
    }

    /**
     * 请求任务列表 --- 结果回调
     * @param response
     */
    @Override
    public void onMeMyTaskAll(BaseResponse<PersonalBean> response) {

        if (response.getCode() == 0) {
            PersonalBean personalBean = response.getResult();
            if (personalBean != null) {

                if (personalBean.getNoviceTask() !=null && noviceList.size() <= 0) {
                    noviceList.addAll(personalBean.getNoviceTask());
                    personalListViewAdapter.notifyDataSetChanged();
                }

                if (pageIndex == 0) {
                    myEverydayItemBeanList.clear();
                }
                if (personalBean.getDailyTask() != null) {
                    myEverydayItemBeanList.addAll(personalBean.getDailyTask());
                }
                if (myEverydayListAdapter != null) {
                    myEverydayListAdapter.notifyDataSetChanged();
                }

            }
        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.data_error));
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if(mSmartRefreshLayout!=null){
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.finishRefresh();
        }
        dismissLoadingAnimationDialog();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.ivTopReturn, R.id.tvAnnouncement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tvAnnouncement:
                //当前公告弹窗是否不为并且 不是显示的状态，然后加载详情数据
                if (announcementDialog != null && !announcementDialog.isShowing()) {
                    announcementDialog.showDialog(tvAnnouncement.getContent());
                }
                break;
        }
    }

    /**
     * 请求领取任务奖励 --- 结果回调
     * @param response
     */
    @Override
    public void onMeGetReceive(BaseResponse response) {
        if(response.getCode()==0){
            String name;
            if(listType){
                noviceList.get(index).setIsDo(2);
                personalListViewAdapter.notifyDataSetChanged();
                name=noviceList.get(index).getRewardName();
            }else{
                myEverydayItemBeanList.get(index).setIsDo(2);
                myEverydayListAdapter.notifyDataSetChanged();
                name=myEverydayItemBeanList.get(index).getRewardName();
            }
            if (mMyVIPReceiveSuccessDialog != null && !mMyVIPReceiveSuccessDialog.isShowing()) {
                mMyVIPReceiveSuccessDialog.showPersonalSuccessDialogTop(name, response.getResult()+"元");
            }

        }else{
            if (mMyVIPReceiveErrorDialog != null && !mMyVIPReceiveErrorDialog.isShowing()) {
                mMyVIPReceiveErrorDialog.showMyVIPReceiveErrorDialogTop();
            }
        }


    }

    //true表示新手任务列表，false为日常任务列表
    boolean listType;
    //列表的子项序号
    int index;
    /**
     * 领取任务奖励 --发送请求
     * @param rewardId
     * @param rewardName
     */
    @Override
    public void getTaskOnClick(int rewardId, String rewardName,boolean listType,int index) {

        this.listType=listType;
        this.index=index;
        Map<String,Object> map=new HashMap<>();
        map.put("token",PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        map.put("rewardId",rewardId);
        map.put("rewardName",rewardName);
        mPresenter.requestMeGetReceive(map);
    }

    /**
     * 展示指定奖励的规则dialog
     * @param rewardName
     */
    @Override
    public void getShowDialog(String rewardName) {
        if(awardRuleDialog!=null && !awardRuleDialog.isShowing()){
            awardRuleDialog.show(rewardName);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(awardRuleDialog!=null && awardRuleDialog.isShowing()){
            awardRuleDialog.dismiss();
        }
    }
}
