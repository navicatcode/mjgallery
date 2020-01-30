package com.mjgallery.mjgallery.mvp.model.bean;

public class VIPWithdrawListBean {


    /**
     * amount : 100.00
     * orderNo : 1000000491117301
     * verifyResultStatus : 2
     * createTime : 2019-09-04 23:04:15
     */

    private String amount;
    private String orderNo;
    private int verifyResultStatus;//(0,提现中1:提现失败;2:提现成功)'
    private String time;
    private int type;
    private String rewardName;
    private int rewardCode;

    
    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getVerifyResultStatus() {
        return verifyResultStatus;
    }

    public void setVerifyResultStatus(int verifyResultStatus) {
        this.verifyResultStatus = verifyResultStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(int rewardCode) {
        this.rewardCode = rewardCode;
    }
}
