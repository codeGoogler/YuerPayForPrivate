package com.svg.pay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.svg.pay.bean.LoginModel;
import com.svg.pay.bean.LoginParams;
import com.svg.pay.http.HttpActionHandle;
import com.svg.pay.http.HttpUrl;
import com.svg.pay.http.RequestProvider;
import com.svg.pay.utils.GetToast;
import com.svg.pay.utils.LogUtil;
import com.svg.pay.utils.Vardepend;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import requesdt.volley.com.handlerequestlib.http.IDataListener;
import requesdt.volley.com.handlerequestlib.http.Volley;

public class MainActivity extends AppCompatActivity {
    private String url = "https://www.apiopen.top/login";
    //测试 https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String ,String> hashMap = new HashMap<>();
        hashMap.put("key","00d91e8e0cca2b76f515926a36db68f5");
        hashMap.put("phone","13594347817");
        hashMap.put("passwd","123456");
       /* Volley.sendJSONRequest(hashMap, HttpUrl.main+"v2/quiz/lists", LoginModel.class, new IDataListener<LoginModel>() {
            @Override
            public void onSuccess(LoginModel responceData) {
                Toast.makeText(MainActivity.this,responceData.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Log.i("TAG","onFailure()");
            }
        });*/
        RequestProvider requestProvider = new RequestProvider(new HttpActionHandle() {
            @Override
            public void handleActionStart(String actionName, Object object) {

            }

            @Override
            public void handleActionFinish(String actionName, Object object) {

            }

            @Override
            public void handleActionError(String actionName, Object object) {

            }

            @Override
            public void handleActionSuccess(String actionName, Object object) {
                LoginModel responceData = (LoginModel) object;
                Toast.makeText(MainActivity.this,responceData.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestProvider.login();
        loadAnserListData();
    }
    private void loadAnserListData() {
        RequestParams params = new RequestParams();
//        params.put("key","00d91e8e0cca2b76f515926a36db68f5");
//        params.put("phone","13594347817");
//        params.put("passwd","123456");
        //https://crm.futurelab.tv/searchbysinglefield/?key=android&pagesize=20&pageno=1
        HttpUrl.getForLogHeard(MainActivity.this, HttpUrl.main + "v2/quiz/lists", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String result = new String(bytes);
                    LogUtil.e(MyBaseApplication.TAG_HTTP, "Analyz----"+result);
                    JSONObject object = new JSONObject(result);
                    if (object.optString("code").equals("0")) {
                        JSONObject info = object.optJSONObject("info");
                        JSONArray jsonObject = info.optJSONArray("data");
//                        dealSetData(jsonObject);
                    }else {
                        GetToast.useString(MainActivity.this,TextUtils.isEmpty(object.optString("message")) ? "" :"请求失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                try {
                    GetToast.useString(MainActivity.this,""+throwable.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
