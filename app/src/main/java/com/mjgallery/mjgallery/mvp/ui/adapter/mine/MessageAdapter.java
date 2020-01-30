package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;
import com.mjgallery.mjgallery.widget.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private final List<MyShowBean> mDataList = new ArrayList<>();
    private MessagePicturesLayout.Callback mCallback;
    boolean isShow = false;

    public void setiMessageClick(IMessageClick iMessageClick) {
        this.iMessageClick = iMessageClick;
    }

    IMessageClick iMessageClick;

    public MessageAdapter(Context context, boolean isShow) {
        this.isShow = isShow;
    }

    public MessageAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }

    public void set(List<MyShowBean> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iAvatar, ivIFoundXiangXia;
        TextView tNickname, tTime, tContent, tvReadNumber, tvCommentsNumber;
        MessagePicturesLayout lPictures;
        RelativeLayout rlIFoundXiangXia;
        MyShowBean mData;

        ViewHolder(View itemView) {
            super(itemView);
            ivIFoundXiangXia = (ImageView) itemView.findViewById(R.id.ivIFoundXiangXia);
            rlIFoundXiangXia = (RelativeLayout) itemView.findViewById(R.id.rlIFoundXiangXia);
            iAvatar = (ImageView) itemView.findViewById(R.id.i_avatar);
            tvReadNumber = (TextView) itemView.findViewById(R.id.tvReadNumber);
            tvCommentsNumber = (TextView) itemView.findViewById(R.id.tvCommentsNumber);
            tNickname = (TextView) itemView.findViewById(R.id.t_nickname);
            tTime = (TextView) itemView.findViewById(R.id.t_time);
            tContent = (TextView) itemView.findViewById(R.id.t_content);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictures);
            lPictures.setCallback(mCallback);
        }

        void refresh(int pos) {
            mData = mDataList.get(pos);
            if (mData != null) {
                if (mData.getUserDO() != null) {
                    GlideUtil.loadCircleImage(iAvatar, R.drawable.mine_user_normal_img);
                    if (!TextUtils.isEmpty(mData.getUserDO().getHeadImg())) {
                        GlideUtil.loadCircleImage(iAvatar, mData.getUserDO().getHeadImg(), R.drawable.mine_user_normal_img);
                    }
                    tNickname.setText(mData.getUserDO().getNikeName());
                }
                tTime.setText(mData.getCreateTime());
                tContent.setText(mData.getDescription());
                if (mData.getShowPictureDoList() != null) {
                    lPictures.setInit(true);
                    lPictures.set(mData.getShowPictureDoList());

                }
                tvReadNumber.setText(mData.getClickCount() + "");
                tvCommentsNumber.setText(mData.getCommentCount() + "");
            }
            rlIFoundXiangXia.setVisibility(View.GONE);
            if (isShow) {
                rlIFoundXiangXia.setVisibility(View.VISIBLE);
            }

            rlIFoundXiangXia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iMessageClick != null) {
                        iMessageClick.onItemMessageClick(mData, ivIFoundXiangXia);
                    }
                }
            });

            ivIFoundXiangXia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iMessageClick != null) {
                        iMessageClick.onItemMessageClick(mData, ivIFoundXiangXia);
                    }
                }
            });
            iAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iMessageClick != null) {
                        iMessageClick.onImgMessageClick();
                    }
                }
            });

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_main_message, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).refresh(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public interface IMessageClick {
        void onItemMessageClick(MyShowBean myShowBean, View ivIFoundXiangXia);

        void onImgMessageClick();

    }
}
