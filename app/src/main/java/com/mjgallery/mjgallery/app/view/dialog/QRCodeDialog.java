package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.OnClick;

import static com.mjgallery.mjgallery.app.glide.GlideUtil.loadCircleImage;

public class QRCodeDialog extends BaseDialog implements View.OnClickListener {
    TextView tvInvitationCode;
    ImageView ivXiaZai;
    RelativeLayout SaveLocal;
    TextView tvCopyInvitationCode;
    ImageView ivMineUserImg;
    ImageView ivQRCode;
    String inviteUrl;
    String inviteCode;
    Bitmap mBitmap;
    LinearLayout llAll;

    ICopyInvitationCode iCopyInvitationCode;

    public QRCodeDialog(Activity activity, ICopyInvitationCode iCopyInvitationCode) {
        super(activity);
        this.iCopyInvitationCode = iCopyInvitationCode;
        setCanceledOnTouchOutside(true);
        init();
    }


    private void init() {
        setContentView(R.layout.dialog_qr_code);
        tvInvitationCode = findViewById(R.id.tvInvitationCode);
        ivQRCode = findViewById(R.id.ivQRCode);
        ivXiaZai = findViewById(R.id.ivXiaZai);
        SaveLocal = findViewById(R.id.SaveLocal);
        llAll = findViewById(R.id.llAll);
        tvCopyInvitationCode = findViewById(R.id.tvCopyInvitationCode);
        ivMineUserImg = findViewById(R.id.ivMineUserImg);
        tvCopyInvitationCode.setOnClickListener(this);
        SaveLocal.setOnClickListener(this);
        GlideUtil.loadCircleImage(ivMineUserImg, R.drawable.mine_user_normal_img);
    }

    @OnClick({R.id.tvInvitationCode, R.id.ivXiaZai, R.id.SaveLocal, R.id.tvCopyInvitationCode, R.id.ivMineUserImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvInvitationCode:
                break;
            case R.id.ivXiaZai:
                break;
            case R.id.SaveLocal:
                if (iCopyInvitationCode != null) {
                    iCopyInvitationCode.onSaveCode(mBitmap);
                }
                break;
            case R.id.tvCopyInvitationCode:
                if (iCopyInvitationCode != null) {
                    iCopyInvitationCode.onCopyInvitationCode(tvInvitationCode.getText().toString());
                }

                break;
            case R.id.ivMineUserImg:
                break;
        }
    }


    public void show(String inviteUrl, String inviteCode, String userUrl) {
        this.inviteUrl = inviteUrl;
        this.inviteCode = inviteCode;
        tvInvitationCode.setText(inviteCode);
        loadCircleImage(ivMineUserImg, userUrl, R.drawable.mine_user_normal_img);
        mBitmap = CodeUtils.createImage(inviteUrl, 200, 200, null);
        ivQRCode.setImageBitmap(mBitmap);
        super.show();
    }

    public interface ICopyInvitationCode {
        void onCopyInvitationCode(String copyName);

        void onSaveCode(Bitmap bitmap);
    }
}
