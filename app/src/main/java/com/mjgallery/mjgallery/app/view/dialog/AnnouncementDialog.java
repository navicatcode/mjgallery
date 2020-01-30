package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mjgallery.mjgallery.R;


public class AnnouncementDialog extends BaseDialog {
    ImageView ivExit;
    TextView tvTitle;

    public AnnouncementDialog(@NonNull Activity context) {
        super(context);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_announcement);
        tvTitle = this.findViewById(R.id.tvTitle);
        ivExit = this.findViewById(R.id.ivExit);
        setCancelable(false);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void showDialog(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        show();
    }
}
