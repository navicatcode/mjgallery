package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mjgallery.mjgallery.R;


/**
 * @author Joe
 * @time 2018/11/9 0009
 * @describe IOS底部选择对话框
 */
public class BottomSexSelectDialog implements View.OnClickListener {
    private Dialog bottomDialog;
    private Interface anInterface;

    /**
     * 弹出dialog
     *
     * @return this
     */
    public BottomSexSelectDialog showDialog(Activity activity) {
        if (bottomDialog == null && !activity.isFinishing()) {
            bottomDialog = new Dialog(activity, R.style.edit_AlertDialog_style);
            View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_content_sex, null);
            initView(contentView);

            bottomDialog.setContentView(contentView);

            //点外部不消失
            bottomDialog.setCanceledOnTouchOutside(false);
            bottomDialog.setCancelable(false);
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
            bottomDialog.show();
            //set the dialog fullscreen
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
//            params.width = activity.getResources().getDisplayMetrics().widthPixels
//                    - dp2px(activity, 16f);
//            params.bottomMargin = dp2px(activity, 10f);
//            contentView.setLayoutParams(params);
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
     * 初始化布局控件
     *
     * @param contentView
     */
    private void initView(View contentView) {
        //相机
        TextView man = contentView.findViewById(R.id.man);
        man.setOnClickListener(this);
        //相册
        TextView woman = contentView.findViewById(R.id.woman);
        woman.setOnClickListener(this);
        //取消
        TextView cancle = contentView.findViewById(R.id.cancle);
        cancle.setOnClickListener(this);

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
    public void onClick(View v) {
        if (anInterface == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.man:
                anInterface.selectedMan();
                cancle();
                break;
            case R.id.woman:
                anInterface.selectedWoman();
                cancle();
                break;
            case R.id.cancle:
                cancle();
                break;
            default:
                break;
        }
    }

    /**
     * 回调接口
     */
    public interface Interface {
        void selectedMan();

        void selectedWoman();
    }

    /**
     * 设置回调
     */
    public void setOnClickListener(Interface onClickListener) {
        anInterface = onClickListener;
    }


    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

}
