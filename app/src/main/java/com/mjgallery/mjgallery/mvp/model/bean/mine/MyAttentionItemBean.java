package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class MyAttentionItemBean extends BaseResponse<List<MyAttentionItemBean>> {

        /**
         * userId : 259
         * nickName : 大哥
         * headImg : http://192.168.0.212:8080/attachment/headImg/1565958822343.jpg
         * inviteCode : v1hL1A
         * privacy : 0
         * sysBrokerageAmount : 1000
         * type : 1
         * heLike : 3
         * heFans : 3
         */

        private int userId;
        private String nickName;
        private String headImg;
        private String inviteCode;
        private int privacy;
        private int sysBrokerageAmount;
        private int type;
        private int heLike;
        private int heFans;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getPrivacy() {
            return privacy;
        }

        public void setPrivacy(int privacy) {
            this.privacy = privacy;
        }

        public int getSysBrokerageAmount() {
            return sysBrokerageAmount;
        }

        public void setSysBrokerageAmount(int sysBrokerageAmount) {
            this.sysBrokerageAmount = sysBrokerageAmount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getHeLike() {
            return heLike;
        }

        public void setHeLike(int heLike) {
            this.heLike = heLike;
        }

        public int getHeFans() {
            return heFans;
        }

        public void setHeFans(int heFans) {
            this.heFans = heFans;
        }
}
