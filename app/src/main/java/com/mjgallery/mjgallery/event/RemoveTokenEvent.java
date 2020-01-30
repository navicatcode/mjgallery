package com.mjgallery.mjgallery.event;

public class RemoveTokenEvent {
    private int code;

    public RemoveTokenEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
