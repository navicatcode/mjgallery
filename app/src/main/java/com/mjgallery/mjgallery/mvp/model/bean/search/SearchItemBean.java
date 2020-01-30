package com.mjgallery.mjgallery.mvp.model.bean.search;

import java.util.List;

public class SearchItemBean {

    /**
     * code : 0
     * message : 获取数据成功
     * result : [{"id":35119,"name":"(新版)四柱预测B","maxTerm":127},{"id":35244,"name":"中四柱预测B"},{"id":35248,"name":"中版四柱预测B"},{"id":35249,"name":"中版四柱预测B(原小版)"},{"id":35937,"name":"另版老四柱预测B"},{"id":36375,"name":"新版中版四柱预测B"},{"id":37132,"name":"老四柱预测B"}]
     * count : 0
     */

    private int code;
    private String message;
    private int count;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 35119
         * name : (新版)四柱预测B
         * maxTerm : 127
         */

        private int id;
        private String name;
        private int maxTerm;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxTerm() {
            return maxTerm;
        }

        public void setMaxTerm(int maxTerm) {
            this.maxTerm = maxTerm;
        }
    }
}
