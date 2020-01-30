package com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mjgallery.mjgallery.app.MJBaseFragment;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.app.utils.WithdrawResultInterFace;
import com.mjgallery.mjgallery.di.component.DaggerWithdrawSettlementComponent;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawSettlementContract;
import com.mjgallery.mjgallery.mvp.presenter.withdraw.WithdrawSettlementPresenter;
import com.mjgallery.mjgallery.widget.ShadowDrawable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;



/**
 * ================================================
 * Description:设置结算方式
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawSettlementFragment extends MJBaseFragment<WithdrawSettlementPresenter> implements WithdrawSettlementContract.View {

    @BindView(R.id.iv_un_select)
    ImageView ivUnSelect;
    @BindView(R.id.tv_un_select)
    TextView tvUnSelect;
    @BindView(R.id.tv_un_select_dec)
    TextView tvUnSelectDec;
    @BindView(R.id.rl_un_select)
    RelativeLayout rlUnSelect;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_select_dec)
    TextView tvSelectDec;
    @BindView(R.id.rl_select)
    RelativeLayout rlSelect;
    @BindView(R.id.tv_select_1)
    TextView tvSelect1;
    @BindView(R.id.tv_select_dec_1)
    TextView tvSelectDec1;
    @BindView(R.id.rl_select_1)
    RelativeLayout rlSelect1;
    @BindView(R.id.iv_un_select_1)
    ImageView ivUnSelect1;
    @BindView(R.id.tv_un_select_1)
    TextView tvUnSelect1;
    @BindView(R.id.tv_un_select_dec_1)
    TextView tvUnSelectDec1;
    @BindView(R.id.rl_un_select_1)
    RelativeLayout rlUnSelect1;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_mid)
    LinearLayout llMid;
    @BindView(R.id.tv_settlement_next)
    TextView tvSettlementNext;
    //2为月结，1日结
    private int isSelectType = 2;
    public static WithdrawResultInterFace wrResultInfter;
    public static WithdrawSettlementFragment newInstance(WithdrawResultInterFace wrResultInfter) {
        WithdrawSettlementFragment fragment = new WithdrawSettlementFragment();
        WithdrawSettlementFragment.wrResultInfter=wrResultInfter;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawSettlementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_settlement, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        ShadowDrawable.setShadowDrawable(rlSelect, new int[]{
                        Color.parseColor("#82DFFF"), Color.parseColor("#73C8EE")}, dpToPx(6),
                Color.parseColor("#9973C8EE"), dpToPx(12), dpToPx(6), dpToPx(6));//997C4DFF

        ShadowDrawable.setShadowDrawable(rlSelect1, new int[]{
                        Color.parseColor("#82DFFF"), Color.parseColor("#73C8EE")}, dpToPx(6),
                Color.parseColor("#9973C8EE"), dpToPx(12), dpToPx(6), dpToPx(6));//997C4DFF
        initEvent();
    }

    private void initEvent() {
        //左日右月 默认月结选中
        rlSelect.setVisibility(View.VISIBLE);
        rlUnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectType=1;
                rlUnSelect.setVisibility(View.GONE);
                rlSelect1.setVisibility(View.VISIBLE);
                rlUnSelect1.setVisibility(View.VISIBLE);
                rlSelect.setVisibility(View.GONE);
            }
        });
        rlUnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectType=2;
                rlUnSelect.setVisibility(View.VISIBLE);
                rlSelect1.setVisibility(View.GONE);
                rlUnSelect1.setVisibility(View.GONE);
                rlSelect.setVisibility(View.VISIBLE);
            }
        });


    }

    @OnClick(R.id.tv_settlement_next)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_settlement_next:
                String token = PreferenceUtil.getInstance(BaseApplication.getInstance()).
                        getString(AppConstants.TOKEN, "");
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("settlement", isSelectType);
                mPresenter.requestWithdrawMsg(map);
                break;
        }
    }


    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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

    private int dpToPx(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
    }


    @Override
    public void onWithdrawMsg(BaseResponse<String> baseResponse) {
        dismissLoadingAnimationDialog();
        wrResultInfter.onWithdrawResultMsg(baseResponse);
    }

}
