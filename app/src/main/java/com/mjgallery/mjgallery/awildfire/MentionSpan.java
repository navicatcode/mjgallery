package com.mjgallery.mjgallery.awildfire;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

@SuppressWarnings("all")
public class MentionSpan extends ClickableSpan {
    private boolean isMentionAll;
    private String uid;

    public MentionSpan(String uid) {
        this.uid = uid;
    }

    public MentionSpan(boolean isMentionAll) {
        this.isMentionAll = isMentionAll;
    }

    public boolean isMentionAll() {
        return isMentionAll;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        // do nothing
    }

    @Override
    public void onClick(@NonNull View widget) {
        // do nothing
    }
}

