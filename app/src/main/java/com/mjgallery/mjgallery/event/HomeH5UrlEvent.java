package com.mjgallery.mjgallery.event;

public class HomeH5UrlEvent {
    private int position;

    public HomeH5UrlEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
