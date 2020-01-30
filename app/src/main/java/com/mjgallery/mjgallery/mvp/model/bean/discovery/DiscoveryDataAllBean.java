package com.mjgallery.mjgallery.mvp.model.bean.discovery;

public class DiscoveryDataAllBean<T> {
    private String letter;
    private DiscoveryDataBean discoveryDataBean;

    public DiscoveryDataAllBean() {
    }

    public DiscoveryDataAllBean(String letter, DiscoveryDataBean discoveryDataBean) {
        this.letter = letter;
        this.discoveryDataBean = discoveryDataBean;
    }

    @Override
    public String toString() {
        return "DiscoveryDataAllBean{" +
                "letter='" + letter + '\'' +
                ", discoveryDataBean=" + discoveryDataBean +
                '}';
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public DiscoveryDataBean getDiscoveryDataBean() {
        return discoveryDataBean;
    }

    public void setDiscoveryDataBean(DiscoveryDataBean discoveryDataBean) {
        this.discoveryDataBean = discoveryDataBean;
    }

}
