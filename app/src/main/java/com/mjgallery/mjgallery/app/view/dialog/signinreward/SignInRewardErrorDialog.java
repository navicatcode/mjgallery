package com.mjgallery.mjgallery.app.view.dialog.signinreward;


import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;

/**
 * 签到查看奖励详
 */
public class SignInRewardErrorDialog extends BaseDialog {


    TextView tvSignInRewardDetailsTitle;
    TextView tvSignInRewardErrorTitle;
    TextView tvSignInRewardErrorTitleTwo;
    LinearLayout llSignInRewardDetails;
    TextView tvSignInRewardDetailsClose;

    public SignInRewardErrorDialog(Activity activity) {
        super(activity);
        initView();
        setCanceledOnTouchOutside(true);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        setContentView(R.layout.dialog_sign_in_reward_error);
        tvSignInRewardDetailsTitle = findViewById(R.id.tvSignInRewardDetailsTitle);
        tvSignInRewardErrorTitle = findViewById(R.id.tvSignInRewardErrorTitle);
        tvSignInRewardErrorTitleTwo = findViewById(R.id.tvSignInRewardErrorTitleTwo);
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
     * @param stringError
     */
    public void showSignInRewardErrorDialog(String stringError) {
        StringBuffer stringBufferSignInRewardDetails = new StringBuffer();
        stringBufferSignInRewardDetails.append(ArmsUtils.getString(getContext(), R.string.sign_in_reward_error_01));
        stringBufferSignInRewardDetails.append(stringError);
        stringBufferSignInRewardDetails.append("）");
        tvSignInRewardErrorTitleTwo.setText(stringBufferSignInRewardDetails.toString());

        show();
    }

}
