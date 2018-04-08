package com.outsource.changnanguoshui.bean;

/**
 * Created by Administrator on 2018/4/8.
 */

public class AddPayOrderBean {

    /**
     * status : 1
     * msg : 成功返回！
     * payment_id : 2
     * price : 0.01
     * pay_no : M18040821522937
     * appid : wx20d82208bc65f133
     * partnerid : 1501199541
     * prepayid : wx08215049680176ce57813df70497223932
     * noncestr : 35db629104c04bf389beb9cc5b910112
     * timestamp : 1523195550
     * _package : Sign=WXPay
     * sign : 56D3AC39A198313F92FC96C7A070B630
     */

    private int status;
    private String msg;
    private String payment_id;
    private String price;
    private String pay_no;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String _package;
    private String sign;

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

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String get_package() {
        return _package;
    }

    public void set_package(String _package) {
        this._package = _package;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
