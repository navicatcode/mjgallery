package com.mjgallery.mjgallery.event;

public class SavePhoneEvent {
    String phoneNuber;

    public SavePhoneEvent(String phoneNuber) {
        this.phoneNuber = phoneNuber;
    }

    public String getPhoneNuber() {
        return phoneNuber;
    }

    public void setPhoneNuber(String phoneNuber) {
        this.phoneNuber = phoneNuber;
    }
}
