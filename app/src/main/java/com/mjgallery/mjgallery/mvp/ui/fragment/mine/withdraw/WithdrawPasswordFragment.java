package com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.mjgallery.mjgallery.di.component.DaggerWithdrawPasswordComponent;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawPasswordContract;
import com.mjgallery.mjgallery.mvp.presenter.withdraw.WithdrawPasswordPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:设置交易密码
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawPasswordFragment extends MJBaseFragment<WithdrawPasswordPresenter> implements WithdrawPasswordContract.View {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_sure)
    EditText etSure;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    public static WithdrawResultInterFace wrResultInfter;

    public static WithdrawPasswordFragment newInstance(WithdrawResultInterFace wrResultInfter) {
        WithdrawPasswordFragment fragment = new WithdrawPasswordFragment();
        WithdrawPasswordFragment.wrResultInfter=wrResultInfter;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawPasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_password, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvSure.setEnabled(false);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString()) && editable.toString().trim().length()>=6 && editable.toString().trim().equals(etSure.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });

        etSure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString()) && editable.toString().trim().length()>=6 && editable.toString().trim().equals(etPassword.getText().toString().trim())) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                }else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });
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

    @Override
    public void onWithdrawMsg(BaseResponse<String> baseResponse) {
        dismissLoadingAnimationDialog();
        wrResultInfter.onWithdrawResultMsg(baseResponse);
    }

    @OnClick(R.id.tv_sure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                    //由于输入框已限止了只能输入数字与字母，所有剩下只要判断长度就好了
                    if(etPassword.getText().toString().trim().length()>15)
                        showMessage(this.getString(R.string.set_pwd_eror));
                    else
                        isLogin();
                break;
        }
    }


    @Override
    protected void requestData() {
        super.requestData();
            Map<String, Object> map = new HashMap<>();
            map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).
                    getString(AppConstants.TOKEN, ""));
            map.put("password",  etPassword.getText().toString().trim());
            mPresenter.requestWithdrawMsg(map);
    }

}
