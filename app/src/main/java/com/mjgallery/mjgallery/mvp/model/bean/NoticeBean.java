package com.mjgallery.mjgallery.mvp.model.bean;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class NoticeBean extends BaseResponse<List<NoticeBean>> {

    /**
     * title : 老中医第39集预告
     * content : 测试2被治愈的十名日本军人在大夫们面前炫耀他们杀害中国士兵的功迹，大家敢怒不敢言。翁泉海告诉赵闵堂给小鬼子制药的真相，让赵闵堂走地道出去报信，赵腿抖不敢去。翁泉海让小朴去给远征军报信。在放人前，浦田提出要处理私改药方的人，一辈子胆小的赵闵堂勇敢站了出来，说要死出个响动来。药被中国军方中途调包，中国远征军的病被治好，日本军方震怒。葆秀突然回家了，翁泉海口不对心的一通骂，关上门暗自掉泪。
     */

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
