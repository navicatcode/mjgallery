package com.mjgallery.mjgallery.event;

import com.tencent.mm.opensdk.modelbase.BaseResp;

public class WXEntryEvent {
    private BaseResp baseResp;

    public WXEntryEvent(BaseResp baseResp) {
        this.baseResp = baseResp;
    }

    public BaseResp getBaseResp() {
        return baseResp;
    }

    public void setBaseResp(BaseResp baseResp) {
        this.baseResp = baseResp;
    }
}
