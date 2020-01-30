package com.mjgallery.mjgallery.app.view.dialog.home;


import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;

/**
 * 首页详情评论列表
 */
public class HomeDetailsCommentsDialog extends BaseDialog implements View.OnClickListener {
    IDiscoveryDetailsComments iDiscoveryDetailsComments;

    LinearLayout llCopy;

    LinearLayout llDelete;

    LinearLayout llAllTwo;

    LinearLayout llAll;

    String copyText;
    int type;        //评论类型(3-一级评论，4-二级评论)
    int commentId;   //评论id

    int subCommentId;//子评论id


    public HomeDetailsCommentsDialog(Activity activity, IDiscoveryDetailsComments mIDiscoveryDetailsComments) {
        super(activity);
        this.iDiscoveryDetailsComments = mIDiscoveryDetailsComments;
        setCanceledOnTouchOutside(true);
        init();
    }



    /**
     * 显示弹窗
     *
     * @param type         //评论类型(3-一级评论，4-二级评论)
     * @param commentId    //评论id
     * @param subCommentId //子评论id
     */
    public void showDiscoveryDetailsCommentsDialog(String copyText,int type, int commentId, int subCommentId) {
        this.type = type;
        this.copyText = copyText;
        this.commentId = commentId;
        this.subCommentId = subCommentId;
        show();
    }
    /**
     * 初始化
     */
    private void init() {
        setContentView(R.layout.dialog_discovery_details_comments);
        llCopy = findViewById(R.id.llCopy);
        llDelete = findViewById(R.id.llDelete);
        llAllTwo = findViewById(R.id.llAllTwo);
        llAll = findViewById(R.id.llAll);
        llDelete.setOnClickListener(this);
        llAll.setOnClickListener(this);
        llAllTwo.setOnClickListener(this);
        llCopy.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llCopy:
                if (TextUtils.isEmpty(copyText)) {
                    ArmsUtils.getString(BaseApplication.getInstance(),R.string.copy_error);
                    return;
                }
                if (iDiscoveryDetailsComments != null) {
                    iDiscoveryDetailsComments.onDiscoveryDetailsCommentsCopy(copyText);
                }
                break;
            case R.id.llDelete:
                if (iDiscoveryDetailsComments != null) {
                    iDiscoveryDetailsComments.onDiscoveryDetailsCommentsDelete(type, commentId, subCommentId);
                }
                break;
            case R.id.llAllTwo:

                break;
            case R.id.llAll:
                dismiss();
                break;
        }
    }


    public interface IDiscoveryDetailsComments {
        void onDiscoveryDetailsCommentsDelete(int type, int commentId, int subCommentId);

        void onDiscoveryDetailsCommentsCopy(String stringText);


    }
}
