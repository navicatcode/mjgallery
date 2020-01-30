package com.mjgallery.mjgallery.mvp.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.ImagePictureSelectUtils;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.Utils;
import com.mjgallery.mjgallery.app.view.dialog.BillingInstructionsDialog;
import com.mjgallery.mjgallery.app.view.dialog.QRCodeDialog;
import com.mjgallery.mjgallery.app.view.dialog.WithdrawDepositDialog;
import com.mjgallery.mjgallery.di.component.DaggerMineComponent;
import com.mjgallery.mjgallery.event.SavePhoneEvent;
import com.mjgallery.mjgallery.event.UpDateEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.MineContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineMenuBean;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;
import com.mjgallery.mjgallery.mvp.presenter.mine.MinePresenter;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.AboutUsActivityActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.IFoundActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.invitedplayers.InvitedPlayersActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyAttentionActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyCommentsActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.myvip.MyVIPActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyWalletActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.NoticeActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.PerfectInformationActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.PersonalActivityCenterActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.SettingActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.signinreward.SignInRewardActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawActivity;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawDepositStatusActivity;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.MineGridMenuAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.home.MineMenuAdapter;
import com.mjgallery.mjgallery.widget.UIImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mjgallery.mjgallery.app.glide.GlideUtil.loadCircleImage;


/**
 * ================================================
 * Description:个人中心界面
 * <p>
 * Created by MVPArmsTemplate on 08/15/2019 08:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */


public class MineFragment extends MJBaseFragment<MinePresenter> implements MineContract.View, QRCodeDialog.ICopyInvitationCode,
        WithdrawDepositDialog.IWithdrawDepositListener, MineMenuAdapter.IMineMenuListener, MineGridMenuAdapter.IMineGridMenuListener {
    WithdrawDepositDialog wdDialog;
    BillingInstructionsDialog billingInstructionsDialog;
    QRCodeDialog qrCodeDialog;
    List<MineMenuBean> mineMenuBeanList;
    MineMenuAdapter mineMenuAdapter;
    List<MineMenuBean> mineGridMenuBeanList;
    MineGridMenuAdapter mineGridMenuAdapter;
    @BindView(R.id.ivCopyCode)
    ImageView ivCopyCode;
    @BindView(R.id.rlCopyCode)
    LinearLayout rlCopyCode;
    @BindView(R.id.rlSumRegister)
    LinearLayout rlSumRegister;
    @BindView(R.id.rlTodayRegister)
    LinearLayout rlTodayRegister;
    int gender = 1;
    String inviteUrl;
    String inviteCode;
    String userImg;
    @BindView(R.id.ivMineSetting)
    UIImageView ivMineSetting;
    @BindView(R.id.ivMineNotice)
    UIImageView ivMineNotice;
    @BindView(R.id.ivMineUserImg)
    ImageView ivMineUserImg;
    @BindView(R.id.tvMineUserName)
    TextView tvMineUserName;
    @BindView(R.id.tvMineInviteCode)
    TextView tvMineInviteCode;
    @BindView(R.id.tvMineInviteCodeNumber)
    TextView tvMineInviteCodeNumber;
    @BindView(R.id.tvMineBindingMobilePhone)
    TextView tvMineBindingMobilePhone;
    @BindView(R.id.tvMineBindingMobilePhoneNumber)
    TextView tvMineBindingMobilePhoneNumber;
    @BindView(R.id.btnPerfectInformation)
    TextView btnPerfectInformation;
    @BindView(R.id.tvTotalCommission)
    TextView tvTotalCommission;
    @BindView(R.id.tvSalesToday)
    TextView tvSalesToday;
    @BindView(R.id.tvTotalNumber)
    TextView tvTotalNumber;
    @BindView(R.id.llMineTop)
    LinearLayout llMineTop;
    @BindView(R.id.ivBalanceImg)
    ImageView ivBalanceImg;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.tvBalanceNumber)
    TextView tvBalanceNumber;
    @BindView(R.id.tvMineWithdrawal)
    TextView tvMineWithdrawal;
    @BindView(R.id.rlMineWithdrawal)
    RelativeLayout rlMineWithdrawal;
    @BindView(R.id.rlWithdraw)
    RelativeLayout rlWithdraw;
    @BindView(R.id.ivInstructionsImg)
    ImageView ivInstructionsImg;
    @BindView(R.id.tvInstructions)
    TextView tvInstructions;
    @BindView(R.id.tvInstructionsNumber)
    TextView tvInstructionsNumber;
    @BindView(R.id.tvMineInstructions)
    TextView tvMineInstructions;
    @BindView(R.id.rlMineInstructions)
    RelativeLayout rlMineInstructions;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewGrid)
    RecyclerView recyclerViewGrid;
    String mobile;
    UserInformation userInformation;
    @BindView(R.id.llVipLevel)
    LinearLayout llVipLevel;
    @BindView(R.id.tvVipLevelNumber)
    TextView tvVipLevelNumber;
    @BindView(R.id.tvVipLevelTxt)
    TextView tvVipLevelTxt;
    @BindView(R.id.tvSignInReward)
    TextView tvSignInReward;
    @BindView(R.id.llSignInReward)
    LinearLayout llSignInReward;
    private Bitmap mBitmap;
    ImagePictureSelectUtils mImagePictureSelectUtils;

    File userHeadFile;
    public boolean updMineHeadStatus = false;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        billingInstructionsDialog = new BillingInstructionsDialog(getActivity());
        wdDialog = new WithdrawDepositDialog(getActivity(), this);
        qrCodeDialog = new QRCodeDialog(getActivity(), this);
        loadCircleImage(ivMineUserImg, R.drawable.mine_user_normal_img);
        mImagePictureSelectUtils = ImagePictureSelectUtils.getInstance();
        mImagePictureSelectUtils.setMaxSelectNum(1);
        mImagePictureSelectUtils.setEnableCrop(true);
        mImagePictureSelectUtils.setCircleDimmedLayer(true);
        mImagePictureSelectUtils.setCompress(true);

        initGridRecyclerView();
        initListRecyclerView();
        isLogin();

    }

    /**
     * 初始化横向列表（我的关注，评论，发布，活动中心等）
     */
    private void initGridRecyclerView() {
        mineGridMenuBeanList = new ArrayList<>();
        mineGridMenuBeanList.add(new MineMenuBean(1, R.drawable.my_guanzhi, ArmsUtils.getString(BaseApplication.getInstance(), R.string.my_attention)));
        mineGridMenuBeanList.add(new MineMenuBean(2, R.drawable.my_pinluo, ArmsUtils.getString(BaseApplication.getInstance(), R.string.my_comments)));
        mineGridMenuBeanList.add(new MineMenuBean(3, R.drawable.my_fabu, ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_fabu)));
        mineGridMenuBeanList.add(new MineMenuBean(4, R.drawable.my_huoduo, ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_huodong)));

        mineGridMenuAdapter = new MineGridMenuAdapter(getContext(), R.layout.mine_grid_menu_item, mineGridMenuBeanList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGrid.setLayoutManager(gridLayoutManager);
        recyclerViewGrid.setAdapter(mineGridMenuAdapter);
    }

    /**
     * 初始化纵向列表（我的钱包，vip，消息，个人信息,关于我们，二维码等）
     */
    private void initListRecyclerView() {
        mineMenuBeanList = new ArrayList<>();
        mineMenuBeanList.add(new MineMenuBean(1, R.drawable.mine_qibao_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_wallet)));
        mineMenuBeanList.add(new MineMenuBean(2, R.drawable.mine_myvip_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_my_vip)));
        mineMenuBeanList.add(new MineMenuBean(3, R.drawable.mine_message_three_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.my_news)));
        mineMenuBeanList.add(new MineMenuBean(4, R.drawable.mine_meinfo_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.personal_information)));
        mineMenuBeanList.add(new MineMenuBean(5, R.drawable.mine_pengyou_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.about_us)));
        mineMenuBeanList.add(new MineMenuBean(6, R.drawable.mine_qrcode_img, ArmsUtils.getString(BaseApplication.getInstance(), R.string.my_qr_code)));
        mineMenuAdapter = new MineMenuAdapter(getContext(), R.layout.mine_menu_item, mineMenuBeanList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mineMenuAdapter);
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

    /**
     * 提交要提现的金额--发送请求
     *
     * @param password
     * @param money
     */
    @Override
    public void onSubmit(String password, String money) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("password", password);
        map.put("money", money);
        mPresenter.requestMeWithdraw(map);
    }

    @Override
    public void onShowMessage(String showMessage) {
        showMessage(showMessage);
    }

    @Override
    public void showMessage(@NonNull String message) {
        if (message == null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getContext(), message);
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
     * Event刷新个人绑定的手机号码
     *
     * @param upDateEvent
     */
    @Subscriber(mode = ThreadMode.MAIN)
    public void onSavePhoneEvent(SavePhoneEvent savePhoneEvent) {
        if (!TextUtils.isEmpty(savePhoneEvent.getPhoneNuber())) {
            mobile = savePhoneEvent.getPhoneNuber();
            tvMineBindingMobilePhoneNumber.setText(mobile);
        }
    }


    /**
     * Event刷新个人信息
     *
     * @param upDateEvent
     */
    @Subscriber(mode = ThreadMode.MAIN)
    public void onUpDateEvent(UpDateEvent upDateEvent) {
        if (!hiddenStatus)
            normalMine();
    }


    @Override
    public void onResume() {
        super.onResume();
        hiddenStatus = false;
    }

    /**
     * 还原默认设置
     */
    private void normalMine() {
        if (!isLoginStatus()) {
            tvMineUserName.setText("");
            tvMineBindingMobilePhoneNumber.setText("");
            tvMineInviteCodeNumber.setText("");
            tvTotalCommission.setText("");
            tvSalesToday.setText("");
            tvTotalNumber.setText("");
            tvBalanceNumber.setText("");
            loadCircleImage(ivMineUserImg, R.drawable.mine_user_normal_img);
        } else {
            Objects.requireNonNull(mPresenter).requestData();
        }
    }

    @OnClick({R.id.ivMineSetting, R.id.rlCopyCode, R.id.ivCopyCode, R.id.btnPerfectInformation, R.id.ivMineNotice,
            R.id.ivMineUserImg, R.id.llSignInReward, R.id.rlWithdraw, R.id.rlMineInstructions, R.id.rlSumRegister, R.id.rlTodayRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMineSetting://设置
                onMineSettingClick();
                break;
            case R.id.llSignInReward://签到
                isToOtherActivity(SignInRewardActivity.class, null);
                break;
            case R.id.ivMineNotice://通知（小铃铛图标）
                isToOtherActivity(NoticeActivity.class, null);
                break;
            case R.id.rlCopyCode:
            case R.id.ivCopyCode:
                //获取剪贴板管理器：
                onCopyInvitationCode(tvMineInviteCodeNumber.getText().toString());
                break;
            case R.id.rlWithdraw:   //提现
                withdrawDeposit();
                break;
            case R.id.ivMineUserImg://点击头像
                if (isLoginStatus() && userInformation != null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userId", userInformation.getUserId() + "");
//                    isToOtherActivity(HisInformationActivity.class, bundle);
                    updMineHeadStatus = true;
                    mImagePictureSelectUtils.initActivityPictureSelector(getActivity()
                            , PictureMimeType.ofAll());
                }
                break;
            case R.id.btnPerfectInformation:   //完善信息
                if (isLoginStatus() && userInformation != null) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("gender", gender);
                    bundle1.putString("headImg", userInformation.getHeadImg());
                    bundle1.putString("nikeName", userInformation.getNikeName());
                    bundle1.putString("birthday", userInformation.getBirthday());
                    isToOtherActivity(PerfectInformationActivity.class, bundle1);
                }
                break;
            case R.id.rlMineInstructions:  //结算说明
                if (billingInstructionsDialog != null && !billingInstructionsDialog.isShowing()) {
                    billingInstructionsDialog.show();
                }
                break;
            case R.id.rlSumRegister:  //总注册人
                Bundle bundle = new Bundle();
                bundle.putInt("status", 0);
                toOtherActivity(InvitedPlayersActivity.class, bundle);
                break;
            case R.id.rlTodayRegister:  //今天注册
                Bundle bundle1 = new Bundle();
                bundle1.putInt("status", 1);
                toOtherActivity(InvitedPlayersActivity.class, bundle1);
                break;

        }
    }

    private void onMineSettingClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userInformation", userInformation);
        toOtherActivity(SettingActivity.class, bundle);
    }


    @Override
    public void onCopyInvitationCode(String copyName) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyName);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.copy_invitation_code_successfully));

    }

    @Override
    public void onSaveCode(Bitmap bitmap) {
        mBitmap = bitmap;
        mPresenter.externalStorage();
    }

    boolean hiddenStatus = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        hiddenStatus = hidden;
        if (!hidden) {
            normalMine();
        }
    }

    @Override
    protected void requestData() {
        mPresenter.requestData();
    }

    /**
     * 获取个人信息接口--回调结果
     *
     * @param userInformation
     */
    @Override
    public void onUserInformation(UserInformation userInformation) {
        if (userInformation != null) {

            this.userInformation = userInformation;
            tvMineUserName.setText(userInformation.getNikeName());
            if (TextUtils.isEmpty(userInformation.getMobile())) {
                tvMineBindingMobilePhoneNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.app_mine_unbinding));
                tvMineBindingMobilePhoneNumber.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.color_FF5C5C));
            } else {
                tvMineBindingMobilePhoneNumber.setText(userInformation.getMobile());
                tvMineBindingMobilePhoneNumber.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(), R.color.white));
            }
            mobile = userInformation.getMobile();
            gender = userInformation.getGender();
            userImg = userInformation.getHeadImg();
            loadCircleImage(ivMineUserImg, userInformation.getHeadImg(), R.drawable.mine_user_normal_img);
            tvMineInviteCodeNumber.setText(userInformation.getInviteCode());
            tvTotalCommission.setText("¥" + userInformation.getSysBrokerageAmount());
            tvSalesToday.setText(userInformation.getSumDay() + "人");
            tvTotalNumber.setText(userInformation.getMax() + "人");
            tvBalanceNumber.setText(userInformation.getUserBalanceAmount() + "");
            inviteUrl = userInformation.getInviteUrl();
            inviteCode = userInformation.getInviteCode();
            mineMenuAdapter.hasNewNotify=userInformation.getHasNewNotify();
            mineMenuAdapter.notifyDataSetChanged();
            switch (userInformation.getSettlement()) {
                case 0:
                    tvInstructionsNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.is_not_set));
                    break;
                case 1:
                    tvInstructionsNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.daily));
                    break;
                case 2:
                    tvInstructionsNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.monthly_statement));
                    break;

            }

            //用户VIP级别
            Obj obj = userInformation.getObj();
            if (obj != null && obj.getCode() > 0) {
                tvVipLevelNumber.setText("V" + obj.getCode());

                tvVipLevelTxt.setText(Utils.getUserLevelName(obj.getCode()));
                llVipLevel.setVisibility(View.VISIBLE);
            } else {
                llVipLevel.setVisibility(View.INVISIBLE);
            }
        }
    }




    /**
     * 提现金额接口--回调结果
     */
    @Override
    public void onMeWithdraw(BaseResponse<WithdrawDepositBean> response) {
        if (response.getResult() != null && response.getCode() == 0) {

            WithdrawDepositBean withdrawDepositBean = response.getResult();
            skipWithdrawDepositStatus(withdrawDepositBean);
        } else {
            showMessage(response.getMessage());
        }

    }


    /**
     * 获取提现信息状态接口--回调结果
     *
     * @param response
     */
    @Override
    public void onMeWithdrawMsg(BaseResponse<WithdrawDepositBean> response) {
        if (response.getCode() == 0) {

            WithdrawDepositBean withdrawDepositBean = response.getResult();
            //如果是审核状态或未读结果，刚跳转到审核进度页面
            if (withdrawDepositBean != null && (withdrawDepositBean.getVerifyResultStatus() == 0 || withdrawDepositBean.getMsg() == 0)) {

                skipWithdrawDepositStatus(withdrawDepositBean);
            } else {
                //如果是提现完成或失败，且已读信息下，或者第一次提现没有任何提现记录下，弹出输入提现金额的dialog
                wdDialog.showDialog(userInformation);
            }
        } else {
            showMessage(response.getMessage());
        }

    }

    @Override
    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }

    @Override
    public void onSaveQRImg() {
        saveImageToGallery(mBitmap);
    }


    /**
     * 跳转到提现进度状态Activity
     *
     * @param withdrawDepositBean
     */
    public void skipWithdrawDepositStatus(WithdrawDepositBean withdrawDepositBean) {
        if (wdDialog != null) {
            wdDialog.etClear();
            wdDialog.dismiss();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("verifyResultStatus", withdrawDepositBean.getVerifyResultStatus());
        bundle.putString("formatTime", withdrawDepositBean.getFormatTime());
        bundle.putString("orderNo", withdrawDepositBean.getOrderNo());
        toOtherActivity(WithdrawDepositStatusActivity.class, bundle);
    }

    /**
     * 保存二维码图片
     *
     * @param bitmap
     */
    public void saveImageToGallery(Bitmap bitmap) {
        // 首先保存图片
        File file = null;
        String fileName = System.currentTimeMillis() + ".jpg";
        File root = new File(Environment.getExternalStorageDirectory(), "碼經圖庫");
        File dir = new File(root, "images");
        if (dir.mkdirs() || dir.isDirectory()) {
            file = new File(dir, fileName);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(getContext(), new String[]{file.getAbsolutePath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(uri);
                            getActivity().sendBroadcast(mediaScanIntent);
                        }
                    });
        } else {
            String relationDir = file.getParent();
            File file1 = new File(relationDir);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file1.getAbsoluteFile())));
        }
        if (qrCodeDialog != null && qrCodeDialog.isShowing()) {
            qrCodeDialog.dismiss();
        }
        showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.saved_qr_code_successfully));
    }


    /**
     * 提现，点击提交请求的入口
     */
    protected void withdrawDeposit() {
        //判断是否登录，如果未登录则让他登录
        if (isLoginStatus()) {
            if (userInformation != null) {
                //如果提现资料已填完，刚进入下一步判断
                if (userInformation.getResultType() == 0) {
                    mPresenter.requestMeWithdrawMsg(token);
                } else if (userInformation.getResultType() == 1) {
                    //如果提现资料未填完，刚跳转填写资料页面
                    toOtherActivity(WithdrawActivity.class);
                } else {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.zhi_liao_hint));
                }
            } else {
                showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.zhi_liao_error));
            }
        }
    }

    @Override
    public void onMineMenuListener(int position) {
        if (mineMenuBeanList.size() > position) {
            MineMenuBean mineMenuBean = mineMenuBeanList.get(position);
            switch (mineMenuBean.getType()) {
                case 1:   //我的钱包
                    if (isLoginStatus()) {
                        if (userInformation != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userInformation", userInformation);
                            toOtherActivity(MyWalletActivity.class, bundle);
                        }
                    }
                    break;
                case 2:   //我的VIP
                    if (isLoginStatus()) {
                        if(userInformation!=null){
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("userInformation", userInformation);
                            toOtherActivity(MyVIPActivity.class,bundle);
                        }
                    }
                    break;
                case 3:   //我的消息
                    isToOtherActivity(NoticeActivity.class, null);
                    break;
                case 4:   //个人信息
                    if (isLoginStatus() && userInformation != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", userInformation.getUserId() + "");
                        isToOtherActivity(HisInformationActivity.class, bundle);
                    }
                    break;
                case 5:   //关于我们
                    isToOtherActivity(AboutUsActivityActivity.class, new Bundle());
                    break;
                case 6:   //我的二维码
                    if (TextUtils.isEmpty(inviteUrl)) {
                        return;
                    }
                    if (qrCodeDialog != null && !qrCodeDialog.isShowing()) {
                        qrCodeDialog.show(inviteUrl, inviteCode, userImg);
                    }
                    break;

            }
        }
    }

    @Override
    public void onMineGridMenuListener(int position) {
        if (mineMenuBeanList.size() > position) {
            MineMenuBean mineMenuBean = mineMenuBeanList.get(position);
            switch (mineMenuBean.getType()) {
                case 1:   //我的关注
                    isToOtherActivity(MyAttentionActivity.class, null);
                    break;
                case 2:   //我的评论
                    isToOtherActivity(MyCommentsActivity.class, new Bundle());
                    break;
                case 3:   //我的发布
                    isToOtherActivity(IFoundActivity.class, new Bundle());
                    break;
                case 4:   //活动中心
                    toOtherActivity(PersonalActivityCenterActivity.class);
                    break;
            }
        }
    }

    public void onHeadFileMethod(File file) {
        this.userHeadFile = file;
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).getString("token", ""));
        mPresenter.requestUpdatePerfect(map, file);

    }

    @Override
    public void onUpdatePerfect(BaseResponse baseResponse) {
        if (baseResponse.getCode() == 0) {
            GlideUtil.loadCircleImage(ivMineUserImg, userHeadFile);
            normalMine();
        } else {
            showMessage(ArmsUtils.getString(BaseApplication.getInstance(), R.string.mine_head_img_upd));
        }

    }
}
