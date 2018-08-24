package com.svg.pay.http;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.svg.pay.bean.LoginModel;
import com.svg.pay.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类功能描述： 请求参数+接口调用</br>
 * @author yuyahao
 * @version 1.0 </p> 修改时间：2018/4/9. </br> 修改备注：</br>
 */
public class RequestProvider {
    private HttpActionHandle actionHandle;
    private Gson gson = new MyGsonBuilder().createGson();
    public RequestProvider(HttpActionHandle actionHandle) {
        this.actionHandle = actionHandle;
    }

    /**
     * 抽取 获得每次请求捎带的头
     *
     * @return
     */
    private Map<String, String> getMapHeader() {
        Map<String, String> mapHeader = new HashMap<String, String>();
//        mapHeader.put("NF_USER_INFO", MyApplication.getToken());
//        if (!TextUtils.isEmpty(MyApplication.getNF_USER_SESSION())) {
//            mapHeader.put("NF_USER_SESSION", MyApplication.getNF_USER_SESSION());
//        }
        return mapHeader;
    }

    /**
     * 测试：调用方式如下
     */
    public void login() {
        RequestParams params = new RequestParams();
       /* params.put("key","00d91e8e0cca2b76f515926a36db68f5");
        params.put("phone","13594347817");
        params.put("passwd","123456");*/
        HttpRequestProvider client = new HttpRequestProvider(actionHandle, Act.Flag.FLAG_LOAD_MODE_DATA);
        client.requestGet(HttpUrl.main+"v2/quiz/lists", params,new HttpRequestProvider.DataParse() {
            @Override
            public void onParse(String result) {
//                String data = JsonUtils.getFiledData(result,"info");
                Gson gson = new MyGsonBuilder().createGson();
                JSONObject info = null;
                try {
                    info = new JSONObject(result).optJSONObject("info");
                    JSONArray jsonObject = info.optJSONArray("data");
                    List<LoginModel> subList = new MyGsonBuilder().createGson().fromJson(jsonObject.toString(), new TypeToken<List<LoginModel>>() {
                    }.getType());

                    actionHandle.handleActionSuccess(Act.Flag.FLAG_LOAD_MODE_DATA,subList.get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

               /* List<SolutionSchemeBean> list = gson.fromJson(data, new TypeToken<List<SolutionSchemeBean>>() {
                }.getType());*/
            }
        });
    }

    

}