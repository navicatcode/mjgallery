package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverDetailsCommentsItemBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverDetailsCommentsAdapter extends BaseQuickAdapter<DiscoveryCommentsBean.DataBean, BaseViewHolder> {
    Context mContext;
    IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener;
    boolean isDtata = false;//判断当前是否是资料
    public DiscoverDetailsCommentsAdapter(Context context, int layoutResId,
                                          @Nullable List<DiscoveryCommentsBean.DataBean> data
            , IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener, boolean isDtata) {
        super(layoutResId, data);
        this.mContext = context;
        this.isDtata = isDtata;
        this.iDiscoveryDetailsCommentsItemClickListener = iDiscoveryDetailsCommentsItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoveryCommentsBean.DataBean item) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //发布时间
        TextView tvItemTime = helper.getView(R.id.tvItemTime);
        TextView mItemAddIndex = helper.getView(R.id.mItemAddIndex);
        RelativeLayout rlRecyclerView = helper.getView(R.id.rlRecyclerView);
        RecyclerView mRecyclerView = helper.getView(R.id.mRecyclerView);
        //点赞数量
        TextView tvItemGiveLike = helper.getView(R.id.tvItemGiveLike);
        //踩数量
        TextView tvItemNoGiveLike = helper.getView(R.id.tvItemNoGiveLike);
        //点赞图标
        UIImageView ivItemGiveLike = helper.getView(R.id.ivItemGiveLike);
        UIImageView ivItemReply = helper.getView(R.id.ivItemReply);
        //踩图标
        UIImageView ivItemNoGiveLike = helper.getView(R.id.ivItemNoGiveLike);
        //评论内容
        TextView tvPublishContent = helper.getView(R.id.tvPublishContent);
        //发布人图像
        ImageView ivCommentList = helper.getView(R.id.ivCommentList);
        //发布人名字
        TextView tvContentPublisher = helper.getView(R.id.tvContentPublisher);
        rlRecyclerView.setVisibility(View.GONE);
        mItemAddIndex.setVisibility(View.GONE);
        List<DiscoverDetailsCommentsItemBean> discoverDetailsCommentsItemBeans = new ArrayList<>();
        if (item.getSubCommentDetailDos() != null && item.getSubCommentDetailDos().size() > 0) {
            for (int i = 0; i < item.getSubCommentDetailDos().size(); i++) {
                DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos = item.getSubCommentDetailDos().get(i);
                DiscoverDetailsCommentsItemBean discoverDetailsCommentsItemBean = new DiscoverDetailsCommentsItemBean();
                if (item.getSubCommentDetailDos().size() > 1 && i == 0) {
                    discoverDetailsCommentsItemBean.setOpen(true);
                } else {
                    discoverDetailsCommentsItemBean.setOpen(false);
                }
                discoverDetailsCommentsItemBean.setSubCommentDetailDosBean(subCommentDetailDos);
                discoverDetailsCommentsItemBean.setRemainingMessages(item.getSubCommentDetailDos().size() - 1);
                discoverDetailsCommentsItemBeans.add(discoverDetailsCommentsItemBean);
            }

        }

        tvItemTime.setText(item.getCommentTime());
        tvItemGiveLike.setText(item.getThumbUpCount() + "");
        tvItemNoGiveLike.setText(item.getStepOnCount() + "");
        tvPublishContent.setText(item.getCommentContent().trim());
        if (item.getUserDO() != null) {
            tvContentPublisher.setText(item.getUserDO().getNikeName());
            GlideUtil.loadCircleImage(ivCommentList, item.getUserDO().getHeadImg(), R.drawable.mine_user_normal_img);
        }
        GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like);
        GlideUtil.loadNormalImage(ivItemNoGiveLike, R.drawable.item_give_no_like);
        if (item.getIsThumpUp() == 1) {
            GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like_isclick);

        }
        if (item.isStepOn()) {
            GlideUtil.loadNormalImage(ivItemNoGiveLike, R.drawable.item_give_no_like_isclick);
        }

        if (discoverDetailsCommentsItemBeans.size() > 0) {
            rlRecyclerView.setVisibility(View.VISIBLE);
            int count = item.getPageInfo().getTotal() - discoverDetailsCommentsItemBeans.size();
            if (count > 0) {
                mItemAddIndex.setVisibility(View.VISIBLE);
                mItemAddIndex.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.titleone)+count+ArmsUtils.getString(BaseApplication.getInstance(),R.string.titletwo));
            }
        }
        DiscoverDetailsCommentsItemAdapter discoverDetailsCommentsItemAdapter = new
                DiscoverDetailsCommentsItemAdapter(R.layout.adapter_discovery_details_comments_item_item,
                discoverDetailsCommentsItemBeans, new DiscoverDetailsCommentsItemAdapter.IDiscoveryDetailsCommentsItemClickListener() {
            @Override
            public void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int itemPosition) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemClickListener(item, subCommentDetailDos, item.getCommentId(), helper.getLayoutPosition(), itemPosition);
                }
            }

            @Override
            public void onDiscoveryDetailsCommentsItemSwitchInformation(int userId) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                        iDiscoveryDetailsCommentsItemClickListener.
                                onDiscoveryUserHisInformation(userId);
                }
            }

        });

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(discoverDetailsCommentsItemAdapter);
        tvItemTime.setText(item.getCommentTime());
        tvItemGiveLike.setText(item.getThumbUpCount() + "");
        tvPublishContent.setText(item.getCommentContent().trim());
        tvPublishContent.setText(item.getCommentContent().trim());
        if (item.getUserDO() != null) {
            tvContentPublisher.setText(item.getUserDO().getNikeName());
            if (TextUtils.isEmpty(item.getUserDO().getHeadImg())) {
                GlideUtil.loadCircleImage(ivCommentList, R.drawable.mine_user_normal_img);
            } else {
                GlideUtil.loadCircleImage(ivCommentList, item.getUserDO().getHeadImg(), R.drawable.mine_user_normal_img);
            }

        }
        GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like);
        if (item.getIsThumpUp() == 1) {
            GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like_isclick);

        }
        tvPublishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.
                            onDiscoveryDetailsCommentsClickListener(item, helper.getLayoutPosition());
                }
            }
        });
        ivItemGiveLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.
                            onDiscoveryDetailsCommentsGiveLike(item, helper.getLayoutPosition());
                }
            }
        });
        mItemAddIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onAddCommentsClickListener(item.getCommentId(), helper.getLayoutPosition());
                }
            }
        });
        ivItemNoGiveLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.
                            onDiscoveryDetailsCommentsGiveNoLike(item, helper.getLayoutPosition());
                }
            }
        });

        ivItemReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.
                            onDiscoveryDetailsCommentsReply(item, helper.getLayoutPosition());
                }
            }
        });
        ivCommentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    if (item.getUserDO() != null) {
                        iDiscoveryDetailsCommentsItemClickListener.
                                onDiscoveryUserHisInformation(item.getUserDO().getId());
                    }

                }
            }
        });

    }


    public interface IDiscoveryDetailsCommentsItemClickListener {
        void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean dataBean, DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition);

        void onDiscoveryDetailsCommentsClickListener(DiscoveryCommentsBean.DataBean dataBean, int position);

        void onDiscoveryDetailsCommentsGiveLike(DiscoveryCommentsBean.DataBean dataBean, int position);

        void onAddCommentsClickListener(int itemTypeId, int position);


        void onDiscoveryDetailsCommentsGiveNoLike(DiscoveryCommentsBean.DataBean dataBean, int position);

        void onDiscoveryDetailsCommentsReply(DiscoveryCommentsBean.DataBean dataBean, int position);

        void onDiscoveryUserHisInformation(int userID);
    }

}
