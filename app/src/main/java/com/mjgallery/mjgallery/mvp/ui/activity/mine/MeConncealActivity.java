package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerMeConncealComponent;
import com.mjgallery.mjgallery.mvp.contract.mine.MeConncealContract;
import com.mjgallery.mjgallery.mvp.presenter.mine.MeConncealPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:隐私设置
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * @author 鲁班
 * ================================================
 */
public class MeConncealActivity extends MJBaseActivity<MeConncealPresenter> implements MeConncealContract.View {

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
    @BindView(R.id.lsShouZhi1)
    ImageView lsShouZhi1;
    @BindView(R.id.lsShouZhi2)
    ImageView lsShouZhi2;
    @BindView(R.id.lsJieSuan1)
    ImageView lsJieSuan1;
    @BindView(R.id.lsJieSuan2)
    ImageView lsJieSuan2;
    @BindView(R.id.lsQRCode1)
    ImageView lsQRCode1;
    @BindView(R.id.lsQRCode2)
    ImageView lsQRCode2;

    //记录最近一次的选择
    ImageView iv1;
    ImageView iv2;
    boolean status;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMeConncealComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_conceal_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_connceal));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);


        showStatus(lsShouZhi1,lsShouZhi2,AppConstants.shouZhiOpen,true);
        showStatus(lsJieSuan1,lsJieSuan2,AppConstants.jieSuanOpen,true);
        showStatus(lsQRCode1,lsQRCode2,AppConstants.qRCodeOpen,true);
    }


    /**
     * 显示之前各种类型设置的开关状态。

     * @param iniValue 定义初始值，true为开，false为关
     */
    public void showStatus(ImageView iv1,ImageView iv2,String key,boolean iniValue){
        boolean status = PreferenceUtil.getInstance(this).getBoolean(key,iniValue);
        showUI(iv1,iv2,status);
    }

    /**
     * UI展示
     */
    public void showUI(ImageView iv1,ImageView iv2,boolean status){
        if(status){
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.GONE);
        }else{
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.VISIBLE);
        }
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


    @OnClick({R.id.ivTopReturn, R.id.lsShouZhi1, R.id.lsShouZhi2, R.id.lsJieSuan1, R.id.lsJieSuan2, R.id.lsQRCode1, R.id.lsQRCode2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                finish();
                break;
            case R.id.lsShouZhi1:
                setStatus(lsShouZhi1,lsShouZhi2, AppConstants.shouZhiOpen,false);
                break;
            case R.id.lsShouZhi2:
                setStatus(lsShouZhi1,lsShouZhi2,AppConstants.shouZhiOpen,true);
                break;
            case R.id.lsJieSuan1:
                setStatus(lsJieSuan1,lsJieSuan2,AppConstants.jieSuanOpen,false);
                break;
            case R.id.lsJieSuan2:
                setStatus(lsJieSuan1,lsJieSuan2,AppConstants.jieSuanOpen,true);
                break;
            case R.id.lsQRCode1:
                setStatus(lsQRCode1,lsQRCode2,AppConstants.qRCodeOpen,false);
                break;
            case R.id.lsQRCode2:
                setStatus(lsQRCode1,lsQRCode2,AppConstants.qRCodeOpen,true);
                break;
        }
    }

    //设置开关新状态
    public void setStatus(ImageView iv1,ImageView iv2,String key,boolean status){
        PreferenceUtil.getInstance(this).putBoolean(key, status);
         this.iv1=iv1;
         this.iv2=iv2;
         this.status=status;
        sendPrivacy();
    }


    public void sendPrivacy(){

        String token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                getString(AppConstants.TOKEN, "");
        boolean privacy = PreferenceUtil.getInstance(this).getBoolean(AppConstants.shouZhiOpen,true);
        boolean sentPrivacy = PreferenceUtil.getInstance(this).getBoolean(AppConstants.jieSuanOpen,true);
        boolean qrPrivacy = PreferenceUtil.getInstance(this).getBoolean(AppConstants.qRCodeOpen,true);

        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("privacy",privacy?0:1);
        map.put("sentPrivacy", sentPrivacy?0:1);
        map.put("qrPrivacy", qrPrivacy?0:1);
        mPresenter.requestMePrivacy(map);
    };

    @Override
    public void onMePrivacy(BaseResponse baseResponse) {
        if (baseResponse != null && baseResponse.getCode()==0) {
            showUI(iv1,iv2,status);
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.request_ok));
        }else{
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.data_error));
        }
    }
}
