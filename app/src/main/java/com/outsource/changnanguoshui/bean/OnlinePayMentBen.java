package com.outsource.changnanguoshui.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/17.
 */

public class OnlinePayMentBen {
    /**
     * status : 1
     * msg :
     * syear : 2017
     * count_price : 35.0
     * list : [{"pay_month":"2017-12","amount":35}]
     */

    private int status;
    private String msg;
    private int syear;
    private double count_price;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSyear() {
        return syear;
    }

    public void setSyear(int syear) {
        this.syear = syear;
    }

    public double getCount_price() {
        return count_price;
    }

    public void setCount_price(double count_price) {
        this.count_price = count_price;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * pay_month : 2017-12
         * amount : 35.0
         */

        private String pay_month;
        private double amount;
        private boolean checkBox=false;
        public String getPay_month() {
            return pay_month;
        }

        public void setPay_month(String pay_month) {
            this.pay_month = pay_month;
        }

        public boolean isCheckBox() {
            return checkBox;
        }

        public void setCheckBox(boolean checkBox) {
            this.checkBox = checkBox;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
