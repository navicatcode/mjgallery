package com.mjgallery.mjgallery.app.view.dialog.download;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.view.dialog.BaseDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载apk的dialog
 *
 */
public class VersionDownloadDialog extends BaseDialog{

    ImageView ivDownloadProgress; //进度条
    TextView tvDownloadProgress;//进度文本
    ImageView ivRightImg;
    FrameLayout flJinduLayout;
    String apkURL;
    private OkHttpClient mOkHttpClient;
    private ProgressInfo mLastDownloadingInfo;
    private Handler mHandler;
    File file;
    String downloadPath=Environment.getExternalStorageDirectory() + "/Download/mjgallery";
    String apkName="majin.apk";
    public VersionDownloadDialog(@NonNull Activity context) {
        super(context);
        init();
        setCanceledOnTouchOutside(false);
    }

    private void init() {
        setContentView(R.layout.dialog_version_download);
        ivDownloadProgress=findViewById(R.id.ivDownloadProgress);
        tvDownloadProgress=findViewById(R.id.tvDownloadProgress);
        flJinduLayout=findViewById(R.id.flJinduLayout);
        ivRightImg=findViewById(R.id.ivRightImg);

        mHandler = new Handler();
        mOkHttpClient = ((BaseApplication)getContext().getApplicationContext()).getOkHttpClient();
    }


    public void show(String apkUrl) {
        this.apkURL=apkUrl;
        super.show();
        downloadStart();
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    /**
     * 点击开始下载资源
     */
    private void downloadStart() {
        //Okhttp/Retofit 下载监听，不断更新ui进度条
        ProgressManager.getInstance().addResponseListener(apkURL, getDownloadListener());

        //开起下载
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(apkURL)
                            .build();

                    Response response = mOkHttpClient.newCall(request).execute();

                    InputStream is = response.body().byteStream();
                    //创建目录，创建文件,
                    // 不知道为什么，当目录与文件都没有时，想连着创建会异常，暂时分开创建吧，反正不是天天更新。
                    file = new File(downloadPath);
                    boolean bool=true;
                    if(!file.exists()) {
                        bool=file.mkdir();
                        //file.createNewFile();
                    }
                    Log.e("dddd == ",""+bool);
                    file=new File(downloadPath+"/"+apkName);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    bis.close();
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(apkURL, e);
                }
            }
        }).start();
    }


    @NonNull
    private ProgressListener getDownloadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的下载进度用来展示,顺便展示下 id 的用法

                if (mLastDownloadingInfo == null) {
                    mLastDownloadingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                    mLastDownloadingInfo = progressInfo;
                }

                int progress = mLastDownloadingInfo.getPercent();
                setImageViewWidth(progress);
                if (mLastDownloadingInfo.isFinish()) {
                    //说明已经下载完成
                    installApk(file);
                    dismiss();

                }
            }

            @Override
            public void onError(long id, Exception e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArmsUtils.makeText(getContext(), ArmsUtils.getString(BaseApplication.getInstance(),R.string.dialog_version_update6));
                        dismiss();
                    }
                });
            }
        };
    }



    /**
     *根据下载的百分比，控制ivDownloadProgress进条度的宽度
     */
    public void setImageViewWidth(int jindu){
        //设定总宽度
        int sumWidth=flJinduLayout.getLayoutParams().width;

        LayoutParams layoutParams = ivDownloadProgress.getLayoutParams();
        if(jindu<100){
            layoutParams.width =(int)(((sumWidth*jindu)/100)+0.5);
            if(jindu>=90){//进度达到90时,把辅助圆角的图隐藏
                ivRightImg.setVisibility(View.GONE);
            }
        }else{
            layoutParams.width =sumWidth;
        }
        layoutParams.height = LayoutParams.MATCH_PARENT;;
        ivDownloadProgress.setLayoutParams(layoutParams);

        tvDownloadProgress.setText(jindu+"%");
    }

    /**
     * 启动安装
     * @param file
     */
    protected void installApk(File file) {
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("filePath == ",""+file.getPath());
            Uri apkUri = FileProvider.getUriForFile(getContext(), "com.mjgallery.mjgallery.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        getContext().startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
