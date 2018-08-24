package com.svg.pay.utils.utils;


import android.text.TextUtils;
import android.widget.Toast;

import com.svg.pay.MyBaseApplication;
import com.svg.pay.bean.BasicNameValuePair;
import com.svg.pay.bean.NameValuePair;
import com.svg.pay.bean.OrederSendInfo;
import com.svg.pay.utils.LogUtil;
import com.svg.pay.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by xmg on 2018/8/22
 */

public class WXpayUtils {

    private static IWXAPI iwxapi;
    private static PayReq req;

    public static IWXAPI getWXAPI(){
        if (iwxapi == null){
            //通过WXAPIFactory创建IWAPI实例
            iwxapi = WXAPIFactory.createWXAPI(MyBaseApplication.getMyAppContext(), null);
            req = new PayReq();
            //将应用的appid注册到微信
            iwxapi.registerApp(Constants.APP_ID);
        }
        return iwxapi;
    }

    //生成随机字符串
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //获得时间戳
    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    //生成支付参数
    private static void genPayReq(OrederSendInfo orederSendInfo) {
        req.appId = TextUtils.isEmpty(orederSendInfo.getPayInfo().getAppid()) ?  Constants.APP_ID : orederSendInfo.getPayInfo().getAppid();// 微信开放平台审核通过的应用APPID
        req.partnerId = TextUtils.isEmpty(orederSendInfo.getPayInfo().getMch_id()) ? Constants.MCH_ID : orederSendInfo.getPayInfo().getMch_id();// 微信支付分配的商户号
        req.prepayId = orederSendInfo.getPrepay_id();// 预支付订单号，app服务器调用“统一下单”接口获取
       // payResult.nonceStr = "";// 随机字符串，不长于32位，服务器小哥会给咱生成
      //  req.timeStamp = "";// 时间戳，app服务器小哥给出
     //   req.packageValue ="";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
       // req.sign = orederSendInfo.getPayInfo().getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
        req.packageValue = "Sign=" + orederSendInfo.getPrepay_id();
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
    }

    //生成支付随机签名
    private static String genAppSign(List<NameValuePair>  params){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        //拼接密钥
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign.toUpperCase();
    }


    public static void Pay(OrederSendInfo orederSendInfo){
        if (judgeCanGo()){
            genPayReq(orederSendInfo);
            iwxapi.registerApp(Constants.APP_ID);
            iwxapi.sendReq(req);
        }
    }

    private static boolean judgeCanGo(){
        getWXAPI();
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(MyBaseApplication.getMyAppContext(), "请先安装微信应用", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!iwxapi.isWXAppSupportAPI()) {
            Toast.makeText(MyBaseApplication.getMyAppContext(), "请先更新微信应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //用于微信支付点击事件的处理
    private void webChatPay(){

        PayReq payResult = new PayReq();
        payResult.appId = Constants.APP_ID;// 微信开放平台审核通过的应用APPID
        try {
            payResult.partnerId =  "";// 微信支付分配的商户号
            payResult.prepayId = "";// 预支付订单号，app服务器调用“统一下单”接口获取
            payResult.nonceStr = "";// 随机字符串，不长于32位，服务器小哥会给咱生成
            payResult.timeStamp = "";// 时间戳，app服务器小哥给出
            payResult.packageValue ="";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
            payResult.sign = "";// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
        } catch (Exception e) {
            e.printStackTrace();
        }
        iwxapi.sendReq(payResult);
        LogUtil.d("发起微信支付申请");
    }
}
