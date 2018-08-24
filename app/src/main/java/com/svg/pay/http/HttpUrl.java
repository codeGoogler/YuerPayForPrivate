package com.svg.pay.http;


import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.svg.pay.MyBaseApplication;
import com.svg.pay.utils.LogUtil;
import com.svg.pay.utils.Vardepend;

import cz.msebera.android.httpclient.conn.ssl.AllowAllHostnameVerifier;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;


/**
* @类名 HttpUrl.java
* @描述 TODO【网络请求静态类】
* @Author 杨炳志（yang_bingzhi@qq.com）
* @Date 2015-12-3  下午3:45:53
* @Version V1.0.1
*/
public class HttpUrl {

    public static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

    static {
        client.setTimeout(60000); // 设置链接超时，如果不设置，默认为10s
        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
    }

    /**
     * get请求
     *
     * @param context（封装的方法建议都加上Context参数，以在Activity pause或stop时取消掉没用的请求。）
     * @param url
     * @param responseHandler
     */
    public static void get(Context context, String url, AsyncHttpResponseHandler responseHandler) {
//        NetConnectUtil netConnectUtil = new NetConnectUtil(context);
//        if (netConnectUtil.isNetworkAvaiable()){
        client.get(context, url, responseHandler);
//        }else{
//
//        }

    }

    /**
     * get请求
     *
     * @param context         （封装的方法建议都加上Context参数，以在Activity pause或stop时取消掉没用的请求。）
     * @param url
     * @param parame
     * @param responseHandler
     */
    public static void getForLog(Context context, String url, String parame, AsyncHttpResponseHandler responseHandler) {
//        NetConnectUtil netConnectUtil = new NetConnectUtil(context);
//        if (netConnectUtil.isNetworkAvaiable()){
        LogUtil.e(MyBaseApplication.TAG_HTTP, "========http'log=======");
        LogUtil.e(MyBaseApplication.TAG_HTTP, main + url + "-----" + parame);
        String base64 = Vardepend.base64encode(parame);
        LogUtil.e(MyBaseApplication.TAG_HTTP, "base64-----" + base64);
        client.get(context, main + url + base64, responseHandler);
//        }else {
//
//        }

    }


    /**
     * post请求
     *
     * @param context         （封装的方法建议都加上Context参数，以在Activity pause或stop时取消掉没用的请求。）
     * @param url
     * @param parame
     * @param responseHandler
     */
    public static void postForLogHeard(Context context, String url, RequestParams parame, String header, AsyncHttpResponseHandler responseHandler) {
        LogUtil.e(MyBaseApplication.TAG_HTTP, url + "-----" + parame + "---Authorization" + header);
        client.addHeader("Authorization", header);
        client.post(context, url, parame, responseHandler);

    }


    /**
     * get请求
     *
     * @param context         （封装的方法建议都加上Context参数，以在Activity pause或stop时取消掉没用的请求。）
     * @param url
     * @param parame
     * @param responseHandler
     */
    public static void getForLogHeard(Context context, String url, RequestParams parame, String header, AsyncHttpResponseHandler responseHandler) {
        LogUtil.e(MyBaseApplication.TAG_HTTP, url + "-----" + parame + "----" + header);
        client.addHeader("Authorization", header);
        client.get(context, url + "?" + parame.toString(), responseHandler);

    }
    /**
     * get请求
     *
     * @param context         （封装的方法建议都加上Context参数，以在Activity pause或stop时取消掉没用的请求。）
     * @param url
     * @param parame
     * @param responseHandler
     */
    public static void getForLogHeard(Context context, String url, RequestParams parame, AsyncHttpResponseHandler responseHandler) {
//        LogUtil.e(MyBaseApplication.TAG_HTTP, url + "-----" + parame + "----" + header);
//        client.addHeader("Authorization", header);
        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
        client.get(context, url + "?" + parame.toString(), responseHandler);

    }


    //public static final String main = "https://www.apiopen.top/";
    public static final String main = "http://app.shuiqian.cc/";

}

