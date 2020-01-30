package com.mjgallery.mjgallery.mvp.model.bean.discovery;

public class DiscoverDetailsCommentsItemBean {
    private boolean isOpen = false;
    private int remainingMessages;
   DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean;

    public DiscoverDetailsCommentsItemBean() {
    }

    public DiscoverDetailsCommentsItemBean(boolean isOpen, int remainingMessages, DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean) {
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

    public DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean getSubCommentDetailDosBean() {
        return subCommentDetailDosBean;
    }

    public void setSubCommentDetailDosBean(DiscoveryCommentsBean.DataBean.SubCommentDetailDosBean subCommentDetailDosBean) {
        this.subCommentDetailDosBean = subCommentDetailDosBean;
    }
}
