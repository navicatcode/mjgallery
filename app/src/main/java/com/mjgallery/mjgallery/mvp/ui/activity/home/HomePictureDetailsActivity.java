package com.mjgallery.mjgallery.mvp.ui.activity.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lihang.ShadowLayout;
import com.luck.picture.lib.photoview.PhotoView;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.BannerGlideImageLoader;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.app.utils.FileUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.home.HomeDetailsCommentsDialog;
import com.mjgallery.mjgallery.awildfire.ConversationInputPanel;
import com.mjgallery.mjgallery.awildfire.InputAwareLayout;
import com.mjgallery.mjgallery.awildfire.KeyboardAwareLinearLayout;
import com.mjgallery.mjgallery.di.component.DaggerHomePictureDetailsComponent;
import com.mjgallery.mjgallery.event.SelectApplicableEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.home.HomePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.presenter.home.HomePictureDetailsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.SelectApplicableActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.HomeDetailsCommentsAdapter;
import com.mjgallery.mjgallery.widget.UIImageView;
import com.mjgallery.mjgallery.widget.goodview.GoodView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.utils.Utils.setNumbersBg;


/**
 * ================================================
 * Description:首页图片详情界面
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 18:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class HomePictureDetailsActivity extends MJBaseActivity<HomePictureDetailsPresenter>
        implements HomePictureDetailsContract.View, KeyboardAwareLinearLayout.OnKeyboardHiddenListener, HomeDetailsCommentsDialog.IDiscoveryDetailsComments, HomeDetailsCommentsAdapter.IDiscoveryDetailsCommentsItemClickListener, ConversationInputPanel.OnConversationInputPanelStateChangeListener, KeyboardAwareLinearLayout.OnKeyboardShownListener {
    String year;
    String termsByYear;
    List<String> yearStringList;
    int pictureTypeId;
    int picId;
    List<String> termsByYearList;
    String pictureUrl;
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
    @BindView(R.id.ivHomePictureDetailsImg)
    ImageView ivHomePictureDetailsImg;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.inputPanelFrameLayout)
    ConversationInputPanel inputPanelFrameLayout;
    @BindView(R.id.mInputAwareLayout)
    InputAwareLayout mInputAwareLayout;
    @BindView(R.id.mPhotoView)
    PhotoView mPhotoView;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.tvDay)
    TextView tvDay;
    @BindView(R.id.tvQishu)
    TextView tvQishu;
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
    @BindView(R.id.tvPictureDetailsNper)
    TextView tvPictureDetailsNper;
    @BindView(R.id.tvPetElvesDetailsTitle)
    TextView tvPetElvesDetailsTitle;
    @BindView(R.id.tvPetElvesDetailsFocusOn)
    TextView tvPetElvesDetailsFocusOn;
    @BindView(R.id.ivPetElvesDetailsShare)
    LinearLayout ivPetElvesDetailsShare;
    @BindView(R.id.ivPetElvesDetailsGiveLike)
    UIImageView ivPetElvesDetailsGiveLike;
    @BindView(R.id.tvPetElvesDetailsGiveLike)
    TextView tvPetElvesDetailsGiveLike;
    @BindView(R.id.ivPetElvesDetailsCollection)
    UIImageView ivPetElvesDetailsCollection;
    @BindView(R.id.tvPetElvesDetailsCollection)
    TextView tvPetElvesDetailsCollection;
    @BindView(R.id.ivPetElvesDetailsComments)
    UIImageView ivPetElvesDetailsComments;
    @BindView(R.id.tvPetElvesDetailsComments)
    TextView tvPetElvesDetailsComments;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.idAllComments)
    TextView idAllComments;
    @BindView(R.id.llPictureDetailsNper)
    LinearLayout llPictureDetailsNper;
    @BindView(R.id.ivPictureDetailsNper)
    UIImageView ivPictureDetailsNper;
    @BindView(R.id.mShadowLayoutTop)
    ShadowLayout mShadowLayoutTop;
    @BindView(R.id.rlPictureDetailsTop)
    RelativeLayout rlPictureDetailsTop;
    @BindView(R.id.tvPictureDetailsTop)
    TextView tvPictureDetailsTop;
    @BindView(R.id.mPictureDetailsImgDownload)
    UIImageView mPictureDetailsImgDownload;
    @BindView(R.id.llAll)
    LinearLayout llAll;
    @BindView(R.id.tvPetElvesDetailsViews)
    TextView tvPetElvesDetailsViews;
    private float mLastX;
    GestureDetector gestureDetector;
    int isCollection;//是否收藏 0是没有收藏 1是已经收藏
    int isThumpUp;//是否点赞  0是没有点赞  1是已经点赞
    int pageIndex = 0;
    GoodView mGoodView;
    int commentListType = 1;//评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
    int commentId = 0;//评论id(一级评论不需要，二级评论需要带上)
    int subCommentId = 0;//子评论id
    int replyUserId = 0;//被回复人id(二级回复需要带上)
    int itemTypeId = 0;
    int termsByYearIndex = 0;
    String replyNickName = "";


    int supportCount = 0;//点赞的数量
    int collectionCount = 0;// 收藏的数量
    int picComment = 0;//评论的数量

    int isItemThumpUp = 0;//评论里面里面item是否点赞（1是已经点赞，0是没有点赞）
    boolean isItemStepOn = false;//评论里面里面item是否点踩
    int itemThumbUpCount = 0;  //评论里面里面item点赞数量
    int itemStepOnCount = 0;  //评论里面里面item点踩数量
    int itemIndex = 0;        //评论列表里面一级评论当前下标
    int itemItemIndex = 0;        //评论列表里面二级评论当前下标

    //是否点击了图片详情的点赞
    boolean isClickGiveLikeImageDetails = false;

    //评论数据
    List<HomeDetailsCommentsBean.DataBean> mDiscoveryDetailsCommentsBeanDataBeanList;
    //评论适配器
    HomeDetailsCommentsAdapter homeDetailsCommentsAdapter;
    HomeDetailsCommentsDialog mHomeDetailsCommentsDialog;
    int itemPageIndex = 0;
    HomeDetailsCommentsBean.DataBean dataBean;
    //广告图片url
    String advertUrl = null;
    boolean isEventBus = false;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomePictureDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_picture_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //期数列表
        termsByYearList = new ArrayList<>();
        yearStringList = new ArrayList<>();

        isEventBus = false;
        //输入表情界面初始化
        inputPanelFrameLayout.init(this, mInputAwareLayout);
        //表情输入法事件监听
        mInputAwareLayout.addOnKeyboardShownListener(this);
        mInputAwareLayout.addOnKeyboardHiddenListener(this);
        inputPanelFrameLayout.setOnConversationInputPanelStateChangeListener(this);
        //滑动监听
        gestureDetector = new GestureDetector(this, listener);

        rlPictureDetailsTop.setVisibility(View.GONE);
        tvPictureDetailsTop.setVisibility(View.GONE);
        //显示头部布局和设置颜色
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        tvTopTitle.setVisibility(View.VISIBLE);
        ivTopTitle.setVisibility(View.GONE);
        mHomeDetailsCommentsDialog = new HomeDetailsCommentsDialog(this, this);
        GlideUtil.loadNormalImageSV(ivTopReturn, R.drawable.activity_return_while_img);
        ivTopRightImg.setVisibility(View.VISIBLE);
        GlideUtil.loadNormalImageSV(ivTopRightImg, R.drawable.home_fengxiang);
        mDiscoveryDetailsCommentsBeanDataBeanList = new ArrayList<>();
        // 初始化评论列表适配器和列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeDetailsCommentsAdapter = new HomeDetailsCommentsAdapter
                (getApplication(), R.layout.adapter_discovery_details_comments_item,
                        mDiscoveryDetailsCommentsBeanDataBeanList, this);
        homeDetailsCommentsAdapter.setEmptyView(R.layout.adapter_discovery_details_comments_item_no_date, mSmartRefreshLayout);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(homeDetailsCommentsAdapter);
        //下拉和上拉刷新加载评论数据
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getApplication()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                requestCommentDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                requestCommentDetail();
                //获取图片详情数据
                getPictureDetail();
            }
        });
        mGoodView = new GoodView(this);
        onNewIntent(getIntent());
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("onNewIntent===", "onNewIntent====");
        //获取上个界面传递的信息
        if (!isEventBus) {
            if (intent != null) {
                if (intent.getStringArrayListExtra("yearString") != null) {
                    yearStringList.addAll(intent.getStringArrayListExtra("yearString"));
                }
                termsByYear = String.valueOf(intent.getIntExtra("termsByYear", 0));
                pictureTypeId = intent.getIntExtra("pictureTypeId", 0);
                picId = intent.getIntExtra("picId", 0);
                year = intent.getStringExtra("year");
            }
            isEventBus = false;
            getData();
        }
        mNestedScrollView.scrollTo(0, mShadowLayoutTop.getTop());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.finishRefresh();
        }
    }

    //设置图片的点击事件
    public void setImageClick(String url) {
        // 获取/设置 最大缩放倍数
        RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
        GlideUtil.loadRequestOptions(mPhotoView, url, options);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoView.setVisibility(View.GONE);
                ivHomePictureDetailsImg.setVisibility(View.VISIBLE);
            }
        });

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPresenter.externalStorage(false);
                return true;
            }
        });


        ivHomePictureDetailsImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, @NonNull MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    // 记录点击到ViewPager时候，手指的X坐标
                    mLastX = event.getX();
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    // 超过阈值
                    if (Math.abs(event.getX() - mLastX) > 60f) {
                        mNestedScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (action == MotionEvent.ACTION_UP) {
                    // 用户抬起手指，恢复父布局状态
                    mNestedScrollView.requestDisallowInterceptTouchEvent(false);
                }
                return gestureDetector.onTouchEvent(event);
            }
        });
        ivHomePictureDetailsImg.setClickable(true);
        ivHomePictureDetailsImg.setLongClickable(true);
    }


    @NonNull
    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            LogUtils.e("muuv", "单击了图像.........");
            ivHomePictureDetailsImg.setVisibility(View.GONE);
            mPhotoView.setVisibility(View.VISIBLE);
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mPresenter.externalStorage(false);
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            LogUtils.e("muuv", "双击了");
            ivHomePictureDetailsImg.setVisibility(View.GONE);
            mPhotoView.setVisibility(View.VISIBLE);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtils.e("muuv", "onScrollL:distanceX:" + distanceX + "---" + "distanceY:" + distanceY);
            return true;
        }

        private int verticalMinDistance = 10;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > verticalMinDistance) {
                LogUtils.e("muuv", "向左手势");
                // 期数-1
                // termPostion+1
                if (termsByYearIndex < termsByYearList.size() - 1) {
                    termsByYearIndex++;
                    termsByYear = termsByYearList.get(termsByYearIndex);
                    updateData();
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_one_title));
                }
            } else if (e2.getX() - e1.getX() > verticalMinDistance) {
                LogUtils.e("muuv", "向右手势");
                // 期数+1
                // termPosition-1
                if (termsByYearIndex > 0) {
                    termsByYearIndex--;
                    termsByYear = termsByYearList.get(termsByYearIndex);
                    updateData();
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_two_title));
                }
            }
            return true;
        }
    };


    /**
     * 左右滑刷新数据
     */
    private void updateData() {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("terms", termsByYear);
        map.put("pictureTypeId", pictureTypeId);
        map.put("commentType", 1);
        map.put("token", token);
        mPresenter.getPictureDetail(map);
        getLotteryRecordDtosBean(year, termsByYear);
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
        dismissLoadingAnimationDialog();
        finish();
    }

    @Override
    protected void requestData() {

    }


    @OnClick({R.id.ivTopReturn, R.id.llAll, R.id.mNestedScrollView, R.id.mRecyclerView, R.id.mPictureDetailsImgDownload, R.id.inputPanelFrameLayout, R.id.ivPetElvesDetailsShare, R.id.ivPetElvesDetailsComments, R.id.ivPetElvesDetailsGiveLike, R.id.tvPetElvesDetailsGiveLike, R.id.ivPetElvesDetailsCollection, R.id.tvPetElvesDetailsCollection, R.id.tvTopTitle, R.id.llPictureDetailsNper, R.id.tvPictureDetailsNper, R.id.ivTopRightImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.mPictureDetailsImgDownload:
                mPresenter.externalStorage(false);
                break;
            case R.id.llPictureDetailsNper:
            case R.id.tvPictureDetailsNper:
                animRotate(ivPictureDetailsNper, false);
                break;
            case R.id.ivTopRightImg:
                mPresenter.externalStorage(true);
                break;
            case R.id.ivPetElvesDetailsComments:
                Log.e("setCommentNormal=====", "ivPetElvesDetailsComments");
                setCommentNormal();
                showSoftInputFromWindow("");
                break;
            case R.id.ivPetElvesDetailsCollection:
            case R.id.tvPetElvesDetailsCollection:
                onCollectionClick();
                break;
            case R.id.ivPetElvesDetailsGiveLike:
            case R.id.tvPetElvesDetailsGiveLike:
                onGiveLike(picId, "", 1, tvPetElvesDetailsGiveLike);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mPhotoView != null && mPhotoView.getVisibility() == View.VISIBLE) {
            mPhotoView.setVisibility(View.GONE);
            ivHomePictureDetailsImg.setVisibility(View.VISIBLE);
        } else if (mInputAwareLayout.getCurrentInput() != null) {
            mInputAwareLayout.hideAttachedInput(true);
            inputPanelFrameLayout.collapse();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(String draftString) {
        inputPanelFrameLayout.show(mInputAwareLayout, draftString);
    }

    /**
     * 保存图片
     */
    public void savePicture() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                e.onNext(Glide.with(HomePictureDetailsActivity.this)
                        .load(pictureUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        //获取到下载得到的图片，进行本地保存
                        File pictureFolder = Environment.getExternalStorageDirectory();
                        //第二个参数为你想要保存的目录名称
                        File appDir = new File(pictureFolder, "/DCIM");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //把gilde下载得到图片复制到定义好的目录中去
                        FileUtil.copyFile(file, destFile, destFile.getPath(), fileName, HomePictureDetailsActivity.this);
                        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.in_download_title_one) + destFile.getPath());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.in_download_title_error));
                    }
                });
    }


    /**
     * @param id     目标id
     * @param userId 被点赞人id
     * @param type   类型(1-首页，2-发现，3-评论，4-子评论)
     */
    private void onGiveLike(int id, String userId, int type, View view) {
        if (!isLoginStatus()) {
            return;
        } else {
            isClickGiveLikeImageDetails = true;
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            if (!TextUtils.isEmpty(userId)) {
                map.put("userId", userId);
            }
            if (isThumpUp == 0) {
                mGoodView.setText("+1");
                mGoodView.show(view);
            }
            isThumpUp = (isThumpUp != 0) ? 0 : 1;
            map.put("status", isThumpUp);//代表点赞状态
            map.put("type", type);
            map.put("token", token);
            map.put("relatedId", getIntent().getIntExtra("picId", 0));//点赞时要传这个参数
            mPresenter.getLike(map);
        }
    }


    @Override
    public void onKeyboardShown() {
        inputPanelFrameLayout.onKeyboardShown();
    }

    @Override
    public void onInputPanelExpanded() {
        Log.i("onInputPanelExpanded", "onInputPanelExpanded=====");
    }

    @Override
    public void onInputPanelCollapsed() {
        Log.i("onInputPanelCollapsed", "onInputPanelCollapsed=====");
    }

    @Override
    public void onSendText(String sendText) {
        getAddCommentRecommend(sendText);
    }

    /**
     * 发表评论
     *
     * @param commentContent
     */
    private void getAddCommentRecommend(String commentContent) {
        if (!isLoginStatus()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", picId);
        if (commentId != 0) {
            map.put("commentId", commentId);
        }
        if (subCommentId != 0) {
            map.put("subCommentId", subCommentId);
        }
        if (replyUserId != 0) {
            map.put("replyUserId", replyUserId);
        }
        if (!TextUtils.isEmpty(replyNickName)) {
            map.put("replyNickName", replyNickName);
        }
        map.put("commentContent", commentContent);
        map.put("commentType", 1);
        map.put("type", commentListType);
        map.put("token", token);
        mPresenter.getAddCommentRecommend(map);
    }


    //旋转动画打开透明的SelectApplicableActivity
    public void animRotate(View view, boolean isExit) {
        if (!isExit) {
            view.animate().setDuration(500).rotation(180).start();
            Bundle bundle = new Bundle();
            bundle.putInt("pictureTypeId", pictureTypeId);
            bundle.putStringArrayList("yearString", (ArrayList<String>) yearStringList);
            bundle.putBoolean("isHome", true);
            toOtherActivity(SelectApplicableActivity.class, bundle);
            overridePendingTransition(R.anim.dialog_show_anim, 0);
        } else {
            view.animate().setDuration(500).rotation(0).start();
        }
    }

    //收藏点击事件
    private void onCollectionClick() {
        if (!isLoginStatus()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        if (isCollection != 0) {
            if (!TextUtils.isEmpty(String.valueOf(picId))) {
                map.put("picId", picId);
                map.put("type", 1);
                map.put("token", token);
                map.put("relatedId", pictureTypeId);
                mPresenter.getCancelCollection(map);//取消收藏
            }
        } else {
            if (!TextUtils.isEmpty(String.valueOf(picId))) {
                map.put("tmpId", picId);
                map.put("type", 1);
                map.put("relatedId", pictureTypeId);
                map.put("token", token);
                mPresenter.getAddCollection(map);//添加收藏
            }
        }
    }


    /**
     * 首页第一次刷新数据
     */
    private void getPictureDetail() {
        Map<String, Object> map = new HashMap<>();
        if (picId != 0) {
            map.put("id", picId);
            map.put("commentType", "1");
            map.put("token", token);
            mPresenter.getPictureDetail(map);
        } else {
            if (!TextUtils.isEmpty(year) && pictureTypeId != 0 && !TextUtils.isEmpty(termsByYear)) {
                map.put("pictureTypeId", pictureTypeId);
                map.put("year", year);
                map.put("terms", termsByYear);
                map.put("commentType", "1");
                map.put("token", token);
                mPresenter.getPictureDetail(map);
            }
        }

    }


    @Override
    public void getPictureDetail(HomeDetailsBean homeDetailsBean) {
        if (homeDetailsBean != null) {
            if (homeDetailsBean.getData() != null) {
                //得到实体对象
                HomeDetailsBean.DataBean dataBea = homeDetailsBean.getData();
                if (dataBea != null) {

                    //是否收藏过
                    isCollection = dataBea.getIsCollection();
                    //是否点赞过
                    isThumpUp = dataBea.getIsThumpUp();
                    //点赞的数量
                    supportCount = dataBea.getSupportCount();
                    // 收藏的数量
                    collectionCount = dataBea.getCollectionCount();
                    //评论的数量
                    picComment = dataBea.getPicComment();
                    //刷新图片详情的点赞和收藏的图片和数量
                    updateThumpUpAndCollectionImg();
                    //图片ID
                    picId = dataBea.getPicId();
                    //期数
                    termsByYear = String.valueOf(dataBea.getTerms());
                    //图片年份
                    year = String.valueOf(dataBea.getYear());
                    //图片类型ID
                    pictureTypeId = dataBea.getPictureTypeId();
                    //设置标题栏文本
                    tvTopTitle.setText(dataBea.getPicName());
                    //设置标题栏颜色
                    tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
                    pageIndex = 0;
                    //请求图片数据
                    requestCommentDetail();
                    //设置图片的高度和宽度
                    ViewGroup.LayoutParams layoutParams = ivHomePictureDetailsImg.getLayoutParams();
                    int screenWidth = ScreenUtils.getScreenWidth();
                    int height = dataBea.getHeight();
                    int width = dataBea.getWidth();
                    //图片地址
                    pictureUrl = dataBea.getFilePath();
                    layoutParams.height = (int) (screenWidth / (width * 1.0 / height));
                    RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
                    String picUrl = pictureUrl.split("[?]")[0];
                    GlideUtil.loadRequestOptions(ivHomePictureDetailsImg, picUrl, options);
                    getLotteryRecordDtosBean();
                    if (dataBea.getBrowsingVolume() > 10000) {
                        BigDecimal bigDecimalClickCount = new BigDecimal(dataBea.getBrowsingVolume());
                        BigDecimal bigDecimalChu = new BigDecimal(10000);
                        float clickCount = bigDecimalClickCount.divide(bigDecimalChu).
                                setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                        tvPetElvesDetailsViews.setText(clickCount + ArmsUtils.getString(getApplication(),R.string.wan));
                    } else {
                        tvPetElvesDetailsViews.setText(dataBea.getBrowsingVolume() + "");
                    }
                    //设置图片的点击事件
                    setImageClick(picUrl);
                    //根据年份和图片类型获取期数
                    getTermsByYear();
                }
            }
        }
    }


    //刷新图片详情的点赞图片和数量
    private void updateThumpUpImgAndThumpUpCount() {
        //设置点赞数量
        tvPetElvesDetailsGiveLike.setText(supportCount + "");
        if (isThumpUp != 0) {
            GlideUtil.loadNormalImage(ivPetElvesDetailsGiveLike, R.drawable.pet_elves_details_give_lick_click_img);
        } else {
            GlideUtil.loadNormalImage(ivPetElvesDetailsGiveLike, R.drawable.pet_elves_details_give_lick_img);
        }
    }


    //刷新图片详情的收藏图片和数量
    private void updateCollectionImgAndCollectionCount() {
        //设置收藏数量
        tvPetElvesDetailsCollection.setText(collectionCount + "");
        if (isCollection != 0) {
            GlideUtil.loadNormalImage(ivPetElvesDetailsCollection, R.drawable.pet_elves_details_collection_click_img);
        } else {
            GlideUtil.loadNormalImage(ivPetElvesDetailsCollection, R.drawable.pet_elves_details_collection_img);
        }
    }


    //刷新图片详情的评论数量
    private void updateThumpUpAndCollectionCount() {
        //设置评论数量
        idAllComments.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.pet_elves_details_all_title) +
                "(" + picComment + ")");
        tvPetElvesDetailsComments.setText(picComment + "");
    }


    // 刷新图片详情的点赞和收藏的图片和数量
    private void updateThumpUpAndCollectionImg() {
        updateCollectionImgAndCollectionCount();
        updateThumpUpImgAndThumpUpCount();
        updateThumpUpAndCollectionCount();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDoShare() {
//        if (TextUtils.isEmpty(pictureUrl)) {
//            showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_share));
//            return;
//        }
        String shareUrl = SPUtils.getInstance().getString("shareUrl", AppConstants.APP_DOMAIN+"common/share?type=1&id="+getIntent().getIntExtra("picId", 0));
        ShareUtils.doShare(HomePictureDetailsActivity.this, shareUrl,true);
    }

    @Override
    public void onAddCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                collectionCount++;
                isCollection = 1;
                //刷新图片详情的收藏图片和数量0
                updateCollectionImgAndCollectionCount();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCancelCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                if (collectionCount > 0) {
                    collectionCount--;
                }
                isCollection = 0;
                //刷新图片详情的收藏图片和数量
                updateCollectionImgAndCollectionCount();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCancelUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onConcernUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void getTermsByYear(List<String> stringList) {
        termsByYearList.clear();
        termsByYearList.addAll(stringList);
        termsByYearIndex = termsByYearList.indexOf(termsByYear);
    }

    @Override
    public void onCommentDetail(HomeDetailsCommentsBean homeDetailsCommentsBean) {

        if (homeDetailsCommentsBean!=null) {
            if (pageIndex == 0) {
                mDiscoveryDetailsCommentsBeanDataBeanList.clear();
            }
            if (homeDetailsCommentsBean.getData() != null && homeDetailsCommentsBean.getData().size() > 0) {
                mDiscoveryDetailsCommentsBeanDataBeanList.addAll(homeDetailsCommentsBean.getData());
            }
            if (homeDetailsCommentsAdapter != null) {
                homeDetailsCommentsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDeleteComment(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                switch (commentListType) {
                    case 1:
                        if (mDiscoveryDetailsCommentsBeanDataBeanList.size() > itemIndex) {
                            mDiscoveryDetailsCommentsBeanDataBeanList.remove(itemIndex);
                            picComment = picComment - 1;
                            updateThumpUpAndCollectionCount();
                            if (homeDetailsCommentsAdapter != null) {
                                homeDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case 3:
                    case 4:
                        if (dataBean.getSubCommentDetailDos() != null) {
                            List<HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                            subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                            if (subCommentDetailDosBeanList.size() > itemItemIndex) {
                                subCommentDetailDosBeanList.remove(itemItemIndex);
                            }
                            this.dataBean.getSubCommentDetailDos().clear();
                            this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                            if (mDiscoveryDetailsCommentsBeanDataBeanList.size() > itemIndex) {
                                mDiscoveryDetailsCommentsBeanDataBeanList.set(itemIndex, this.dataBean);
                                if (homeDetailsCommentsAdapter != null) {
                                    homeDetailsCommentsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        break;
                }


            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onLike(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                //当前是图片详情点赞还是评论点赞
                if (isClickGiveLikeImageDetails) {
                    isClickGiveLikeImageDetails = false;
                    supportCount = (isThumpUp != 1) ? supportCount - 1 : supportCount + 1;
                    if (supportCount < 0)
                        supportCount = 0;
                    //刷新图片详情的点赞图片和数量
                    updateThumpUpImgAndThumpUpCount();
                } else {
                    //这里是评论列表里面的点击逻辑处理
                    itemThumbUpCount = (isItemThumpUp != 1) ? itemThumbUpCount - 1 : itemThumbUpCount + 1;
                    if (itemThumbUpCount < 0)
                        itemThumbUpCount = 0;
                    this.dataBean.setIsThumpUp(isItemThumpUp);
                    this.dataBean.setThumbUpCount(itemThumbUpCount);
                    //刷新单条评论数据
                    updateDiscoveryDetailsCommentsAdapter(this.dataBean);
                }
            }

        }
    }

    @Override
    public void onAddCommentRecommend(BaseResponse<HomeDetailsCommentsBean.DataBean> dataBeanBaseResponse) {
        inputPanelFrameLayout.collapse();
        if (dataBeanBaseResponse != null) {
            HomeDetailsCommentsBean.DataBean homeDetailsCommentsBeanData = dataBeanBaseResponse.getResult();
            if (dataBeanBaseResponse.getCode() == 0 && homeDetailsCommentsBeanData != null) {
                //评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
                switch (commentListType) {
                    case 1:
                        mDiscoveryDetailsCommentsBeanDataBeanList.add(0, homeDetailsCommentsBeanData);
                        picComment = picComment + 1;
                        updateThumpUpAndCollectionCount();
                        setScrollToPosition();
                        if (homeDetailsCommentsAdapter != null) {
                            homeDetailsCommentsAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                    case 4:
                        if (homeDetailsCommentsBeanData != null &&
                                homeDetailsCommentsBeanData.getSubCommentDetailDos().size() > 0) {
                            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean = homeDetailsCommentsBeanData.getSubCommentDetailDos().get(0);
                            List<HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                            if (this.dataBean.getSubCommentDetailDos() != null && this.dataBean.getSubCommentDetailDos().size() > 0) {
                                subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                                this.dataBean.getSubCommentDetailDos().clear();
                            }
                            if (subCommentDetailDosBean != null) {
                                subCommentDetailDosBeanList.add(0, subCommentDetailDosBean);
                            }
                            this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                            mDiscoveryDetailsCommentsBeanDataBeanList.set(itemIndex, this.dataBean);
                            if (homeDetailsCommentsAdapter != null) {
                                homeDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }


                setCommentNormal();

            }
            showMessage(dataBeanBaseResponse.getMessage());
        }
    }


    // 刷新单条评论数据
    private void updateDiscoveryDetailsCommentsAdapter(HomeDetailsCommentsBean.DataBean dataBean) {
        if (mDiscoveryDetailsCommentsBeanDataBeanList.size() > itemIndex) {
            mDiscoveryDetailsCommentsBeanDataBeanList.set(itemIndex, dataBean);
        }
        if (homeDetailsCommentsAdapter != null) {
            homeDetailsCommentsAdapter.notifyDataSetChanged();
        }
    }


    //设置评论初始状态
    private void setCommentNormal() {
        Log.e("setCommentNormal=====", "setCommentNormal");
        this.subCommentId = 0;
        this.commentId = 0;
        commentListType = 1;
        itemItemIndex = 0;
        itemIndex = 0;
        replyUserId = 0;
        replyNickName = "";
    }

    @Override
    public void onSubCommentDetail(HomeSubCommentDetailDosBean subCommentDetailDosBeans) {
        if (subCommentDetailDosBeans != null && subCommentDetailDosBeans.getData() != null && subCommentDetailDosBeans.getData().size() > 0) {
            for (int i = 0; i < mDiscoveryDetailsCommentsBeanDataBeanList.size(); i++) {
                HomeDetailsCommentsBean.DataBean dataBean = mDiscoveryDetailsCommentsBeanDataBeanList.get(i);
                if (itemTypeId != 0 && itemTypeId == dataBean.getCommentId()) {
                    List<HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                    if (itemPageIndex == 0) {
                        dataBean.getSubCommentDetailDos().clear();
                    } else {
                        subCommentDetailDosBeanList.addAll(dataBean.getSubCommentDetailDos());
                    }
                    for (HomeSubCommentDetailDosBean.DataBean homeSubCommentDetailDosBean : subCommentDetailDosBeans.getData()) {
                        HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean = new HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean();
                        subCommentDetailDosBean.setReplyUserDO(homeSubCommentDetailDosBean.getReplyUserDO());
                        subCommentDetailDosBean.setUserDO(homeSubCommentDetailDosBean.getUserDO());
                        subCommentDetailDosBean.setSubCommentContent(homeSubCommentDetailDosBean.getSubCommentContent());
                        subCommentDetailDosBean.setSubCommentId(homeSubCommentDetailDosBean.getSubCommentId());
                        subCommentDetailDosBean.setSubCommentRelateId(homeSubCommentDetailDosBean.getSubCommentRelateId());
                        subCommentDetailDosBean.setSubCommentTime(homeSubCommentDetailDosBean.getSubCommentTime());
                        subCommentDetailDosBean.setSubCommentUuid(homeSubCommentDetailDosBean.getSubCommentUuid());
                        subCommentDetailDosBean.setThumbUpCount(homeSubCommentDetailDosBean.getThumbUpCount());
                        subCommentDetailDosBeanList.add(subCommentDetailDosBean);
                    }
                    dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                    mDiscoveryDetailsCommentsBeanDataBeanList.set(i, dataBean);
                    if (homeDetailsCommentsAdapter != null) {
                        homeDetailsCommentsAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean) {
        //根据期数和日期得到开奖记录
        if (lotteryRecordDtosBean != null) {
            tvDay.setText(DateUtils.timet(String.valueOf(lotteryRecordDtosBean.getCreateTime())));
            tvQishu.setText(lotteryRecordDtosBean.getPeriod() + ArmsUtils.getString(BaseApplication.getInstance(),
                    R.string.period));
            String preiodStr = (lotteryRecordDtosBean.getPeriod() < 100) ? ("0" + lotteryRecordDtosBean.getPeriod()) : ("" + lotteryRecordDtosBean.getPeriod());
            tvPictureDetailsNper.setText(lotteryRecordDtosBean.getYear() + ArmsUtils.getString(BaseApplication.getInstance(),
                    R.string.year) + preiodStr + ArmsUtils.getString(BaseApplication.getInstance(), R.string.period));
            if (lotteryRecordDtosBean.getSx() != null && lotteryRecordDtosBean.getWx() != null && lotteryRecordDtosBean.getNumbers() != null) {
                setRecord(lotteryRecordDtosBean.getSx(), lotteryRecordDtosBean.getWx(), lotteryRecordDtosBean.getNumbers());
            }

        }
    }

    @Override
    public void onStepOn(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                //这里是评论列表里面的点击逻辑处理
                itemStepOnCount = (!isItemStepOn) ? itemStepOnCount - 1 : itemStepOnCount + 1;
                if (itemStepOnCount < 0)
                    itemStepOnCount = 0;
                this.dataBean.setStepOn(isItemStepOn);
                this.dataBean.setStepOnCount(itemStepOnCount);
                //刷新单条评论数据
                updateDiscoveryDetailsCommentsAdapter(this.dataBean);
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onImgDownload() {
        savePicture();
    }

    @Override
    public void onAdvert(List<HomeBanner> resultBeanList) {
        if (resultBeanList != null && resultBeanList.size() > 0) {
            banner.setImageLoader(new BannerGlideImageLoader());
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
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


    private void getData() {
        //获取图片详情数据
        getPictureDetail();
        //获取广告
        mPresenter.getAdvert(5);
    }

    private void getLotteryRecordDtosBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("period", termsByYear);
        mPresenter.getLotteryRecordDtosBean(map);
    }


    private void getLotteryRecordDtosBean(String years, String termsByYears) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", years);
        map.put("period", termsByYears);
        mPresenter.getLotteryRecordDtosBean(map);
    }

    /**
     * 设置开奖
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


    @Subscriber(mode = ThreadMode.MAIN)
    public void onSelectApplicableEvent(SelectApplicableEvent selectApplicableEvent) {
        animRotate(ivPictureDetailsNper, true);
        isEventBus = true;
        String years = selectApplicableEvent.getYear();
        String termsByYears = selectApplicableEvent.getTermsByYear();
        if (!TextUtils.isEmpty(years) && !TextUtils.isEmpty(termsByYears)) {
            getLotteryRecordDtosBean(years, termsByYears);
            Map<String, Object> map = new HashMap<>();
            if (pictureTypeId != 0) {
                map.put("pictureTypeId", pictureTypeId);
                map.put("year", years);
                map.put("terms", termsByYears);
                map.put("commentType", "1");
                map.put("token", token);
                mPresenter.getPictureDetail(map);
            }
        }
    }


    //根据年份和图片类型获取期数
    private void getTermsByYear() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(year) && pictureTypeId != 0) {
            map.put("year", year);
            map.put("pictureTypeId", pictureTypeId);
            mPresenter.getTermsByYear(map);
        }

    }


    private void showDiscoveryDetailsCommentsDialog(String copyText, int commentType, int commentId,
                                                    int subCommentId) {
        if (mHomeDetailsCommentsDialog != null &&
                !mHomeDetailsCommentsDialog.isShowing()) {
            mHomeDetailsCommentsDialog.showDiscoveryDetailsCommentsDialog(copyText, commentType, commentId, subCommentId);
        }
    }


    @Override
    public void onDiscoveryDetailsCommentsItemClickListener(HomeDetailsCommentsBean.DataBean dataBean, HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition) {
        this.commentId = commentId;
        this.dataBean = dataBean;
        this.subCommentId = subCommentDetailDos.getSubCommentId();
        commentListType = 4;
        itemIndex = position;
        this.itemItemIndex = itemPosition;
        //二级评论点击事件
        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        if (subCommentDetailDos != null) {
            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanXX
                    userDO = subCommentDetailDos.getUserDO();
            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean
                    replyUserDO = subCommentDetailDos.getReplyUserDO();
            boolean isFalse = false;
            if (userDO != null) {
                replyUserId = userDO.getId();
                replyNickName = userDO.getNikeName();
                if (userId.equals(String.valueOf(userDO.getId()))) {
                    isFalse = true;
                }
            }
            if (replyUserDO != null) {
                if (userId.equals(String.valueOf(replyUserDO.getId()))) {
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                } else {
                    if (isFalse) {// 二级评论是自己发的显示删除
                        showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                                getSubCommentContent(), commentListType, commentId, subCommentDetailDos.getSubCommentId());
                    } else {
                        showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                    }
                }
            } else {
                if (isFalse) {
                    showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                            getSubCommentContent(), commentListType, commentId, subCommentDetailDos.getSubCommentId());
                } else {
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                }
            }
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsClickListener(HomeDetailsCommentsBean.DataBean subCommentDetailDos, int position) {
        this.dataBean = subCommentDetailDos;
        this.itemIndex = position;
        if (dataBean != null) {
            this.commentId = this.dataBean.getCommentId();
            this.subCommentId = 0;
            HomeDetailsCommentsBean.DataBean.UserDOBeanX userDOBeanX = this.dataBean.getUserDO();
            if (userDOBeanX != null) {
                replyUserId = userDOBeanX.getId();
                replyNickName = userDOBeanX.getNikeName();
                if (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "")
                        .equals(String.valueOf(userDOBeanX.getId()))) {
                    inputPanelFrameLayout.collapse();
                    commentListType = 1;
                    showDiscoveryDetailsCommentsDialog(this.dataBean.getCommentContent(), 3, commentId, subCommentId);
                } else {
                    commentListType = 3;
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title_two) + userDOBeanX.getNikeName());
                }
            }
        }
    }

    @Override
    public void onAddCommentsClickListener(int itemTypeId, int position) {
        if (itemTypeId != this.itemTypeId) {
            itemPageIndex = 0;
        } else {
            itemPageIndex = itemPageIndex + 1;
        }
        this.itemTypeId = itemTypeId;
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (itemTypeId != 0) {
            map.put("id", itemTypeId);
            map.put("commentType", 1);
            map.put("pageIndex", itemPageIndex);
            map.put("pageSize", 15);
            map.put("type", 4);
            map.put("token", token);
            mPresenter.getSubCommentDetail(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveLike(HomeDetailsCommentsBean.DataBean dataBean, int position) {
        if (isLoginStatus()) {
            this.dataBean = dataBean;
            this.itemIndex = position;
            if (this.dataBean != null) {
                //点赞
                isItemThumpUp = (this.dataBean.getIsThumpUp()) != 0 ? 0 : 1;
                itemThumbUpCount = this.dataBean.getThumbUpCount();
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("id", this.dataBean.getCommentId());
                map.put("userId", this.dataBean.getCommentUserId());
                map.put("type", 3);
                map.put("status", isItemThumpUp);
                map.put("relatedId", getIntent().getIntExtra("picId", 0));//点赞时要传这个参数
                mPresenter.getLike(map);
            }
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveNoLike(HomeDetailsCommentsBean.DataBean dataBean, int position) {
        //点踩
        if (isLoginStatus()) {
            this.dataBean = dataBean;
            this.itemIndex = position;
            if (this.dataBean != null) {
                isItemStepOn = !this.dataBean.isStepOn();
                itemStepOnCount = dataBean.getStepOnCount();
                int itemStepStatus = !isItemStepOn ? 0 : 1;
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("id", this.dataBean.getCommentId());
//            map.put("userId", dataBean.getCommentUserId());
                map.put("type", 3);
                map.put("status", itemStepStatus);
                mPresenter.getStepOn(map);
            }
        }

    }

    @Override
    public void onDiscoveryDetailsCommentsReply(HomeDetailsCommentsBean.DataBean dataBean, int position) {
        this.dataBean = dataBean;
        this.itemIndex = position;
        //评论
        if (this.dataBean != null && this.dataBean.getUserDO() != null) {
            HomeDetailsCommentsBean.DataBean.UserDOBeanX userDOBeanX = this.dataBean.getUserDO();
            String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
            if (userDOBeanX != null) {
                if (String.valueOf(userDOBeanX.getId()).equals(userId)) {
                    this.subCommentId = 0;
                    this.commentId = this.dataBean.getCommentId();
                    commentListType = 1;
                    replyUserId = 0;
                    replyNickName = "";
                    showDiscoveryDetailsCommentsDialog(this.dataBean.getCommentContent(), 3, commentId, commentListType);
                } else {
                    this.subCommentId = 0;
                    this.commentId = this.dataBean.getCommentId();
                    commentListType = 3;
                    replyUserId = userDOBeanX.getId();
                    pageIndex = 0;
                    replyNickName = "";
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title_two) + userDOBeanX.getNikeName());
                }
            }
        }
    }

    @Override
    public void onDiscoveryUserHisInformation(int userID) {
        if (isLoginStatus()) {
            Bundle bundle = new Bundle();
            bundle.putString("userId", String.valueOf(userID));
            isToOtherActivity(HisInformationActivity.class, bundle);
        }
    }


    //请求评论数据
    private void requestCommentDetail() {
        Map<String, Object> map = new HashMap<>();
        if (picId != 0) {
            map.put("id", picId);
            map.put("commentType", "1");
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 15);
            map.put("type", 3);
            map.put("token", token);
            mPresenter.getCommentDetail(map);
        }

    }


    @Override
    public void onDiscoveryDetailsCommentsDelete(int type, int commentId, int subCommentId) {
        if (mHomeDetailsCommentsDialog != null) {
            mHomeDetailsCommentsDialog.dismiss();
        }
        if (!isLoginStatus()) {
            return;
        } else {
            //进行删除评论操作
            Map<String, Object> map = new HashMap<>();
            map.put("type", type);
            map.put("commentId", commentId);
            map.put("token", token);
            map.put("subCommentId", subCommentId);
            map.put("id", picId);
            map.put("commentType", 1);
            mPresenter.getDeleteComment(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsCopy(String stringText) {
        copyString(stringText);
    }


    /**
     * 复制文本内容
     *
     * @param copyString 需要复制的内容
     */
    private void copyString(String copyString) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyString);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        if (mHomeDetailsCommentsDialog != null && mHomeDetailsCommentsDialog.isShowing()) {
            mHomeDetailsCommentsDialog.dismiss();
        }
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.copy_text_successfully));
    }


    //设置评论列表到第一个位置
    private void setScrollToPosition() {
//        Log.e("setScrollToPosition", "---setScrollToPosition");
//        mNestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
        mNestedScrollView.scrollTo(0, idAllComments.getBottom());
    }

    @Override
    public void onKeyboardHidden() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
