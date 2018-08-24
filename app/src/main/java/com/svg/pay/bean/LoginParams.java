package com.svg.pay.bean;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/8/21</br> 修改备注：</br>
 */
public class LoginParams {
    public LoginParams(String key, String phone, String passwd) {
        this.key = key;
        this.phone = phone;
        this.passwd = passwd;
    }

    private String key;
    private String phone;
    private String passwd;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
