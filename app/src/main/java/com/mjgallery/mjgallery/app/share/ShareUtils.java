package com.mjgallery.mjgallery.app.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.retrofiturlmanage.NetWorkManager;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.ResponseBody;

public class ShareUtils {

    /**
     * 分享
     */
    public static void doShare(Activity activity, String shareUrl,boolean typeBool) {
        AppConstants.isShare = true;
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == null) {
                            //根据key来区分自定义按钮的类型，并进行对应的操作
                            if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                                ArmsUtils.makeText(activity.getApplicationContext(), "add button success");
                            }
                        } else {
                            UMImage image = new UMImage(activity.getApplicationContext(), R.mipmap.ic_launcher);
                            UMWeb web = new UMWeb(shareUrl);
                            web.setTitle(ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_name));//标题
                            web.setThumb(image);  //缩略图
                            switch (share_media) {
                                case WEIXIN:
                                case QQ:
                                case QZONE:
                                case WEIXIN_CIRCLE:
                                    web.setDescription(ArmsUtils.getString(BaseApplication.getInstance(),R.string.fenxian_content));//描述
                                    new ShareAction(activity)
                                            .setPlatform(share_media)
                                            .setCallback(new UMShareListener() {
                                                @Override
                                                public void onStart(SHARE_MEDIA share_media) {
                                                    Log.e("onStart", share_media.toString());

                                                }

                                                @Override
                                                public void onResult(SHARE_MEDIA share_media) {
                                                    Log.e("onResult", share_media.toString());
                                                }

                                                @Override
                                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                                    Log.e("onError", share_media.toString());
                                                    Log.e("onError", "throwable=====" + throwable.toString());
                                                }

                                                @Override
                                                public void onCancel(SHARE_MEDIA share_media) {
                                                    Log.e("onCancel", share_media.toString());
                                                }
                                            })
                                            .withMedia(web)
                                            .share();
                                    //记录分享文章次数
                                    new ShareUtils().requestFenXianJiLu(activity,typeBool,share_media);
                                    break;
                            }
                        }
                    }
                })//面板点击监听器
                .open();
    }


    /**
     * @param smsBody
     */
    private static void sendSMS(String smsBody, Activity activity) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        activity.startActivity(intent);

    }

    /**
     *分享文章记录请求 begin-------------------------------------------------------------------------------
     */
    public void requestFenXianJiLu(Context context,boolean typeBool,SHARE_MEDIA share_media){
        if(typeBool){//判断是否为分享文章再进下一步

            switch (share_media) {
                case WEIXIN:
                case WEIXIN_CIRCLE:
                    if (!ShareFileUtils.isAppInstall(BaseApplication.getInstance(), "com.tencent.mm")) {
                        Log.e("ShareUtils：","未安装微信，记录分享失败");
                        return;
                    }
                    break;
                case QQ:
                case QZONE:
                    if (!ShareFileUtils.isAppInstall(context, "com.tencent.mobileqq")) {
                        Log.e("ShareUtils：","未安装QQ，记录分享失败");
                        return;
                    }
                    break;
            }

            //发送请求
            RetrofitUrlManager.getInstance().putDomain("fenxiao", AppConstants.APP_DOMAIN);
            String token=PreferenceUtil.getInstance(context).getString(AppConstants.TOKEN, "");
            NetWorkManager
                    .getInstance()
                    .getCommonService()
                    .getFenXiaoJiLu(token)
                    .compose(this.<ResponseBody>getDefaultTransformer())
                    .subscribe(responseFenXianJiLu());
        }
    }

    private <T> ObservableTransformer<T, T> getDefaultTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                //mProgressDialog.show();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                //mProgressDialog.dismiss();
                            }
                        });
            }
        };
    }

    /**
     * 分享文章记录的结果回调
     */
    private Observer<ResponseBody> responseFenXianJiLu() {
        return new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody response) {
                try {
                    String string = response.string();
                    JsonObject jsonObject = (JsonObject) new JsonParser().parse(string);

                    int code=jsonObject.get("code").getAsInt();
                    if(code==0){
                        Log.e("ShareUtils：","记录分享成功+1");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                //showResult(throwable.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    //分享文章请求 end-------------------------------------------------------------------------------
}
