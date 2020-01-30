package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.di.component.DaggerMyCommentsComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.mine.MyCommentsContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyCommentsBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.MyCommentsPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.MyCommentsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的评论界面
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 18:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class MyCommentsActivity extends MJBaseActivity<MyCommentsPresenter> implements MyCommentsContract.View, MyCommentsAdapter.OnCommentsAdapterInterface {

    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.ivHomeSearch)
    ImageView ivHomeSearch;
    @BindView(R.id.ivHomeTop)
    ImageView ivHomeTop;
    @BindView(R.id.ivHomeShare)
    ImageView ivHomeShare;
    int pageIndex = 0;
    List<MyCommentsBean> myCommentsBeanList;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    MyCommentsAdapter myCommentsAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCommentsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_comments;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Utils.setTopImg(this,ivHomeTop,false);
        myCommentsBeanList = new ArrayList<>();
        myCommentsAdapter = new MyCommentsAdapter(R.layout.adapter_my_comments_item, myCommentsBeanList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myCommentsAdapter);
        myCommentsAdapter.setEmptyView(R.layout.adapter_my_comments_error, mSmartRefreshLayout);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(this));
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


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.ivTopReturn, R.id.ivHomeShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivHomeShare:
                break;
        }
    }

    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        mPresenter.onMyCommentsData(map);
    }


    @Override
    public void onMyComment(List<MyCommentsBean> myCommentsBeans) {

        if (pageIndex == 0) {
            myCommentsBeanList.clear();
        }
        if (myCommentsBeans != null && myCommentsBeans.size() > 0) {
            myCommentsBeanList.addAll(myCommentsBeans);
        }

        if (myCommentsAdapter != null) {
            myCommentsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onImgOnclik() {
        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        isToOtherActivity(HisInformationActivity.class, bundle);
    }

    @Override
    public void onCommentsContentOnclik(int relatedType,int relatedId) {
        //（0-发现图片，1-视频，2-资料 3-首页）
        Bundle bundle = new Bundle();
        switch (relatedType){
            case 0:
                bundle.putInt("imgId", relatedId);
                bundle.putInt("type", 1);
                toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);
                break;
            case 1:

                break;
            case 2:
                bundle.putInt("id", relatedId);
                toOtherActivity(PetElvesDetailsActivity.class, bundle);
                break;
            case 3:
                bundle.putInt("picId", relatedId);
                //跳转到详情
                toOtherActivity(HomePictureDetailsActivity.class, bundle);
                break;


        }
    }
}
