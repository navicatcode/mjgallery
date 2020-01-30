package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * ================================================
 * Description:隐私设置
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 22:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class ConncealActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceal_setting);
        ButterKnife.bind(this);
        initData();

    }

    public void initData() {

        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.setting_connceal));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        showStatus(lsShouZhi1,lsShouZhi2,AppConstants.shouZhiOpen,true);
        showStatus(lsJieSuan1,lsJieSuan2,AppConstants.jieSuanOpen,false);
        showStatus(lsQRCode1,lsQRCode2,AppConstants.qRCodeOpen,false);
    }

    /**
     * 显示之前各种类型设置的开关状态。

     * @param iniValue 定义初始值，true为开，false为关
     */
    public void showStatus(ImageView iv1,ImageView iv2,String key,boolean iniValue){
        boolean status = PreferenceUtil.getInstance(ConncealActivity.this).getBoolean(key,iniValue);
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

    @OnClick({R.id.ivTopReturn,R.id.lsShouZhi1, R.id.lsShouZhi2, R.id.lsJieSuan1, R.id.lsJieSuan2, R.id.lsQRCode1, R.id.lsQRCode2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                finish();
                break;
            case R.id.lsShouZhi1:
                setStatus(lsShouZhi1,lsShouZhi2,AppConstants.shouZhiOpen,false);
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
        PreferenceUtil.getInstance(ConncealActivity.this).putBoolean(key, status);
        showUI(iv1,iv2,status);
    }

}
