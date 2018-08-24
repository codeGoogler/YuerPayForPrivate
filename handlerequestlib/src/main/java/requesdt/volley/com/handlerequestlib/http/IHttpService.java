package requesdt.volley.com.handlerequestlib.http;

/**
 * Created by baby on 2017/5/26.
 */

public interface IHttpService {
    void setUrl(String url);
    void setRequestData(String requestData);
    void excute();
    void setHttpCallBack(IHttpListener httpListener);
}
