package com.mjgallery.mjgallery.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.share.ShareUtils;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryComponent;
import com.mjgallery.mjgallery.mvp.contract.DiscoveryContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryTitleBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeViewBean;
import com.mjgallery.mjgallery.mvp.presenter.DiscoveryPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.discovery.DiscoveryTitleAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.main.DiscoveryViewAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.DiscoveryCircleFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.DiscoveryDataFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.DiscoveryRecommendedFragment;
import com.mjgallery.mjgallery.mvp.ui.adapter.FmPagerAdapter;
import com.mjgallery.mjgallery.widget.viewpager.CustomScrollViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.DISCOVERY_TYPE;


/**
 * ================================================
 * Description:我的圈子界面
 * <p>
 * Created by MVPArmsTemplate on 08/11/2019 21:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 圈子
 */
public class DiscoveryFragment extends MJBaseFragment<DiscoveryPresenter> implements DiscoveryViewAdapter.IDiscoveryClickListener, DiscoveryContract.View {
    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.ivHomeSearch)
    ImageView ivHomeSearch;
    @BindView(R.id.ivHomeTop)
    ImageView ivHomeTop;
    @BindView(R.id.ivHomeShare)
    ImageView ivHomeShare;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mViewPager)
    CustomScrollViewPager mViewPager;
    List<DiscoveryTitleBean> discoveryTitleBeans;
    DiscoveryTitleAdapter discoveryTitleAdapter;
    List<Fragment> fragmentList;
    FmPagerAdapter fmPagerAdapter;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivTopReturn.setVisibility(View.GONE);
        ivHomeSearch.setVisibility(View.GONE);
        ivHomeShare.setVisibility(View.GONE);
        fragmentList = new ArrayList<>();
        fragmentList.add(DiscoveryDataFragment.newInstance());
        fragmentList.add(DiscoveryCircleFragment.newInstance());
        fragmentList.add(DiscoveryRecommendedFragment.newInstance());
        GlideUtil.loadNormalImage(ivHomeSearch, R.drawable.discovery_seacher_img);
        GlideUtil.loadNormalImage(ivHomeShare, R.drawable.discovery_share_img);
        Utils.setTopImg(getContext(),ivHomeTop,false);
        addDiscoveryTitleBeansData();
        fmPagerAdapter = new FmPagerAdapter(fragmentList, getChildFragmentManager());
        mViewPager.setAdapter(fmPagerAdapter);
        mViewPager.setCurrentItem(0);
        discoveryTitleAdapter = new DiscoveryTitleAdapter(R.layout.adapter_discovery_title_item, discoveryTitleBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(discoveryTitleAdapter);
        setSwitchFragmentTitle();
        discoveryTitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    if (position < discoveryTitleBeans.size()) {
                        switchFragment(discoveryTitleBeans.get(0).getType());
                    }
                } else {
                    if (position < discoveryTitleBeans.size()) {
                        DiscoveryTitleBean discoveryTitleBean = discoveryTitleBeans.get(position);
                        switchFragment(discoveryTitleBean.getType());
                        discoveryTitleBean.setPlacedTop(true);
                        discoveryTitleBeans.remove(position);
                        List<DiscoveryTitleBean> discoveryTitleBeanList = new ArrayList<>();
                        discoveryTitleBeanList.add(discoveryTitleBean);
                        for (int i = 0; i < discoveryTitleBeans.size(); i++) {
                            DiscoveryTitleBean discoveryTitle = discoveryTitleBeans.get(i);
                            discoveryTitle.setPlacedTop(false);
                            discoveryTitleBeanList.add(discoveryTitle);
                        }
                        discoveryTitleBeans.clear();
                        discoveryTitleBeans.addAll(discoveryTitleBeanList);
                        if (discoveryTitleAdapter != null) {
                            discoveryTitleAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();

    }


    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getContext(), message);
    }


    public static DiscoveryFragment newInstance() {
        Bundle args = new Bundle();
        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDiscoveryItem(HomeViewBean item) {

    }


    /**
     * 添加标题数据
     */
    private void addDiscoveryTitleBeansData() {
        discoveryTitleBeans = new ArrayList<>();
        discoveryTitleBeans.add(new DiscoveryTitleBean(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_discovery_title_two), 3, false));
        discoveryTitleBeans.add(new DiscoveryTitleBean(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_discovery_title_three), 1, false));
        discoveryTitleBeans.add(new DiscoveryTitleBean(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_discovery_title_four), 0, false));
    }


    /**
     * 根据类型切换Fragment
     *
     * @param type
     */
    private void switchFragment(int type) {
        DISCOVERY_TYPE = type;
        switch (type) {
            case 0:
                mViewPager.setCurrentItem(2);
                break;
            case 1:
                mViewPager.setCurrentItem(1);
                break;
            case 3:
                mViewPager.setCurrentItem(0);
                break;
        }
    }


    /**
     * 根据类型切换界面,并且把当前选中的数据放到第一条
     */
    private void setSwitchFragmentTitle() {
        if (DISCOVERY_TYPE == 2) {
            return;
        }

        DiscoveryTitleBean discoveryTitleBean = null;
        for (int i = 0; i < discoveryTitleBeans.size(); i++) {
            if (discoveryTitleBeans.get(i).getType() == DISCOVERY_TYPE) {
                discoveryTitleBean = discoveryTitleBeans.get(i);
                discoveryTitleBeans.remove(i);
                break;
            }
        }

        List<DiscoveryTitleBean> discoveryTitleBeanList = new ArrayList<>();
        if (discoveryTitleBean != null) {
            discoveryTitleBean.setPlacedTop(true);
            discoveryTitleBeanList.add(discoveryTitleBean);
            for (int i = 0; i < discoveryTitleBeans.size(); i++) {
                DiscoveryTitleBean discoveryTitle = discoveryTitleBeans.get(i);
                discoveryTitle.setPlacedTop(false);
                discoveryTitleBeanList.add(discoveryTitle);
            }
        }

        discoveryTitleBeans.clear();
        discoveryTitleBeans.addAll(discoveryTitleBeanList);
        switchFragment(DISCOVERY_TYPE);
        if (discoveryTitleAdapter != null) {
            discoveryTitleAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.ivHomeSearch, R.id.ivHomeTop, R.id.ivHomeShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHomeShare:
                mPresenter.externalStorage();
                break;
            case R.id.ivHomeTop:
                break;

        }
    }

    public static void main(String[] args) {
        String str="ssss";
        System.out.println(str.length());
    }
}
