package com.mjgallery.mjgallery.app.view.dialog.myvip;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;
import com.mjgallery.mjgallery.widget.UIImageView;


/**
 * 奖励领取失败
 */
public class MyVIPReceiveErrorDialog extends BaseDialog {
    UIImageView ivMyVIPReceiveErrorClose;
    Button btnMyVipReceiveError;

    public MyVIPReceiveErrorDialog(Activity activity) {
        super(activity);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        setContentView(R.layout.dialog_my_vip_receive_error);
        ivMyVIPReceiveErrorClose = findViewById(R.id.ivMyVIPReceiveErrorClose);
        btnMyVipReceiveError = findViewById(R.id.btnMyVipReceiveError);
        ivMyVIPReceiveErrorClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnMyVipReceiveError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showMyVIPReceiveErrorDialogTop() {
        btnMyVipReceiveError.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（3s）");
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                int miao = (int) (millisUntilFinished / 1000);
                btnMyVipReceiveError.setText(ArmsUtils.getString(getContext(), R.string.my_vip_receive) + "（" + miao + "s）");
            }

            public void onFinish() {
                dismiss();
            }
        }.start();
        show();
    }


}
