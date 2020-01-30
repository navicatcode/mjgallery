package com.mjgallery.mjgallery.mvp.model.bean.mine;

import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;

public class MyAccountUserBean extends BaseResponse<MyAccountUserBean> {


    /**
     * userBalanceAmount : 499498.84
     * passWithdrawAmount : 501.16
     * settlement : 1
     * amount : 0
     * time : 2019-09-12 22:06:15
     * list : [{"amount":-100.1,"type":2,"time":"2019-09-12 22:01:07.0"},{"amount":-100.97,"type":2,"time":"2019-09-12 22:02:28.0"}]
     */

    private String userBalanceAmount;
    private String passWithdrawAmount;
    private int settlement;
    private int amount;
    private String time;
    private List<ListBean> list;

    public String getUserBalanceAmount() {
        return userBalanceAmount;
    }

    public void setUserBalanceAmount(String userBalanceAmount) {
        this.userBalanceAmount = userBalanceAmount;
    }

    public String getPassWithdrawAmount() {
        return passWithdrawAmount;
    }

    public void setPassWithdrawAmount(String passWithdrawAmount) {
        this.passWithdrawAmount = passWithdrawAmount;
    }

    public int getSettlement() {
        return settlement;
    }

    public void setSettlement(int settlement) {
        this.settlement = settlement;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : -100.1
         * type : 2
         * time : 2019-09-12 22:01:07.0
         */

        private double amount;
        private int type;
        private String time;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
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
        }
}
