package com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerPetElvesComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.PetElvesContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.discoverdetailsdata.PetElvesInFoViewBean;
import com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata.PetElvesPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails.PetElvesInFoViewAdapter;
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
 * Description:资料列表界面
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class PetElvesActivity extends MJBaseActivity<PetElvesPresenter> implements PetElvesContract.View, PetElvesInFoViewAdapter.IPetElvesInFoViewClickListener {

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
    @BindView(R.id.btnQueDing)
    TextView btnQueDing;
    @BindView(R.id.rlQueDing)
    RelativeLayout rlQueDing;
    @BindView(R.id.ivRightHomeSearch)
    ImageView ivRightHomeSearch;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    int typeId;
    String title;
    int pageIndex = 0;
    List<PetElvesInFoViewBean.DataBean> petElvesInFoViewBeanList;
    PetElvesInFoViewAdapter petElvesInFoViewAdapter;
    String token;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPetElvesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pet_elves; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            typeId = getIntent().getIntExtra("typeId", 0);
            title=getIntent().getStringExtra("title");
        }
        tvTopTitle.setText(title);
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        ivRightHomeSearch.setVisibility(View.GONE);
        petElvesInFoViewBeanList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        petElvesInFoViewAdapter = new PetElvesInFoViewAdapter(R.layout.adapter_pet_elves_item, petElvesInFoViewBeanList, this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(petElvesInFoViewAdapter);
        token = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.TOKEN, "");
        //禁止越界拖动：
        mSmartRefreshLayout.setEnableOverScrollDrag(false);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getApplication()));
        petElvesInFoViewAdapter.setEmptyView(R.layout.adapter_pet_elves_item_error, mSmartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                getPetElvesData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                getPetElvesData();
            }
        });

        petElvesInFoViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (petElvesInFoViewBeanList.size() > position) {
                    int id = petElvesInFoViewBeanList.get(position).getId();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    toOtherActivity(PetElvesDetailsActivity.class, bundle);
                }

            }
        });
        getPetElvesData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.finishRefresh();
        dismissLoadingAnimationDialog();
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


    @OnClick({R.id.ivTopReturn, R.id.ivRightHomeSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivRightHomeSearch:
                break;
        }
    }


    private void getPetElvesData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        map.put("typeId", typeId);
        map.put("token", token);
        mPresenter.getPetElvesInFoViewList(map);

    }


    @Override
    protected void requestData() {

    }

    @Override
    public void onPetElvesInFoViewCollectionClickListener(int isCollection, int imgId) {
        onCollectionClick(isCollection, imgId);
    }

    @Override
    public void onPetElvesInFoViewList(PetElvesInFoViewBean beans) {
        if (pageIndex == 0) {
            petElvesInFoViewBeanList.clear();
        }
        if (beans.getData() != null && beans.getData().size() > 0) {
            petElvesInFoViewBeanList.addAll(beans.getData());
        }
        if (petElvesInFoViewAdapter != null) {
            petElvesInFoViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onAddCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                pageIndex = 0;
                getPetElvesData();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onCancelCollection(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                pageIndex = 0;
                getPetElvesData();
            }
            showMessage(baseResponse.getMessage());
        }
    }


    //收藏点击事件
    private void onCollectionClick(int isCollection, int imgId) {
        if (!isLoginStatus()) {
            return;
        }
//        subType", value = "发现的type，0-图片，1-视频，2-资料
        Map<String, Object> map = new HashMap<>();
        if (isCollection != 0) {
            if (!TextUtils.isEmpty(String.valueOf(imgId))) {
                map.put("picId", imgId);
                map.put("type", 2);
                map.put("token", token);
                mPresenter.getCancelCollection(map);
            }
        } else {
            if (!TextUtils.isEmpty(String.valueOf(imgId))) {
                map.put("tmpId", imgId);
                map.put("type", 2);
                map.put("subType", 2);
                map.put("token", token);
                mPresenter.getAddCollection(map);
            }
        }
    }

}
