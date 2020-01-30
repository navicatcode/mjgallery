package com.mjgallery.mjgallery.mvp.model.bean;

public class InvitedPlayersBean {


    /**
     * userId : 617
     * nickName : test006
     * headImg :
     * inviteCode : lU5X14
     * privacy : 0
     * sysBrokerageAmount : 0
     * type : 0
     * heLike : 0
     * heFans : 0
     */

    private int userId;
    private String nickName;
    private String headImg;
    private String inviteCode;
    private int privacy;
    private int sysBrokerageAmount;
    private int type;
    private int heLike;
    private int heFans;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public int getSysBrokerageAmount() {
        return sysBrokerageAmount;
    }

    public void setSysBrokerageAmount(int sysBrokerageAmount) {
        this.sysBrokerageAmount = sysBrokerageAmount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeLike() {
        return heLike;
    }

    public void setHeLike(int heLike) {
        this.heLike = heLike;
    }

    public int getHeFans() {
        return heFans;
    }

    public void setHeFans(int heFans) {
        this.heFans = heFans;
    }
}
