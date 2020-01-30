package com.mjgallery.mjgallery.event;

public class UpDateEvent {

    public boolean isLoginBool=false;
    public UpDateEvent() {
    }

    public UpDateEvent(boolean isLoginBool){
        this.isLoginBool=isLoginBool;
    }

    public boolean isLoginBool() {
        return isLoginBool;
    }

    public void setLoginBool(boolean login) {
        isLoginBool = login;
    }
}
