package com.mjgallery.mjgallery.mvp.model.bean.discovery;

public class DiscoveryTitleBean {
    private String discoveryTitle;
    private int type;
    private boolean placedTop;






    public String getDiscoveryTitle() {
        return discoveryTitle;
    }

    public void setDiscoveryTitle(String discoveryTitle) {
        this.discoveryTitle = discoveryTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPlacedTop() {
        return placedTop;
    }

    public void setPlacedTop(boolean placedTop) {
        this.placedTop = placedTop;
    }

    @Override
    public String toString() {
        return "DiscoveryTitleBean{" +
                "discoveryTitle='" + discoveryTitle + '\'' +
                ", type=" + type +
                ", placedTop=" + placedTop +
                '}';
    }

    public DiscoveryTitleBean() {
    }

    public DiscoveryTitleBean(String discoveryTitle, int type, boolean placedTop) {
        this.discoveryTitle = discoveryTitle;
        this.type = type;
        this.placedTop = placedTop;
    }
}
