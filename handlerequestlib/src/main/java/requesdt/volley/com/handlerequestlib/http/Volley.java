package requesdt.volley.com.handlerequestlib.http;

import java.util.concurrent.FutureTask;

/**
 * Created by baby on 2017/5/26.
 */

public class Volley {
    public static <T> void sendJSONRequest(T requestInfo, String url, String responce, IDataListener dataListener) {
        IHttpListener httpListener=new JsonHttpListener(responce,dataListener);
        IHttpService httpService=new JsonHttpService();
        HttpTask<T> httpTask=new HttpTask(requestInfo,url,httpService,httpListener);
        ThreadPoolManager.getInstance().execute(httpTask);
    }
}
