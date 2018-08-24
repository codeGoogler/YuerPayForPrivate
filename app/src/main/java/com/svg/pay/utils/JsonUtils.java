package com.svg.pay.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	/**
	 * 服务器返回是否成功
	 * @param result
	 * @return
	 */
	public static boolean isSuccess (String result){
		try {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.optBoolean("success",false);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 服务器返回是否成功
	 * @param result
	 * @return
	 */
	public static boolean isUploadSuccess(String result){
		try {
			JSONObject jsonObject = new JSONObject(result);
			return jsonObject.optBoolean("ret",false);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取成功结果之后的data节点数据
	 * @param result
	 * @param feild
	 * @return
	 */
	public static String getKey2Value(String result,String feild){
		try {
			JSONObject jsonObject=new JSONObject(result);
			return jsonObject.optString(feild, "失败");
		} catch (JSONException e) {
			e.printStackTrace();
			return "失败";
		}
	}

	/**
	 * 获取调用失败的信息
	 * @param result
	 * @return
	 */
	public static String getResponseMessage(String result){
		try {
			JSONObject jsonObject=new JSONObject(result);
			String msg = jsonObject.optString("msg","错误了");
			return msg;
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取成功结果之后的data节点数据
	 * @param result
	 * @param feild
	 * @return
	 */
	public static String getFiledData(String result,String feild){
		try {
			JSONObject jsonObject=new JSONObject(result);
			return jsonObject.optString(feild,"");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 获取成功结果之后的data节点数据
	 * @param result
	 * @param feild
	 * @return
	 */
	public static int getFiledIntData(String result,String feild){
		try {
			JSONObject jsonObject=new JSONObject(result);
			return jsonObject.optInt(feild,-1);
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * 获取成功结果之后的data节点数据
	 * @param result
	 * @param feild
	 * @return
	 */
	public static String getFiledData(JSONObject result,String feild){
		try {
			if(result != null){
				if(result.has(feild)){
					return result.optString(feild,"");
				}
			}
			/*JSONObject jsonObject=new JSONObject(result);*/
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
