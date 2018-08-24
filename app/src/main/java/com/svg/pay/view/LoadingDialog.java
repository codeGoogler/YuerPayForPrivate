package com.svg.pay.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.svg.pay.R;


/**
 * Class: LoadingDialog
 * Describe:
 * Date: 2016-04-15
 * Time: 13:42
 * FIXME
 */
public class LoadingDialog extends Dialog {

    ImageView ivLoading;
    TextView loadingText;

    private Window window = null;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_loading_web);
        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        loadingText = (TextView) findViewById(R.id.loading_text);

        windowDeploy(0, 0);
        //不可以点击其它区域取消
        setCanceledOnTouchOutside(false);
        //不可以点击返回键取消
        setCancelable(false);

        Animation animation = AnimationUtils.loadAnimation( context, R.anim.loading_animation);
        ivLoading.startAnimation(animation);
    }

    //设置窗口显示
    public void windowDeploy(int x, int y) {
        window = getWindow(); //得到对话框
        window.setDimAmount(0);//去背景遮盖
        window.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    public void setLoadText(String content) {
        loadingText.setText(content);
    }
}
