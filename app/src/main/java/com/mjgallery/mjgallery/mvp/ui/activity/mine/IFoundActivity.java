package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.PreferenceUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideSimpleLoader;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.ImagePictureSelectUtils;
import com.mjgallery.mjgallery.app.view.dialog.IFoundDialog;
import com.mjgallery.mjgallery.di.component.DaggerIFoundComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.mine.IFoundContract;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.presenter.mine.IFoundPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.GridImageAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MessageAdapter;
import com.mjgallery.mjgallery.widget.FullyGridLayoutManager;
import com.mjgallery.mjgallery.widget.ImageWatcher;
import com.mjgallery.mjgallery.widget.ImageWatcherHelper;
import com.mjgallery.mjgallery.widget.MessagePicturesLayout;
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

import static com.jess.arms.utils.Preconditions.checkNotNull;



/**
 * ================================================
 * Description:我的发现
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IFoundActivity extends MJBaseActivity<IFoundPresenter> implements
        IFoundContract.View, MessageAdapter.IMessageClick, IFoundDialog.IFoundDialogClickListener,
        MessagePicturesLayout.Callback, GridImageAdapter.onAddPicClickListener {

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
    @BindView(R.id.ivIFoundUserImg)
    ImageView ivIFoundUserImg;
    @BindView(R.id.ivIFoundUserName)
    TextView ivIFoundUserName;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.mRecyclerViewImg)
    RecyclerView mRecyclerViewImg;
    @BindView(R.id.rlIFoundPictureOne)
    ImageView rlIFoundPictureOne;
    @BindView(R.id.tvFoundPictureTitle)
    TextView tvFoundPictureTitle;
    @BindView(R.id.rlIFoundPicture)
    RelativeLayout rlIFoundPicture;
    @BindView(R.id.rlIFoundError)
    LinearLayout rlIFoundError;
    @BindView(R.id.recyclerViewIFound)
    RecyclerView recyclerViewIFound;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private ImageWatcherHelper iwHelper;
    private MessageAdapter adapter;
    List<File> files;
    boolean isList = true;
    int pageIndex = 0;
    List<MyShowBean> myShowBeans;
    IFoundDialog iFoundDialog;
    boolean isOne = true;//是否第一次进来
    ImagePictureSelectUtils mImagePictureSelectUtils;
    GridImageAdapter mGridImageAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;
    List<String> filePathList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIFoundComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ifound;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mGridImageAdapter = new GridImageAdapter(getApplication(), this);
        mImagePictureSelectUtils.setEnableCrop(false);
        mImagePictureSelectUtils.setCompress(true);
        tvTopTitle.setText(getString(R.string.i_found));

        et.clearFocus();
        files = new ArrayList<>();
        filePathList = new ArrayList<>();
        if (getIntent() != null && getIntent().getStringArrayListExtra("filePathList") != null) {
            filePathList.addAll(getIntent().getStringArrayListExtra("filePathList"));
        }
        if (filePathList.size() > 0) {
            for (String s : filePathList) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setCompressPath(s);
                localMedia.setCutPath(s);
                localMedia.setPath(s);
                files.add(new File(s));
                selectList.add(localMedia);
            }
            btnQueDing.setEnabled(true);
        } else {
            btnQueDing.setEnabled(false);
        }

        rlQueDing.setVisibility(View.VISIBLE);
        iFoundDialog = new IFoundDialog(this, this);
        recyclerViewIFound.setVisibility(View.GONE);
        rlIFoundError.setVisibility(View.VISIBLE);
        myShowBeans = new ArrayList<>();
        rlIFoundError.setVisibility(View.GONE);
        GlideUtil.loadCircleImage(ivIFoundUserImg, R.drawable.mine_user_normal_img);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(IFoundActivity.this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerViewImg.setLayoutManager(manager);
        mGridImageAdapter.setList(selectList);
        mGridImageAdapter.setSelectMax(maxSelectNum);
        mRecyclerViewImg.setAdapter(mGridImageAdapter);
        mGridImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            mImagePictureSelectUtils.openActivityExternalPreview(IFoundActivity.this, position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            mImagePictureSelectUtils.externaActivitylPictureVideo(IFoundActivity.this, media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            mImagePictureSelectUtils.externalActivityPictureVideo(IFoundActivity.this, media.getPath());
                            break;
                    }
                }
            }
        });
        recyclerViewIFound.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerViewIFound.setAdapter(adapter = new MessageAdapter(getApplication(), true).setPictureClickCallback(this));

        //  **************  动态 addView   **************

        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(0)
                // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.drawable.error_picture_img) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
                    @Override
                    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
                        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                        showMessage("长按了第" + (pos + 1) + "张图片");
                    }
                });
        adapter.setiMessageClick(this);
        isLogin();
        //设置刷新控件头部布局
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getBaseContext()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                isList = true;
                isLogin();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = 0;
                isList = true;
                isLogin();
            }
        });
    }


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.finishRefresh();
        }
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
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {
        iwHelper.show(i, imageGroupList, urlList);
    }


    @Override
    public void onPublishDiscovery(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                selectList.clear();
                mPresenter.externalStorage();
                if (mGridImageAdapter != null) {
                    mGridImageAdapter.setList(selectList);
                    mGridImageAdapter.notifyDataSetChanged();
                }
                et.setText("");
                files.clear();
                btnQueDing.setEnabled(false);
                tvFoundPictureTitle.setVisibility(View.VISIBLE);
                requestData();
                GlideUtil.loadNormalImage(rlIFoundPictureOne, R.drawable.i_found_tupian_img);
            }
        }
    }

    @Override
    public void onMyShowList(List<MyShowBean> myShowBeanList) {
        if (pageIndex == 0) {
            myShowBeans.clear();
        }
        if (myShowBeanList != null) {
            myShowBeans.addAll(myShowBeanList);
        }

        if (myShowBeans.size() > 0) {
            adapter.set(myShowBeans);
            recyclerViewIFound.setVisibility(View.VISIBLE);
            rlIFoundError.setVisibility(View.GONE);
        } else {
            recyclerViewIFound.setVisibility(View.GONE);
            rlIFoundError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 删除发现--回调结果
     * @param baseResponse
     */
    @Override
    public void onDiscoveryDelete(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                pageIndex = 0;
                isLogin();
            }
        }
    }

    /**
     * 获取个人中心的信息--回调结果
     * @param userInformation
     */
    @Override
    public void onUserInformation(UserInformation userInformation) {
        if (userInformation != null) {
            ivIFoundUserName.setText(userInformation.getNikeName());
            GlideUtil.loadCircleImage(ivIFoundUserImg, userInformation.getHeadImg(), R.drawable.mine_user_normal_img);
        }
    }

    /**
     * 添加收藏--回调结果
     * @param baseResponse
     */
    @Override
    public void onAddCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDeleteCacheDirFile() {
        PictureFileUtils.deleteCacheDirFile(IFoundActivity.this);
    }


    /**
     * 发表图文--发送请求
     */
    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (isList) {
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 15);
            mPresenter.requestDataMyComments(map);
        } else {
            if (selectList.size() > 0) {
                if (!TextUtils.isEmpty(et.getText().toString())) {
                    map.put("desc", et.getText().toString());
                }
                files.clear();
                for (LocalMedia media : selectList) {
                    Log.i(TAG, "压缩---->" + media.getCompressPath());
                    Log.i(TAG, "原图---->" + media.getPath());
                    Log.i(TAG, "裁剪---->" + media.getCutPath());
                    if (media.getCompressPath() != null) {
                        files.add(new File(media.getCompressPath()));
                    }
                }
                if (PreferenceUtil.getInstance(getBaseContext()).getBoolean("isVideo", false)) {
                    map.put("type", "1");
                } else {
                    map.put("type", "0");
                }
                mPresenter.getPublishDiscovery(map, files);
                isList = true;
            }
        }

        if (isOne) {
            mPresenter.getUserInformation();
            isOne = false;
        }
    }


    @OnClick({R.id.ivTopReturn, R.id.btnQueDing, R.id.rlQueDing, R.id.ivIFoundUserImg,
            R.id.rlIFoundPictureOne, R.id.rlIFoundPicture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivIFoundUserImg://点击头像
                onImgMessageClick();
                break;
            case R.id.btnQueDing:
            case R.id.rlQueDing:
                isList = false;
                isLogin();
                break;
            case R.id.rlIFoundPictureOne:
            case R.id.rlIFoundPicture:
                mImagePictureSelectUtils.initActivityPictureSelector(IFoundActivity.this, PictureMimeType.ofAll());
                break;
        }
    }


    /**
     * 展开单条发现的操作选项列表
     * @param myShowBean
     * @param ivIFoundXiangXia
     */
    @Override
    public void onItemMessageClick(MyShowBean myShowBean, View ivIFoundXiangXia) {
        if (iFoundDialog != null && !iFoundDialog.isShowing()) {
            iFoundDialog.show(myShowBean, ivIFoundXiangXia);
        }
    }

    /**
     * 点击item的头像，跳转到用户信息页面
     */
    @Override
    public void onImgMessageClick() {
        String userId = com.mjgallery.mjgallery.app.utils.PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        isToOtherActivity(HisInformationActivity.class, bundle);
    }

    /**
     * 添加收藏--发送请求
     * @param myShowBean
     */
    @Override
    public void onShouChang(MyShowBean myShowBean) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("type", 2);
        map.put("tmpId", myShowBean.getId());
        mPresenter.getAddCollection(map);
    }

    /**
     * 删除发现--发送请求
     * @param myShowBean
     */
    @Override
    public void onDelete(MyShowBean myShowBean) {
        if (myShowBean != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("id", myShowBean.getId());
            mPresenter.onMyCommentsDelete(map);
        }
    }

    @Override
    public void onBianJi(MyShowBean myShowBean) {
        if (myShowBean != null) {
            et.setText(myShowBean.getDescription());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    if (PreferenceUtil.getInstance(getBaseContext()).getBoolean("isVideo", false) && selectList.size() > 1) {
                        LocalMedia media = selectList.get(selectList.size() - 1);
                        selectList.clear();
                        selectList.add(media);
                        showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.message_tishi));
                    }
                    if (selectList.size() > 0) {
                        btnQueDing.setEnabled(true);
                    }
                    if (mGridImageAdapter != null) {
                        mGridImageAdapter.setList(selectList);
                        mGridImageAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    @Override
    public void onAddPicClick() {
        mImagePictureSelectUtils.initActivityPictureSelector(IFoundActivity.this, PictureMimeType.ofAll());
    }

}
