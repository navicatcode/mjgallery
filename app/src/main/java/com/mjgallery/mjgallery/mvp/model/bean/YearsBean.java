package com.mjgallery.mjgallery.mvp.model.bean;

public class YearsBean <T>{

    @Override
    public String toString() {
        return "YearsBean{" +
                "year=" + year +
                ", isSelect=" + isSelect +
                '}';
    }

    private String year;

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

    private boolean isSelect=false;
}
