package com.mjgallery.mjgallery.mvp.model.bean.mine;


import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class MyCommentsBean  extends BaseResponse<List<MyCommentsBean>> {

        /**
         * content : 一老伙计丢车，当他把新买的一辆车放在楼下时他上了三把锁并夹了一张纸:让你丫偷！第二天车没丢，并且多了两把锁和一张，上写着：让你丫骑．
         * type : 1
         * title : (新版)四柱预测B
         * myName : 很多很多很多后
         * headImg : http://192.168.0.212:8080/attachment/headImg/1565958319195.jpg
         * filePath : /attachment/recommend/picture/2019/8/13/导航.jpg
         * height : 1465
         * width : 750
         * picId : 1507
         * commentTime : 1565626845000
         * year : 2016
         * terms : 1
         */

        private String content;
        private int type;
        private String title;
        private String myName;
        private String headImg;
        private String filePath;
        private int height;
        private int width;
        private int picId;
        private long commentTime;
        private String year;
        private String terms;


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMyName() {
            return myName;
        }

        public void setMyName(String myName) {
            this.myName = myName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
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

        public int getPicId() {
            return picId;
        }

        public void setPicId(int picId) {
            this.picId = picId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

}
