package com.mjgallery.mjgallery.mvp.model.bean.home;

import com.orient.tea.barragephoto.model.DataSource;

import java.util.List;

public class HomeBarrageDataBean implements DataSource {

        /**
         * commentId : 55
         * commentUserId : 251
         * commentContent : 一老伙计丢车，当他把新买的一辆车放在楼下时他上了三把锁并夹了一张纸:让你丫偷！第二天车没丢，并且多了两把锁和一张，上写着：让你丫骑．
         * commentTime : 1565699463000
         * commentUuid : 1559
         * commentType : 1
         * subCommentDetailDos : [{"subCommentId":9,"subCommentContent":"丈夫出其不意回到家，看到床边的烟灰缸仍有冒着烟的雪茄，满腹狐疑地瞪着那根雪茄，对着缩在床头抖缩的妻子咆哮：＂这从哪里来得？一阵沉寂之后，从衣橱中传出发抖的男人的声音：＂古巴．＂","subCommentType":"4","subCommentTime":1565699840000,"subCommentUuid":55,"thumbUpCount":13,"userDO":{"id":1,"nikeName":"admin","concernByUserCount":0,"concernUserCount":0}},{"subCommentId":7,"subCommentContent":"某男向某女求爱，用二胡拉了一曲＜二泉映月＞．事后女的说：＂二胡拉的不咋地，人长的倒是和瞎子阿炳挺像．＂","subCommentType":"4","subCommentTime":1565699799000,"subCommentUuid":55,"thumbUpCount":5,"userDO":{"id":1,"nikeName":"admin","concernByUserCount":0,"concernUserCount":0}}]
         * userDO : {"id":251,"username":"很多很多很多后","nikeName":"很多很多很多后","headImg":"http://192.168.0.212:8080/attachment/headImg/1565958319195.jpg","concernByUserCount":0,"concernUserCount":0}
         * isCanDelete : 0
         * thumbUpCount : 23
         * commentCount : 0
         * isThumpUp : 0
         * pageInfo : {"total":3,"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":2,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2],"navigateFirstPage":1,"navigateLastPage":2,"firstPage":1,"lastPage":2}
         */

        private int commentId;
        private int commentUserId;
        private String commentContent;
        private long commentTime;
        private int commentUuid;
        private String commentType;
        private HomeDetailsBean.DataBean.CommentDetailDoBean.UserDOBean userDO;
        private int isCanDelete;
        private int thumbUpCount;
        private int commentCount;
        private int isThumpUp;
        private HomeDetailsBean.DataBean.CommentDetailDoBean.PageInfoBean pageInfo;
        private List<HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean> subCommentDetailDos;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
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

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
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

        public HomeDetailsBean.DataBean.CommentDetailDoBean.UserDOBean getUserDO() {
            return userDO;
        }

        public void setUserDO(HomeDetailsBean.DataBean.CommentDetailDoBean.UserDOBean userDO) {
            this.userDO = userDO;
        }

        public int getIsCanDelete() {
            return isCanDelete;
        }

        public void setIsCanDelete(int isCanDelete) {
            this.isCanDelete = isCanDelete;
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

        public int getIsThumpUp() {
            return isThumpUp;
        }

        public void setIsThumpUp(int isThumpUp) {
            this.isThumpUp = isThumpUp;
        }

        public HomeDetailsBean.DataBean.CommentDetailDoBean.PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(HomeDetailsBean.DataBean.CommentDetailDoBean.PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public List<HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean> getSubCommentDetailDos() {
            return subCommentDetailDos;
        }

        public void setSubCommentDetailDos(List<HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean> subCommentDetailDos) {
            this.subCommentDetailDos = subCommentDetailDos;
        }

    @Override
    public int getType() {
        return 0;
    }

    public static class UserDOBean {
            /**
             * id : 251
             * username : 很多很多很多后
             * nikeName : 很多很多很多后
             * headImg : http://192.168.0.212:8080/attachment/headImg/1565958319195.jpg
             * concernByUserCount : 0
             * concernUserCount : 0
             */

            private int id;
            private String username;
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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
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

        public static class PageInfoBean {
            /**
             * total : 3
             * pageNum : 1
             * pageSize : 2
             * size : 2
             * startRow : 1
             * endRow : 2
             * pages : 2
             * prePage : 0
             * nextPage : 2
             * isFirstPage : true
             * isLastPage : false
             * hasPreviousPage : false
             * hasNextPage : true
             * navigatePages : 8
             * navigatepageNums : [1,2]
             * navigateFirstPage : 1
             * navigateLastPage : 2
             * firstPage : 1
             * lastPage : 2
             */

            private int total;
            private int pageNum;
            private int pageSize;
            private int size;
            private int startRow;
            private int endRow;
            private int pages;
            private int prePage;
            private int nextPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private boolean hasPreviousPage;
            private boolean hasNextPage;
            private int navigatePages;
            private int navigateFirstPage;
            private int navigateLastPage;
            private int firstPage;
            private int lastPage;
            private List<Integer> navigatepageNums;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public int getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(int navigatePages) {
                this.navigatePages = navigatePages;
            }

            public int getNavigateFirstPage() {
                return navigateFirstPage;
            }

            public void setNavigateFirstPage(int navigateFirstPage) {
                this.navigateFirstPage = navigateFirstPage;
            }

            public int getNavigateLastPage() {
                return navigateLastPage;
            }

            public void setNavigateLastPage(int navigateLastPage) {
                this.navigateLastPage = navigateLastPage;
            }

            public int getFirstPage() {
                return firstPage;
            }

            public void setFirstPage(int firstPage) {
                this.firstPage = firstPage;
            }

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }
        }

        public static class SubCommentDetailDosBean {
            /**
             * subCommentId : 9
             * subCommentContent : 丈夫出其不意回到家，看到床边的烟灰缸仍有冒着烟的雪茄，满腹狐疑地瞪着那根雪茄，对着缩在床头抖缩的妻子咆哮：＂这从哪里来得？一阵沉寂之后，从衣橱中传出发抖的男人的声音：＂古巴．＂
             * subCommentType : 4
             * subCommentTime : 1565699840000
             * subCommentUuid : 55
             * thumbUpCount : 13
             * userDO : {"id":1,"nikeName":"admin","concernByUserCount":0,"concernUserCount":0}
             */

            private int subCommentId;
            private String subCommentContent;
            private String subCommentType;
            private long subCommentTime;
            private int subCommentUuid;
            private int thumbUpCount;
            private HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean.UserDOBeanX userDO;

            public int getSubCommentId() {
                return subCommentId;
            }

            public void setSubCommentId(int subCommentId) {
                this.subCommentId = subCommentId;
            }

            public String getSubCommentContent() {
                return subCommentContent;
            }

            public void setSubCommentContent(String subCommentContent) {
                this.subCommentContent = subCommentContent;
            }

            public String getSubCommentType() {
                return subCommentType;
            }

            public void setSubCommentType(String subCommentType) {
                this.subCommentType = subCommentType;
            }

            public long getSubCommentTime() {
                return subCommentTime;
            }

            public void setSubCommentTime(long subCommentTime) {
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

            public HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean.UserDOBeanX getUserDO() {
                return userDO;
            }

            public void setUserDO(HomeDetailsBean.DataBean.CommentDetailDoBean.SubCommentDetailDosBean.UserDOBeanX userDO) {
                this.userDO = userDO;
            }

            public static class UserDOBeanX {
                /**
                 * id : 1
                 * nikeName : admin
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
        }
}
