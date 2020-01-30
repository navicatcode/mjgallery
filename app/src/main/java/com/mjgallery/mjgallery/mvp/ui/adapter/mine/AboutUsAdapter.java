package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.model.bean.AboutUsBean;

import java.util.List;

public class AboutUsAdapter extends BaseQuickAdapter<AboutUsBean, BaseViewHolder> {
    IAboutUsAdapterClickListener iAboutUsAdapterClickListener;
    public AboutUsAdapter(int layoutResId, @Nullable List<AboutUsBean> data,IAboutUsAdapterClickListener iAboutUsAdapterClickListener) {
        super(layoutResId, data);
        this.iAboutUsAdapterClickListener=iAboutUsAdapterClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AboutUsBean item) {
        helper.setText(R.id.tvAboutUsItemTitle, item.getName());
        TextView tvAboutUsItemValue=helper.getView(R.id.tvAboutUsItemValue);
        tvAboutUsItemValue.setText(item.getValue());
        RelativeLayout rlAboutUsItemTitle=helper.getView(R.id.rlAboutUsItemTitle);
        tvAboutUsItemValue.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_4D4D4D));
        if(item.isUrl()){
            tvAboutUsItemValue.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0AAFFA));
        }
        rlAboutUsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iAboutUsAdapterClickListener!=null){
                    iAboutUsAdapterClickListener.onAboutUsAdapterClickListener(item);
                }
            }
        });
    }

    public interface IAboutUsAdapterClickListener{
        void onAboutUsAdapterClickListener(AboutUsBean aboutUsBean);
    }
}
