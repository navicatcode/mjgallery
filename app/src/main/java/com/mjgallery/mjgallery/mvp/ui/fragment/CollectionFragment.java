package com.mjgallery.mjgallery.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
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

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.GlideSimpleLoader;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.CollectionDeleteDialog;
import com.mjgallery.mjgallery.di.component.DaggerCollectionComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.CollectionContract;
import com.mjgallery.mjgallery.mvp.model.bean.CollectionBean;
import com.mjgallery.mjgallery.mvp.presenter.CollectionPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.CollectionMultiItemAdapter;
import com.mjgallery.mjgallery.widget.ImageWatcher;
import com.mjgallery.mjgallery.widget.ImageWatcherHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_DATA;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_VIDEO;
import static com.mjgallery.mjgallery.app.AppConstants.HOME;


/**
 * ================================================
 * Description:我的收藏界面
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 20:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class CollectionFragment extends MJBaseFragment<CollectionPresenter> implements
        CollectionContract.View, CollectionDeleteDialog.ICollectionDelete, CollectionMultiItemAdapter.ICollectionDelete {

    int pageIndex = 0;
    CollectionDeleteDialog collectionDeleteDialog;
    CollectionMultiItemAdapter mCollectionMultiItemAdapter;
    List<CollectionBean> collectionBeanList;
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
    @BindView(R.id.tvDiscoveryTwo)
    TextView tvDiscoveryTwo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ImageWatcherHelper iwHelper;
    public boolean isImageWatcher = false;

    public static CollectionFragment newInstance() {
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        collectionDeleteDialog = new CollectionDeleteDialog(getActivity(), this);
        ivTopReturn.setVisibility(View.GONE);
        tvTopTitle.setText(ArmsUtils.getString(getContext(), R.string.app_collection));
        tvTopTitle.setTextColor(ArmsUtils.getColor(getContext(), R.color.white));
        collectionBeanList = new ArrayList<>();
        mCollectionMultiItemAdapter = new CollectionMultiItemAdapter(collectionBeanList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mCollectionMultiItemAdapter);
        setDiscoveryTwoTextColor();
        isImageWatcher = false;
        mCollectionMultiItemAdapter.setEmptyView(R.layout.collection_empty_view, refreshLayout);
        refreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                if (!isLoginStatus()) {
                    refreshLayout.finishLoadMore();
                } else {
                    getRequestDada();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                if (!isLoginStatus()) {
                    refreshLayout.finishRefresh();
                } else {
                    getRequestDada();
                }
            }

        });
//        iwHelper = ImageWatcherHelper.with(getActivity(), new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
//                .setTranslucentStatus(0)
//                // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
//                .setErrorImageRes(R.drawable.icon_loading) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
//                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
//                    @Override
//                    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
//                        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
//                        showMessage("长按了第" + (pos + 1) + "张图片");
//                    }
//                });
        mCollectionMultiItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (collectionBeanList.size() > position) {
                    CollectionBean collectionBean = collectionBeanList.get(position);
                    if (collectionBean.getPictureName().equals("图片已经不存在")) {
                        showMessage(collectionBean.getPictureName());
                        return;
                    }
                    Bundle bundle = new Bundle();
                    switch (collectionBean.getType()) {
                        case 1://当前type是1的话直接切首页
                            bundle.putInt("picId", collectionBean.getPictureId());
                            bundle.putString("year", String.valueOf(collectionBean.getYear()));
                            bundle.putInt("termsByYear", collectionBean.getTerms());
                            toOtherActivity(HomePictureDetailsActivity.class, bundle);
                            break;
                        case 2:
                            //  0-图片，1-视频，2-资料
                            int subType = collectionBean.getSubType();
                            if (subType == 0) {
                                //跳转发现图片详情
                                bundle.putInt("imgId", collectionBean.getPictureId());
                                toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);

                            } else if (subType == 2) {
                                //跳转资料详情
                                bundle.putInt("id", collectionBean.getPictureId());
                                toOtherActivity(PetElvesDetailsActivity.class, bundle);
                            } else {
                                //跳转视频详情
                            }
                            break;
                    }
                }
            }
        });
        isLogin();
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
     *     if (data != null && data instanceof Message  ) {
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
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
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
    protected void requestData() {
        super.requestData();
        getRequestDada();
    }


    @Override
    public void onMyCollection(List<CollectionBean> collectionBeans, int count) {
        if (pageIndex == 0) {
            collectionBeanList.clear();
        }
        tvDiscoveryTwo.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.collection_one) + count +
                ArmsUtils.getString(BaseApplication.getInstance(), R.string.collection_two));
        setDiscoveryTwoTextColor();
        if (collectionBeans != null && collectionBeans.size() > 0) {
            collectionBeanList.addAll(collectionBeans);
        }
        for (int i = 0; i < collectionBeanList.size(); i++) {
            CollectionBean collectionBean = collectionBeanList.get(i);
            switch (collectionBean.getType()) {
                case 1://当前type是就是首页的数据
                    collectionBean.setItemType(HOME);
                    break;
                case 2:
                    //  0-图片，1-视频，2-资料
                    int subType = collectionBean.getSubType();
                    if (subType == 0) {
                        collectionBean.setItemType(FOUND);

                    } else if (subType == 2) {
                        collectionBean.setItemType(FOUND_DATA);
                    } else {
                        collectionBean.setItemType(FOUND_VIDEO);
                    }
                    break;
            }
//            collectionBeans.set(i, collectionBean);
        }
        if (mCollectionMultiItemAdapter != null) {
            mCollectionMultiItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCollectionDelete(BaseResponse baseResponse) {
        if (baseResponse != null) {
            showMessage(baseResponse.getMessage());
            if (baseResponse.getCode() == 0) {
                pageIndex = 0;
                getRequestDada();
            }
        }
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


    @Override
    public void onCollectionDelete(int pictureId, int type, int relatedId) {
        Map<String, Object> map = new HashMap<>();
        map.put("picId", pictureId);
        map.put("token", token);
        map.put("relatedId", relatedId);
        map.put("type", type);
        mPresenter.requestDelete(map);
    }

    @Override
    public void onCollectionAdapterDelete(int pictureId, int type, int relatedId) {
        if (collectionDeleteDialog != null && !collectionDeleteDialog.isShowing()) {
            collectionDeleteDialog.show(pictureId, type, relatedId);
        }
    }

    @Override
    public void onCollectionAdapterImg(ImageView imageView, String url) {
//        if(iwHelper!=null){
//            isImageWatcher = true;
//            SparseArray<ImageView> imageGroupList=new SparseArray<>();
//            imageGroupList.append(0,imageView);
//            Uri uri = Uri.parse(url);
//            List<Uri> uriList=new ArrayList<>();
//            uriList.add(uri);
//            iwHelper.show(imageView,imageGroupList,uriList);
//
//        }
    }


    public void handleBackPressed(){
        if(iwHelper!=null){
            iwHelper.handleBackPressed();
            isImageWatcher=false;
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            upDataCollection();
        }
    }

    private void getRequestDada() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).getString("token", ""));
        mPresenter.requestDada(map);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onUpDateEvent(UpDateEvent upDateEvent) {
        upDataCollection();
    }

    /**
     * 进行状态判断刷新
     */
    private void upDataCollection() {
        isImageWatcher = false;
        if (isLoginStatus()) {
            if (pageIndex == 0) {
                getRequestDada();
            }
        } else {
            normalCollection();
        }
    }


    /**
     * 还原默认状态和数据
     */
    private void normalCollection() {
        pageIndex = 0;
        collectionBeanList.clear();
        tvDiscoveryTwo.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.collection_one) + 0 + ArmsUtils.getString(BaseApplication.getInstance(), R.string.collection_two));
        setDiscoveryTwoTextColor();
        if (mCollectionMultiItemAdapter != null) {
            mCollectionMultiItemAdapter.notifyDataSetChanged();
        }
        setDiscoveryTwoTextColor();
    }


    private void setDiscoveryTwoTextColor() {
        SpannableStringBuilder style = new SpannableStringBuilder(tvDiscoveryTwo.getText().toString());
        style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                R.color.color_0AAFFA)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDiscoveryTwo.setText(style);
    }

}
