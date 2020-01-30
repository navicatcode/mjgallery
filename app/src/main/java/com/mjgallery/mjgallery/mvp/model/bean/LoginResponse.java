package com.mjgallery.mjgallery.mvp.model.bean;

/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link LoginResponse}, 如果你实在看不懂, 请忽略
 * <p>
 * Created by JessYan on 26/09/2016 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class LoginResponse<T> {


    /**
     * code : 0
     * message : 登录成功
     * result : {"userId":251,"token":"bTS6cvbnKaXIFvpH4/UCf1wWPQZkJq7H2mWD/FH2qtVoqwd5ssRkRC6cck4qUWFfV/DF5YHkWpU3t3syaGLGYMIfIQfyHccngVIcR9NQfUWHiD2ydEtwKdvmdi/Wft10sqxVxXYnl+M0INc45snK8+56SFsDeA/3ooVspBxgJB0="}
     * count : 0
     */

    private int code;
    private String message;
    private ResultBean result;
    private int count;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class ResultBean {
        /**
         * userId : 251
         * token : bTS6cvbnKaXIFvpH4/UCf1wWPQZkJq7H2mWD/FH2qtVoqwd5ssRkRC6cck4qUWFfV/DF5YHkWpU3t3syaGLGYMIfIQfyHccngVIcR9NQfUWHiD2ydEtwKdvmdi/Wft10sqxVxXYnl+M0INc45snK8+56SFsDeA/3ooVspBxgJB0=
         */

        private String userId;
        private String token;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
