package com.mjgallery.mjgallery.app.view.dialog.signinreward;


import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;

/**
 * 签到查看奖励详
 */
public class SignInRewardDetailsDialog extends BaseDialog {
    TextView tvSignInRewardDetailsTitle;
    TextView tvSignInRewardDetailsCount;
    TextView tvSignInRewardDetailsMessage;
    LinearLayout llSignInRewardDetails;
    ImageView ivSignInRewardDetails;
    TextView tvSignInRewardDetailsClose;

    public SignInRewardDetailsDialog(Activity activity) {
        super(activity);
        initView();
        setCanceledOnTouchOutside(true);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        setContentView(R.layout.dialog_sign_in_reward_details);
        tvSignInRewardDetailsTitle = findViewById(R.id.tvSignInRewardDetailsTitle);
        tvSignInRewardDetailsCount = findViewById(R.id.tvSignInRewardDetailsCount);
        tvSignInRewardDetailsMessage = findViewById(R.id.tvSignInRewardDetailsMessage);
        llSignInRewardDetails = findViewById(R.id.llSignInRewardDetails);
        ivSignInRewardDetails = findViewById(R.id.ivSignInRewardDetails);
        tvSignInRewardDetailsClose = findViewById(R.id.tvSignInRewardDetailsClose);
        tvSignInRewardDetailsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 显示当前弹窗
     *
     * @param type
     * @param signInRewardDetailsTitle
     * @param signInRewardDetailsImg
     */
    public void showSignInRewardDetailsDialog(int type, int countDay, String signInRewardDetailsTitle, String signInRewardDetailsImg) {
        switch (type) {
            case 1://今日奖励
                settTvSignInRewardDetailsTitleText(ArmsUtils.getString(getContext(), R.string.sign_in_reward_details_title_one));
                tvSignInRewardDetailsCount.setVisibility(View.GONE);
                tvSignInRewardDetailsMessage.setText(signInRewardDetailsTitle);
                break;
            case 2://累计签到奖励
                settTvSignInRewardDetailsTitleText(ArmsUtils.getString(getContext(), R.string.sign_in_reward_details_title_two));
                tvSignInRewardDetailsCount.setVisibility(View.VISIBLE);
                tvSignInRewardDetailsMessage.setText(signInRewardDetailsTitle);
                StringBuffer stringBufferSignInRewardDetails = new StringBuffer();
                stringBufferSignInRewardDetails.append(ArmsUtils.getString(getContext(), R.string.sign_in_reward_details_01));
                stringBufferSignInRewardDetails.append(countDay);
                stringBufferSignInRewardDetails.append(ArmsUtils.getString(getContext(), R.string.sign_in_reward_details_02));
                tvSignInRewardDetailsCount.setText(stringBufferSignInRewardDetails.toString());
                break;
        }
//        if (!TextUtils.isEmpty(signInRewardDetailsImg) && ivSignInRewardDetails != null) {
//            GlideUtil.imageNormalLoader(ivSignInRewardDetails, signInRewardDetailsImg, R.drawable.icon_loading);
//        }
        show();
    }

    /**
     * 设置提示标题
     *
     * @param signInRewardDetailsTitle
     */
    private void settTvSignInRewardDetailsTitleText(String signInRewardDetailsTitle) {
        if (tvSignInRewardDetailsTitle != null) {
            tvSignInRewardDetailsTitle.setText(signInRewardDetailsTitle);
        }
    }
}
