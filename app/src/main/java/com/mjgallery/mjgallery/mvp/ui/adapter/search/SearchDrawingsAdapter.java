package com.mjgallery.mjgallery.mvp.ui.adapter.search;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchDrawingsBean;

import java.util.List;

public class SearchDrawingsAdapter extends BaseQuickAdapter<SearchDrawingsBean, BaseViewHolder> {
    ISearchDrawings mISearchDrawings;

    public SearchDrawingsAdapter(int layoutResId, @Nullable List<SearchDrawingsBean> data, ISearchDrawings mISearchDrawings) {
        super(layoutResId, data);
        this.mISearchDrawings = mISearchDrawings;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchDrawingsBean item) {
        TextView tvSearchDrawingsItemTitle = helper.getView(R.id.tvSearchDrawingsItemTitle);
        LinearLayout llSearchDrawingsItemTitle = helper.getView(R.id.llSearchDrawingsItemTitle);
        tvSearchDrawingsItemTitle.setText(item.getName());
        llSearchDrawingsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mISearchDrawings!=null){
                    mISearchDrawings.onSearchDrawingsClick(item);
                }
            }
        });
    }

    public interface ISearchDrawings {
        void onSearchDrawingsClick(SearchDrawingsBean searchDrawingsBean);
    }
}
