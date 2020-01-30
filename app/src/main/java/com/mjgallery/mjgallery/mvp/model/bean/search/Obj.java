package com.mjgallery.mjgallery.mvp.model.bean.search;

import java.io.Serializable;

public class Obj implements Serializable {


    /**
     * vipGrade : vip0
     * vipTitle : 0
     * code : 0
     * isRevice : 0
     */

    private String vipGrade;
    private String vipTitle;
    private int code;  //vip代码
    private int isRevice; //是否可领取晋升奖励

    public String getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(String vipGrade) {
        this.vipGrade = vipGrade;
    }

    public String getVipTitle() {
        return vipTitle;
    }

    public void setVipTitle(String vipTitle) {
        this.vipTitle = vipTitle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIsRevice() {
        return isRevice;
    }

    public void setIsRevice(int isRevice) {
        this.isRevice = isRevice;
    }
}
