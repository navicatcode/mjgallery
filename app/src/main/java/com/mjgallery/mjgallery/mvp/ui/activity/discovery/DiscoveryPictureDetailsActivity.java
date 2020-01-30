package com.mjgallery.mjgallery.mvp.ui.activity.discovery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
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
import com.mjgallery.mjgallery.app.view.dialog.home.discovery.DiscoveryDetailsCommentsDialog;
import com.mjgallery.mjgallery.awildfire.ConversationInputPanel;
import com.mjgallery.mjgallery.awildfire.InputAwareLayout;
import com.mjgallery.mjgallery.awildfire.KeyboardAwareLinearLayout;
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryPictureDetailsComponent;
import com.mjgallery.mjgallery.event.SelectApplicableEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryPictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata.DiscoveryPictureDetailsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.SelectApplicableActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails.DiscoverDetailsCommentsAdapter;
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
import java.util.Calendar;
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
 * Description: 发现详情界面
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class DiscoveryPictureDetailsActivity extends MJBaseActivity<DiscoveryPictureDetailsPresenter>
        implements DiscoveryPictureDetailsContract.View, KeyboardAwareLinearLayout.OnKeyboardHiddenListener, DiscoveryDetailsCommentsDialog.IDiscoveryDetailsComments, DiscoverDetailsCommentsAdapter.IDiscoveryDetailsCommentsItemClickListener, ConversationInputPanel.OnConversationInputPanelStateChangeListener, KeyboardAwareLinearLayout.OnKeyboardShownListener {

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
    @BindView(R.id.ivPictureDetailsNper)
    UIImageView ivPictureDetailsNper;
    @BindView(R.id.llPictureDetailsNper)
    LinearLayout llPictureDetailsNper;
    @BindView(R.id.mShadowLayoutTop)
    ShadowLayout mShadowLayoutTop;
    @BindView(R.id.tvPetElvesDetailsTitle)
    TextView tvPetElvesDetailsTitle;
    @BindView(R.id.tvPetElvesDetailsFocusOn)
    TextView tvPetElvesDetailsFocusOn;
    @BindView(R.id.ivHomePictureDetailsImg)
    ImageView ivHomePictureDetailsImg;
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
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.inputPanelFrameLayout)
    ConversationInputPanel inputPanelFrameLayout;
    @BindView(R.id.mPhotoView)
    PhotoView mPhotoView;
    @BindView(R.id.mInputAwareLayout)
    InputAwareLayout mInputAwareLayout;
    GestureDetector gestureDetector;
    GoodView mGoodView;
    int pageIndex = 0;
    String year;
    int imgId;
    List<DiscoveryCommentsBean.DataBean> beanList;
    DiscoverDetailsCommentsAdapter discoverDetailsCommentsAdapter;
    int isCollection;//是否收藏 0是没有收藏 1是已经收藏
    int isThumpUp;//是否点赞  0是没有点赞  1是已经点赞
    boolean isConcern = false;//是否添加了关注
    @BindView(R.id.rlPictureDetailsTop)
    RelativeLayout rlPictureDetailsTop;
    @BindView(R.id.tvPictureDetailsTop)
    TextView tvPictureDetailsTop;
    @BindView(R.id.mPictureDetailsImgDownload)
    UIImageView mPictureDetailsImgDownload;
    @BindView(R.id.tvPetElvesDetailsViews)
    TextView tvPetElvesDetailsViews;
    private float mLastX;
    int commentListType = 2;//评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
    int commentId = 0;//评论id(一级评论不需要，二级评论需要带上)
    int subCommentId = 0;//子评论id
    int replyUserId = 0;
    int itemTypeId = 0;
    int itemPageIndex = 0;
    String picUrl;
    int userId = 0;//被点赞人id
    int concernUserId = 0;
    String replyNickName = "";
    String concernUserNickName = "";
    List<String> mSelectByYeryList;


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
    DiscoveryCommentsBean.DataBean dataBean;


    DiscoveryDetailsCommentsDialog mDiscoveryDetailsCommentsDialog;
    int imgIndex = 0;
    List<DiscoveryDetailBean.DataBean.ShowPictureDoListBean> showPictureDoListBeans;
    //广告图片url
    String advertUrl = null;
    boolean isEventBus = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryPictureDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_discovery_picture_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isEventBus = false;
        //输入表情界面初始化
        inputPanelFrameLayout.init(this, mInputAwareLayout);
        //表情输入法事件监听
        mInputAwareLayout.addOnKeyboardShownListener(this);
        mInputAwareLayout.addOnKeyboardHiddenListener(this);
        inputPanelFrameLayout.setOnConversationInputPanelStateChangeListener(this);
        //滑动监听
        gestureDetector = new GestureDetector(this, listener);
        mDiscoveryDetailsCommentsDialog = new DiscoveryDetailsCommentsDialog(this, this);
        //显示头部布局和设置颜色
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        tvTopTitle.setVisibility(View.VISIBLE);

        ivTopTitle.setVisibility(View.GONE);
        GlideUtil.loadNormalImageSV(ivTopReturn, R.drawable.activity_return_while_img);
        ivTopRightImg.setVisibility(View.VISIBLE);
        GlideUtil.loadNormalImageSV(ivTopRightImg, R.drawable.home_fengxiang);
        mSelectByYeryList = new ArrayList<>();
        beanList = new ArrayList<>();
        // 初始化评论列表适配器和列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        discoverDetailsCommentsAdapter = new DiscoverDetailsCommentsAdapter(getApplication(),
                R.layout.adapter_discovery_details_comments_item,
                beanList, this, false);
        discoverDetailsCommentsAdapter.setEmptyView(R.layout.adapter_discovery_details_comments_item_no_date, mSmartRefreshLayout);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(discoverDetailsCommentsAdapter);
        //下拉和上拉刷新加载评论数据'
        showPictureDoListBeans = new ArrayList<>();
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
                getDiscoveryDetail();
                pageIndex = 0;
                requestCommentDetail();
            }
        });
        mGoodView = new GoodView(this);
        onNewIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //获取上个界面传递的信息
        if (!isEventBus) {
            if (intent != null) {
                imgId = intent.getIntExtra("imgId", 0);
            }
            Calendar calendar = Calendar.getInstance();//
            year = String.valueOf(calendar.get(Calendar.YEAR));
            getDiscoveryDetail();
            getSelectByYery();
            mPresenter.getAdvert(5);
        }
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
//            savePicture();
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
                if (imgIndex == showPictureDoListBeans.size() - 1) {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_three_title));
                } else {
                    imgIndex++;
                    if (imgIndex < showPictureDoListBeans.size()) {
                        ViewGroup.LayoutParams layoutParams = ivHomePictureDetailsImg.getLayoutParams();
                        int screenWidth = ScreenUtils.getScreenWidth();
                        DiscoveryDetailBean.DataBean.ShowPictureDoListBean showPictureDoListBean = showPictureDoListBeans.get(imgIndex);
                        if (showPictureDoListBean != null) {
                            int height = showPictureDoListBean.getHeight();
                            int width = showPictureDoListBean.getWidth();
                            layoutParams.height = (int) (screenWidth / (width * 1.0 / height));
                            RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
                            picUrl = showPictureDoListBean.getFilePath().split("[?]")[0];
                            GlideUtil.loadRequestOptions(ivHomePictureDetailsImg, picUrl, options);
                            setImageClick(picUrl);
                        }

                    }
                }
            } else if (e2.getX() - e1.getX() > verticalMinDistance) {
                LogUtils.e("muuv", "向右手势");
                if (imgIndex == 0) {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_four_title));
                } else {
                    imgIndex--;
                    if (imgIndex < showPictureDoListBeans.size()) {
                        ViewGroup.LayoutParams layoutParams = ivHomePictureDetailsImg.getLayoutParams();
                        int screenWidth = ScreenUtils.getScreenWidth();
                        DiscoveryDetailBean.DataBean.ShowPictureDoListBean showPictureDoListBean = showPictureDoListBeans.get(imgIndex);
                        if (showPictureDoListBean != null) {
                            int height = showPictureDoListBean.getHeight();
                            int width = showPictureDoListBean.getWidth();
                            layoutParams.height = (int) (screenWidth / (width * 1.0 / height));
                            RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
                            picUrl = showPictureDoListBean.getFilePath().split("[?]")[0];
                            GlideUtil.loadRequestOptions(ivHomePictureDetailsImg, picUrl, options);
                            setImageClick(picUrl);
                        }

                    }
                }

            }
            return true;
        }
    };


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
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

    @Override
    public void onInputPanelExpanded() {

    }

    @Override
    public void onInputPanelCollapsed() {

    }

    @Override
    public void onSendText(String sendText) {
        Map<String, Object> map = new HashMap<>();
        if (isLoginStatus()) {
            map.put("token", token);
            map.put("id", imgId);
            map.put("type", commentListType);
            if (commentId != 0) {
                map.put("commentId", commentId);
            }

            map.put("commentContent", sendText);
            if (subCommentId != 0) {
                map.put("subCommentId", subCommentId);
            }
            if (replyUserId != 0) {
                map.put("replyUserId", replyUserId);
            }
            map.put("commentType", 2);
            if (!TextUtils.isEmpty(replyNickName)) {
                map.put("replyNickName", replyNickName);
            }
            map.put("relatedType", 2);//发现
            mPresenter.getAddCommentDiscovery(map);
        }
    }

    @Override
    public void onKeyboardShown() {

    }


    @Override
    public void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean dataBean,
                                                            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition) {
        this.commentId = commentId;
        this.subCommentId = subCommentDetailDos.getSubCommentId();
        commentListType = 4;
        this.itemIndex = position;
        this.itemItemIndex = itemPosition;
        this.dataBean = dataBean;
        //二级评论点击事件
        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        if (subCommentDetailDos != null) {

            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanX
                    userDO = subCommentDetailDos.getUserDO();
            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean
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
                    if (isFalse) {
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
    public void onDiscoveryDetailsCommentsClickListener(DiscoveryCommentsBean.DataBean dataBean, int position) {
        this.commentId = dataBean.getCommentId();
        this.subCommentId = 0;
        commentListType = 3;
        this.dataBean = dataBean;
        this.itemIndex = position;

        if (dataBean != null) {
            this.commentId = dataBean.getCommentId();
            this.subCommentId = 0;

            DiscoveryCommentsBean.DataBean.UserDOBean userDOBean = dataBean.getUserDO();
            if (userDOBean != null) {
                replyUserId = userDOBean.getId();
                replyNickName = userDOBean.getNikeName();
                if (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "")
                        .equals(String.valueOf(userDOBean.getId()))) {
                    inputPanelFrameLayout.collapse();
                    commentListType = 2;
                    showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), 3, commentId, commentListType);
                } else {
                    commentListType = 3;
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title_two) + userDOBean.getNikeName());
                }
            }

        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveLike(DiscoveryCommentsBean.DataBean dataBean, int position) {
        if (isLoginStatus()) {
            this.dataBean = dataBean;
            isItemThumpUp = (this.dataBean.getIsThumpUp()) != 0 ? 0 : 1;
            itemThumbUpCount = this.dataBean.getThumbUpCount();
            this.itemIndex = position;
            userId = dataBean.getUserDO().getId();
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("id", dataBean.getCommentId());
            if (userId != 0) {
                map.put("userId", userId);
            }
            map.put("type", 3);
            map.put("status", isItemThumpUp);
            map.put("relatedType", 2);//发现
            map.put("relatedId", getIntent().getIntExtra("imgId", 0));//发现点赞时要传这个参数
            mPresenter.getThumbUpDiscovery(map);
        }
    }

    @Override
    public void onAddCommentsClickListener(int itemTypeId, int position) {
        this.itemIndex = position;
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
            map.put("commentType", 2);
            map.put("pageIndex", itemPageIndex);
            map.put("pageSize", 15);
            map.put("type", 4);
            map.put("token", token);
            mPresenter.getDiscoverSubCommentDetailDosBean(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveNoLike(DiscoveryCommentsBean.DataBean dataBean, int position) {
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
    public void onDiscoveryDetailsCommentsReply(DiscoveryCommentsBean.DataBean dataBean, int position) {
        //评论
        if (dataBean != null && dataBean.getUserDO() != null) {
            this.dataBean = dataBean;
            this.itemIndex = position;
            DiscoveryCommentsBean.DataBean.UserDOBean userDOBeanX = dataBean.getUserDO();
            String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
            if (userDOBeanX != null) {
                replyNickName = concernUserNickName;
                if (String.valueOf(userDOBeanX.getId()).equals(userId)) {
                    //评论是自己发的,显示删除
                    this.subCommentId = 0;
                    this.commentId = dataBean.getCommentId();
                    commentListType = 2;
                    replyUserId = 0;
                    showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), 3, dataBean.getCommentId(), 0);
                } else {
                    this.subCommentId = 0;
                    this.commentId = dataBean.getCommentId();
                    commentListType = 3;
                    pageIndex = 0;
                    replyUserId = userDOBeanX.getId();
                    commentListType = 3;
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title_two) + userDOBeanX.getNikeName());
                }
            }

        }
    }

    @Override
    public void onDiscoveryUserHisInformation(int userID) {
        if (isLoginStatus()) {
            Bundle bundle = new Bundle();
            bundle.putString("userId", String.valueOf(userId));
            isToOtherActivity(HisInformationActivity.class, bundle);
        }
    }


    private void showDiscoveryDetailsCommentsDialog(String copyText, int commentType, int commentId,
                                                    int subCommentId) {
        if (mDiscoveryDetailsCommentsDialog != null &&
                !mDiscoveryDetailsCommentsDialog.isShowing()) {
            mDiscoveryDetailsCommentsDialog.showDiscoveryDetailsCommentsDialog(copyText, commentType, commentId, subCommentId);
        }
    }


    @OnClick({R.id.ivTopReturn, R.id.tvPetElvesDetailsFocusOn, R.id.mPictureDetailsImgDownload, R.id.inputPanelFrameLayout, R.id.ivPetElvesDetailsShare,
            R.id.ivPetElvesDetailsComments, R.id.ivPetElvesDetailsGiveLike, R.id.tvPetElvesDetailsGiveLike, R.id.ivPetElvesDetailsCollection, R.id.tvPetElvesDetailsCollection,
            R.id.tvTopTitle, R.id.llPictureDetailsNper, R.id.tvPictureDetailsNper, R.id.ivTopRightImg})
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
                setCommentNormal();
                showSoftInputFromWindow("");
                break;
            case R.id.ivPetElvesDetailsCollection:
            case R.id.tvPetElvesDetailsCollection:
                onCollectionClick();
                break;
            case R.id.ivPetElvesDetailsGiveLike:
            case R.id.tvPetElvesDetailsGiveLike:
                isClickGiveLikeImageDetails = true;
                onGiveLike(tvPetElvesDetailsGiveLike);
                break;

            case R.id.tvPetElvesDetailsFocusOn:
                //关注和去取消按钮的点击事件方法
                onPetElvesDetailsFocusOnClick();
                break;

        }
    }

    //添加关注和取消关注
    private void onPetElvesDetailsFocusOnClick() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        //判断当前是否已经关注来了，没有关注就添加关注，关注了取消关注
        if (isConcern) {
            map.put("id", concernUserId);
            mPresenter.getCancelUser(map);
        } else {
            map.put("userId", concernUserId);
            mPresenter.getConcernUser(map);
        }

    }

    //点赞
    private void onGiveLike(View view) {
        if (!isLoginStatus()) {
            return;
        } else {
            if (isThumpUp == 0) {
                mGoodView.setText("+1");
                mGoodView.show(view);
            }
            isThumpUp = (isThumpUp != 0) ? 0 : 1;
            Map<String, Object> map = new HashMap<>();
            map.put("id", imgId);
            if (userId != 0) {
                map.put("userId", userId);
                map.put("type", 2);
                map.put("status", isThumpUp);
                map.put("token", token);
                map.put("relatedType", 2);//发现
                map.put("relatedId", getIntent().getIntExtra("imgId", 0));//发现点赞时要传这个参数
                mPresenter.getThumbUpDiscovery(map);

            }
        }
    }


    //收藏点击事件
    private void onCollectionClick() {
        if (!isLoginStatus()) {
            return;
        }
        //        subType", value = "发现的type，0-图片，1-视频，2-资料
        Map<String, Object> map = new HashMap<>();
        //根据收藏状态判断当前是取消还是添加收藏
        if (isCollection != 0) {
            if (!TextUtils.isEmpty(String.valueOf(imgId))) {
                map.put("picId", imgId);
                map.put("type", 2);
                map.put("token", token);
                mPresenter.getCancelCollection(map);
            }
        } else {
            if (!TextUtils.isEmpty(String.valueOf(imgId))) {
                map.put("tmpId", imgId);
                map.put("type", 2);
                map.put("subType", 0);
                map.put("token", token);
                mPresenter.getAddCollection(map);
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(String draftString) {
        inputPanelFrameLayout.show(mInputAwareLayout, draftString);
    }


    public void animRotate(View view, boolean isExit) {
        if (!isExit) {
            view.animate().setDuration(500).rotation(180).start();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isHome", false);
            toOtherActivity(SelectApplicableActivity.class, bundle);
            overridePendingTransition(R.anim.dialog_show_anim, 0);
        } else {
            view.animate().setDuration(500).rotation(0).start();
        }
    }


    private void getDiscoveryDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", 15);
        map.put("pageIndex", pageIndex);
        map.put("token", token);
        map.put("id", imgId);
        map.put("type", 1);
        mPresenter.getDiscoveryDetail(map);
    }


    @Override
    public void onDiscoveryDetail(DiscoveryDetailBean discoveryDetailBean) {
        if (discoveryDetailBean != null) {
            if (discoveryDetailBean.getData() != null) {
                //得到实体对象
                DiscoveryDetailBean.DataBean dataBea = discoveryDetailBean.getData();
                if (dataBea != null) {

                    //是否点赞过
                    isThumpUp = dataBea.getIsThumpUp();
                    //是否收藏过
                    isCollection = dataBea.getIsCollection();
                    //是否点赞过
                    isThumpUp = dataBea.getIsThumpUp();
                    //点赞的数量
                    supportCount = dataBea.getThumbUpCount();
                    // 收藏的数量
                    collectionCount = dataBea.getCollectionCount();
                    //评论的数量
                    picComment = dataBea.getCommentCount();
                    DiscoveryDetailBean.DataBean.UserDOBean userDOBean = dataBea.getUserDO();
                    if (userDOBean != null) {
                        tvPetElvesDetailsTitle.setText(userDOBean.getNikeName());
                        concernUserId = userDOBean.getId();
                        concernUserNickName = userDOBean.getNikeName();
                        isConcern = userDOBean.isConcern();
                        replyUserId = userDOBean.getId();
                        userId = userDOBean.getId();
                        replyNickName = concernUserNickName;
                    }
                    updateThumpUpAndCollectionImgAndConcernStatus();
                    tvTopTitle.setText(dataBea.getDescription());
                    tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
                    ViewGroup.LayoutParams layoutParams = ivHomePictureDetailsImg.getLayoutParams();
                    int screenWidth = ScreenUtils.getScreenWidth();
                    pageIndex = 0;
                    requestCommentDetail();
                    showPictureDoListBeans.clear();
                    if (dataBea.getClickCount() > 10000) {
                        BigDecimal bigDecimalClickCount = new BigDecimal(dataBea.getClickCount());
                        BigDecimal bigDecimalChu = new BigDecimal(10000);
                        float clickCount = bigDecimalClickCount.divide(bigDecimalChu)
                                .setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                        tvPetElvesDetailsViews.setText(clickCount + ArmsUtils.getString(getApplication(),R.string.wan));
                    } else {
                        tvPetElvesDetailsViews.setText(dataBea.getClickCount() + "");
                    }
                    if (dataBea.getShowPictureDoList() != null && dataBea.getShowPictureDoList().size() > 0) {
                        showPictureDoListBeans.addAll(dataBea.getShowPictureDoList());
                        DiscoveryDetailBean.DataBean.ShowPictureDoListBean showPictureDoListBean = dataBea.getShowPictureDoList().get(0);
                        if (showPictureDoListBean != null) {
                            int height = showPictureDoListBean.getHeight();
                            int width = showPictureDoListBean.getWidth();
                            layoutParams.height = (int) (screenWidth / (width * 1.0 / height));
                            RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
                            picUrl = showPictureDoListBean.getFilePath().split("[?]")[0];
                            GlideUtil.loadRequestOptions(ivHomePictureDetailsImg, picUrl, options);
                            setImageClick(picUrl);
                        }
                    }
                }
            }
        }

    }


    //刷新图片详情的点赞图片和数量
    private void updateThumpUpImgAndThumpUpCount() {
        //设置点赞数量
        tvPetElvesDetailsGiveLike.setText(supportCount + "");
        if (isThumpUp != 0 && supportCount > 0) {
            GlideUtil.loadNormalImage(ivPetElvesDetailsGiveLike, R.drawable.pet_elves_details_give_lick_click_img);
        } else {
            GlideUtil.loadNormalImage(ivPetElvesDetailsGiveLike, R.drawable.pet_elves_details_give_lick_img);
        }
    }


    //刷新图片详情的收藏图片和数量
    private void updateCollectionImgAndCollectionCount() {
        //设置收藏数量
        tvPetElvesDetailsCollection.setText(collectionCount + "");
        if (isCollection != 0 && collectionCount > 0) {
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
    private void updateThumpUpAndCollectionImgAndConcernStatus() {
        updateCollectionImgAndCollectionCount();
        updateThumpUpImgAndThumpUpCount();
        updateThumpUpAndCollectionCount();
        updateConcernStatus();
    }


    //修改关注状态
    private void updateConcernStatus() {
        if (isConcern) {
            tvPetElvesDetailsFocusOn.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.focus_on_two));
        } else {
            tvPetElvesDetailsFocusOn.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.focus_on_one));
        }
    }


    //设置图片点击事件
    public void setImageClick(String url) {
        // 获取/设置 最大缩放倍数
        RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
        GlideUtil.loadRequestOptions(mPhotoView, url, options);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoView != null) {
                    mPhotoView.setVisibility(View.GONE);
                }
                if (ivHomePictureDetailsImg != null) {
                    ivHomePictureDetailsImg.setVisibility(View.VISIBLE);
                }
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


    @Override
    public void onThumbUpDiscovery(BaseResponse baseResponse) {
        if (baseResponse != null) {
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
            showMessage(baseResponse.getMessage());
        }
    }


    // 刷新单条评论数据
    private void updateDiscoveryDetailsCommentsAdapter(DiscoveryCommentsBean.DataBean dataBean) {
        if (beanList.size() > itemIndex) {
            beanList.set(itemIndex, dataBean);
        }
        if (discoverDetailsCommentsAdapter != null) {
            discoverDetailsCommentsAdapter.notifyDataSetChanged();
        }
    }

    //请求评论数据
    private void requestCommentDetail() {
        Map<String, Object> map = new HashMap<>();
        if (imgId != 0) {
            map.put("id", imgId);
            map.put("commentType", 2);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 15);
            map.put("type", 3);
            map.put("token", token);
            mPresenter.getCommentDetail(map);
        }

    }


    @Override
    public void onCommentDetail(DiscoveryCommentsBean homeDetailsCommentsBean) {

        if (homeDetailsCommentsBean!=null) {
            if (pageIndex == 0) {
                beanList.clear();
            }

            if (homeDetailsCommentsBean.getData() != null) {
                beanList.addAll(homeDetailsCommentsBean.getData());
            }
            if (discoverDetailsCommentsAdapter != null) {
                discoverDetailsCommentsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDiscoverSubCommentDetailDosBean(DiscoverSubCommentDetailDosBean discoverSubCommentDetailDosBean) {
        if (discoverSubCommentDetailDosBean != null && discoverSubCommentDetailDosBean.getData() != null && discoverSubCommentDetailDosBean.getData().size() > 0) {
            for (int i = 0; i < beanList.size(); i++) {
                DiscoveryCommentsBean.DataBean dataBean = beanList.get(i);
                if (itemTypeId != 0 && itemTypeId == dataBean.getCommentId()) {
                    List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                    if (itemPageIndex == 0) {
                        dataBean.getSubCommentDetailDos().clear();
                    } else {
                        subCommentDetailDosBeanList.addAll(dataBean.getSubCommentDetailDos());
                    }

                    for (DiscoverSubCommentDetailDosBean.DataBean homeSubCommentDetailDosBean : discoverSubCommentDetailDosBean.getData()) {
                        DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean = new DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean();
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
                    beanList.set(i, dataBean);
                    if (discoverDetailsCommentsAdapter != null) {
                        discoverDetailsCommentsAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onAddCommentDiscovery(BaseResponse<DiscoveryCommentsBean.DataBean> dataBeanBaseResponse) {
        inputPanelFrameLayout.collapse();
        if (dataBeanBaseResponse != null) {
            DiscoveryCommentsBean.DataBean discoveryCommentsBeanData = dataBeanBaseResponse.getResult();
            if (dataBeanBaseResponse.getCode() == 0 && discoveryCommentsBeanData != null) {
                //评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
                switch (commentListType) {
                    case 2:
                        picComment = picComment + 1;
                        updateThumpUpAndCollectionCount();
                        beanList.add(0, discoveryCommentsBeanData);
                        setScrollToPosition();
                        if (discoverDetailsCommentsAdapter != null) {
                            discoverDetailsCommentsAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                    case 4:
                        if (discoveryCommentsBeanData != null &&
                                discoveryCommentsBeanData.getSubCommentDetailDos().size() > 0) {
                            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean = discoveryCommentsBeanData.getSubCommentDetailDos().get(0);
                            List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                            if (this.dataBean.getSubCommentDetailDos() != null && this.dataBean.getSubCommentDetailDos().size() > 0) {
                                subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                                this.dataBean.getSubCommentDetailDos().clear();
                            }
                            if (subCommentDetailDosBean != null) {
                                subCommentDetailDosBeanList.add(0, subCommentDetailDosBean);
                            }
                            this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                            if (beanList.size() > itemIndex) {
                                beanList.set(itemIndex, this.dataBean);
                            }
                            if (discoverDetailsCommentsAdapter != null) {
                                discoverDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }


                setCommentNormal();
            }
            showMessage(dataBeanBaseResponse.getMessage());
        }
    }

    //设置评论列表到第一个位置
    private void setScrollToPosition() {
//        Log.e("setScrollToPosition", "---setScrollToPosition");
//        mNestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
        mNestedScrollView.scrollTo(0, idAllComments.getBottom());
    }

    @Override
    public void onCircleDeleteComment(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                switch (commentListType) {
                    case 2:
                        if (beanList.size() > itemIndex) {
                            beanList.remove(itemIndex);
                            picComment = picComment - 1;
                            updateThumpUpAndCollectionCount();
                            if (discoverDetailsCommentsAdapter != null) {
                                discoverDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case 3:
                    case 4:
                        if (this.dataBean != null && this.dataBean.getSubCommentDetailDos() != null) {
                            List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                            subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                            if (subCommentDetailDosBeanList.size() > itemItemIndex) {
                                subCommentDetailDosBeanList.remove(itemItemIndex);
                            }
                            this.dataBean.getSubCommentDetailDos().clear();
                            this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                            if (beanList.size() > itemIndex) {
                                beanList.set(itemIndex, this.dataBean);
                                if (discoverDetailsCommentsAdapter != null) {
                                    discoverDetailsCommentsAdapter.notifyDataSetChanged();
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
    public void onAddCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                collectionCount++;
                isCollection = 1;
                //刷新图片详情的收藏图片和数量
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
            if (baseResponse.getCode() == 0) {
                isConcern = !isConcern;
                updateConcernStatus();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onConcernUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                isConcern = !isConcern;
                updateConcernStatus();
            }
            showMessage(baseResponse.getMessage());
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


    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDoShare() {
//        if (TextUtils.isEmpty(picUrl)) {
//            showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_details_share));
//            return;
//        }
        String shareUrl = SPUtils.getInstance().getString("shareUrl", AppConstants.APP_DOMAIN+"common/share?type=2&id="+getIntent().getIntExtra("imgId", 0));
        ShareUtils.doShare(DiscoveryPictureDetailsActivity.this, shareUrl,true);
    }

    @Override
    public void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean) {
        //根据期数和日期得到开奖记录
        if (lotteryRecordDtosBean != null) {
            tvDay.setText(DateUtils.timet(String.valueOf(lotteryRecordDtosBean.getLotteryTime())));
            String preiodStr = (lotteryRecordDtosBean.getPeriod() < 100) ? ("0" + lotteryRecordDtosBean.getPeriod()) : ("" + lotteryRecordDtosBean.getPeriod());
            tvPictureDetailsNper.setText(lotteryRecordDtosBean.getYear() + ArmsUtils.getString(BaseApplication.getInstance(), R.string.year) +
                    preiodStr + ArmsUtils.getString(BaseApplication.getInstance(), R.string.period));
            tvQishu.setText(preiodStr + ArmsUtils.getString(BaseApplication.getInstance(), R.string.period));
            setRecord(lotteryRecordDtosBean.getSx(), lotteryRecordDtosBean.getWx(), lotteryRecordDtosBean.getNumbers());
        }
    }


    /**
     *
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


    @Override
    public void onSelectByYery(List<String> stringList) {
        mSelectByYeryList.clear();
        mSelectByYeryList.addAll(stringList);
        if (stringList.size() > 0) {
            getLotteryRecordDtosBean(year, mSelectByYeryList.get(0));
        }
    }

    @Override
    public void onStepOn(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
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


    /**
     * 保存图片
     */
    public void savePicture() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                e.onNext(Glide.with(DiscoveryPictureDetailsActivity.this)
                        .load(picUrl)
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
                        FileUtil.copyFile(file, destFile, destFile.getPath(), fileName, DiscoveryPictureDetailsActivity.this);
                        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.in_download_title_one) + destFile.getPath());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.in_download_title_error));
                    }
                });
    }


    @Subscriber(mode = ThreadMode.MAIN)
    public void onSelectApplicableEvent(SelectApplicableEvent selectApplicableEvent) {
        isEventBus = false;
        animRotate(ivPictureDetailsNper, true);
        String years = selectApplicableEvent.getYear();
        String termsByYears = selectApplicableEvent.getTermsByYear();
        if (!TextUtils.isEmpty(years) && !TextUtils.isEmpty(termsByYears)) {
            getLotteryRecordDtosBean(years, termsByYears);
        }

    }

    private void getLotteryRecordDtosBean(String years, String termsByYears) {
        Map<String, Object> map = new HashMap<>();
        if (TextUtils.isEmpty(years)) {
            map.put("year", year);
        } else {
            map.put("year", years);
        }
        if (TextUtils.isEmpty(termsByYears) && mSelectByYeryList.size() > 0) {
            map.put("period", mSelectByYeryList.get(0));
        } else {
            map.put("period", termsByYears);
        }
        mPresenter.getLotteryRecordDtosBean(map);
    }


    //获取开奖记录的期数
    private void getSelectByYery() {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        mPresenter.getSelectByYery(map);
    }

    @Override
    public void onDiscoveryDetailsCommentsDelete(int type, int commentId, int subCommentId) {
        if (mDiscoveryDetailsCommentsDialog != null) {
            mDiscoveryDetailsCommentsDialog.dismiss();
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
            map.put("id", imgId);
            map.put("commentType", 2);
            mPresenter.getCircleDeleteComment(map);
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
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.copy_text_successfully));
    }

    @Override
    public void onKeyboardHidden() {

    }


    //设置评论初始状态
    private void setCommentNormal() {
        this.subCommentId = 0;
        this.commentId = 0;
        commentListType = 2;
        replyUserId = concernUserId;
        replyNickName = concernUserNickName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
