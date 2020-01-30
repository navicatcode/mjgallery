package com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord;

public class SignRewardBean {

        /**
         * name : 0.5元
         * type : 1
         * desc : 平常签到奖励
         * number : 0.5
         * img : /file/761dc9aeba7342dd8f10f369a3038c54.jpg
         * accumulateDays : 0
         */

        private String name;
        private int type;
        private String desc;
        private double number;
        private String img;
        private int accumulateDays;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public double getNumber() {
            return number;
        }

        public void setNumber(double number) {
            this.number = number;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getAccumulateDays() {
            return accumulateDays;
        }

        public void setAccumulateDays(int accumulateDays) {
            this.accumulateDays = accumulateDays;
        }
}
