package com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.retrofiturlmanage.NetWorkManager;
import com.mjgallery.mjgallery.app.utils.ImagePictureSelectUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.QRCodeDecoder;
import com.mjgallery.mjgallery.app.utils.WithdrawResultInterFace;
import com.mjgallery.mjgallery.di.component.DaggerWithdrawPayComponent;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawPayContract;
import com.mjgallery.mjgallery.mvp.presenter.withdraw.WithdrawPayPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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

import static android.app.Activity.RESULT_OK;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:设置支付方式
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawPayFragment extends MJBaseFragment<WithdrawPayPresenter> implements WithdrawPayContract.View {
    //以当前ui设计的位置，创建对应位置的两个数组，[0]元素表示左边，[1]元素表示中间，[3]元素右边
    //分别对应内容与图片
    int[] recordImgList = {R.drawable.btn_yinhanka_mohu_bg, R.drawable.btn_zhifubao_mohu_bg, R.drawable.btn_weixi_mohu_bg};
    int[] recordTxtList = {R.string.bank_card, R.string.pay_treasure, R.string.wechat};

    @BindView(R.id.tvSelectPay)
    TextView tvSelectPay;
    @BindView(R.id.ivYinHangKa)
    TextView ivYinHangKa;
    @BindView(R.id.tvYinHangKa)
    TextView tvYinHangKa;
    @BindView(R.id.llYinHangKa)
    LinearLayout llYinHangKa;
    @BindView(R.id.ivZhiFuBao)
    TextView ivZhiFuBao;
    @BindView(R.id.tvZhiFuBao)
    TextView tvZhiFuBao;
    @BindView(R.id.llZhiFuBao)
    LinearLayout llZhiFuBao;
    @BindView(R.id.ivWeiXi)
    TextView ivWeiXi;
    @BindView(R.id.tvWeiXi)
    TextView tvWeiXi;
    @BindView(R.id.llWeiXi)
    LinearLayout llWeiXi;
    @BindView(R.id.llSelectPay)
    LinearLayout llSelectPay;
    @BindView(R.id.codeShowImg)
    ImageView codeShowImg;
    @BindView(R.id.llCamera)
    LinearLayout llCamera;
    @BindView(R.id.tvWithdrawPayTitle)
    TextView tvWithdrawPayTitle;
    @BindView(R.id.llHint)
    LinearLayout llHint;
    @BindView(R.id.llHint2)
    LinearLayout llHint2;
    @BindView(R.id.tvSettlementNext)
    TextView tvSettlementNext;
    @BindView(R.id.evYinHangKaName)
    EditText evYinHangKaName;
    @BindView(R.id.evYinHangKaCard)
    EditText evYinHangKaCard;
    ImagePictureSelectUtils mImagePictureSelectUtils;
    private List<LocalMedia> selectList = new ArrayList<>();
    File fileImg;
    public static WithdrawResultInterFace wrResultInfter;
    public static WithdrawPayFragment newInstance(WithdrawResultInterFace wrResultInfter) {
        WithdrawPayFragment fragment = new WithdrawPayFragment();
        WithdrawPayFragment.wrResultInfter=wrResultInfter;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawPayComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_pay, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        selectList.clear();
        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mImagePictureSelectUtils.setMaxSelectNum(1);
        mImagePictureSelectUtils.setCircleDimmedLayer(false);
        mImagePictureSelectUtils.setEnableCrop(false);
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getContext(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    /**
     * 右边与中间切换动画
     */
    private void setAnimatorRight() {
        ObjectAnimator translation01 = ObjectAnimator.ofFloat(llWeiXi, "translationX", 0, -200);
        ObjectAnimator translation02 = ObjectAnimator.ofFloat(llZhiFuBao, "translationX", 0, 200);
        ObjectAnimator translation03 = ObjectAnimator.ofFloat(llWeiXi, "translationX", -200, 0);
        ObjectAnimator translation04 = ObjectAnimator.ofFloat(llZhiFuBao, "translationX", 200, 0);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translation01, translation02, translation03, translation04); //设置动画
        animatorSet.setDuration(500);  //设置动画时间
        animatorSet.start(); //启动
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //这里是切换时的触发
                Log.e("提现方式", "切换");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 左边与中间切换动画
     */
    private void setAnimatorLeft() {
        ObjectAnimator translation01 = ObjectAnimator.ofFloat(llZhiFuBao, "translationX", 0, -200);
        ObjectAnimator translation02 = ObjectAnimator.ofFloat(llYinHangKa, "translationX", 0, 200);
        ObjectAnimator translation03 = ObjectAnimator.ofFloat(llZhiFuBao, "translationX", -200, 0);
        ObjectAnimator translation04 = ObjectAnimator.ofFloat(llYinHangKa, "translationX", 200, 0);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translation01, translation02, translation03, translation04); //设置动画
        animatorSet.setDuration(500);  //设置动画时间
        animatorSet.start(); //启动
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //这里是切换时的触发
                Log.e("提现方式", "切换");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @OnClick({R.id.tvSettlementNext, R.id.llYinHangKa, R.id.llWeiXi, R.id.llCamera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llCamera:
                //上传支付宝或微信的收款二维码
                mImagePictureSelectUtils.initFragmentPictureSelector(WithdrawPayFragment.this,
                        PictureMimeType.ofImage());
                break;
            case R.id.tvSettlementNext://提交完成
                requestData();
                break;
            case R.id.llYinHangKa://银行卡点击
                showContentUI(false);
                setAnimatorLeft();
                break;
            case R.id.llWeiXi://微信点击
                showContentUI(true);
                setAnimatorRight();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    boolean isVideo = false;
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        if (media.getCompressPath() != null) {
                            fileImg = new File(media.getCompressPath());
                            checkQRCode(fileToBitmap(fileImg.getPath()), "该图片不是收款码");
                        }

                    }
                    break;
            }
        }
    }

    /**
     * 显示切换后的提现方式ui
     *
     * @param type 切换方式，true为右边与中间切换，false为左边与中间切换
     */
    public void showContentUI(boolean type) {

        int imgRecord = 0;
        int txtRecord = 0;
        if (type) {
            //右边与中间切换
            imgRecord = recordImgList[1];
            txtRecord = recordTxtList[1];
            recordImgList[1] = recordImgList[2];
            recordTxtList[1] = recordTxtList[2];
            recordImgList[2] = imgRecord;
            recordTxtList[2] = txtRecord;

        } else {
            //左边与中间切换
            imgRecord = recordImgList[1];
            txtRecord = recordTxtList[1];
            recordImgList[1] = recordImgList[0];
            recordTxtList[1] = recordTxtList[0];
            recordImgList[0] = imgRecord;
            recordTxtList[0] = txtRecord;
        }

        //展示最终结果
        ivYinHangKa.setBackgroundResource(recordImgList[0]);
        tvYinHangKa.setText(recordTxtList[0]);

        ivZhiFuBao.setBackgroundResource(recordImgList[1]);
        tvZhiFuBao.setText(recordTxtList[1]);

        ivWeiXi.setBackgroundResource(recordImgList[2]);
        tvWeiXi.setText(recordTxtList[2]);

        if (recordTxtList[1] == R.string.bank_card) {
            //如果中间的是银行卡
            llCamera.setVisibility(View.GONE);
            tvWithdrawPayTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdraw_pay2));
            return;
        }

        if(recordTxtList[1] == R.string.pay_treasure){
            //如果是支付宝
            tvWithdrawPayTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdraw_pay));
        }else{
            //如果是微信
            tvWithdrawPayTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdraw_pay1));
        }
        llCamera.setVisibility(View.VISIBLE);
    }


//    /**
//     * 获取TakePhoto实例
//     *
//     * @return
//     */
//    public TakePhoto getTakePhoto() {
//        if (mTakePhoto == null) {
//            mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
//        }
//        return mTakePhoto;
//    }
//
//    /**
//     * 选择完图片后的回调
//     * @param result
//     */
//    @Override
//    public void takeSuccess(TResult result) {
//        List<TImage> resultImages = result.getImages();
//        if (resultImages != null && resultImages.size() > 0) {
//            TImage image = resultImages.get(0);
//            if (image != null && !TextUtils.isEmpty(image.getCompressPath())) {
//                File file = new File(image.getCompressPath());
//
//                //如果连续选择都同一张收款码，则不再处理
//                if(fileImg==null || !(fileImg.getPath()).equals(file.getPath()))
//                {
//                    fileImg = file;
//                    checkQRCode(fileToBitmap(file.getPath()), "该图片不是收款码");
//                }
//
//            }
//        }
//
//
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//
//    }
//
//    @Override
//    public void takeCancel() {
//
//    }
//
//    //调用相册
//    public void getImgeUpload() {
//        mImageUploadConfigUtil.imageOperation(mTakePhoto, ImageUploadConfigUtil.IMAGE_GET_FROM_ALBUM);
//    }



    @Override
    protected void requestData() {
        super.requestData();
        //判断当前选择着(中间)提现方式是哪种
        if (recordTxtList[1] == R.string.bank_card) {
            //如果是银行卡
            yiHanKa();
        }else{
            //如果是支付宝或微信
            zhiFuBaoAndWeiXin();
        }

    }

    /**
     * 银行卡资料提交
     */
    public void yiHanKa(){
        String cardNoStr=evYinHangKaCard.getText().toString().trim();
        if(evYinHangKaName.getText().length()<=2 || evYinHangKaCard.getText().length()<=12){
            ArmsUtils.makeText(getContext(),this.getString(R.string.upload_card_info));
        }else{
            //通过阿里云api较验出银行卡号是哪个发户行，回调结果找getDefaultObserver()方法
            //https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=6225887868856846&cardBinCheck=true

            RetrofitUrlManager.getInstance().putDomain("douban", "https://ccdcapi.alipay.com");

            NetWorkManager
                    .getInstance()
                    .getCommonService()
                    .getBook(cardNoStr,true)
                    .compose(this.<ResponseBody>getDefaultTransformer())
                    .subscribe(getDefaultObserver());

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
     * 较验银行卡号的结果回调
     */
    private Observer<ResponseBody> getDefaultObserver() {
        return new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody response) {
                try {
                    String string = response.string();


                    JsonObject jsonObject = (JsonObject) new JsonParser().parse(string);

                    boolean validated=jsonObject.get("validated").getAsBoolean();
                    if(!validated){//银行卡号无法识别，说明输入的卡号不对
                        showMessage(getActivity().getString(R.string.upload_card_info7));
                        return;
                    }

                    String cardType=jsonObject.get("cardType").getAsString();//卡号类型，DC是储蓄卡，CC是信用卡
                    String bank=jsonObject.get("bank").getAsString();
                    if(!"DC".equals(cardType)){//如果不是储蓄卡，拒绝
                        showMessage(getActivity().getString(R.string.upload_card_info6));
                        return;
                    }

                    Map<String, Object> map = new HashMap<>();
                    map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                            getString(AppConstants.TOKEN, ""));
                    map.put("type", 0);
                    map.put("realName", evYinHangKaName.getText().toString().trim());
                    map.put("cardNum", evYinHangKaCard.getText().toString().trim());
                    map.put("regBank", bank);
                    mPresenter.requestWithdrawMsg(map, null);

                    //showResult(string);
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

    /**
     * 较验图片是否为二维码图片
     * @param bitmap
     * @param errorTip
     */
    private void checkQRCode(final Bitmap bitmap, final String errorTip) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return QRCodeDecoder.syncDecodeQRCode(bitmap);
            }

            @Override
            protected void onPostExecute(String result) {
                if (TextUtils.isEmpty(result)) {
                    fileImg=null;
                    codeShowImg.setVisibility(View.GONE);
                    showMessage(errorTip);
                } else {
                    //较验通过，显示图片
                    GlideUtil.loadNormalImage(codeShowImg, fileImg);
                    codeShowImg.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }


    /**
     * file转为bitmap
     */
    public static Bitmap fileToBitmap(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }
    /**
     * 支付宝或微信资料提交
     */
    public void zhiFuBaoAndWeiXin(){
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, ""));
        //先判断下有没有上传图片
        if (fileImg == null) {
            showMessage(this.getString(R.string.up_load_img));
            return;
        } else if (recordTxtList[1] == R.string.pay_treasure) {
            //支付宝
            map.put("type", 1);
        } else {
            //微信
            map.put("type", 2);
        }
        mPresenter.requestWithdrawMsg(map, fileImg);
    }

    /**
     * 提交提现资料后的回调接口
     * @param baseResponse
     */
    @Override
    public void onWithdrawMsg(BaseResponse<String> baseResponse) {
        dismissLoadingAnimationDialog();
        wrResultInfter.onWithdrawResultMsg(baseResponse);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDeleteCacheDirFile() {
        PictureFileUtils.deleteCacheDirFile(getContext());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
