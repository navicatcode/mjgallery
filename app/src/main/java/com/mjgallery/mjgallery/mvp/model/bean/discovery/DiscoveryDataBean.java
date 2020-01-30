package com.mjgallery.mjgallery.mvp.model.bean.discovery;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class DiscoveryDataBean extends BaseResponse<List<DiscoveryDataBean>> {


    /**
     * id : 1
     * name : 图纸大全
     * sort : 1
     * newInfo : true
     * hotInfo : true
     */

    private int id;
    private String name;
    private int sort;
    private boolean newInfo;
    private boolean hotInfo;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isNewInfo() {
        return newInfo;
    }

    public void setNewInfo(boolean newInfo) {
        this.newInfo = newInfo;
    }

    public boolean isHotInfo() {
        return hotInfo;
    }

    public void setHotInfo(boolean hotInfo) {
        this.hotInfo = hotInfo;
    }
}
