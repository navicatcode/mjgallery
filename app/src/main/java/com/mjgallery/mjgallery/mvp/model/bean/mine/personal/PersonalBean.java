package com.mjgallery.mjgallery.mvp.model.bean.mine.personal;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 活动中心列表Bean
 */
public class PersonalBean extends BaseResponse<PersonalBean> implements Serializable {

    //新手任务
    private List<NoviceTaskBean> noviceTask;
    //日常任务
    private List<DailyTaskBean> dailyTask;

    public List<NoviceTaskBean> getNoviceTask() {
        return noviceTask;
    }

    public void setNoviceTask(List<NoviceTaskBean> noviceTask) {
        this.noviceTask = noviceTask;
    }

    public List<DailyTaskBean> getDailyTask() {
        return dailyTask;
    }

    public void setDailyTask(List<DailyTaskBean> dailyTask) {
        this.dailyTask = dailyTask;
    }
}
