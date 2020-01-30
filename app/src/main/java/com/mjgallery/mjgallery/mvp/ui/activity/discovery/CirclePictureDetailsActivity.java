package com.mjgallery.mjgallery.mvp.ui.activity.discovery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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
import com.mjgallery.mjgallery.app.view.dialog.home.discovery.DiscoveryDetailsCommentsDialog;
import com.mjgallery.mjgallery.awildfire.ConversationInputPanel;
import com.mjgallery.mjgallery.awildfire.InputAwareLayout;
import com.mjgallery.mjgallery.awildfire.KeyboardAwareLinearLayout;
import com.mjgallery.mjgallery.di.component.DaggerCirclePictureDetailsComponent;
import com.mjgallery.mjgallery.mvp.contract.discovery.CirclePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryBarrageDataBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.CirclePictureDetailsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryRecordActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails.DiscoverDetailsCommentsAdapter;
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
 * Description:发现详情第1版详情界面
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 08:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class CirclePictureDetailsActivity extends MJBaseActivity<CirclePictureDetailsPresenter>
        implements CirclePictureDetailsContract.View, KeyboardAwareLinearLayout.OnKeyboardShownListener,
        KeyboardAwareLinearLayout.OnKeyboardHiddenListener, DiscoverDetailsCommentsAdapter.IDiscoveryDetailsCommentsItemClickListener, DiscoveryDetailsCommentsDialog.IDiscoveryDetailsComments,
        ConversationInputPanel.OnConversationInputPanelStateChangeListener, GestureDetector.OnGestureListener {

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
    @BindView(R.id.tvSendComments)
    TextView tvSendComments;
    @BindView(R.id.rlSendComments)
    RelativeLayout rlSendComments;
    @BindView(R.id.llConversationInputPanel)
    LinearLayout llConversationInputPanel;
    @BindView(R.id.llCommentListOne)
    LinearLayout llCommentListOne;
    @BindView(R.id.tvDiscoveryDetailsCommentsTitle)
    TextView tvDiscoveryDetailsCommentsTitle;
    @BindView(R.id.ivDialogClose)
    UIImageView ivDialogClose;
    @BindView(R.id.rlDialogClose)
    RelativeLayout rlDialogClose;
    @BindView(R.id.llDiscoveryDetailsCommentsTitle)
    LinearLayout llDiscoveryDetailsCommentsTitle;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.inputPanelFrameLayout)
    ConversationInputPanel inputPanelFrameLayout;
    @BindView(R.id.etDiscoveryDetailsCommentsDialog)
    TextView etDiscoveryDetailsCommentsDialog;
    @BindView(R.id.rlDiscoveryDetailsDialogComments)
    RelativeLayout rlDiscoveryDetailsDialogComments;
    @BindView(R.id.rlCommentList)
    RelativeLayout rlCommentList;
    @BindView(R.id.llCommentList)
    InputAwareLayout llCommentList;
    @BindView(R.id.mSuperLikeLayout)
    SuperLikeLayout mSuperLikeLayout;
    String token;
    DiscoveryDetailsCommentsDialog mDiscoveryDetailsCommentsDialog;
    List<DiscoveryBarrageDataBean> discoveryBarrageDataBeans;
    GestureDetector gestureDetector;
    int pageIndex = 0;
    int isCollection;//是否收藏 0是没有收藏 1是已经收藏
    int isThumpUp;//是否点赞  0是没有点赞  1是已经点赞
    DownloadPicturesDialog mDownloadPicturesDialog;
    String pictureUrl;
    List<String> pictureUrlList;
    int imgId;//图片ID
    int type;
    int index = 0;//图片滑动默认为下标0
    GoodView goodView;
    int userId = 0;//被点赞人id
    List<DiscoveryDetailBean.DataBean.ShowPictureDoListBean> showPictureDoListBeans;
    private BarrageAdapter<DiscoveryBarrageDataBean> mBarrageAdapter;
    List<DiscoveryCommentsBean.DataBean> beanList;
    DiscoverDetailsCommentsAdapter discoverDetailsCommentsAdapter;
    int commentListType = 2;//评论类型(1-首页一级评论，3-首页二级评论，4-首页二级回复)
    int commentId = 0;//评论id(一级评论不需要，二级评论需要带上)
    int subCommentId = 0;//子评论id
    int replyUserId = 0;
    int itemTypeId = 0;
    int itemPageIndex = 0;
    boolean isShowComments = false;
    private int verticalMinDistance = 10;

    @Override
    protected void requestData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
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
            if (pictureUrlList.size() == 1) {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.title_message_one));
            } else {
                if (index >= 0 && index < pictureUrlList.size() - 1) {
                    index = index + 1;
                    pictureUrl = pictureUrlList.get(index);
                    setIvHDImageViewImg();
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.title_message_one));
                }
            }

        } else if (e2.getX() - e1.getX() > verticalMinDistance) {

            if (pictureUrlList.size() == 1) {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.title_message_one));
            } else {
                if (index >= 1 && index <= pictureUrlList.size()) {
                    index = index - 1;
                    pictureUrl = pictureUrlList.get(index);
                    setIvHDImageViewImg();
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.title_message_two));
                }
            }
        }
        return true;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCirclePictureDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_circle_picture_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        ivTopTitle.setVisibility(View.GONE);
        if (getIntent() != null) {
            imgId = getIntent().getIntExtra("imgId", 0);
            type = getIntent().getIntExtra("type", 0);
        }
        pictureUrlList = new ArrayList<>();
        showPictureDoListBeans = new ArrayList<>();
        goodView = new GoodView(this);
        tvTopTitle.setVisibility(View.VISIBLE);
        inputPanelFrameLayout.init(this, llCommentList);
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.TOKEN, "");
        inputPanelFrameLayout.setOnConversationInputPanelStateChangeListener(this);
        mDiscoveryDetailsCommentsDialog = new DiscoveryDetailsCommentsDialog(this, this);
        discoveryBarrageDataBeans = new ArrayList<>();
        gestureDetector = new GestureDetector(this, this);
        //下载图片弹窗,没有做任何的处理，只展示
        mDownloadPicturesDialog = new DownloadPicturesDialog(this);
        GlideUtil.loadNormalImageSV(ivTopReturn, R.drawable.activity_return_while_img);
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        beanList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        discoverDetailsCommentsAdapter = new DiscoverDetailsCommentsAdapter(getApplication(),
                R.layout.adapter_discovery_details_comments_item,
                beanList, this, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        discoverDetailsCommentsAdapter.setEmptyView(R.layout.adapter_discovery_details_comments_item_no_date, mSmartRefreshLayout);
        mRecyclerView.setAdapter(discoverDetailsCommentsAdapter);
        //图片放大缩小的监听，处理图片是否透明
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
        getDiscoveryDetail();
        requestCommentDetail();
    }

    @Override
    public void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean dataBean, DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition) {
        this.commentId = commentId;
        this.subCommentId = subCommentDetailDos.getSubCommentId();
        commentListType = 4;

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
                if (userId.equals(String.valueOf(userDO.getId()))) {
                    isFalse = true;
                }
            }
            if (replyUserDO != null) {
                if (userId.equals(String.valueOf(replyUserDO.getId()))) {
                    showSoftInputFromWindow("");
                } else {
                    if (isFalse) {
                        showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                                getSubCommentContent(), commentId, subCommentDetailDos.getSubCommentId());
                    } else {
                        showSoftInputFromWindow("");
                    }
                }
            } else {
                if (isFalse) {
                    showDiscoveryDetailsCommentsDialog(subCommentDetailDos.
                            getSubCommentContent(), commentId, subCommentDetailDos.getSubCommentId());
                } else {
                    showSoftInputFromWindow("");
                }
            }
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsClickListener(DiscoveryCommentsBean.DataBean dataBean, int position) {
        this.commentId = dataBean.getCommentId();
        this.subCommentId = 0;
        commentListType = 3;
        if (dataBean.getUserDO() != null) {
            replyUserId = dataBean.getUserDO().getId();
        }
        if (dataBean != null) {
            this.commentId = dataBean.getCommentId();
            this.subCommentId = 0;
            commentListType = 3;
            if (dataBean.getUserDO() != null) {
                if (PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "")
                        .equals(String.valueOf(dataBean.getUserDO().getId()))) {
                    inputPanelFrameLayout.onKeyboardHidden();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            showDiscoveryDetailsCommentsDialog(dataBean.getCommentContent(), commentId, commentListType);
                        }
                    }, 800);   //5秒
                } else {
                    showSoftInputFromWindow("");
                }
            } else {
                showSoftInputFromWindow("");
            }

        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveLike(DiscoveryCommentsBean.DataBean dataBean, int position) {
        if (isLoginStatus()) {
            userId = dataBean.getUserDO().getId();
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("id", dataBean.getCommentId());
            if (userId != 0) {
                map.put("userId", userId);
            }
            map.put("type", 3);
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
            mPresenter.getDiscoverSubCommentDetailDosBean(map);
        }
    }

    @Override
    public void onDiscoveryDetailsCommentsGiveNoLike(DiscoveryCommentsBean.DataBean dataBean, int position) {

    }

    @Override
    public void onDiscoveryDetailsCommentsReply(DiscoveryCommentsBean.DataBean dataBean, int position) {

    }

    @Override
    public void onDiscoveryUserHisInformation(int userID) {
        
    }


    @OnTouch({R.id.llCommentList, R.id.mRecyclerView, R.id.inputPanelFrameLayout})
    boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() != R.id.inputPanelFrameLayout) {
                commentId = 0;
                subCommentId = 0;
                replyUserId = 0;
                inputPanelFrameLayout.collapse();
            }
        }
        return false;
    }

    private void showDiscoveryDetailsCommentsDialog(String copyText, int commentId,
                                                    int subCommentId) {
        if (mDiscoveryDetailsCommentsDialog != null &&
                !mDiscoveryDetailsCommentsDialog.isShowing()) {
            mDiscoveryDetailsCommentsDialog.showDiscoveryDetailsCommentsDialog(copyText, commentListType, commentId, subCommentId);
        }
    }




    class ViewHolder extends BarrageAdapter.BarrageViewHolder<DiscoveryBarrageDataBean> {

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
        protected void onBind(DiscoveryBarrageDataBean data) {
            if (data.getCommentDetailDosBean() != null && data.getCommentDetailDosBean().getUserDO() != null) {
                DiscoveryDetailBean.DataBean.CommentDetailDosBean.UserDOBeanX userDOBeanX = data.getCommentDetailDosBean().getUserDO();
                GlideUtil.loadCircleImage(mHeadView, userDOBeanX.getHeadImg(), R.drawable.mine_user_normal_img);
                tvUserName.setText(userDOBeanX.getNikeName());
            }
            mContent.setText(data.getCommentDetailDosBean().getCommentContent());
        }
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
        mBarrageView.setAdapter(mBarrageAdapter = new BarrageAdapter<DiscoveryBarrageDataBean>(null, getApplication()) {
            @Override
            public BarrageViewHolder<DiscoveryBarrageDataBean> onCreateViewHolder(View root, int type) {
                return new ViewHolder(root);
            }

            @Override
            public int getItemLayout(DiscoveryBarrageDataBean barrageData) {
                return R.layout.barrage_item_big;
            }
        });
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
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.copy_text_successfully));
        if (mDiscoveryDetailsCommentsDialog != null && mDiscoveryDetailsCommentsDialog.isShowing()) {
            mDownloadPicturesDialog.dismiss();
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
            mPresenter.getAddCommentDiscovery(map);
        }

    }

    @Override
    public void onKeyboardHidden() {

    }

    @Override
    public void onKeyboardShown() {

    }


    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(),message);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onDiscoveryDetail(DiscoveryDetailBean discoveryDetailBean) {
        if (discoveryDetailBean == null) {
            return;
        }
        DiscoveryDetailBean.DataBean data = discoveryDetailBean.getData();
        if (data == null) {
            return;
        }
        tvTopTitle.setText(data.getDescription());
        if (data != null) {
            userId = data.getId();
        }
        if (discoveryDetailBean.getCondition() != null) {
            tvDiscoveryDetailsCommentsTitle.setText(data.getCommentCount() + "");
        }

        tvImgTitleName.setText(data.getDescription());
        //是否收藏过
        isCollection = data.getIsCollection();
        //是否点赞过
        isThumpUp = data.getIsThumpUp();
        //设置点赞数量
        tvGiveLike.setText(data.getThumbUpCount() + "");
        //设置收藏数量
        tvCollection.setText(data.getCollectionCount() + "");
        //设置评论数量
        tComments.setText(data.getCommentCount() + "");
        showPictureDoListBeans.clear();
        discoveryBarrageDataBeans.clear();
        if(mBarrageAdapter!=null){
            mBarrageAdapter.destroy();
            mBarrageAdapter=null;
        }

        initBarrage();
        if (data.getShowPictureDoList() != null && data.getCommentDetailDos() != null) {
            showPictureDoListBeans.addAll(data.getShowPictureDoList());
            for (DiscoveryDetailBean.DataBean.CommentDetailDosBean commentDetailDosBean :
                    data.getCommentDetailDos()) {
                DiscoveryBarrageDataBean discoveryBarrageDataBean = new DiscoveryBarrageDataBean();
                discoveryBarrageDataBean.setCommentDetailDosBean(commentDetailDosBean);
                discoveryBarrageDataBeans.add(discoveryBarrageDataBean);

            }
        }
        if(mBarrageView!=null){
            if(discoveryBarrageDataBeans.size()==0){
                mBarrageView.setVisibility(View.GONE);
            }else {
                mBarrageView.setVisibility(View.VISIBLE);
            }
        }

        mBarrageAdapter.addList(discoveryBarrageDataBeans);
        pictureUrlList.clear();
        if (showPictureDoListBeans.size() > 0) {
            for (DiscoveryDetailBean.DataBean.ShowPictureDoListBean showPictureDoListBean : showPictureDoListBeans) {
                pictureUrlList.add(showPictureDoListBean.getFilePath());
            }
            pictureUrl = showPictureDoListBeans.get(0).getFilePath();
            setIvHDImageViewImg();
//            GlideUtil.loadNormalImage(ivHDImageView,pictureUrl, R.drawable.discovery_details_bg);

        }
    }


    private void setIvHDImageViewImg() {
        RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
        Glide.with(CirclePictureDetailsActivity.this).load(pictureUrl).apply(options).into(ivHDImageView);

    }





    @Override
    public void onThumbUpDiscovery(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                getDiscoveryDetail();
                requestCommentDetail();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCommentDetail(DiscoveryCommentsBean homeDetailsCommentsBean) {
        if (pageIndex == 0) {
            beanList.clear();
        }

        if (homeDetailsCommentsBean != null) {
            beanList.addAll(homeDetailsCommentsBean.getData());
        }
        if (discoverDetailsCommentsAdapter != null) {
            if (pageIndex == 0) {
                mRecyclerView.scrollToPosition(0);
            }
            discoverDetailsCommentsAdapter.notifyDataSetChanged();
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
    public void onAddCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                getDiscoveryDetail();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCancelCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                getDiscoveryDetail();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onAddCommentDiscovery(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                requestCommentDetail();
                getDiscoveryDetail();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCircleDeleteComment(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                requestCommentDetail();
                getDiscoveryDetail();
            }
            showMessage(baseResponse.getMessage());
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
                commentListType = 2;
                replyUserId = 0;
                showCommentsList();
                break;
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.rlTop:

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
                userId = 0;
                onGiveLike();
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
                isShowComments = true;
                llCommentList.setVisibility(View.VISIBLE);
                llCommentList.startAnimation(ctrlAnimation);
                showSoftInputFromWindow("");
            }
        }, 500);
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(String draftString) {
        inputPanelFrameLayout.show(llCommentList,draftString);
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


    private void getDiscoveryDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", 15);
        map.put("pageIndex", pageIndex);
        map.put("token", token);
        map.put("id", imgId);
        map.put("type", type);
        mPresenter.getDiscoveryDetail(map);
    }

    //收藏点击事件
    private void onCollectionClick() {
        if (!isLoginStatus()) {
            return;
        }
        //        subType", value = "发现的type，0-图片，1-视频，2-资料
        Map<String, Object> map = new HashMap<>();
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
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
    }

    /**
     * 点赞
     */
    private void onGiveLike() {
        if (!isLoginStatus()) {
            return;
        } else {
            if (isThumpUp == 0) {
                goodView.setText("+1");
                goodView.show(tvGiveLike);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", imgId);
            if (userId != 0) {
                map.put("userId", userId);
                map.put("type", 2);
                map.put("token", token);
                mPresenter.getThumbUpDiscovery(map);

            }
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
                e.onNext(Glide.with(CirclePictureDetailsActivity.this)
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
                        FileUtil.copyFile(file, destFile, destFile.getPath(), fileName, CirclePictureDetailsActivity.this);
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

}
