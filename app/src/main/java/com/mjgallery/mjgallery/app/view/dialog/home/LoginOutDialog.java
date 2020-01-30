package com.mjgallery.mjgallery.app.view.dialog.home;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;

/**
 * 登陆的弹窗
 */
public class LoginOutDialog extends BaseDialog implements View.OnClickListener {
    TextView tvLoginExit;
    TextView tvLoginConfirm;
    LinearLayout llAllLogin;
    ILoginOutConfirm mILoginOutConfirm;

    public LoginOutDialog(Activity activity, ILoginOutConfirm mILoginOutConfirm) {
        super(activity);
        this.mILoginOutConfirm = mILoginOutConfirm;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_login_out);
        llAllLogin = findViewById(R.id.llAllLogin);
        tvLoginConfirm = findViewById(R.id.tvLoginConfirm);
        tvLoginExit = findViewById(R.id.tvLoginExit);
        tvLoginExit.setOnClickListener(this);
        tvLoginConfirm.setOnClickListener(this);
        llAllLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLoginExit:
                dismiss();
                break;
            case R.id.tvLoginConfirm:
                if (mILoginOutConfirm != null) mILoginOutConfirm.onLoginOutConfirm();
                break;
            case R.id.llAllLogin:
                break;
        }
    }

    public interface ILoginOutConfirm {
        void onLoginOutConfirm();
    }
}
