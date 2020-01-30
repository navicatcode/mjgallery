package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.ui.activity.MainActivity;


/**
 * @author Joe
 * @time 2018/11/9 0009
 * @describe IOS底部选择对话框
 */
public class SharetSelectDialog extends BaseDialog implements View.OnClickListener {

    LinearLayout qqShare;

    LinearLayout weixiShare;

    LinearLayout penyouShare;

    LinearLayout xinlanShare;

    LinearLayout copyShare;
    private Dialog bottomDialog;
    private Interface anInterface;
    MainActivity activity;


    public SharetSelectDialog(MainActivity activity){
        this.activity=activity;
    }

    /**
     * 弹出dialog
     *
     * @return this
     */
    public SharetSelectDialog showDialog() {
        if (bottomDialog == null && !activity.isFinishing()) {
            bottomDialog = new Dialog(activity, R.style.edit_AlertDialog_style);
            View contentView = LayoutInflater.from(activity).inflate(R.layout.go_share_app, null);

            weixiShare=(LinearLayout)contentView.findViewById(R.id.weixi_share);
            penyouShare=(LinearLayout)contentView.findViewById(R.id.penyou_share);
            weixiShare.setOnClickListener(this);
            penyouShare.setOnClickListener(this);
            bottomDialog.setContentView(contentView);

            bottomDialog.setCancelable(false);
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
            bottomDialog.setCanceledOnTouchOutside(true);
            bottomDialog.show();

            Window win = bottomDialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
        } else {
            bottomDialog.show();
        }

        return this;
    }



    /**
     * dismiss dialog
     */
    private void cancle() {
        if (bottomDialog == null) {
            return;
        }
        bottomDialog.dismiss();

//        bottomDialog = null;
        anInterface = null;
    }

    @Override
    public void onClick(View view) {
        this.cancle();
        switch (view.getId()) {
            case R.id.qq_share:
                break;
//            case R.id.weixi_share:
//                activity.wechatShare(0);
//                break;
//            case R.id.penyou_share:
//                activity.wechatShare(1);1
//                break;
            case R.id.xinlan_share:
                break;
            case R.id.copy_share:
                break;
        }

    }


    /**
     * 回调接口
     */
    public interface Interface {
        void selectedCamera();

        void selectedAlbum();
    }


    /**
     * 设置回调
     */
    public void setOnClickListener(Interface onClickListener) {
        anInterface = onClickListener;
    }



}
