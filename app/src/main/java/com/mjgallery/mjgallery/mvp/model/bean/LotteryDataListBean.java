package com.mjgallery.mjgallery.mvp.model.bean;

import java.util.List;

public class LotteryDataListBean {

    /**
     * typeName : 特肖记录
     * lotteryData : [{"sx":"鼠肖","number":[12,24,36,48],"latestPeriod":89,"continuousPeriodUnopened":29,"maxPeriodUnopened":{"period":74,"beginPeriod":"1985050","endPeriod":"1986020"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2009110","endPeriod":"2009112"}},{"sx":"牛肖","number":[11,23,35,47],"latestPeriod":104,"continuousPeriodUnopened":14,"maxPeriodUnopened":{"period":116,"beginPeriod":"2011082","endPeriod":"2012043"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"1998090","endPeriod":"1998092"}},{"sx":"虎肖","number":[10,22,34,46],"latestPeriod":101,"continuousPeriodUnopened":17,"maxPeriodUnopened":{"period":62,"beginPeriod":"1997033","endPeriod":"1997094"},"maxPeriodContinuousOpen":{"period":4,"beginPeriod":"1990030","endPeriod":"1990033"}},{"sx":"兔肖","number":[9,21,33,45],"latestPeriod":112,"continuousPeriodUnopened":6,"maxPeriodUnopened":{"period":73,"beginPeriod":"1999008","endPeriod":"1999080"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2018005","endPeriod":"2018007"}},{"sx":"龙肖","number":[8,20,32,44],"latestPeriod":118,"continuousPeriodUnopened":0,"maxPeriodUnopened":{"period":67,"beginPeriod":"1995018","endPeriod":"1995084"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2017143","endPeriod":"2017145"}},{"sx":"蛇肖","number":[7,19,31,43],"latestPeriod":108,"continuousPeriodUnopened":10,"maxPeriodUnopened":{"period":99,"beginPeriod":"2016140","endPeriod":"2017087"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2015074","endPeriod":"2015076"}},{"sx":"马肖","number":[6,18,30,42],"latestPeriod":99,"continuousPeriodUnopened":19,"maxPeriodUnopened":{"period":63,"beginPeriod":"1977078","endPeriod":"1978038"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2008039","endPeriod":"2008041"}},{"sx":"羊肖","number":[5,17,29,41],"latestPeriod":110,"continuousPeriodUnopened":8,"maxPeriodUnopened":{"period":66,"beginPeriod":"1988092","endPeriod":"1989055"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2009002","endPeriod":"2009004"}},{"sx":"猴肖","number":[4,16,28,40],"latestPeriod":114,"continuousPeriodUnopened":4,"maxPeriodUnopened":{"period":112,"beginPeriod":"1983043","endPeriod":"1984051"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2015098","endPeriod":"2015100"}},{"sx":"鸡肖","number":[3,15,27,39],"latestPeriod":102,"continuousPeriodUnopened":16,"maxPeriodUnopened":{"period":70,"beginPeriod":"2016129","endPeriod":"2017047"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2017106","endPeriod":"2017108"}},{"sx":"狗肖","number":[2,14,26,38],"latestPeriod":107,"continuousPeriodUnopened":11,"maxPeriodUnopened":{"period":60,"beginPeriod":"1997073","endPeriod":"1998028"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"2018098","endPeriod":"2018100"}},{"sx":"猪肖","number":[1,13,25,37,49],"latestPeriod":117,"continuousPeriodUnopened":1,"maxPeriodUnopened":{"period":75,"beginPeriod":"2006081","endPeriod":"2007001"},"maxPeriodContinuousOpen":{"period":3,"beginPeriod":"1987050","endPeriod":"1987052"}}]
     */

    private String typeName;
    private List<LotteryDataBean> lotteryData;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<LotteryDataBean> getLotteryData() {
        return lotteryData;
    }

    public void setLotteryData(List<LotteryDataBean> lotteryData) {
        this.lotteryData = lotteryData;
    }

    public static class LotteryDataBean {
        /**
         * sx : 鼠肖
         * number : [12,24,36,48]
         * latestPeriod : 89
         * continuousPeriodUnopened : 29
         * maxPeriodUnopened : {"period":74,"beginPeriod":"1985050","endPeriod":"1986020"}
         * maxPeriodContinuousOpen : {"period":3,"beginPeriod":"2009110","endPeriod":"2009112"}
         */

        private String sx;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        private int year;
        private int latestPeriod;
        private int continuousPeriodUnopened;
        private MaxPeriodUnopenedBean maxPeriodUnopened;
        private MaxPeriodContinuousOpenBean maxPeriodContinuousOpen;
        private List<Integer> number;
        private List<String> sxList;

        public List<String> getSxList() {
            return sxList;
        }

        public void setSxList(List<String> sxList) {
            this.sxList = sxList;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }

        public int getLatestPeriod() {
            return latestPeriod;
        }

        public void setLatestPeriod(int latestPeriod) {
            this.latestPeriod = latestPeriod;
        }

        public int getContinuousPeriodUnopened() {
            return continuousPeriodUnopened;
        }

        public void setContinuousPeriodUnopened(int continuousPeriodUnopened) {
            this.continuousPeriodUnopened = continuousPeriodUnopened;
        }

        public MaxPeriodUnopenedBean getMaxPeriodUnopened() {
            return maxPeriodUnopened;
        }

        public void setMaxPeriodUnopened(MaxPeriodUnopenedBean maxPeriodUnopened) {
            this.maxPeriodUnopened = maxPeriodUnopened;
        }

        public MaxPeriodContinuousOpenBean getMaxPeriodContinuousOpen() {
            return maxPeriodContinuousOpen;
        }

        public void setMaxPeriodContinuousOpen(MaxPeriodContinuousOpenBean maxPeriodContinuousOpen) {
            this.maxPeriodContinuousOpen = maxPeriodContinuousOpen;
        }

        public List<Integer> getNumber() {
            return number;
        }

        public void setNumber(List<Integer> number) {
            this.number = number;
        }

        public static class MaxPeriodUnopenedBean {
            /**
             * period : 74
             * beginPeriod : 1985050
             * endPeriod : 1986020
             */

            private int period;
            private String beginPeriod;
            private String endPeriod;

            public int getPeriod() {
                return period;
            }

            public void setPeriod(int period) {
                this.period = period;
            }

            public String getBeginPeriod() {
                return beginPeriod;
            }

            public void setBeginPeriod(String beginPeriod) {
                this.beginPeriod = beginPeriod;
            }

            public String getEndPeriod() {
                return endPeriod;
            }

            public void setEndPeriod(String endPeriod) {
                this.endPeriod = endPeriod;
            }
        }

        public static class MaxPeriodContinuousOpenBean {
            /**
             * period : 3
             * beginPeriod : 2009110
             * endPeriod : 2009112
             */

            private int period;
            private String beginPeriod;
            private String endPeriod;

            public int getPeriod() {
                return period;
            }

            public void setPeriod(int period) {
                this.period = period;
            }

            public String getBeginPeriod() {
                return beginPeriod;
            }

            public void setBeginPeriod(String beginPeriod) {
                this.beginPeriod = beginPeriod;
            }

            public String getEndPeriod() {
                return endPeriod;
            }

            public void setEndPeriod(String endPeriod) {
                this.endPeriod = endPeriod;
            }
        }
    }
}
