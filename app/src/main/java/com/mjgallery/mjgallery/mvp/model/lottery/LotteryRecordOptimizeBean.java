package com.mjgallery.mjgallery.mvp.model.lottery;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class LotteryRecordOptimizeBean implements MultiItemEntity {


    private int itemType;
    private boolean isWuXing = false;
    private int type = 0;
    /**
     * id : 209
     * year : 2017
     * period : 77
     * firstSx : 鼠
     * lotteryTime : 1564848000
     * sx : ["兔","鼠","兔","鼠","蛇","牛","牛"]
     * wx : ["土","火","火","火","土","火","水"]
     * numbers : ["21","12","33","12","43","11","23"]
     * createTime : 1564900747
     */

    private int id;
    private int year;
    private int period;
    private String firstSx;

    private int lotteryTime;
    private int createTime;
    private List<String> sx;
    private List<String> wx;
    private List<String> numbers;
    private boolean isXiaLa = false;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFirstSx() {
        return firstSx;
    }

    public void setFirstSx(String firstSx) {
        this.firstSx = firstSx;
    }

    public int getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(int lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public List<String> getSx() {
        return sx;
    }

    public void setSx(List<String> sx) {
        this.sx = sx;
    }

    public List<String> getWx() {
        return wx;
    }

    public void setWx(List<String> wx) {
        this.wx = wx;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public boolean isXiaLa() {
        return isXiaLa;
    }

    public void setXiaLa(boolean xiaLa) {
        isXiaLa = xiaLa;
    }

    public boolean isWuXing() {
        return isWuXing;
    }

    public void setWuXing(boolean wuXing) {
        isWuXing = wuXing;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
