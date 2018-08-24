package com.svg.pay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.svg.pay.MyBaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * @类名: Vardepend
 * @描述: TODO(封装常用方法)
 * @作者: Pangzhonghua
 * @日期: 2015-5-25 上午10:47:14
 * @修改人:
 * @修改时间: 2015-5-25 上午10:47:14
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright 2015 北京八爪鱼（互动）科技有限公司. All rights reserved.
 */

public class Vardepend {

	/**
	 * 获取APP文件缓存路径
	 * 
	 *param context
	 * @return
	 */
	public static String getDataDir() {
		String dataDir = "";

		File file = MyBaseApplication.getMyAppContext().getExternalFilesDir(null);
		if (file == null || !file.exists()) {
			dataDir = MyBaseApplication.getMyAppContext().getFilesDir().toString()
					+ File.separator;
		} else {
			dataDir = file.toString() + File.separator;
		}

		return dataDir;
	}

	/**
	 * 获取录音相关文件缓存路径
	 *
	 * @return
	 */
	public static String getAudioDataDir() {
		String dataDir = "";
		File file = MyBaseApplication.getMyAppContext().getExternalFilesDir(null);
		if (file == null || !file.exists()) {
			dataDir = MyBaseApplication.getMyAppContext().getFilesDir().toString()
					+ File.separator;
		} else {
			dataDir = file.toString() + File.separator;
		}

		return dataDir;
	}

	/**
	 * 清空目录
	 * 
	 * @param filePath
	 */
	public static void deleteFolder(String filePath) {
		File file = new File(filePath);

		if (file.isDirectory()) {// 处理目录

			File files[] = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					deleteFolder(files[i].getAbsolutePath());
				}
			}

			file.delete();
		} else {
			file.delete();
		}
	}
	


	/**
	 * base64编码
	 * 
	 * @param string
	 * @return
	 */
	public static String base64encode(String string) {

		String str = "";

		// try {
		// str = URLEncoder.encode(Base64.encodeToString((string).getBytes(),
		// Base64.NO_WRAP),"utf-8");
		// //str = str.replaceAll("%2B", "+");//将%2B替换成+
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// str = new String(Base64.encode(string.getBytes(), Base64.DEFAULT));
		str = Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);

		return str;
	}

	/**
	 * base64解码
	 * @param string
	 * @return
	 */
	public static String base64decode(String string) {
		return new String(Base64.decode(string, Base64.NO_WRAP));
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	/**
	 * 线程里运行 OnRun
	 */
	public static abstract class OnRun implements Runnable {

		public abstract void runn() throws Exception;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				runn();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
			}
		}

	}

	/**
	 * 创建view
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static View createView(Context context, int id) {
		LayoutInflater factory = LayoutInflater.from(context);
		return factory.inflate(id, null);
	}

	/**
	 * 配置文件(把对象写入文件)
	 * 
	 * @param obj
	 * @param file
	 */
	public static void objToFile(Context context, Object obj, String file) {

		if (context == null) {
			return;
		}

		SharedPreferences settings = context.getSharedPreferences(file, 0);
		SharedPreferences.Editor editor = settings.edit();
		Field[] fields = obj.getClass().getFields();

		for (Field f : fields) {
			try {
				if (Modifier.isFinal(f.getModifiers())
						|| Modifier.isStatic(f.getModifiers()))
					continue;

				editor.putString(f.getName(), f.get(obj).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		editor.commit();
	}


	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long 单位为 byte
	 * @throws Exception
	 */
	public static long getFolderSize(File file) {
		long size = 0;
		File[] fileList = file.listFiles();
		if (fileList == null)
			return 0;

		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}

		return size;
	}

	// 获取文件夹大小
	public static long getFolderSize(String file) {
		return getFolderSize(new File(file));

	}

	/**
	 * 内存大小换算
	 * 
	 * @param len
	 * @return
	 */
	public static String getByte(long len) {
		if (len <= 1024) {
			return len + " B";
		}

		if (len <= 1024 * 1024) {
			return len / 1024 + " KB";
		}

		if (len <= 1024 * 1024 * 1024) {
			return String.format("%.1f MB", (double) len / 1024 / 1024);
		}

		return String.format("%.1f GB", (double) len / 1024 / 1024 / 1024);
	}

	/**
	 * 全部时间信息
	 */
	public static String AllTime(long currentTime) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日      HH:mm");

		Date date = new Date(currentTime);

		return formatter.format(date);

	}



	/**
	 * 打印日志（统一管理）
	 * 
	 * @param string
	 */
	public static void OutPrint(String string) {
		if (string != null) {
			System.out.println(string);
		}
	}



    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = MyBaseApplication.getMyAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MyBaseApplication.getMyAppContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

	static String userid = "0";


    /**
     * 判断是否符合正常暖心值
     * @param str
     * @return
     */
    public static boolean isIntNum(String str) {

        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 大数量评论文字处理
     * @param numstring
     * @return
     */
    public static String getNum(String numstring){

        String s_result = "";
        if (isIntNum(numstring)){

            int i_num = Integer.parseInt(numstring);
            if (i_num<10000){
                s_result  = numstring;
            }else {
                s_result = i_num/10000 + "万+";
            }

            return s_result;
        }else {
            return  s_result;
        }

    }

    public static String replaceText(String textstring){
        if (textstring==null){
            return "";
        }
        String s_result = "";
        s_result = textstring;
        s_result = s_result.replace("\n","*n0X86");
        s_result = s_result.replace("\t","*t0X85");
        s_result = s_result.replace("\r","*r0X84");

        return s_result;
    }

    public static void Toast(String string){

        if (string == null){
            return;
        }
        Toast.makeText(MyBaseApplication.getMyAppContext(), string,Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新字段标识（存code）
     */
    static String s_update = "";
    public static void setUpdate(String string){

        if (string == null){
            return;
        }
        s_update = string;
    }
    public static String getUpdate(){
        return s_update;
    }

    /**
     * 获取字符串前n个字
     *
     * @return
     */
    public static String getCutString(String string, int len) {
        if(string==null){
            return "";
        }
        if (string.length() < len+1) {
            return string;
        } else {
            return string.substring(0, len) + "…";
        }

    }

    static String s_send = "";
    public static String getSendString() {
            return s_send;
    }

    public static void setSendString(String string) {
        s_send = string;
    }

	/**
	 * 判断字符串是否为数字类型
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 截取字符串类型 0~9999显示数字，9999以上显示*.*w,例如1.5w，2.3w
	 */
	public static String showNum(String str){
		if(isNumeric(str)){
			if(str.length()>4){
				int shang = Integer.parseInt(str) / 10000;
				int yu = Integer.parseInt(str) % 10000;
				int yushang = yu/1000;
				return shang+"."+yushang+"w";
			}else{
				return str;
			}
		}else{
			return "0";
		}
	}
    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = {"\"", "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }


    public static int statusLeftTop(int nowtime,int start ,int end ,int review_date_start,int review_date_end){
		// 竞赛未开始  1即将开始 2  报名中   3  评审中   4 已结束
		int status = 4;
		if (nowtime<start){
			status = 1;
		}else if (nowtime<end){
			status = 2;
		} else if (nowtime<review_date_end){
			status = 3;
		} else if (nowtime>review_date_end){
			status = 4;
		}
		return status;
	}



	public static int statusLeftTopForWish(int nowtime,int start ,int end ,int review_date_start){
		// 竞赛未开始  1即将开始 2  活动中   3 已结束
		int status = 3;
		if (nowtime<start){
			status = 1;
		}else if (nowtime<end){
			status = 2;
		} else if (nowtime>end){
			status = 3;
		}
		return status;
	}


    public static String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath); // Create an FileInputStream instance according to the filepath
            byte[] buffer = new byte[1024]; // The buffer to read the file
            MessageDigest digest = MessageDigest.getInstance("MD5"); // Get a MD5 instance
            int numRead = 0; // Record how many bytes have been read
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead); // Update the digest
            }
            byte [] md5Bytes = digest.digest(); // Complete the hash computing
            return convertHashToString(md5Bytes); // Call the function to convert to hex digits
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close(); // Close the InputStream
                } catch (Exception e) { }
            }
        }
    }
    private static String convertHashToString(byte[] hashBytes) {
        String returnVal = "";
        for (int i = 0; i < hashBytes.length; i++) {
            returnVal += Integer.toString(( hashBytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal.toLowerCase();
    }



	public static Bitmap getNameBitmap(String name,int color,float varSize) {

        /* 把@相关的字符串转换成bitmap 然后使用DynamicDrawableSpan加入输入框中 */
		name = name;
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAntiAlias(true);
		paint.setTextSize(varSize);
		Rect rect = new Rect();
		paint.getTextBounds(name, 0, name.length(), rect);
		// 获取字符串在屏幕上的长度
		int width = (int) (paint.measureText(name));
		final Bitmap bmp = Bitmap.createBitmap(width, rect.height(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		canvas.drawText(name, rect.left, rect.height() - rect.bottom, paint);
		return bmp;
	}




	public static SpannableStringBuilder colorSelectSchool(String sOri, String sString, final int color){
		SpannableStringBuilder spannable = new SpannableStringBuilder(sOri);
		int i = sOri.indexOf(sString);
		if (i==-1){
			return spannable;
		}
		spannable.setSpan(new ForegroundColorSpan(color),i,i+sString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}



	public static int timeToDay(int t){
		int t2 = t + (60*60*24-1);
		return t2/(60*60*24);

	}




}
