package com.svg.pay.http;



import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.svg.pay.utils.LogUtil;

import cz.msebera.android.httpclient.Header;


/**
 * 类功能描述：请求封装</br>
 * @author yuyahao
 * @version 1.0 </p> 修改时间：2018/4/9. </br> 修改备注：</br>
 */
public class HttpRequestProvider {
    private static final String TAG = HttpRequestProvider.class.getSimpleName();
    private HttpActionHandle actionHandle;
    private String actionName;

    public HttpRequestProvider(HttpActionHandle actionHandle, String actionName) {
        this.actionHandle = actionHandle;
        this.actionName = actionName;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
//    public void cancelRequest(String tag) {
//        LogUtil.i(TAG, "----cancelRequest----" + tag + actionHandle.hashCode());
//        MyApplication.getRequestQueue().cancelAll(actionHandle.hashCode() + tag);
//    }

    /**
     * 取消所有请求
     */
//    public void cancelAllRequest() {
//        LogUtil.i(TAG, "----cancleAllRequest----");
//        MyApplication.getRequestQueue().cancelAll(new RequestFilter() {
//            @Override
//            public boolean apply(Request<?> request) {
//                return (int) request.getTag() == actionHandle.hashCode();
//            }
//        });
//    }

    /**
     * base请求
     * @param url
     * @param params
     * @param dataParse
     */
    public void requestPost(String url, RequestParams params, final DataParse dataParse) {
        //添加全局的token --->添加固定请求参数
       // HttpUrl.client.addHeader("Authorization", TextUtils.isEmpty( Vardepend.getUserModel().getToken()) ? "" : Vardepend.getUserModel().getToken());
        actionHandle.handleActionStart(actionName, null);
        HttpUrl.client.post(url, params, new MyAsyncHttpResponseHandler() {
            @Override
            public void onMySuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String response = new String(bytes);
                    if (response != null) {
//                        if (JsonUtils.isSuccess(response)) {
                        try {
                            dataParse.onParse(response);
                        } catch (Exception e) {
                            actionHandle.handleActionError(actionName, "请求出问题啦~555");
                            e.printStackTrace();
                            LogUtil.exception(e);
                        }
//                        } else {
//                            actionHandle.handleActionError(actionName, response);
//                        }
                    }
                    actionHandle.handleActionFinish(actionName, null);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    actionHandle.handleActionError(actionName, null);
                }
                actionHandle.handleActionFinish(actionName, null);
            }
            @Override
            public void onMyFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                actionHandle.handleActionError(actionName, null);
                actionHandle.handleActionFinish(actionName, null);
            }
        });
    }
    /**
     * base请求
     * @param url
     * @param params
     * @param dataParse
     */
    public void requestGet( String url, RequestParams params, final DataParse dataParse) {
        //添加全局的token --->添加固定请求参数
        // HttpUrl.client.addHeader("Authorization", TextUtils.isEmpty( Vardepend.getUserModel().getToken()) ? "" : Vardepend.getUserModel().getToken());

        actionHandle.handleActionStart(actionName, null);

        HttpUrl.client.get(url, params, new MyAsyncHttpResponseHandler() {
            @Override
            public void onMySuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String response = new String(bytes);
                    if (response != null) {
//                        if (JsonUtils.isSuccess(response)) {
                        try {
                            dataParse.onParse(response);
                        } catch (Exception e) {
                            actionHandle.handleActionError(actionName, "请求出问题啦~555");
                            e.printStackTrace();
                            LogUtil.exception(e);
                        }
//                        } else {
//                            actionHandle.handleActionError(actionName, response);
//                        }
                    }
                    actionHandle.handleActionFinish(actionName, null);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    actionHandle.handleActionError(actionName, null);
                }
                actionHandle.handleActionFinish(actionName, null);
            }
            @Override
            public void onMyFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                actionHandle.handleActionError(actionName, null);
                actionHandle.handleActionFinish(actionName, null);
            }
        });

    }
    // 解析数据
    public interface DataParse {
        void onParse(String result) throws com.google.gson.JsonParseException;
    }

}
