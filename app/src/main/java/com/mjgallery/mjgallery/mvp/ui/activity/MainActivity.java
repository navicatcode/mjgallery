package com.mjgallery.mjgallery.mvp.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.mjgallery.mjgallery.MarginLottery;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.utils.FragmentUtils;
import com.mjgallery.mjgallery.app.utils.ImagePictureSelectUtils;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.app.view.NavTab;
import com.mjgallery.mjgallery.app.view.NavTabRound;
import com.mjgallery.mjgallery.app.view.dialog.download.VersionDownloadDialog;
import com.mjgallery.mjgallery.app.view.dialog.download.VersionUpdateDialog;
import com.mjgallery.mjgallery.di.component.DaggerMainComponent;
import com.mjgallery.mjgallery.event.SocketStartEvent;
import com.mjgallery.mjgallery.mvp.contract.MainContract;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;
import com.mjgallery.mjgallery.mvp.presenter.MainPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.IFoundActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyCommentsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.signinreward.SignInRewardActivity;
import com.mjgallery.mjgallery.mvp.ui.fragment.CollectionFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.DiscoveryFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.HomeFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.MineFragment;
import com.mjgallery.mjgallery.socket.ObjectEvent;
import com.mjgallery.mjgallery.socket.handler.ClientPoHandlerProto;
import com.mjgallery.mjgallery.socket.handler.TimeClientHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * =======================================if (discoveryDataBeans.size() > position) {
 * DiscoveryDataBean discoveryDataBean = discoveryDataBeans.get(position);
 * if (discoveryDataBean != null) {
 * toPetElvesActivity(discoveryDataBean.getId());
 * }
 * }=========
 * Description:主界面
 * <p>
 * Created by MVPArmsTemplate on 07/13/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends MJBaseActivity<MainPresenter> implements MainContract.View,
        VersionUpdateDialog.UpdateDownloadListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.tab)
    PageNavigationView mainPNV;
    @BindView(R.id.llAllFragment)
    LinearLayout llAllFragment;
    @BindView(R.id.llcontactServiceLayout)
    LinearLayout llcontactServiceLayout;
    private FrameLayout fl_mask;
    private RelativeLayout rl_collection;
    private RelativeLayout rl_phone_album;
    private RelativeLayout rl_camera;
    private RelativeLayout rl_found;
    private TextView tv_collection;
    private TextView tv_phone_album;
    private TextView tv_camera;
    private TextView tv_found;
    private ImageView iv1;
    private ImageView iv_publish;
    private List<RelativeLayout> imageViews = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private float radius = 400;
    private ObjectAnimator objectAnimatorX;
    private ObjectAnimator objectAnimatorY;
    private ObjectAnimator alpha;
    private ObjectAnimator objectAnimatorX1;
    private ObjectAnimator objectAnimatorY1;
    private boolean isShowMenu = false;
    private int widthPixels;
    private int heightPixels;
    List<String> filePathList;
    CollectionFragment collectionFragment;
    MineFragment mineFragment;
    DiscoveryFragment discoveryFragment;
    HomeFragment homeFragment;
    Bootstrap mBootstrap;
    SocketChannel mSocketChannel;
    private TimerTask reConnTask;
    private TimerTask heartTask;
    private Timer heartTimer;
    private Timer reConnTimer;
    private int connect;
    ImagePictureSelectUtils mImagePictureSelectUtils;
    private List<LocalMedia> selectList = new ArrayList<>();
    VersionUpdateDialog versionUpdateDialog;
    VersionDownloadDialog versionDownloadDialog;
    public static String newVersionNo = null;
    //当连接异常，增加执行次数，最多4次。-1为已正常执行，非-1要再执行四次
    int executeCoun = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Log.e("北京当前时间 == ", "" + Utils.getChinaTime());
        TW2CN.getInstance(this);
        selectList.clear();
        AppConstants.isOneStart = true;
        widthPixels = ArmsUtils.getScreenWidth(BaseApplication.getInstance());
        heightPixels = ArmsUtils.getScreenHeidth(BaseApplication.getInstance());
        filePathList = new ArrayList<>();
        versionUpdateDialog = new VersionUpdateDialog(this, this);
        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mImagePictureSelectUtils.setMaxSelectNum(9);
        mImagePictureSelectUtils.setCircleDimmedLayer(false);
        mImagePictureSelectUtils.setEnableCrop(false);
        assignViews();
        NavigationController navigationController = mainPNV.custom()
                .addItem(newItem(R.drawable.icon_home, R.drawable.icon_home_select,
                        getResources().getString(R.string.app_home)))
                .addItem(newItem(R.drawable.icon_discovery, R.drawable.icon_discovery_select,
                        getResources().getString(R.string.app_discovery)))
                .addItem(newRoundItem(R.color.trans, R.color.trans,
                        getResources().getString(R.string.app_publish)))
                .addItem(newItem(R.drawable.icon_collection, R.drawable.icon_collection_select,
                        getResources().getString(R.string.app_collection)))
                .addItem(newItem(R.drawable.icon_mine, R.drawable.icon_mine_select,
                        getResources().getString(R.string.app_mine)))
                .build();

        navigationController.addSimpleTabItemSelectedListener(new SimpleTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index) {
                    case 0:
                        playMusic(R.raw.controllergoback);
                        closeMenu();
                        showHomeFragment();
                        break;
                    case 1:
                        playMusic(R.raw.controllergoback);
                        closeMenu();
                        showDiscoveryFragment();
                        break;
                    case 2:

                        break;
                    case 3:
                        playMusic(R.raw.controllergoback);
                        closeMenu();
                        showCollectionFragment();
                        break;
                    case 4:
                        playMusic(R.raw.controllergoback);
                        closeMenu();
                        showMineFragment();
                        break;
                }
            }
        });
        showHomeFragment();
        checkVersionUpdate();
    }


    public void checkVersionUpdate() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "0");
        map.put("versionCode", Utils.getLocalVersion(this) + "");
        mPresenter.requestVersionUpdate(map);
    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
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

    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NavTab mainTab = new NavTab(this);
        mainTab.initialize(drawable, checkedDrawable, text);
        mainTab.setTextDefaultColor(getResources().getColor(R.color.appNavTextColor));
        mainTab.setTextCheckedColor(getResources().getColor(R.color.appNavTextSelColor));
        return mainTab;
    }

    /**
     * 圆形tab
     */
    private BaseTabItem newRoundItem(int drawable, int checkedDrawable, String text) {
        NavTabRound mainTab = new NavTabRound(this);
        mainTab.initialize(drawable, checkedDrawable, text);
        mainTab.setTextDefaultColor(getResources().getColor(R.color.appNavTextColor));
        mainTab.setTextCheckedColor(getResources().getColor(R.color.appNavTextSelColor));
        return mainTab;
    }


    /**
     * 显示MineFragment
     */
    public void showMineFragment() {
        hideFragment();
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
            FragmentUtils.add(getSupportFragmentManager(), mineFragment, R.id.llAllFragment);
        }

        FragmentUtils.show(mineFragment);
    }


    /**
     * 显示CollectionFragment
     */
    public void showCollectionFragment() {
        hideFragment();
        if (collectionFragment == null) {
            collectionFragment = CollectionFragment.newInstance();
            FragmentUtils.add(getSupportFragmentManager(), collectionFragment, R.id.llAllFragment);
        }

        FragmentUtils.show(collectionFragment);
    }


    /**
     * 显示HomeFragment
     */
    public void showHomeFragment() {
        hideFragment();
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            FragmentUtils.add(getSupportFragmentManager(), homeFragment, R.id.llAllFragment);
        }
        FragmentUtils.show(homeFragment);

    }


    private void hideFragment() {
        if (homeFragment != null) {
            FragmentUtils.hide(homeFragment);
        }
        if (mineFragment != null) {
            FragmentUtils.hide(mineFragment);
        }
        if (collectionFragment != null) {
            FragmentUtils.hide(collectionFragment);
        }
        if (discoveryFragment != null) {
            FragmentUtils.hide(discoveryFragment);
        }
    }


    /**
     * 显示DiscoveryFragment
     */
    public void showDiscoveryFragment() {
        hideFragment();
        if (discoveryFragment == null) {
            discoveryFragment = DiscoveryFragment.newInstance();
            FragmentUtils.add(getSupportFragmentManager(), discoveryFragment, R.id.llAllFragment);
        }
        FragmentUtils.show(discoveryFragment);
    }

    //-------------------------------------底部菜单-------------------------------------------------

    private void assignViews() {
        fl_mask = findViewById(R.id.fl_mask);
        rl_collection = findViewById(R.id.rl_collection);
        imageViews.add(rl_collection);
        rl_phone_album = findViewById(R.id.rl_phone_album);
        imageViews.add(rl_phone_album);
        rl_camera = findViewById(R.id.rl_camera);
        imageViews.add(rl_camera);
        rl_found = findViewById(R.id.rl_found);
        imageViews.add(rl_found);

        tv_collection = findViewById(R.id.tv_collection);
        textViews.add(tv_collection);
        tv_phone_album = findViewById(R.id.tv_phone_album);
        textViews.add(tv_phone_album);
        tv_camera = findViewById(R.id.tv_camera);
        textViews.add(tv_camera);
        tv_found = findViewById(R.id.tv_found);
        textViews.add(tv_found);
        iv1 = findViewById(R.id.iv1);
        iv_publish = findViewById(R.id.iv_publish);
        for (int i = 0; i < textViews.size(); i++) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(textViews.get(i), "alpha", 1, 0);
            AnimatorSet animatorSetAlpha = new AnimatorSet();
            animatorSetAlpha.setDuration(500);
            animatorSetAlpha.setInterpolator(new AnticipateOvershootInterpolator());
            animatorSetAlpha.setStartDelay((500 * (textViews.size() - i)) / 10);
            animatorSetAlpha.play(alpha);
            animatorSetAlpha.start();
        }

        //当该布局为显示状态时，点击该布局就关闭扇形菜单
        fl_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeMenu();
            }
        });
    }

    //该变量用于防止扇形菜单各选项被重复点击多次，限制1秒内只能选择点击一次
    private long menuTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onClick(View v) {
        if (v.getId() == iv1.getId()) {
            if (isShowMenu == false) {
                showSectorMenu();
            } else {
                closeSectorMenu();
            }
        } else {

            if ((System.currentTimeMillis() - menuTime) > 1000) {//限制1秒内只能选择点击一次

                menuTime = System.currentTimeMillis();

                if (imageViews.indexOf(v) == 0) {
                    if (isLoginStatus()) {
                        toOtherActivity(MyCommentsActivity.class);
                    }
                } else if (imageViews.indexOf(v) == 1) {
                    mImagePictureSelectUtils.setCamera(false);
                    mImagePictureSelectUtils.initActivityPictureSelector(MainActivity.this, PictureMimeType.ofImage());
                } else if (imageViews.indexOf(v) == 2) {
                    mImagePictureSelectUtils.setCamera(true);
                    mImagePictureSelectUtils.setSelectionMode(PictureConfig.MULTIPLE);

                    mImagePictureSelectUtils.initActivityOpenCameraPictureSelector(MainActivity.this, PictureMimeType.ofImage());
                } else if (imageViews.indexOf(v) == 3) {
                    if (isLoginStatus()) {
                        toOtherActivity(IFoundActivity.class);
                    }
                }

                closeSectorMenu();
            }

        }
    }

    private void closeMenu() {
        if (isShowMenu) {
            closeSectorMenu();
        }
    }

    /**
     * 显示扇形菜单 https://github.com/linglongxin24/CircleMenu
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showSectorMenu() {
        iv_publish.setBackground(getResources().getDrawable(R.drawable.icon_expand_select));
        fl_mask.setVisibility(View.VISIBLE);
        isShowMenu = true;
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            double angle = 0;
            if (i == 0) {
                angle = 25.0;
                radius = widthPixels / 2.7f;
            } else if (i == 1) {
                angle = 65.0;
                radius = widthPixels / 3.085f;
            } else if (i == 2) {
                angle = 115.0;
                radius = widthPixels / 3.085f;
            } else if (i == 3) {
                angle = 155.0;
                radius = widthPixels / 2.7f;
            }
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius;
            objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, point.x);
            objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, point.y);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
            animatorSet.setStartDelay((500 * (imageViews.size() - i)) / 10);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();

            alpha = ObjectAnimator.ofFloat(textViews.get(i), "alpha", 0, 1);
            AnimatorSet animatorSetAlpha = new AnimatorSet();
            animatorSetAlpha.setDuration(500);
            animatorSetAlpha.setInterpolator(new AnticipateOvershootInterpolator());
            animatorSetAlpha.setStartDelay((500 * (textViews.size() - i)) / 10);
            animatorSetAlpha.play(alpha);
            animatorSetAlpha.start();
        }
    }

    /**
     * 关闭扇形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeSectorMenu() {
        iv_publish.setBackground(getResources().getDrawable(R.drawable.icon_expand));
        fl_mask.setVisibility(View.GONE);
        isShowMenu = false;

        //width=1080 height=2094 dpi=460 s8 width=720 height=1382 dpi=307
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            double angle = 0;
            if (i == 0) {
                angle = 25.0;
                radius = widthPixels / 2.7f;
            } else if (i == 1) {
                angle = 65.0;
                radius = widthPixels / 3.085f;
            } else if (i == 2) {
                angle = 115.0;
                radius = widthPixels / 3.085f;
            } else if (i == 3) {
                angle = 155.0;
                radius = widthPixels / 2.7f;
            }
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius;
            objectAnimatorX1 = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", point.x, 0);
            objectAnimatorY1 = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
            animatorSet.setStartDelay((500 * (imageViews.size() - i)) / 10);
            animatorSet.play(objectAnimatorX1).with(objectAnimatorY1);
            animatorSet.start();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(textViews.get(i), "alpha", 1, 0);
            AnimatorSet animatorSetAlpha = new AnimatorSet();
            animatorSetAlpha.setDuration(500);
            animatorSetAlpha.setInterpolator(new AnticipateOvershootInterpolator());
            animatorSetAlpha.setStartDelay((500 * (textViews.size() - i)) / 10);
            animatorSetAlpha.play(alpha);
            animatorSetAlpha.start();
        }
    }
    //-------------------------------------底部菜单-------------------------------------------------


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

                    //判断是否个人中心 ，在改头像
                    if (mineFragment != null && mineFragment.updMineHeadStatus) {

                        for (LocalMedia media : selectList) {
                            Log.i(TAG, "压缩---->" + media.getCompressPath());
                            Log.i(TAG, "原图---->" + media.getPath());
                            Log.i(TAG, "裁剪---->" + media.getCutPath());
                            if (media.getCompressPath() != null) {
                                File file = new File(media.getCompressPath());
                                mineFragment.onHeadFileMethod(file);
                            }
                        }

                    } else {//要发布

                        filePathList.clear();
                        for (LocalMedia media : selectList) {
                            Log.i(TAG, "压缩---->" + media.getCompressPath());
                            Log.i(TAG, "原图---->" + media.getPath());
                            Log.i(TAG, "裁剪---->" + media.getCutPath());
                            if (media.getCompressPath() != null) {
                                filePathList.add(media.getCompressPath());
                            }

                        }
                        if (filePathList.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("filePathList", (ArrayList<String>) filePathList);
                            toOtherActivity(IFoundActivity.class, bundle);
                        }
                        filePathList.clear();
                    }
                    break;
            }
        }
        if (mineFragment != null)
            mineFragment.updMineHeadStatus = false;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onPublishDiscovery(BaseResponse baseResponse) {

    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDeleteCacheDirFile() {
        PictureFileUtils.deleteCacheDirFile(MainActivity.this);
    }

    /**
     * 获取版本信息回调
     */
    @Override
    public void onVersionUpdate(VersionUpdateBean versionUpdateBean) {

        if (versionUpdateDialog != null && !versionUpdateDialog.isShowing()) {
            newVersionNo = versionUpdateBean.getVersionName();
            versionUpdateDialog.show(versionUpdateBean);
        }

    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (collectionFragment != null && !collectionFragment.isHidden() && collectionFragment.isImageWatcher) {
                    collectionFragment.handleBackPressed();
                } else {
                    showMessage("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                }

            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Subscriber(mode = ThreadMode.MAIN)
    public void onSocketStartEvent(SocketStartEvent socketStartEvent) {
        if (socketStartEvent.getStatus() == 1) {
            randomConnIP();
        } else {
            stopHeart();
            stopReConn();
        }
    }

    private void randomConnIP() {
        if (HomeFragment.ipList.size() > 0) {
            int max = HomeFragment.ipList.size() - 1;
            int min = 0;
            int i = min + (int) (Math.random() * (max - min + 1));
            if(i<HomeFragment.ipList.size()) {
                AppConstants.HOST = HomeFragment.ipList.get(i);
                conn();
                return;
            }
        }
        //拿不到连接的ip，每3秒再尝试一次
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //重新请求获取ip
                homeFragment.getMyPresenter().getChangeGetIpList();
                randomConnIP();
            }
        }, 3000);
    }


    public void conn() {

        try {
            final EventLoopGroup group = new NioEventLoopGroup();
            mBootstrap = new Bootstrap()
                    .group(group)
                    .remoteAddress(AppConstants.HOST, AppConstants.PORT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(0, 10, 0))
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufDecoder(MarginLottery.MessagePacket.getDefaultInstance()))
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new TimeClientHandler())
                                    .addLast(new ClientPoHandlerProto())//断线重连
                            ;
                        }
                    });

            final ChannelFuture channelFuture = mBootstrap.connect().sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (future.isSuccess()) {
                                    mSocketChannel = (SocketChannel) channelFuture.channel();
                                    Log.i("获取连接状态", "连接成功");
                                    if (mSocketChannel == null) {
                                        Toast.makeText(BaseApplication.getInstance(), "第一次连接socketChannel为空", Toast.LENGTH_SHORT).show();
                                    } else {
//                                        Toast.makeText(BaseApplication.getInstance(), "连接成功", Toast.LENGTH_SHORT).show();
                                        Log.i("断线重连", "断线重连成功");
                                        stopReConn();
                                        startHeart();
                                        perOnePerformance();//发送心跳包给服务器 保持客户端在线状态，服务器60秒每接受到客户端的心跳包，就会移除客户端
                                        //                                    initUser();
                                    }
                                } else {
                                    // 这里一定要关闭，不然一直重试会引发OOM
                                    future.channel().close();
                                    group.shutdownGracefully();

                                    Log.i("获取连接状态", "连接失败-future.isSuccess()为false" + future.isSuccess());
                                    future.channel().eventLoop().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.i("获取连接状态", "连接异常-第一次" + future.isSuccess());
                                            //                                        Toast.makeText(mContext, "与服务器连接异常", Toast.LENGTH_SHORT).show();
                                            conn();
                                        }
                                    }, 5, TimeUnit.SECONDS);
                                }
                            }
                        });
                        executeCoun = -1;
                    } catch (Throwable throwable) {
                        Log.i("获取连接状态", "连接异常-" + throwable.toString());
                        throwable.printStackTrace();
                        //ip连接异常，会再次随机选择一个ip连接尝试，最多三次连接不上就放弃
                        if (executeCoun != -1 && executeCoun < 4) {
                            executeCoun++;
                            randomConnIP();
                        }

                    }
                }
            });
        } catch (Exception e) {
            Log.i("获取连接状态", "连接异常-" + e.toString());
            e.printStackTrace();
        }
    }


    private void perOnePerformance() {
        heartTask = new TimerTask() {
            public void run() {
                sendHeart();
            }
        };
        heartTimer.schedule(heartTask, 5000, 15000);//延迟1秒后，每隔15秒发送心跳包，保持连接
    }

    private void reConn() {
        reConnTask = new TimerTask() {
            public void run() {
                stopHeart();//取消心跳包
                conn();
            }
        };
        reConnTimer.schedule(reConnTask, 10000, 1000 * 60);//延迟1秒后，每隔5秒断线重连
    }

    private void stopHeart() {
        if (heartTimer != null) {
            heartTimer.cancel();
            heartTimer = null;
        }
        if (heartTask != null) {
            heartTask.cancel();
            heartTask = null;
        }
    }

    private void stopReConn() {
        if (reConnTimer != null) {
            reConnTimer.cancel();
            reConnTimer = null;
        }
        if (reConnTask != null) {
            reConnTask.cancel();
            reConnTask = null;
        }
    }

    private void startHeart() {
        if (heartTimer == null) {
            heartTimer = new Timer();
        }
    }

    private void startReConn() {
        if (reConnTimer == null) {
            reConnTimer = new Timer();
        }
    }


    private void sendHeart() {
        MarginLottery.MessagePacket.Builder builder1 = MarginLottery.MessagePacket.newBuilder();
        builder1.setMessageType(MarginLottery.MessageType.HEART_BEAT);
        MarginLottery.HeartBeat.Builder newBuilder = MarginLottery.HeartBeat.newBuilder();
        newBuilder.setUserId("12");
        builder1.setHeartBeat(newBuilder.build());
        if (mSocketChannel == null) {
            Log.i("获取连接状态", "发送心跳失败");
//            Toast.makeText(BaseApplication.getInstance(), "发送心跳失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connect != 1) {
                            Log.i("获取连接状态", "发送心跳成功");
                        }
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            mSocketChannel.writeAndFlush(builder1.build());
        }
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void reConn(final ObjectEvent.MessageEvent event) {
        connect = event.connectState;
        if (event.connectState == 1) {
            Log.i("获取连接状态", "断线重连中");
            startReConn();
            reConn();
        }
    }


    @Override
    public void updateDownload(String apkUrl) {
        if (versionDownloadDialog == null) {
            versionDownloadDialog = new VersionDownloadDialog(this);
        }
        if (!versionDownloadDialog.isShowing()) {
            versionDownloadDialog.show(apkUrl);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopHeart();
        stopReConn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.llcontactServiceLayout)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llcontactServiceLayout:
                toOtherActivity(CustomerServiceActivity.class);
                break;
        }
    }


//    public static void main(String[] args) {
//        try {
//            JChineseConvertor jChineseConvertor = JChineseConvertor.getInstance();
//
//            System.out.println("繁体："+jChineseConvertor.s2t("马经图库"));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//
//    }
}
