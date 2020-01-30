package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.di.component.DaggerNoticeComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.mine.NoticeContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineNoticeBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.NoticePresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MineNoticeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的消息（通知）
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 12:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NoticeActivity extends MJBaseActivity<NoticePresenter> implements
        NoticeContract.View, MineNoticeAdapter.IHisInformationClickListener {

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
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    MineNoticeAdapter mineNoticeAdapter;
    List<MineNoticeBean> mineNoticeBeanList;
    int pageIndex = 0;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNoticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_notice;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mineNoticeBeanList = new ArrayList<>();
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.notice));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineNoticeAdapter = new MineNoticeAdapter(R.layout.mine_notice_item,
                mineNoticeBeanList, this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mineNoticeAdapter);
        mineNoticeAdapter.setEmptyView(R.layout.mine_notice_item_error, smartRefreshLayout);
        smartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(this));
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishLoadMore();
            smartRefreshLayout.finishRefresh();
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
        EventBus.getDefault().post(new UpDateEvent());
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            killMyself();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ivTopReturn, R.id.tvTopTitle, R.id.ivTopTitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.tvTopTitle:
                break;
            case R.id.ivTopTitle:
                break;
        }
    }


    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "15");
        mPresenter.requestDada(map);
    }

    @Override
    public void onQueryNotifyList(List<MineNoticeBean> mineNoticeBeans) {
        if (pageIndex == 0) {
            mineNoticeBeanList.clear();
        }
        if (mineNoticeBeans != null && mineNoticeBeans.size() > 0) {
            mineNoticeBeanList.addAll(mineNoticeBeans);
        }


        if (mineNoticeAdapter != null) {
            mineNoticeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHisInformation(String userId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        isToOtherActivity(HisInformationActivity.class, bundle);
    }

    @Override
    public void getOnClickIntent(int relatedType, int relatedId) {
        //（1-首页，2-发现图片，3-视频，4-资料）
        Bundle bundle = new Bundle();
        switch (relatedType){
            case 1:
                bundle.putInt("picId", relatedId);
                //跳转到详情
                toOtherActivity(HomePictureDetailsActivity.class, bundle);
                break;
            case 2:
                bundle.putInt("imgId", relatedId);
                bundle.putInt("type", 1);
                toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);
                break;
            case 3:

                break;
            case 4:
                bundle.putInt("id", relatedId);
                toOtherActivity(PetElvesDetailsActivity.class, bundle);
                break;
        }

    }
}
