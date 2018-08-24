package requesdt.volley.com.handlerequestlib.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by baby on 2017/5/26.
 */

public class HttpTask<T> implements Runnable {

    private IHttpService httpService;
    private IHttpListener httpListener;

    protected  <T> HttpTask(T requestInfo, String url, IHttpService httpService,IHttpListener httpListener) {
        this.httpService=httpService;
	    this.httpListener=httpListener;
        httpService.setUrl(url);
        //设置关系
        httpService.setHttpCallBack(httpListener);
        //设置请求参数
        if (requestInfo != null) {
         //   String requestContent=  JSON.toJSONString(requestInfo);
            HashMap<String ,String> hashMap = (HashMap<String, String>) requestInfo;
            StringBuilder stringBuilder = new StringBuilder();
            if(hashMap.size() >0 ){
                Iterator<HashMap.Entry<String, String>> iterator = hashMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    entry.getValue();
                    if(TextUtils.isEmpty(stringBuilder.toString())){
                        stringBuilder.append( entry.getKey()+"="+ entry.getValue());
                    }else{
                        stringBuilder.append("&").append( entry.getKey()+"="+ entry.getValue());
                    }
                }
            }
            try {
                this.httpService.setRequestData(stringBuilder.toString());
               //this.httpService.setRequestData(stringBuilder.toString().getBytes("UTF-8"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        //执行网络请求
        httpService.excute();
    }

}
