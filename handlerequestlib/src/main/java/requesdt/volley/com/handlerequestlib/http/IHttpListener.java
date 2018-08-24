package requesdt.volley.com.handlerequestlib.http;

import java.io.InputStream;

/**
 * Created by baby on 2017/5/26.
 */

public interface IHttpListener {
    //接收上一个接口的结果
    void onSuccess(InputStream inputStream);

    void onFailure();
}
