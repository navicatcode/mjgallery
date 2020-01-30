package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mjgallery.mjgallery.R;

import butterknife.OnClick;

/**
 * 结算说明
 */
public class BillingInstructionsDialog extends BaseDialog implements View.OnClickListener {
    LinearLayout llAllTwo;
    LinearLayout llAll;
    ImageView ivBillingInstructionsClose;

    public BillingInstructionsDialog(Activity activity) {
        super(activity);
        setCanceledOnTouchOutside(true);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_billin_instructions);
        llAllTwo = findViewById(R.id.llAllTwo);
        llAll = findViewById(R.id.llAll);
        ivBillingInstructionsClose = findViewById(R.id.ivBillingInstructionsClose);
        llAllTwo.setOnClickListener(this);
        llAll.setOnClickListener(this);
        ivBillingInstructionsClose.setOnClickListener(this);

    }

    @OnClick({R.id.llAllTwo, R.id.llAll, R.id.ivBillingInstructionsClose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llAllTwo:
                break;
            case R.id.llAll:
            case R.id.ivBillingInstructionsClose:
                dismiss();
                break;
        }
    }
}
