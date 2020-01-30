package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;

import static com.mjgallery.mjgallery.app.glide.GlideUtil.loadCircleImage;


/**
 * 提现金额输入Dialog
 * @author 鲁班
 * @time 2018/11/9 0009
 * @describe IOS底部选择对话框
 */
public class WithdrawDepositDialog extends BaseDialog implements View.OnClickListener {

    LinearLayout llWithdrawDeposit;
    LinearLayout llAll;
    TextView tvBalance;
    ImageView ivHeadImg;
    TextView tvUserName;
    EditText etInputMoney;
    TextView tvMoney1;
    TextView tvMoney2;
    TextView tvMoney3;
    EditText etTransPwd;
    TextView tvSure;

    UserInformation userInformation;

    IWithdrawDepositListener iWithdrawDepositListener;

    public WithdrawDepositDialog(Activity activity, IWithdrawDepositListener iWithdrawDepositListener) {
        super(activity, R.style.edit_AlertDialog_style);
        this.iWithdrawDepositListener = iWithdrawDepositListener;
        setCanceledOnTouchOutside(true);
        init();

    }


    private void init() {
        setContentView(R.layout.activity_withdraw_deposit);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        //空白处控件
        llWithdrawDeposit = findViewById(R.id.llWithdrawDeposit);
        llAll = findViewById(R.id.llAll);
        llWithdrawDeposit.setOnClickListener(this);
        tvBalance = findViewById(R.id.tvBalance);
        ivHeadImg = findViewById(R.id.ivHeadImg);
        tvUserName = findViewById(R.id.tvUserName);
        tvMoney1 = findViewById(R.id.tvMoney1);
        tvMoney2 = findViewById(R.id.tvMoney2);
        tvMoney3 = findViewById(R.id.tvMoney3);
        etTransPwd = findViewById(R.id.etTransPwd);
        etInputMoney = findViewById(R.id.etInputMoney);
        tvSure = findViewById(R.id.tvSure);
        tvMoney1.setOnClickListener(this);
        tvMoney2.setOnClickListener(this);
        llAll.setOnClickListener(this);
        tvMoney3.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        //输入金额设置字符过滤
        etInputMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        initMone();
    }


    /**
     * 弹出dialog
     *
     * @return this
     */
    public void showDialog(UserInformation userInformation) {
        this.userInformation = userInformation;
        setValue();
        show();
    }


    public void setValue() {
        if (userInformation == null)
            return;
        tvBalance.setText(userInformation.getUserBalanceAmount() + "");
        tvUserName.setText(userInformation.getNikeName());
        loadCircleImage(ivHeadImg, userInformation.getHeadImg(), R.drawable.mine_user_normal_img);
    }

    /**
     * 初始化监听
     */
    public void initMone() {

        etInputMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean condition1 = source.equals(".") && (dest.toString().length() == 0);
                boolean condition2 = source.equals("0") && (dest.toString().length() == 0);
                if (condition1 || condition2) {
                    return "0.";
                }

                int index = dest.toString().indexOf(".");

                //如果已经有点了，则不于许再增加点
                if (source.equals(".") && index != -1) {

                    return "";
                }

                //如果已经两位小数，不于许再增加一位小数
                if (index != -1) {
                    int length = dest.toString().substring(index).length();
                    if (length == 3 && index < dstart) {
                        return "";
                    }
                }

                //如果个位数字是0，则要增加的数字不能在小数点与零的中间,如：0.13
                if (dest.toString().length() > 0 && "0".equals(dest.toString().substring(0, 1)) && dstart == index) {
                    return "";
                }
                //如果个位已经存在，则不能把零加在最前面，比如0.32
                if (dest.toString().length() > 0 && dstart == 0 && source.equals("0")) {
                    return "";
                }

                //如果大小至少是2位数以上，删除了首位，可后一位是零的话，转成数值类型，把零都删掉，如：10002.23
                if (index > 2 && (dstart == 0 && source.equals("")) && "0".equals(dest.toString().substring(1, 2))) {
                    double d = Double.parseDouble(dest.toString().substring(1));
                    etInputMoney.setText(d + "");
                    return "";
                }

                return null;
            }
        }});

        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etTransPwd.getText().toString().trim().length() >= 6 && editable.toString().length() > 0) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                } else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });

        etTransPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 6 && etInputMoney.getText().toString().length() > 2) {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg);
                } else {
                    tvSure.setBackgroundResource(R.drawable.btn_0eb4fe_8_bg_normal);
                    tvSure.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llWithdrawDeposit:
                dismiss();
                break;
            case R.id.llAll:
                break;
            case R.id.tvMoney1:
                etInputMoney.setText("100.00");
                break;
            case R.id.tvMoney2:
                etInputMoney.setText("200.00");
                break;
            case R.id.tvMoney3:
                etInputMoney.setText("300.00");
                break;
            case R.id.tvSure://提交
                double money = 0.00;
                try {
                    money = Double.parseDouble(etInputMoney.getText().toString());
                    double balance = Double.parseDouble(tvBalance.getText().toString());
                    if (money > balance || money < 100.0) {
                        if (iWithdrawDepositListener != null) {
                            iWithdrawDepositListener.onShowMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdraw_deposit14));
                        }
                        return;
                    }
                    if (iWithdrawDepositListener != null) {
                        iWithdrawDepositListener.onSubmit(etTransPwd.getText().toString(), money + "");
                    }
                } catch (Exception e) {
                    if (iWithdrawDepositListener != null) {
                        iWithdrawDepositListener.onShowMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.withdraw_deposit15));
                    }
                }


                break;
        }
    }

    public interface IWithdrawDepositListener {
        void onSubmit(String password, String money);

        void onShowMessage(String showMessage);

    }

    public void etClear() {
        etInputMoney.setText("");
        etTransPwd.setText("");
    }

}
