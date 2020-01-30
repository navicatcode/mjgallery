package com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord;

import java.util.List;

public class RewardViewBean {
        /**
         * accumulateSign : [0,0,0,0]
         * totalSignCount : 8
         * signStatus : false
         */

        private int totalSignCount;
        private boolean signStatus;
        private List<Integer> accumulateSign;

        public int getTotalSignCount() {
            return totalSignCount;
        }

        public void setTotalSignCount(int totalSignCount) {
            this.totalSignCount = totalSignCount;
        }

        public boolean isSignStatus() {
            return signStatus;
        }

        public void setSignStatus(boolean signStatus) {
            this.signStatus = signStatus;
        }

        public List<Integer> getAccumulateSign() {
            return accumulateSign;
        }

        public void setAccumulateSign(List<Integer> accumulateSign) {
            this.accumulateSign = accumulateSign;
        }
}
