package com.mjgallery.mjgallery.mvp.model.bean.mine;

public class MineMenuBean <T>{
    private int type;
    private int img;
    private String name;

    public MineMenuBean(int type, int img, String name) {
        this.type = type;
        this.img = img;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MineMenuBean{" +
                "type=" + type +
                ", img=" + img +
                ", name='" + name + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
