package com.mjgallery.mjgallery.mvp.model.bean.search;

public class SearchUserBean {

    /**
     * id : 615
     * username : 18320891523
     * nikeName : 胖子
     * headImg : http://thirdwx.qlogo.cn/mmopen/vi_32/SUJcTwXiclja2JPAz661WCRZicjS1DYLJlkNLbIjh2WLICvkZfG4ichricBtMLtBpJYjS2WRT0KD8oQgiclib49mceLw/132
     * concernByUserCount : 0
     * concernUserCount : 0
     * concern : false
     */

    private int id;
    private String username;
    private String nikeName;
    private String headImg;
    private int concernByUserCount;
    private int concernUserCount;
    private boolean concern;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getConcernByUserCount() {
        return concernByUserCount;
    }

    public void setConcernByUserCount(int concernByUserCount) {
        this.concernByUserCount = concernByUserCount;
    }

    public int getConcernUserCount() {
        return concernUserCount;
    }

    public void setConcernUserCount(int concernUserCount) {
        this.concernUserCount = concernUserCount;
    }

    public boolean isConcern() {
        return concern;
    }

    public void setConcern(boolean concern) {
        this.concern = concern;
    }
}
