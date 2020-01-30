package com.mjgallery.mjgallery.mvp.model.bean;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;

import java.io.Serializable;

public class UserInformation extends BaseResponse<UserInformation> implements Serializable {


    /**
     * filePath : http://192.168.0.212:8080/attachment/payment/pay.png
     * resultType : 0
     * userId : 251
     * msgSettlement : 1
     * msgPay : 1
     * msgPassword : 1
     * nikeName : 你好我是大吉吧
     * inviteCode : HrOu3V
     * headImg : http://192.168.0.212:8080/attachment/headImg/a02ea6d9-67a3-42c3-a426-0a0bc9c686fc.jpeg
     * mobile : 18320891523
     * sysBrokerageAmount : 500000
     * sumDay : 0
     * max : 0
     * userBalanceAmount : 499800.01
     * settlement : 1
     * gender : 1
     * birthday : 2019年09月06日
     * inviteUrl : http://202.60.229.57/invite.html?code=HrOu3V
     */

    private String filePath;
    private int resultType = 1; //提现资料的最终审核结果(0.通过 1.不通过 2.资料审核中)
    private int userId;
    private int msgSettlement;
    private int msgPay;
    private int msgPassword;
    private String nikeName;
    private String inviteCode;
    private String headImg;
    private String mobile;
    private int sysBrokerageAmount;
    private int sumDay;
    private int max;
    private double userBalanceAmount;
    private int settlement;
    private int gender;
    private String birthday;
    private String inviteUrl;
    private Obj obj;
    private boolean hasNewNotify;

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMsgSettlement() {
        return msgSettlement;
    }

    public void setMsgSettlement(int msgSettlement) {
        this.msgSettlement = msgSettlement;
    }

    public int getMsgPay() {
        return msgPay;
    }

    public void setMsgPay(int msgPay) {
        this.msgPay = msgPay;
    }

    public int getMsgPassword() {
        return msgPassword;
    }

    public void setMsgPassword(int msgPassword) {
        this.msgPassword = msgPassword;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSysBrokerageAmount() {
        return sysBrokerageAmount;
    }

    public void setSysBrokerageAmount(int sysBrokerageAmount) {
        this.sysBrokerageAmount = sysBrokerageAmount;
    }

    public int getSumDay() {
        return sumDay;
    }

    public void setSumDay(int sumDay) {
        this.sumDay = sumDay;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getUserBalanceAmount() {
        return userBalanceAmount;
    }

    public void setUserBalanceAmount(double userBalanceAmount) {
        this.userBalanceAmount = userBalanceAmount;
    }

    public int getSettlement() {
        return settlement;
    }

    public void setSettlement(int settlement) {
        this.settlement = settlement;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public boolean getHasNewNotify() {
        return hasNewNotify;
    }

    public void setHasNewNotify(boolean hasNewNotify) {
        this.hasNewNotify = hasNewNotify;
    }
}
