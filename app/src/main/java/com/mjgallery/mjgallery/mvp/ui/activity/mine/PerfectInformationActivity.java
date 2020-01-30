package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.mjgallery.mjgallery.app.view.dialog.TimePickerDialog;
import com.mjgallery.mjgallery.di.component.DaggerPerfectInformationComponent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.PerfectInformationContract;
import com.mjgallery.mjgallery.mvp.presenter.mine.PerfectInformationPresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.glide.GlideUtil.loadCircleImage;


/**
 * ================================================
 * Description:完善信息界面
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 18:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class PerfectInformationActivity extends MJBaseActivity<PerfectInformationPresenter>
        implements PerfectInformationContract.View {


    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.ivTopRight)
    TextView ivTopRight;
    @BindView(R.id.tvNickname)
    TextView tvNickname;
    @BindView(R.id.tvSed)
    TextView tvSed;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.btnChangePassword)
    TextView btnChangePassword;
    @BindView(R.id.etNickname)
    EditText etNickname;
    @BindView(R.id.radioButtonMale)
    RadioButton radioButtonMale;
    @BindView(R.id.radioButtonFemale)
    RadioButton radioButtonFemale;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rlBirthday)
    RelativeLayout rlBirthday;
    TimePickerDialog timePickerDialog;
    TimePickerView pvCustomLunar;
    @BindView(R.id.ivPerfectInformationImg)
    ImageView ivPerfectInformationImg;
    @BindView(R.id.tvBirthdayNumber)
    TextView tvBirthdayNumber;
    private List<LocalMedia> selectList = new ArrayList<>();
    private File file;
    int gender;
    String birthday;
    ImagePictureSelectUtils mImagePictureSelectUtils;



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPerfectInformationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_perfect_information;
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mImagePictureSelectUtils.setMaxSelectNum(1);
        mImagePictureSelectUtils.setEnableCrop(true);
        mImagePictureSelectUtils.setCircleDimmedLayer(true);
        mImagePictureSelectUtils.setCompress(true);
        selectList.clear();
        //显示资料
        initUserInfo();

        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.perfect_information));
        Drawable drawable_Male = ArmsUtils.getDrawablebyResource(getApplicationContext(), R.drawable.tab_male_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_Male.setBounds(0, 0, 30, 30);
        //设置图片在文字的哪个方向
        radioButtonMale.setCompoundDrawables(drawable_Male, null, null, null);
        Drawable drawable_Female = ArmsUtils.getDrawablebyResource(getApplicationContext(), R.drawable.tab_female_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_Female.setBounds(0, 0, 30, 30);
        //设置图片在文字的哪个方向
        radioButtonFemale.setCompoundDrawables(drawable_Female, null, null, null);
        timePickerDialog = new TimePickerDialog(this);
        initLunarPicker();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonMale:
                        gender = 1;
                        break;
                    case R.id.radioButtonFemale:
                        gender = 0;
                        break;

                }
            }
        });
    }

    private void initUserInfo() {
        if(getIntent().getExtras()!=null){
            gender = getIntent().getIntExtra("gender", 0);
            if(gender==1) {
                radioGroup.check(radioButtonMale.getId());
            }else{
                radioGroup.check(radioButtonFemale.getId());
            }
            etNickname.setText(getIntent().getStringExtra("nikeName")+"");
            String headImg=getIntent().getStringExtra("headImg");
            if(!TextUtils.isEmpty(headImg))
                loadCircleImage(ivPerfectInformationImg, headImg, R.drawable.perfect_information_touxiang_img);
            String birthday=getIntent().getStringExtra("birthday");
            if(!TextUtils.isEmpty(birthday)){
                tvBirthdayNumber.setText(birthday.substring(0,4)+"-"+birthday.substring(5,7)+"-"+birthday.substring(8,10));
            }

        }
    }

    public void setBtnEnabled(boolean bool){
        btnChangePassword.setEnabled(bool);
        if(bool){
            btnChangePassword.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
        }else{
            btnChangePassword.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
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

    }

    @Override
    public void killMyself() {
        dismissLoadingAnimationDialog();
        finish();
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
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        if (media.getCompressPath() != null) {
                            file = new File(media.getCompressPath());
                            GlideUtil.loadCircleImage(ivPerfectInformationImg, file);
                        }

                    }
                    break;
            }
        }
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 0, 1);

        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvBirthdayNumber.setText(getTime(date));
                birthday=getTimeTwo(date);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getTimeTwo(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 ");
        return format.format(date);
    }


    @OnClick({R.id.ivPerfectInformationImg, R.id.ivTopReturn, R.id.rlBirthday, R.id.btnChangePassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.rlBirthday:
                pvCustomLunar.show();
                break;
            case R.id.btnChangePassword:
                isLogin();
                break;
            case R.id.ivPerfectInformationImg:
                mImagePictureSelectUtils.initActivityPictureSelector(PerfectInformationActivity.this
                        , PictureMimeType.ofAll());
                break;
        }
    }

    @Override
    public void onUpdatePerfect(BaseResponse baseResponse) {
        if (baseResponse != null && baseResponse.getCode() == 0) {
            mPresenter.externalStorage();
            selectList.clear();
            EventBus.getDefault().post(new UpDateEvent());
            toOtherActivity(MainActivity.class);
            //killMyself();
        } else {
            showMessage(baseResponse.getMessage());
        }
    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onDeleteCacheDirFile() {
        PictureFileUtils.deleteCacheDirFile(PerfectInformationActivity.this);
    }


    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).getString("token", ""));
        if (!TextUtils.isEmpty(etNickname.getText().toString())) {
            map.put("nikename", etNickname.getText().toString());
        }
        if (!TextUtils.isEmpty(birthday)) {
            map.put("birthday", birthday);
        }
        map.put("gender", gender);
        mPresenter.requestUpdatePerfect(map, file);
    }


}
