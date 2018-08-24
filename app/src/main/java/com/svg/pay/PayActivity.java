//package com.svg.pay;
//
///**
// * 类功能描述：</br>
// *
// * @author 于亚豪
// * @version 1.0 </p> 修改时间：2018/8/21</br> 修改备注：</br>
// */
//
//import java.io.StringReader;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//import java.util.HashMap;
//
//import java.util.List;
//import java.util.Map;
//
//
//
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Xml;
//import android.view.View;
//import android.widget.Button;
//
//import com.svg.pay.bean.NameValuePair;
//import com.svg.pay.utils.GetToast;
//import com.svg.pay.utils.utils.MD5;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//public class PayActivity extends Activity {
//    PayReq req;
//    private IWXAPI msgApi;
//    Map<String, String> resultunifiedorder;
//    StringBuffer sb;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay);
//        req = new PayReq();
//        sb = new StringBuffer();
//        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
//        /**
//         * 将app注册到微信
//         */
//        boolean registerApp = msgApi.registerApp(Constants.APP_ID);
//       GetToast.useString(this, "注册========" + registerApp + "");
//        Button appayBtn = (Button) findViewById(R.id.appay_btn);
//        Button check_pay_btn = (Button) findViewById(R.id.check_pay_btn);
//        appayBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//                getPrepayId.execute();
//            }
//        });
//        /**
//         * 将该app注册到微信
//         */
//        check_pay_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                genPayReq();
//                sendPayReq();
//
//            }
//        });
////      // 生成签名参数
////      Button appay_pre_btn = (Button) findViewById(R.id.appay_pre_btn);
////      appay_pre_btn.setOnClickListener(new View.OnClickListener() {
////
////          @Override
////          public void onClick(View v) {
////              genPayReq();
////          }
////      });
//    }
//
//    /**
//     * 生成签名
//     */
//
//    private String genPackageSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//        Log.e("拼接=====", sb.toString());
//        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion生成签名===", packageSign);
//        return packageSign;
//    }
//
//    /**
//     * 签名工具 不含商户密钥 －暂时不用 = * 编码格式 UTF-8 = * @return
//     */
//    public static String createSignNoKey(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        String signStr = sb.toString();
//        String subStr = signStr.substring(0, signStr.length() - 1);
//        // 注意sign转为大写
//        return MD5.getMessageDigest(subStr.getBytes()).toUpperCase();
//    }
//
//    private String genAppSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.KEY);
//
//        this.sb.append("sign str\n" + sb.toString() + "\n\n");
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
//        Log.e("orion", appSign);
//        return appSign;
//    }
//
//    private String toXml(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<xml>");
//        for (int i = 0; i < params.size(); i++) {
//            sb.append("<" + params.get(i).getName() + ">");
//
//            sb.append(params.get(i).getValue());
//            sb.append("</" + params.get(i).getName() + ">");
//        }
//        sb.append("</xml>");
//
//        Log.e("orion", sb.toString());
//        return sb.toString();
//    }
//
//    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
//
//        private ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip),
//                    getString(R.string.getting_prepayid));
//        }
//
//        @Override
//        protected void onPostExecute(Map<String, String> result) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//
//            resultunifiedorder = result;
//
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Map<String, String> doInBackground(Void... params) {
//
//            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            String entity = genProductArgs();
//            Log.e("orion===发送过去", entity);
//            byte[] buf = Util.httpPost(url, entity);
//
//            String content = new String(buf);
//            Log.e("orion", content);
//            Map<String, String> xml = decodeXml(content);
//
//            return xml;
//        }
//    }
//
//    public Map<String, String> decodeXml(String content) {
//
//        try {
//            Map<String, String> xml = new HashMap<String, String>();
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(new StringReader(content));
//            int event = parser.getEventType();
//            while (event != XmlPullParser.END_DOCUMENT) {
//
//                String nodeName = parser.getName();
//                switch (event) {
//                    case XmlPullParser.START_DOCUMENT:
//
//                        break;
//                    case XmlPullParser.START_TAG:
//
//                        if ("xml".equals(nodeName) == false) {
//                            // 实例化student对象
//                            xml.put(nodeName, parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//                event = parser.next();
//            }
//
//            return xml;
//        } catch (Exception e) {
//            Log.e("orion", e.toString());
//        }
//        return null;
//
//    }
//
//    /**
//     * 生成随机数
//     *
//     * @return
//     */
//    private String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    /**
//     * 时间戳
//     *
//     * @return
//     */
//    private long genTimeStamp() {
//        return System.currentTimeMillis() / 1000;
//    }
//
//    /**
//     * 随机数
//     *
//     * @return
//     */
//    private String genOutTradNo() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    /**
//     * 获取设备的ip地址
//     *
//     * @return
//     */
//    public String getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        return inetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//        }
//        return null;
//    }
//
//    private String getWifiIp() {
//        // 获取wifi服务
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        // 判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        String ip = intToIp(ipAddress);
//        return ip;
//    }
//
//    /**
//     * 转化成Ip地址的格式
//     *
//     * @param i
//     * @return
//     */
//    private String intToIp(int i) {
//
//        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
//    }
//
//    private String genProductArgs() {
//        StringBuffer xml = new StringBuffer();
//        String ip = getWifiIp();
//        if (ip == "" && ip == "") {
//            ip = getLocalIpAddress();
//        }
//        try {
//            String nonceStr = genNonceStr();
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
//            packageParams.add(new BasicNameValuePair("body", "APPtest"));
//            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", ConfigUtil.NOTIFY_URL));
//            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
//            packageParams.add(new BasicNameValuePair("spbill_create_ip", ip));
//            packageParams.add(new BasicNameValuePair("total_fee", "100"));
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//            String sign = genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign", sign));
//            String xmlstring = toXml(packageParams);
//            return xmlstring;
//        } catch (Exception e) {
//            Log.e("TAG", "fail, ex = " + e.getMessage());
//            return null;
//        }
//    }
//
//    private void genPayReq() {
//        req.appId = Constants.APP_ID;
//        req.partnerId = Constants.MCH_ID;
//        req.prepayId = resultunifiedorder.get("prepay_id");
//        req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
//        req.nonceStr = genNonceStr();
//        req.timeStamp = String.valueOf(genTimeStamp());
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//        req.sign = genAppSign(signParams);
//        sb.append("sign\n" + req.sign + "\n\n");
//        Log.e("orion==genPayReq===============", signParams.toString());
//
//    }
//
//    private void sendPayReq() {
//        boolean sendReq = msgApi.sendReq(req);
//        T.show(this, "微信跳转=====" + sendReq + "");
//    }
//
//}