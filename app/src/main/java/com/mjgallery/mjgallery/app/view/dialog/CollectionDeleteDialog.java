package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mjgallery.mjgallery.R;

import butterknife.OnClick;

/**
 * 删除登陆弹窗
 */
public class CollectionDeleteDialog extends BaseDialog implements View.OnClickListener {

    Button btnCancel;
    Button btnCommit;
    LinearLayout llAll;
    ICollectionDelete iCollectionDelete;
    private int index;
    private int relatedId;
    private int type;

    public CollectionDeleteDialog(Activity activity, ICollectionDelete iCollectionDelete) {
        super(activity);
        this.iCollectionDelete = iCollectionDelete;
        init();
        setCanceledOnTouchOutside(true);
    }

    private void init() {
        setContentView(R.layout.dialog_collection_delect);
        btnCancel = findViewById(R.id.btnCancel);
        btnCommit = findViewById(R.id.btnCommit);
        llAll = findViewById(R.id.llAll);
        btnCancel.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        llAll.setOnClickListener(this);
    }


    @OnClick({R.id.btnCancel, R.id.btnCommit, R.id.llAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnCommit:
                if (iCollectionDelete != null) {
                    iCollectionDelete.onCollectionDelete(index,type,relatedId);
                    dismiss();
                }
                break;
            case R.id.llAll:

                break;
        }
    }

    public void show(int index,int type,int relatedId) {
        this.index = index;
        this.type = type;
        this.relatedId=relatedId;
        super.show();

    }

    public interface ICollectionDelete {
        void onCollectionDelete(int index, int type, int relatedId);
    }
}
