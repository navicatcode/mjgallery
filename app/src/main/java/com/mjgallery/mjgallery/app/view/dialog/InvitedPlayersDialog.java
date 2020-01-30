package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.BarUtils;
import com.mjgallery.mjgallery.app.utils.SystemUtil;
import com.mjgallery.mjgallery.mvp.model.bean.dialog.InvitedPlayersDialogBean;
import com.mjgallery.mjgallery.mvp.ui.adapter.dialog.InvitedPlayersDialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class InvitedPlayersDialog extends BaseDialog {
    RecyclerView mRecyclerView;
    List<InvitedPlayersDialogBean> invitedPlayersDialogBeans;
    InvitedPlayersDialogAdapter mInvitedPlayersDialogAdapter;
    IInvitedPlayersSelect mIInvitedPlayersSelect;

    public InvitedPlayersDialog(Activity activity, IInvitedPlayersSelect mIInvitedPlayersSelect) {
        super(activity, R.style.MyDialogStyleBottomTransparent);
        getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
        this.mIInvitedPlayersSelect = mIInvitedPlayersSelect;
        setCanceledOnTouchOutside(true);
    }

    private void initView() {
        setContentView(R.layout.dialog_invited_players_status);
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        invitedPlayersDialogBeans = new ArrayList<>();
        invitedPlayersDialogBeans.add(new InvitedPlayersDialogBean(ArmsUtils.getString(getContext(), R.string.all), 0, false));
        invitedPlayersDialogBeans.add(new InvitedPlayersDialogBean(ArmsUtils.getString(getContext(), R.string.to_day), 1, false));
        invitedPlayersDialogBeans.add(new InvitedPlayersDialogBean(ArmsUtils.getString(getContext(), R.string.to_7day), 2, false));
        invitedPlayersDialogBeans.add(new InvitedPlayersDialogBean(ArmsUtils.getString(getContext(), R.string.to_15day), 3, false));
        invitedPlayersDialogBeans.add(new InvitedPlayersDialogBean(ArmsUtils.getString(getContext(), R.string.to_30day), 4, false));
        mInvitedPlayersDialogAdapter = new InvitedPlayersDialogAdapter(R.layout.invited_players, invitedPlayersDialogBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mInvitedPlayersDialogAdapter);
        mInvitedPlayersDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mIInvitedPlayersSelect != null && invitedPlayersDialogBeans.size() > position) {
                    mIInvitedPlayersSelect.onClickListenerSelect(invitedPlayersDialogBeans.get(position));
                }
            }

        });
    }

    public void showBottomView(View view, int status) {
//小米的这个型号做特殊的处理
        if ("Xiaomi".equals(SystemUtil.getDeviceBrand()) && "MI 9".equals(SystemUtil.getSystemModel()) ) {
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("samsung".equals(SystemUtil.getDeviceBrand()) && "SM-A105G".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("HUAWEI".equals(SystemUtil.getDeviceBrand()) && "ELE-L29".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else if ("HUAWEI".equals(SystemUtil.getDeviceBrand()) && "MAR-LX2".equals(SystemUtil.getSystemModel())) {
            //三星的这个型号做特顺处理
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 18);
        } else {
            paddingTop(ArmsUtils.getScreenHeidth(getContext()) / 11);
        }
        for (int i = 0; i < invitedPlayersDialogBeans.size(); i++) {
            InvitedPlayersDialogBean invitedPlayersDialogBean = invitedPlayersDialogBeans.get(i);
            if (invitedPlayersDialogBean.getStatusType() == status) {
                invitedPlayersDialogBean.setSelect(true);
            } else {
                invitedPlayersDialogBean.setSelect(false);
            }
        }
        if (mInvitedPlayersDialogAdapter != null) {
            mInvitedPlayersDialogAdapter.notifyDataSetChanged();
        }
        show();
    }

    public interface IInvitedPlayersSelect {
        void onClickListenerSelect(InvitedPlayersDialogBean invitedPlayersDialogBean);

        void onClickListenerDismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mIInvitedPlayersSelect != null) {
            mIInvitedPlayersSelect.onClickListenerDismiss();
        }
    }
}
