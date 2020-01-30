package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.ImagePictureSelectUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerFeedbackInfoComponent;
import com.mjgallery.mjgallery.mvp.contract.setting.FeedbackInfoContract;
import com.mjgallery.mjgallery.mvp.presenter.setting.FeedbackInfoPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:意见反馈
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 20:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * @author 鲁班
 * ================================================
 */
public class FeedbackInfoActivity extends MJBaseActivity<FeedbackInfoPresenter> implements FeedbackInfoContract.View {

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
    @BindView(R.id.etFeedback1)
    EditText etFeedback1;
    @BindView(R.id.ivFeedbackImg1)
    ImageView ivFeedbackImg1;
    @BindView(R.id.etFeedback2)
    EditText etFeedback2;
    @BindView(R.id.ivFeedbackImg2)
    ImageView ivFeedbackImg2;
    @BindView(R.id.etFeedback3)
    EditText etFeedback3;
    @BindView(R.id.ivFeedbackImg3)
    ImageView ivFeedbackImg3;
    @BindView(R.id.etFeedback4)
    EditText etFeedback4;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.ivUploadImg)
    ImageView ivUploadImg;
    ImageView[] ivList;
    /**记录被选中的反馈类型*/
    int selectType=0;
    private File file;
    ImagePictureSelectUtils mImagePictureSelectUtils;
    private List<LocalMedia> selectList = new ArrayList<>();
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_feedback_info));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        ivList=new ImageView[]{ivFeedbackImg1, ivFeedbackImg2, ivFeedbackImg3};
        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mImagePictureSelectUtils.setMaxSelectNum(1);
        mImagePictureSelectUtils.setCircleDimmedLayer(false);
        mImagePictureSelectUtils.setEnableCrop(false);

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
        ArmsUtils.makeText(getApplication(),message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void requestData() {

    }



    @OnClick({R.id.ivTopReturn,R.id.ivFeedbackImg1, R.id.ivFeedbackImg2, R.id.ivFeedbackImg3, R.id.tvSubmit,R.id.ivUploadImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivFeedbackImg1:
                changingImgBack(ivFeedbackImg1,ivFeedbackImg2,ivFeedbackImg3,ivFeedbackImg1);
                break;
            case R.id.ivFeedbackImg2:
                changingImgBack(ivFeedbackImg1,ivFeedbackImg2,ivFeedbackImg3,ivFeedbackImg2);
                break;
            case R.id.ivFeedbackImg3:
                changingImgBack(ivFeedbackImg1,ivFeedbackImg2,ivFeedbackImg3,ivFeedbackImg3);
                break;
            case R.id.ivUploadImg:
                mImagePictureSelectUtils.initActivityPictureSelector(
                        FeedbackInfoActivity.this, PictureMimeType.ofImage());
                break;
            case R.id.tvSubmit:

                if(etFeedback4.getText().toString().length()<=10){
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.fedBackErrorHint));
                    return;
                }

                Map<String,Object> map=new HashMap<>();
                map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).getString("token", ""));
                map.put("type",selectType+1);
                map.put("content",etFeedback4.getText().toString());

                mPresenter.requestMeFeedback(map,file);
                break;
        }
    }

    /**
     * 根据勾选的反馈类型，更新ui
     * @param img1
     * @param img2
     * @param img3
     * @param imgUI
     */
    public void changingImgBack(ImageView img1,ImageView img2,ImageView img3,ImageView imgUI){
        for(int i=0;i<ivList.length;i++){
            if(ivList[i]==imgUI){
                selectType=i;
                //ImgView改为'选中'背景。
                ivList[i].setImageResource(R.drawable.select_daguo2);
            }else{
                //ImgView改为'未选中'背景。
                ivList[i].setImageResource(R.drawable.select_daguo1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    for (LocalMedia media : selectList) {
                        android.util.Log.i(TAG, "压缩---->" + media.getCompressPath());
                        android.util.Log.i(TAG, "原图---->" + media.getPath());
                        android.util.Log.i(TAG, "裁剪---->" + media.getCutPath());
                        if (media.getCompressPath() != null) {
                            file = new File(media.getCompressPath());
                            GlideUtil.loadNormalImage(ivUploadImg, file);
                        }

                    }
                    break;
            }
        }
    }


    @Override
    public void onMeFeedback(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
                killMyself();
            } else {
                showMessage(baseResponse.getMessage());
            }
        }
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDeleteCacheDirFile() {
        PictureFileUtils.deleteCacheDirFile(FeedbackInfoActivity.this);
    }
}
