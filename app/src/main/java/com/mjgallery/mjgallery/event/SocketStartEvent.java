package com.mjgallery.mjgallery.event;

public class SocketStartEvent {

    int status=0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SocketStartEvent(int status) {
        this.status = status;
    }
}
