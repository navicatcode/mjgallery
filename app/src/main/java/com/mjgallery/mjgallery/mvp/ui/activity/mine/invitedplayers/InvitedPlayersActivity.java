package com.mjgallery.mjgallery.mvp.ui.activity.mine.invitedplayers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.view.dialog.InvitedPlayersDialog;
import com.mjgallery.mjgallery.app.view.dialog.InvitedPlayersDialog.IInvitedPlayersSelect;
import com.mjgallery.mjgallery.di.component.DaggerInvitedPlayersComponent;
import com.mjgallery.mjgallery.header.CustomRefreshHeader;
import com.mjgallery.mjgallery.mvp.contract.mine.InvitedPlayersContract;
import com.mjgallery.mjgallery.mvp.model.bean.InvitedPlayersBean;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.InvitedPlayersDialogBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.InvitedPlayersPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.InvitedPlayersAdapter;
import com.mjgallery.mjgallery.widget.UIImageView;
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
 * Description:邀请玩家
 * <p>
 * Created by MVPArmsTemplate on 10/21/2019 12:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class InvitedPlayersActivity extends MJBaseActivity<InvitedPlayersPresenter> implements
        InvitedPlayersContract.View, IInvitedPlayersSelect, InvitedPlayersAdapter.InvitedPlayersAdapterListener {

    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.llAll)
    LinearLayout llAll;
    @BindView(R.id.tvActivityNperStatus)
    TextView tvActivityNperStatus;
    @BindView(R.id.ivActivityNper)
    UIImageView ivActivityNper;
    @BindView(R.id.rlActivityNperStatus)
    RelativeLayout rlActivityNperStatus;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.etInvitedPlayersSearch)
    EditText etInvitedPlayersSearch;
    @BindView(R.id.btnInvitedPlayersSearch)
    Button btnInvitedPlayersSearch;
    InvitedPlayersDialog mInvitedPlayersDialog;
    List<InvitedPlayersBean> invitedPlayersBeans;
    InvitedPlayersAdapter mInvitedPlayersAdapter;
    int pageIndex = 0;
    int timeType = 0;
    String niceName;
    String searchNiceName;
    boolean isAttention = false;
    InvitedPlayersBean mInvitedPlayersBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInvitedPlayersComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_invited_players; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(getBaseContext(), R.string.invited_players));
        mInvitedPlayersDialog = new InvitedPlayersDialog(this, this);
        tvTopTitle.setTextColor(ArmsUtils.getColor(getBaseContext(), R.color.color_0AAFFA));
        if (getIntent() != null) {
            timeType = getIntent().getIntExtra("status", 0);
        }
        invitedPlayersBeans = new ArrayList<>();
        //判断当前当前状态
        if (timeType == 0) {
            tvActivityNperStatus.setText(ArmsUtils.getString(getBaseContext(), R.string.all));
        } else {
            tvActivityNperStatus.setText(ArmsUtils.getString(getBaseContext(), R.string.lottery_date_txt1));
        }
        initRecyclerView();
        initSmartRefreshLayout();
        requestMyInvite();
    }


    private void requestMyInvite() {
        if (isLoginStatus()) {
            getMyInvite();
        }
    }

    /**
     * 对SmartRefreshLayout进行初始化
     */
    private void initSmartRefreshLayout() {        //设置刷新控件头部布局
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(this));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.getmoredata);
                pageIndex = pageIndex + 1;
                requestMyInvite();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                playMusic(R.raw.viewrefresh);
                pageIndex = 0;
                requestMyInvite();
            }
        });
    }

    /**
     * 对RecyclerView进行初始化
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mInvitedPlayersAdapter = new InvitedPlayersAdapter(R.layout.adapter_my_attention_item, invitedPlayersBeans, this);
        mRecyclerView.setAdapter(mInvitedPlayersAdapter);
        mInvitedPlayersAdapter.setEmptyView(R.layout.my_attention_one, mSmartRefreshLayout);
    }


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
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
        dismissLoadingAnimationDialog();
        finish();
    }


    @OnClick({R.id.ivTopReturn, R.id.ivActivityNper, R.id.btnInvitedPlayersSearch, R.id.rlActivityNperStatus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.btnInvitedPlayersSearch:
                pageIndex = 0;
                niceName = etInvitedPlayersSearch.getText().toString().trim();
                getMyInvite();
                break;
            case R.id.ivActivityNper://做筛选处理,筛选的弹窗
            case R.id.rlActivityNperStatus:
                ivActivityNper.animate().setDuration(500).rotation(180).start();
                if (mInvitedPlayersDialog != null && !mInvitedPlayersDialog.isShowing()) {
                    mInvitedPlayersDialog.showBottomView(llAll, timeType);
                }
                break;
        }
    }

    @Override
    public void onClickListenerSelect(InvitedPlayersDialogBean invitedPlayersDialogBean) {
        if (invitedPlayersDialogBean != null) {
            timeType = invitedPlayersDialogBean.getStatusType();
            tvActivityNperStatus.setText(invitedPlayersDialogBean.getInvitedPlayersStatus());
            getMyInvite();
            invitedPlayersDialogDismiss();
        }

    }

    @Override
    public void onClickListenerDismiss() {
        invitedPlayersDialogDismiss();
        ivActivityNper.animate().setDuration(500).rotation(0).start();
    }

    private void invitedPlayersDialogDismiss() {
        if (mInvitedPlayersDialog != null && mInvitedPlayersDialog.isShowing()) {
            mInvitedPlayersDialog.dismiss();
        }
    }

    @Override
    protected void requestData() {

    }

    private void getMyInvite() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(getBaseContext()).getString(AppConstants.TOKEN, ""));
        map.put("timeType", timeType);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 15);
        if (!TextUtils.isEmpty(niceName)) {
            map.put("niceName", niceName);
        }
        mPresenter.getMyInvite(map);
    }

    @Override
    public void onMyInvite(List<InvitedPlayersBean> list) {
        if (pageIndex == 0) {
            invitedPlayersBeans.clear();
        }
        if (list != null && list.size() > 0) {

            invitedPlayersBeans.addAll(list);
        }
        if (mInvitedPlayersAdapter != null) {
            mInvitedPlayersAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                updateInvitedPlayersItem();
            }
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public void onConcernUser(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                updateInvitedPlayersItem();
            }
            showMessage(baseResponse.getMessage());
        }
    }


    /**
     * 更新当前的item状态
     */
    private void updateInvitedPlayersItem() {
        for (int i = 0; i < invitedPlayersBeans.size(); i++) {
            if (mInvitedPlayersBean != null) {
                InvitedPlayersBean invitedPlayersBean = invitedPlayersBeans.get(i);
                if (invitedPlayersBean != null && invitedPlayersBean.getUserId() == mInvitedPlayersBean.getUserId()) {
                    if (isAttention) {
                        invitedPlayersBean.setType(1);
                        invitedPlayersBeans.set(i, invitedPlayersBean);
                        if (mInvitedPlayersAdapter != null) {
                            mInvitedPlayersAdapter.notifyDataSetChanged();
                        }
                        return;
                    } else {
                        invitedPlayersBean.setType(3);
                        invitedPlayersBeans.set(i, invitedPlayersBean);
                        invitedPlayersBeans.set(i, invitedPlayersBean);
                        if (mInvitedPlayersAdapter != null) {
                            mInvitedPlayersAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void onInvitedPlayersAdapterAddAttentionClick(InvitedPlayersBean item) {
        isAttention = false;
        this.mInvitedPlayersBean = item;
        //添加关注
        onPetElvesDetailsFocusOnClick(item.getUserId());
    }

    @Override
    public void onInvitedPlayersAdapterCancelAttentionClick(InvitedPlayersBean item) {
        isAttention = true;
        this.mInvitedPlayersBean = item;
        // 取消关注
        onPetElvesDetailsFocusOnClick(item.getUserId());
    }

    @Override
    public void onInvitedSwitchClick(int userID) {
        if (isLoginStatus()) {
            Bundle bundle = new Bundle();
            bundle.putString("userId", String.valueOf(userID));
            isToOtherActivity(HisInformationActivity.class, bundle);
        }
    }

    /**
     * 关注或者取消关注的请求
     *
     * @param userId
     */
    private void onPetElvesDetailsFocusOnClick(int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        if (isAttention) {
            map.put("id", userId);
            mPresenter.getCancelUser(map);
        } else {
            map.put("userId", userId);
            mPresenter.getConcernUser(map);
        }
    }

}
