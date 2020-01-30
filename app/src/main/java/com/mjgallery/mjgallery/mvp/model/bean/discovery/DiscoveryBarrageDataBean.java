package com.mjgallery.mjgallery.mvp.model.bean.discovery;

import com.orient.tea.barragephoto.model.DataSource;

public class DiscoveryBarrageDataBean implements DataSource {


    private DiscoveryDetailBean.DataBean.CommentDetailDosBean commentDetailDosBean;


    public DiscoveryBarrageDataBean(DiscoveryDetailBean.DataBean.CommentDetailDosBean commentDetailDosBean) {
        this.commentDetailDosBean = commentDetailDosBean;
    }

    public DiscoveryBarrageDataBean() {
    }

    public DiscoveryDetailBean.DataBean.CommentDetailDosBean getCommentDetailDosBean() {
        return commentDetailDosBean;
    }

    public void setCommentDetailDosBean(DiscoveryDetailBean.DataBean.CommentDetailDosBean commentDetailDosBean) {
        this.commentDetailDosBean = commentDetailDosBean;
    }

    @Override
    public int getType() {
        return 0;
    }
}
