package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class MineNoticeBean extends BaseResponse<List<MineNoticeBean>> {


    /**
     * id : 7
     * content : 今晚开奖，哒哒哒哒哒哒哒哒今晚开奖，哒哒哒哒哒哒哒哒今晚开奖，哒哒哒哒哒哒哒哒
     * action : 5
     * senderId : 1
     * senderType : 2
     * isRead : 0
     * createTime : 1565777535000
     * nickname : 系统通知
     * avatarUrl : /attachment/headImg/1565883895782ok3wn5wUdhegUigVcuqj3SlmoN2A.jpg
     */

    private int id;
    private String content;
    private int action;
    private int senderId;
    private int senderType;
    private int isRead;
    private long createTime;
    private String nickname;
    private String avatarUrl;
    private int relatedType;//（1-首页，2-发布，3-视频，4-资料）
    private int relatedId; //跳转琏接的主要参数id

    public int getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(int relatedType) {
        this.relatedType = relatedType;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
