package com.mjgallery.mjgallery.mvp.model.bean;

public class PaymentDetailsBean<T> {
    private String amount;
    private int type;
    private String time;
    private String date;

    public PaymentDetailsBean() {
    }

    @Override
    public String toString() {
        return "PaymentDetailsBean{" +
                "amount='" + amount + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public PaymentDetailsBean(String amount, int type, String time, String date) {
        this.amount = amount;
        this.type = type;
        this.time = time;
        this.date = date;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
