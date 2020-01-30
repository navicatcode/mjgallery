package com.mjgallery.mjgallery.mvp.model.bean;

public class MuLuBean<T> {
    private String title;
    private String year;
    private boolean isSelect;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MuLuBean() {
    }

    @Override
    public String toString() {
        return "MuLuBean{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", isSelect=" + isSelect +
                ", type=" + type +
                '}';
    }

    public MuLuBean(String title, String year, boolean isSelect, int type) {
        this.title = title;
        this.year = year;
        this.isSelect = isSelect;
        this.type = type;
    }
}
