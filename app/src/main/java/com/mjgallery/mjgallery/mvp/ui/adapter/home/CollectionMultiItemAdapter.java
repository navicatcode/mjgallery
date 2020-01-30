package com.mjgallery.mjgallery.mvp.ui.adapter.home;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.CollectionBean;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.List;

import static com.mjgallery.mjgallery.app.AppConstants.FOUND;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_DATA;
import static com.mjgallery.mjgallery.app.AppConstants.FOUND_VIDEO;
import static com.mjgallery.mjgallery.app.AppConstants.HOME;


public class CollectionMultiItemAdapter extends BaseMultiItemQuickAdapter<CollectionBean, BaseViewHolder> {
    ICollectionDelete mICollectionDelete;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CollectionMultiItemAdapter(List<CollectionBean> data, ICollectionDelete mICollectionDelete) {
        super(data);
        this.mICollectionDelete = mICollectionDelete;
        addItemType(FOUND_DATA, R.layout.adapter_collection_multi_item_discovery_data);
        addItemType(FOUND_VIDEO, R.layout.adapter_collection_multi_item_discovery_video);
        addItemType(FOUND, R.layout.adapter_collection_multi_item_discovery_img);
        addItemType(HOME, R.layout.adapter_collection_multi_item_home_img);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CollectionBean item) {
        switch (helper.getItemViewType()) {
            case FOUND://发现图片
                UIImageView ivCollectionMultiItemDiscoveryImgDelete = helper.getView(R.id.ivCollectionMultiItemDiscoveryImgDelete);
                ImageView ivCollectionMultiItemDiscoveryImg = helper.getView(R.id.ivCollectionMultiItemDiscoveryImg);
                TextView tvCollectionMultiItemDiscoveryImgTitle = helper.getView(R.id.tvCollectionMultiItemDiscoveryImgTitle);
                TextView tvCollectionMultiItemDiscoveryTime = helper.getView(R.id.tvCollectionMultiItemDiscoveryTime);
                if (ivCollectionMultiItemDiscoveryImg != null) {
                    GlideUtil.imageNormalLoader(ivCollectionMultiItemDiscoveryImg, item.getFilePath(), R.drawable.icon_loading);
//                    ivCollectionMultiItemDiscoveryImg.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mICollectionDelete != null) {
//                                mICollectionDelete.onCollectionAdapterImg(ivCollectionMultiItemDiscoveryImg, item.getFilePath());
//                            }
//                        }
//                    });
                }

                if (tvCollectionMultiItemDiscoveryImgTitle != null) {
                    tvCollectionMultiItemDiscoveryImgTitle.setText(item.getPictureName());
                }
                if (tvCollectionMultiItemDiscoveryTime != null) {
                    tvCollectionMultiItemDiscoveryTime.setText(item.getCreateTime());
                }
                if (ivCollectionMultiItemDiscoveryImgDelete != null) {
                    ivCollectionMultiItemDiscoveryImgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mICollectionDelete != null) {
                                mICollectionDelete.onCollectionAdapterDelete(item.getPictureId(), item.getType(), item.getRelatedId());
                            }
                        }
                    });
                }

                break;
            case HOME:// 首页图片
                TextView tvCollectionMultiItemHomeTime = helper.getView(R.id.tvCollectionMultiItemHomeTime);
                TextView tvCollectionMultiItemHomeImgTitle = helper.getView(R.id.tvCollectionMultiItemHomeImgTitle);
                ImageView ivCollectionMultiItemHomeImg = helper.getView(R.id.ivCollectionMultiItemHomeImg);
                UIImageView ivCollectionMultiItemHomeImgDelete = helper.getView(R.id.ivCollectionMultiItemHomeImgDelete);
                if (ivCollectionMultiItemHomeImgDelete != null) {
                    GlideUtil.imageNormalLoader(ivCollectionMultiItemHomeImg, item.getFilePath(), R.drawable.icon_loading);
//                    ivCollectionMultiItemHomeImg.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mICollectionDelete != null) {
//                                mICollectionDelete.onCollectionAdapterImg(ivCollectionMultiItemHomeImg, item.getFilePath());
//                            }
//                        }
//                    });
                }
                if (tvCollectionMultiItemHomeImgTitle != null) {
                    tvCollectionMultiItemHomeImgTitle.setText(item.getPictureName());
                }
                if (tvCollectionMultiItemHomeTime != null) {
                    tvCollectionMultiItemHomeTime.setText(item.getCreateTime());
                }
                if (ivCollectionMultiItemHomeImgDelete != null) {
                    ivCollectionMultiItemHomeImgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mICollectionDelete != null) {
                                mICollectionDelete.onCollectionAdapterDelete(item.getPictureId(), item.getType(), item.getRelatedId());
                            }
                        }
                    });
                }


                break;
            case FOUND_VIDEO://视频暂时没有接入
                break;
            case FOUND_DATA://资料界面
                TextView tvCollectionMultiItemDiscoveryDataTime = helper.getView(R.id.tvCollectionMultiItemDiscoveryDataTime);
                UIImageView ivCollectionMultiItemDiscoveryDataDelete = helper.getView(R.id.ivCollectionMultiItemDiscoveryDataDelete);
                TextView tvCollectionMultiItemDiscoveryDataTitle = helper.getView(R.id.tvCollectionMultiItemDiscoveryDataTitle);
                TextView tvCollectionMultiItemDiscoveryContent = helper.getView(R.id.tvCollectionMultiItemDiscoveryContent);
                if (tvCollectionMultiItemDiscoveryDataTitle != null) {
                    tvCollectionMultiItemDiscoveryDataTitle.setText(item.getPictureName());
                }
                if (tvCollectionMultiItemDiscoveryDataTime != null) {
                    tvCollectionMultiItemDiscoveryDataTime.setText(item.getCreateTime());
                }
                if (tvCollectionMultiItemDiscoveryContent != null && !TextUtils.isEmpty(item.getContent())) {
                    tvCollectionMultiItemDiscoveryContent.setText(Html.fromHtml(item.getContent()));
                }
                tvCollectionMultiItemDiscoveryDataTitle.setText(item.getPictureName());
                if (ivCollectionMultiItemDiscoveryDataDelete != null) {
                    ivCollectionMultiItemDiscoveryDataDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mICollectionDelete != null) {
                                mICollectionDelete.onCollectionAdapterDelete(item.getPictureId(), item.getType(), item.getRelatedId());
                            }
                        }
                    });
                }
                break;
        }

    }

    public interface ICollectionDelete {
        void onCollectionAdapterDelete(int pictureId, int type, int relatedId);

        void onCollectionAdapterImg(ImageView imageView, String url);
    }

}
