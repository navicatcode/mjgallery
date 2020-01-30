package com.mjgallery.mjgallery.mvp.ui.adapter.discoverydetails;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverDetailsCommentsItemBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.widget.spannable.CircleMovementMethod;
import com.mjgallery.mjgallery.widget.spannable.SpannableClickable;

import java.util.List;


public class DiscoverDetailsCommentsItemAdapter extends BaseQuickAdapter<DiscoverDetailsCommentsItemBean, BaseViewHolder> {
    IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener;
    private int itemColor=ArmsUtils.getColor(BaseApplication.getInstance(),R.color.praise_item_default);
    private int itemSelectorColor=ArmsUtils.getColor(BaseApplication.getInstance(),R.color.praise_item_selector_default);
    public DiscoverDetailsCommentsItemAdapter(int layoutResId,
                                              @Nullable List<DiscoverDetailsCommentsItemBean> data
            , IDiscoveryDetailsCommentsItemClickListener iDiscoveryDetailsCommentsItemClickListener) {
        super(layoutResId, data);
        this.iDiscoveryDetailsCommentsItemClickListener = iDiscoveryDetailsCommentsItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiscoverDetailsCommentsItemBean item) {
        TextView tvDiscoveryDetailsCommentsItemTitle = helper.getView(R.id.tvDiscoveryDetailsCommentsItemTitle);
        LinearLayout llDiscoveryDetailsCommentsItem = helper.getView(R.id.llDiscoveryDetailsCommentsItem);
        String userId = PreferenceUtil.getInstance(BaseApplication.getInstance()).getString(AppConstants.USERID, "");
        DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean = item.getSubCommentDetailDosBean();
        DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanX userDOBeanX = subCommentDetailDosBean.getUserDO();
        DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean replyUserDOBean = subCommentDetailDosBean.getReplyUserDO();
        //回复人
        CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);
        boolean isFlase = false;
        StringBuffer stringBuffer = new StringBuffer();
        String publishedReople = "";
        String respondPeople = "";
        if (userDOBeanX != null) {
            respondPeople = userDOBeanX.getNikeName();
            if (userId.equals(String.valueOf(userDOBeanX.getId()))) {
                respondPeople = ArmsUtils.getString(BaseApplication.getInstance(),R.string.me);
                isFlase = true;
            }
        }
        //被回复人的对象
        if (replyUserDOBean != null) {
            if (userId.equals(String.valueOf(replyUserDOBean.getId()))) {
                stringBuffer.append(respondPeople);
                stringBuffer.append(" ");
                stringBuffer.append(ArmsUtils.getString(BaseApplication.getInstance(), R.string.reply));
                stringBuffer.append(ArmsUtils.getString(BaseApplication.getInstance(), R.string.me));
                stringBuffer.append(":");
                stringBuffer.append(subCommentDetailDosBean.getSubCommentContent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer.toString());
                style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                        R.color.color_0AAFFA)),
                        0,
                        respondPeople.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new SpannableClickable() {
                    @Override
                    public void onClick(View widget) {
                        if (iDiscoveryDetailsCommentsItemClickListener != null) {
                            if (userDOBeanX != null) {
                                iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemSwitchInformation(userDOBeanX.getId());
                            }
                        }
                    }
                }, 0, respondPeople.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                        R.color.color_0AAFFA)),
                        stringBuffer.toString().indexOf(":") - 1,
                        stringBuffer.toString().indexOf(":"),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new SpannableClickable() {
                    @Override
                    public void onClick(View widget) {
                        if (iDiscoveryDetailsCommentsItemClickListener != null) {
                            iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemSwitchInformation(replyUserDOBean.getId());
                        }
                    }
                }, stringBuffer.toString().indexOf(":") - 1, stringBuffer.toString().indexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDiscoveryDetailsCommentsItemTitle.setText(style);
            } else {
                publishedReople = replyUserDOBean.getNikeName();
                stringBuffer.append(respondPeople);
                stringBuffer.append(" ");
                stringBuffer.append(ArmsUtils.getString(BaseApplication.getInstance(), R.string.reply));
                stringBuffer.append(publishedReople);
                stringBuffer.append(":");
                stringBuffer.append(subCommentDetailDosBean.getSubCommentContent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer.toString());
                style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                        R.color.color_0AAFFA)),
                        0,
                        respondPeople.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new SpannableClickable() {
                    @Override
                    public void onClick(View widget) {
                        if (iDiscoveryDetailsCommentsItemClickListener != null) {
                            if (userDOBeanX != null) {
                                iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemSwitchInformation(userDOBeanX.getId());
                            }
                        }
                    }
                },0,respondPeople.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                        R.color.color_0AAFFA)),
                        stringBuffer.toString().indexOf(":") - publishedReople.length(),
                        stringBuffer.toString().indexOf(":"),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new SpannableClickable() {
                    @Override
                    public void onClick(View widget) {
                        if (iDiscoveryDetailsCommentsItemClickListener != null) {
                            iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemSwitchInformation(replyUserDOBean.getId());
                        }
                    }
                },stringBuffer.toString().indexOf(":") - publishedReople.length(), stringBuffer.toString().indexOf(":"),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDiscoveryDetailsCommentsItemTitle.setText(style);
            }

        } else {
            //没有回复人对象
            stringBuffer.append(respondPeople);
            stringBuffer.append(":");
            stringBuffer.append(subCommentDetailDosBean.getSubCommentContent());
            SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer.toString());
            style.setSpan(new ForegroundColorSpan(ArmsUtils.getColor(BaseApplication.getInstance(),
                    R.color.color_0AAFFA)), 0,
                    respondPeople.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new SpannableClickable() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (iDiscoveryDetailsCommentsItemClickListener != null) {
                        if (userDOBeanX != null) {
                            iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemSwitchInformation(userDOBeanX.getId());
                        }
                    }
                }
            }, 0, respondPeople.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvDiscoveryDetailsCommentsItemTitle.setText(style);
        }
        tvDiscoveryDetailsCommentsItemTitle.setMovementMethod(circleMovementMethod);
        llDiscoveryDetailsCommentsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemClickListener
                            (subCommentDetailDosBean,helper.getAdapterPosition());
                }
            }
        });
        tvDiscoveryDetailsCommentsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDiscoveryDetailsCommentsItemClickListener != null) {
                    iDiscoveryDetailsCommentsItemClickListener.onDiscoveryDetailsCommentsItemClickListener
                            (subCommentDetailDosBean,helper.getAdapterPosition());
                }
            }
        });
    }

    public interface IDiscoveryDetailsCommentsItemClickListener {
        void onDiscoveryDetailsCommentsItemClickListener(DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDos, int itemPosition);
        void onDiscoveryDetailsCommentsItemSwitchInformation(int userId);
    }
}
