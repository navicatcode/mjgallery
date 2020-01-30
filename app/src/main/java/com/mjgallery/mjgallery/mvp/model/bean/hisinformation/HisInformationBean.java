package com.mjgallery.mjgallery.mvp.model.bean.hisinformation;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.search.Obj;

public class HisInformationBean extends BaseResponse<HisInformationBean> {

        /**
         * nikeName : admin
         * headImg : http://192.168.0.212:8080/attachment/headImg/1565883895782ok3wn5wUdhegUigVcuqj3SlmoN2A.jpg
         * inviteCode : 10001
         * settlement : 1
         * privacy : 0
         * amount : 0.00
         * fans : 1
         * type : 1
         */

        private String nikeName;
        private String headImg;
        private String inviteCode;
        private int settlement;
        private int privacy;
        private String amount;
        private String fans;
        private String myConcern;
        private int type;
        private Obj obj;

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
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

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getSettlement() {
            return settlement;
        }

        public void setSettlement(int settlement) {
            this.settlement = settlement;
        }

        public int getPrivacy() {
            return privacy;
        }

        public void setPrivacy(int privacy) {
            this.privacy = privacy;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getMyConcern() {
        return myConcern;
    }

    public void setMyConcern(String myConcern) {
        this.myConcern = myConcern;
    }

    public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
  
}
