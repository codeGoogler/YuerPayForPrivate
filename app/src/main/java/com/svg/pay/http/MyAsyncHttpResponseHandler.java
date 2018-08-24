package com.svg.pay.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.svg.pay.MyBaseApplication;
import com.svg.pay.utils.LogUtil;
import com.svg.pay.view.LoadingDialog;

import cz.msebera.android.httpclient.Header;

/**
 * 类名MyAsyncHttpResponseHandler
 * 类描述： 【类功能】
 * 创建人：李静波
 * 创建时间：2017/3/14
 * 修改人：yang
 * 修改时间：2017/3/14
 * 修改备注：
 */
public abstract class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private LoadingDialog loadingDialog;
    private boolean isShow;

    public MyAsyncHttpResponseHandler(){

    }
    public MyAsyncHttpResponseHandler(Context context){
        loadingDialog = new LoadingDialog(context);
    }
    public MyAsyncHttpResponseHandler(boolean isShow, Context context){
        this.isShow = isShow;
        loadingDialog = new LoadingDialog(context);
    }
    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        LogUtil.e(MyBaseApplication.TAG_HTTP, new String(bytes));
        LogUtil.e(MyBaseApplication.TAG_HTTP, "========http'log----end=======");
        onMySuccess(i,headers,bytes);
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        onMyFailure(i,headers,bytes,throwable);
    }

    public abstract void onMySuccess(int i, Header[] headers, byte[] bytes);

    public abstract void onMyFailure(int i, Header[] headers, byte[] bytes, Throwable throwable);

    @Override
    public void onStart() {
        super.onStart();

        if (isShow&&loadingDialog!=null){
            loadingDialog.show();
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        if (isShow&&loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
