package com.mjgallery.mjgallery.mvp.model.bean.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mjgallery.mjgallery.app.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 发现和推荐的实体类
 */
public class HomeBean extends BaseResponse<List<HomeBean>> implements Serializable, MultiItemEntity {


    /**
     * picId : 8
     * picName : (新版)四柱预测B
     * picTypeName : 1
     * terms : 123
     * year : 2019
     * supportCount : 27
     * collectionCount : 10
     * browsingVolume : 35
     * picComment : 1
     * smallHeight : 906
     * smallWidth : 496
     * filePathYs : /attachment/recommend/picture/2019/8/6/123.jpg
     */

    private int picId;
    private String picName;
    private String picTypeName;
    private int terms;
    private int year;
    private int supportCount;
    private int collectionCount;
    private int browsingVolume;
    private int picComment;
    private int smallHeight;
    private int smallWidth;
    private String filePathYs;

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicTypeName() {
        return picTypeName;
    }

    public void setPicTypeName(String picTypeName) {
        this.picTypeName = picTypeName;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getBrowsingVolume() {
        return browsingVolume;
    }

    public void setBrowsingVolume(int browsingVolume) {
        this.browsingVolume = browsingVolume;
    }

    public int getPicComment() {
        return picComment;
    }

    public void setPicComment(int picComment) {
        this.picComment = picComment;
    }

    public int getSmallHeight() {
        return smallHeight;
    }

    public void setSmallHeight(int smallHeight) {
        this.smallHeight = smallHeight;
    }

    public int getSmallWidth() {
        return smallWidth;
    }

    public void setSmallWidth(int smallWidth) {
        this.smallWidth = smallWidth;
    }

    public String getFilePathYs() {
        return filePathYs;
    }

    public void setFilePathYs(String filePathYs) {
        this.filePathYs = filePathYs;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "picId=" + picId +
                ", picName='" + picName + '\'' +
                ", picTypeName='" + picTypeName + '\'' +
                ", terms=" + terms +
                ", year=" + year +
                ", supportCount=" + supportCount +
                ", collectionCount=" + collectionCount +
                ", browsingVolume=" + browsingVolume +
                ", picComment=" + picComment +
                ", smallHeight=" + smallHeight +
                ", smallWidth=" + smallWidth +
                ", filePathYs='" + filePathYs + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
