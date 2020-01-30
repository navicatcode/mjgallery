package com.mjgallery.mjgallery.app.view.dialog.myvip;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.barnettwong.autofitcolortextview_library.AutoFitColorTextView;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;
import com.mjgallery.mjgallery.widget.UIImageView;


/**
 * 奖励领取成功
 */
public class MyVIPReceiveSuccessDialog extends BaseDialog {
    UIImageView ivMyVIPReceiveSuccessClose;
    AutoFitColorTextView tvMyVipReceive;
    TextView tvMyVipReceiveType;
    TextView tvMyVipReceiveTypeXiahuaXian;
    Button btnMyVipReceiveSuccess;
    Activity activity;
    public MyVIPReceiveSuccessDialog(Activity activity) {
        super(activity);
        initView();
        this.activity=activity;
        setCanceledOnTouchOutside(true);
    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.dialog_my_vip_receive_success);
        ivMyVIPReceiveSuccessClose = findViewById(R.id.ivMyVIPReceiveSuccessClose);
        tvMyVipReceiveType = findViewById(R.id.tvMyVipReceiveType);
        tvMyVipReceiveTypeXiahuaXian = findViewById(R.id.tvMyVipReceiveTypeXiahuaXian);
        tvMyVipReceive = findViewById(R.id.tvMyVipReceive);
        btnMyVipReceiveSuccess = findViewById(R.id.btnMyVipReceiveSuccess);
        ivMyVIPReceiveSuccessClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnMyVipReceiveSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 显示领取成功弹窗
     *
     * @param receiveTitle 奖励的名称
     * @param type         奖励的类型
     */
    public void showMyVIPReceiveSuccessDialogTop(String receiveTitle, int receiveType) {
        switch (receiveType) {
            case 1://vip奖励
                setTvMyVipReceiveText(ArmsUtils.getString(getContext(), R.string.vip_receive));
                break;
            case 2://签到奖励
                setTvMyVipReceiveText(ArmsUtils.getString(getContext(), R.string.sign_in_receive));
                break;
            case 3://累计签到奖励
                setTvMyVipReceiveText(ArmsUtils.getString(getContext(), R.string.cumulative_sign_in_receive));
                break;

        }
        if (tvMyVipReceive != null) {
            tvMyVipReceive.setText(receiveTitle);
        }
        btnMyVipReceiveSuccess.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（3s）");
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                int miao = (int) (millisUntilFinished / 1000);
                btnMyVipReceiveSuccess.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（" + miao + "s）");
            }

            public void onFinish() {
                dismiss();
            }
        }.start();
        showTop();
    }


    /**
     * 显示领取成功弹窗(活动中心专用)
     *
     * @param receiveTitle 奖励的名称
     * @param strType         奖励的类型
     */
    public void showPersonalSuccessDialogTop(String receiveTitle, String amountStr) {

        setTvMyVipReceiveText(TW2CN.getInstance(activity).toLocalStringS2T(receiveTitle));

        if (tvMyVipReceive != null) {
            tvMyVipReceive.setText(amountStr);
        }
        btnMyVipReceiveSuccess.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（3s）");
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                int miao = (int) (millisUntilFinished / 1000);
                btnMyVipReceiveSuccess.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（" + miao + "s）");
            }

            public void onFinish() {
                dismiss();
            }
        }.start();
        showTop();
    }

    /**
     * 设置类型奖励
     */
    private void setTvMyVipReceiveText(String receiveTextTitle) {
        if (tvMyVipReceiveType != null) {
            tvMyVipReceiveType.setText(receiveTextTitle);
        }
        if (tvMyVipReceiveTypeXiahuaXian != null) {
            tvMyVipReceiveTypeXiahuaXian.setText(receiveTextTitle);
        }
    }

}
