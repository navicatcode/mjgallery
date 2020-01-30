package com.mjgallery.mjgallery.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class LotteryRecordBean extends BaseResponse<List<LotteryRecordBean>> implements MultiItemEntity {


    /**
     * year : 2017
     * lotteryRecordDtos : [{"id":209,"year":2017,"period":77,"firstSx":"鼠","lotteryTime":1564848000,"sx":["兔","鼠","兔","鼠","蛇","牛","牛"],"wx":["土","火","火","火","土","火","水"],"numbers":["21","12","33","12","43","11","23"],"createTime":1564900747},{"id":211,"year":2017,"period":78,"firstSx":"龙","lotteryTime":1564848000,"sx":["龙","鼠","牛","牛","牛","狗","龙"],"wx":["木","火","火","水","火","土","水"],"numbers":["32","12","11","23","11","14","8"],"createTime":1564905538},{"id":207,"year":2017,"period":76,"firstSx":"鼠","lotteryTime":1564588800,"sx":["鼠","蛇","兔","猪","鸡","龙","龙"],"wx":["火","土","木","水","金","土","水"],"numbers":["12","43","9","37","27","44","8"],"createTime":1564635958}]
     */

    private int year;
    private List<LotteryRecordDtosBean> lotteryRecordDtos;
    private boolean isWuXing = false;
    private int type = 0;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isWuXing() {
        return isWuXing;
    }

    public void setWuXing(boolean wuXing) {
        isWuXing = wuXing;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<LotteryRecordDtosBean> getLotteryRecordDtos() {
        return lotteryRecordDtos;
    }

    public void setLotteryRecordDtos(List<LotteryRecordDtosBean> lotteryRecordDtos) {
        this.lotteryRecordDtos = lotteryRecordDtos;
    }

    @Override
    public int getItemType() {
        return 1;
    }

    public static class LotteryRecordDtosBean extends BaseResponse<LotteryRecordDtosBean> implements MultiItemEntity{
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

        public boolean isXiaLa() {
            return isXiaLa;
        }

        public void setXiaLa(boolean xiaLa) {
            isXiaLa = xiaLa;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        @Override
        public int getItemType() {
            return 2;
        }
    }
}
