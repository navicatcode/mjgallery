package com.mjgallery.mjgallery.mvp.ui.adapter.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.CollectionBean;

import java.util.List;


public class CollectionAdapter extends BaseQuickAdapter<CollectionBean, BaseViewHolder> {


    ICollectionDelete iCollectionDelete;

    public CollectionAdapter(int layoutResId, @Nullable List<CollectionBean> data, ICollectionDelete iCollectionDelete) {
        super(layoutResId, data);
        this.iCollectionDelete = iCollectionDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionBean item) {
//        ImageView ivCollectionItemImg = helper.getView(R.id.ivCollectionItemImg);
//        ImageView ivCollectionItemDelete = helper.getView(R.id.ivCollectionItemDelete);
//        TextView tvCollectionItemName = helper.getView(R.id.tvCollectionItemName);
//        TextView tvViewedNumber = helper.getView(R.id.tvViewedNumber);
//        TextView tvCommentsNumber = helper.getView(R.id.tvCommentsNumber);
//        TextView tvCollectionNumber = helper.getView(R.id.tvCollectionNumber);
//        if (!TextUtils.isEmpty(item.getFilePath())) {
//            GlideUtil.loadNormalImage(ivCollectionItemImg, item.getFilePath(), R.drawable.icon_loading);
//        } else {
//            GlideUtil.loadNormalImage(ivCollectionItemImg, R.drawable.icon_loading);
//        }
//
//        tvCollectionItemName.setText(item.getPictureName());
//        tvCollectionNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.number_collection) + item.getCollectionCount() + "人");
//        tvViewedNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.look_number) + item.getClickCount() + "人");
//        tvCommentsNumber.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.comments_number) + item.getCommentCount() + "人");
//        ivCollectionItemDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (iCollectionDelete != null) {
//                    iCollectionDelete.onCollectionAdapterDelete(item.getPictureId(), item.getType(),item.getRelatedId());
//                }
//            }
//        });
    }


    public interface ICollectionDelete {
        void onCollectionAdapterDelete(int pictureId, int type, int relatedId);
    }
}
