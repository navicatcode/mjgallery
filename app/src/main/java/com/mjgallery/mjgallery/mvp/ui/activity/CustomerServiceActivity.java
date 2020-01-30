package com.mjgallery.mjgallery.mvp.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.AndroidBug5497Workaround;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryRecordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系在线客服
 */
public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.ivTopTitle)
    ImageView ivTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.ivTopRight)
    TextView ivTopRight;
    @BindView(R.id.ivTopRightImg)
    ImageView ivTopRightImg;
    @BindView(R.id.btnQueDing)
    TextView btnQueDing;
    @BindView(R.id.rlQueDing)
    RelativeLayout rlQueDing;
    @BindView(R.id.ivRightHomeSearch)
    ImageView ivRightHomeSearch;
    @BindView(R.id.llTopReturnAll)
    RelativeLayout llTopReturnAll;
    @BindView(R.id.webViewSession)
    WebView webViewSession;
    WebSettings mWebSettings;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 101;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_customer_service;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.contact_service));
        tvTopTitle.setTextColor(getResources().getColor(R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        mWebSettings = webViewSession.getSettings();
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebSettings.setJavaScriptEnabled(true);//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebSettings.setSupportZoom(true);//是否可以缩放，默认true
        mWebSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mWebSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebSettings.setAppCacheEnabled(true);//是否使用缓存
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setDomStorageEnabled(true);//开启本地DOM存储
        mWebSettings.setLoadsImagesAutomatically(true); // 加载图片
        mWebSettings.setMediaPlaybackRequiresUserGesture(false);//播放音频，多媒体需要用户手动？设置为false为可自动播放

        webViewSession.loadUrl("https://chat.liveneed.net/chat/Hotline/channel.jsp?cid=5048061&cnfid=40577&j=6065262798&lan=zh_TW&subject=%E5%92%A8%E8%AF%A2&prechatinfoexist=1&s=1");
        //设置不用系统浏览器打开,直接显示在当前Webview
        webViewSession.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        AndroidBug5497Workaround.assistActivity(this);

        webViewSession.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                CustomerServiceActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                CustomerServiceActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                Log.d(TAG, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                CustomerServiceActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                CustomerServiceActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                        Log.e(TAG, "onActivityResultAboveL: " + results[i].getPath());
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
                Log.e(TAG, "onActivityResultAboveL: " + results.length);
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    @OnClick(R.id.ivTopReturn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webViewSession != null) {
            webViewSession.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webViewSession.clearHistory();

            ((ViewGroup) webViewSession.getParent()).removeView(webViewSession);
            webViewSession.destroy();
            webViewSession = null;
        }
        super.onDestroy();
    }
}
