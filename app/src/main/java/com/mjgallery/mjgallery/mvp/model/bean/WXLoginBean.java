package com.mjgallery.mjgallery.mvp.model.bean;

public class WXLoginBean {
    /**
     * mobile : 手机号码
     * nickname : 昵称
     * password : 密码
     * token : 认证token
     */

    private String mobile;
    private String nickname;
    private String password;
    private String token;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
