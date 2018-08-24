package requesdt.volley.com.handlerequestlib.http;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 */
public class JsonHttpService  implements IHttpService{

    String url;
    IHttpListener httpListener;
//    private byte[] requestData;
    private String  requestData;

    @Override
    public void setUrl(String url) {
        this.url=url;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener=httpListener;
    }

    @Override
    public void setRequestData(String requestData) {
        this.requestData=requestData;
    }

    @Override
    public void excute() {
        httpUrlconnPost();
    }

    /**
     * 做耗时操，执行请求
     */

    HttpURLConnection urlConnection = null;

    public void httpUrlconnPost(){

        URL url = null;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();//打开http连接
            urlConnection.setConnectTimeout(6000);//连接的超时时间
            urlConnection.setUseCaches(false);//不使用缓存
            urlConnection.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
            urlConnection.setReadTimeout(3000);//响应的超时时间
            urlConnection.setDoInput(true);//设置这个连接是否可以写入数据
            urlConnection.setDoOutput(true);//设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("GET");//设置请求的方式
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设置消息的类型
            urlConnection.connect();// 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            //-------------使用字节流发送数据--------------
//            OutputStream out = urlConnection.getOutputStream();
//            BufferedOutputStream bos = new BufferedOutputStream(out);//缓冲字节流包装字节流
//            bos.write(requestData);//把这个字节数组的数据写入缓冲区中
//            bos.flush();//刷新缓冲区，发送数据
//            out.close();
//            bos.close();
            // 建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(requestData);
            dos.flush();
            dos.close();
            //------------字符流写入数据------------
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {//得到服务端的返回码是否连接成功
                InputStream in = urlConnection.getInputStream();
                httpListener.onSuccess(in);
            }
        } catch (Exception e) {
            httpListener.onFailure();
        }finally{
            urlConnection.disconnect();//使用完关闭TCP连接，释放资源
        }
    }
}
