package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.mvp.model.bean.PaymentDetailsBean;

import java.util.List;

public class MyAccountBean<T> {
    private String yearsMonth;// 年月份
    private String TotalSpending;  //总支出
    private String TotalRevenue;   //总收入
    List<PaymentDetailsBean> paymentDetailsBeanList;

    public MyAccountBean() {
    }

    public MyAccountBean(String yearsMonth, String totalSpending, String totalRevenue, List<PaymentDetailsBean> paymentDetailsBeanList) {
        this.yearsMonth = yearsMonth;
        TotalSpending = totalSpending;
        TotalRevenue = totalRevenue;
        this.paymentDetailsBeanList = paymentDetailsBeanList;
    }

    public String getYearsMonth() {
        return yearsMonth;
    }

    public void setYearsMonth(String yearsMonth) {
        this.yearsMonth = yearsMonth;
    }

    public String getTotalSpending() {
        return TotalSpending;
    }

    public void setTotalSpending(String totalSpending) {
        TotalSpending = totalSpending;
    }

    public String getTotalRevenue() {
        return TotalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        TotalRevenue = totalRevenue;
    }

    public List<PaymentDetailsBean> getPaymentDetailsBeanList() {
        return paymentDetailsBeanList;
    }

    public void setPaymentDetailsBeanList(List<PaymentDetailsBean> paymentDetailsBeanList) {
        this.paymentDetailsBeanList = paymentDetailsBeanList;
    }

    @Override
    public String toString() {
        return "MyAccountBean{" +
                "yearsMonth='" + yearsMonth + '\'' +
                ", TotalSpending='" + TotalSpending + '\'' +
                ", TotalRevenue='" + TotalRevenue + '\'' +
                ", paymentDetailsBeanList=" + paymentDetailsBeanList +
                '}';
    }
}
