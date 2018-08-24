package requesdt.volley.com.handlerequestlib.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by baby on 2017/5/26.
 */

public class JsonHttpListener  implements  IHttpListener{
    //结果作为参数传进来了

    private IDataListener dataListener;

    String responceClass;


    Handler handler=new Handler(Looper.getMainLooper());

    public JsonHttpListener(String responceClass, IDataListener dataListener) {
        this.responceClass = responceClass;
        this.dataListener = dataListener;


    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content=getContent(inputStream);
       // final M responce= JSON.parseObject(content,responceClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onSuccess(responceClass);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        if (dataListener != null) {
            dataListener.onFailure();
        }
    }


    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line = null;

            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

            } catch (IOException e) {

                System.out.println("Error=" + e.toString());

            } finally {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    System.out.println("Error=" + e.toString());

                }

            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
