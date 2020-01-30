package com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata;

import android.os.Bundle;
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
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryDataHotComponent;
import com.mjgallery.mjgallery.event.DiscoveryDataEvent;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryDataHotContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata.DiscoveryDataHotPresenter;
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
 * Description:圈子资料最火界面
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DiscoveryDataHotFragment extends MJBaseFragment<DiscoveryDataHotPresenter> implements DiscoveryDataHotContract.View {


    @BindView(R.id.mRecyclerViewDataList)
    RecyclerView mRecyclerViewDataList;
    @BindView(R.id.mRecyclerViewDataGrid)
    RecyclerView mRecyclerViewDataGrid;
    DiscoveryDataListAdapter discoveryDataListAdapter;
    List<DiscoveryDataBean> discoveryDataBeans;
    DiscoveryDataGridAdapter discoveryDataGridAdapter;

    public static DiscoveryDataHotFragment newInstance() {
        DiscoveryDataHotFragment fragment = new DiscoveryDataHotFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryDataHotComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery_data_hot, container, false);
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
        mPresenter.requestDiscoveryDataList(3);
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
