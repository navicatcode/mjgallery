package com.mjgallery.mjgallery.mvp.ui.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineMenuBean;

import java.util.List;

public class MineGridMenuAdapter extends BaseQuickAdapter<MineMenuBean, BaseViewHolder> {
    private Context context;
    IMineGridMenuListener iMineGridMenuListener;

    public MineGridMenuAdapter(Context context, int layoutResId, @Nullable List<MineMenuBean> data, IMineGridMenuListener iMineGridMenuListener) {
        super(layoutResId, data);
        this.context = context;
        this.iMineGridMenuListener = iMineGridMenuListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineMenuBean item) {
        LinearLayout itemAll = helper.getView(R.id.itemAll);
        ImageView ivMineItemImg = helper.getView(R.id.ivMineItemImg);
        TextView tvMineItemName = helper.getView(R.id.tvMineItemName);
        GlideUtil.loadNormalImage(ivMineItemImg, item.getImg());
        tvMineItemName.setText(item.getName());
        itemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMineGridMenuListener!=null){
                    iMineGridMenuListener.onMineGridMenuListener(helper.getLayoutPosition());
                }
            }
        });
    }

    public interface IMineGridMenuListener {
        void onMineGridMenuListener(int position);
    }
}
