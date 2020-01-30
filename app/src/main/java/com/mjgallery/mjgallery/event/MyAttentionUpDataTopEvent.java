package com.mjgallery.mjgallery.event;

public class MyAttentionUpDataTopEvent {

    int index = 0;
    int count;

    public MyAttentionUpDataTopEvent(int index, int count) {
        this.index = index;
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
