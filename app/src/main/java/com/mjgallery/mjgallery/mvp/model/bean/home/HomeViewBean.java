package com.mjgallery.mjgallery.mvp.model.bean.home;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class HomeViewBean extends BaseResponse<List<HomeViewBean>> implements MultiItemEntity {


        /**
         * createTime : 2019-08-22 05:40:15
         * isCollection : 0
         * isThumpUp : 0
         * collectionCount : 0
         * id : 655
         * description : xacacas
         * thumbUpCount : 1
         * commentCount : 0
         * clickCount : 7
         * type : 0
         * showPictureDoList : [{"createTime":"2019-08-22 05:40:15","isCollection":0,"isThumpUp":0,"id":655,"filePath":"http://192.168.0.212:8080null","picture_SERVER_ADDRESS_PREFIX":"http://192.168.0.212:8080"}]
         * showPictureDo : {"createTime":"2019-08-22 06:41:12","isCollection":0,"isThumpUp":0,"id":893,"showId":655,"filePath":"http://192.168.0.212:8080/attachment/discovery/picture/2019/8/22/66568e77-078b-45e6-aacc-cfda070812c2_19_1_g332.jpg","height":1316,"width":1906,"picture_SERVER_ADDRESS_PREFIX":"http://192.168.0.212:8080"}
         * userDO : {"id":1,"nikeName":"admin","headImg":"http://192.168.0.212:8080/attachment/headImg/1565883895782ok3wn5wUdhegUigVcuqj3SlmoN2A.jpg","concernByUserCount":0,"concernUserCount":0}
         * picture_SERVER_ADDRESS_PREFIX : http://192.168.0.212:8080
         */

        private String createTime;
        private int isCollection;
        private int isThumpUp;
        private int collectionCount;
        private int id;
        private String description;
        private int thumbUpCount;
        private int commentCount;
        private int clickCount;
        private int type;
        private ShowPictureDoBean showPictureDo;
        private UserDOBean userDO;
        private String picture_SERVER_ADDRESS_PREFIX;
        private List<ShowPictureDoListBean> showPictureDoList;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public ShowPictureDoBean getShowPictureDo() {
            return showPictureDo;
        }

        public void setShowPictureDo(ShowPictureDoBean showPictureDo) {
            this.showPictureDo = showPictureDo;
        }

        public UserDOBean getUserDO() {
            return userDO;
        }

        public void setUserDO(UserDOBean userDO) {
            this.userDO = userDO;
        }

        public String getPicture_SERVER_ADDRESS_PREFIX() {
            return picture_SERVER_ADDRESS_PREFIX;
        }

        public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
            this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
        }

        public List<ShowPictureDoListBean> getShowPictureDoList() {
            return showPictureDoList;
        }

        public void setShowPictureDoList(List<ShowPictureDoListBean> showPictureDoList) {
            this.showPictureDoList = showPictureDoList;
        }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class ShowPictureDoBean {
            /**
             * createTime : 2019-08-22 06:41:12
             * isCollection : 0
             * isThumpUp : 0
             * id : 893
             * showId : 655
             * filePath : http://192.168.0.212:8080/attachment/discovery/picture/2019/8/22/66568e77-078b-45e6-aacc-cfda070812c2_19_1_g332.jpg
             * height : 1316
             * width : 1906
             * picture_SERVER_ADDRESS_PREFIX : http://192.168.0.212:8080
             */

            private String createTime;
            private int isCollection;
            private int isThumpUp;
            private int id;
            private int showId;
            private String filePath;
            private int height;
            private int width;
            private String picture_SERVER_ADDRESS_PREFIX;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShowId() {
                return showId;
            }

            public void setShowId(int showId) {
                this.showId = showId;
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

            public String getPicture_SERVER_ADDRESS_PREFIX() {
                return picture_SERVER_ADDRESS_PREFIX;
            }

            public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
                this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
            }
        }

        public static class UserDOBean {
            /**
             * id : 1
             * nikeName : admin
             * headImg : http://192.168.0.212:8080/attachment/headImg/1565883895782ok3wn5wUdhegUigVcuqj3SlmoN2A.jpg
             * concernByUserCount : 0
             * concernUserCount : 0
             */

            private int id;
            private String nikeName;
            private String headImg;
            private int concernByUserCount;
            private int concernUserCount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNikeName() {
                return nikeName;
            }

            public void setNikeName(String nikeName) {
                this.nikeName = nikeName;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getConcernByUserCount() {
                return concernByUserCount;
            }

            public void setConcernByUserCount(int concernByUserCount) {
                this.concernByUserCount = concernByUserCount;
            }

            public int getConcernUserCount() {
                return concernUserCount;
            }

            public void setConcernUserCount(int concernUserCount) {
                this.concernUserCount = concernUserCount;
            }
        }

        public static class ShowPictureDoListBean {
            /**
             * createTime : 2019-08-22 05:40:15
             * isCollection : 0
             * isThumpUp : 0
             * id : 655
             * filePath : http://192.168.0.212:8080null
             * picture_SERVER_ADDRESS_PREFIX : http://192.168.0.212:8080
             */

            private String createTime;
            private int isCollection;
            private int isThumpUp;
            private int id;
            private String filePath;
            private String picture_SERVER_ADDRESS_PREFIX;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getPicture_SERVER_ADDRESS_PREFIX() {
                return picture_SERVER_ADDRESS_PREFIX;
            }

            public void setPicture_SERVER_ADDRESS_PREFIX(String picture_SERVER_ADDRESS_PREFIX) {
                this.picture_SERVER_ADDRESS_PREFIX = picture_SERVER_ADDRESS_PREFIX;
            }
        }
}
