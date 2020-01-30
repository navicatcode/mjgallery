package com.mjgallery.mjgallery.app.view.dialog.download;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;

import java.util.List;

/**
 * 提示要版本更新dialog
 */
public class VersionUpdateDialog extends BaseDialog implements View.OnClickListener {

    TextView tvUpdateContent;
    TextView tvSelectUpdate;
    TextView tvSelectNoUpdate;
    TextView tvUpdateTitle;
    LinearLayout llItem1,llItem2;
    UpdateDownloadListener updateDownloadListener;
    boolean isClickBool=false;//为true时，说明是点击了立即更新
    boolean isClickBool2=false; //为true时，说明是点击了空白处或取消更新
    int forceUpdate;
    String apkUrl;

    public VersionUpdateDialog(@NonNull Activity context,UpdateDownloadListener updateDownloadListener) {
        super(context);
        this.updateDownloadListener=updateDownloadListener;
        setCanceledOnTouchOutside(true);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_version_update);

        tvUpdateContent = findViewById(R.id.tvUpdateContent);
        tvSelectUpdate = findViewById(R.id.tvSelectUpdate);
        tvSelectNoUpdate = findViewById(R.id.tvSelectNoUpdate);
        tvUpdateTitle = findViewById(R.id.tvUpdateTitle);
        llItem1 = findViewById(R.id.llItem1);
        llItem2 = findViewById(R.id.llItem2);

        llItem1.setOnClickListener(this);
        llItem2.setOnClickListener(this);
        tvSelectNoUpdate.setOnClickListener(this);
        tvSelectUpdate.setOnClickListener(this);

        //tvUpdateTitle.setTypeface(Utils.setTextTypeface(2));
    }


    public void show(VersionUpdateBean versionUpdateBean) {
        isClickBool=false;
        isClickBool2=false;
        forceUpdate=versionUpdateBean.getForceUpdate();
        apkUrl=versionUpdateBean.getUrl();
        List<String> versionDescs=versionUpdateBean.getVersionDesc();
        if(versionDescs ==null || versionDescs.size()<=0)
            return;

        StringBuffer sb=new StringBuffer();
        for(int i=0;i<versionDescs.size();i++){
            if(i<versionDescs.size()-1){
                sb.append(versionDescs.get(i)+"\n");
            }else{
                sb.append(versionDescs.get(i));
            }
        }
        tvUpdateContent.setText(sb.toString());
        super.show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSelectUpdate:
                isClickBool=true;
                dismiss();
                break;
            case R.id.tvSelectNoUpdate:
            case R.id.llItem1:
                isClickBool2=true;
                dismiss();
                break;
            case R.id.llItem2:

                break;


        }
    }

    @Override
    public void dismiss() {
        if(forceUpdate==1 || isClickBool){
            if(isClickBool){
                super.dismiss();
                updateDownloadListener.updateDownload(apkUrl);
                return;
            }
            //只有点击空白处或取消更新时才有文本提示，点返回键没有
            if(isClickBool2){
                isClickBool2=false;
                ArmsUtils.makeText(getContext(),"此次更新对您非常重要，无法取消！");
            }
            return;
        }else
            super.dismiss();
    }

    public interface UpdateDownloadListener{
         void updateDownload(String apkUrl);
    }
}
