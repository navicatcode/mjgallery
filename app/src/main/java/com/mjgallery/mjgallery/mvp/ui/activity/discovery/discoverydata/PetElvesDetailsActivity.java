package com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lihang.ShadowLayout;
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
import com.mjgallery.mjgallery.di.component.DaggerPetElvesDetailsComponent;
import com.mjgallery.mjgallery.event.SelectApplicableEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.PetElvesDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata.PetElvesDetailsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.SelectApplicableActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
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
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.Callback;
import com.zzhoujay.richtext.callback.SimpleImageFixCallback;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
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
 * Description:资料详情界面
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 08:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class PetElvesDetailsActivity extends MJBaseActivity<PetElvesDetailsPresenter> implements
        PetElvesDetailsContract.View, KeyboardAwareLinearLayout.OnKeyboardShownListener, DiscoverDetailsCommentsAdapter.IDiscoveryDetailsCommentsItemClickListener
        , DiscoveryDetailsCommentsDialog.IDiscoveryDetailsComments, KeyboardAwareLinearLayout.OnKeyboardHiddenListener, ConversationInputPanel.OnConversationInputPanelStateChangeListener {
    int id = 0;
    int userId = 0;
    int isCollection;
    //是否收藏 0是没有收藏 1是已经收藏
    int isThumpUp;
    //是否点赞  0是没有点赞  1是已经点赞
    GoodView goodView;
    List<DiscoveryCommentsBean.DataBean> beanList;
    DiscoverDetailsCommentsAdapter discoverDetailsCommentsAdapter;
    int commentListType = 2;//评论类型(2-发现一级评论，3-发现二级评论，4-发现二级回复)
    int commentId = 0;//评论id(一级评论不需要，二级评论需要带上)
    int subCommentId = 0;//评论子评论id
    int replyUserId = 0;//评论需要回复人id
    int concernUserId = 0;////资料发布人id
    String replyNickName = "";//评论需要回复人昵称
    String concernUserNickName = "";//资料发布人昵称
    int pageIndex = 0;
    DiscoveryDetailsCommentsDialog mDiscoveryDetailsCommentsDialog;
    int itemTypeId = 0;//一级评论的ID
    int itemPageIndex = 0;//二级评论分页数据当前的下标
    boolean isConcern = false;//是否关注了
    String year;
    String period;
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
    @BindView(R.id.mWebView)
    WebView mWebView;
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
    @BindView(R.id.mInputAwareLayout)
    InputAwareLayout mInputAwareLayout;


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
    LinearLayoutManager mLinearLayoutManager;
    List<String> imgPathList;  //banner图集合,验证会不会出现类型转换异常
    List<String> urlList;     // banner条点击地址,同上的处理
    @BindView(R.id.tvPetElvesDetailsHTML)
    TextView tvPetElvesDetailsHTML;
    boolean isEventBus = false;
    @BindView(R.id.llTopReturnAll)
    RelativeLayout llTopReturnAll;
    @BindView(R.id.rlPictureDetailsTop)
    RelativeLayout rlPictureDetailsTop;
    @BindView(R.id.tvPictureDetailsTop)
    TextView tvPictureDetailsTop;
    @BindView(R.id.tvPetElvesDetailsViews)
    TextView tvPetElvesDetailsViews;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPetElvesDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pet_elves_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void onBackPressed() {
        if (mInputAwareLayout.getCurrentInput() != null) {
            mInputAwareLayout.hideAttachedInput(true);
            inputPanelFrameLayout.collapse();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isEventBus = false;
        initView();
        initWebView();
        initListener();
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!isEventBus) {
            if (intent != null) {
                id = intent.getIntExtra("id", 0);
            }
            Calendar calendar = Calendar.getInstance();//
            year = String.valueOf(calendar.get(Calendar.YEAR));
            getDiscoveryDetail();
            Map<String, Object> map = new HashMap<>();
            map.put("year", year);
            mPresenter.getSelectByYery(map);//根据年份请求开奖记录
            mPresenter.getAdvert(5);//请求广告
        }
    }

    /**
     * 初始化控件和集合
     */
    private void initView() {
        inputPanelFrameLayout.init(this, mInputAwareLayout);
        goodView = new GoodView(this);
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        ivRightHomeSearch.setVisibility(View.VISIBLE);
        GlideUtil.loadNormalImageSV(ivRightHomeSearch, R.drawable.home_fengxiang);
        beanList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(getApplication());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDiscoveryDetailsCommentsDialog = new DiscoveryDetailsCommentsDialog(this, this);
        discoverDetailsCommentsAdapter = new DiscoverDetailsCommentsAdapter(getApplication(),
                R.layout.adapter_discovery_details_comments_item,
                beanList, this, true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //下拉和上拉刷新加载评论数据
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getApplication()));
        mRecyclerView.setAdapter(discoverDetailsCommentsAdapter);
        discoverDetailsCommentsAdapter.setEmptyView(R.layout.adapter_discovery_details_comments_item_no_date, mSmartRefreshLayout);
        urlList = new ArrayList<>();
        imgPathList = new ArrayList<>();
    }


    /**
     * 初始化事件监听
     */
    private void initListener() {
        mInputAwareLayout.addOnKeyboardShownListener(this);
        mInputAwareLayout.addOnKeyboardHiddenListener(this);
        inputPanelFrameLayout.setOnConversationInputPanelStateChangeListener(this);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ViewGroup.LayoutParams params = mWebView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels;
                params.height = mWebView.getHeight() - mNestedScrollView.getHeight();
                mWebView.setLayoutParams(params);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受所有网站的证书
            }
        });
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
                getDiscoveryDetail();
            }
        });
    }


    /**
     * 初始化webView设置
     */
    private void initWebView() {
        // 初始化设置
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(false);//开启javascript
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setBuiltInZoomControls(true);// 支持缩放
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        mSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        mSettings.setAppCacheEnabled(true);//开启缓存机制
        mSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mSettings.setTextSize(WebSettings.TextSize.LARGEST);
        tvPetElvesDetailsHTML.setMovementMethod(LinkMovementMethod.getInstance());  //其实就这一句是关键
        /*解决图片不显示*/
        mSettings.setBlockNetworkImage(false);//不阻塞网络图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //允许混合（http，https）
            //websettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
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


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
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
        dismissLoadingAnimationDialog();
        finish();
    }


    @OnClick({R.id.ivTopReturn, R.id.llPictureDetailsNper, R.id.tvPictureDetailsNper, R.id.tvPetElvesDetailsFocusOn,
            R.id.ivPetElvesDetailsShare, R.id.ivPetElvesDetailsGiveLike, R.id.tvPetElvesDetailsGiveLike, R.id.ivPetElvesDetailsCollection,
            R.id.tvPetElvesDetailsCollection, R.id.ivPetElvesDetailsComments, R.id.tvPetElvesDetailsComments,R.id.ivRightHomeSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivRightHomeSearch:
                mPresenter.externalStorage(true);
                break;
            case R.id.llPictureDetailsNper:
            case R.id.tvPictureDetailsNper:
                animRotate(ivPictureDetailsNper, false);
                break;
            case R.id.tvPetElvesDetailsFocusOn:
                onPetElvesDetailsFocusOnClick();
                break;
            case R.id.ivPetElvesDetailsGiveLike:
            case R.id.tvPetElvesDetailsGiveLike:
                isClickGiveLikeImageDetails = true;
                onGiveLike(tvPetElvesDetailsGiveLike);
                break;
            case R.id.ivPetElvesDetailsCollection:
            case R.id.tvPetElvesDetailsCollection:
                onCollectionClick();
                break;

        }
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


    @Subscriber(mode = ThreadMode.MAIN)
    public void onSelectApplicableEvent(SelectApplicableEvent selectApplicableEvent) {
        isEventBus = true;
        animRotate(ivPictureDetailsNper, true);
        String years = selectApplicableEvent.getYear();
        String termsByYears = selectApplicableEvent.getTermsByYear();
        if (!TextUtils.isEmpty(years) && !TextUtils.isEmpty(termsByYears)) {
            year = years;
            period = termsByYears;
            requestLotteryRecordData();
        }

    }

    /**
     * 添加关注和取消关注
     */
    private void onPetElvesDetailsFocusOnClick() {
        if (isLoginStatus()) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            //判断当前是否已经关注来了，没有关注就添加关注，关注了取消关注
            if (isConcern) {
                map.put("id", concernUserId);
                //发送取消关注的请求到后台
                mPresenter.getCancelUser(map);
            } else {
                map.put("userId", concernUserId);
                //发送添加关注的请求到后台
                mPresenter.getConcernUser(map);
            }
        }
    }

    /**
     * 收藏点击事件
     */
    private void onCollectionClick() {
        if (!isLoginStatus()) {
            return;
        }
        //TODO subType", value = "发现的type，0-图片，1-视频，2-资料
        Map<String, Object> map = new HashMap<>();
        //根据收藏状态判断当前是取消还是添加收藏（如果是1就是取消收藏）
        if (isCollection != 0) {
            if (!TextUtils.isEmpty(String.valueOf(id))) {
                map.put("picId", id);
                map.put("type", 2);
                map.put("token", token);
                //发送取消收藏的请求到后台
                mPresenter.getCancelCollection(map);
            }
        } else {
            if (!TextUtils.isEmpty(String.valueOf(id))) {
                map.put("tmpId", id);
                map.put("type", 2);
                map.put("subType", 2);
                map.put("token", token);
                //发送添加收藏的请求到后台
                mPresenter.getAddCollection(map);
            }
        }
    }


    /**
     * 资料详情的点赞的点击事件
     *
     * @param view
     */
    private void onGiveLike(View view) {
        if (!isLoginStatus()) {//判断是否登陆了,根据token进行判断
            return;
        } else {
            //如果用户没有点赞，添加加1的动画
            if (isThumpUp == 0) {
                goodView.setText("+1");
                goodView.show(view);
            }
            Map<String, Object> map = new HashMap<>();
            //如果当前的状态是点赞，那就取消点赞，如果没有点赞，那就执行设置为点赞状态给后台
            isThumpUp = (isThumpUp != 0) ? 0 : 1;
            map.put("id", id);
            if (userId != 0) {
                map.put("userId", userId);
            }
            map.put("type", 2);
            map.put("status", isThumpUp);
            map.put("token", token);
            map.put("relatedType", 4);//资料
            map.put("relatedId",getIntent().getIntExtra("id", 0));
            //发送点赞的请求到后台
            mPresenter.getThumbUpDiscovery(map);
        }
    }


    @Override
    public void onThumbUpDiscovery(BaseResponse baseResponse) {
        //点赞回调
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

    /**
     * 刷新单条评论数据
     *
     * @param dataBean
     */
    private void updateDiscoveryDetailsCommentsAdapter(DiscoveryCommentsBean.DataBean dataBean) {
        if (beanList.size() > itemIndex) {
            beanList.set(itemIndex, dataBean);
        }
        if (discoverDetailsCommentsAdapter != null) {
            discoverDetailsCommentsAdapter.notifyDataSetChanged();
        }
    }


    /***
     * 请求评论数据
     */
    private void requestCommentDetail() {
        Map<String, Object> map = new HashMap<>();
        if (id != 0) {
            map.put("id", id);
            map.put("commentType", 2);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 15);
            map.put("type", 3);
            map.put("token", token);
            //发送请求评论数据的请求到后台
            mPresenter.getCommentDetail(map);
        }

    }


    @Override
    public void onCommentDetail(DiscoveryCommentsBean homeDetailsCommentsBean) {
        //评论数据回调
        if (homeDetailsCommentsBean != null) {
            if (pageIndex == 0) {
                beanList.clear();
            }
            if ( homeDetailsCommentsBean.getData() != null) {
                beanList.addAll(homeDetailsCommentsBean.getData());
            }
            if (discoverDetailsCommentsAdapter != null) {
                discoverDetailsCommentsAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onAddCollection(BaseResponse baseResponse) {
        //添加收藏回调
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
        //取消收藏回调
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
        // 取消关注人接口回调
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
        //添加关注人接口回调
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                isConcern = !isConcern;
                updateConcernStatus();
            }
            showMessage(baseResponse.getMessage());
        }
    }


    @Override
    public void onAddCommentDiscovery(BaseResponse<DiscoveryCommentsBean.DataBean> dataBeanBaseResponse) {
        //关闭键盘
        inputPanelFrameLayout.collapse();
        //发送评论回调
        if (dataBeanBaseResponse != null) {
            DiscoveryCommentsBean.DataBean discoveryCommentsBeanData = dataBeanBaseResponse.getResult();
            if (dataBeanBaseResponse.getCode() == 0 && discoveryCommentsBeanData != null) {
                //评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
                switch (commentListType) {
                    case 2://资料一级评论
                        //把当前的评论数据添加到第一条
                        beanList.add(0, discoveryCommentsBeanData);
                        picComment = picComment + 1;
                        //刷新图片详情的评论数量
                        updateThumpUpAndCollectionCount();
                        //移动到评论列表的第一条数据
                        setScrollToPosition();
                        if (discoverDetailsCommentsAdapter != null) {
                            discoverDetailsCommentsAdapter.notifyDataSetChanged();
                        }
                        break;
                    //二级评论和二级评论回复
                    case 3:
                    case 4:
                        if (this.dataBean != null) {
                            if (discoveryCommentsBeanData != null &&
                                    discoveryCommentsBeanData.getSubCommentDetailDos().size() > 0) {
                                //得到二级评论的列表数据
                                DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean
                                        = discoveryCommentsBeanData.getSubCommentDetailDos().get(0);
                                //创建二级列表的集合
                                List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList
                                        = new ArrayList<>();
                                //如果当前二级评论的列表不等于空，就把当前的二级评论列表的数据添加到subCommentDetailDosBeanList
                                //并且把自己的二级评论列表清空
                                if (this.dataBean.getSubCommentDetailDos() != null && this
                                        .dataBean.getSubCommentDetailDos().size() > 0) {
                                    subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                                    this.dataBean.getSubCommentDetailDos().clear();
                                }
                                //把接口里面返回的接口数据添加subCommentDetailDosBeanList集合的第一条
                                if (subCommentDetailDosBean != null) {
                                    subCommentDetailDosBeanList.add(0, subCommentDetailDosBean);
                                }
                                //把集合里面数据添加当前的数据当中
                                this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                                if (beanList.size() > itemIndex) {
                                    beanList.set(itemIndex, this.dataBean);
                                }
                                if (discoverDetailsCommentsAdapter != null) {
                                    discoverDetailsCommentsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        break;
                }
                //设置评论初始状态
                setCommentNormal();

            }
            showMessage(dataBeanBaseResponse.getMessage());
        }
    }

    /**
     * 设置评论列表到第一个位置
     */
    private void setScrollToPosition() {
        mNestedScrollView.scrollTo(0, idAllComments.getBottom());
    }

    @Override
    public void onCircleDeleteComment(BaseResponse baseResponse) {
        //删除评论接口回调
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                switch (commentListType) {
                    case 2://  资料一级评论
                        //进行集合的个数判断，避免出现下标越界
                        if (beanList.size() > itemIndex) {
                            //移除当前下标的数据
                            beanList.remove(itemIndex);
                            picComment = picComment - 1;
                            updateThumpUpAndCollectionCount();
                            if (discoverDetailsCommentsAdapter != null) {
                                discoverDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    //资料二级评论和二级评论回复
                    case 3:
                    case 4:
                        //进行非空判断避免出现空指针
                        if (this.dataBean != null && this.dataBean.getSubCommentDetailDos() != null) {
                            // //创建二级列表的集合
                            List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                            //添加当前的数据到上一步创建的集合中
                            subCommentDetailDosBeanList.addAll(this.dataBean.getSubCommentDetailDos());
                            //进行判断，避免出现下标越界
                            if (subCommentDetailDosBeanList.size() > itemItemIndex) {
                                //移除当前下标的二级评论数据
                                subCommentDetailDosBeanList.remove(itemItemIndex);
                            }
                            //清除当前集合的数据
                            this.dataBean.getSubCommentDetailDos().clear();
                            //把临时的二级评论集合列表的数据添加到当前的数据当中
                            this.dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                            //进行判断，避免出现下标越界
                            if (beanList.size() > itemIndex) {
                                //修改当前下标的数据
                                beanList.set(itemIndex, this.dataBean);
                            }
                            if (discoverDetailsCommentsAdapter != null) {
                                discoverDetailsCommentsAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }
            showMessage(baseResponse.getMessage());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDiscoveryDetail(DiscoveryDetailBean discoveryDetailBean) {
        //资料详情数据回调
        if (discoveryDetailBean != null) {
            if (discoveryDetailBean.getData() != null) {
                DiscoveryDetailBean.DataBean discoveryDetailBeanData = discoveryDetailBean.getData();
                DiscoveryDetailBean.DataBean.ShowInfoDoBean showInfoDo = discoveryDetailBeanData.getShowInfoDo();
                if (showInfoDo != null) {
                    //设置标题
                    tvTopTitle.setText(showInfoDo.getTitle());
                    RichText.from(showInfoDo.getContent())
                            .showBorder(true)
                            .fix(new SimpleImageFixCallback() {

                                @Override
                                public void onFailure(ImageHolder holder, Exception e) {
                                    super.onFailure(holder, e);
                                    e.printStackTrace();
                                }
                            })
                            .done(new Callback() {
                                @Override
                                public void done(boolean imageLoadDone) {
                                    Log.d(TAG, "imageDownloadFinish() called with: imageLoadDone = [" + imageLoadDone + "]");
                                }
                            })
                            .into(tvPetElvesDetailsHTML);
//                    tvPetElvesDetailsHTML.setText(spanned);
                }
                DiscoveryDetailBean.DataBean.UserDOBean userDOBean = discoveryDetailBeanData.getUserDO();
                if (userDOBean != null) {
                    //资料发布人的信息
                    tvPetElvesDetailsTitle.setText(userDOBean.getNikeName());
                    concernUserId = userDOBean.getId();
                    concernUserNickName = userDOBean.getNikeName();
                    isConcern = userDOBean.isConcern();
                    replyUserId = userDOBean.getId();
                    replyNickName = concernUserNickName;
                }
                //是否点赞过
                isThumpUp = discoveryDetailBeanData.getIsThumpUp();
                //是否收藏过
                isCollection = discoveryDetailBeanData.getIsCollection();
                //是否点赞过
                isThumpUp = discoveryDetailBeanData.getIsThumpUp();
                //点赞的数量
                supportCount = discoveryDetailBeanData.getThumbUpCount();
                // 收藏的数量
                collectionCount = discoveryDetailBeanData.getCollectionCount();
                //评论的数量
                picComment = discoveryDetailBeanData.getCommentCount();
                //刷新图片详情的点赞和收藏的图片和数量
                updateThumpUpAndCollectionImgAndConcernStatus();
                if (discoveryDetailBeanData.getClickCount() > 10000) {
                    BigDecimal bigDecimalClickCount = new BigDecimal
                            (discoveryDetailBeanData.getClickCount());
                    BigDecimal bigDecimalChu = new BigDecimal(10000);
                    float clickCount = bigDecimalClickCount.divide
                            (bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                    tvPetElvesDetailsViews.setText(clickCount + ArmsUtils.getString(getApplication(),R.string.wan));
                } else {
                    tvPetElvesDetailsViews.setText(discoveryDetailBeanData.getClickCount() + "");
                }
                pageIndex = 0;
                requestCommentDetail();
            }
        }
    }

    @Override
    public void onDiscoverSubCommentDetailDosBean(DiscoverSubCommentDetailDosBean discoverSubCommentDetailDosBean) {
        if (discoverSubCommentDetailDosBean != null && discoverSubCommentDetailDosBean.getData() != null && discoverSubCommentDetailDosBean.getData().size() > 0) {
            for (int i = 0; i < beanList.size(); i++) {
                DiscoveryCommentsBean.DataBean dataBean = beanList.get(i);
                //查找到当前一级评论的下标并且在当前的评论列表中得到当前下标
                if (itemTypeId != 0 && itemTypeId == dataBean.getCommentId()) {
                    List<DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean> subCommentDetailDosBeanList = new ArrayList<>();
                    //如果是第0页数据，清除当前数据的二级评论
                    if (itemPageIndex == 0) {
                        dataBean.getSubCommentDetailDos().clear();
                    } else {
                        //如果不是第0页的数据，就把当前二级评论数据全部添加至临时集合中
                        subCommentDetailDosBeanList.addAll(dataBean.getSubCommentDetailDos());
                    }

                    //由于数据没有做统一出来，对接口返回的数据进行添加处理
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
                    //设置当前对象的二级评论数据
                    dataBean.setSubCommentDetailDos(subCommentDetailDosBeanList);
                    //退换当前下标集合数据
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
    public void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean) {
        //根据期数和日期得到开奖记录
        if (lotteryRecordDtosBean != null) {
            //根据时间戳转成当前的日前
            tvDay.setText(DateUtils.timet(String.valueOf(lotteryRecordDtosBean.getLotteryTime())));
            //对当前的期数进行小于100加0处理
            String preiodStr = (lotteryRecordDtosBean.getPeriod() < 100) ? ("0" + lotteryRecordDtosBean.getPeriod()) : ("" + lotteryRecordDtosBean.getPeriod());
            //设置年和期数
            tvPictureDetailsNper.setText(lotteryRecordDtosBean.getYear() + ArmsUtils.getString(BaseApplication.getInstance(),
                    R.string.year) + preiodStr + ArmsUtils.getString(BaseApplication.getInstance(), R.string.period));
            //设置期数
            tvQishu.setText(preiodStr + ArmsUtils.getString(BaseApplication.getInstance(), R.string.period));
            if (lotteryRecordDtosBean.getSx() != null && lotteryRecordDtosBean.getWx() != null && lotteryRecordDtosBean.getNumbers() != null) {
                setRecord(lotteryRecordDtosBean.getSx(), lotteryRecordDtosBean.getWx(), lotteryRecordDtosBean.getNumbers());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 重写图片加载接口
     *
     * @author Ruffian
     * @date 2016年1月15日
     */
    class HtmlImageGetter implements Html.ImageGetter {

        /**
         * 获取图片
         */
        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            Drawable empty = getResources().getDrawable(
                    R.drawable.icon_loading);
            d.addLevel(0, 0, empty);
            d.setBounds(0, 0, ArmsUtils.getScreenWidth(BaseApplication.getInstance()),
                    empty.getIntrinsicHeight());
            new LoadImage().execute(source, d);
            return d;
        }

        /**
         * 异步下载图片类
         *
         * @author Ruffian
         * @date 2016年1月15日
         */
        class LoadImage extends AsyncTask<Object, Void, Bitmap> {

            private LevelListDrawable mDrawable;

            @Override
            protected Bitmap doInBackground(Object... params) {
                String source = (String) params[0];
                mDrawable = (LevelListDrawable) params[1];
                try {
                    InputStream is = new URL(source).openStream();
                    return BitmapFactory.decodeStream(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 图片下载完成后执行
             */
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    BitmapDrawable d = new BitmapDrawable(bitmap);
                    mDrawable.addLevel(1, 1, d);
                    /**
                     * 适配图片大小 <br/>
                     * 默认大小：bitmap.getWidth(), bitmap.getHeight()<br/>
                     * 适配屏幕：getDrawableAdapter
                     */
                    mDrawable = getDrawableAdapter(getApplication(), mDrawable,
                            bitmap.getWidth(), bitmap.getHeight());

                    // mDrawable.setBounds(0, 0, bitmap.getWidth(),
                    // bitmap.getHeight());

                    mDrawable.setLevel(1);

                    /**
                     * 图片下载完成之后重新赋值textView<br/>
                     * mtvActNewsContent:我项目中使用的textView
                     *
                     */
                    if (tvPetElvesDetailsHTML != null) {
                        tvPetElvesDetailsHTML.invalidate();
                        CharSequence t = tvPetElvesDetailsHTML.getText();
                        tvPetElvesDetailsHTML.setText(t);
                    }
                }
            }

            /**
             * 加载网络图片,适配大小
             *
             * @param context
             * @param drawable
             * @param oldWidth
             * @param oldHeight
             * @return
             * @author Ruffian
             * @date 2016年1月15日
             */
            public LevelListDrawable getDrawableAdapter(Context context,
                                                        LevelListDrawable drawable, int oldWidth, int oldHeight) {
                LevelListDrawable newDrawable = drawable;
                long newHeight = 0;// 未知数
                int newWidth = ArmsUtils.getScreenWidth(BaseApplication.getInstance());// 默认屏幕宽
                newHeight = (newWidth * oldHeight) / oldWidth;
                // LogUtils.w("oldWidth:" + oldWidth + "oldHeight:" +
                // oldHeight);
                // LogUtils.w("newHeight:" + newHeight + "newWidth:" +
                // newWidth);
                newDrawable.setBounds(0, 0, newWidth, (int) newHeight);
                return newDrawable;
            }
        }

    }


    /**
     * 设置开奖   设置球号和球颜色
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
        //根据年份得到开奖期数的回调
        if (stringList != null && stringList.size() > 0) {
            //取集合里面的第一条数据
            period = stringList.get(0);
            //根据年份和期数得到当前的开奖记录
            requestLotteryRecordData();
        }
    }

    @Override
    public void onStepOn(BaseResponse baseResponse) {
        //点踩的数据回调
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                //如果是点踩，点踩的数据加1，如果是取消踩的话，数据减1
                itemStepOnCount = (!isItemStepOn) ? itemStepOnCount - 1 : itemStepOnCount + 1;
                //进行数量的0的数据处理，避免出现负数
                if (itemStepOnCount < 0)
                    itemStepOnCount = 0;
                //设置当前数据的点踩的状态
                this.dataBean.setStepOn(isItemStepOn);
                //设置当前数据的点踩的状数量
                this.dataBean.setStepOnCount(itemStepOnCount);
                //刷新单条评论数据
                updateDiscoveryDetailsCommentsAdapter(this.dataBean);
            }
            showMessage(baseResponse.getMessage());
        }
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
     * 根据年份和期数得到当前的开奖记录
     */
    private void requestLotteryRecordData() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(period) && !TextUtils.isEmpty(year)) {
            map.put("year", year);
            map.put("period", period);
            //发送年份和期数的请求到后台
            mPresenter.getLotteryRecordDtosBean(map);
        }
    }


    /**
     * 获取资料详情数据
     */
    private void getDiscoveryDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", 15);
        map.put("pageIndex", "0");
        map.put("token", token);
        if (id != 0) {
            map.put("id", id);
            map.put("type", 3);
            //发送资料详情数据的请求到后台
            mPresenter.getDiscoveryDetail(map);
        }

    }

    @Override
    protected void requestData() {

    }


    @Override
    public void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean dataBean, DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition) {
        //二级评论点击
        //一级评论ID
        this.commentId = commentId;
        //二级评论ID
        this.subCommentId = subCommentDetailDos.getSubCommentId();
        commentListType = 4;
        this.dataBean = dataBean;
        //当前点击一级评论的下标
        this.itemIndex = position;
        //当前点击二级评论的下标
        this.itemItemIndex = itemPosition;
        //获取登陆时候的userid
        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        if (subCommentDetailDos != null) {
            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanX
                    userDO = subCommentDetailDos.getUserDO();
            DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean
                    replyUserDO = subCommentDetailDos.getReplyUserDO();
            boolean isFalse = false;//判断二级评论或者回复是不是自己发的
            if (userDO != null) {
                //被回复人id
                replyUserId = userDO.getId();
                //被回复人昵称
                replyNickName = userDO.getNikeName();
                //判断二级评论是不是自己发的
                if (userId.equals(String.valueOf(userDO.getId()))) {
                    //当前二级评论是自己的发的
                    isFalse = true;
                }
            }
            //判断被回复人是否等于空
            if (replyUserDO != null) {
                if (userId.equals(String.valueOf(replyUserDO.getId()))) {
                    //调用软件盘进行回复
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                } else {
                    if (isFalse) {
                        //当前这条评论自己发的，调用弹窗进行复制和删除的操作
                        showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                                getSubCommentContent(), commentListType, commentId, subCommentDetailDos.getSubCommentId());
                    } else {  //调用软件盘进行回复
                        showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                    }
                }
            } else {
                if (isFalse) {
                    //当前这条评论自己发的，调用弹窗进行复制和删除的操作
                    showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                            getSubCommentContent(), commentListType, commentId, subCommentDetailDos.getSubCommentId());
                } else {
                    //调用软件盘进行回复
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title) + userDO.getNikeName());
                }
            }
        }

    }

    @Override
    public void onDiscoveryDetailsCommentsClickListener(DiscoveryCommentsBean.DataBean dataBean, int position) {
        //点击一级评论添加二级评论的回调接口
        this.dataBean = dataBean;
        //得到当前的一家评论的下标
        this.itemIndex = position;
        if (dataBean != null) {
            //一级评论的ID
            this.commentId = dataBean.getCommentId();
            //设置二级评论的ID设置默认状态0
            this.subCommentId = 0;
            DiscoveryCommentsBean.DataBean.UserDOBean userDOBean = dataBean.getUserDO();
            if (userDOBean != null) {
                //设置被回复人ID
                replyUserId = userDOBean.getId();
                //设置被回复人昵称
                replyNickName = userDOBean.getNikeName();
                //判断当前的评论是不是自己发的
                if (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "")
                        .equals(String.valueOf(userDOBean.getId()))) {
                    //当前的评论自己发的
                    //关闭软键盘
                    inputPanelFrameLayout.onKeyboardHidden();
                    // 设置当前的操作还是一级评论
                    commentListType = 2;
                    //当前这条评论自己发的，调用弹窗进行复制和删除的操作
                    showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), 3, commentId, commentListType);
                } else {
                    // 设置当前的操作还是二级评论
                    commentListType = 3;
                    //当前评论不是自己发的，进行回复操作
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
            map.put("relatedType", 4);//资料
            map.put("relatedId",getIntent().getIntExtra("id", 0));
            //发送评论点赞的请求到后台
            mPresenter.getThumbUpDiscovery(map);
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
            map.put("commentType", 2);
            map.put("pageIndex", itemPageIndex);
            map.put("pageSize", 15);
            map.put("type", 4);
            map.put("token", token);
            //发送分页加载二级评论数据的请求到后台
            mPresenter.getDiscoverSubCommentDetailDosBean(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveNoLike(DiscoveryCommentsBean.DataBean dataBean, int position) {
        //评论列表点踩的接口回调
        if (isLoginStatus()) {
            this.dataBean = dataBean;
            //设置当前点踩数据的下标
            this.itemIndex = position;
            if (this.dataBean != null) {
                //如果当前的状态是踩那就修改false,如果不是修改为true;
                isItemStepOn = !this.dataBean.isStepOn();
                //设置点踩的数量
                itemStepOnCount = dataBean.getStepOnCount();
                int itemStepStatus = !isItemStepOn ? 0 : 1;
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("id", this.dataBean.getCommentId());
//            map.put("userId", dataBean.getCommentUserId());
                map.put("type", 3);
                map.put("status", itemStepStatus);
                //发送点踩的请求到后台
                mPresenter.getStepOn(map);
            }
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsReply(DiscoveryCommentsBean.DataBean dataBean, int position) {
        //评论
        if (dataBean != null && dataBean.getUserDO() != null) {
            this.dataBean = dataBean;
            DiscoveryCommentsBean.DataBean.UserDOBean userDOBeanX = dataBean.getUserDO();
            replyNickName = concernUserNickName;
            String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
            if (userDOBeanX != null) {
                if (String.valueOf(userDOBeanX.getId()).equals(userId)) {
                    //设置二级评论的ID设置默认状态0
                    this.subCommentId = 0;
                    //设置一级评论的ID
                    this.commentId = dataBean.getCommentId();
                    //设置当前的类型是一级评论
                    commentListType = 2;
                    //设置当前的被回复人ID为当前资料的发布人
                    replyUserId = concernUserId;
                    //当前这条评论自己发的，调用弹窗进行复制和删除的操作
                    showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), 3, dataBean.getCommentId(), 0);
                } else {
                    //设置二级评论的ID设置默认状态0
                    this.subCommentId = 0;
                    //设置一级评论的ID
                    this.commentId = dataBean.getCommentId();
                    //设置当前的类型是二级评论
                    commentListType = 3;
                    //设置当前的被回复人ID
                    replyUserId = userDOBeanX.getId();
                    //当前的评论不是自己发的，调用软件盘进行回复
                    showSoftInputFromWindow(ArmsUtils.getString(BaseApplication.getInstance(), R.string.picture_reply_details_title_two) + userDOBeanX.getNikeName());
                }
            }

        }
    }

    @Override
    public void onDiscoveryUserHisInformation(int userID) {
        //点击图片跳转到他人资料
        if (isLoginStatus()) {
            Bundle bundle = new Bundle();
            bundle.putString("userId", String.valueOf(userId));
            isToOtherActivity(HisInformationActivity.class, bundle);
        }
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(String draftString) {
        inputPanelFrameLayout.show(mInputAwareLayout, draftString);
    }

    /**
     * //当前这条评论自己发的，调用弹窗进行复制和删除的操作
     *
     * @param copyText     评论的内容
     * @param commentType  操作的类型
     * @param commentId    一级的评论ID
     * @param subCommentId 二级的评论ID
     */
    private void showDiscoveryDetailsCommentsDialog(String copyText, int commentType, int commentId,
                                                    int subCommentId) {
        if (mDiscoveryDetailsCommentsDialog != null &&
                !mDiscoveryDetailsCommentsDialog.isShowing()) {
            mDiscoveryDetailsCommentsDialog.showDiscoveryDetailsCommentsDialog(copyText, commentType, commentId, subCommentId);
        }
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
            map.put("id", id);
            map.put("commentType", 2);
            //发送删除评论的请求到后台
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
            map.put("id", id);
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
            map.put("relatedType", 4);//资料
            //发送评论的请求到后台
            mPresenter.getAddCommentDiscovery(map);
        }
    }

    @Override
    public void onKeyboardShown() {

    }

    /**
     * 设置评论初始状态
     */
    private void setCommentNormal() {
        this.subCommentId = 0;
        this.commentId = 0;
        commentListType = 2;
        replyUserId = concernUserId;
        replyNickName = concernUserNickName;
    }

    @Override
    public void onKeyboardHidden() {
        inputPanelFrameLayout.onKeyboardHidden();
    }

    @Override
    public void onDoShare() {
        String shareUrl = SPUtils.getInstance().getString("shareUrl", AppConstants.APP_DOMAIN+"common/share?type=2&id="+getIntent().getIntExtra("id", 0));
        ShareUtils.doShare(this, shareUrl,true);
    }




    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }
}
