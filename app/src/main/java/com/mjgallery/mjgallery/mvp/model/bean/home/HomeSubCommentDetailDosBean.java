package com.mjgallery.mjgallery.mvp.model.bean.home;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class HomeSubCommentDetailDosBean extends BaseResponse<HomeSubCommentDetailDosBean> {

    /**
     * data : [{"subCommentId":147,"subCommentContent":"hdjjdjdj","subCommentTime":"2019-09-05 08:10:39","subCommentUuid":157,"thumbUpCount":0,"userDO":{"id":251,"nikeName":"很多很多很多后","concernByUserCount":0,"concernUserCount":0}},{"subCommentId":137,"subCommentContent":"Huuuuhhhhhhh did t the same thing happened at least once before update I had no idea why you couldn\u2019t put them down on I just got back out early so I I need kids know when I r I don\u2019t think I could ever see any ","subCommentTime":"2019-09-04 21:23:46","subCommentUuid":157,"thumbUpCount":0,"userDO":{"id":343,"nikeName":"野菊花","concernByUserCount":0,"concernUserCount":0}},{"subCommentId":29,"subCommentContent":"Nixdf ","subCommentTime":"2019-08-27 05:28:46","subCommentUuid":157,"thumbUpCount":0,"userDO":{"id":305,"nikeName":"盛唐","concernByUserCount":0,"concernUserCount":0}},{"subCommentId":31,"subCommentContent":"Nixdf ","subCommentTime":"2019-08-27 05:28:43","subCommentUuid":157,"thumbUpCount":0,"userDO":{"id":305,"nikeName":"盛唐","concernByUserCount":0,"concernUserCount":0}},{"subCommentId":27,"subCommentContent":"Nixdf ","subCommentTime":"2019-08-27 05:28:11","subCommentUuid":157,"thumbUpCount":0,"userDO":{"id":305,"nikeName":"盛唐","concernByUserCount":0,"concernUserCount":0}}]
     * condition : {"pageIndex":0,"pageSize":10,"pageCount":0,"totalCount":0,"id":157,"commentType":"1","limitStart":0,"limitEnd":0}
     */

    private ConditionBean condition;
    private List<DataBean> data;

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
        this.condition = condition;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ConditionBean {
        /**
         * pageIndex : 0
         * pageSize : 10
         * pageCount : 0
         * totalCount : 0
         * id : 157
         * commentType : 1
         * limitStart : 0
         * limitEnd : 0
         */

        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int totalCount;
        private int id;
        private String commentType;
        private int limitStart;
        private int limitEnd;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommentType() {
            return commentType;
        }

        public void setCommentType(String commentType) {
            this.commentType = commentType;
        }

        public int getLimitStart() {
            return limitStart;
        }

        public void setLimitStart(int limitStart) {
            this.limitStart = limitStart;
        }

        public int getLimitEnd() {
            return limitEnd;
        }

        public void setLimitEnd(int limitEnd) {
            this.limitEnd = limitEnd;
        }
    }

    public static class DataBean {

        /**
         * subCommentId : 121
         * replyUserId : 251
         * subCommentContent : 解放军的基督教基督教的基督教
         * subCommentTime : 2019-09-02 09:42:33
         * subCommentUuid : 61
         * thumbUpCount : 0
         * subCommentRelateId : 33
         * userDO : {"id":251,"nikeName":"很多很多很多后","concernByUserCount":0,"concernUserCount":0}
         * replyUserDO : {"id":251,"username":"很多很多很多后","concernByUserCount":0,"concernUserCount":0}
         */

        private int subCommentId;
        private int replyUserId;
        private String subCommentContent;
        private String subCommentTime;
        private int subCommentUuid;
        private int thumbUpCount;
        private int subCommentRelateId;
        private HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanXX userDO;
        private HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean replyUserDO;

        public int getSubCommentId() {
            return subCommentId;
        }

        public void setSubCommentId(int subCommentId) {
            this.subCommentId = subCommentId;
        }

        public int getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(int replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getSubCommentContent() {
            return subCommentContent;
        }

        public void setSubCommentContent(String subCommentContent) {
            this.subCommentContent = subCommentContent;
        }

        public String getSubCommentTime() {
            return subCommentTime;
        }

        public void setSubCommentTime(String subCommentTime) {
            this.subCommentTime = subCommentTime;
        }

        public int getSubCommentUuid() {
            return subCommentUuid;
        }

        public void setSubCommentUuid(int subCommentUuid) {
            this.subCommentUuid = subCommentUuid;
        }

        public int getThumbUpCount() {
            return thumbUpCount;
        }

        public void setThumbUpCount(int thumbUpCount) {
            this.thumbUpCount = thumbUpCount;
        }

        public int getSubCommentRelateId() {
            return subCommentRelateId;
        }

        public void setSubCommentRelateId(int subCommentRelateId) {
            this.subCommentRelateId = subCommentRelateId;
        }

        public HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanXX getUserDO() {
            return userDO;
        }

        public void setUserDO(HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.UserDOBeanXX userDO) {
            this.userDO = userDO;
        }

        public HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean getReplyUserDO() {
            return replyUserDO;
        }

        public void setReplyUserDO(HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean.ReplyUserDOBean replyUserDO) {
            this.replyUserDO = replyUserDO;
        }

        public static class UserDOBeanXX {
            /**
             * id : 251
             * nikeName : 很多很多很多后
             * concernByUserCount : 0
             * concernUserCount : 0
             */

            private int id;
            private String nikeName;
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

        public static class ReplyUserDOBean {
            /**
             * id : 251
             * username : 很多很多很多后
             * concernByUserCount : 0
             * concernUserCount : 0
             */

            private int id;
            private String username;
            private String nikeName;
            private int concernByUserCount;
            private int concernUserCount;

            public String getNikeName() {
                return nikeName;
            }

            public void setNikeName(String nikeName) {
                this.nikeName = nikeName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
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
    }
}
