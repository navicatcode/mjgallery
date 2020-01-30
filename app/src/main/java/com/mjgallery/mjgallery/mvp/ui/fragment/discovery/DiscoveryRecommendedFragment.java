package com.mjgallery.mjgallery.mvp.ui.fragment.discovery;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryRecommendedComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.discovery.DiscoveryRecommendedContract;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeViewBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.DiscoveryRecommendedPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.main.RecommendedDiscovertAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:发现推荐界面
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 18:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class DiscoveryRecommendedFragment extends MJBaseFragment<DiscoveryRecommendedPresenter>
        implements DiscoveryRecommendedContract.View, RecommendedDiscovertAdapter.IDiscoveryClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    RecommendedDiscovertAdapter mRecommendedDiscovertAdapter;
    List<HomeViewBean> discoveryViewBeans;
    int pageIndex = 0;
    public static DiscoveryRecommendedFragment newInstance() {
        DiscoveryRecommendedFragment fragment = new DiscoveryRecommendedFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryRecommendedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery_recommended, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        discoveryViewBeans = new ArrayList<>();
        mRecommendedDiscovertAdapter = new RecommendedDiscovertAdapter(discoveryViewBeans, this, getActualWidth());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecommendedDiscovertAdapter);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                getDiscoveryData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                getDiscoveryData();
            }
        });
        getDiscoveryData();
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
        if(mSmartRefreshLayout!=null){
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
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
    public void getDiscoveryView(List<HomeViewBean> discoveryViewBeanList) {
        if (pageIndex == 0) {
            discoveryViewBeans.clear();
        }
        discoveryViewBeans.addAll(discoveryViewBeanList);
        if (mRecommendedDiscovertAdapter != null) {
            mRecommendedDiscovertAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDiscoveryItem(HomeViewBean item) {
        Bundle bundle = new Bundle();
        bundle.putInt("imgId", item.getId());
        bundle.putInt("type", 1);
        toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);
    }

    @Override
    public void onDiscoveryDataItem(HomeViewBean item) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        toOtherActivity(PetElvesDetailsActivity.class, bundle);
    }


    public int getActualWidth() {
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int marginWidth = SizeUtils.dp2px(8);
        return point.x / 2 - marginWidth;
    }

    private void getDiscoveryData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        map.put("type", 0);
        mPresenter.onDiscoveryData(map);
    }

}
