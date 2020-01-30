package com.mjgallery.mjgallery.mvp.model.bean.mine;

public class MonthlyBillsBean<T> {

    private String amount;
    private int type;
    private String action;
    private int month;
    private int dateName;

    @Override
    public String toString() {
        return "MonthlyBillsBean{" +
                "amount='" + amount + '\'' +
                ", type=" + type +
                ", action='" + action + '\'' +
                ", month=" + month +
                ", dateName=" + dateName +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDateName() {
        return dateName;
    }

    public void setDateName(int dateName) {
        this.dateName = dateName;
    }
}
