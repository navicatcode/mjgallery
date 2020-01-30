package com.mjgallery.mjgallery.event;

public class SearchContentEvent {
    String content;

    public SearchContentEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
