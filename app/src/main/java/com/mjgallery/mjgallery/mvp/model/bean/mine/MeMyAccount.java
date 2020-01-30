package com.mjgallery.mjgallery.mvp.model.bean.mine;

/**
 * 钱包信息
 */
public class MeMyAccount {


    /**
     * moneyCount : null
     * userBalanceAmount : 0.00
     * settlement : 2
     * passWithdrawAmount : 0.00
     */
    /**总收益，小于空，大到不限*/
    private String moneyCount;
    /**余额*/
    private String userBalanceAmount;
    /**'结算方式(0、未设置 1、日结 2、月结)’*/
    private int settlement;
    /**提现金额*/
    private String passWithdrawAmount;

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getUserBalanceAmount() {
        return userBalanceAmount;
    }

    public void setUserBalanceAmount(String userBalanceAmount) {
        this.userBalanceAmount = userBalanceAmount;
    }

    public int getSettlement() {
        return settlement;
    }

    public void setSettlement(int settlement) {
        this.settlement = settlement;
    }

    public String getPassWithdrawAmount() {
        return passWithdrawAmount;
    }

    public void setPassWithdrawAmount(String passWithdrawAmount) {
        this.passWithdrawAmount = passWithdrawAmount;
    }
}
