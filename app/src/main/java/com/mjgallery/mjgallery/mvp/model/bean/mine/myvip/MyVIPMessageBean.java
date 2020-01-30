package com.mjgallery.mjgallery.mvp.model.bean.mine.myvip;

import java.util.List;

public class MyVIPMessageBean {



        /**
         * id : 156
         * now : 200
         * next : 500
         * nextVipGrade : vip3
         * rewardCommtent : 价值1W元的iPhoneX
         * isAvailable : 0
         * obj : [{"id":1,"vipGrade":"vip1","vipTitle":"路人","vipIntegral":100,"vipReward":"iphoneX","createTime":1571819023000,"updateTime":1572507853000,"rewardUnit":"2","rewardCommtent":"价值1W元的iPhoneX","code":1,"isRevice":0},{"id":2,"vipGrade":"vip2","vipTitle":"黑铁","vipIntegral":200,"vipReward":"58.00","createTime":1571819500000,"updateTime":1572507857000,"rewardUnit":"1","rewardCommtent":"58元","code":2,"isRevice":0},{"id":3,"vipGrade":"vip3","vipTitle":"青铜","vipIntegral":500,"vipReward":"128.00","createTime":1571822452000,"updateTime":1572507858000,"rewardUnit":"1","code":3,"isRevice":0},{"id":4,"vipGrade":"vip4","vipTitle":"白银","vipIntegral":1000,"vipReward":"288.00","createTime":1571822472000,"updateTime":1572507859000,"rewardUnit":"1","code":4,"isRevice":0},{"id":5,"vipGrade":"vip5","vipTitle":"黄金","vipIntegral":2000,"vipReward":"368.00","createTime":1571822493000,"updateTime":1572507859000,"rewardUnit":"1","code":5,"isRevice":0},{"id":6,"vipGrade":"vip6","vipTitle":"铂金","vipIntegral":5000,"vipReward":"588.00","createTime":1571831984000,"updateTime":1572507860000,"rewardUnit":"1","code":6,"isRevice":0},{"id":7,"vipGrade":"vip7","vipTitle":"钻石","vipIntegral":10000,"vipReward":"888.00","createTime":1571832009000,"updateTime":1572507861000,"rewardUnit":"1","code":7,"isRevice":0},{"id":8,"vipGrade":"vip8","vipTitle":"大师","vipIntegral":50000,"vipReward":"1888.00","createTime":1571832038000,"updateTime":1572507862000,"rewardUnit":"1","code":8,"isRevice":0},{"id":9,"vipGrade":"vip9","vipTitle":"星耀","vipIntegral":100000,"vipReward":"5888.00","createTime":1571832064000,"updateTime":1572507863000,"rewardUnit":"1","code":9,"isRevice":0},{"id":10,"vipGrade":"vip10","vipTitle":"王者","vipIntegral":500000,"vipReward":"18888.00","createTime":1571832099000,"updateTime":1572507864000,"rewardUnit":"1","code":10,"isRevice":0}]
         */

        private int id;

    public String getNextRewardCommtent() {
        return nextRewardCommtent;
    }

    public void setNextRewardCommtent(String nextRewardCommtent) {
        this.nextRewardCommtent = nextRewardCommtent;
    }

    private String now;
    private String nextRewardCommtent;

    public String getNowVipReward() {
        return nowVipReward;
    }

    public void setNowVipReward(String nowVipReward) {
        this.nowVipReward = nowVipReward;
    }

    private String next;
        private String nowVipReward;

    public String getNowVipGrade() {
        return nowVipGrade;
    }

    public void setNowVipGrade(String nowVipGrade) {
        this.nowVipGrade = nowVipGrade;
    }

    private String nextVipGrade;
        private String nowVipGrade;
        private String rewardCommtent;
        private int isAvailable;
        private List<ObjBean> obj;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNow() {
            return now;
        }

        public void setNow(String now) {
            this.now = now;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getNextVipGrade() {
            return nextVipGrade;
        }

        public void setNextVipGrade(String nextVipGrade) {
            this.nextVipGrade = nextVipGrade;
        }

        public String getRewardCommtent() {
            return rewardCommtent;
        }

        public void setRewardCommtent(String rewardCommtent) {
            this.rewardCommtent = rewardCommtent;
        }

        public int getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public List<ObjBean> getObj() {
            return obj;
        }

        public void setObj(List<ObjBean> obj) {
            this.obj = obj;
        }

        public static class ObjBean {
            /**
             * id : 1
             * vipGrade : vip1
             * vipTitle : 路人
             * vipIntegral : 100
             * vipReward : iphoneX
             * createTime : 1571819023000
             * updateTime : 1572507853000
             * rewardUnit : 2
             * rewardCommtent : 价值1W元的iPhoneX
             * code : 1
             * isRevice : 0
             */

            private int id;
            private String vipGrade;
            private String vipTitle;
            private int vipIntegral;
            private String vipReward;
            private long createTime;
            private long updateTime;
            private String rewardUnit;
            private String rewardCommtent;
            private int code;
            private int isRevice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVipGrade() {
                return vipGrade;
            }

            public void setVipGrade(String vipGrade) {
                this.vipGrade = vipGrade;
            }

            public String getVipTitle() {
                return vipTitle;
            }

            public void setVipTitle(String vipTitle) {
                this.vipTitle = vipTitle;
            }

            public int getVipIntegral() {
                return vipIntegral;
            }

            public void setVipIntegral(int vipIntegral) {
                this.vipIntegral = vipIntegral;
            }

            public String getVipReward() {
                return vipReward;
            }

            public void setVipReward(String vipReward) {
                this.vipReward = vipReward;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getRewardUnit() {
                return rewardUnit;
            }

            public void setRewardUnit(String rewardUnit) {
                this.rewardUnit = rewardUnit;
            }

            public String getRewardCommtent() {
                return rewardCommtent;
            }

            public void setRewardCommtent(String rewardCommtent) {
                this.rewardCommtent = rewardCommtent;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getIsRevice() {
                return isRevice;
            }

            public void setIsRevice(int isRevice) {
                this.isRevice = isRevice;
            }
        }
}
