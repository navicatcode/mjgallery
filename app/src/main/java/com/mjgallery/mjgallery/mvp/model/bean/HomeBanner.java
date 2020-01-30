package com.mjgallery.mjgallery.mvp.model.bean;


import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;
public class HomeBanner extends BaseResponse<List<HomeBanner>> {

    /**
     * isCollection : 0
     * isThumpUp : 0
     * addOrUpdate : false
     * imgPath : http://192.168.0.212:8080/attachment/advert/picture/2019/9/6/c0c670e8-ad36-462c-a72d-58d646a8eff3_banner.jpg
     * url :
     * sort : 2
     * openStyle : 1
     * picture_SERVER_ADDRESS_PREFIX : http://192.168.0.212:8080
     */

    private int isCollection;
    private int isThumpUp;
    private boolean addOrUpdate;
    private String imgPath;
    private String url;
    private int sort;
    private String openStyle;
    private String picture_SERVER_ADDRESS_PREFIX;
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

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

    public boolean isAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(boolean addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(String openStyle) {
        this.openStyle = openStyle;
    }

    public String getPicture_SERVER_ADDRESS_PREFIX() {
        return picture_SERVER_ADDRESS_PREFIX;
    }

    public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
        this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
    }
}
