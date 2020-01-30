package com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord;

import java.util.List;

public class SignRecordBean {


        /**
         * month : 10
         * year : 2019
         * array : [{"isSpecial":2,"isSign":1},{"isSpecial":1,"isSign":1},{"isSpecial":1,"isSign":1},{"isSpecial":1,"isSign":1},{"isSpecial":1,"isSign":1},{"isSpecial":1,"isSign":1},{"isSpecial":2,"isSign":1},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":2,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":2,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":2,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":1},{"isSpecial":2,"isSign":0},{"isSpecial":1,"isSign":0},{"isSpecial":1,"isSign":0}]
         */

        private String month;
        private String year;
        private List<ArrayBean> array;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<ArrayBean> getArray() {
            return array;
        }

        public void setArray(List<ArrayBean> array) {
            this.array = array;
        }

        public static class ArrayBean {
            /**
             * isSpecial : 2
             * isSign : 1
             */

            private int isSpecial;
            private int isSign;

            public int getIsSpecial() {
                return isSpecial;
            }

            public void setIsSpecial(int isSpecial) {
                this.isSpecial = isSpecial;
            }

            public int getIsSign() {
                return isSign;
            }

            public void setIsSign(int isSign) {
                this.isSign = isSign;
            }
        }
}
