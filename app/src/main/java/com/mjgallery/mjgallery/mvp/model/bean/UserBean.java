package com.mjgallery.mjgallery.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {
    /**
     * createTime : 2019-04-13 12:40
     * headPic :
     * id : 101
     * mobile : 15617686902
     * online : 0
     * sex : 0
     * status : 1
     * type : 2
     * updateTime : 1555130402000
     * yixinId : 100101
     */

    private String createTime;
    private String headPic;
    private int id;
    private String mobile;
    private int sex;
    private int status;
    private int type;
    private long updateTime;
    private String signature;
    private String nickname;
    private String wechatHeadPic;
    private String wechatNickname;
    private String wechatUnionId;

    public String getWechatHeadPic() {
        return wechatHeadPic;
    }

    public void setWechatHeadPic(String wechatHeadPic) {
        this.wechatHeadPic = wechatHeadPic;
    }

    public String getWechatNickname() {
        return wechatNickname;
    }

    public void setWechatNickname(String wechatNickname) {
        this.wechatNickname = wechatNickname;
    }

    public String getWechatUnionId() {
        return wechatUnionId;
    }

    public void setWechatUnionId(String wechatUnionId) {
        this.wechatUnionId = wechatUnionId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.headPic);
        dest.writeInt(this.id);
        dest.writeString(this.mobile);
        dest.writeInt(this.sex);
        dest.writeInt(this.status);
        dest.writeInt(this.type);
        dest.writeLong(this.updateTime);
        dest.writeString(this.signature);
        dest.writeString(this.nickname);
        dest.writeString(this.wechatHeadPic);
        dest.writeString(this.wechatNickname);
        dest.writeString(this.wechatUnionId);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.createTime = in.readString();
        this.headPic = in.readString();
        this.id = in.readInt();
        this.mobile = in.readString();
        this.sex = in.readInt();
        this.status = in.readInt();
        this.type = in.readInt();
        this.updateTime = in.readLong();
        this.signature = in.readString();
        this.nickname = in.readString();
        this.wechatHeadPic = in.readString();
        this.wechatNickname = in.readString();
        this.wechatUnionId = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
