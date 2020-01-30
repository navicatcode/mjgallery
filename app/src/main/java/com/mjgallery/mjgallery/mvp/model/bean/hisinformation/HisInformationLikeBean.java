package com.mjgallery.mjgallery.mvp.model.bean.hisinformation;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class HisInformationLikeBean extends BaseResponse<List<HisInformationLikeBean>> implements MultiItemEntity {





    /**
     * isCollection : 0
     * isThumpUp : 0
     * multiple : false
     * showId : 11173
     * thumbUpCount : 5
     * commentCount : 3
     * clickCount : 58
     * filePath : http://www.imxiaomang.cn/files/show/picture/2019/10/7/6628eef52c5e4d25a24eac0cd77f63a7.jpeg
     * height : 2248
     * width : 1080
     * type : 2
     * subType : 0
     * picture_SERVER_ADDRESS_PREFIX : http://www.imxiaomang.cn/files
     */

    private int isCollection;
    private int isThumpUp;
    private boolean multiple;
    private int showId;
    private String description;
    private int thumbUpCount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int commentCount;
    private int clickCount;
    private String filePath;
    private int height;
    private String content;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    private int width;
    private int type;
    private int subType;
    private int itemType;
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

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getThumbUpCount() {
        return thumbUpCount;
    }

    public void setThumbUpCount(int thumbUpCount) {
        this.thumbUpCount = thumbUpCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getPicture_SERVER_ADDRESS_PREFIX() {
        return picture_SERVER_ADDRESS_PREFIX;
    }

    public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
        this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
    }
}
