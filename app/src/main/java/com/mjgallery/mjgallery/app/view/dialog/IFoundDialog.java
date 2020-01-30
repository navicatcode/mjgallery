package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;


import butterknife.OnClick;

public class IFoundDialog extends BaseDialog implements View.OnClickListener {

    LinearLayout llBianJi;
    LinearLayout llShouChang;
    LinearLayout llDelete;
    LinearLayout llAll;
    LinearLayout llAllTwo;
    IFoundDialogClickListener iFoundDialogClickListener;
    MyShowBean mMyShowBean;

    public IFoundDialog(Activity activity,IFoundDialogClickListener iFoundDialogClickListener) {
        super(activity);
        this.iFoundDialogClickListener=iFoundDialogClickListener;
        setCanceledOnTouchOutside(true);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_i_found);
        llAll=findViewById(R.id.llAll);
        llAllTwo=findViewById(R.id.llAllTwo);
        llDelete=findViewById(R.id.llDelete);
        llShouChang=findViewById(R.id.llShouChang);
        llBianJi=findViewById(R.id.llBianJi);
        llAll.setOnClickListener(this);
        llAllTwo.setOnClickListener(this);
        llDelete.setOnClickListener(this);
        llShouChang.setOnClickListener(this);
        llBianJi.setOnClickListener(this);
    }

    @OnClick({R.id.llBianJi, R.id.llAllTwo,R.id.llShouChang, R.id.llDelete, R.id.llAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBianJi:
                if(iFoundDialogClickListener!=null){
                    iFoundDialogClickListener.onBianJi(mMyShowBean);
                    dismiss();
                }
                break;
            case R.id.llShouChang:
                if(iFoundDialogClickListener!=null){
                    iFoundDialogClickListener.onShouChang(mMyShowBean);
                    dismiss();
                }
                break;
            case R.id.llDelete:
                if(iFoundDialogClickListener!=null){
                    iFoundDialogClickListener.onDelete(mMyShowBean);
                    dismiss();
                }
                break;
            case R.id.llAll:
            case R.id.llAllTwo:
                break;
        }
    }


    public void show(MyShowBean myShowBean,View view){
          this.mMyShowBean=myShowBean;
          showViewBottom(view);
    }

    public interface IFoundDialogClickListener{
        void onShouChang(MyShowBean myShowBean);
        void onDelete(MyShowBean myShowBean);
        void onBianJi(MyShowBean myShowBean);
    }
}
