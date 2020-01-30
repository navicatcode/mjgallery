package com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.GlideSimpleLoader;
import com.mjgallery.mjgallery.di.component.DaggerHisInformationDynamicComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationDynamicContract;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;
import com.mjgallery.mjgallery.mvp.presenter.hisinformation.HisInformationDynamicPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MessageAdapter;
import com.mjgallery.mjgallery.widget.DecorationLayout;
import com.mjgallery.mjgallery.widget.ImageWatcher;
import com.mjgallery.mjgallery.widget.ImageWatcherHelper;
import com.mjgallery.mjgallery.widget.MessagePicturesLayout;
import com.mjgallery.mjgallery.widget.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:用户信息：动态(已弃用，换成评论)
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HisInformationDynamicFragment extends MJBaseFragment<HisInformationDynamicPresenter> implements
        HisInformationDynamicContract.View, MessageAdapter.IMessageClick, MessagePicturesLayout.Callback {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    int pageIndex = 0;
    String userId;
    @BindView(R.id.rlIFoundError)
    LinearLayout rlIFoundError;
    private DecorationLayout layDecoration;
    List<MyShowBean> myShowBeans;
    private ImageWatcherHelper iwHelper;
    private MessageAdapter adapter;


    public static HisInformationDynamicFragment newInstance(String userId) {
        HisInformationDynamicFragment fragment = new HisInformationDynamicFragment();
        fragment.userId = userId;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHisInformationDynamicComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_his_information_dynamic, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rlIFoundError.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        layDecoration = new DecorationLayout(getContext());
        myShowBeans = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new MessageAdapter(getContext(), false).setPictureClickCallback(this));
        //  **************  动态 addView   **************

        iwHelper = ImageWatcherHelper.with(getActivity(), new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
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
        layDecoration.attachImageWatcher(iwHelper);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                isLogin();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                isLogin();
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
        ArmsUtils.makeText(getContext(),message);
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
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("userId", userId);
        map.put("pageSize", "15");
        mPresenter.requestDataHerInformationList(map);
    }

    @Override
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {
        iwHelper.show(i, imageGroupList, urlList);
    }

    @Override
    public void onHerInformationList(List<MyShowBean> beanList) {
        if (pageIndex == 0) {
            myShowBeans.clear();
        }

        if (beanList != null && beanList.size() > 0) {
            myShowBeans.addAll(beanList);
        }
        if (myShowBeans.size() > 0) {
            rlIFoundError.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

        } else {
            rlIFoundError.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        if (adapter != null) {
            adapter.set(myShowBeans);

        }
    }


    @Override
    public void onItemMessageClick(MyShowBean myShowBean, View ivIFoundXiangXia) {

    }

    @Override
    public void onImgMessageClick() {

    }
}
