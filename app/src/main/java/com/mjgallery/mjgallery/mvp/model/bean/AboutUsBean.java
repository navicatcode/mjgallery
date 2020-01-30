package com.mjgallery.mjgallery.mvp.model.bean;


import java.util.List;

public class AboutUsBean {

        /**
         * code : qq
         * value : 123456
         * name : qq
         * url : false
         */

        private String code;
        private String value;
        private String name;
        private boolean url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isUrl() {
            return url;
        }

        public void setUrl(boolean url) {
            this.url = url;
        }
}
