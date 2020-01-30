package com.mjgallery.mjgallery.mvp.model.bean.home.homedetailscomments;

import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;

public class HomeDetailsCommentsItemBean {
    private boolean isOpen = false;
    private int remainingMessages;
    HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean;

    public HomeDetailsCommentsItemBean() {
    }

    public HomeDetailsCommentsItemBean(boolean isOpen, int remainingMessages, HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean) {
        this.isOpen = isOpen;
        this.remainingMessages = remainingMessages;
        this.subCommentDetailDosBean = subCommentDetailDosBean;
    }

    @Override
    public String toString() {
        return "HomeDetailsCommentsItemBean{" +
                "isOpen=" + isOpen +
                ", remainingMessages=" + remainingMessages +
                ", subCommentDetailDosBean=" + subCommentDetailDosBean +
                '}';
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getRemainingMessages() {
        return remainingMessages;
    }

    public void setRemainingMessages(int remainingMessages) {
        this.remainingMessages = remainingMessages;
    }

    public HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean getSubCommentDetailDosBean() {
        return subCommentDetailDosBean;
    }

    public void setSubCommentDetailDosBean(HomeDetailsCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean) {
        this.subCommentDetailDosBean = subCommentDetailDosBean;
    }
}
