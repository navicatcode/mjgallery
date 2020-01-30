package com.mjgallery.mjgallery.mvp.ui.adapter.lottery;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.DateUtils;
import com.mjgallery.mjgallery.mvp.model.lottery.LotteryRecordOptimizeBean;

import java.util.List;

import static com.mjgallery.mjgallery.app.AppConstants.TYPE_CONTENT;
import static com.mjgallery.mjgallery.app.AppConstants.TYPE_HEAD;


public class LotteryOptimizeRecordAdapter extends BaseMultiItemQuickAdapter<LotteryRecordOptimizeBean, BaseViewHolder> {

    List<LotteryRecordOptimizeBean> data;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LotteryOptimizeRecordAdapter(List<LotteryRecordOptimizeBean> data) {
        super(data);
        this.data = data;
        addItemType(TYPE_HEAD, R.layout.lottery_record_item);
        addItemType(TYPE_CONTENT, R.layout.lottery_record_item_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LotteryRecordOptimizeBean item) {
        switch (helper.getItemViewType()) {
            case TYPE_HEAD:
                TextView tvLotteryRecordYear = helper.getView(R.id.tvLotteryRecordYear);
                RelativeLayout rlLotteryRecordJangXu = helper.getView(R.id.rlLotteryRecordJangXu);
                RelativeLayout rlLotteryRecordWuXing = helper.getView(R.id.rlLotteryRecordWuXing);
                TextView tvLotteryRecordJangXu = helper.getView(R.id.tvLotteryRecordJangXu);
                TextView tvLotteryRecordWuXing = helper.getView(R.id.tvLotteryRecordWuXing);
                TextView tvLotteryRecordShengXu = helper.getView(R.id.tvLotteryRecordShengXu);


                tvLotteryRecordYear.setText(item.getYear()
                        + ArmsUtils.getString(BaseApplication.getInstance(),R.string.annual_drawing_record));
                if (item.isWuXing()) {
                    tvLotteryRecordWuXing.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0AAFFA));
                } else {
                    tvLotteryRecordWuXing.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_7E7E7E));
                }
                switch (item.getType()) {
                    case 0:
                        tvLotteryRecordJangXu.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.descending_order));
                        tvLotteryRecordShengXu.setVisibility(View.GONE);
                        tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvLotteryRecordShengXu.setVisibility(View.VISIBLE);
                        tvLotteryRecordJangXu.setVisibility(View.GONE);
                        break;
                    case 2:
                        tvLotteryRecordShengXu.setVisibility(View.GONE);
                        tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
                        break;
                }
                rlLotteryRecordWuXing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("rlLotteryRecordWuXing==","rlLotteryRecordWuXing===");
                        updateWuXiang(item.getYear());
                    }
                });
                tvLotteryRecordJangXu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        updateShengXuOrJiangXu(1, groupIndex);
                    }
                });
                tvLotteryRecordShengXu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        updateShengXuOrJiangXu(2, groupIndex);
                    }
                });
                break;
            case TYPE_CONTENT:
                TextView tvDay = helper.getView(R.id.tvDay);
                TextView tvQishu = helper.getView(R.id.tvQishu);

                TextView tvvLotteryRecordItemMa01 = helper.getView(R.id.tvvLotteryRecordItemMa01);
                TextView tvvLotteryRecordItemMa02 = helper.getView(R.id.tvvLotteryRecordItemMa02);
                TextView tvvLotteryRecordItemMa03 = helper.getView(R.id.tvvLotteryRecordItemMa03);
                TextView tvvLotteryRecordItemMa04 = helper.getView(R.id.tvvLotteryRecordItemMa04);
                TextView tvvLotteryRecordItemMa05 = helper.getView(R.id.tvvLotteryRecordItemMa05);
                TextView tvvLotteryRecordItemMa06 = helper.getView(R.id.tvvLotteryRecordItemMa06);
                TextView tvvLotteryRecordItemMa08 = helper.getView(R.id.tvvLotteryRecordItemMa08);
                TextView tvLotteryRecordItem01 = helper.getView(R.id.tvLotteryRecordItem01);
                TextView tvLotteryRecordItem02 = helper.getView(R.id.tvLotteryRecordItem02);
                TextView tvLotteryRecordItem03 = helper.getView(R.id.tvLotteryRecordItem03);
                TextView tvLotteryRecordItem04 = helper.getView(R.id.tvLotteryRecordItem04);
                TextView tvLotteryRecordItem05 = helper.getView(R.id.tvLotteryRecordItem05);
                TextView tvLotteryRecordItem06 = helper.getView(R.id.tvLotteryRecordItem06);
                TextView tvLotteryRecordItem08 = helper.getView(R.id.tvLotteryRecordItem08);
                ImageView ivLotteryRecordItem01 = helper.getView(R.id.ivLotteryRecordItem01);
                ImageView ivLotteryRecordItem02 = helper.getView(R.id.ivLotteryRecordItem02);
                ImageView ivLotteryRecordItem03 = helper.getView(R.id.ivLotteryRecordItem03);
                ImageView ivLotteryRecordItem04 = helper.getView(R.id.ivLotteryRecordItem04);
                ImageView ivLotteryRecordItem05 = helper.getView(R.id.ivLotteryRecordItem05);
                ImageView ivLotteryRecordItem06 = helper.getView(R.id.ivLotteryRecordItem06);
                ImageView ivLotteryRecordItem08 = helper.getView(R.id.ivLotteryRecordItem08);
                LinearLayout llLotteryRecordItem = helper.getView(R.id.llLotteryRecordItem);
                TextView tvLotteryRecordItemOne = helper.getView(R.id.tvLotteryRecordItemOne);
                TextView tvLotteryRecordItemTwo = helper.getView(R.id.tvLotteryRecordItemTwo);
                TextView tvLotteryRecordItemThree = helper.getView(R.id.tvLotteryRecordItemThree);
                TextView tvLotteryRecordItemFour = helper.getView(R.id.tvLotteryRecordItemFour);
                RelativeLayout rlXiangQingClose = helper.getView(R.id.rlXiangQingClose);
                RelativeLayout rlXiangQingOpen = helper.getView(R.id.rlXiangQingOpen);

                String preiodStr = (item.getPeriod() < 100) ? ("0" + item.getPeriod()) : ("" + item.getPeriod());
                tvDay.setText(DateUtils.timet(String.valueOf(item.getLotteryTime())));
                tvQishu.setText(preiodStr +ArmsUtils.getString(BaseApplication.getInstance(),R.string.period));

                for (int i = 0; i < item.getSx().size(); i++) {
                    switch (i) {
                        case 0:
                            tvvLotteryRecordItemMa01.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 1:
                            tvvLotteryRecordItemMa02.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 2:
                            tvvLotteryRecordItemMa03.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 3:
                            tvvLotteryRecordItemMa04.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 4:
                            tvvLotteryRecordItemMa05.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 5:
                            tvvLotteryRecordItemMa06.setText(setTextMa(item.isWuXing(), i, item));
                            break;
                        case 6:
                            tvvLotteryRecordItemMa08.setText(setTextMa(item.isWuXing(), i, item));
                            break;


                    }
                }

                rlXiangQingClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rlXiangQingClose.setVisibility(View.GONE);
                        rlXiangQingOpen.setVisibility(View.VISIBLE);
                        llLotteryRecordItem.setVisibility(View.VISIBLE);

                    }
                });

                rlXiangQingOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rlXiangQingClose.setVisibility(View.VISIBLE);
                        rlXiangQingOpen.setVisibility(View.GONE);
                        llLotteryRecordItem.setVisibility(View.GONE);

                    }
                });
                //特码总和大小
                int totalTeMa = 0;
                for (int i = 0; i < item.getNumbers().size(); i++) {
                    int itemNumber = Integer.valueOf(item.getNumbers().get(i));
                    String lottery = (itemNumber < 10) ? ("0" + itemNumber) : ("" + itemNumber);
                    totalTeMa = totalTeMa + itemNumber;
                    switch (i) {
                        case 0:
                            tvLotteryRecordItem01.setText(lottery);
                            GlideUtil.loadNormalImage(ivLotteryRecordItem01, setNumbersBg(itemNumber));
                            break;
                        case 1:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem02, setNumbersBg(itemNumber));
                            tvLotteryRecordItem02.setText(lottery);
                            break;
                        case 2:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem03, setNumbersBg(itemNumber));
                            tvLotteryRecordItem03.setText(lottery);
                            break;
                        case 3:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem04, setNumbersBg(itemNumber));
                            tvLotteryRecordItem04.setText(lottery);
                            break;
                        case 4:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem05, setNumbersBg(itemNumber));
                            tvLotteryRecordItem05.setText(lottery);
                            break;
                        case 5:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem06, setNumbersBg(itemNumber));
                            tvLotteryRecordItem06.setText(lottery);
                            break;
                        case 6:
                            GlideUtil.loadNormalImage(ivLotteryRecordItem08, setNumbersBg(itemNumber));
                            tvLotteryRecordItem08.setText(lottery);
                            break;
                    }
                }
                if (item.getNumbers().size() == 7) {
                    //特码号码
                    int teMaNumber = Integer.valueOf(item.getNumbers().get(6));
                    if (teMaNumber % 2 == 0) {
                        tvLotteryRecordItemOne.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.lottery_record_title01));
                    } else {
                        tvLotteryRecordItemOne.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                                R.string.lottery_record_title02)
                        );
                    }
                    if (teMaNumber > 24) {
                        tvLotteryRecordItemTwo.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                                R.string.lottery_record_title03));
                    } else {
                        tvLotteryRecordItemTwo.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                                R.string.lottery_record_title04));
                    }
                }
                if (totalTeMa > 174) {
                    tvLotteryRecordItemThree.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                            R.string.lottery_record_title05));
                } else {
                    tvLotteryRecordItemThree.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                            R.string.lottery_record_title06));
                }
                if (totalTeMa % 2 == 0) {
                    tvLotteryRecordItemFour.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                            R.string.lottery_record_title07));
                } else {
                    tvLotteryRecordItemFour.setText(ArmsUtils.getString(BaseApplication.getInstance(),
                            R.string.lottery_record_title08));
                }

                break;
        }

    }


//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        if (viewType == VIEW_TYPE_ITEM_TIME) {
//            TitleItemHolder holder = new TitleItemHolder(
//                    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lottery_record_item, viewGroup, false));
//            return holder;
//        } else {
//            return new SubItemHolder(LayoutInflater.from(viewGroup.getContext()).
//                    inflate(R.layout.lottery_record_item_item, viewGroup, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//        int groupIndex = mIndexMap.get(position).getGroupIndex();
//        LotteryRecordBean lotteryRecordBean = mDataList.get(groupIndex);
//        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
//            TitleItemHolder itemHolder = (TitleItemHolder) viewHolder;
//            itemHolder.itemView.setTag(mDataList.get(groupIndex));
//            itemHolder.tvLotteryRecordYear.setText(mDataList.get(groupIndex).getYear()
//                    +UIUtils.getString(R.string.annual_drawing_record));
//            if (lotteryRecordBean.isWuXing()) {
//                itemHolder.tvLotteryRecordWuXing.setTextColor(UIUtils.getColor(R.color.color_0AAFFA));
//            } else {
//                itemHolder.tvLotteryRecordWuXing.setTextColor(UIUtils.getColor(R.color.color_7E7E7E));
//            }
//
//
//            switch (lotteryRecordBean.getType()) {
//                case 0:
//                    itemHolder.tvLotteryRecordJangXu.setText(UIUtils.getString(R.string.descending_order));
//                    itemHolder.tvLotteryRecordShengXu.setVisibility(View.GONE);
//                    itemHolder.tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
//                    break;
//                case 1:
//                    itemHolder.tvLotteryRecordShengXu.setVisibility(View.VISIBLE);
//                    itemHolder.tvLotteryRecordJangXu.setVisibility(View.GONE);
//                    break;
//                case 2:
//                    itemHolder.tvLotteryRecordShengXu.setVisibility(View.GONE);
//                    itemHolder.tvLotteryRecordJangXu.setVisibility(View.VISIBLE);
//                    break;
//            }
//            itemHolder.rlLotteryRecordWuXing.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    updataWuXiang(groupIndex);
//                }
//            });
//            itemHolder.tvLotteryRecordJangXu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    updateShengXuOrJiangXu(1, groupIndex);
//                }
//            });
//            itemHolder.tvLotteryRecordShengXu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    updateShengXuOrJiangXu(2, groupIndex);
//                }
//            });
//
//        } else {
//            SubItemHolder subHolder = (SubItemHolder) viewHolder;
//            int childIndex = mIndexMap.get(position).getChildIndex();
//            LotteryRecordBean.LotteryRecordDtosBean subItem = mDataList.get(groupIndex).getLotteryRecordDtos().get(childIndex);
//            subHolder.itemView.setTag(subItem);
//            String preiodStr = (subItem.getPeriod() < 100) ? ("0" + subItem.getPeriod()) : ("" + subItem.getPeriod());
//            subHolder.tvDay.setText(DateUtils.timet(String.valueOf(subItem.getLotteryTime())));
//                subHolder.tvQishu.setText(preiodStr + UIUtils.getString(R.string.period));
//
//            for (int i = 0; i < subItem.getSx().size(); i++) {
//                switch (i) {
//                    case 0:
//                        subHolder.tvvLotteryRecordItemMa01.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 1:
//                        subHolder.tvvLotteryRecordItemMa02.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 2:
//                        subHolder.tvvLotteryRecordItemMa03.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 3:
//                        subHolder.tvvLotteryRecordItemMa04.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 4:
//                        subHolder.tvvLotteryRecordItemMa05.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 5:
//                        subHolder.tvvLotteryRecordItemMa06.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//                    case 6:
//                        subHolder.tvvLotteryRecordItemMa08.setText(setTextMa(lotteryRecordBean.isWuXing(), i, subItem));
//                        break;
//
//
//                }
//            }
//
//            subHolder.rlXiangQingClose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    subHolder.rlXiangQingClose.setVisibility(View.GONE);
//                    subHolder.rlXiangQingOpen.setVisibility(View.VISIBLE);
//                    subHolder.llLotteryRecordItem.setVisibility(View.VISIBLE);
//
//                }
//            });
//
//            subHolder.rlXiangQingOpen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    subHolder.rlXiangQingClose.setVisibility(View.VISIBLE);
//                    subHolder.rlXiangQingOpen.setVisibility(View.GONE);
//                    subHolder.llLotteryRecordItem.setVisibility(View.GONE);
//
//                }
//            });
//            //特码总和大小
//            int totalTeMa = 0;
//            for (int i = 0; i < subItem.getNumbers().size(); i++) {
//                int itemNumber = Integer.valueOf(subItem.getNumbers().get(i));
//                String lottery  = (itemNumber < 10) ? ("0" + itemNumber) : ("" + itemNumber);
//                totalTeMa = totalTeMa + itemNumber;
//                switch (i) {
//                    case 0:
//                        subHolder.tvLotteryRecordItem01.setText(lottery);
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem01, setNumbersBg(itemNumber));
//                        break;
//                    case 1:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem02, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem02.setText(lottery);
//                        break;
//                    case 2:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem03, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem03.setText(lottery);
//                        break;
//                    case 3:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem04, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem04.setText(lottery);
//                        break;
//                    case 4:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem05, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem05.setText(lottery);
//                        break;
//                    case 5:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem06, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem06.setText(lottery);
//                        break;
//                    case 6:
//                        GlideUtil.loadNormalImage(subHolder.ivLotteryRecordItem08, setNumbersBg(itemNumber));
//                        subHolder.tvLotteryRecordItem08.setText(lottery);
//                        break;
//                }
//            }
//            if (subItem.getNumbers().size() == 7) {
//                //特码号码
//                int teMaNumber = Integer.valueOf(subItem.getNumbers().get(6));
//                if (teMaNumber % 2 == 0) {
//                    subHolder.tvLotteryRecordItemOne.setText(UIUtils.getString(R.string.lottery_record_title01));
//                } else {
//                    subHolder.tvLotteryRecordItemOne.setText(UIUtils.getString(
//                            R.string.lottery_record_title02)
//                            );
//                }
//                if (teMaNumber > 24) {
//                    subHolder.tvLotteryRecordItemTwo.setText(UIUtils.getString(
//                            R.string.lottery_record_title03));
//                } else {
//                    subHolder.tvLotteryRecordItemTwo.setText(UIUtils.getString(
//                            R.string.lottery_record_title04));
//                }
//            }
//            if (totalTeMa > 174) {
//                subHolder.tvLotteryRecordItemThree.setText(UIUtils.getString(
//                        R.string.lottery_record_title05));
//            } else {
//                subHolder.tvLotteryRecordItemThree.setText(UIUtils.getString(
//                        R.string.lottery_record_title06));
//            }
//            if (totalTeMa % 2 == 0) {
//                subHolder.tvLotteryRecordItemFour.setText(UIUtils.getString(
//                        R.string.lottery_record_title07));
//            } else {
//                subHolder.tvLotteryRecordItemFour.setText(UIUtils.getString(
//                        R.string.lottery_record_title08));
//            }
//        }
//    }
//

    // 得到显示的码数
    private String setTextMa(boolean isWuXing, int index,
                             LotteryRecordOptimizeBean lotteryRecordOptimizeBean) {
        StringBuffer stringBuffer = new StringBuffer();
        if (isWuXing) {
            stringBuffer.append(lotteryRecordOptimizeBean.getSx().get(index));
            stringBuffer.append("/");
            stringBuffer.append(lotteryRecordOptimizeBean.getWx().get(index));
        } else {
            stringBuffer.append(lotteryRecordOptimizeBean.getSx().get(index));
        }

        return stringBuffer.toString();
    }

//

    /**
     * 得到背景颜色
     *
     * @param numbers
     * @return
     */
    private int setNumbersBg(int numbers) {
        int numbersBg = R.drawable.normal_se_bg;
        switch (numbers) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                //红
                numbersBg = R.drawable.home_sheng_xiao_bg1_img;
                break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                //蓝
                numbersBg = R.drawable.home_sheng_xiao_bg3_img;
                break;
            case 0:
                //默认
                numbersBg = R.drawable.home_sheng_xiao_bg1_img;
                break;
            default:
                //绿
                numbersBg = R.drawable.home_sheng_xiao_bg2_img;
                break;
        }
        return numbersBg;
    }

    private int setTextColor(int numbers) {
        int textColor = R.color.color_8f8f8f;
        switch (numbers) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                //红
                textColor = R.color.color_FF3344;
                break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                //蓝
                textColor = R.color.color_0193e8;
                break;
            case 0:
                //默认
                textColor = R.color.color_8f8f8f;
                break;
            default:
                //绿
                textColor = R.color.color_1fc16b;
                break;
        }
        return textColor;
    }

    //
//
//    @Override
//    public void onBindPinnedViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int groupIndex = mIndexMap.get(position).getGroupIndex();
//        TitleItemHolder itemHolder = (TitleItemHolder) holder;
//        itemHolder.tvLotteryRecordYear.setVisibility(View.VISIBLE);
//        itemHolder.rlLotteryRecordWuXing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updataWuXiang(groupIndex);
//            }
//        });
//        itemHolder.tvLotteryRecordJangXu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateShengXuOrJiangXu(1, groupIndex);
//            }
//        });
//        itemHolder.tvLotteryRecordShengXu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateShengXuOrJiangXu(2, groupIndex);
//            }
//        });
//    }
//
//
//    static class TitleItemHolder extends RecyclerView.ViewHolder {
//
//        TextView tvLotteryRecordYear;
//        RelativeLayout rlLotteryRecordJangXu;
//        RelativeLayout rlLotteryRecordWuXing;
//        TextView tvLotteryRecordJangXu;
//        TextView tvLotteryRecordWuXing;
//        TextView tvLotteryRecordShengXu;
//
//
//        TitleItemHolder(View itemView) {
//            super(itemView);
//            tvLotteryRecordYear = itemView.findViewById(R.id.tvLotteryRecordYear);
//            rlLotteryRecordJangXu = itemView.findViewById(R.id.rlLotteryRecordJangXu);
//            rlLotteryRecordWuXing = itemView.findViewById(R.id.rlLotteryRecordWuXing);
//            tvLotteryRecordJangXu = itemView.findViewById(R.id.tvLotteryRecordJangXu);
//            tvLotteryRecordWuXing = itemView.findViewById(R.id.tvLotteryRecordWuXing);
//            tvLotteryRecordShengXu = itemView.findViewById(R.id.tvLotteryRecordShengXu);
//        }
//    }
//
//
//    static class SubItemHolder extends RecyclerView.ViewHolder {
//        TextView tvDay;
//        TextView tvQishu;
//
//        TextView tvvLotteryRecordItemMa01;
//        TextView tvvLotteryRecordItemMa02;
//        TextView tvvLotteryRecordItemMa03;
//        TextView tvvLotteryRecordItemMa04;
//        TextView tvvLotteryRecordItemMa05;
//        TextView tvvLotteryRecordItemMa06;
//        TextView tvvLotteryRecordItemMa08;
//        TextView tvLotteryRecordItem01;
//        TextView tvLotteryRecordItem02;
//        TextView tvLotteryRecordItem03;
//        TextView tvLotteryRecordItem04;
//        TextView tvLotteryRecordItem05;
//        TextView tvLotteryRecordItem06;
//        TextView tvLotteryRecordItem08;
//        ImageView ivLotteryRecordItem01;
//        ImageView ivLotteryRecordItem02;
//        ImageView ivLotteryRecordItem03;
//        ImageView ivLotteryRecordItem04;
//        ImageView ivLotteryRecordItem05;
//        ImageView ivLotteryRecordItem06;
//        ImageView ivLotteryRecordItem07;
//        ImageView ivLotteryRecordItem08;
//        LinearLayout llLotteryRecordItem;
//        TextView tvLotteryRecordItemOne;
//        TextView tvLotteryRecordItemTwo;
//        TextView tvLotteryRecordItemThree;
//        TextView tvLotteryRecordItemFour;
//        RelativeLayout rlXiangQingClose;
//        RelativeLayout rlXiangQingOpen;
//
//        SubItemHolder(View itemView) {
//            super(itemView);
//            tvDay = itemView.findViewById(R.id.tvDay);
//            tvQishu = itemView.findViewById(R.id.tvQishu);
//            tvvLotteryRecordItemMa01 = itemView.findViewById(R.id.tvvLotteryRecordItemMa01);
//            tvvLotteryRecordItemMa02 = itemView.findViewById(R.id.tvvLotteryRecordItemMa02);
//            tvvLotteryRecordItemMa03 = itemView.findViewById(R.id.tvvLotteryRecordItemMa03);
//            tvvLotteryRecordItemMa04 = itemView.findViewById(R.id.tvvLotteryRecordItemMa04);
//            tvvLotteryRecordItemMa05 = itemView.findViewById(R.id.tvvLotteryRecordItemMa05);
//            tvvLotteryRecordItemMa06 = itemView.findViewById(R.id.tvvLotteryRecordItemMa06);
//            tvvLotteryRecordItemMa08 = itemView.findViewById(R.id.tvvLotteryRecordItemMa08);
//            tvLotteryRecordItem01 = itemView.findViewById(R.id.tvLotteryRecordItem01);
//            tvLotteryRecordItem02 = itemView.findViewById(R.id.tvLotteryRecordItem02);
//            tvLotteryRecordItem03 = itemView.findViewById(R.id.tvLotteryRecordItem03);
//            tvLotteryRecordItem04 = itemView.findViewById(R.id.tvLotteryRecordItem04);
//            tvLotteryRecordItem05 = itemView.findViewById(R.id.tvLotteryRecordItem05);
//            tvLotteryRecordItem06 = itemView.findViewById(R.id.tvLotteryRecordItem06);
//            tvLotteryRecordItem08 = itemView.findViewById(R.id.tvLotteryRecordItem08);
//            ivLotteryRecordItem01 = itemView.findViewById(R.id.ivLotteryRecordItem01);
//            ivLotteryRecordItem02 = itemView.findViewById(R.id.ivLotteryRecordItem02);
//            ivLotteryRecordItem03 = itemView.findViewById(R.id.ivLotteryRecordItem03);
//            ivLotteryRecordItem04 = itemView.findViewById(R.id.ivLotteryRecordItem04);
//            ivLotteryRecordItem05 = itemView.findViewById(R.id.ivLotteryRecordItem05);
//            ivLotteryRecordItem06 = itemView.findViewById(R.id.ivLotteryRecordItem06);
//            ivLotteryRecordItem08 = itemView.findViewById(R.id.ivLotteryRecordItem08);
//            llLotteryRecordItem = itemView.findViewById(R.id.llLotteryRecordItem);
//            tvLotteryRecordItemOne = itemView.findViewById(R.id.tvLotteryRecordItemOne);
//            tvLotteryRecordItemTwo = itemView.findViewById(R.id.tvLotteryRecordItemTwo);
//            tvLotteryRecordItemThree = itemView.findViewById(R.id.tvLotteryRecordItemThree);
//            tvLotteryRecordItemFour = itemView.findViewById(R.id.tvLotteryRecordItemFour);
//            rlXiangQingClose = itemView.findViewById(R.id.rlXiangQingClose);
//            rlXiangQingOpen = itemView.findViewById(R.id.rlXiangQingOpen);
//        }
//    }
//
//
////
////    Context context;
////    public LotteryRecordAdapter(Context context, int layoutResId, @Nullable List<LotteryRecordBean> data) {
////        super(layoutResId, data);
////        this.context = context;
////    }
////
////    @Override
////    protected void convert(@NonNull BaseViewHolder helper, LotteryRecordBean item) {
////        TextView tvLotteryRecordYear = helper.getView(R.id.tvLotteryRecordYear);
////        RelativeLayout rlLotteryRecordJangXu = helper.getView(R.id.rlLotteryRecordJangXu);
////        RelativeLayout rlLotteryRecordWuXing = helper.getView(R.id.rlLotteryRecordWuXing);
////        RecyclerView recyclerViewLotteryRecord = helper.getView(R.id.recyclerViewLotteryRecord);
////        tvLotteryRecordYear.setText(item.getYear() + "年开奖记录");
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////        LotteryRecordItemAdapter lotteryRecordItemAdapter = new LotteryRecordItemAdapter(
////                R.layout.lottery_record_item_item, item.getLotteryRecordDtos());
////        recyclerViewLotteryRecord.setLayoutManager(linearLayoutManager);
////        recyclerViewLotteryRecord.setAdapter(lotteryRecordItemAdapter);
////
////    }
//
//
    //刷新是否显示五行
    public void updateWuXiang(int year) {
        for (int i = 0; i < data.size(); i++) {
            LotteryRecordOptimizeBean lotteryRecordOptimizeBean = data.get(i);
            if (lotteryRecordOptimizeBean.getYear() == year) {
                lotteryRecordOptimizeBean.setWuXing(!lotteryRecordOptimizeBean.isWuXing());
                data.set(i, lotteryRecordOptimizeBean);
            }
        }
        this.notifyDataSetChanged();
    }

//    private void updateShengXuOrJiangXu(int type, int groupIndex) {
//        LotteryRecordBean lotteryRecordBeanShengXuOrJiangXu = mDataList.get(groupIndex);
//        lotteryRecordBeanShengXuOrJiangXu.setType(type);
//        if (type == 1) {
//            Collections.sort(lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos(), new Comparator<LotteryRecordBean.LotteryRecordDtosBean>() {
//                @Override
//                public int compare(LotteryRecordBean.LotteryRecordDtosBean o1, LotteryRecordBean.LotteryRecordDtosBean o2) {
//                    BigDecimal bigDecimalPeriod1 = new BigDecimal(o1.getPeriod());
//                    BigDecimal bigDecimalPeriod2 = new BigDecimal(o2.getPeriod());
//
//                    return bigDecimalPeriod1.compareTo(bigDecimalPeriod2);
//                }
//
//            });
//
//            List<LotteryRecordBean.LotteryRecordDtosBean> lotteryRecordDtosBeans = new ArrayList<>();
//            for (LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean : lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos()) {
//                lotteryRecordDtosBeans.add(lotteryRecordDtosBean);
//            }
//            lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos().clear();
//            lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos().addAll(lotteryRecordDtosBeans);
//            mDataList.set(groupIndex, lotteryRecordBeanShengXuOrJiangXu);
//            this.notifyDataSetChanged();
//        } else {
//            Collections.sort(lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos(), new Comparator<LotteryRecordBean.LotteryRecordDtosBean>() {
//                @Override
//                public int compare(LotteryRecordBean.LotteryRecordDtosBean o1, LotteryRecordBean.LotteryRecordDtosBean o2) {
//                    BigDecimal bigDecimalPeriod1 = new BigDecimal(o1.getPeriod());
//                    BigDecimal bigDecimalPeriod2 = new BigDecimal(o2.getPeriod());
//
//                    return bigDecimalPeriod2.compareTo(bigDecimalPeriod1);
//                }
//
//            });
//
//            List<LotteryRecordBean.LotteryRecordDtosBean> lotteryRecordDtosBeans = new ArrayList<>();
//            for (LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean : lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos()) {
//                lotteryRecordDtosBeans.add(lotteryRecordDtosBean);
//            }
//            lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos().clear();
//            lotteryRecordBeanShengXuOrJiangXu.getLotteryRecordDtos().addAll(lotteryRecordDtosBeans);
//            mDataList.set(groupIndex, lotteryRecordBeanShengXuOrJiangXu);
//            this.notifyDataSetChanged();
//        }
//
//    }


}
