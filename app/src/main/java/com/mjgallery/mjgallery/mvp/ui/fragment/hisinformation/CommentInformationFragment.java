package com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerCommentInformationComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.CommentInformationContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.GetUserInfoBean;
import com.mjgallery.mjgallery.mvp.presenter.hisinformation.CommentInformationPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.HisInfoCommonAdapter;
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
 * Description:他人评论
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommentInformationFragment extends MJBaseFragment<CommentInformationPresenter> implements CommentInformationContract.View, HisInfoCommonAdapter.OnClickDetailsListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    int pageIndex = 0;
    String userId;
    HisInfoCommonAdapter hisInfoCommonAdapter;
    List<GetUserInfoBean> getUserInfoBeanList;

    public static CommentInformationFragment newInstance(String userId) {
        CommentInformationFragment fragment = new CommentInformationFragment();
        fragment.userId = userId;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommentInformationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_information, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getUserInfoBeanList = new ArrayList<>();
        hisInfoCommonAdapter = new HisInfoCommonAdapter(R.layout.recycler_main_common_item, getUserInfoBeanList,this);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(hisInfoCommonAdapter);
        hisInfoCommonAdapter.setEmptyView(R.layout.include_ifound_error,mSmartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                requestData();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                requestData();
            }
        });

        requestData();

    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if(mSmartRefreshLayout!=null){
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.finishRefresh();
        }
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
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
    public void onHerInformation(BaseResponse<List<GetUserInfoBean>> response) {
        if(response.getCode()==0){
            if(response.getResult()!=null && getUserInfoBeanList!=null && hisInfoCommonAdapter!=null){
                if(pageIndex==0)
                    getUserInfoBeanList.clear();

                getUserInfoBeanList.addAll(response.getResult());
                hisInfoCommonAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void requestData() {
        super.requestData();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 10);
        mPresenter.requestHerInformation(map);
    }

    @Override
    public void OnClickDetails(String commentType, int showType,int id) {
        Bundle bundle = new Bundle();
        if("1".equals(commentType)){//首页的图片

            bundle.putInt("picId", id);
            //跳转到详情
            toOtherActivity(HomePictureDetailsActivity.class, bundle);
        }else{
            //0-图片，1-视频，2-资料
            if(showType==0){
                bundle.putInt("imgId", id);
                bundle.putInt("type", 1);
                toOtherActivity(DiscoveryPictureDetailsActivity.class, bundle);
            }else if(showType==1){

            }else{
                bundle.putInt("id", id);
                toOtherActivity(PetElvesDetailsActivity.class, bundle);
            }
        }
    }
}
