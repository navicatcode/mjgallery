package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.List;

/**
 * 介绍奖励规则dialog
 */
public class AwardRuleDialog extends BaseDialog implements View.OnClickListener {

    TextView tvDialogTitle;
    TextView tvUpdateContent;
    TextView tvDialogOk;
    UIImageView tvDialogClose;
    Activity context;

    public AwardRuleDialog(@NonNull Activity context) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.context=context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_award_rule);

        tvDialogTitle = findViewById(R.id.tvDialogTitle);
        tvUpdateContent = findViewById(R.id.tvUpdateContent);
        tvDialogOk = findViewById(R.id.tvDialogOk);
        tvDialogClose = findViewById(R.id.tvDialogClose);
        tvDialogOk.setOnClickListener(this);
        tvDialogClose.setOnClickListener(this);

    }


    public void show(String name) {
        //由于只能文本做为判断，而后台传的文本只有简体，所以直接写死文本在代码中判断
        if("邀请奖励".equals(name)){
            tvDialogTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule1));
            tvUpdateContent.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule2));
        }else if("阅读奖励".equals(name)){
            tvDialogTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule7));
            tvUpdateContent.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule8));
        }else if("发帖奖励".equals(name)){
            tvDialogTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule9));
            tvUpdateContent.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule10));
        }else if("分享奖励".equals(name)){
            tvDialogTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule3));
            tvUpdateContent.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule4));
        }else if("回复奖励".equals(name)){
            tvDialogTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule5));
            tvUpdateContent.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.award_rule6));
        }else{
            return;
        }
        super.show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDialogOk:
            case R.id.tvDialogClose:
                dismiss();
                break;


        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


}
