package com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation;

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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerHisInformationLikeComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationLikeContract;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationLikeBean;
import com.mjgallery.mjgallery.mvp.presenter.hisinformation.HisInformationLikePresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.hisinformation.HisInformationLikeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
 * Description:他人喜欢界面
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class HisInformationLikeFragment extends MJBaseFragment<HisInformationLikePresenter> implements HisInformationLikeContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    HisInformationLikeAdapter hisInformationLikeAdapter;
    List<HisInformationLikeBean> hisInformationLikeBeanList;
    int pageIndex = 0;
    String userId;


    public static HisInformationLikeFragment newInstance(String userId) {
        HisInformationLikeFragment fragment = new HisInformationLikeFragment();
        fragment.userId = userId;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHisInformationLikeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_his_information_like, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        hisInformationLikeBeanList = new ArrayList<>();
        hisInformationLikeAdapter = new HisInformationLikeAdapter
                (hisInformationLikeBeanList, getActualWidth());
        mRecyclerView.setAdapter(hisInformationLikeAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        hisInformationLikeAdapter.setEmptyView(R.layout.discovery_item_error, mRecyclerView);
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
        hisInformationLikeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (hisInformationLikeBeanList.size() > position) {
                    Bundle bundle = new Bundle();
                    HisInformationLikeBean hisInformationLikeBean = hisInformationLikeBeanList.get(position);
                    switch (hisInformationLikeBean.getType()) {
                        case 1://当前type是1的话直接切首页
                            bundle.putInt("picId", hisInformationLikeBean.getShowId());
                            toOtherActivity(HomePictureDetailsActivity.class, bundle);
                            break;
                        case 2:
                            //  0-图片，1-视频，2-资料
                            int subType = hisInformationLikeBean.getSubType();
                            if (subType == 0) {
                                //跳转发现图片详情
                                bundle.putInt("imgId", hisInformationLikeBean.getShowId());
                                toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);

                            } else if (subType == 2) {
                                //跳转资料详情
                                bundle.putInt("id", hisInformationLikeBean.getShowId());
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
    public void onHerLike(List<HisInformationLikeBean> likeBeans) {
        if (pageIndex == 0) {
            hisInformationLikeBeanList.clear();
        }
        if (likeBeans != null && likeBeans.size() > 0) {
            hisInformationLikeBeanList.addAll(likeBeans);
        }
        for (int i = 0; i < hisInformationLikeBeanList.size(); i++) {
            HisInformationLikeBean hisInformationLikeBean = hisInformationLikeBeanList.get(i);
            switch (hisInformationLikeBean.getType()) {
                case 1://当前type是就是首页的数据
                    hisInformationLikeBean.setItemType(HOME);
                    break;
                case 2:
                    //  0-图片，1-视频，2-资料
                    int subType = hisInformationLikeBean.getSubType();
                    if (subType == 0) {
                        hisInformationLikeBean.setItemType(FOUND);

                    } else if (subType == 2) {
                        hisInformationLikeBean.setItemType(FOUND_DATA);
                    } else {
                        hisInformationLikeBean.setItemType(FOUND_VIDEO);
                    }
                    break;
            }
            hisInformationLikeBeanList.set(i, hisInformationLikeBean);
        }
        if (hisInformationLikeAdapter != null) {
            hisInformationLikeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void requestData() {
        super.requestData();
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "15");
        map.put("userId", userId);
        mPresenter.requestLikeData(map);
    }

    public int getActualWidth() {
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int marginWidth = SizeUtils.dp2px(8);
        return point.x / 2 - marginWidth;
    }


}
