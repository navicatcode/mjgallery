package com.mjgallery.mjgallery.opendanmaku;

import android.graphics.Canvas;

public interface IDanmakuItem {

    void doDraw(Canvas canvas);

    void setTextSize(int sizeInDip);

    void setTextColor(int colorResId);

    void setStartPosition(int x, int y);

    float getSpeedFactor();

    void setSpeedFactor(float factor);

    boolean isOut();

    boolean willHit(IDanmakuItem runningItem);

    void release();

    int getWidth();

    int getHeight();

    int getCurrX();

    int getCurrY();
}
