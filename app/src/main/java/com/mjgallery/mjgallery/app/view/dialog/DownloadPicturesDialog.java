package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;

/**
 * 下载图片弹窗
 */
public class DownloadPicturesDialog extends BaseDialog {
    public DownloadPicturesDialog(Activity activity) {
        super(activity);
        init();
    }


    private void init() {
        setContentView(R.layout.dialog_download_pictures);
    }


    //下面这个构造涵数是给清除缓存时用的
    public DownloadPicturesDialog(Activity activity,String text){
        super(activity);
        setContentView(R.layout.dialog_download_pictures);
        ImageView ivDownImg=findViewById(R.id.ivDownImg);
        TextView tvTextContent=findViewById(R.id.tvTextContent);
        ivDownImg.setImageResource(R.drawable.clear_cache_show);
        tvTextContent.setText(text);
        tvTextContent.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_323232));
    };
}
