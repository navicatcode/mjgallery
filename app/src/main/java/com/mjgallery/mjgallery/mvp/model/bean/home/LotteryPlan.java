package com.mjgallery.mjgallery.mvp.model.bean.home;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

/**
 * 开奖时间
 */
public class LotteryPlan  extends BaseResponse<LotteryPlan> {

    private int period;         //期数，如:92
    private String lotteryTime; //开奖时间，如："2019-09-10 07:40"


    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(String lotteryTime) {
        this.lotteryTime = lotteryTime;
    }
}
