package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.app.BaseResponse;

public class MyAttentionnNumberBean extends BaseResponse<MyAttentionnNumberBean> {


    /**
     * myConcernCount : 6
     * myFansCount : 3
     */

    private int myConcernCount;
    private int myFansCount;

    public int getMyConcernCount() {
        return myConcernCount;
    }

    public void setMyConcernCount(int myConcernCount) {
        this.myConcernCount = myConcernCount;
    }

    public int getMyFansCount() {
        return myFansCount;
    }

    public void setMyFansCount(int myFansCount) {
        this.myFansCount = myFansCount;
    }
}
