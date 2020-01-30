package com.mjgallery.mjgallery.mvp.ui.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.di.component.DaggerSearchDataComponent;
import com.mjgallery.mjgallery.event.SearchContentEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.search.SearchDataContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;
import com.mjgallery.mjgallery.mvp.presenter.search.SearchDataPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.search.SearchDataAdapter;
import com.mjgallery.mjgallery.recyclerview.FlowLayoutManager;
import com.mjgallery.mjgallery.recyclerview.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;


/**
 * ================================================
 * Description:搜索资料界面
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 13:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchDataFragment extends MJBaseFragment<SearchDataPresenter> implements SearchDataContract.View, SearchDataAdapter.ISearchData {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.llAll)
    LinearLayout llAll;
    SearchDataAdapter searchDataAdapter;
    List<DiscoveryDataBean> discoveryDataBeans;
    int pageIndex = 0;
    private String keyword;
    private boolean isVisibleToUser;

    public static SearchDataFragment newInstance() {
        SearchDataFragment fragment = new SearchDataFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchDataComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_data, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        discoveryDataBeans = new ArrayList<>();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(5)));
        mRecyclerView.setLayoutManager(flowLayoutManager);
        searchDataAdapter = new SearchDataAdapter(R.layout.adapter_search_drawings_item, discoveryDataBeans, this);
        searchDataAdapter.setEmptyView(R.layout.adapter_search_all_item_error, mSmartRefreshLayout);
        mRecyclerView.setLayoutManager(flowLayoutManager);
        mRecyclerView.setAdapter(searchDataAdapter);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex = pageIndex + 1;
                getHomeSearch();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getHomeSearch();
            }
        });
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

    }

    @Override
    public void onSearchDataClick(int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", id);
        bundle.putString("title", title);
        toOtherActivity(PetElvesActivity.class, bundle);
    }


    private void getHomeSearch() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(AppConstants.KEYWORD)) {
            map.put("keyword", AppConstants.KEYWORD);
            map.put("type", 4);
            map.put("pageIndex", pageIndex);
            map.put("pageSize", 50);
            if(mPresenter!=null){
                mPresenter.getSearchData(map);
            }
        } else {
            if (mSmartRefreshLayout != null) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
            }
        }

    }
    @Subscriber(mode = ThreadMode.MAIN)
    public void onSearchContentEvent(SearchContentEvent savePhoneEvent) {
        if (!TextUtils.isEmpty(savePhoneEvent.getContent())) {
            this.keyword = savePhoneEvent.getContent();
            pageIndex = 0;
            getHomeSearch();

        }
    }


    @Override
    public void onSearchData(List<DiscoveryDataBean> beanList) {
        if (pageIndex == 0) {
            discoveryDataBeans.clear();
        }
        if (beanList != null && beanList.size() > 0) {
            discoveryDataBeans.addAll(beanList);
        }
        if (searchDataAdapter != null) {
            searchDataAdapter.notifyDataSetChanged();
        }
    }
}
