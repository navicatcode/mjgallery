package com.mjgallery.mjgallery.mvp.ui.adapter.home;

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
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.homedetailscomments.HomeDetailsCommentsItemBean;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeDetailsCommentsAdapter extends BaseQuickAdapter<HomeDetailsCommentsBean.DataBean, BaseViewHolder> {
    Context mContext;
    IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener;

    public HomeDetailsCommentsAdapter(Context context, int layoutResId,
                                      @Nullable List<HomeDetailsCommentsBean.DataBean> data
            , IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener) {
        super(layoutResId, data);
        this.mContext = context;
        this.iDiscoveryDetailsCommentsItemClickListener = iDiscoveryDetailsCommentsItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeDetailsCommentsBean.DataBean item) {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        List<HomeDetailsCommentsItemBean> homeDetailsCommentsItemBeans = new ArrayList<>();
        if (item.getSubCommentDetailDos() != null && item.getSubCommentDetailDos().size() > 0) {
            for (int i = 0; i < item.getSubCommentDetailDos().size(); i++) {
                HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos = item.getSubCommentDetailDos().get(i);
                HomeDetailsCommentsItemBean homeDetailsCommentsItemBean = new HomeDetailsCommentsItemBean();
                if (item.getSubCommentDetailDos().size() > 1 && i == 0) {
                    homeDetailsCommentsItemBean.setOpen(true);
                } else {
                    homeDetailsCommentsItemBean.setOpen(false);
                }
                homeDetailsCommentsItemBean.setSubCommentDetailDosBean(subCommentDetailDos);
                homeDetailsCommentsItemBean.setRemainingMessages(item.getSubCommentDetailDos().size() - 1);
                homeDetailsCommentsItemBeans.add(homeDetailsCommentsItemBean);
            }

        }

        if (homeDetailsCommentsItemBeans.size() > 0) {
            rlRecyclerView.setVisibility(View.VISIBLE);
            if (item.getPageInfo()!=null) {
                int count = item.getPageInfo().getTotal() - homeDetailsCommentsItemBeans.size();
                if (count > 0) {
                    mItemAddIndex.setVisibility(View.VISIBLE);
                    mItemAddIndex.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.titleone) + count +
                            ArmsUtils.getString(BaseApplication.getInstance(),R.string.titletwo));
                }
            }
        }
        HomeDetailsCommentsItemAdapter homeDetailsCommentsItemAdapter = new
                HomeDetailsCommentsItemAdapter(R.layout.adapter_discovery_details_comments_item_item,
                homeDetailsCommentsItemBeans, new HomeDetailsCommentsItemAdapter.IDiscoveryDetailsCommentsItemClickListener() {
            @Override
            public void onDiscoveryDetailsCommentsItemClickListener(HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int position) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemClickListener(item, subCommentDetailDos, item.getCommentId(), helper.getLayoutPosition(), position);
                }
            }

            @Override
            public void onDiscoveryDetailsCommentsItemSwitchInformation(int userId) {
                if (iDiscoveryDetailsCommentsItemClickListener != null)
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryUserHisInformation(userId);
            }
        });

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(homeDetailsCommentsItemAdapter);
        tvItemTime.setText(item.getCommentTime());
        tvItemGiveLike.setText(item.getThumbUpCount() + "");
        tvItemNoGiveLike.setText(item.getStepOnCount() + "");
        tvPublishContent.setText(item.getCommentContent().trim());
        HomeDetailsCommentsBean.DataBean.UserDOBeanX userDOBeanX = item.getUserDO();
        if (userDOBeanX != null) {
            tvContentPublisher.setText(item.getUserDO().getNikeName());
            if (TextUtils.isEmpty(userDOBeanX.getHeadImg())) {
                GlideUtil.loadCircleImage(ivCommentList, R.drawable.mine_user_normal_img);
            } else {
                GlideUtil.loadCircleImage(ivCommentList, item.getUserDO().getHeadImg(), R.drawable.mine_user_normal_img);
            }

        }
        GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like);
        GlideUtil.loadNormalImage(ivItemNoGiveLike, R.drawable.item_give_no_like);
        if (item.getIsThumpUp() == 1) {
            GlideUtil.loadNormalImage(ivItemGiveLike, R.drawable.item_give_like_isclick);

        }
        if (item.isStepOn()) {
            GlideUtil.loadNormalImage(ivItemNoGiveLike, R.drawable.item_give_no_like_isclick);
        }

        tvPublishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsClickListener(item, helper.getLayoutPosition());
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
        ivItemGiveLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.
                            onDiscoveryDetailsCommentsGiveLike(item, helper.getLayoutPosition());
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
        void onDiscoveryDetailsCommentsItemClickListener(HomeDetailsCommentsBean.DataBean dataBean, HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int commentId, int position, int itemPosition);

        void onDiscoveryDetailsCommentsClickListener(HomeDetailsCommentsBean.DataBean subCommentDetailDos, int position);

        void onAddCommentsClickListener(int itemTypeId, int position);

        void onDiscoveryDetailsCommentsGiveLike(HomeDetailsCommentsBean.DataBean dataBean, int position);

        void onDiscoveryDetailsCommentsGiveNoLike(HomeDetailsCommentsBean.DataBean dataBean, int position);

        void onDiscoveryDetailsCommentsReply(HomeDetailsCommentsBean.DataBean dataBean, int position);

        void onDiscoveryUserHisInformation(int userID);
    }

}
