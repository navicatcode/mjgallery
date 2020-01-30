package com.mjgallery.mjgallery.mvp.model.bean.dialog;

public class InvitedPlayersDialogBean {
    private String invitedPlayersStatus;
    private int statusType;
    boolean isSelect = false;

    public InvitedPlayersDialogBean() {
    }


    @Override
    public String toString() {
        return "InvitedPlayersDialogBean{" +
                "invitedPlayersStatus='" + invitedPlayersStatus + '\'' +
                ", statusType='" + statusType + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    public InvitedPlayersDialogBean(String invitedPlayersStatus, int statusType, boolean isSelect) {
        this.invitedPlayersStatus = invitedPlayersStatus;
        this.statusType = statusType;
        this.isSelect = isSelect;
    }

    public String getInvitedPlayersStatus() {
        return invitedPlayersStatus;
    }

    public void setInvitedPlayersStatus(String invitedPlayersStatus) {
        this.invitedPlayersStatus = invitedPlayersStatus;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
