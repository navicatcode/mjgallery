package com.mjgallery.mjgallery.mvp.model.bean;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationLikeBean;

public class WithdrawDepositBean extends BaseResponse<WithdrawDepositBean> {

    /**
     * orderNo : 1000000491117301
     * verifyResultStatus : 0
     * msg : 0
     * formatTime : 09/04/23:04
     * limitStart : 0
     * limitEnd : 0
     */

    private String orderNo; //订单号，如：1000000491117301
    private int verifyResultStatus;//0审核中 1不通过 2审核通过
    private int msg;//0为未读，1为已读
    private String formatTime;//审核时间，格式：09/04/23:04
    private int limitStart;
    private int limitEnd;

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

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

}
