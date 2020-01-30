package com.mjgallery.mjgallery.mvp.ui.activity.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.FileUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.DownloadPicturesDialog;
import com.mjgallery.mjgallery.app.view.dialog.YearsDialog;
import com.mjgallery.mjgallery.app.view.dialog.home.HomeDetailsCommentsDialog;
import com.mjgallery.mjgallery.awildfire.ConversationInputPanel;
import com.mjgallery.mjgallery.awildfire.InputAwareLayout;
import com.mjgallery.mjgallery.awildfire.KeyboardAwareLinearLayout;
import com.mjgallery.mjgallery.di.component.DaggerHomeDetailsComponent;
import com.mjgallery.mjgallery.mvp.contract.home.HomeDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBarrageDataBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.presenter.home.HomeDetailsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryRecordActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.HomeDetailsCommentsAdapter;
import com.mjgallery.mjgallery.superlike.SuperLikeLayout;
import com.mjgallery.mjgallery.widget.UIImageView;
import com.mjgallery.mjgallery.widget.goodview.GoodView;
import com.orient.tea.barragephoto.adapter.BarrageAdapter;
import com.orient.tea.barragephoto.ui.BarrageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:首页图片第一版详情
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class HomeDetailsActivity extends MJBaseActivity<HomeDetailsPresenter> implements
        HomeDetailsContract.View, GestureDetector.OnGestureListener, HomeDetailsCommentsDialog.IDiscoveryDetailsComments,
        BaseQuickAdapter.OnItemChildClickListener, KeyboardAwareLinearLayout.OnKeyboardShownListener,
        KeyboardAwareLinearLayout.OnKeyboardHiddenListener, ConversationInputPanel.OnConversationInputPanelStateChangeListener,
        YearsDialog.IYearsSelectDialog, HomeDetailsCommentsAdapter.IDiscoveryDetailsCommentsItemClickListener {

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
    @BindView(R.id.ivHDImageView)
    PhotoView ivHDImageView;
    @BindView(R.id.mBarrageView)
    BarrageView mBarrageView;
    @BindView(R.id.ivDownloadPictures)
    UIImageView ivDownloadPictures;
    @BindView(R.id.ivComments)
    ImageView ivComments;
    @BindView(R.id.tComments)
    TextView tComments;
    @BindView(R.id.llComments)
    RelativeLayout llComments;
    @BindView(R.id.ivCollection)
    UIImageView ivCollection;
    @BindView(R.id.tvCollection)
    TextView tvCollection;
    @BindView(R.id.llCollection)
    RelativeLayout llCollection;
    @BindView(R.id.ivGiveLike)
    UIImageView ivGiveLike;
    @BindView(R.id.tvGiveLike)
    TextView tvGiveLike;
    @BindView(R.id.llGiveLike)
    RelativeLayout llGiveLike;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.llShare)
    RelativeLayout llShare;
    @BindView(R.id.tvImgTitleName)
    TextView tvImgTitleName;
    @BindView(R.id.rlImgTitleName)
    RelativeLayout rlImgTitleName;
    @BindView(R.id.etComments)
    TextView etComments;
    @BindView(R.id.ivOpenBarrage)
    ImageView ivOpenBarrage;
    @BindView(R.id.ivCloseBarrage)
    ImageView ivCloseBarrage;
    @BindView(R.id.rlCommentsBg)
    RelativeLayout rlCommentsBg;
    @BindView(R.id.rlSendComments)
    RelativeLayout rlSendComments;
    @BindView(R.id.mSuperLikeLayout)
    SuperLikeLayout mSuperLikeLayout;
    @BindView(R.id.tvSendComments)
    TextView tvSendComments;
    @BindView(R.id.llCommentListOne)
    LinearLayout llCommentListOne;
    @BindView(R.id.tvDiscoveryDetailsCommentsTitle)
    TextView tvDiscoveryDetailsCommentsTitle;
    @BindView(R.id.rlDialogClose)
    RelativeLayout rlDialogClose;
    @BindView(R.id.llDiscoveryDetailsCommentsTitle)
    LinearLayout llDiscoveryDetailsCommentsTitle;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.etDiscoveryDetailsCommentsDialog)
    EditText etDiscoveryDetailsCommentsDialog;
    @BindView(R.id.rlDiscoveryDetailsDialogComments)
    RelativeLayout rlDiscoveryDetailsDialogComments;
    @BindView(R.id.rlCommentList)
    RelativeLayout rlCommentList;
    @BindView(R.id.llCommentList)
    InputAwareLayout llCommentList;
    @BindView(R.id.ivDialogClose)
    UIImageView ivDialogClose;
    @BindView(R.id.inputPanelFrameLayout)
    ConversationInputPanel inputPanelFrameLayout;
    private int verticalMinDistance = 10;
    int commentType = 1;
    String year;
    String termsByYear;
    List<String> yearStringList;
    List<HomeBarrageDataBean> homeBarrageDataBeans;
    private BarrageAdapter<HomeBarrageDataBean> mBarrageAdapter;
    GoodView goodView;
    String pictureUrl;
    List<String> termsByYearList;
    int termsByYearIndex = 0;
    String imgName;
    int picId;
    GestureDetector gestureDetector;
    YearsDialog mYearsDialog;
    int isCollection;//是否收藏 0是没有收藏 1是已经收藏
    int isThumpUp;//是否点赞  0是没有点赞  1是已经点赞
    DownloadPicturesDialog mDownloadPicturesDialog;
    int pageIndex = 0;
    List<HomeDetailsCommentsBean.DataBean> mDiscoveryDetailsCommentsBeanDataBeanList;//评论数据
    String pictureTypeId;//图片系列ID
    HomeDetailsCommentsAdapter homeDetailsCommentsAdapter;
    HomeDetailsCommentsDialog mHomeDetailsCommentsDialog;
    int commentListType;//评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
    int commentId = 0;//评论id(一级评论不需要，二级评论需要带上)
    int subCommentId = 0;//子评论id
    int replyUserId = 0;//被回复人id(二级回复需要带上)
    int itemTypeId = 0;
    int itemPageIndex = 0;
    boolean isShowComments = false;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_discovery_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        inputPanelFrameLayout.init(this, llCommentList);
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.TOKEN, "");
        llCommentList.addOnKeyboardShownListener(this);
        inputPanelFrameLayout.setOnConversationInputPanelStateChangeListener(this);
        mHomeDetailsCommentsDialog = new HomeDetailsCommentsDialog(this, this);
        //隐藏评论列表布局
        llCommentList.setVisibility(View.GONE);
        //显示头部布局和设置颜色
        tvTopTitle.setVisibility(View.VISIBLE);
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        //弹幕数据集合
        homeBarrageDataBeans = new ArrayList<>();
        //期数数据集合
        termsByYearList = new ArrayList<>();
        //初始化点赞加1的动画view
        goodView = new GoodView(this);
        //下载图片弹窗,没有做任何的处理，只展示
        mDownloadPicturesDialog = new DownloadPicturesDialog(this);
        //开始 初始化控件  评论数据列表
        //评论列表数据集合
        mDiscoveryDetailsCommentsBeanDataBeanList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeDetailsCommentsAdapter = new HomeDetailsCommentsAdapter
                (getApplication(), R.layout.adapter_discovery_details_comments_item,
                        mDiscoveryDetailsCommentsBeanDataBeanList, this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        homeDetailsCommentsAdapter.setEmptyView(R.layout.adapter_discovery_details_comments_item_no_date, mSmartRefreshLayout);
        mRecyclerView.setAdapter(homeDetailsCommentsAdapter);
        homeDetailsCommentsAdapter.setOnItemChildClickListener(this);
        //结束 初始化控件  评论数据列表=
        //图片id用于第一次查询
        picId = getIntent().getIntExtra("picId", 0);
        //当前的类型 1是首页2发现
        commentType = getIntent().getIntExtra("type", 1);
        //年份数据集合
        yearStringList = getIntent().getStringArrayListExtra("yearString");
        ivTopRight.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_record));
        //隐藏顶部右边图片
        ivTopRight.setVisibility(View.VISIBLE);
        GlideUtil.loadNormalImageSV(ivTopReturn, R.drawable.activity_return_while_img);
        //滑动监听初始化
        gestureDetector = new GestureDetector(this, this);
        if (yearStringList.size() > 0) {
            year = yearStringList.get(0);
        }
        //年份和期数的弹窗
        mYearsDialog = new YearsDialog(this, yearStringList, this);
        //请求数据
        request();
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
            }
        });

    }



    /**
     * 接口请求
     */
    private void request() {
        getPictureDetail();
    }


    /**
     * 初始化弹幕
     */
    private void initBarrage() {
        BarrageView.Options options = new BarrageView.Options()
                .setGravity(BarrageView.GRAVITY_FULL)                // 设置弹幕的位置
                .setInterval(80)                                     // 设置弹幕的发送间隔
                .setSpeed(200, 29)                   // 设置速度和波动值
                .setModel(BarrageView.MODEL_RANDOM)     // 设置弹幕生成模式
                .setRepeat(-1)                                       // 循环播放 默认为1次 -1 为无限循环
                .setClick(false);                                    // 设置弹幕是否可以点击
        mBarrageView.setOptions(options);
        // 设置适配器 第一个参数是点击事件的监听器
        mBarrageView.setAdapter(mBarrageAdapter = new BarrageAdapter<HomeBarrageDataBean>(null, getApplication()) {
            @Override
            public BarrageViewHolder<HomeBarrageDataBean> onCreateViewHolder(View root, int type) {
                return new ViewHolder(root);
            }

            @Override
            public int getItemLayout(HomeBarrageDataBean barrageData) {
                return R.layout.barrage_item_big;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBarrageView != null) {
            mBarrageView.destroy();
        }

        if (mDownloadPicturesDialog != null) mDownloadPicturesDialog.dismiss();
        if (mYearsDialog != null) mYearsDialog.dismiss();

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (isShowComments) {
            return true;
        }
        if (e1.getX() - e2.getX() > verticalMinDistance) {

            if (termsByYearIndex > 0 && termsByYearList.size() - 1 >= termsByYearIndex) {
                termsByYearIndex = termsByYearIndex - 1;
                termsByYear = termsByYearList.get(termsByYearIndex);
                updateData();

            }
        } else if (e2.getX() - e1.getX() > verticalMinDistance) {

            if (termsByYearIndex >= 0 && termsByYearList.size() - 1 >= termsByYearIndex) {
                termsByYearIndex = termsByYearIndex + 1;
                termsByYear = termsByYearList.get(termsByYearIndex);
                updateData();
            }


        }
        return true;
    }


    @Override
    public void onYearsSelect(String year) {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(year)) {
            map.put("year", year);
            mPresenter.getTermsByYear(map);
        }
    }

    @Override
    public void onYearItemSelect(String year, String itemYear) {
        this.year = year;
        if (mYearsDialog != null && mYearsDialog.isShowing()) {
            mYearsDialog.dismiss();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("pictureTypeId", pictureTypeId);
        map.put("terms", itemYear);
        map.put("commentType", 1);
        map.put("token", token);
        mPresenter.getPictureDetail(map);
    }


    @OnClick({R.id.ivTopReturn, R.id.rlTop, R.id.ivDialogClose,
            R.id.llCommentListOne, R.id.tvDiscoveryDetailsCommentsTitle,
            R.id.rlDialogClose, R.id.llDiscoveryDetailsCommentsTitle,
            R.id.mRecyclerView, R.id.mSmartRefreshLayout,
            R.id.etDiscoveryDetailsCommentsDialog, R.id.rlDiscoveryDetailsDialogComments,
            R.id.rlCommentList, R.id.llCommentList
            , R.id.ivTopRight, R.id.ivDownloadPictures, R.id.ivComments, R.id.tComments, R.id.llComments, R.id.ivCollection, R.id.tvCollection, R.id.llCollection, R.id.ivGiveLike, R.id.tvGiveLike, R.id.llGiveLike, R.id.ivShare, R.id.llShare, R.id.tvImgTitleName, R.id.rlImgTitleName, R.id.etComments, R.id.ivOpenBarrage, R.id.ivCloseBarrage, R.id.rlCommentsBg, R.id.rlSendComments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etComments:
            case R.id.rlCommentsBg:
            case R.id.ivComments:
            case R.id.tComments:
            case R.id.llComments:
                this.subCommentId = 0;
                this.commentId = 0;
                commentListType = 1;
                replyUserId = 0;
                showCommentsList();
                break;
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.rlTop:
                if (mYearsDialog != null) {
                    mYearsDialog.showViewBottom(rlTop);
                }
                break;
            case R.id.ivTopRight:
                toOtherActivity(LotteryRecordActivity.class);
                break;
            case R.id.ivDownloadPictures:
                if (mDownloadPicturesDialog != null && !mDownloadPicturesDialog.isShowing()) {
                    mDownloadPicturesDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            savePicture();
                        }
                    }, 2000);   //5秒

                }
                break;

            case R.id.ivCollection:
            case R.id.tvCollection:
            case R.id.llCollection:
                onCollectionClick();
                break;
            case R.id.ivGiveLike:
            case R.id.tvGiveLike:
            case R.id.llGiveLike:
                onGiveLike(picId, "", 1);
                break;
            case R.id.ivShare:
            case R.id.llShare:
                mPresenter.externalStorage();

                break;
            case R.id.ivOpenBarrage:
                mBarrageView.setVisibility(View.VISIBLE);
                ivOpenBarrage.setVisibility(View.GONE);
                ivCloseBarrage.setVisibility(View.VISIBLE);
                break;
            case R.id.ivCloseBarrage:
                mBarrageView.setVisibility(View.GONE);
                ivOpenBarrage.setVisibility(View.VISIBLE);
                ivCloseBarrage.setVisibility(View.GONE);
                break;

            case R.id.rlSendComments:
                break;
            case R.id.rlDialogClose:
            case R.id.llCommentListOne:
            case R.id.ivDialogClose:
                replyUserId = 0;
                hideCommentsList();
                break;
            case R.id.tvDiscoveryDetailsCommentsTitle:
                break;

            case R.id.llDiscoveryDetailsCommentsTitle:
                break;
            case R.id.etDiscoveryDetailsCommentsDialog:
                break;
            case R.id.rlDiscoveryDetailsDialogComments:
                break;
        }
    }


    /**
     * 底部弹出动画显示评论列表
     */
    private void showCommentsList() {
        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(400l);     //设置动画的过渡时间
        llCommentList.postDelayed(new Runnable() {
            @Override
            public void run() {
                llCommentList.setVisibility(View.VISIBLE);
                llCommentList.startAnimation(ctrlAnimation);
                isShowComments = true;
                showSoftInputFromWindow("");
            }
        }, 500);
    }


    /**
     * 顶部消失至底部动画
     */
    private void hideCommentsList() {

        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(400);
        llCommentList.clearAnimation();
        llCommentList.setAnimation(mHiddenAction);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llCommentList.setVisibility(View.GONE);
                isShowComments = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    /**
     * @param id     目标id
     * @param userId 被点赞人id
     * @param type   类型(1-首页，2-发现，3-评论，4-子评论)
     */
    private void onGiveLike(int id, String userId, int type) {
        if (!isLoginStatus()) {
            return;
        } else {
            if (isThumpUp == 0) {

                goodView.setText("+1");
                goodView.show(tvGiveLike);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            if (!TextUtils.isEmpty(userId)) {
                map.put("userId", userId);
            }
            map.put("type", type);
            map.put("token", token);
            mPresenter.getLike(map);
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
                mPresenter.getCancelCollection(map);
            }
        } else {
            if (!TextUtils.isEmpty(String.valueOf(picId))) {
                map.put("tmpId", picId);
                map.put("type", 1);
                map.put("relatedId", pictureTypeId);
                map.put("token", token);
                mPresenter.getAddCollection(map);
            }
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
            map.put("commentType", commentType);
            mPresenter.getDeleteComment(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsCopy(String stringText) {
        //进行复制操作
        copyString(stringText);
    }

//    @Override
//    public void onDiscoveryDetailsCommentsItemClickListener(
//            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos
//            , int commentId) {
//        this.commentId = commentId;
//        this.subCommentId = subCommentDetailDos.getSubCommentId();
//        commentListType = 4;
//
//        //二级评论点击事件
//        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
//        if (subCommentDetailDos != null) {
//
//            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanXX
//                    userDO = subCommentDetailDos.getUserDO();
//            HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean
//                    replyUserDO = subCommentDetailDos.getReplyUserDO();
//
//            boolean isFalse = false;
//            if (userDO != null) {
//                replyUserId = userDO.getId();
//                if (userId.equals(String.valueOf(userDO.getId()))) {
//                    isFalse = true;
//                }
//            }
//            if (replyUserDO != null) {
//                if (userId.equals(String.valueOf(replyUserDO.getId()))) {
//                    showSoftInputFromWindow("");
//                } else {
//                    if (isFalse) {
//                        showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
//                                getSubCommentContent(), commentId, subCommentDetailDos.getSubCommentId());
//                    } else {
//                        showSoftInputFromWindow("");
//                    }
//                }
//            } else {
//                if (isFalse) {
//                    showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
//                            getSubCommentContent(), commentId, subCommentDetailDos.getSubCommentId());
//                } else {
//                    showSoftInputFromWindow("");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onDiscoveryDetailsCommentsClickListener(HomeDetailsCommentsBean.DataBean dataBean) {
//        if (dataBean != null) {
//            this.commentId = dataBean.getCommentId();
//            this.subCommentId = 0;
//            commentListType = 3;
//            if (dataBean.getUserDO() != null) {
//                replyUserId = dataBean.getUserDO().getId();
//                if (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "")
//                        .equals(String.valueOf(dataBean.getUserDO().getId()))) {
//                    inputPanelFrameLayout.onKeyboardHidden();
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), commentId, commentListType);
//                        }
//                    }, 800);   //5秒
//                } else {
//                    showSoftInputFromWindow("");
//                }
//            } else {
//                showSoftInputFromWindow("");
//            }
//        }
//    }
//
//    @Override
//    public void onAddCommentsClickListener(int itemTypeId) {
//        if (itemTypeId != this.itemTypeId) {
//            itemPageIndex = 0;
//        } else {
//            itemPageIndex = itemPageIndex + 1;
//        }
//        this.itemTypeId = itemTypeId;
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", token);
//        if (itemTypeId != 0) {
//            map.put("id", itemTypeId);
//            map.put("commentType", commentType);
//            map.put("pageIndex", itemPageIndex);
//            map.put("pageSize", 15);
//            map.put("type", 4);
//            map.put("token", token);
//            mPresenter.getSubCommentDetail(map);
//        }
//    }
//
//    @Override
//    public void onDiscoveryDetailsCommentsGiveLike(HomeDetailsCommentsBean.DataBean dataBean) {
//        if (isLoginStatus()) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("token", token);
//            map.put("id", dataBean.getCommentId());
//            map.put("userId", dataBean.getCommentUserId());
//            map.put("type", 3);
//            mPresenter.getLike(map);
//        }
//    }
//
//    @Override
//    public void onDiscoveryDetailsCommentsGiveNoLike(HomeDetailsCommentsBean.DataBean dataBean) {
//
//    }
//
//    @Override
//    public void onDiscoveryDetailsCommentsReply(HomeDetailsCommentsBean.DataBean dataBean) {
//
//    }


    private void showDiscoveryDetailsCommentsDialog(String copyText, int commentId,
                                                    int subCommentId) {
        if (mHomeDetailsCommentsDialog != null &&
                !mHomeDetailsCommentsDialog.isShowing()) {
            mHomeDetailsCommentsDialog.showDiscoveryDetailsCommentsDialog(copyText, commentListType, commentId, subCommentId);
        }
    }


    @OnTouch({R.id.llCommentList, R.id.mRecyclerView, R.id.inputPanelFrameLayout})
    boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(view.getId()!=R.id.inputPanelFrameLayout){
                commentId=0;
                subCommentId=0;
                replyUserId=0;
                inputPanelFrameLayout.collapse();
            }
        }
        return false;
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(String draftString) {
        inputPanelFrameLayout.show(llCommentList,draftString);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        //评论列表点击事件
    }

    @Override
    public void onKeyboardHidden() {
        inputPanelFrameLayout.onKeyboardHidden();
    }

    @Override
    public void onKeyboardShown() {
        inputPanelFrameLayout.onKeyboardShown();
    }


    @Override
    public void onBackPressed() {
        if (llCommentList.getCurrentInput() != null) {
            llCommentList.hideAttachedInput(true);
            inputPanelFrameLayout.collapse();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onInputPanelExpanded() {

    }

    @Override
    public void onInputPanelCollapsed() {

    }

    @Override
    public void onSendText(String sendText) {
        getAddCommentRecommend(sendText);
    }





    @Override
    public void onDiscoveryDetailsCommentsItemClickListener(HomeDetailsCommentsBean.DataBean dataBean, HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition) {

    }

    @Override
    public void onDiscoveryDetailsCommentsClickListener(HomeDetailsCommentsBean.DataBean subCommentDetailDos, int position) {

    }

    @Override
    public void onAddCommentsClickListener(int itemTypeId, int position) {

    }

    @Override
    public void onDiscoveryDetailsCommentsGiveLike(HomeDetailsCommentsBean.DataBean dataBean, int position) {

    }

    @Override
    public void onDiscoveryDetailsCommentsGiveNoLike(HomeDetailsCommentsBean.DataBean dataBean, int position) {

    }

    @Override
    public void onDiscoveryDetailsCommentsReply(HomeDetailsCommentsBean.DataBean dataBean, int position) {

    }

    @Override
    public void onDiscoveryUserHisInformation(int userID) {

    }


    class ViewHolder extends BarrageAdapter.BarrageViewHolder<HomeBarrageDataBean> {

        private ImageView mHeadView;
        private TextView mContent;
        private TextView tvUserName;

        ViewHolder(View itemView) {
            super(itemView);

            mHeadView = itemView.findViewById(R.id.image);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(HomeBarrageDataBean data) {
            if (data.getUserDO() != null) {
                GlideUtil.loadCircleImage(mHeadView, data.getUserDO().getHeadImg(), R.drawable.mine_user_normal_img);
                tvUserName.setText(data.getUserDO().getNikeName());
            }
            mContent.setText(data.getCommentContent());
        }
    }


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
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.finishRefresh();
        }
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
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


    @Override
    public void getPictureDetail(HomeDetailsBean homeDetailsBean) {
        if (homeDetailsBean != null) {
            if (homeDetailsBean.getData() != null) {
                //得到实体对象
                HomeDetailsBean.DataBean dataBea = homeDetailsBean.getData();
                //图片id
                picId = dataBea.getPicId();
                pictureUrl = dataBea.getFilePath();
                //图片系列ID
                pictureTypeId = String.valueOf(dataBea.getPictureTypeId());
                getTermsByYear();
                //变量接受图片名字（后面接口有需要，通过图片名字查询相应的类型）
                imgName = dataBea.getPicName();
                //是否收藏过
                isCollection = dataBea.getIsCollection();
                //是否点赞过
                isThumpUp = dataBea.getIsThumpUp();
                //设置图片名字
                tvImgTitleName.setText(imgName);
                //设置点赞数量
                tvGiveLike.setText(dataBea.getSupportCount() + "");
                //设置收藏数量
                tvCollection.setText(dataBea.getCollectionCount() + "");
                //设置评论数量
                tComments.setText(dataBea.getPicComment() + "");
                tvDiscoveryDetailsCommentsTitle.setText(dataBea.getPicComment() + "");
                //设置年份和期数
                tvTopTitle.setText(dataBea.getYear() + " "
                        + dataBea.getTerms() + "期");
                //设置可以缩放的图片
                RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
                Glide.with(HomeDetailsActivity.this).load(pictureUrl).apply(options).into(ivHDImageView);
                tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
                //清楚弹幕数据
                //初始化弹幕
                homeBarrageDataBeans.clear();
                if (mBarrageAdapter != null) {
                    mBarrageAdapter.destroy();
                    mBarrageAdapter = null;
                }

                initBarrage();
                if(dataBea.getCommentDetailDo()!=null){
                    for (HomeDetailsBean.DataBean.CommentDetailDoBean commentDetailDoBean :
                            dataBea.getCommentDetailDo()) {
                        HomeBarrageDataBean dataBean = new HomeBarrageDataBean();
                        dataBean.setCommentContent(commentDetailDoBean.getCommentContent());
                        dataBean.setUserDO(commentDetailDoBean.getUserDO());
                        homeBarrageDataBeans.add(dataBean);
                    }
                    if (mBarrageView != null) {
                        if (homeBarrageDataBeans.size() == 0) {
                            mBarrageView.setVisibility(View.GONE);
                        } else {
                            mBarrageView.setVisibility(View.VISIBLE);
                        }
                    }
                    //添加弹幕数据
                    mBarrageAdapter.addList(homeBarrageDataBeans);
                }
                pageIndex = 0;
                requestCommentDetail();
            }
        }
    }



    @Override
    public void getTermsByYear(List<String> stringList) {
        termsByYearList.clear();
        termsByYearList.addAll(stringList);
        if (mYearsDialog != null) {
            mYearsDialog.setItemListData(termsByYearList);
        }
    }

    @Override
    public void onCommentDetail(HomeDetailsCommentsBean homeDetailsCommentsBean) {
        // 得到评论记录
        if (pageIndex == 0) {
            mDiscoveryDetailsCommentsBeanDataBeanList.clear();
        }
        if (homeDetailsCommentsBean != null && homeDetailsCommentsBean.getData() != null &&
                homeDetailsCommentsBean.getCondition().getPageInfo().getPages() > pageIndex) {
            mDiscoveryDetailsCommentsBeanDataBeanList.addAll(homeDetailsCommentsBean.getData());
        }


        if (homeDetailsCommentsAdapter != null) {
            if(pageIndex==0){
                mRecyclerView.scrollToPosition(0);
            }
            homeDetailsCommentsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDeleteComment(BaseResponse baseResponse) {
        //删除评了回调
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                getPictureDetail();
                pageIndex = 0;
                requestCommentDetail();
            }
        }
    }

    @Override
    public void onLike(BaseResponse baseResponse) {
        // 点赞
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                getPictureDetail();
            }
        }
    }

    @Override
    public void onAddCollection(BaseResponse baseResponse) {
        //添加收藏
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                getPictureDetail();
            }
        }
    }

    @Override
    public void onCancelCollection(BaseResponse baseResponse) {
        //取消收藏
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                getPictureDetail();
            }
        }
    }

    @Override
    public void onAddCommentRecommend(BaseResponse baseResponse) {
        //添加评论回调
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                getPictureDetail();
                pageIndex = 0;
                requestCommentDetail();
            }
        }
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
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDoShare() {
        ShareUtils.doShare(this, pictureUrl,true);
    }


    /**
     * 首页第一次刷新数据
     */
    private void getPictureDetail() {
        Map<String, Object> map = new HashMap<>();
        if (picId != 0) {
            map.put("id", picId);
        }
        map.put("commentType", commentType);
        map.put("token", token);
        mPresenter.getPictureDetail(map);
    }


    /**
     * 得到期数列表
     */
    private void getTermsByYear() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(pictureTypeId)) {
            map.put("year", year);
            map.put("pictureTypeId", pictureTypeId);
            mPresenter.getTermsByYear(map);
        }

    }


    @Override
    protected void requestData() {

    }


    /**
     * 保存图片
     */
    public void savePicture() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                e.onNext(Glide.with(HomeDetailsActivity.this)
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
                        FileUtil.copyFile(file, destFile, destFile.getPath(), fileName, HomeDetailsActivity.this);

                        showMessage("图片成功保存至" + destFile.getPath());
                        if (mDownloadPicturesDialog != null) {
                            mDownloadPicturesDialog.dismiss();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showMessage("文件下载失败，请稍后再试.");
                        if (mDownloadPicturesDialog != null) {
                            mDownloadPicturesDialog.dismiss();
                        }
                    }
                });
    }


    //请求评论数据
    private void requestCommentDetail() {
        Map<String, Object> map = new HashMap<>();
        if (picId!=0) {
            map.put("id", picId);
            map.put("commentType", commentType);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 15);
            map.put("type", 3);
            map.put("token", token);
            mPresenter.getCommentDetail(map);
        }

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
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.copy_text_successfully));
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
        map.put("type", commentListType);
        if (commentId != 0) {
            map.put("commentId", commentId);
        }
        if (subCommentId != 0) {
            map.put("subCommentId", subCommentId);
        }
        if (replyUserId != 0) {
            map.put("replyUserId", replyUserId);
        }
        map.put("commentContent", commentContent);
        map.put("commentType", commentType);
        map.put("type", commentListType);
        map.put("token", token);
        mPresenter.getAddCommentRecommend(map);
    }

}
