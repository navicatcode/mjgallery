package com.mjgallery.mjgallery.mvp.model.bean;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class AndroidGetAccountXBean extends BaseResponse<List<AndroidGetAccountXBean>> {


        private List<TotlaBean> totla;
        private List<AndroidlistBean> androidlist;

        public List<TotlaBean> getTotla() {
            return totla;
        }

        public void setTotla(List<TotlaBean> totla) {
            this.totla = totla;
        }

        public List<AndroidlistBean> getAndroidlist() {
            return androidlist;
        }

        public void setAndroidlist(List<AndroidlistBean> androidlist) {
            this.androidlist = androidlist;
        }

        public static class TotlaBean {
            /**
             * time : 201909
             * just : 0.00
             * back : -201.07
             */

            private String time;
            private String just;
            private String back;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getJust() {
                return just;
            }

            public void setJust(String just) {
                this.just = just;
            }

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }
        }

        public static class AndroidlistBean {
            /**
             * amount : -100.1
             * type : 2
             * time : 2019-09-12 22:03:14
             * date : 201909
             */

            private String amount;
            private int type;
            private String time;
            private String date;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
}
