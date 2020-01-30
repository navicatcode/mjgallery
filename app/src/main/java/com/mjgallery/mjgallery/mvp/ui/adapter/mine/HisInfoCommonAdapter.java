package com.mjgallery.mjgallery.mvp.ui.adapter.mine;


import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.mvp.model.bean.mine.GetUserInfoBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 他的评论adapter
 */
public class HisInfoCommonAdapter extends BaseQuickAdapter<GetUserInfoBean, BaseViewHolder> {

    OnClickDetailsListener onClickDetailsListener;
    public HisInfoCommonAdapter(int layoutResId, @Nullable List<GetUserInfoBean> data,OnClickDetailsListener onClickDetailsListener) {
        super(layoutResId, data);
        this.onClickDetailsListener=onClickDetailsListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GetUserInfoBean item) {

        TextView tvTime = helper.getView(R.id.tvTime);
        TextView tvContent = helper.getView(R.id.tvContent);
        TextView tvTieZhiName = helper.getView(R.id.tvTieZhiName);
        TextView commonTxt = helper.getView(R.id.commonTxt);
        ImageView commonImg = helper.getView(R.id.commonImg);
        TextView commonTime = helper.getView(R.id.commonTime);
        if (item.getClickCount() > 10000) {
            BigDecimal bigDecimalClickCount = new BigDecimal(item.getClickCount());
            BigDecimal bigDecimalChu = new BigDecimal(10000);
            float clickCount = bigDecimalClickCount.divide(bigDecimalChu).setScale(1, BigDecimal.ROUND_DOWN).floatValue();
            helper.setText(R.id.tvBrowseNumber,clickCount + ArmsUtils.getString(helper.itemView.getContext(), R.string.wan));
        } else {
            helper.setText(R.id.tvBrowseNumber,item.getClickCount() + "");
        }
        TextView tvCommentsNumber = helper.getView(R.id.tvCommentsNumber);
        TextView tvLikeNumber = helper.getView(R.id.tvLikeNumber);
        LinearLayout llTieZhiInfo= helper.getView(R.id.rlTieZhiInfo);

        tvTime.setText(item.getCommentTime());
        tvContent.setText(item.getCommentContent());
        tvTieZhiName.setText(item.getTitle());
        commonTime.setText(item.getCreateTime());
        tvCommentsNumber.setText(item.getCommentCount()+"");
        tvLikeNumber.setText(item.getThumbUpCount()+"");
        ViewGroup.LayoutParams layoutParams = commonImg.getLayoutParams();
        int height = item.getHeight();
        int width = item.getWidth();
        layoutParams.height = (int) (commonImg.getWidth() / (width * 1.0 / height));
        if("1".equals(item.getCommentType())){
            GlideUtil.loadCircleImage(commonImg,item.getFilePath(), R.drawable.icon_loading);

            setVisibility(commonImg,View.VISIBLE,commonTxt,View.GONE);
        }else{
            //0-图片，1-视频，2-资料
            if(item.getShowType()==0){
                GlideUtil.loadCircleImage(commonImg,item.getFilePath(), R.drawable.icon_loading);
                setVisibility(commonImg,View.VISIBLE,commonTxt,View.GONE);
            }else if(item.getShowType()==1){
                commonTxt.setText("视频琏接已失效...");
                setVisibility(commonImg,View.GONE,commonTxt,View.VISIBLE);
            }else{
                commonTxt.setText(Html.fromHtml(item.getContent()));
                setVisibility(commonImg,View.GONE,commonTxt,View.VISIBLE);
            }
        }

        llTieZhiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickDetailsListener.OnClickDetails(item.getCommentType(),item.getShowType(),item.getCommentUuid());

            }
        });
    }


    public void setVisibility(ImageView commonImg,int commonImgStatus,TextView commonTxt,int commonTxtStatus){
        commonImg.setVisibility(commonImgStatus);
        commonTxt.setVisibility(commonTxtStatus);
    }

    public interface OnClickDetailsListener{
        void OnClickDetails(String commentType,int showType,int id);
    }
}
