package com.mjgallery.mjgallery.mvp.ui.adapter.search;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchUserBean;

import java.util.List;

public class SearchUserAdapter extends BaseQuickAdapter<SearchUserBean, BaseViewHolder> {
    ISearchUser mISearchUser;

    public SearchUserAdapter(int layoutResId, @Nullable List<SearchUserBean> data, ISearchUser mISearchUser) {
        super(layoutResId, data);
        this.mISearchUser = mISearchUser;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchUserBean item) {
        ImageView ivSearchUserImg = helper.getView(R.id.ivSearchUserImg);
        LinearLayout llSearchUser = helper.getView(R.id.llSearchUser);
        TextView tvSearchUserName = helper.getView(R.id.tvSearchUserName);
        tvSearchUserName.setText(item.getNikeName());
        llSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mISearchUser != null) {
                    mISearchUser.onSearchUserClick(item.getId());
                }
            }
        });
        GlideUtil.loadCircleImage(ivSearchUserImg, item.getHeadImg(), R.drawable.mine_user_normal_img);
    }

    public interface ISearchUser {
        void onSearchUserClick(int id);
    }
}
