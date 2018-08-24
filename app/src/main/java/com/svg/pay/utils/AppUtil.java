package com.svg.pay.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class AppUtil {

    /**
     * 拍照获取图片
     *
     * @param act
     * @param requestCode
     * @param tempFile    照片路径
     */
    public static void takePicFromCamera(Activity act, int requestCode, File tempFile) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 将拍到的照片临时存储到指定路径，路径的uri截图的时候要用到
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

        act.startActivityForResult(cameraIntent, requestCode);

    }

    /**
     * 打开本地相册，获取图片
     *
     * @param act
     * @param requestCode
     */
    public static void takePicFromaLocal(Activity act, int requestCode) {
        //在4.3或以下可以直接用ACTION_GET_CONTENT的,在4.4或以上,官方建议用ACTION_OPEN_DOCUMENT,
        //返回的Uri不一样，之前的格式：content://media/external/images/media/49704
        //4.4以后的格式为：content://com.android.providers.media.documents/document/image:3951
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent localIntent2 = Intent.createChooser(localIntent, "选择");
        act.startActivityForResult(localIntent2, requestCode);
    }

    /**
     * 获取App的包名
     *
     * @return
     */
    public static String getAppPackageName(Context context) {
        int pID = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<RunningAppProcessInfo> i = l.iterator();
        while (i.hasNext()) {
            RunningAppProcessInfo info = i
                    .next();
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 获取App的名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            String appName = context.getResources().getString(labelRes);
            return appName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "com.temp";
        }
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @param ctx
     * @return
     */
    public static int getVersion(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(ctx.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            // can't reach
            return 0;
        }
    }

    /**
     * 下载APK，通知保存id
     *
     * @param ctx
     * @param url
     * @return 需要保存到SharedPreference
     */
    public static long downloadApk(Context ctx, String url) {
        DownloadManager manager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("恩福APP正在下载,点击取消");
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        //设置下载存放的文件夹和文件名字,华为p9plus 7.0 不设置安装时解析错误
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myApp.apk");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        // 禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        // request.setShowRunningNotification(false);
        // 不显示下载界面
        request.setVisibleInDownloadsUi(true);

        // request.setDestinationInExternalFilesDir(this, null, "tar.apk");
        long id = manager.enqueue(request);

        return id;
    }

    /**
     * 安裝App
     *
     * @param act
     * @param path
     */
    public static void installAPK(Activity act, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");//
        intent.setDataAndType(Uri.fromFile(
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "myApp.apk")),
                "application/vnd.android.package-archive");
        act.startActivityForResult(intent, 100);
    }

    /**
     * 安裝App
     *
     * @param act
     * @param file
     */
    public static void installAPK(Context act, File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        act.startActivity(intent);
    }

    /**
     * 获取手机IMEI码
     */
    public static String getPhoneIMEI(Context cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     *
     * @return 形如2.3.3
     */
    public static String getSystemVersion() {
        return VERSION.RELEASE;
    }

    /**
     * 回到home，后台运行
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }


    /**
     * 获取设备的可用内存大小
     *
     * @param cxt 应用上下文对象context
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory(Context cxt) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * 清理后台进程与服务
     *
     * @param cxt 应用上下文对象context
     * @return 被清理的数量
     */
    public static int gc(Context cxt) {
        long i = getDeviceUsableMemory(cxt);
        int count = 0; // 清理掉的进程数
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的service列表
        List<RunningServiceInfo> serviceList = am.getRunningServices(100);
        if (serviceList != null)
            for (RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid())
                    continue;
                try {
                    android.os.Process.killProcess(service.pid);
                    count++;
                } catch (Exception e) {
                    e.getStackTrace();
                    continue;
                }
            }

        // 获取正在运行的进程列表
        List<RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        if (processList != null)
            for (RunningAppProcessInfo process : processList) {
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    // pkgList 得到该进程下运行的包名
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
//                        KJLoger.debug("======正在杀死包名：" + pkgName);
                        try {
                            am.killBackgroundProcesses(pkgName);
                            count++;
                        } catch (Exception e) { // 防止意外发生
                            e.getStackTrace();
                            continue;
                        }
                    }
                }
            }
//        KJLoger.debug("清理了" + (getDeviceUsableMemory(cxt) - i) + "M内存");
        return count;
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param act
     */
    public static void setTranslucentStatus(Activity act) {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 获取最上层activity名字
     *
     * @param ctx
     * @return
     */
    public static String getRunningActivityName(Activity ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

}
