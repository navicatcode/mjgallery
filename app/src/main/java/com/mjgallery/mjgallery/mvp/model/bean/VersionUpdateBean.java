package com.mjgallery.mjgallery.mvp.model.bean;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class VersionUpdateBean extends BaseResponse<VersionUpdateBean> {

    private int isCollection;
    private int isThumpUp;
    private int type;
    private int versionCode;    //版本代码
    private String versionName; //版本名称，如：v1.0.1
    private String url; //apk下载琏接
    private int forceUpdate; //1是强制更新，非1不是强制更新
    private List<String> versionDesc;//更新内容描述
    private String picture_SERVER_ADDRESS_PREFIX;

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getIsThumpUp() {
        return isThumpUp;
    }

    public void setIsThumpUp(int isThumpUp) {
        this.isThumpUp = isThumpUp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public List<String> getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(List<String> versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getPicture_SERVER_ADDRESS_PREFIX() {
        return picture_SERVER_ADDRESS_PREFIX;
    }

    public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
        this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
    }
}
