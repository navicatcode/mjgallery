package com.mjgallery.mjgallery.mvp.ui.fragment.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.di.component.DaggerDiscoveryDataComponent;
import com.mjgallery.mjgallery.event.DiscoveryDataEvent;
import com.mjgallery.mjgallery.mvp.contract.discovery.DiscoveryDataContract;
import com.mjgallery.mjgallery.mvp.presenter.discovery.DiscoveryDataPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata.DiscoveryDataAllFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata.DiscoveryDataHotFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata.DiscoveryDataLatestFragment;
import com.mjgallery.mjgallery.widget.UIImageView;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.AppConstants.DISCOVERY_DATA_ISLIST;


/**
 * ================================================
 * Description:发现资料界面
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 18:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class DiscoveryDataFragment extends MJBaseFragment<DiscoveryDataPresenter> implements
        DiscoveryDataContract.View {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mSlidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.ivDataGridImg)
    UIImageView ivDataGridImg;
    @BindView(R.id.ivDataListImg)
    UIImageView ivDataListImg;
    @BindView(R.id.rlDataGridImg)
    RelativeLayout rlDataGridImg;
    @BindView(R.id.rlDataListImg)
    RelativeLayout rlDataListImg;
    private ArrayList<Fragment> mFragments;
    String[] mTitle = new String[3];
    FragmentPagerAdapter fragmentPagerAdapter;

    public static DiscoveryDataFragment newInstance() {
        DiscoveryDataFragment fragment = new DiscoveryDataFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDiscoveryDataComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery_data, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitle[0] =ArmsUtils.getString(BaseApplication.getInstance(),R.string.discovery_data_title_one);
        mTitle[1] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.discovery_data_title_two);
        mTitle[2] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.discovery_data_title_three);
        mFragments.add(DiscoveryDataAllFragment.newInstance());
        mFragments.add(DiscoveryDataLatestFragment.newInstance());
        mFragments.add(DiscoveryDataHotFragment.newInstance());
        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitle);
        mViewPager.setAdapter(fragmentPagerAdapter);
        //设置ViewPager缓存数量，避免出现第三个的时候没有创建问题
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        if (DISCOVERY_DATA_ISLIST) {
            ivDataGridImg.setEnabled(true);
            ivDataListImg.setEnabled(false);
        } else {
            ivDataGridImg.setEnabled(false);
            ivDataListImg.setEnabled(true);
        }
        EventBus.getDefault().post(new DiscoveryDataEvent());
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

    @OnClick({R.id.rlDataGridImg, R.id.rlDataListImg, R.id.ivDataGridImg, R.id.ivDataListImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivDataGridImg:
            case R.id.rlDataGridImg:
                DISCOVERY_DATA_ISLIST = false;
                ivDataGridImg.setEnabled(false);
                ivDataListImg.setEnabled(true);
                rlDataListImg.setEnabled(true);
                rlDataGridImg.setEnabled(false);
                EventBus.getDefault().post(new DiscoveryDataEvent());
                break;
            case R.id.ivDataListImg:
            case R.id.rlDataListImg:
                DISCOVERY_DATA_ISLIST = true;
                rlDataListImg.setEnabled(false);
                rlDataGridImg.setEnabled(true);
                ivDataGridImg.setEnabled(true);
                ivDataListImg.setEnabled(false);
                EventBus.getDefault().post(new DiscoveryDataEvent());
                break;
        }
    }
}
