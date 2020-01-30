package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;


import android.util.SparseArray;

import androidx.recyclerview.widget.RecyclerView;

import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.ui.adapter.ExpandGroupIndexEntity;
import com.mjgallery.mjgallery.widget.pinnedheader.PinnedHeaderAdapter;

import java.util.List;

public abstract class LotteryRecordRecyclerExpandBaseAdapter
        <VH extends RecyclerView.ViewHolder> extends PinnedHeaderAdapter<VH> {

    protected static final int VIEW_TYPE_ITEM_TIME = 0;
    protected static final int VIEW_TYPE_ITEM_CONTENT = 1;

    protected List<LotteryRecordBean> mDataList;
    protected SparseArray<ExpandGroupIndexEntity> mIndexMap;

    public LotteryRecordRecyclerExpandBaseAdapter() {
        this(null);
    }

    public LotteryRecordRecyclerExpandBaseAdapter(List<LotteryRecordBean> dataList) {
        mDataList = dataList;
        mIndexMap = new SparseArray<>();
    }

    public void setData(List<LotteryRecordBean> dataList) {
        mDataList = dataList;
        mIndexMap.clear();
        notifyDataSetChanged();
    }




    public List<LotteryRecordBean> getData() {
        return mDataList;
    }

    @Override
    public boolean isPinnedPosition(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM_TIME;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (LotteryRecordBean item : mDataList) {
            count = count + 1;
            if (position == count - 1) {
                return VIEW_TYPE_ITEM_TIME;
            }
            if (item.getLotteryRecordDtos() != null) {
                count = count + item.getLotteryRecordDtos().size();
            }
            if (position < count) {
                return VIEW_TYPE_ITEM_CONTENT;
            }
        }
        throw new IllegalArgumentException("getItemViewType exception");
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int group = 0; group < mDataList.size(); group++) {
            LotteryRecordBean item = mDataList.get(group);
            //标题
            count = count + 1;

            mIndexMap.put(count - 1, new ExpandGroupIndexEntity(group, -1, item.getLotteryRecordDtos() == null ? 0 : item.getLotteryRecordDtos().size()));
            int childStartPosition = count;
            if (item.getLotteryRecordDtos() != null) {
                //sub
                count = count + item.getLotteryRecordDtos().size();
            }
            int childEndPosition = count;
            for (int loop = childStartPosition; loop < childEndPosition; loop++) {
                mIndexMap.put(loop, new ExpandGroupIndexEntity(group, loop - childStartPosition,
                        item.getLotteryRecordDtos() == null ? 0 : item.getLotteryRecordDtos().size()));
            }
        }
        return count;
    }

}
