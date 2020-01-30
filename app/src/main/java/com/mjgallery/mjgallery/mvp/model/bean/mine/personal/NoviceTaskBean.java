package com.mjgallery.mjgallery.mvp.model.bean.mine.personal;

import java.io.Serializable;

public class NoviceTaskBean implements Serializable {


    /**
     * id : 5
     * rewardName : 新手注册
     * type : 3
     * content : 新用户下载注册可以领取一次注册红包
     * isDo : 0
     */

    private int id;
    private String rewardName;
    private int type; //类型3为新手任务，2日常任务
    private String content;
    private int isDo; //0是还不能领取，1是立即领取，2是已领取

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }
}
