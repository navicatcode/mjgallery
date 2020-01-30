package com.mjgallery.mjgallery.mvp.model.bean.dialog;

public class LotteryDataChooseTypeBean {
    private String lotteryDataChooseTypeTitle;
    private int lotteryDataChooseType;
    boolean isSelect = false;

    @Override
    public String toString() {
        return "LotteryDataChooseTypeBean{" +
                "lotteryDataChooseTypeTitle='" + lotteryDataChooseTypeTitle + '\'' +
                ", lotteryDataChooseType=" + lotteryDataChooseType +
                ", isSelect=" + isSelect +
                '}';
    }

    public String getLotteryDataChooseTypeTitle() {
        return lotteryDataChooseTypeTitle;
    }

    public void setLotteryDataChooseTypeTitle(String lotteryDataChooseTypeTitle) {
        this.lotteryDataChooseTypeTitle = lotteryDataChooseTypeTitle;
    }

    public int getLotteryDataChooseType() {
        return lotteryDataChooseType;
    }

    public void setLotteryDataChooseType(int lotteryDataChooseType) {
        this.lotteryDataChooseType = lotteryDataChooseType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public LotteryDataChooseTypeBean() {
    }

    public LotteryDataChooseTypeBean(String lotteryDataChooseTypeTitle, int lotteryDataChooseType, boolean isSelect) {
        this.lotteryDataChooseTypeTitle = lotteryDataChooseTypeTitle;
        this.lotteryDataChooseType = lotteryDataChooseType;
        this.isSelect = isSelect;
    }




}
