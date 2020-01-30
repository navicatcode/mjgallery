package com.mjgallery.mjgallery.app.utils;

/**
 * 活动中心领取任务等的接口
 */
public interface PersonalGetTask {

    public void getTaskOnClick(int rewardId, String rewardName, boolean listType, int index);

    public void getShowDialog(String rewardName);
}
