package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class GetUserInfoBean extends BaseResponse<List<GetUserInfoBean>> {


    private int commentUserId; //用户id
    private String commentContent;//评论内容
    private String commentTime; //评论时间
    private int commentUuid;    //评论的链接或文章或图片，它的id
    private String commentType; //0为首页，1为圈子
    private int thumbUpCount; //点赞数
    private int commentCount; //评论数
    private int clickCount; //浏览数
    private String title;   //评论的链接或文章或图片，它的标题
    private String content; //类型为资料时，这是资料内容
    private int showType; //当为圈子类型时，再一步判断这变量，（0-图片，1-视频，2-资料）
    private String createTime; //评论的链接或文章或图片，它的时间
    private String filePath; //图片琏接
    private int width; //图片的宽
    private int height; //图片的长

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentUuid() {
        return commentUuid;
    }

    public void setCommentUuid(int commentUuid) {
        this.commentUuid = commentUuid;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
