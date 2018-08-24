package com.svg.pay.http;

public interface HttpActionHandle {
	//请求动作开始调用
	void handleActionStart(String actionName, Object object);
	//请求动作结束调用
	void handleActionFinish(String actionName, Object object);
	//请求动作错误调用
	void handleActionError(String actionName, Object object);
	//请求动作成功时调用
	void handleActionSuccess(String actionName, Object object);

}
