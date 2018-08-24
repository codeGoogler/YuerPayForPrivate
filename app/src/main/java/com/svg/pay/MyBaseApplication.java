package com.svg.pay;

import android.app.Application;
import android.content.Context;

/**
 * 类功能描述：</br>
 *
 * @author 于亚豪
 * @version 1.0 </p> 修改时间：2018/8/21</br> 修改备注：</br>
 */
public class MyBaseApplication extends Application {
    public  static  String TAG_HTTP = "YuerPayForPrivate_Http";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public  static  Context getMyAppContext(){
        return  context;
    }
}
