package com.svg.pay.bean;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/8/21</br> 修改备注：</br>
 */
public class OrederSendInfo {
    /**
     * payInfo : {"appid":"wx202de326574683ef","mch_id":"1476716702","device_info":"WEB","nonce_str":"0obCyzNAtuPAu4BNbyaBjfmaIpGcIOED","sign":"48DECC7A47965EA4E11BC2ED250FD5B1","sign_type":"MD5","body":"爱尚康-充值","detail":null,"attach":null,"out_trade_no":"2017052413572600056270","fee_type":"CNY","total_fee":"10","spbill_create_ip":"114.112.124.161","time_start":null,"time_expire":null,"goods_tag":null,"notify_url":"http://test.hbank.nf1000.com/payment/weixinNotify.action","trade_type":"APP","limit_pay":null}
     * prepay_id : wx20170524135726b23bed59c70709053309
     */

    private PayInfoBean payInfo;
    private String prepay_id;

    public PayInfoBean getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfoBean payInfo) {
        this.payInfo = payInfo;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public static class PayInfoBean {
        /**
         * appid : wx202de326574683ef
         * mch_id : 1476716702
         * device_info : WEB
         * nonce_str : 0obCyzNAtuPAu4BNbyaBjfmaIpGcIOED
         * sign : 48DECC7A47965EA4E11BC2ED250FD5B1
         * sign_type : MD5
         * body : 爱尚康-充值
         * detail : null
         * attach : null
         * out_trade_no : 2017052413572600056270
         * fee_type : CNY
         * total_fee : 10
         * spbill_create_ip : 114.112.124.161
         * time_start : null
         * time_expire : null
         * goods_tag : null
         * notify_url : http://test.hbank.nf1000.com/payment/weixinNotify.action
         * trade_type : APP
         * limit_pay : null
         */

        private String appid;
        private String mch_id;
        private String device_info;
        private String nonce_str;
        private String sign;
        private String sign_type;
        private String body;
        private Object detail;
        private Object attach;
        private String out_trade_no;
        private String fee_type;
        private String total_fee;
        private String spbill_create_ip;
        private Object time_start;
        private Object time_expire;
        private Object goods_tag;
        private String notify_url;
        private String trade_type;
        private Object limit_pay;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getDevice_info() {
            return device_info;
        }

        public void setDevice_info(String device_info) {
            this.device_info = device_info;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Object getDetail() {
            return detail;
        }

        public void setDetail(Object detail) {
            this.detail = detail;
        }

        public Object getAttach() {
            return attach;
        }

        public void setAttach(Object attach) {
            this.attach = attach;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getFee_type() {
            return fee_type;
        }

        public void setFee_type(String fee_type) {
            this.fee_type = fee_type;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public Object getTime_start() {
            return time_start;
        }

        public void setTime_start(Object time_start) {
            this.time_start = time_start;
        }

        public Object getTime_expire() {
            return time_expire;
        }

        public void setTime_expire(Object time_expire) {
            this.time_expire = time_expire;
        }

        public Object getGoods_tag() {
            return goods_tag;
        }

        public void setGoods_tag(Object goods_tag) {
            this.goods_tag = goods_tag;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public Object getLimit_pay() {
            return limit_pay;
        }

        public void setLimit_pay(Object limit_pay) {
            this.limit_pay = limit_pay;
        }
    }


}
