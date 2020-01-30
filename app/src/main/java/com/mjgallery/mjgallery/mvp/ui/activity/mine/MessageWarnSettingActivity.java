package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * ================================================
 * 消息提醒设置
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * @author 鲁班
 * ================================================
 */
public class MessageWarnSettingActivity extends Activity {


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
    @BindView(R.id.music_set_img)
    ImageView musicSetImg;
    @BindView(R.id.music_set_linearLayout)
    LinearLayout musicSetLinearLayout;
    @BindView(R.id.warn_set_img)
    ImageView warnSetImg;
    @BindView(R.id.warn_set_linearLayout)
    LinearLayout warnSetLinearLayout;
    @BindView(R.id.banner_set_img)
    ImageView bannerSetImg;
    @BindView(R.id.banner_set_linearLayout)
    LinearLayout bannerSetLinearLayout;
    @BindView(R.id.sound_set_img)
    ImageView soundSetImg;
    @BindView(R.id.sound_set_linearLayout)
    LinearLayout soundSetLinearLayout;
    @BindView(R.id.shake_set_img)
    ImageView shakeSetImg;
    @BindView(R.id.shake_set_linearLayout)
    LinearLayout shakeSetLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_warn_setting);
        initData();

    }

    public void initData(){
        ButterKnife.bind(this);
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.message_setting));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);

        showOpenSetting(musicSetImg, AppConstants.musicOpen, AppConstants.STATUS_OPEN);
        showOpenSetting(warnSetImg, AppConstants.messageOpen, AppConstants.STATUS_OPEN);
        showOpenSetting(bannerSetImg, AppConstants.bannerOpen, AppConstants.STATUS_OPEN);
        showOpenSetting(soundSetImg, AppConstants.soundOpen, AppConstants.STATUS_COLSE);
        showOpenSetting(shakeSetImg, AppConstants.shakeOpen, AppConstants.STATUS_COLSE);

    }

    /**
     * 显示之前各种类型设置的开关状态。
     * @param img
     * @param key
     * @param iniValue
     */
    public void showOpenSetting(ImageView img,String key,int iniValue){
        int status=PreferenceUtil.getInstance(this).getInt(key,iniValue);
        if(status == AppConstants.STATUS_OPEN  ){
            //如果之前是开，刚显示开
            img.setImageResource(R.drawable.select_open);
        }else{
            //如果之前是关，刚显示关
            img.setImageResource(R.drawable.select_close);
        }
    }


    @OnClick({R.id.ivTopReturn, R.id.music_set_linearLayout, R.id.warn_set_linearLayout, R.id.banner_set_linearLayout, R.id.sound_set_linearLayout, R.id.shake_set_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                finish();
                break;
            case R.id.music_set_linearLayout:
                //音效开关
                setOpenStatus(musicSetImg, AppConstants.musicOpen, AppConstants.STATUS_OPEN);
                break;
            case R.id.warn_set_linearLayout:
                //通知开关
                setOpenStatus(warnSetImg, AppConstants.messageOpen, AppConstants.STATUS_OPEN);
                break;
            case R.id.banner_set_linearLayout:
                //横幅开关
                setOpenStatus(bannerSetImg, AppConstants.bannerOpen, AppConstants.STATUS_OPEN);
                break;
            case R.id.sound_set_linearLayout:
                //铃声开关
                setOpenStatus(soundSetImg, AppConstants.soundOpen, AppConstants.STATUS_COLSE);
                break;
            case R.id.shake_set_linearLayout:
                //震动开关
                setOpenStatus(shakeSetImg, AppConstants.shakeOpen, AppConstants.STATUS_COLSE);
                break;
        }
    }


    /**
     * 存储各种类型设置的开关状态。
     * @param img 开关勾选ui
     * @param key 键
     * @param iniValue 设置初始值，1为开，0为关
     */
    public void setOpenStatus(ImageView img,String key,int iniValue){
        int status=PreferenceUtil.getInstance(this).getInt(key,iniValue);
        if(status == AppConstants.STATUS_OPEN ){
            //如果之前是开，刚改为关
            PreferenceUtil.getInstance(this).putInt(key, AppConstants.STATUS_COLSE);
            img.setImageResource(R.drawable.select_close);
        }else{
            //如果之前是关，刚改为开
            PreferenceUtil.getInstance(this).putInt(key, AppConstants.STATUS_OPEN);
            img.setImageResource(R.drawable.select_open);
        }

    }
}
