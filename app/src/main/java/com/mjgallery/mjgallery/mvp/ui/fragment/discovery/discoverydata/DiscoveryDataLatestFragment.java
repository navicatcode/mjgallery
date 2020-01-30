package com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryDataLatestComponent;
import com.mjgallery.mjgallery.event.DiscoveryDataEvent;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryDataLatestContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata.DiscoveryDataLatestPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails.DiscoveryDataGridAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails.DiscoveryDataListAdapter;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.DISCOVERY_DATA_ISLIST;


/**
 * ================================================
 * Description:圈子资料最新界面
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 20:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DiscoveryDataLatestFragment extends MJBaseFragment<DiscoveryDataLatestPresenter> implements DiscoveryDataLatestContract.View {

    @BindView(R.id.mRecyclerViewDataList)
    RecyclerView mRecyclerViewDataList;
    @BindView(R.id.mRecyclerViewDataGrid)
    RecyclerView mRecyclerViewDataGrid;
    DiscoveryDataListAdapter discoveryDataListAdapter;
    List<DiscoveryDataBean> discoveryDataBeans;
    DiscoveryDataGridAdapter discoveryDataGridAdapter;
    public static DiscoveryDataLatestFragment newInstance() {
        DiscoveryDataLatestFragment fragment = new DiscoveryDataLatestFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryDataLatestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery_data_latest, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        discoveryDataBeans = new ArrayList<>();
        discoveryDataGridAdapter = new DiscoveryDataGridAdapter(R.layout.adapter_discovery_details_data_grid_item, discoveryDataBeans);
        discoveryDataListAdapter = new DiscoveryDataListAdapter(R.layout.adapter_discovery_details_data_list_item, discoveryDataBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewDataList.setLayoutManager(linearLayoutManager);
        mRecyclerViewDataList.setAdapter(discoveryDataListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerViewDataGrid.setLayoutManager(gridLayoutManager);
        mRecyclerViewDataGrid.setAdapter(discoveryDataGridAdapter);
        mPresenter.requestDiscoveryDataList(2);
        discoveryDataListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (discoveryDataBeans.size() > position) {
                    DiscoveryDataBean discoveryDataBean = discoveryDataBeans.get(position);
                    if (discoveryDataBean != null) {
                        toPetElvesActivity(discoveryDataBean.getId(),discoveryDataBean.getName());
                    }
                }

            }
        });
        discoveryDataGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (discoveryDataBeans.size() > position) {
                    DiscoveryDataBean discoveryDataBean = discoveryDataBeans.get(position);
                    if (discoveryDataBean != null) {
                        toPetElvesActivity(discoveryDataBean.getId(),discoveryDataBean.getName());
                    }
                }
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
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
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
    public void onDiscoveryDataList(List<DiscoveryDataBean> beanList) {
        if (beanList!=null && beanList.size() > 0) {
            discoveryDataBeans.clear();
            discoveryDataBeans.addAll(beanList);
        }
        if (discoveryDataGridAdapter != null) {
            discoveryDataGridAdapter.notifyDataSetChanged();
        }
        if (discoveryDataListAdapter != null) {
            discoveryDataListAdapter.notifyDataSetChanged();
        }

    }


    @Subscriber(mode = ThreadMode.MAIN)
    public void onDiscoveryDataEvent(DiscoveryDataEvent discoveryDataEvent) {
        if (DISCOVERY_DATA_ISLIST) {
            mRecyclerViewDataList.setVisibility(View.VISIBLE);
            mRecyclerViewDataGrid.setVisibility(View.GONE);
        } else {
            mRecyclerViewDataGrid.setVisibility(View.VISIBLE);
            mRecyclerViewDataList.setVisibility(View.GONE);
        }
    }



    /**
     * 跳转到宠物精灵界面
     *
     * @param typeId
     */
    private void toPetElvesActivity(int typeId, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        bundle.putString("title", title);
        toOtherActivity(PetElvesActivity.class, bundle);
    }
}
