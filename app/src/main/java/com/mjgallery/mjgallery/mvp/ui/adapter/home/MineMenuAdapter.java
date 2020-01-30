package com.mjgallery.mjgallery.mvp.ui.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineMenuBean;

import java.util.List;

public class MineMenuAdapter extends BaseQuickAdapter<MineMenuBean, BaseViewHolder> {
    private Context context;
    IMineMenuListener iMineMenuListener;
    public boolean hasNewNotify=false;

    public MineMenuAdapter(Context context, int layoutResId, @Nullable List<MineMenuBean> data, IMineMenuListener iMineMenuListener) {
        super(layoutResId, data);
        this.context = context;
        this.iMineMenuListener = iMineMenuListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineMenuBean item) {
        RelativeLayout itemAll = helper.getView(R.id.itemAll);
        ImageView ivMineItemImg = helper.getView(R.id.ivMineItemImg);
        TextView tvMineItemName = helper.getView(R.id.tvMineItemName);
        TextView tvVersionSign = helper.getView(R.id.tvVersionSign);
        GlideUtil.loadNormalImage(ivMineItemImg, item.getImg());
        tvMineItemName.setText(item.getName());

        if(hasNewNotify && (ArmsUtils.getString(BaseApplication.getInstance(), R.string.my_news)).equals(item.getName())){
            tvVersionSign.setVisibility(View.VISIBLE);
        }else{
            tvVersionSign.setVisibility(View.GONE);
        }
        itemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMineMenuListener!=null){
                    iMineMenuListener.onMineMenuListener(helper.getLayoutPosition());
                }
            }
        });
    }

    public interface IMineMenuListener {
        void onMineMenuListener(int position);
    }
}
