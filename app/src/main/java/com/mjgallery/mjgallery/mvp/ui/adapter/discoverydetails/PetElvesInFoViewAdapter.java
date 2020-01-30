package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.barnettwong.autofitcolortextview_library.AutoFitColorTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.discoverdetailsdata.PetElvesInFoViewBean;

import java.math.BigDecimal;
import java.util.List;

public class PetElvesInFoViewAdapter extends BaseQuickAdapter<PetElvesInFoViewBean.DataBean, BaseViewHolder> {
    IPetElvesInFoViewClickListener iPetElvesInFoViewClickListener;

    public PetElvesInFoViewAdapter(int layoutResId, @Nullable List<PetElvesInFoViewBean.DataBean> data, IPetElvesInFoViewClickListener iPetElvesInFoViewClickListener) {
        super(layoutResId, data);
        this.iPetElvesInFoViewClickListener = iPetElvesInFoViewClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PetElvesInFoViewBean.DataBean item) {
        ImageView ivPetElvesBg = helper.getView(R.id.ivPetElvesBg);
        ImageView ivPetElvesOne = helper.getView(R.id.ivPetElvesOne);
        ImageView ivPetElvesTwo = helper.getView(R.id.ivPetElvesTwo);
        TextView tvPetElvesTitle = helper.getView(R.id.tvPetElvesTitle);
        AutoFitColorTextView tvPetElvesTitleTwo = helper.getView(R.id.tvPetElvesTitleTwo);
        TextView tvPetElvesTime = helper.getView(R.id.tvPetElvesTime);
        GlideUtil.loadNormalImage(ivPetElvesBg, R.drawable.pet_elves_item_normal_bg);
        ivPetElvesOne.setVisibility(View.GONE);
        ivPetElvesTwo.setVisibility(View.GONE);
        tvPetElvesTitleTwo.setVisibility(View.GONE);
        if (item.getShowInfoDo() != null) {
            if (item.getShowInfoDo().isHotInfo() && item.getShowInfoDo().isNewInfo()) {
                GlideUtil.loadNormalImage(ivPetElvesBg, R.drawable.pet_elves_item_re_bg);
                GlideUtil.loadNormalImage(ivPetElvesOne, R.drawable.pet_elves_re_img);
                GlideUtil.loadNormalImage(ivPetElvesTwo, R.drawable.pet_elves_new_img);
                ivPetElvesOne.setVisibility(View.VISIBLE);
                ivPetElvesTwo.setVisibility(View.VISIBLE);
            } else if (item.getShowInfoDo().isHotInfo()) {
                GlideUtil.loadNormalImage(ivPetElvesBg, R.drawable.pet_elves_item_re_bg);
                GlideUtil.loadNormalImage(ivPetElvesOne, R.drawable.pet_elves_re_img);
                ivPetElvesOne.setVisibility(View.VISIBLE);
            } else if (item.getShowInfoDo().isNewInfo()) {
                GlideUtil.loadNormalImage(ivPetElvesBg, R.drawable.pet_elves_item_new_bg);
                GlideUtil.loadNormalImage(ivPetElvesOne, R.drawable.pet_elves_new_img);
                ivPetElvesOne.setVisibility(View.VISIBLE);
            } else {
                GlideUtil.loadNormalImage(ivPetElvesOne, R.drawable.pet_elves_normal_img);
                ivPetElvesOne.setVisibility(View.VISIBLE);
                tvPetElvesTitleTwo.setVisibility(View.VISIBLE);
                tvPetElvesTitleTwo.setText(helper.getLayoutPosition() + 1 + "");
            }
        }
        if (item.getClickCount() > 10000) {
            BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
            BigDecimal bigDecimalChu = new BigDecimal(10000);
            float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
            helper.setText(R.id.tvPetElvesViews, clickCount + ArmsUtils.getString(helper.itemView.getContext(),R.string.wan));

        } else {
            helper.setText(R.id.tvPetElvesViews, item.getClickCount() + "");
        }
        tvPetElvesTitle.setText(item.getShowInfoDo().getTitle());
        tvPetElvesTime.setText(item.getCreateTime());

    }

    public interface IPetElvesInFoViewClickListener {
        void onPetElvesInFoViewCollectionClickListener(int isCollection, int imgId);
    }
}
