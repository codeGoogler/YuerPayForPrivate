//package com.svg.pay.http;
//
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * 类功能描述：</br>
// *
// * @author 于亚豪
// * @version 1.0 </p> 修改时间：2018/8/21</br> 修改备注：</br>
// */
//public class Request {
//    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
//
//
//    /** @deprecated */
//    @Deprecated
//    protected String getPostParamsEncoding() {
//        return this.getParamsEncoding();
//    }
//
//    /** @deprecated */
//    @Deprecated
//    public String getPostBodyContentType() {
//        return this.getBodyContentType();
//    }
//
//
//
//    protected String getParamsEncoding() {
//        return "UTF-8";
//    }
//
//    public String getBodyContentType() {
//        return "application/x-www-form-urlencoded; charset=" + this.getParamsEncoding();
//    }
//
//
//    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
//        StringBuilder encodedParams = new StringBuilder();
//
//        try {
//            Iterator i$ = params.entrySet().iterator();
//
//            while(i$.hasNext()) {
//                java.util.Map.Entry<String, String> entry = (java.util.Map.Entry)i$.next();
//                encodedParams.append(URLEncoder.encode((String)entry.getKey(), paramsEncoding));
//                encodedParams.append('=');
//                encodedParams.append(URLEncoder.encode((String)entry.getValue(), paramsEncoding));
//                encodedParams.append('&');
//            }
//
//            return encodedParams.toString().getBytes(paramsEncoding);
//        } catch (UnsupportedEncodingException var6) {
//            throw new RuntimeException("Encoding not supported: " + paramsEncoding, var6);
//        }
//    }
//
//
//
//    public static enum Priority {
//        LOW,
//        NORMAL,
//        HIGH,
//        IMMEDIATE;
//
//        private Priority() {
//        }
//    }
//
//    public interface Method {
//        int DEPRECATED_GET_OR_POST = -1;
//        int GET = 0;
//        int POST = 1;
//        int PUT = 2;
//        int DELETE = 3;
//        int HEAD = 4;
//        int OPTIONS = 5;
//        int TRACE = 6;
//        int PATCH = 7;
//    }
//}
